package net.springboot.javaguides.controller;

import net.springboot.javaguides.entity.Student;
import net.springboot.javaguides.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/students/")
public class StudentController {

	@Autowired
	private StudentRepository studentRepository;	

	@GetMapping
	public ResponseEntity<List<Student>> students() {
		return new ResponseEntity<>(this.studentRepository.findAll(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Student> addStudent(@Valid @RequestBody Student student) {
		return new ResponseEntity<>(this.studentRepository.save(student), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable("id") long id, @Valid @RequestBody Student student) {
		Student oldStudent = this.studentRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid student id : " + id));

		if(student.getEmail() != null){
			oldStudent.setEmail(student.getEmail());
		}

		if(student.getName() != null){
			oldStudent.setName(student.getName());
		}

		if(student.getPhoneNo() != null){
			oldStudent.setPhoneNo(student.getPhoneNo());
		}
		// update student
		return new ResponseEntity<>(studentRepository.save(oldStudent), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity deleteStudent(@PathVariable ("id") long id) {
		Student student = this.studentRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid student id : " + id));
		this.studentRepository.delete(student);
		return ResponseEntity.ok().body("Suppression effectuée avec succès !");
	}
}

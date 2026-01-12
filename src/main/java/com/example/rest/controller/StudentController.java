package com.example.rest.controller;

import com.example.rest.dto.StudentDto;
import com.example.rest.entity.Student;
import com.example.rest.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudents(){
        List<Student> allStudents = studentService.getAllStudents();
        List<StudentDto> mappedStudents= allStudents.stream().map(student-> new StudentDto(student.getId(),student.getName(),student.getEmail())).toList();
        return ResponseEntity.ok(mappedStudents);
    }

    @GetMapping
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long Id){
        Student studentWith= studentService.getById(Id);
        StudentDto convertedStudent= new StudentDto(studentWith.getId(),studentWith.getName(),studentWith.getEmail());
        return ResponseEntity.ok(convertedStudent);
    }

    @PostMapping
    public ResponseEntity<StudentDto> updateById(StudentDto passedStudent){
        Student mappedStudent = new Student();
        mappedStudent.setId(passedStudent.getId());
        mappedStudent.setName(passedStudent.getName());
        mappedStudent.setEmail(passedStudent.getEmail());
        Student updatedStudent=studentService.updateById(mappedStudent);
        StudentDto mappedDtoRes=new StudentDto(updatedStudent.getId(),updatedStudent.getName(),updatedStudent.getEmail());
        return ResponseEntity.ok(mappedDtoRes);
    }

    @PostMapping
    public ResponseEntity<StudentDto> createStudent(@RequestBody StudentDto studentDto){
        Student student = new Student();
        student.setName(studentDto.getName());
        student.setEmail(studentDto.getEmail());

        Student savedStudent = studentService.saveStudent(student);

        StudentDto responseDto = new StudentDto(
                savedStudent.getId(),
                savedStudent.getName(),
                savedStudent.getEmail()
        );

        return ResponseEntity.ok(responseDto);
    }
}
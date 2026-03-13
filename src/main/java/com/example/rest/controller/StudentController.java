package com.example.rest.controller;

import com.example.rest.dto.StudentDto;
import com.example.rest.entity.Student;
import com.example.rest.service.StudentService;
import com.example.rest.utility.ResponseUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
     public ResponseEntity< ResponseUtility.SuccessResponse<List<StudentDto>>> getAllStudents(){
        List<Student> allStudents = studentService.getAllStudents();
        List<StudentDto> mappedStudents= allStudents.stream().map(student-> new StudentDto(student.getId(),student.getName(),student.getEmail())).toList();
        ResponseUtility.SuccessResponse<List<StudentDto>> response=new ResponseUtility.SuccessResponse<List<StudentDto>>("success","students fetched successfully",mappedStudents,null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseUtility<StudentDto>> getStudentById(@PathVariable("id") Long Id){
        Student studentWith= studentService.getById(Id);
        StudentDto convertedStudent= new StudentDto(studentWith.getId(),studentWith.getName(),studentWith.getEmail());
        ResponseUtility<StudentDto> response=new ResponseUtility.SuccessResponse<StudentDto>("success","students fetched successfully",convertedStudent,null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update")
    public ResponseEntity<StudentDto> updateById(StudentDto passedStudent){
        Student mappedStudent = new Student();
        mappedStudent.setId(passedStudent.getId());
        mappedStudent.setName(passedStudent.getName());
        mappedStudent.setEmail(passedStudent.getEmail());
        Student updatedStudent=studentService.updateById(mappedStudent);
        StudentDto mappedDtoRes=new StudentDto(updatedStudent.getId(),updatedStudent.getName(),updatedStudent.getEmail());
        return ResponseEntity.ok(mappedDtoRes);
    }

    @PostMapping("/create")
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

    @PatchMapping("/update-any")
    public  ResponseEntity<ResponseUtility<StudentDto>> updateStudent(Long id, Map<String,Object> updates){
        Student matchedStudent=studentService.updatePartialStudent(id,updates);
        StudentDto dtoStudent=new StudentDto(matchedStudent.getId(),matchedStudent.getName(),matchedStudent.getEmail());
        ResponseUtility<StudentDto> response = new ResponseUtility.SuccessResponse<StudentDto>("success","Student update successfully",dtoStudent,null);
        return ResponseEntity.ok(response);
    }

}
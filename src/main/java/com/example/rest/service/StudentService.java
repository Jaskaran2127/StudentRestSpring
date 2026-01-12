    package com.example.rest.service;

    import com.example.rest.entity.Student;
    import com.example.rest.repository.StudentRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.util.List;

    @Service
    public class StudentService {
        @Autowired
        private StudentRepository studentRepository;

        public Student saveStudent(Student student){
            return  studentRepository.save(student);
        }

        public List<Student> getAllStudents(){
            return studentRepository.findAll();
        }

        public Student getById(Long id){
            return studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found with id " + id));
        }

        public Student updateById(Student passedStudent){
            Long matchedId=passedStudent.getId();
            Student matchedStudent= studentRepository.findById(matchedId).orElseThrow(() -> new RuntimeException(("No student with id" + matchedId)));

            matchedStudent.setName(passedStudent.getName());
            matchedStudent.setEmail(passedStudent.getEmail());

            Student updatedStudent = studentRepository.save(matchedStudent);

            return updatedStudent;
        }
    }

package com.danilermolenko.boot.controllers;

import com.danilermolenko.boot.models.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1, "Danil"),
            new Student(2, "Yasya"),
            new Student(3, "Vika")
    );

    @GetMapping
    public List<Student> findAll(){
        return STUDENTS;
    }
    @GetMapping("/{studentID}")
    public Student findOneByID(@PathVariable("studentID") int id){
        return STUDENTS.stream()
                .filter(st -> st.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Not found"));
    }
}

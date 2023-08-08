package com.danilermolenko.boot.controllers;

import com.danilermolenko.boot.models.Student;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/management/api")
public class StudentManagementController {
    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1, "Danil"),
            new Student(2, "Yasya"),
            new Student(3, "Vika")
    );
    @GetMapping("/students")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMIN_TRAINEE')")
    public List<Student> findAll(){
        System.out.println("public List<Student> findAll(){");
        return STUDENTS;
    }
    @PostMapping("/students")
    @PreAuthorize("hasAuthority('user:write')")
    public void save(@RequestBody Student student){
        System.out.println("save");
        System.out.println(student);
    }
    @PutMapping("/students/{id}")
    @PreAuthorize("hasAuthority('user:write')")
    public void update(@PathVariable Integer id, @RequestBody Student student){
        System.out.println("update");
        System.out.println(id + " " + student);
    }
    @DeleteMapping("/students/{id}")
    @PreAuthorize("hasAuthority('user:write')")
    public void delete(@PathVariable Integer id){
        System.out.println("delete");
        System.out.println(id);
    }
}

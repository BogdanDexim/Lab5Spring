package com.example.demo.Controler;

import com.example.demo.Repository.Student;
import com.example.demo.Service.UserService;
import com.example.demo.Repository.OracleStudentsRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "users", description = "Operations with users")
public class StudentControler {

    private final OracleStudentsRepository oracleStudentsRepository;
    private final UserService userservice;

    public StudentControler(OracleStudentsRepository oracleStudentsRepository, UserService userservice) {

        this.oracleStudentsRepository = oracleStudentsRepository;
        this.userservice = userservice;
    }

    
    @GetMapping
    @Operation(

    		summary = "Get Students",
    		description = "Get a list with all Students"
    		)
    		@ApiResponses({
    		@ApiResponse(responseCode = "200", description = "Success")
    		})
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> studentList = oracleStudentsRepository.findAll();
        return ResponseEntity.ok(studentList);
    }

    @GetMapping("/findByName")
    @Operation(

    		summary = "Get Students by Name",
    		description = "Get a list with Students with specific second name",
    		parameters = {@Parameter(name = "secondname", description = "Student`s last-name", example = "Шевченко")}
    		)
    		@ApiResponses({
    		@ApiResponse(responseCode = "200", description = "Success"),
    		@ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    		})
    public ResponseEntity<List<Student>> findStudentsByName(@RequestParam String secondname) {
        List<Student> studentList = userservice.findStudentsByName(secondname);
        if (studentList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentList);
    }

    @GetMapping("/findByGroup")
    @Operation(

    		summary = "Get Students by Group",
    		description = "Get a list with Students from specific Group",
    		parameters = {@Parameter(name = "groupname", description = "Student`s group name", example = "ІО-21")}
    		)
    		@ApiResponses({
    		@ApiResponse(responseCode = "200", description = "Success"),
    		@ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    		})
    public ResponseEntity<List<Student>> findStudentsByGroup(@RequestParam String groupname) {
        List<Student> studentList = userservice.findStudentsByGroup(groupname);
        if (studentList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentList);
    }
}
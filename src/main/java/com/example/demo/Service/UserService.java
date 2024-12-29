package com.example.demo.Service;
import com.example.demo.Repository.Student;

import com.example.demo.Repository.OracleStudentsRepository;

import java.util.List;


import org.springframework.stereotype.Service;

@Service
public class UserService {
	private final OracleStudentsRepository studentRepository;
    public UserService(OracleStudentsRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    public List<Student> findStudentsByName(String secondname) {
    	return studentRepository.findStudentsByName(secondname);
    }
    public List<Student> findStudentsByGroup(String group) {
    	return studentRepository.findStudentsByGroup(group);
    }
}

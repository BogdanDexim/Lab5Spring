package com.example.demo.Service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.example.demo.Repository.Student;
import com.example.demo.Repository.OracleStudentsRepository;

@Service
public class AdminService {
	private final OracleStudentsRepository studentRepository;
	
	 public AdminService(OracleStudentsRepository studentRepository) {
	     this.studentRepository = studentRepository;
	 }
	 
//	 public void editStudent(int studId, String firstname, String secondname, String groupname){
	//    	Student student = new Student(studId , firstname, secondname, groupname);
	//    	studentRepository.update(student);
//}
	    public void removeStudent(int studId){
	        studentRepository.removeStudent(studId);
	    }
	    @Transactional
	    public void newStudent(int id, String firstname, String secondname, String groupname){
	    	Student student = new Student(id , firstname, secondname, groupname);
	    	studentRepository.save(student);
	        if ( firstname == null || secondname == null || groupname == null) {
	        	TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	        }

	    }

}

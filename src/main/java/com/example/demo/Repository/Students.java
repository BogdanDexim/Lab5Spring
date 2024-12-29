package com.example.demo.Repository;

import java.util.List;

public interface Students {
		Student findStudenById(int id); // Optional<User>
		List<Student> findStudentsByName(String secondname);
		List<Student> findStudentsByGroup(String groupname);
		List<Student> findAll();
		Student save(Student student);
		void removeStudent(int id);

		


}

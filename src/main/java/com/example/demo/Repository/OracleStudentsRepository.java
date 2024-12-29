package com.example.demo.Repository;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
@Repository
@Primary
@AllArgsConstructor
public class OracleStudentsRepository implements Students {
	
	private final JdbcTemplate jdbcTemplate;
	
	private static final String FIND_ALL_SQL = "SELECT * FROM STUDENTs";
	private static final String FIND_BY_ID_SQL = "SELECT * FROM STUDENTS WHERE id=?";
	private static final String FIND_BY_SECONDNAME_SQL = "SELECT * FROM STUDENTS WHERE secondname=?";
	private static final String FIND_BY_GROUPNAME_SQL = "SELECT * FROM STUDENTS WHERE groupname=?";
	private static final String DELETE_BY_ID_SQL = "DELETE FROM STUDENTS WHERE id=?";
	private static final String UPDATE_SQL = "UPDATE STUDENTS SET firstname=?, secondname=?, groupname=? WHERE id=?";
	private static final String INSERT_SQL = "INSERT INTO STUDENTS (id, firstname, secondname, groupname) VALUES (?, ?, ?, ?)";
	private static final RowMapper<Student> ROW_MAPPER = new RowMapper<>() {
		
	@Override
	public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
	return new Student(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
	}
	};
	@Override
	public List<Student> findAll() {

		System.out.println(jdbcTemplate.query(FIND_ALL_SQL, ROW_MAPPER));

	return jdbcTemplate.query(FIND_ALL_SQL, ROW_MAPPER);
	}
	

	@Override
	public Student findStudenById(int id) {
		try {
			return jdbcTemplate.queryForObject(FIND_BY_ID_SQL, ROW_MAPPER, id);
			} catch(IncorrectResultSizeDataAccessException e) {

			}
		return null;
	}

	@Override
	public List<Student> findStudentsByName(String secondname) {
		try {
			return jdbcTemplate.query(FIND_BY_SECONDNAME_SQL, ROW_MAPPER, secondname);
			} catch(IncorrectResultSizeDataAccessException e) {

			}
		return null;

	}

	@Override
	public List<Student> findStudentsByGroup(String groupname) {
		try {
			return jdbcTemplate.query(FIND_BY_GROUPNAME_SQL, ROW_MAPPER, groupname);
			} catch(IncorrectResultSizeDataAccessException e) {

			}
		return null;

	}
	private int update(Student student) {
		return jdbcTemplate.update(UPDATE_SQL, student.getFirstname(), student.getSecondname(), student.getGroupname(), student.getStudId());
		}
	@Override
	public Student save(Student student) {
		if(update(student) == 1) return student;
		return create(student);

	}
	private Student create(Student student) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(INSERT_SQL,

		Types.NUMERIC, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR);

		pscf.setGeneratedKeysColumnNames("ID");
		PreparedStatementCreator preparedStatementCreator = pscf.newPreparedStatementCreator(
		new Object[] { student.getStudId(),student.getFirstname(), student.getSecondname(), student.getGroupname() });
		jdbcTemplate.update(preparedStatementCreator, keyHolder);
		int newId = keyHolder.getKey().intValue();
		return new Student(newId, student.getFirstname(), student.getSecondname(), student.getGroupname());
		}
	@Override
	public void removeStudent(int id) {
		jdbcTemplate.update(DELETE_BY_ID_SQL, id);
		
	}
	
	

}

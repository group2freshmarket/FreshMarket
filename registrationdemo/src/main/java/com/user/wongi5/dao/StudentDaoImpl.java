package com.user.wongi5.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.user.wongi5.model.Course;
import com.user.wongi5.model.Student;

@Repository
public class StudentDaoImpl implements StudentDao {

	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	// @Override
	public Student findByEmail(String email) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("email", email);

		String sql = "SELECT * FROM students WHERE email=:email";

//        Student result = namedParameterJdbcTemplate.queryForObject(
//                    sql,
//                    params,
//                    new StudentMapper());

		List<Student> results = namedParameterJdbcTemplate.query(sql, params, new StudentMapper());

		if(results.size() == 0) {
			return null;
		}
		Student student = results.get(0);
		
		List<Course> courseResults = findRegisteredCourses(student.getEmail());
		
		student.setRegisteredCourses(courseResults);
		
		return student;

	}

	public int registerCourseByCourseCode(String email, String code) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("email", email);
		params.put("code", code);

		String sql = "INSERT INTO registrations VALUES(:email, :code)";

//        Student result = namedParameterJdbcTemplate.queryForObject(
//                    sql,
//                    params,
//                    new StudentMapper());

		return namedParameterJdbcTemplate.update(sql, params);
		
	}

	
	private static final class StudentMapper implements RowMapper<Student> {

		public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
			Student student = new Student();
			student.setName(rs.getString("name"));
			student.setEmail(rs.getString("email"));
			student.setPassword(rs.getString("password"));
			return student;
		}
	}
	
	private static final class CourseMapper implements RowMapper<Course> {

		public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
			Course course = new Course();
			course.setCode(rs.getString("code"));
			course.setName(rs.getString("name"));
			return course;
		}
	}

	public List<Course> findRegisteredCourses(String email) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("email", email);
		
		String sql = "SELECT * FROM registrations r, courses c WHERE r.email=:email AND r.code=c.code;";
		
		List<Course> courseResults = namedParameterJdbcTemplate.query(sql, params, new CourseMapper());
		
		return courseResults;
	}

}

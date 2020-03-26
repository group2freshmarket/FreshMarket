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


@Repository
public class CourseDaoImpl implements CourseDao {

	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	//@Override
	public Course findByCode(String code) {
		
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("code", code);
        
		String sql = "SELECT * FROM courses WHERE code=:code";
		
        Course result = namedParameterJdbcTemplate.queryForObject(
                    sql,
                    params,
                    new CourseMapper());
                    
        //new BeanPropertyRowMapper(Customer.class));
        
        return result;
        
	}

	//@Override
	public List<Course> findAll() {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String sql = "SELECT * FROM courses";
		
        List<Course> result = namedParameterJdbcTemplate.query(sql, params, new CourseMapper());
        
        return result;
        
	}

	private static final class CourseMapper implements RowMapper<Course> {

		public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
			Course course = new Course();
//			user.setId(rs.getInt("id"));
			course.setCode(rs.getString("code"));
			course.setName(rs.getString("name"));
			return course;
		}
	}

}

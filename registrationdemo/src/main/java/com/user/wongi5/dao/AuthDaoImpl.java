package com.user.wongi5.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.user.wongi5.model.User;

@Repository
public class AuthDaoImpl implements AuthDao {

	NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate )
	{
		this.jdbcTemplate=jdbcTemplate;
	}
	
	private SqlParameterSource getSqlParameterByModel(User c)
	{
		MapSqlParameterSource parameterSource=new MapSqlParameterSource();
		if(c!=null)
		{
			parameterSource.addValue("email", c.getEmail());
			parameterSource.addValue("name", c.getName());
			parameterSource.addValue("password", c.getPassword());
			parameterSource.addValue("userType", c.getUserType());
		}
		return parameterSource;
	}
	//add to the database
	
	public boolean addUser(User customer) {
		boolean res=true;
		try {
		String sql="INSERT INTO USER(email,name,password,userType) VALUES(:email,:name,:password,:userType)";
		jdbcTemplate.update(sql, getSqlParameterByModel(customer));
		}catch (Exception e) {
			res=false;
			System.out.println(e.getMessage());
		}
		return res;
	}

	//check user
	public boolean checkUser(String userType, String email, String password) {
		String sql="SELECT * FROM USER WHERE email=:email AND password=:password AND userType=:userType";
		User user=null;
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("email", email);
        params.put("password", password);
        params.put("userType", userType);
        System.out.println("email  : "+email);
        System.out.println("userType  : "+userType);
			user= (User) jdbcTemplate.queryForObject(sql, params,new UserMapper());
		if(user!=null)
		{
			System.out.println("Name : "+user.getName());
			return true;
		}
		return false;
	}
	
	private static final class UserMapper implements RowMapper<User>{

		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		
			User c=new User();
			c.setEmail(rs.getString("email"));
			c.setName(rs.getString("name"));
			c.setPassword(rs.getString("password"));
			c.setUserType(rs.getString("userType"));
			return c;
		}
		
	}

	@Override
	public User getUser(String email) {
		String sql = "SELECT * FROM USER WHERE email=:email;";
		
		User user = null;
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("email", email);
        
		user= (User) jdbcTemplate.queryForObject(sql, params,new UserMapper());
		
		return user;
	}

	@Override
	public boolean updateUser(User user) {
		boolean res = true;
		try {
			String sql = "UPDATE User SET name=:name, password=:password, userType=:userType WHERE email=:email";
			
			jdbcTemplate.update(sql, getSqlParameterByModel(user));
		} catch (Exception e) {
			res = false;
			System.out.println(e.getMessage());
		}
		return res;
	}

	
}

package com.user.wongi5.dao;

import java.util.List;

import com.user.wongi5.model.Course;
 
public interface CourseDao {

	Course findByCode(String name);
	 
	List<Course> findAll();

}

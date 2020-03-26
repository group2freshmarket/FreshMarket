package com.user.wongi5.dao;

import java.util.List;

import com.user.wongi5.model.Course;
import com.user.wongi5.model.Student;

 
public interface StudentDao {

	Student findByEmail(String email);
	
	int registerCourseByCourseCode(String email, String code);
	
	List<Course> findRegisteredCourses(String email);

}

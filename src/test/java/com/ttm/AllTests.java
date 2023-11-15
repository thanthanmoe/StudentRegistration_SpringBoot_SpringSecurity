package com.ttm;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ TestCourseService.class, TestStudentCourseService.class, TestStudentPhotoService.class,
		TestStudentService.class, TestStudentUpdateService.class, TestUserPhotoService.class,
		TestUserUpdateService.class })
public class AllTests {

}

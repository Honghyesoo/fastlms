package com.zerobase.fastlms.admin.course.repository;

import com.zerobase.fastlms.admin.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

}

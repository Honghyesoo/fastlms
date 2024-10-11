package com.zerobase.fastlms.admin.course.service;

import com.zerobase.fastlms.admin.course.dto.CourseDto;
import com.zerobase.fastlms.admin.course.entity.Course;
import com.zerobase.fastlms.admin.course.model.CourseInput;
import com.zerobase.fastlms.admin.course.repository.CourseRepository;
import com.zerobase.fastlms.admin.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService{
    private final CourseRepository courseRepository;

    //강좌 등록
    @Override
    public boolean add(CourseInput parameter) {

        Course course = Course.builder()
                .categoryId(parameter.getCategoryId())
                .subject(parameter.getSubject())
                .regDt(LocalDateTime.now())
                .build();

        courseRepository.save(course);
        return true;
    }

    //강좌 목록
    @Override
    public Page<CourseDto> list(Pageable pageable) {
        return courseRepository.findAll(pageable).map(this::convertToDto);
    }

    //강좌 상세 정보
    @Override
    public CourseDto getById(long id) {
         return (CourseDto) courseRepository.findById(id).map(CourseDto::of).orElse(null);
    }

    @Override
    public boolean set(CourseInput parameter) {

        Optional<Course> optionalCourse= courseRepository.findById(parameter.getId());
       if (optionalCourse.isEmpty()){
           return false;
       }
       Course course = optionalCourse.get();
       course.setCategoryId(parameter.getCategoryId());
       course.setSubject(parameter.getSubject());
       course.setUdtDt(LocalDateTime.now());
       courseRepository.save(course);
        return true;
    }

    private CourseDto convertToDto(Course course) {
        return CourseDto.builder()
                .id(course.getId())
                .subject(course.getSubject())
                .regDt(LocalDateTime.now())
                .build();
    }
}

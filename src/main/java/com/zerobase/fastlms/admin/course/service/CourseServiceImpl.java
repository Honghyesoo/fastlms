package com.zerobase.fastlms.admin.course.service;

import com.zerobase.fastlms.admin.course.dto.CourseDto;
import com.zerobase.fastlms.admin.course.entity.Course;
import com.zerobase.fastlms.admin.course.model.CourseInput;
import com.zerobase.fastlms.admin.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService{
    private final CourseRepository courseRepository;
    private LocalDate getLocalDate(String value){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try{
          return LocalDate.parse(value,formatter);
        }catch (Exception e){

        }
        return null;
    }

    //강좌 등록
    @Override
    public boolean add(CourseInput parameter) {
        //2025-01-01
        LocalDate saleEndDt = getLocalDate(parameter.getSaleEndDtText());

        Course course = Course.builder()
                .categoryId(parameter.getCategoryId())
                .subject(parameter.getSubject())
                .keyword(parameter.getKeyword())
                .summary(parameter.getSummary())
                .contents(parameter.getContents())
                .price(parameter.getSalePrice())
                .salePrice(parameter.getSalePrice())
                .saleEndDt(saleEndDt)
                //종료일문자열
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
        LocalDate saleEndDt = getLocalDate(parameter.getSaleEndDtText());

        Optional<Course> optionalCourse= courseRepository.findById(parameter.getId());
       if (optionalCourse.isEmpty()){
           return false;
       }
       Course course = optionalCourse.get();
       course.setCategoryId(parameter.getCategoryId());
       course.setSubject(parameter.getSubject());
       course.setKeyword(parameter.getKeyword());
       course.setSummary(parameter.getSummary());
       course.setContents(parameter.getContents());
       course.setPrice(parameter.getPrice());
       course.setSalePrice(parameter.getSalePrice());
       course.setSaleEndDt(saleEndDt) ;
       //종료 문자열
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

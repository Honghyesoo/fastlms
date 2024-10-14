package com.zerobase.fastlms.admin.course.service;

import com.zerobase.fastlms.admin.course.dto.CourseDto;
import com.zerobase.fastlms.admin.course.entity.Course;
import com.zerobase.fastlms.admin.course.model.CourseInput;
import com.zerobase.fastlms.admin.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    //강좌내용삭제
    @Override
    public boolean del(String idList) {

        if (idList != null && idList.length() > 0){
            String[] ids = idList.split(",");
            for (String x : ids){
                long id = 0L;
                try{
                    id =Long.parseLong(x);
                }catch (Exception e){

                }
                if (id > 0 ){
                    courseRepository.deleteById(id);
                }
            }
        }
        return true ;
    }

    @Override
    public List<CourseDto> frontList() {
        List<Course> courseList = courseRepository.findAll();
        return courseList.stream().map(CourseDto::of).collect(Collectors.toList());
    }

    @Override
    public List<CourseDto> getCoursesByCategoryId(Long categoryId) {
        List<Course> courseList = courseRepository.findByCategoryId(categoryId);
        return courseList.stream().map(CourseDto::of).collect(Collectors.toList());
    }

    //프론트 강좌 상세 정보
    @Override
    public CourseDto frontDetail(Long id) {
        return courseRepository.findById(id)
                .map(CourseDto::of)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
    }


    //프론트 강좌 목록

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

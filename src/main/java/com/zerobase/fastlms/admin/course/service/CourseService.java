package com.zerobase.fastlms.admin.course.service;

import com.zerobase.fastlms.admin.course.dto.CourseDto;
import com.zerobase.fastlms.admin.course.model.CourseInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CourseService {

    // 강좌 등록
    boolean add(CourseInput parameter);

    //강좌 정보 수정
    boolean set(CourseInput parameter);


    //강좌 목록
    Page<CourseDto> list(
            Pageable pageable
    );

    //강좌 상세 정보
    CourseDto getById(long id);

    //강좌내용삭제
    boolean del(String idList);

    //프론트 강좌 목록
    List<CourseDto> frontList();

    List<CourseDto> getCoursesByCategoryId(Long categoryId);

    //프론트 강좌 상세 정보
    CourseDto frontDetail(Long id);

    //수강신청
    boolean req(Long courseId, String username);
}


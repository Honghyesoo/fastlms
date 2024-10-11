package com.zerobase.fastlms.admin.course.service;

import com.zerobase.fastlms.admin.course.dto.CourseDto;
import com.zerobase.fastlms.admin.course.model.CourseInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

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
}


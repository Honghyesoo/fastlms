package com.zerobase.fastlms.admin.course.controller;

import com.zerobase.fastlms.admin.course.dto.CourseDto;
import com.zerobase.fastlms.admin.course.model.CourseInput;
import com.zerobase.fastlms.admin.course.service.CourseService;
import com.zerobase.fastlms.admin.service.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class CourseController {

    private final CourseService courseService;
    @GetMapping("/course")
    public String course(Model model
//                       @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ){
        List<CourseDto> list = courseService.frontList();
        model.addAttribute("list",list);

//        Page<CourseDto> courseList = courseService.list(pageable);
//        model.addAttribute("courseList", courseList);
//
//        // 총 데이터 수량을 모델에 추가
//        model.addAttribute("totalElements", courseList.getTotalElements());
        return "course/index";
    }

}
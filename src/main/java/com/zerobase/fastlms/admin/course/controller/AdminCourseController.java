package com.zerobase.fastlms.admin.course.controller;

import com.zerobase.fastlms.admin.course.dto.CourseDto;
import com.zerobase.fastlms.admin.course.model.CourseInput;
import com.zerobase.fastlms.admin.course.service.CourseService;
import com.zerobase.fastlms.admin.dto.MemberDto;
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
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class AdminCourseController {

    private final CourseService courseService;
    private final CategoryService categoryService;

    //  강좌 목록
    @GetMapping("/admin/course/list.do")
    public String list(Model model,
                       @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ){
        Page<CourseDto> courseList = courseService.list(pageable);
        model.addAttribute("courseList", courseList);

        return "admin/course/list";
    }

    @GetMapping(value = {"/admin/course/add.do", "/admin/course/edit.do"})
    public String add(Model model, HttpServletRequest request,
                      CourseInput parameter){
        //카테고리 정보를 내려줘야함
            model.addAttribute("category",categoryService.list());


        boolean editMode = request.getRequestURI().contains("/edit.do");
        CourseDto detail = new CourseDto();

        if (editMode){
            long id = parameter.getId();
            CourseDto existCourse = courseService.getById(id);
            if (existCourse == null){
                //error처리
                model.addAttribute("message","강좌정보가 존재하지 않습니다.");
                return "common/error";
            }
            detail = existCourse;

        }
        model.addAttribute("editMode",editMode);
        model.addAttribute("detail",detail );
        return "admin/course/add";
    }

    @PostMapping(value = {"/admin/course/add.do", "/admin/course/edit.do"})
    public String addSubmit(Model model,HttpServletRequest request,
                            CourseInput parameter){
        boolean editMode = request.getRequestURI().contains("/edit.do");

        if (editMode){
            long id = parameter.getId();
            CourseDto existCourse = courseService.getById(id);
            if (existCourse == null){
                //error처리
                model.addAttribute("message","강좌정보가 존재하지 않습니다.");
                return "common/error";
            }
            boolean result = courseService.set(parameter);
        }else{
            boolean result = courseService.add(parameter);
        }
        return "redirect:/admin/course/list.do";
    }

}
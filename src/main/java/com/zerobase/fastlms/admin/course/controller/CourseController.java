package com.zerobase.fastlms.admin.course.controller;

import com.zerobase.fastlms.admin.course.dto.CourseDto;
import com.zerobase.fastlms.admin.course.model.CourseInput;
import com.zerobase.fastlms.admin.course.service.CourseService;
import com.zerobase.fastlms.admin.dto.CategoryDto;
import com.zerobase.fastlms.admin.entity.Category;
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

import java.util.List;

@RequiredArgsConstructor
@Controller
public class CourseController {
    private  final CategoryService categoryService;
    private final CourseService courseService;

    @GetMapping("/course")
    public String course(Model model,
                         @RequestParam(name = "categoryId" ,required = false) Long categoryId,
                         @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        List<CourseDto> list;
        if (categoryId != null) {
            list = courseService.getCoursesByCategoryId(categoryId);
        } else {
            list = courseService.frontList();
        }
        model.addAttribute("list", list);

        Page<Category> categoryList = categoryService.frontList(pageable);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("selectedCategoryId", categoryId);

        return "course/index";
    }

}
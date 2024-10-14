package com.zerobase.fastlms.admin.course.controller;

import com.zerobase.fastlms.admin.course.dto.CourseDto;
import com.zerobase.fastlms.admin.course.service.CourseService;
import com.zerobase.fastlms.admin.entity.Category;
import com.zerobase.fastlms.admin.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class ApiCourseController {
    private  final CategoryService categoryService;
    private final CourseService courseService;

    @PostMapping("/api/course/req.api")
    public ResponseEntity<?> courseReq(@RequestBody Map<String, Object> requestBody) {
        Long courseId = Long.parseLong(requestBody.get("courseId").toString());
        String username = (String) requestBody.get("username");

        boolean result = courseService.req(courseId, username);
        if (!result) {
            return ResponseEntity.badRequest().body("수강신청 실패하였습니다.");
        }
        return ResponseEntity.ok().body(courseId);
    }
}
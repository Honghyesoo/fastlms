package com.zerobase.fastlms.admin.controller;

import com.zerobase.fastlms.admin.dto.CategoryDto;
import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.entity.CategoryInput;
import com.zerobase.fastlms.admin.entity.MemberInput;
import com.zerobase.fastlms.admin.entity.MemberParam;
import com.zerobase.fastlms.admin.service.CategoryService;
import com.zerobase.fastlms.member.service.MemberService;
import jdk.jfr.Category;
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
public class AdminCategoryController {
    private final CategoryService categoryService;

    //카테고리관리
    @GetMapping("/admin/category/list.do")
    public String list(Model model, MemberParam parameter) {
        List<CategoryDto> list = categoryService.list();
        model.addAttribute("list",list);

        return "admin/category/list";
    }

    @PostMapping("/admin/category/add.do")
    public String add(Model model, CategoryInput parameter){

        boolean result = categoryService.add(parameter.getCategoryName());
        return "redirect:/admin/category/list.do";
    }

}
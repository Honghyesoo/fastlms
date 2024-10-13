    package com.zerobase.fastlms.admin.service;

    import com.zerobase.fastlms.admin.dto.CategoryDto;
    import com.zerobase.fastlms.admin.entity.Category;
    import com.zerobase.fastlms.admin.model.CategoryInput;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.stereotype.Service;

    import java.util.List;


    public interface CategoryService {

        List<CategoryDto> list();

        // 카테고리 신규추가
        boolean add(String categoryName);

        //카테고리 수정
        boolean update(CategoryInput parameter);

        //카텍고리 삭제
        boolean del(long id);

        Page<Category> frontList(Pageable pageable);
    }

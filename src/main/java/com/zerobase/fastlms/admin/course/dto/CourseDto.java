package com.zerobase.fastlms.admin.course.dto;

import com.zerobase.fastlms.admin.course.entity.Course;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CourseDto {
    private Long id;

    private String imagePath;
    private String keyWord;
    private String subject;
    private String summary;
    private String contents;
    private long price;
    private long salePrice;
    private LocalDateTime saleEndDt;
    private LocalDateTime regDt; //등록일 (추가날짜)
    private LocalDateTime udtDt; //수정일 (추가날짜)

    private long totalCount;

    public static Object of(Course course) {
        return  CourseDto.builder()
                .id(course.getId())
                .imagePath(course.getImagePath())
                .keyWord(course.getKeyWord())
                .subject(course.getSubject())
                .summary(course.getSummary())
                .contents(course.getContents())
                .price(course.getPrice())
                .salePrice(course.getSalePrice())
                .saleEndDt(course.getSaleEndDt())
                .regDt(course.getRegDt())
                .udtDt(course.getUdtDt())
                .build();
    }
}

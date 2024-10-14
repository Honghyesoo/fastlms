package com.zerobase.fastlms.admin.course.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.C;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Course { //강좌
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    long categoryId;

    String imagePath;
    String keyword;
    String subject;

    @Column(length = 1000)
    String summary;

    @Lob
    String contents;
    long price;
    long salePrice;
    LocalDate saleEndDt; //할인 종료일

    LocalDateTime regDt; //등록일 (추가날짜)
    LocalDateTime udtDt; //수정일 (추가날짜)
}

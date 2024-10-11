package com.zerobase.fastlms.admin.course.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.checkerframework.checker.units.qual.C;

import java.time.LocalDateTime;

@Data
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String imagePath;
    String keyWord;
    String subject;

    @Column(length = 1000)
    String summary;

    @Lob
    String contents;
    long price;
    long salePrice;
    LocalDateTime saleEndDt;
}

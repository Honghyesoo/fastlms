package com.zerobase.fastlms.admin.course.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class TakeCourse implements TakeCourseCode { //수강신청
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    long courseId;
    String userId;

    long payPrice; //결제금액
    String status; //상태(수강신청, 결재완료, 수강취소)

    LocalDateTime regDt; //신청일

}

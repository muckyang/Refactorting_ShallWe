package com.web.blog.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@Getter
public class UserInfo {
    private int point;
    private int grade;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;



    public void setPoint(int point) {
        this.point = point;
        this.grade = (point / 1000) + 1;
    }

    public enum UserStatus {
        ACTIVE, BAN
    }
}

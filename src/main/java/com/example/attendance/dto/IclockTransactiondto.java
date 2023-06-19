package com.example.attendance.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IclockTransactiondto {
    private int id;


    private Date punch_time;


    private String empCode;


    private String areaAlias;


    private String terminalAlias;
}

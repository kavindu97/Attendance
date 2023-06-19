package com.example.attendance.controller;

import com.example.attendance.dto.ResponseDto;
import com.example.attendance.repository.IclockTransactionRepository;
import com.example.attendance.service.IclockTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@CrossOrigin
@RequestMapping("/transaction")
public class IclockTransactionController {
    @Autowired
    private IclockTransaction iclockTransactionservice;
    @GetMapping("/all/{empCode}/{punchTime}/{id}")
    public ResponseDto get(@PathVariable String empCode,@PathVariable String punchTime,@PathVariable int id){
        return iclockTransactionservice.all(empCode,punchTime,id);
    }
    @GetMapping("/all/{punchTime}/{empCode}")
    public ResponseDto gett(@PathVariable String punchTime,@PathVariable String empCode){
        return  iclockTransactionservice.getDate(punchTime,empCode);
    }
}

package com.dxy.service;

import com.dxy.dto.ApplicantDto;
import com.dxy.mapper.ApplicantMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/applicant")
public class ApplicantService {

    @Autowired
    private ApplicantMapper applicantMapper;

    @ResponseBody
    @PostMapping(value = "/add")
    public int addApplicant(@RequestBody ApplicantDto applicantDto){
        return applicantMapper.insert(applicantDto);
    }

    @ResponseBody
    @GetMapping(value = "/all")
    public List<ApplicantDto> findAllUser(){
        return applicantMapper.selectAllApplicant();
    }


}

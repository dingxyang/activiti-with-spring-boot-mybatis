package com.dxy.mapper;

import com.dxy.dto.ApplicantDto;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ApplicantMapper {

    int insert(@Param("applicantDto") ApplicantDto applicantDto);

    List<ApplicantDto> selectAllApplicant();
}

package com.dxy.mapper;

import com.dxy.dto.UserDto;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    int insert(@Param("record") UserDto record);

    int updateByPrimaryKey(UserDto record);

    UserDto selectByPrimaryKey(@Param("id") Long id);

    int deleteByPrimaryKey(@Param("id")  Long id);

    List<UserDto> selectAllUser();
}

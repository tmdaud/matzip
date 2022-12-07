package com.smchoi.matzip.mappers;

import com.smchoi.matzip.entities.member.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IMemberMapper {
    int insertUser(UserEntity user);

    UserEntity selectUserById(@Param(value = "id") String id);
}

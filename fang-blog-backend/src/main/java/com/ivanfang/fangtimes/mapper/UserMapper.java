package com.ivanfang.fangtimes.mapper;

import com.ivanfang.fangtimes.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from user where username = #{username}")
    User getByUsername(String username);

    void add(String username, String password);

    void update(@Param("user") User user);

    @Update("update user set user_pic = #{avatarUrl}, update_time = now() where id = #{id}")
    void updateAvatar(Integer id, String avatarUrl);

    @Update("update user set password = #{newPwdMd5}, update_time = now() where id = #{id}")
    void updatePwd(Integer id, String newPwdMd5);

    @Select("select * from user")
    List<User> getAll();
}

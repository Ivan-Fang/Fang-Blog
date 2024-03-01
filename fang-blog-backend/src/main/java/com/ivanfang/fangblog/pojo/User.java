package com.ivanfang.fangblog.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class User {
    @NotNull
    private Integer id;

    private String username;

    @JsonIgnore     // ignore "password" when springmvc translate an object into json
    private String password;

    @NotEmpty       // not empty == not null + cannot be empty string
    @Pattern(regexp = "^\\S{1,10}$")
    private String nickname;

    @NotEmpty
    @Email
    private String email;

    private String userPic;         // picture url
    private Timestamp createTime;
    private Timestamp updateTime;
}

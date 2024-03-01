package com.ivanfang.fangblog.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @NotNull(groups = Update.class)
    private Integer id;

    @NotEmpty(groups = {Add.class, Update.class})
    private String categoryName;

    @NotEmpty   // if we don't specify the group, SpringBoot will set it as "Default" group.
    private String categoryAlias;

    private Integer createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp updateTime;

    public interface Add extends Default {}

    public interface Update extends Default {}

}

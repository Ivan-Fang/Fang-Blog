package com.ivanfang.fangblog.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ivanfang.fangblog.annotation.State;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    @NotNull(groups = Update.class)
    private Integer id;

    @NotEmpty(groups = Add.class)
    @Pattern(regexp = "^[\\S ]{1,100}$")
    private String title;

    @NotEmpty(groups = Add.class)
    private String content;

    @NotEmpty(groups = Add.class)
    @URL
    private String coverImg;

    @State(groups = Add.class)
    private String state;

    @NotNull(groups = Add.class)
    private Integer categoryId;

    private Integer createUser;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp updateTime;

    public interface Add extends Default {}

    public interface Update extends Default {}
}

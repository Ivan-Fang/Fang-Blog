package com.ivanfang.fangblog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageBean<T> {

    private Long total;     // total data number
    private List<T> items;  // data of "current" page

}

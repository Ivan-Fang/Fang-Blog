package com.ivanfang.fangblog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// unified response format
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Integer code;   // status code (0: success, 1: failed)
    private String message; // prompt message
    private T data;         // return data

    // execution success and has return value
    public static <E> Result<E> success(E data) {
        return new Result<>(0, "Execution Success", data);
    }

    // execution success but don't hava return value
    public static Result success() {
        return new Result(0, "Execution Success", null);
    }

    // execution failure and set prompt message
    public static Result failed(String message) {
        return new Result(1, message, null);
    }

}

package com.example.bookkeeping.helper;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseVo<T> {

    private String status;
    private String msg;
    private T data;

    public ResponseVo(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

}

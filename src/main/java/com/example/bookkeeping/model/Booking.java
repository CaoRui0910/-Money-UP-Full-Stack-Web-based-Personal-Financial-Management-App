package com.example.bookkeeping.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.example.bookkeeping.vo.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class Booking extends BaseVo {

    private String name;

    private String date;

    private Integer price;

    private String kind;

    private Integer year;
    private Integer month;
    private Integer day;
    private Integer userId;
}

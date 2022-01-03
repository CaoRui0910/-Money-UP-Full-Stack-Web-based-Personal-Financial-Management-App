package com.example.bookkeeping.model;

import com.example.bookkeeping.vo.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Budget  extends BaseVo {
    private Integer userId;

    private Integer year;

    private Integer month;

    private Integer price;
}

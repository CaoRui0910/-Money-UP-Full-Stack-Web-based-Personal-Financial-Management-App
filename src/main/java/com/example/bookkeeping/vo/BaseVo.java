package com.example.bookkeeping.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public abstract class BaseVo implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Date createTime = new Date();
    private Date updateTime = new Date();

    private Boolean status = true;

}
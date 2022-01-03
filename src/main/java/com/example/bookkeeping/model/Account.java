package com.example.bookkeeping.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.example.bookkeeping.vo.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class Account extends BaseVo {

    private String username;

    private String mobile;

    private String img = "default.jpg";

    private String password;

    private String salt;
    private String role;

    @TableField(exist = false)
    private String lastIp;

    @TableField(exist = false)
    private Date lastTime;

    public String getCredentialsSalt() {
        return username + salt;
    }
}

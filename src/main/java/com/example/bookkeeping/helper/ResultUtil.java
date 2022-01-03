package com.example.bookkeeping.helper;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ResultUtil {

    public static ResponseVo success() {
        return vo(CoreConst.SUCCESS_CODE, null);
    }

    public static ResponseVo success(String msg) {
        return vo(CoreConst.SUCCESS_CODE, msg);
    }

    public static ResponseVo error() {
        return vo(CoreConst.FAIL_CODE, null);
    }

    public static ResponseVo error(String msg) {
        return vo(CoreConst.FAIL_CODE, msg);
    }


    public static ResponseVo vo(String status, String message) {
        return new ResponseVo<String>(status, message);
    }


}

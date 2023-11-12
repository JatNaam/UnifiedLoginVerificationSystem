package com.yat.unifiedloginverificationsystem.commen;

import lombok.Data;

@Data
public class R<T> {
    /**
     * 状态码
     */
    private Integer code;

    /**
     * 提示信息，如果有错误时，前端可以获取该字段进行提示
     */
    private String msg;

    /**
     * 查询到的结果数据
     */
    private T data;

    public R(Code resultCode, T data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
    }
    public R(Code resultCode, String msg, T data) {
        this.code = resultCode.getCode();
        this.msg =msg;
        this.data = data;
    }
}

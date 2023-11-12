package com.yat.unifiedloginverificationsystem.commen;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Code {
    SUCCESS(200,"请求成功"),
    FAILED(400,"请求参数错误"),
    ERROR(500,"请求失败");

    private int code;
    private String msg;
}

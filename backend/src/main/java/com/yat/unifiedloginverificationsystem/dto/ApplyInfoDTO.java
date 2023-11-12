package com.yat.unifiedloginverificationsystem.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 86183
 * @createTime 2023/10/2 15:31
 * @description
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplyInfoDTO {
    Integer uid;
    String full_name;
    String phone;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    Date applyTime;
}

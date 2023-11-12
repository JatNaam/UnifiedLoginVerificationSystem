package com.yat.unifiedloginverificationsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 86183
 * @createTime 2023/10/2 15:46
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApprovedUserInfoDTO {
    Integer uid;
    String full_name;
    String phone;
    Integer isBindTOTP;
}

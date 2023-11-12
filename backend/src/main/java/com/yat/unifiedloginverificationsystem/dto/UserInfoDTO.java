package com.yat.unifiedloginverificationsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoDTO {
    String first_name;
    String full_name;
    String password;
    String phone;
    String token;
}

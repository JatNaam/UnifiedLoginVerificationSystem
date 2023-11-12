package com.yat.unifiedloginverificationsystem.entry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;
import org.springframework.ldap.odm.annotations.Transient;
import org.springframework.ldap.support.LdapNameBuilder;

import javax.naming.Name;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "tb_user")
@Entry(objectClasses = {"organizationalPerson", "person", "top"}, base = "ou=user")
public class User implements Serializable {

    @Id
    @JsonIgnore
    @TableField(exist = false)
    private Name dn;

    @Attribute(name = "userPassword")
    @TableField(exist = false)
    @JsonProperty("password")
    private String userPassword;

    @TableId(type = IdType.AUTO)
    @Transient
    private Integer uid;

    @Attribute(name = "ou")
    @TableField(exist = false)
    @JsonProperty("type")
    private String ou;

    @Attribute(name = "cn")
    @TableField(value = "cn")
    @JsonProperty("full_name")
    private String cn;

    @TableField(value = "sn")
    @Attribute(name = "sn")
    @JsonProperty("first_name")
    private String sn;

    @Attribute(name = "telephoneNumber")
    @TableField(value = "phone")
    @JsonProperty("phone")
    private String telephoneNumber;

    @TableField(value = "secret_key")
    @Transient
    private String secretKey;

    @TableField(value = "is_approved")
    @JsonIgnore
    @Transient
    private int isApproved; // 是否已被管理员审核通过注册申请

    @TableField(value = "apply_time")
    @Transient
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date applyTime;

    @TableField(value = "is_bindTOTP")
    @Transient
    private Integer isBindTOTP; // 是否开启了无密码登录

    public void setCn(String cn) {
        this.cn = cn;
        if (this.dn == null) {
            this.dn = LdapNameBuilder.newInstance()
                    .add("ou", "user")
                    .add("cn", cn)
                    .build();
        }
    }

}


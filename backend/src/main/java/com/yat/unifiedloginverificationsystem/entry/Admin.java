package com.yat.unifiedloginverificationsystem.entry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;
import java.io.Serializable;

@Data
@Entry(objectClasses = {"organizationalPerson", "person", "top"}, base = "ou=admin")
public class Admin implements Serializable {

    @Id
    @JsonIgnore
    private Name dn;

    @Attribute(name = "ou")
    @JsonProperty("type")
    private String ou;

    @Attribute(name = "cn")
    @JsonProperty("full_name")
    private String cn;

    @Attribute(name = "sn")
    @JsonProperty("first_name")
    private String sn;

    @Attribute(name = "userPassword")
    @JsonProperty("password")
    private String userPassword;

    @Attribute(name = "telephoneNumber")
    @JsonProperty("phone")
    private String telephoneNumber;

}

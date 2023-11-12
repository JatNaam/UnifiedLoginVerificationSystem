package com.yat.unifiedloginverificationsystem.service.impl;

import com.yat.unifiedloginverificationsystem.entry.Admin;
import com.yat.unifiedloginverificationsystem.entry.User;
import com.yat.unifiedloginverificationsystem.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LdapTemplate ldapTemplate;

    @Override
    public Object loginByPassword(String ou, String telephoneNumber) {
        try {
            if (ou.equals("1"))
                return ldapTemplate.findOne(query().where("ou").is(ou)
                        .and("telephoneNumber").is(telephoneNumber), Admin.class);
            else return ldapTemplate.findOne(query().where("ou").is(ou)
                    .and("telephoneNumber").is(telephoneNumber), User.class);
        } catch (EmptyResultDataAccessException e) {
            log.error("Found 0 result, error: {}", e.getMessage());
        }
        return null;
    }

    @Override
    public User loginByCode(String telephoneNumber) {
        return ldapTemplate.findOne(query()
                .where("telephoneNumber")
                .is(telephoneNumber), User.class);
    }
}

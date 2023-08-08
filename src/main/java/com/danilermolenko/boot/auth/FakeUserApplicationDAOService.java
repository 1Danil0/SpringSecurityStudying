package com.danilermolenko.boot.auth;

import com.danilermolenko.boot.models.Roles;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
@Repository("fake")
public class FakeUserApplicationDAOService implements UserApplicationDAO{

    private final PasswordEncoder passwordEncoder;
    @Autowired
    public FakeUserApplicationDAOService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<UserApplication> selectUserApplicationByUsername(String username) {
        return getUserApplications().stream().filter(el -> el.getUsername().equals(username)).findFirst();
    }

    private List<UserApplication> getUserApplications(){
        List<UserApplication> allUsers = Lists.newArrayList(
                new UserApplication(Roles.USER.getGrantedAuthorities(),
                        "danil",
                        passwordEncoder.encode( "danil"),
                        true,
                        true,
                        true,
                        true)
                , new UserApplication(Roles.ADMIN.getGrantedAuthorities(),
                        "yasya",
                        passwordEncoder.encode( "yasya"),
                        true,
                        true,
                        true,
                        true)
                , new UserApplication(Roles.ADMIN_TRAINEE.getGrantedAuthorities(),
                        "tanya",
                        passwordEncoder.encode( "tanya"),
                        true,
                        true,
                        true,
                        true)
        );
        return allUsers;
    }
}

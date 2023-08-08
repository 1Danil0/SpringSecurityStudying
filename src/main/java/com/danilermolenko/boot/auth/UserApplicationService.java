package com.danilermolenko.boot.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserApplicationService implements UserDetailsService {
    private final UserApplicationDAO userApplicationDAO;
    @Autowired
    public UserApplicationService(@Qualifier("fake") UserApplicationDAO userApplicationDAO) {
        this.userApplicationDAO = userApplicationDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userApplicationDAO.selectUserApplicationByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found"));
    }
}

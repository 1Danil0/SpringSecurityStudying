package com.danilermolenko.boot.auth;

import java.util.Optional;

public interface UserApplicationDAO {
    public Optional<UserApplication> selectUserApplicationByUsername(String username);
}

package com.dinchan.service;

import com.dinchan.model.Address;
import com.dinchan.model.User;

public interface UserService {
    User findUserByJwtToken(String jwt) throws Exception;
    User findUserByEmail(String email) throws Exception;
    User updateUserProfile(User user, User info) throws Exception;
}

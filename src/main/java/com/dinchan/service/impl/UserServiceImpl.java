package com.dinchan.service.impl;

import com.dinchan.config.JwtProvider;
import com.dinchan.model.Address;
import com.dinchan.model.User;
import com.dinchan.repository.AddressRepository;
import com.dinchan.repository.UserRepository;
import com.dinchan.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final JwtProvider jwtProvider;

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        return this.findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("user not found with email - "+ email);
        }
        return user;
    }

    @Override
    public User updateUserProfile(User user, User info) {
        if(info.getFullName() != null){
            user.setFullName(info.getFullName());
        }
        if(info.getPhoneNumber() != null){
            user.setPhoneNumber(info.getPhoneNumber());
        }
        if(info.getPassword() != null){
            user.setPassword(info.getPassword());
        }
        return userRepository.save(user);
    }
}

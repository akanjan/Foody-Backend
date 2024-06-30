package com.foody.user.security;

import com.foody.user.config.CustomUserDetails;
import com.foody.user.entitys.User;
import com.foody.user.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Loading User from DB by username
        User user = this.userRepo.findByEmail(username).orElseThrow(() -> new RuntimeException("User Not Found !!"));
        //return (UserDetails) user;
        return new CustomUserDetails(user);
    }
}

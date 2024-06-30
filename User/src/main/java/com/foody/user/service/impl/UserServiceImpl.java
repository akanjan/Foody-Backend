package com.foody.user.service.impl;

import com.foody.user.config.AppConstants;
import com.foody.user.entitys.MasSerialNo;
import com.foody.user.entitys.Role;
import com.foody.user.entitys.User;
import com.foody.user.exception.ApiException;
import com.foody.user.repository.MasSrlRepo;
import com.foody.user.repository.RoleRepo;
import com.foody.user.repository.UserRepo;
import com.foody.user.service.UserService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private MasSrlRepo masSrlRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepo roleRepo;

    @Override
    @PermitAll
    @Transactional
    public User addUser(User user) {
        MasSerialNo masSerialNo = this.masSrlRepo.findBySrlTypeAndStatus("CUST", "A");

        //Create Serial No
        //String slno = masSerialNo.getSrlNo().add(new BigDecimal(1)).toString();
        String slno = Integer.toString(masSerialNo.getSrlNo() + 1);
        String strSlNo = String.valueOf(slno);
        if (strSlNo.length() == 1) {
            strSlNo = "000" + strSlNo;
        } else if (strSlNo.length() == 2) {
            strSlNo = "00" + strSlNo;
        } else if (strSlNo.length() == 3) {
            strSlNo = "0" + strSlNo;
        }
        String finalSrlNo = "CUST"+masSerialNo.getEntryDate().getYear()+strSlNo;

        //Set Serial No
        user.setUserId(finalSrlNo);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepo.findById(AppConstants.NORMAL_USER).get();
        //user.setRoles(new HashSet<>());
        //user.getRoles().add(role);
        user.setRoles(Stream.of(role).collect(Collectors.toSet()));

        //Save User
        User savedUser = this.userRepo.save(user);

        //Update Serial no
        this.masSrlRepo.updateSrlNo("CUST", "A", Integer.parseInt(slno));
        return savedUser;
    }

    @Override
    public Optional<User> getUser(String userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ApiException("User Id Not Found With Id : " + userId));
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> getAllUser() {
        List<User> users = this.userRepo.findAll().stream().collect(Collectors.toList());
        return users;
    }

    @Override
    public Optional<User> updateUser(User user, String userId) {
        User userDtl = this.userRepo.findById(userId)
                .orElseThrow(() -> new ApiException("User Id Not Found With Id : " + userId));
        userDtl.setName(user.getName());
        userDtl.setEmail(user.getEmail());
        userDtl.setPassword(user.getPassword());
        userDtl.setPhone(user.getPhone());
        userDtl.setAddress(user.getAddress());

        return Optional.ofNullable(this.userRepo.save(userDtl));
    }

    @Override
    public void deletUser(String userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ApiException("User Id Not Found With Id : " + userId));
        this.userRepo.delete(user);
    }
}

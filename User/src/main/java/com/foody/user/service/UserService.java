package com.foody.user.service;

import com.foody.user.entitys.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    //Add User
    User addUser(User user);

    //Get User
    Optional<User> getUser(String userId);

    //Get All Users
    List<User> getAllUser();

    //Update User
    Optional<User> updateUser(User user, String userId);

    //Delete User
    void deletUser(String userId);
}

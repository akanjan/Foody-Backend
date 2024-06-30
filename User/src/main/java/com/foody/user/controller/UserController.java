package com.foody.user.controller;

import com.foody.user.entitys.User;
import com.foody.user.exception.ApiResponse;
import com.foody.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/nic/foody/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<User> saveUser(@RequestBody User user){
        User savedUser = this.userService.addUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable String userId){
        Optional<User> user = this.userService.getUser(userId);
        return ResponseEntity.ok(user.get());
    }

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(this.userService.getAllUser());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable String userId){
        return ResponseEntity.ok(this.userService.updateUser(user,userId).get());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId){
        this.userService.deletUser(userId);
        return new ResponseEntity<>(new ApiResponse(new Date(),"Deleted !!","User Deleted Successfully"),
                HttpStatus.OK);
    }

}

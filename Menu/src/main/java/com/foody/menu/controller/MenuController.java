package com.foody.menu.controller;

import com.foody.menu.entitys.MenuItem;
import com.foody.menu.exception.ApiResponse;
import com.foody.menu.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/nic/foody/menu")
@CrossOrigin("*")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @PostMapping()
    public ResponseEntity<MenuItem> saveMenu(@RequestBody MenuItem menuItem){
        MenuItem savedMenu = this.menuService.saveMenu(menuItem);
        return new ResponseEntity<>(savedMenu, HttpStatus.CREATED);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<MenuItem> getMenu(@PathVariable String itemId){
        Optional<MenuItem> menu = this.menuService.getMenu(itemId);
        return ResponseEntity.ok(menu.get());
    }

    @GetMapping()
    public ResponseEntity<List<MenuItem>> getAllMenus(){
        return ResponseEntity.ok(this.menuService.getAllItem());
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<MenuItem> updateUser(@RequestBody MenuItem menuItem, @PathVariable String itemId){
        return ResponseEntity.ok(this.menuService.updateMenuItem(menuItem,itemId).get());
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String itemId){
        this.menuService.deleteMenu(itemId);
        return new ResponseEntity<>(new ApiResponse(new Date(),"Deleted !!","Menu Item Deleted Successfully"),
                HttpStatus.OK);
    }

}

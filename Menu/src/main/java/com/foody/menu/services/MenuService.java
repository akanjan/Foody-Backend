package com.foody.menu.services;

import com.foody.menu.entitys.MenuItem;

import java.util.List;
import java.util.Optional;

public interface MenuService {

    //Save
    MenuItem saveMenu(MenuItem item);

    //Gat
    Optional<MenuItem> getMenu(String itemId);

    //Gat All
    List<MenuItem> getAllItem();

    //Update
    Optional<MenuItem> updateMenuItem(MenuItem item, String itemId);

    //Delete
    void deleteMenu(String itemId);

}

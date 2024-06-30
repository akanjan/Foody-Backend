package com.foody.menu.services.impl;

import com.foody.menu.entitys.MasSerialNo;
import com.foody.menu.entitys.MenuItem;
import com.foody.menu.exception.ApiException;
import com.foody.menu.repository.MasSrlRepo;
import com.foody.menu.repository.MenuRepo;
import com.foody.menu.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MasSrlRepo masSrlRepo;

    @Autowired
    private MenuRepo menuRepo;

    @Override
    public MenuItem saveMenu(MenuItem item) {
        MasSerialNo masSerialNo = this.masSrlRepo.findBySrlTypeAndStatus("MENU", "A");

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
        String finalSrlNo = "MENU"+masSerialNo.getEntryDate().getYear()+strSlNo;

        //Set Serial No
        item.setItemId(finalSrlNo);

        //Save User
        MenuItem savedItem = this.menuRepo.save(item);

        //Update Serial no
        this.masSrlRepo.updateSrlNo("MENU", "A", Integer.parseInt(slno));

        return savedItem;
    }

    @Override
    public Optional<MenuItem> getMenu(String itemId) {
        MenuItem menu = this.menuRepo.findById(itemId)
                .orElseThrow(() -> new ApiException("Menu Not Found With Id : " + itemId));
        return Optional.ofNullable(menu);
    }

    @Override
    public List<MenuItem> getAllItem() {
        List<MenuItem> items = this.menuRepo.findAll().stream().collect(Collectors.toList());
        return items;
    }

    @Override
    public Optional<MenuItem> updateMenuItem(MenuItem item, String itemId) {
        MenuItem menuDtl = this.menuRepo.findById(itemId)
                .orElseThrow(() -> new ApiException("Menu Not Found With Id : " + itemId));

        menuDtl.setItemName(item.getItemName());
        menuDtl.setDescription(item.getDescription());
        menuDtl.setPrice(item.getPrice());
        menuDtl.setRestaurantId(item.getRestaurantId());

        return Optional.ofNullable(this.menuRepo.save(menuDtl));
    }

    @Override
    public void deleteMenu(String itemId) {
        MenuItem item = this.menuRepo.findById(itemId)
                .orElseThrow(() -> new ApiException("Menu Not Found With Id : " + itemId));
        this.menuRepo.delete(item);
    }

}

package com.foody.user.config;

import com.foody.user.entitys.MasSerialNo;
import com.foody.user.entitys.Role;
import com.foody.user.repository.MasSrlRepo;
import com.foody.user.repository.RoleRepo;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class Initializer {
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private MasSrlRepo masSrlRepo;

    @PostConstruct
    @PermitAll
    public void initRoles(){
        List<Role> existingRoles = roleRepo.findAll();
        if(existingRoles.isEmpty()){
            Role admin = Role.builder().roleId(AppConstants.ADMIN_USER).name("ADMIN_USER").build();
            Role seller = Role.builder().roleId(AppConstants.SELLER_USER).name("SELLER_USER").build();
            Role normal = Role.builder().roleId(AppConstants.NORMAL_USER).name("NORMAL_USER").build();

            List<Role> roles = List.of(admin, seller, normal);
            List<Role> savedRolls = this.roleRepo.saveAll(roles);

            savedRolls.forEach( role -> {
                System.out.println(role.getName());
            });
        }
    }

    @PostConstruct
    @PermitAll
    public void initMasSerialNo(){
        MasSerialNo masSerialNo = masSrlRepo.findBySrlTypeAndStatus("CUST", "A");

        if(masSerialNo==null){
            MasSerialNo serialNo = MasSerialNo.builder()
                    .srlType("CUST")
                    .srlDesc("CUSTOMER SERIAL NUMBER")
                    .srlNo(0)
                    .status("A")
                    .entryBY("SYSTEM")
                    .entryDate(LocalDate.now()).build();

            MasSerialNo savedSerial = this.masSrlRepo.save(serialNo);
            System.out.println("Serial Type : " +savedSerial.getSrlType() +
                                " Serial Desc : " +savedSerial.getSrlDesc()+
                                " Serial No : " +savedSerial.getSrlNo()+
                                " Status : " +savedSerial.getStatus()+
                                " Entry By : " +savedSerial.getEntryBY()+
                                " Entry Date : " +savedSerial.getEntryDate());
        }
    }
}

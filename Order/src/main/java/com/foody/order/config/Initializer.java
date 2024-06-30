package com.foody.order.config;

import com.foody.order.entitys.MasSerialNo;
import com.foody.order.repository.MasSrlRepo;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class Initializer {

    @Autowired
    private MasSrlRepo masSrlRepo;

    @PostConstruct
    @PermitAll
    public void initMasSerialNo(){
        MasSerialNo masSerialNo = masSrlRepo.findBySrlTypeAndStatus("ODRS", "A");

        if(masSerialNo==null){
            MasSerialNo serialNo = MasSerialNo.builder()
                    .srlType("ODRS")
                    .srlDesc("ORDER SERIAL NUMBER")
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

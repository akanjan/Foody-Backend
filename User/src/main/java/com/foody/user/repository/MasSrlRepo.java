package com.foody.user.repository;

import com.foody.user.entitys.MasSerialNo;
import jakarta.annotation.security.PermitAll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface MasSrlRepo extends JpaRepository<MasSerialNo, String> {

    MasSerialNo findBySrlTypeAndStatus(String srlType, String status);

    @Modifying
    @Transactional
    @Query("UPDATE MasSerialNo m SET m.srlNo = :srlNo WHERE m.srlType = :srlType AND m.status = :status")
    void updateSrlNo(@Param("srlType") String srlType, @Param("status") String status, @Param("srlNo") int srlNo);
}

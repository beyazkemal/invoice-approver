package com.kemalbeyaz.invoice.approver.repository;

import com.kemalbeyaz.invoice.approver.model.PurchasingSpecialist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author kemal.beyaz
 */
public interface PurchasingSpecialistRepository extends JpaRepository<PurchasingSpecialist, Long> {
    Optional<PurchasingSpecialist> findByEmail(String email);
}

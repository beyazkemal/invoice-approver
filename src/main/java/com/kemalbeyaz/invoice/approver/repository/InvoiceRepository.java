package com.kemalbeyaz.invoice.approver.repository;

import com.kemalbeyaz.invoice.approver.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author kemal.beyaz
 */
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findAllByApprovedAndPurchasingSpecialistEmail(boolean approved, String email);

    List<Invoice> findAllByApproved(boolean approved);

    @Query(value = "SELECT SUM(amount) FROM invoice i, purchasing_specialist ps " +
            "WHERE i.purchasing_specialist_id = ps.id and i.approved = true and ps.email = :email", nativeQuery = true)
    Double sumApprovedAmountFor(@Param("email") String email);
}

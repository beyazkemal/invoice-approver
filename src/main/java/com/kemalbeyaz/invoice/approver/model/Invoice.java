package com.kemalbeyaz.invoice.approver.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author kemal.beyaz
 */
@Entity
public class Invoice extends BaseEntity {

    public static final String AMOUNT_POSITIVE_MSG = "Tutar 0'dan büyük olmalıdır!";
    public static final String SPECIALIST_NOT_DEFINED_MSG = "Satın alım uzmanı alanı boş bırakılamaz!";

    @Valid
    @NotNull(message = SPECIALIST_NOT_DEFINED_MSG)
    @ManyToOne
    private PurchasingSpecialist purchasingSpecialist;

    @Positive(message = AMOUNT_POSITIVE_MSG)
    private double amount;
    private String productName;
    private String billNo;

    private boolean approved;

    public PurchasingSpecialist getPurchasingSpecialist() {
        return purchasingSpecialist;
    }

    public void setPurchasingSpecialist(PurchasingSpecialist purchasingSpecialist) {
        this.purchasingSpecialist = purchasingSpecialist;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}

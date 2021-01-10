package com.kemalbeyaz.invoice.approver.model;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author kemal.beyaz
 */
class InvoiceTest {

    @Test
    void testInvoiceValidation() {
        PurchasingSpecialist purchasingSpecialist = new PurchasingSpecialist();
        purchasingSpecialist.setFirstName("John");
        purchasingSpecialist.setLastName("Doe");
        purchasingSpecialist.setEmail("john@doe.com");

        Invoice invoice = new Invoice();
        invoice.setPurchasingSpecialist(purchasingSpecialist);
        invoice.setProductName("USB Disc");
        invoice.setBillNo("TR001");
        invoice.setAmount(30);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        javax.validation.Validator validator = factory.getValidator();

        Set<ConstraintViolation<Invoice>> constraintViolationSet = validator.validate(invoice);
        assertEquals(0, constraintViolationSet.size());
    }

    @Test
    void testInvoiceValidationNoSpecialist() {
        Invoice invoice = new Invoice();
        invoice.setProductName("USB Disc");
        invoice.setBillNo("TR001");
        invoice.setAmount(1);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        javax.validation.Validator validator = factory.getValidator();

        Set<ConstraintViolation<Invoice>> constraintViolationSet = validator.validate(invoice);
        String validationMessage = constraintViolationSet.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining());

        assertEquals(Invoice.SPECIALIST_NOT_DEFINED_MSG, validationMessage);
    }

    @Test
    void testInvoiceValidationZeroAmount() {
        PurchasingSpecialist purchasingSpecialist = new PurchasingSpecialist();
        purchasingSpecialist.setFirstName("John");
        purchasingSpecialist.setLastName("Doe");
        purchasingSpecialist.setEmail("john@doe.com");

        Invoice invoice = new Invoice();
        invoice.setPurchasingSpecialist(purchasingSpecialist);
        invoice.setProductName("USB Disc");
        invoice.setBillNo("TR001");
        invoice.setAmount(0);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        javax.validation.Validator validator = factory.getValidator();

        Set<ConstraintViolation<Invoice>> constraintViolationSet = validator.validate(invoice);
        String validationMessage = constraintViolationSet.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining());

        assertEquals(Invoice.AMOUNT_POSITIVE_MSG, validationMessage);
    }

}
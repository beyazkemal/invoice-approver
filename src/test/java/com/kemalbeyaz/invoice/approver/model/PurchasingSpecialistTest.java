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
class PurchasingSpecialistTest {

    @Test
    void testPurchasingSpecialistValidation() {
        PurchasingSpecialist purchasingSpecialist = new PurchasingSpecialist();
        purchasingSpecialist.setFirstName("John");
        purchasingSpecialist.setLastName("Doe");
        purchasingSpecialist.setEmail("john@doe.com");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        javax.validation.Validator validator = factory.getValidator();

        Set<ConstraintViolation<PurchasingSpecialist>> constraintViolationSet = validator.validate(purchasingSpecialist);
        assertEquals(0, constraintViolationSet.size());
    }

    @Test
    void testPurchasingSpecialistValidationNoName() {
        PurchasingSpecialist purchasingSpecialist = new PurchasingSpecialist();
        purchasingSpecialist.setFirstName("");
        purchasingSpecialist.setLastName("Doe");
        purchasingSpecialist.setEmail("john@doe.com");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        javax.validation.Validator validator = factory.getValidator();

        Set<ConstraintViolation<PurchasingSpecialist>> constraintViolationSet = validator.validate(purchasingSpecialist);
        String validationMessage = constraintViolationSet.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining());

        assertEquals(PurchasingSpecialist.FIRST_NAME_EMPTY_MSG, validationMessage);
    }

    @Test
    void testPurchasingSpecialistValidationNoLastName() {
        PurchasingSpecialist purchasingSpecialist = new PurchasingSpecialist();
        purchasingSpecialist.setFirstName("Jon");
        purchasingSpecialist.setLastName("");
        purchasingSpecialist.setEmail("john@doe.com");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        javax.validation.Validator validator = factory.getValidator();

        Set<ConstraintViolation<PurchasingSpecialist>> constraintViolationSet = validator.validate(purchasingSpecialist);
        String validationMessage = constraintViolationSet.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining());

        assertEquals(PurchasingSpecialist.LAST_NAME_EMPTY_MSG, validationMessage);
    }

    @Test
    void testPurchasingSpecialistValidationNoEmail() {
        PurchasingSpecialist purchasingSpecialist = new PurchasingSpecialist();
        purchasingSpecialist.setFirstName("Jon");
        purchasingSpecialist.setLastName("Doe");
        purchasingSpecialist.setEmail("");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        javax.validation.Validator validator = factory.getValidator();

        Set<ConstraintViolation<PurchasingSpecialist>> constraintViolationSet = validator.validate(purchasingSpecialist);
        String validationMessage = constraintViolationSet.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining());

        assertEquals(PurchasingSpecialist.EMAIL_EMPTY_MSG, validationMessage);
    }

    @Test
    void testPurchasingSpecialistValidationNotEmail() {
        PurchasingSpecialist purchasingSpecialist = new PurchasingSpecialist();
        purchasingSpecialist.setFirstName("Jon");
        purchasingSpecialist.setLastName("Doe");
        purchasingSpecialist.setEmail("john.com");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        javax.validation.Validator validator = factory.getValidator();

        Set<ConstraintViolation<PurchasingSpecialist>> constraintViolationSet = validator.validate(purchasingSpecialist);
        String validationMessage = constraintViolationSet.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining());

        assertEquals(PurchasingSpecialist.EMAIL_NOT_VALID_MSG, validationMessage);
    }

}
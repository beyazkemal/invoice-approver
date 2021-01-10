package com.kemalbeyaz.invoice.approver.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * @author kemal.beyaz
 */
@Entity
public class PurchasingSpecialist extends BaseEntity {

    private static final String MSG_SUFFIX = " alanı boş bırakılamaz!";
    public static final String FIRST_NAME_EMPTY_MSG = "Ad" + MSG_SUFFIX;
    public static final String LAST_NAME_EMPTY_MSG = "Soyad" + MSG_SUFFIX;
    public static final String EMAIL_EMPTY_MSG = "Email" + MSG_SUFFIX;
    public static final String EMAIL_NOT_VALID_MSG = "Email adresini kontrol edin!";

    @NotEmpty(message = FIRST_NAME_EMPTY_MSG)
    private String firstName;

    @NotEmpty(message = LAST_NAME_EMPTY_MSG)
    private String lastName;

    @Column(unique = true)
    @NotEmpty(message = EMAIL_EMPTY_MSG)
    @Email(message = EMAIL_NOT_VALID_MSG)
    private String email;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

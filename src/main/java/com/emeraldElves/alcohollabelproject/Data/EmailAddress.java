package com.emeraldElves.alcohollabelproject.Data;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * A class representing an email address.
 */
public class EmailAddress {
    private String emailAddress;

    /**
     * Creates an email address which is validated.
     *
     * @param emailAddress The email address.
     */
    public EmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }


    /**
     * Determines if email address is valid.
     *
     * @return True if the address is valid.
     */
    public boolean isValid() {
        Pattern email = Pattern.compile("^([\\w\\.\\-]+)@([\\w\\-]+)((\\.(\\w){2,3})+)$");
        return email.matcher(emailAddress).matches();
    }

    public String getEmailAddress(){ return emailAddress;}

    @Override
    public String toString() {
        return "EmailAddress{" +
                "emailAddress='" + emailAddress + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailAddress that = (EmailAddress) o;
        return Objects.equals(emailAddress, that.emailAddress);
    }

    @Override
    public int hashCode() {

        return Objects.hash(emailAddress);
    }
}

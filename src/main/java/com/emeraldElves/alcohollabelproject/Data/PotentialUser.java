package com.emeraldElves.alcohollabelproject.Data;


import org.jasypt.util.password.StrongPasswordEncryptor;

import java.util.Date;

/**
 * Created by Dan on 4/15/2017.
 */
public class PotentialUser {
    //TODO: compare by email, not name
    private String Name,Password, address;//username is now same as email address
    private int representativeID, permitNum;
    private EmailAddress email;
    private PhoneNumber phoneNumber;
    private UserType userType;
    private Date date;
    private StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
    public PotentialUser(String Name, int representativeID, EmailAddress email, PhoneNumber phoneNumber, UserType userType, String Password, Date date,
    int permitNum, String address) {
        this.Name = Name;
        this.representativeID = representativeID;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userType = userType;
        this.Password = passwordEncryptor.encryptPassword(Password);
        this.Password = Password;
        this.date = date;
        this.address = address;
        this.permitNum = permitNum;

    }

    public EmailAddress getEmail() {
        return email;
    }

    public int getRepresentativeID() {
        return representativeID;
    }

    public int getPermitNum(){ return permitNum; }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return Name;
    }

    public UserType getUserType() {
        return userType;
    }

    public String getPassword() {
        return Password;
    }

    public Date getDate() {
        return date;
    }

    public String getAddress(){ return address; }
}
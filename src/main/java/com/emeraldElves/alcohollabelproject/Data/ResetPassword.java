package com.emeraldElves.alcohollabelproject.Data;

import com.emeraldElves.alcohollabelproject.Authenticator;
import org.jasypt.util.password.StrongPasswordEncryptor;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by keionbis on 4/21/17.
 */
public class ResetPassword {
    private String accountEmail;
    private String randomNum;
    private int minimum = 10000000, maximum = 99999999;
    private Properties props;
    private Session session;
    private StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
    public void resetEmail(String accountEmail){
        this.accountEmail = accountEmail;
        if(Authenticator.getInstance().isvalidAccount(accountEmail)){
            randomNum = String.valueOf(minimum + (int)(Math.random() * maximum));
            try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("cs3733teame@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(accountEmail));
                message.setSubject("Use the following " + randomNum + " to log into your account");
                Transport.send(message);
                System.out.println("Done");
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            Storage.getInstance().updatePassword(accountEmail,passwordEncryptor.encryptPassword(randomNum));
        }
    }
    private void EmailManager(){
        props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("cs3733teame@gmail.com", "EmeraldElvesD17");
                    }
                });
    }
}
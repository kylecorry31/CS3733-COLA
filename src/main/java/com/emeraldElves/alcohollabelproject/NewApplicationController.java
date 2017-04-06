package com.emeraldElves.alcohollabelproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewApplicationController {
    @FXML
    TextField repIDNoTextField;
    @FXML
    TextField permitNoTextField;
    @FXML
    RadioButton international;
    @FXML
    RadioButton domestic;
    @FXML
    RadioButton beer;
    @FXML
    RadioButton wine;
    @FXML
    TextField alcoholName;
    @FXML
    TextField brandNameField;
    @FXML
    TextField addressField;
    @FXML
    TextField phoneNumberField;
    @FXML
    TextField emailAddressField;
    @FXML
    TextField signatureField;
    @FXML
    DatePicker datePicker;
    @FXML
    TextField alcoholContentField;
    @FXML
    TextField wineVintageYearField;
    @FXML
    TextField pHLevelField;
    @FXML
    Label welcomeApplicantLabel;
    @FXML
    Button saveApplication;
    @FXML
    Button cancelApplication;
    @FXML
    Button nextPageBtn;
    @FXML
    Button submitBtn;
    @FXML
    Button logoutBtn;
    @FXML
    Label permitNoErrorField;
    @FXML
    Label addressErrorField;
    @FXML
    Label phoneNumErrorField;
    @FXML
    Label emailErrorField;
    @FXML
    Label pSourceErrorField;
    @FXML
    Label pTypeErrorField;
    @FXML
    Label brandNameErrorField;
    @FXML
    Label alcContentErrorField;
    @FXML
    Label dateErrorField;
    @FXML
    Label signatureErrorField;




    //Initializes and temporarily stores applicant's info
    int repIDNo = -1; //means they didn't enter a rep ID num
    int permitNo = 0;
    String physicalAddress= null;
    EmailAddress applicantEmail = null;
    PhoneNumber applicantPhone = null;

    //Stores the manufacturer's info
    ManufacturerInfo appManInfo = null;

    //Initializes and temporarily stores data fields for alc info
    ProductSource pSource = null;
    AlcoholType alcType = null;
    String alcName = null;
    String brandName = null;
    int alcContent = 0;
    AlcoholInfo.Wine wineType = null; //null if type is beer (this doesn't get edited)

    //Stores the alcohol info from the form
    AlcoholInfo appAlcoholInfo = null;

    private Main main;
    private String username;

    private SubmittedApplication application;


    public void init(Main main, SubmittedApplication application, String username) {
        this.main = main;
        this.username = username;
        this.application = application;
    }


    public void init(Main main, String username){
        init(main, null, username);
    }

    public void nextPage(){

        Boolean formFilled=false;
        Boolean emailValid=false;
        Boolean phoneValid=false;

        //errors are printed only if required fields are not filled in
        if(permitNoTextField.getText().isEmpty()) {
            permitNoErrorField.setText("Please fill in your permit number.");
        } else{
            permitNoErrorField.setText("");
        }
        if(addressField.getText().isEmpty()) {
            addressErrorField.setText("Please fill in the physical address of your company.");
        } else{
            addressErrorField.setText("");
        }
        if(phoneNumberField.getText().isEmpty()) {
            phoneNumErrorField.setText("Please fill in the contact number.");
        } else{
            phoneNumErrorField.setText("");
        }
        if(emailAddressField.getText().isEmpty()) {
            emailErrorField.setText("Please fill in the contact email.");
        } else{
            emailErrorField.setText("");
        }

        //check if required fields are filled
        if(!emailAddressField.getText().isEmpty()&&!phoneNumberField.getText().isEmpty()&&
                !addressField.getText().isEmpty()&&!permitNoTextField.getText().isEmpty()){
            formFilled=true;
        }

        //check if email field is valid
        if(!emailAddressField.getText().isEmpty()){
            applicantEmail = new EmailAddress(emailAddressField.getText()); //get text from the email address field
            if(applicantEmail.isValid()) {
                emailValid = true;
            }
            else emailErrorField.setText("Please fill in a valid email address.");
        }

        //check if phone number field is valid
        if(!phoneNumberField.getText().isEmpty()){
            applicantPhone = new PhoneNumber(phoneNumberField.getText()); //get text from phone num field
            if(applicantPhone.isValid()) {
                phoneValid=true;
            }
            else phoneNumErrorField.setText("Please fill in a valid phone number.");
        }

        if(formFilled && emailValid && phoneValid) { //Store the applicant information now that the form is filled

            //NEED TO CHECK IF THIS IS ACTUALLY AN INT!!!
            if(!repIDNoTextField.getText().isEmpty()) {
                repIDNo=Integer.parseInt(repIDNoTextField.getText().trim());
            } else{
                repIDNo=0;
            }
            //Parses text from permit number field and stores it as an int
            permitNo=Integer.parseInt(permitNoTextField.getText()); //CHECK INT!!!

            //Gets text from email address field and stores it as a string
            physicalAddress=addressField.getText();

            //Gets text from the email address field and stores it as an EmailAddress
            applicantEmail = new EmailAddress(emailAddressField.getText());

            //Gets text from the email address field and stores it as a PhoneNumber
            applicantPhone = new PhoneNumber(phoneNumberField.getText());

            //form is now filled in so go to page 2 of label application
            Main.loadFXML("/fxml/newApplicationPage2.fxml", this);
        }
    }


    public void submitApp() {

        //Creates a database to store the alcohol information from form
        AlcoholDatabase alcoholDB = new AlcoholDatabase(Main.database);

        Boolean formFilled=false;

        //radio buttons are grouped in the following groups:
        //product type group
        ToggleGroup productType = new ToggleGroup();
        beer.setToggleGroup(productType);
        wine.setToggleGroup(productType);

        //product source group
        ToggleGroup productSource = new ToggleGroup();
        international.setToggleGroup(productSource);
        domestic.setToggleGroup(productSource);

        //errors are printed only if required fields are not filled in
        if(productSource.getSelectedToggle() == null) {
            pSourceErrorField.setText("Please select whether the alcohol is imported or domestic.");
        } else{
            pSourceErrorField.setText("");
        }
        if(productType.getSelectedToggle() == null) {
            pTypeErrorField.setText("Please select whether the alcohol is a beer or wine.");
        } else{
            pTypeErrorField.setText("");
        }
        if(brandNameField.getText().isEmpty()) {
            brandNameErrorField.setText("Please fill in the brand name.");
        } else{
            brandNameErrorField.setText("");
        }
        if(alcoholContentField.getText().isEmpty()) {
            alcContentErrorField.setText("Please fill in the alcohol % of the beverage.");
        } else {
            alcContentErrorField.setText("");
        }
        if (productType.getSelectedToggle() == wine){
            int vintageYr=0;
            double pH=0.0;
            if(!wineVintageYearField.getText().isEmpty()) {
                vintageYr=Integer.parseInt(wineVintageYearField.getText()); //CHECK IF INPUT INTEGER!
            }
            if(!pHLevelField.getText().isEmpty()) {
                pH=Double.parseDouble(pHLevelField.getText()); //CHECK IF INPUT INTEGER!
            }
            wineType = new AlcoholInfo.Wine(pH, vintageYr);
        }
        if(datePicker==null) { //this doesn't work for now
            dateErrorField.setText("Please select the date.");
        } else{
            dateErrorField.setText("");
        }
        if(signatureField.getText().isEmpty()) {
            signatureErrorField.setText("Please fill in the signature field.");
        } else{
            signatureErrorField.setText("");
        }

        //check if required fields are filled in
        if((productType.getSelectedToggle() != null) && (productSource.getSelectedToggle() != null) &&
                !brandNameField.getText().isEmpty() && !alcoholContentField.getText().isEmpty() &&
                (datePicker != null) && !signatureField.getText().isEmpty()){
            formFilled=true;
        }

        if(formFilled) {
            //Checking if the product is domestic or imported by checking the product source radio button group
            if (productSource.getSelectedToggle() == domestic) {
                pSource = ProductSource.DOMESTIC;
            }
            else if (productSource.getSelectedToggle() == international) {
                pSource = ProductSource.IMPORTED;
            }

            //Checking if the product is a beer or wine by checking the product source radio button group
            if (productType.getSelectedToggle() == beer) {
                alcType = AlcoholType.BEER;
            }
            else if (productType.getSelectedToggle() == wine) {
                alcType = AlcoholType.WINE;
            }

            //sets alc info fields
            alcName = alcoholName.getText();
            brandName = brandNameField.getText();
            alcContent = Integer.parseInt(alcoholContentField.getText()); //CHECK IF INTEGER

            //sets the alcohol info
            appAlcoholInfo = new AlcoholInfo(alcContent, alcName, brandName, pSource, alcType, wineType);

            //creates a new ManufacturerInfo
            this.appManInfo= new ManufacturerInfo("Name Person", physicalAddress, "company", repIDNo,
                    permitNo, applicantPhone, applicantEmail);

            //creates and sets the date value
            Date newDate = java.sql.Date.valueOf(datePicker.getValue());

            // Creates a new application info and sets data
            ApplicationInfo appInfo = new ApplicationInfo(newDate, this.appManInfo, appAlcoholInfo);

            //!!!!!placeholder for applicant's submitted applications!!!!!
            List<SubmittedApplication> appList = new ArrayList<>();

            //Create applicant to store in submitted application
            Applicant applicant = new Applicant(appList);

            //Create a SubmittedApplication
            SubmittedApplication newApp = new SubmittedApplication(appInfo, ApplicationStatus.PENDINGREVIEW, applicant);
            applicant.addSubmittedApp(newApp);

            if (application != null)
                newApp.setApplicationID(application.getApplicationID());

            //Submit the new application to the database
            alcoholDB.submitApplication(newApp, username);

            //Go back to homepage
            main.loadHomepage(UserType.APPLICANT, username);
        }
    }

    public void cancelApp() {
        //Go back to homepage
        main.loadHomepage(UserType.APPLICANT, username);
    }
    public void saveApp() {
    }
    public void logout() {
        main.loadHomepage(UserType.BASIC, "");
    }
}
package com.emeraldElves.alcohollabelproject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by elijaheldredge on 3/31/17.
 */
public class AlcoholDatabase {

    // TODO: ints to enums

    private final int UNAPPROVED = 0;
    private final int APPROVED = 1;


    private Database db;

    /**
     * Creates an AlcoholDatabase
     *
     * @param db the main database that contains the data
     */
    public AlcoholDatabase(Database db) {
        this.db = db;
    }

    // TODO: finish getMostRecentApproved

    /**
     * Get the most recently approved applications.
     *
     * @param numApplications The maximum number of applications to receive.
     * @return A list of the most recently approved applications ordered from most recent to least recent.
     */
    public List<SubmittedApplication> getMostRecentApproved(int numApplications) {

        return new ArrayList<SubmittedApplication>();
    }

    // TODO: finish searchByBrandName

    /**
     * Search by the brand name of alcohol.
     *
     * @param brandName The brand name to search.
     * @return A list of approved alcohols containing the brandName ordered by time approved.
     */
    public List<SubmittedApplication> searchByBrandName(String brandName) {

        return new ArrayList<SubmittedApplication>();
    }

    // TODO: finish submitApplication

    /**
     * Submit an application for review.
     *
     * @param application The application to submit. If this is an update to an application, be sure to include the correct unique ID number in the application.
     * @return True if the application was submitted without error.
     */
    public boolean submitApplication(SubmittedApplication application) {
        // Put them in the right database or else
        // Fill in fields for submitted application and the other databases
        // read javadocs for return statement

        /*KYLE read this before checking my code. This is how i assume this method works:
        you add info to submittedapplications, manufacturerinfo, and alcoholinfo table.
        all of that info from the application passed in. I understood it better as i was typing
        this out, but i figured you might like to see this. Just imagine me maniachally laughing to myself at
        night. Thats how i'll type out this code.
        */

        /*IMPORTANT: I have not idea where to find the unique ID number for the application. I will just make a randomID number for each application, but
        i dont know where to associate the id number to the application. Other than that, this function should work.
        Also, I don't know where to find the approval time, expiration date, TTBUsername. Nothing in submittedapplication relating to it. hope you can figure this
        out. I'll do what i can with the rest.
        */
        // ManufacturerInfo
        //SubmittedApplications
        // AlcoholInfo

        //getting all info needed from submitted application into variables
        ApplicationStatus status = application.getStatus();
        ApplicationInfo info = application.getApplication();
        AlcoholInfo alcInfo = info.getAlcohol();
        ManufacturerInfo manInfo = info.getManufacturer();
        //

        int appID = (int)(Math.random()*100);//the unique application id for now

        ResultSet resultsSubmitted = db.select("*", "SubmittedApplications", "applicationID = " + appID);

        if(resultsSubmitted != null){
            //already in table, so we update
        }
        else{
            //not in table, need to add to all 3 tables
            //SubmittedApplications
            db.insert("'"+ appID + "', '" //application id
                            + manInfo.getRepresentativeID() + "', '" //applicant ID
                            + status + "', '" //status
                            + status.getMessage() + "', '" //status message
                            + info.getSubmissionDate() + "', '" //submission time
                            + info.getSubmissionDate() + "', '"//no field for expiration date
                            + manInfo.getName() + "', '" //agent name
                            + info.getSubmissionDate() + "', '" //approval date
                            + "admin1'" //TTBUsername
                    ,"SubmittedApplications");
            //ManufacturerInfo
            db.insert("'" + appID +"', '"
                            + manInfo.getName() + "', " //authorized name: i assume this is just the name of the applicant???
                            + manInfo.getPhysicalAddress() + "', " //physical address
                            + manInfo.getCompany() + "', " //company
                            + manInfo.getRepresentativeID() + "', " //representative id
                            + manInfo.getPermitNum() //permit num
                            + manInfo.getPhoneNumber().getPhoneNumber() + "', " //phone num. It may look stupid but it works
                            + manInfo.getEmailAddress().getEmailAddress() + "'" //email
                    ,"ManufacturerInfo");

            //AlcoholInfo
            db.insert("'" + appID + "', "
                            + alcInfo.getAlcoholContent() + "', " //alcohol content
                            + alcInfo.getName() + "', " //fanciful name
                            + alcInfo.getBrandName() + "', " //brand name
                            + alcInfo.getOrigin() + "', " //origin
                            + alcInfo.getAlcoholType() + "', " //type
                            + alcInfo.getWineInfo().pH + "', " //pH: to get ph, have to call wineinfo in alcinfo. Not sure if good
                            + alcInfo.getWineInfo().vintageYear + "'" //vintage year: see above comment
                    ,"AlcoholInfo");
        }





        return false;
    }

    // TODO: finish getMostRecentUnapproved

    /**
     * Get the most recent unapproved applications.
     *
     * @param numApplications The maximum number of applications to receive.
     * @return A list of the most recent unapproved applications ordered from most recent to least recent.
     */
    public List<SubmittedApplication> getMostRecentUnapproved(int numApplications) {
        ResultSet results = db.selectOrdered("*", "SubmittedApplications", "status = " + UNAPPROVED, "submissionTime ASC");

        List<SubmittedApplication> applications = new ArrayList<>();

        try {
            while (results.next()) {
                System.out.println(results.getString("submissionTime"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return applications;
    }

    // TODO: finish updateApplicationStatus

    /**
     * Update the approval status of the application.
     *
     * @param application The application to change the status of.
     * @param status      The new status of the application.
     * @return True if the status was updated without error.
     */
    public boolean updateApplicationStatus(SubmittedApplication application, ApplicationStatus status) {
        return false;
    }


    private ManufacturerInfo getManufacturerInfoByID(int applicationID) {
        ResultSet results = db.select("*", "ManufacturerInfo", "applicationID = " + applicationID);
        try {
            if (results.next()) {
                String authorizedName = results.getString("authorizedName");
                String physicalAddress = results.getString("physicalAddress");
                String company = results.getString("company");
                int repID = results.getInt("representativeID");
                int permitNum = results.getInt("permitNum");
                PhoneNumber phoneNumber = new PhoneNumber(results.getString("phoneNum"));
                EmailAddress emailAddress = new EmailAddress(results.getString("emailAddress"));
                return new ManufacturerInfo(authorizedName, physicalAddress, company, repID, permitNum, phoneNumber, emailAddress);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

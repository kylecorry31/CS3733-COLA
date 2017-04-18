package com.emeraldElves.alcohollabelproject.UserInterface;



import com.emeraldElves.alcohollabelproject.Authenticator;
import com.emeraldElves.alcohollabelproject.COLASearch;
import com.emeraldElves.alcohollabelproject.Data.SubmittedApplication;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.util.*;

/**
 * Created by Harry and Joe on 4/2/2017.
 */
public class HomeController {
    public ArrayList<Label> mostRecentLabels;
    public ArrayList<SubmittedApplication> mostRecentSubmissions;
    public List<SubmittedApplication> submitted;

    private Main main;
    @FXML
    private Button utility;
    @FXML
    private  Button CreateUser;
    @FXML
    private TextField searchbox;
    @FXML
    private Button logButton;
    @FXML
    private Label label0;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;

    private COLASearch search;


    public HomeController() {
        mostRecentLabels = new ArrayList<>();
        mostRecentSubmissions = new ArrayList<>();
        search = new COLASearch();
        submitted = search.searchRecentApplications(4);
    }

    // TODO: put FXML in correct folder

    /**
     * Loads homepage
     */
    public void utilityButton() {
        switch (Authenticator.getInstance().getUserType()) {
            case TTBAGENT:
                main.loadWorkflowPage();
                break;
            case APPLICANT:
                main.loadApplicantWorkflowPage();
                break;
        }
    }

    public void loadLog() {
        switch (Authenticator.getInstance().getUserType()) {
            case TTBAGENT:
                Authenticator.getInstance().logout();
                main.loadHomepage();
                break;
            case APPLICANT:
                Authenticator.getInstance().logout();
                main.loadHomepage();
                break;
            case SUPERAGENT:
                Authenticator.getInstance().logout();
                main.loadHomepage();
                break;
            case BASIC:
                main.loadLoginPage();
                break;
        }
    }

    public void createNewUser(){
        main.loadNewUserPage();
    }

    public void searchDatabase() {
        main.loadSearchPage(searchbox.getText());
    }

    public void feelingThirsty(){
        List<SubmittedApplication> applications = search.searchApprovedApplications();
        Random random = new Random();
        SubmittedApplication application;
        if (applications.isEmpty()) {
            application = null;
        } else {
            int pos = random.nextInt(applications.size());
            application = applications.get(pos);
        }
        if(application != null)
            main.loadDetailedSearchPage(application, application.getApplication().getAlcohol().getBrandName());
    }

    public void init(Main main) {
        this.main = main;
        for (int i = 0; i < submitted.size(); i++) {
            switch (i) {
                case 0:
                    label0.setText(submitted.get(i).getApplication().getAlcohol().getBrandName() + "     " + submitted.get(i).getApplication().getAlcohol().getName());
                    label0.setOnMouseClicked(event -> main.loadDetailedSearchPage(submitted.get(0), submitted.get(0).getApplication().getAlcohol().getBrandName()));
                    break;
                case 1:
                    label1.setText(submitted.get(i).getApplication().getAlcohol().getBrandName() + "     " + submitted.get(i).getApplication().getAlcohol().getName());
                    label1.setOnMouseClicked(event -> main.loadDetailedSearchPage(submitted.get(1), submitted.get(1).getApplication().getAlcohol().getBrandName()));
                    break;
                case 2:
                    label2.setText(submitted.get(i).getApplication().getAlcohol().getBrandName() + "     " + submitted.get(i).getApplication().getAlcohol().getName());
                    label2.setOnMouseClicked(event -> main.loadDetailedSearchPage(submitted.get(2), submitted.get(2).getApplication().getAlcohol().getBrandName()));
                    break;
                case 3:
                    label3.setText(submitted.get(i).getApplication().getAlcohol().getBrandName() + "     " + submitted.get(i).getApplication().getAlcohol().getName());
                    label3.setOnMouseClicked(event -> main.loadDetailedSearchPage(submitted.get(3), submitted.get(3).getApplication().getAlcohol().getBrandName()));
                    break;
            }
            List<SubmittedApplication> resultsList = search.searchApprovedApplications();

            AutoCompletionBinding<String> autoCompletionBinding;
            Set<String> possibleSuggestions = new HashSet<>();
            possibleSuggestions.clear();
            resultsList.sort((lhs, rhs) -> lhs.getApplication().getAlcohol().getBrandName().compareToIgnoreCase(rhs.getApplication().getAlcohol().getBrandName()));

            for(SubmittedApplication application: resultsList){
                possibleSuggestions.add(application.getApplication().getAlcohol().getBrandName());
                possibleSuggestions.add(application.getApplication().getAlcohol().getName());
            }

            autoCompletionBinding = TextFields.bindAutoCompletion(searchbox, possibleSuggestions);


            searchbox.setOnKeyPressed(ke -> autoCompletionBinding.setUserInput(searchbox.getText().trim()));
        }
        switch (Authenticator.getInstance().getUserType()) {
            case SUPERAGENT:
                CreateUser.setVisible(true);
                utility.setVisible(true);
                utility.setText("POTENTIAL USERS");
                logButton.setText("LOG OUT");
                break;
            case TTBAGENT:
                CreateUser.setVisible(false);
                utility.setVisible(true);
                utility.setText("APPLICATIONS");
                logButton.setText("LOG OUT");
                break;
            case APPLICANT:
                CreateUser.setVisible(false);
                utility.setVisible(true);
                utility.setText("MY APPLICATIONS");
                logButton.setText("LOG OUT");
                break;
            default:
                CreateUser.setVisible(false);
                utility.setVisible(false);
                logButton.setText("LOGIN");
                break;
        }


    }

}

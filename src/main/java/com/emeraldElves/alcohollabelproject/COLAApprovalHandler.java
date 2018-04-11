package com.emeraldElves.alcohollabelproject;

import com.emeraldElves.alcohollabelproject.Data.ApplicationStatus;
import com.emeraldElves.alcohollabelproject.Data.UserType;
import com.emeraldElves.alcohollabelproject.LogManager;
import com.emeraldElves.alcohollabelproject.data.COLA;
import com.emeraldElves.alcohollabelproject.data.User;
import com.emeraldElves.alcohollabelproject.database.Storage;

import java.time.LocalDate;
import java.util.List;

public class COLAApprovalHandler {

    private Storage storage;

    public COLAApprovalHandler(){
        storage = Storage.getInstance();
    }

    public synchronized List<COLA> getAssignedApplications(User user){
        List<COLA> applications = storage.getAssignedCOLAs(user);
        applications.removeIf(cola -> cola.getStatus() != ApplicationStatus.RECEIVED);
        return applications;
    }

    public synchronized List<COLA> assignCOLAs(User user, int count){
        User nullUser = new User("", "", "", UserType.TTBAGENT);
        nullUser.setId(-1);

        List<COLA> unassigned = getAssignedApplications(nullUser);

        for (COLA cola: unassigned){
            if(count > 0){
                cola.setAssignedTo(user.getId());
                storage.updateCOLA(cola);
                count--;
            } else {
                break;
            }
        }

        return getAssignedApplications(user);
    }

    public void approveCOLA(COLA cola, String message){
        cola.setStatus(ApplicationStatus.APPROVED);
        cola.setApprovalDate(LocalDate.now());
        cola.setLastUpdated(LocalDate.now());
        // Email user
        LogManager.getInstance().log(getClass().getSimpleName(), "Approved COLA " + cola.getId());
        storage.updateCOLA(cola);
    }

    public void rejectCOLA(COLA cola, String message){
        cola.setStatus(ApplicationStatus.REJECTED);
        cola.setLastUpdated(LocalDate.now());
        // Email user
        LogManager.getInstance().log(getClass().getSimpleName(), "Rejected COLA " + cola.getId());
        storage.updateCOLA(cola);
    }

    public void setNeedsCorrections(COLA cola, String message){
        cola.setStatus(ApplicationStatus.NEEDS_CORRECTION);
        cola.setLastUpdated(LocalDate.now());
        // Email user
        storage.updateCOLA(cola);
    }

}

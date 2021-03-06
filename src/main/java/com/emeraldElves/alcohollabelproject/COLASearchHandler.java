package com.emeraldElves.alcohollabelproject;

import com.emeraldElves.alcohollabelproject.data.COLA;
import com.emeraldElves.alcohollabelproject.data.search.SearchFilter;
import com.emeraldElves.alcohollabelproject.database.Storage;

import java.util.ArrayList;
import java.util.List;

public class COLASearchHandler {

    private Storage storage;
    private List<COLA> availableLabels;

    public COLASearchHandler(){
        storage = Storage.getInstance();
    }

    public synchronized void receiveAllCOLAs(){
        availableLabels = storage.getAllCOLAs();
    }

    public List<COLA> filteredSearch(SearchFilter filter){
        if(availableLabels == null){
            receiveAllCOLAs();
        }
        List<COLA> labels = new ArrayList<>(availableLabels);

        if(filter == null){
            return labels;
        }

        labels.removeIf(filter.not()::matches);

        return labels;
    }

}

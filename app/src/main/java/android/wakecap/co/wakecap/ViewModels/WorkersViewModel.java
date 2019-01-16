package android.wakecap.co.wakecap.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.wakecap.co.wakecap.Models.Item;

import java.util.List;

public class WorkersViewModel extends ViewModel {

    public MutableLiveData<List<Item>> workersList = new MutableLiveData<>();

    public void setWorkersList(List<Item> workersLists) {
         workersList.setValue(workersLists);
    }

    public MutableLiveData<List<Item>> getWorkersList() {
        return workersList;
    }
}

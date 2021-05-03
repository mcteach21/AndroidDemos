package mc.apps.demos.ui.fragments;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private MutableLiveData<String> shared = new MutableLiveData();

    public void setShared(String value, Context context){
        Toast.makeText(context, "setShared - ActiveObservers = "+shared.hasActiveObservers(), Toast.LENGTH_SHORT).show();
        shared.setValue(value);
    }
    public LiveData<String> getShared(){
        return shared;
    }
}
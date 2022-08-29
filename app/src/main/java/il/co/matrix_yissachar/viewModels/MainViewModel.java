package il.co.matrix_yissachar.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import il.co.matrix_yissachar.model.Benefit;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<Benefit> benefitLiveData = new MutableLiveData<>();

    public LiveData<Benefit> getBenefitLiveData() {
        return benefitLiveData;
    }

    public void setBenefitLiveData(Benefit benefit){
        benefitLiveData.postValue(benefit);
    }

    public Boolean isOnOfflineMode = false;
}


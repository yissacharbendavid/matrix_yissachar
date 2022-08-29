package il.co.matrix_yissachar.viewModels;

import androidx.lifecycle.ViewModel;

import il.co.matrix_yissachar.model.Benefit;

public class BenefitViewModel extends ViewModel {

    private Benefit benefit;
    private MainViewModel mainViewModel;

    public Benefit getBenefit() {
        return benefit;
    }

    public void setBenefit(Benefit benefit) {
        this.benefit = benefit;
    }

    public MainViewModel getMainViewModel() {
        return mainViewModel;
    }

    public void setMainViewModel(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }
}

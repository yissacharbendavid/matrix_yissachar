package il.co.matrix_yissachar.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Objects;

import il.co.matrix_yissachar.MyApplication;
import il.co.matrix_yissachar.model.Benefit;
import il.co.matrix_yissachar.model.Category;
import il.co.matrix_yissachar.model.DataObject;
import il.co.matrix_yissachar.repository.BenefitsRepository;

public class AllBenefitsViewModel extends ViewModel {

    BenefitsRepository benefitsRepository;

    private MainViewModel mainViewModel;
    private final MutableLiveData<ArrayList<Benefit>> allBenefitsLiveData;
    private final MutableLiveData<ArrayList<Category>> categoriesLiveData;
    private ArrayList<Benefit> localBenefits;
    private ArrayList<Category> localCategories;

    public void setMainViewModel(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }

    public MainViewModel getMainViewModel() {
        return mainViewModel;
    }

    public AllBenefitsViewModel()
    {
        allBenefitsLiveData = new MutableLiveData<>();
        categoriesLiveData = new MutableLiveData<>();
        benefitsRepository = new BenefitsRepository();
        DataObject localDataObject = benefitsRepository.getLocalJson(MyApplication.getAppContext());
        if(localDataObject != null) {
            localBenefits = localDataObject.DataListObject;
            localCategories = localDataObject.DataListCat;
            if (localBenefits != null)
                allBenefitsLiveData.setValue(localBenefits);
            if(localCategories != null){
                categoriesLiveData.setValue(localCategories);
            }
        }
        getNewDataObject();
    }

    private void getNewDataObject(){
        if(!MyApplication.isNetworkAvailable())
            return;

        // make the request wait a while to make it like an internet request
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                DataObject newDataObject = benefitsRepository.getJsonFromAssets(MyApplication.getAppContext(), "benefitsJsonObject.json");
                if (newDataObject != null && !isBenefitsListEquals(newDataObject.DataListObject,localBenefits)){
                    allBenefitsLiveData.postValue(newDataObject.DataListObject);
                }
                if (newDataObject != null && !isCategoriesListEquals(localCategories,newDataObject.DataListCat))
                    categoriesLiveData.postValue(newDataObject.DataListCat);
            }
        }.start();
    }

    public MutableLiveData<ArrayList<Benefit>> getBenefits(){
        return allBenefitsLiveData;
    }

    public MutableLiveData<ArrayList<Category>> getCategories(){
        return categoriesLiveData;
    }

    private boolean isBenefitsListEquals(ArrayList<Benefit> list1, ArrayList<Benefit> list2){
        if (list1 == null || list2 == null)
            return false;
        if (list1.size() != list2.size())
            return false;
        for(int i = 0; i < list1.size(); i++){
            if((list1.get(i).Id != list2.get(i).Id) && (!Objects.equals(list1.get(i).Title, list2.get(i).Title)) )
                return false;
        }
        return true;
    }

    private boolean isCategoriesListEquals(ArrayList<Category> list1, ArrayList<Category> list2){
        if (list1 == null || list2 == null)
            return false;
        if (list1.size() != list2.size())
            return false;
        for(int i = 0; i < list1.size(); i++){
            if(!Objects.equals(list1.get(i).CTitle, list2.get(i).CTitle))
                return false;
        }
        return true;
    }
}

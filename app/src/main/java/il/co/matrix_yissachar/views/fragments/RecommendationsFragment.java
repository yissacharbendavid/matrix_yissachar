package il.co.matrix_yissachar.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import il.co.matrix_yissachar.model.Category;
import il.co.matrix_yissachar.viewModels.AllBenefitsViewModel;
import il.co.matrix_yissachar.R;
import il.co.matrix_yissachar.model.Benefit;
import il.co.matrix_yissachar.viewModels.MainViewModel;
import il.co.matrix_yissachar.views.fragments.adapters.RecommendationsBenefitAdapter;

public class RecommendationsFragment extends Fragment {

    RecyclerView categoryOne;
    RecyclerView categoryTwo;
    RecyclerView categoryThree;
    RecyclerView categoryFour;
    RecyclerView categoryFive;
    TextView categoryOneTitle;
    TextView categoryTwoTitle;
    TextView categoryThreeTitle;
    TextView categoryFourTitle;
    TextView categoryFiveTitle;

    MutableLiveData<ArrayList<Benefit>> allBenefitsLiveData;
    MutableLiveData<ArrayList<Category>> categoriesLiveData;

    private AllBenefitsViewModel benefitsViewModel;
    public MainViewModel mainViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        benefitsViewModel = new ViewModelProvider(this).get(AllBenefitsViewModel.class);

        setMainViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_benefits, container, false);

        bindViews(view);

        allBenefitsLiveData = benefitsViewModel.getBenefits();
        categoriesLiveData = benefitsViewModel.getCategories();

        setObservers();

        return view;
    }

    private void setMainViewModel(){
        if(benefitsViewModel.getMainViewModel() == null)
            benefitsViewModel.setMainViewModel(mainViewModel);
        else
            mainViewModel = benefitsViewModel.getMainViewModel();
    }

    private void setObservers(){
        Observer<ArrayList<Category>> categoriesObserver = this::updateCategoriesUi;
        Observer<ArrayList<Benefit>> benefitsObserver = this::updateBenefitsUi;

        categoriesLiveData.observe(getViewLifecycleOwner(),categoriesObserver);
        allBenefitsLiveData.observe(getViewLifecycleOwner(),benefitsObserver);
    }

    private void updateCategoriesUi(ArrayList<Category> categories){

        categoryOneTitle.setText(categories.get(0).CTitle);
        categoryTwoTitle.setText(categories.get(1).CTitle);
        categoryThreeTitle.setText(categories.get(2).CTitle);
        categoryFourTitle.setText(categories.get(3).CTitle);
        categoryFiveTitle.setText(categories.get(4).CTitle);

    }

    private void updateBenefitsUi(ArrayList<Benefit> benefits) {
        if(benefits.size() != 0) {
            RecommendationsBenefitAdapter adapter = new RecommendationsBenefitAdapter(filterByCategory(benefits, 1),mainViewModel);
            categoryOne.setAdapter(adapter);
            adapter = new RecommendationsBenefitAdapter(filterByCategory(benefits, 2),mainViewModel);
            categoryTwo.setAdapter(adapter);
            adapter = new RecommendationsBenefitAdapter(filterByCategory(benefits, 3),mainViewModel);
            categoryThree.setAdapter(adapter);
            adapter = new RecommendationsBenefitAdapter(filterByCategory(benefits, 4),mainViewModel);
            categoryFour.setAdapter(adapter);
            adapter = new RecommendationsBenefitAdapter(filterByCategory(benefits, 5),mainViewModel);
            categoryFive.setAdapter(adapter);
        }
    }

    private void bindViews(View view){

        categoryOne = view.findViewById(R.id.category_1_recyclerview);
        categoryTwo = view.findViewById(R.id.category_2_recyclerview);
        categoryThree = view.findViewById(R.id.category_3_recyclerview);
        categoryFour = view.findViewById(R.id.category_4_recyclerview);
        categoryFive = view.findViewById(R.id.category_5_recyclerview);

        categoryOneTitle = view.findViewById(R.id.category_1_title);
        categoryTwoTitle = view.findViewById(R.id.category_2_title);
        categoryThreeTitle = view.findViewById(R.id.category_3_title);
        categoryFourTitle = view.findViewById(R.id.category_4_title);
        categoryFiveTitle = view.findViewById(R.id.category_5_title);

        setRecyclerViews();
    }

    private void setRecyclerViews(){

        categoryOne.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        categoryTwo.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        categoryThree.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        categoryFour.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        categoryFive.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(categoryOne.getContext(),
                LinearLayoutManager.HORIZONTAL);

        dividerItemDecoration.setDrawable
                (Objects.requireNonNull(AppCompatResources.getDrawable(requireContext(),R.drawable.recycler_view_divider)));

        categoryOne.addItemDecoration(dividerItemDecoration);
        categoryTwo.addItemDecoration(dividerItemDecoration);
        categoryThree.addItemDecoration(dividerItemDecoration);
        categoryFour.addItemDecoration(dividerItemDecoration);
        categoryFive.addItemDecoration(dividerItemDecoration);
    }

    private ArrayList<Benefit> filterByCategory(ArrayList<Benefit> benefits, int catId){
        ArrayList<Benefit> filteredList = new ArrayList<>();
        for (Benefit benefit: benefits){
            if(benefit.CatId == catId){
                filteredList.add(benefit);
            }
        }
        return filteredList;
    }
}

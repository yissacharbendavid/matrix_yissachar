package il.co.matrix_yissachar.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import il.co.matrix_yissachar.viewModels.BenefitViewModel;
import il.co.matrix_yissachar.R;
import il.co.matrix_yissachar.model.Benefit;
import il.co.matrix_yissachar.viewModels.MainViewModel;

public class BenefitFragment extends Fragment {

    public BenefitFragment() {}

    TextView categoryName;
    TextView benefitId;
    ImageView image;
    BenefitViewModel benefitViewModel;
    public MainViewModel mainViewModel;
    public Benefit benefit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        connectWithViewModel();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_benefit,container,false);

        bindViews(view);
        updateUi();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mainViewModel.setBenefitLiveData(null);
    }

    private void connectWithViewModel(){

        benefitViewModel = new ViewModelProvider(this).get(BenefitViewModel.class);

        if(benefitViewModel.getMainViewModel() == null)
            benefitViewModel.setMainViewModel(this.mainViewModel);
        else
            this.mainViewModel = benefitViewModel.getMainViewModel();

        benefit = mainViewModel.getBenefitLiveData().getValue();

        if(benefitViewModel.getBenefit() == null)
            benefitViewModel.setBenefit(this.benefit);
        else
            this.benefit = benefitViewModel.getBenefit();
    }

    private void bindViews(View view){
        categoryName = view.findViewById(R.id.category_name);
        benefitId = view.findViewById(R.id.benefit_id);
        image = view.findViewById(R.id.benefit_image);
    }

    private void updateUi(){
        categoryName.setText(getString(R.string.category_title,benefit.CatId));
        benefitId.setText(getString(R.string.benefit_id,"" + benefit.Id));

        Glide
                .with(requireContext())
                .load(benefit.Imag)
                .placeholder(R.drawable.max_image)
                .into(image);
    }
}

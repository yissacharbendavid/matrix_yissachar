package il.co.matrix_yissachar.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;


import il.co.matrix_yissachar.MyApplication;
import il.co.matrix_yissachar.R;
import il.co.matrix_yissachar.viewModels.MainViewModel;
import il.co.matrix_yissachar.views.fragments.AllBenefitsFragment;
import il.co.matrix_yissachar.views.fragments.BenefitFragment;
import il.co.matrix_yissachar.views.fragments.FavoritesFragment;
import il.co.matrix_yissachar.views.fragments.MyTreatsFragment;
import il.co.matrix_yissachar.views.fragments.RecommendationsFragment;
import il.co.matrix_yissachar.model.Benefit;

public class MainActivity extends AppCompatActivity {

    private final String currentFragment = "currentFragment";

    int currentFragmentInt;
    Button allBenefitsButton;
    Button recommendationsButton;
    Button myTreatsButton;
    Button favoritesButton;
    MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        bindViews();

        setButtonsClickListeners();

        allBenefitsButton.callOnClick();

        observeChoosingBenefit();

        checkInternetConnection();

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        currentFragmentInt = savedInstanceState.getInt(currentFragment);

        restoreUiState();
    }

    private void bindViews(){
        allBenefitsButton = findViewById(R.id.all_benefits_button);
        recommendationsButton = findViewById(R.id.recommendations_button);
        favoritesButton = findViewById(R.id.favorites_button);
        myTreatsButton = findViewById(R.id.my_treats_button);
    }

    private void restoreUiState(){
        switch (currentFragmentInt){
            case 1 : allBenefitsButton.callOnClick();
                break;
            case 2 : recommendationsButton.callOnClick();
                break;
            case 3 : myTreatsButton.callOnClick();
                break;
            case 4 : favoritesButton.callOnClick();
            default:
                allBenefitsButton.callOnClick();
        }
    }

    private void setButtonsClickListeners(){

        allBenefitsButton.setOnClickListener(v -> {
            AllBenefitsFragment allBenefitsFragment = new AllBenefitsFragment();
            allBenefitsFragment.mainViewModel = mainViewModel;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView,allBenefitsFragment)
                    .commit();

            setButtonsColors(allBenefitsButton);
            currentFragmentInt = 1;
        });

        recommendationsButton.setOnClickListener(v -> {
            RecommendationsFragment recommendationsFragment = new RecommendationsFragment();
            recommendationsFragment.mainViewModel = mainViewModel;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView,recommendationsFragment)
                    .commit();

            setButtonsColors(recommendationsButton);
            currentFragmentInt = 2;
        });

        myTreatsButton.setOnClickListener(v -> {
            Fragment myTreatsFragment = new MyTreatsFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView,myTreatsFragment)
                    .commit();

            setButtonsColors(myTreatsButton);
            currentFragmentInt = 3;
        });

        favoritesButton.setOnClickListener(v -> {
            Fragment favoritesFragment = new FavoritesFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView,favoritesFragment)
                    .commit();

            setButtonsColors(favoritesButton);
            currentFragmentInt = 4;
        });
    }

    private void observeChoosingBenefit(){

        Observer<Benefit> benefitChooseObserver = chosenBenefit -> {

            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_fragment);
            if(chosenBenefit != null && !(currentFragment instanceof BenefitFragment)) {
                BenefitFragment fragment = new BenefitFragment();
                fragment.mainViewModel = mainViewModel;
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_fragment, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        };

        mainViewModel.getBenefitLiveData().observe(this, benefitChooseObserver);
    }

    private void setButtonsColors(Button selected){
        allBenefitsButton.setBackgroundResource(R.color.white);
        allBenefitsButton.setTextColor(Color.BLACK);
        recommendationsButton.setBackgroundResource(R.color.white);
        recommendationsButton.setTextColor(Color.BLACK);
        myTreatsButton.setBackgroundResource(R.color.white);
        myTreatsButton.setTextColor(Color.BLACK);
        favoritesButton.setBackgroundResource(R.color.white);
        favoritesButton.setTextColor(Color.BLACK);

        selected.setBackgroundResource(R.color.purple_500);
        selected.setTextColor(Color.WHITE);
    }

    private void checkInternetConnection(){

        if(!MyApplication.isNetworkAvailable()&& !mainViewModel.isOnOfflineMode){

            DialogInterface.OnClickListener noInternetDialogClickListener = (dialog, which) -> {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        mainViewModel.isOnOfflineMode = true;
                        break;
                }

            };


            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage(R.string.no_internet)
                    .setPositiveButton(R.string.yes,noInternetDialogClickListener)
                    .setNegativeButton(R.string.continue_without_internet,noInternetDialogClickListener)
                    .create()
                    .show();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(currentFragment,currentFragmentInt);
    }
}
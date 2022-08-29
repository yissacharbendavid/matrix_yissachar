package il.co.matrix_yissachar.views.fragments.adapters;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import il.co.matrix_yissachar.MyApplication;
import il.co.matrix_yissachar.R;
import il.co.matrix_yissachar.model.Benefit;
import il.co.matrix_yissachar.snapHelpers.SnapHelperOneByOne;
import il.co.matrix_yissachar.viewModels.MainViewModel;

public class RecommendationsBenefitAdapter extends RecyclerView.Adapter<RecommendationsBenefitAdapter.BenefitHolder> {

    List<Benefit> allBenefits;
    MainViewModel mainViewModel;

    public RecommendationsBenefitAdapter(List<Benefit> allBenefits, MainViewModel mainViewModel) {
        this.allBenefits = allBenefits;
        this.mainViewModel = mainViewModel;
    }

    @NonNull
    @Override
    public RecommendationsBenefitAdapter.BenefitHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.benefit_list_item, parent, false);
        return new RecommendationsBenefitAdapter.BenefitHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendationsBenefitAdapter.BenefitHolder holder, int position) {
        Benefit benefit = allBenefits.get(position);
        holder.bind(benefit);
    }

    @Override
    public int getItemCount() {
        return allBenefits.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        SnapHelperOneByOne linearSnapHelper = new SnapHelperOneByOne();
        recyclerView.setOnFlingListener(null);
        linearSnapHelper.attachToRecyclerView(recyclerView);
    }

    protected class BenefitHolder extends RecyclerView.ViewHolder {

        private final TextView titleView;
        private final ImageView backgroundImage;

        public BenefitHolder(@NonNull View view) {
            super(view);
            titleView = view.findViewById(R.id.title);
            backgroundImage = view.findViewById(R.id.background_image);
        }

        public void bind(@NonNull Benefit benefit) {
            String title = "<b>" + benefit.Title + "</b> - " + benefit.STitle;
            titleView.setText(Html.fromHtml(title));

            Glide
                    .with(MyApplication.getAppContext())
                    .load(benefit.Imag)
                    .placeholder(R.drawable.max_image)
                    .into(backgroundImage);

            itemView.setOnClickListener(v -> mainViewModel.setBenefitLiveData(benefit));
        }
    }
}
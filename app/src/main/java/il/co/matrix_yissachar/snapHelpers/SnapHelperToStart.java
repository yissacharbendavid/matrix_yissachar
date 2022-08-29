package il.co.matrix_yissachar.snapHelpers;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;



public class SnapHelperToStart extends LinearSnapHelper {

    private OrientationHelper horizontalHelper;

    @Override
    @Nullable
    public View findSnapView(@NonNull RecyclerView.LayoutManager lm) {
        return findView(lm, getHorizontalHelper(lm));
    }

    @Override
    @NonNull
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager,
                                              @NonNull View targetView) {
        int[] out = new int[2];

        if (!(layoutManager instanceof LinearLayoutManager)) {
            return out;
        }

        LinearLayoutManager lm = (LinearLayoutManager) layoutManager;

        out[0] = getDistanceToEnd(targetView, getHorizontalHelper(lm));

        return out;
    }

    private int getDistanceToEnd(View targetView, @NonNull OrientationHelper helper) {
        int distance;

        int childEnd = helper.getDecoratedEnd(targetView);
        if (childEnd >= helper.getEnd() - (helper.getEnd() - helper.getEndAfterPadding()) / 2) {
            distance = helper.getDecoratedEnd(targetView) - helper.getEnd();
        } else {
            distance = childEnd - helper.getEndAfterPadding();
        }

        return distance;
    }

    @Nullable
    private View findView(@NonNull RecyclerView.LayoutManager layoutManager,
                          @NonNull OrientationHelper helper) {

        if (layoutManager.getChildCount() == 0 || !(layoutManager instanceof LinearLayoutManager)) {
            return null;
        }

        final LinearLayoutManager lm = (LinearLayoutManager) layoutManager;

        if (isAtEdgeOfList(lm)) {
            return null;
        }

        View edgeView = null;
        int distanceToTarget = Integer.MAX_VALUE;

        for (int i = 0; i < lm.getChildCount(); i++) {
            View currentView = lm.getChildAt(i);
            int currentViewDistance;
            currentViewDistance = Math.abs(helper.getDecoratedEnd(currentView)
                    - helper.getEnd());
            if (currentViewDistance < distanceToTarget) {
                distanceToTarget = currentViewDistance;
                edgeView = currentView;
            }
        }
        return edgeView;
    }

    private boolean isAtEdgeOfList(LinearLayoutManager lm) {
        if (!lm.getReverseLayout()){
            return lm.findLastCompletelyVisibleItemPosition() == lm.getItemCount() - 1;

        } else {
            return lm.findFirstCompletelyVisibleItemPosition() == 0;
        }
    }

    private OrientationHelper getHorizontalHelper(RecyclerView.LayoutManager layoutManager) {
        if (horizontalHelper == null || horizontalHelper.getLayoutManager() != layoutManager) {
            horizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager);
        }
        return horizontalHelper;
    }
}
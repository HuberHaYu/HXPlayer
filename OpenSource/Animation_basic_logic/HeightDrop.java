package com.hbxr.animation;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.hbxr.animation.Interpolator.FallAndBounceInterpolator;

public class HeightDrop {

    private Context context;

    public HeightDrop(Context context) {
        this.context = context;
    }

    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public void animateViewHeight(View view, int startHeightDp, int endHeightDp, long duration) {
        if (view.getAnimation() != null && view.getAnimation().hasStarted()) {
            view.getAnimation().cancel();
        }
        int startHeightPx = dpToPx(startHeightDp);
        int endHeightPx = dpToPx(endHeightDp);

        ValueAnimator animator = ValueAnimator.ofInt(startHeightPx, endHeightPx);
        animator.setInterpolator(new FallAndBounceInterpolator());
        animator.setDuration(duration);
        animator.addUpdateListener(valueAnimator -> {
            int value = (Integer) valueAnimator.getAnimatedValue();
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = value;
            view.requestLayout();
        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animation) {
            }

            @Override
            public void onAnimationEnd(@NonNull Animator animation) {
                animator.removeAllListeners();
            }

            @Override
            public void onAnimationCancel(@NonNull Animator animation) {
                animator.removeAllListeners();
            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animation) {
            }
        });

        animator.start();
    }
}

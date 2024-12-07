package com.hbxr.animation;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.hbxr.animation.Interpolator.CDI_Interpolator;

public class NonLinearViewWidth {

    private final Context context;

    public NonLinearViewWidth(Context context) {
        this.context = context;
    }

    public void animateViewWidth(View view, int startWidthDp, int endWidthDp, long duration) {
        int startWidthPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, startWidthDp, context.getResources().getDisplayMetrics());
        int endWidthPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, endWidthDp, context.getResources().getDisplayMetrics());

        if (view.getAnimation() != null && view.getAnimation().hasStarted()) {
            view.getAnimation().cancel();
        }

        ValueAnimator animator = ValueAnimator.ofInt(startWidthPx, endWidthPx);
        animator.setInterpolator(new CDI_Interpolator());
        animator.setDuration(duration);
        animator.addUpdateListener(valueAnimator -> {
            int value = (Integer) valueAnimator.getAnimatedValue();
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.width = value;
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

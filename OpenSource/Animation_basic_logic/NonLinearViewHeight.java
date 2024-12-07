package com.hbxr.animation;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.hbxr.animation.Interpolator.CDI_Interpolator;

public class NonLinearViewHeight {

    public NonLinearViewHeight() {
    }

    public void animateViewHeight(View view, int startHeight, int endHeight, long duration) {
        if (view.getAnimation() != null && view.getAnimation().hasStarted()) {
            view.getAnimation().cancel();
        }

        ValueAnimator animator = ValueAnimator.ofInt(startHeight, endHeight);
        animator.setInterpolator(new CDI_Interpolator());
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
                //animator.cancel();
                //System.gc();
            }

            @Override
            public void onAnimationCancel(@NonNull Animator animation) {
                animator.removeAllListeners();
                //animator.cancel();
            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animation) {
            }
        });

        animator.start();
    }
}

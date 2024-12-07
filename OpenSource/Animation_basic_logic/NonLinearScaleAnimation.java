package com.hbxr.animation;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.NonNull;

public class NonLinearScaleAnimation {
    private final View targetView;
    private ValueAnimator animator;

    public NonLinearScaleAnimation(View view) {
        this.targetView = view;
    }

    public void setupAnimation(float startScale, float endScale, int duration, Interpolator interpolator) {
        if (animator != null && animator.isRunning()) {
            // 取消当前动画
            animator.cancel();
        }
        animator = ValueAnimator.ofFloat(startScale, endScale);
        animator.setDuration(duration);
        animator.setInterpolator(interpolator);
        animator.addUpdateListener(animation -> {
            float currentValue = (float) animation.getAnimatedValue();
            targetView.setScaleX(currentValue);
            targetView.setScaleY(currentValue);
        });
    }

    public void startAnimation() {
        if (animator != null) {
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
                    onAnimationEnd(animation);  // Handle cancellation by calling the end method
                }

                @Override
                public void onAnimationRepeat(@NonNull Animator animation) {
                }
            });
            animator.start();
        }
    }

    public void reverseAnimationFromCurrent() {
        if (animator != null && animator.isRunning()) {
            float currentScale = (float) animator.getAnimatedValue();
            animator.reverse();
            setupAnimation(currentScale, 1.0f, 300, new OvershootInterpolator());
            animator.start();
        }
    }
}

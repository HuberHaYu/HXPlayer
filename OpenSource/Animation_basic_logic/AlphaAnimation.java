package com.hbxr.animation;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.Interpolator;
public class AlphaAnimation {
    private final View targetView;
    private ValueAnimator animator;

    public AlphaAnimation(View view) {
        this.targetView = view;
    }

    public void setupAnimation(float startAlpha, float endAlpha, int duration, Interpolator interpolator) {
        if (animator != null && animator.isRunning()) {
            // 取消当前动画
            animator.cancel();
        }
        animator = ValueAnimator.ofFloat(startAlpha, endAlpha);
        animator.setDuration(duration);
        animator.setInterpolator(interpolator);
        animator.addUpdateListener(animation -> {
            float currentValue = (float) animation.getAnimatedValue();
            targetView.setAlpha(currentValue);
        });
    }

    public void startAnimation() {
        if (animator != null) {
            animator.start();
        }
    }

    public void cancel() {
        if (animator != null) {
            animator.cancel();
        }
    }

    public void reverseAnimation(int reverseDuration, Interpolator reverseInterpolator) {
        if (animator != null && animator.isRunning()) {
            animator.cancel(); // 取消当前正在进行的动画
            // 设置反向动画
            animator = ValueAnimator.ofFloat(targetView.getAlpha(), 1);
            animator.setDuration(reverseDuration);
            animator.setInterpolator(reverseInterpolator);
            animator.addUpdateListener(animation -> {
                float currentValue = (float) animation.getAnimatedValue();
                targetView.setAlpha(currentValue);
            });
            animator.start();
        }
    }
}

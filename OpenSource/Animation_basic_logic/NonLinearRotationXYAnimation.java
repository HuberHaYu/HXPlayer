package com.hbxr.animation;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.NonNull;

public class NonLinearRotationXYAnimation {
    private ValueAnimator animatorX;
    private ValueAnimator animatorY;

    public NonLinearRotationXYAnimation() {}

    public void setupAnimation(View targetView, float startRotationX, float endRotationX, float startRotationY, float endRotationY, int duration, Interpolator interpolator) {
        // rotationX
        if (animatorX != null && animatorX.isRunning()) {
            animatorX.cancel();
        }
        animatorX = ValueAnimator.ofFloat(startRotationX, endRotationX);
        animatorX.setDuration(duration);
        animatorX.setInterpolator(interpolator);
        animatorX.addUpdateListener(animation -> {
            float currentValue = (float) animation.getAnimatedValue();
            targetView.setRotationX(currentValue);
        });

        // rotationY
        if (animatorY != null && animatorY.isRunning()) {
            animatorY.cancel();
        }
        animatorY = ValueAnimator.ofFloat(startRotationY, endRotationY);
        animatorY.setDuration(duration);
        animatorY.setInterpolator(interpolator);
        animatorY.addUpdateListener(animation -> {
            float currentValue = (float) animation.getAnimatedValue();
            targetView.setRotationY(currentValue);
        });
    }

    public void startAnimation() {
        if (animatorX != null && animatorY != null) {
            animatorX.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(@NonNull Animator animation) {
                }

                @Override
                public void onAnimationEnd(@NonNull Animator animation) {
                    animatorX.removeAllListeners();
                }

                @Override
                public void onAnimationCancel(@NonNull Animator animation) {
                    onAnimationEnd(animation);
                }

                @Override
                public void onAnimationRepeat(@NonNull Animator animation) {
                }
            });
            animatorX.start();
            animatorY.start();
        }
    }

    public void reverseAnimationFromCurrent(View targetView) {
        if ((animatorX != null && animatorX.isRunning()) || (animatorY != null && animatorY.isRunning())) {
            float currentRotationX = (animatorX != null) ? (float) animatorX.getAnimatedValue() : targetView.getRotationX();
            float currentRotationY = (animatorY != null) ? (float) animatorY.getAnimatedValue() : targetView.getRotationY();

            // 反向动画
            setupAnimation(targetView, currentRotationX, 0.0f, currentRotationY, 0.0f, 300, new OvershootInterpolator());
            animatorX.start();
            animatorY.start();
        }
    }
}

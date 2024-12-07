package com.hbxr.animation;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;

import androidx.annotation.NonNull;

import com.hbxr.animation.Interpolator.FallAndBounceInterpolator;

public class NonLinearViewPosition {

    public NonLinearViewPosition() {
    }

    public void animateViewPositionXY(View view, float startX, float endX, float startY, float endY, long duration) {
        if (view.getAnimation() != null && view.getAnimation().hasStarted()) {
            view.getAnimation().cancel();
        }

        ValueAnimator animatorX = ValueAnimator.ofFloat(startX, endX);
        animatorX.setInterpolator(new FallAndBounceInterpolator());
        animatorX.setDuration(duration);
        animatorX.addUpdateListener(valueAnimator -> {
            float value = (Float) valueAnimator.getAnimatedValue();
            view.setX(value);
        });

        ValueAnimator animatorY = ValueAnimator.ofFloat(startY, endY);
        animatorY.setInterpolator(new FallAndBounceInterpolator());
        animatorY.setDuration(duration);
        animatorY.addUpdateListener(valueAnimator -> {
            float value = (Float) valueAnimator.getAnimatedValue();
            view.setY(value);
        });

        Animator.AnimatorListener endListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animation) {
            }

            @Override
            public void onAnimationEnd(@NonNull Animator animation) {
                animatorX.removeAllListeners();
                animatorY.removeAllListeners();
            }

            @Override
            public void onAnimationCancel(@NonNull Animator animation) {
                onAnimationEnd(animation);
            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animation) {
            }
        };

        animatorX.addListener(endListener);
        animatorY.addListener(endListener);

        animatorX.start();
        animatorY.start();
    }
}


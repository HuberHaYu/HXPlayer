package com.hbxr.animation;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;

import androidx.annotation.NonNull;

import com.hbxr.animation.Interpolator.CDI;

public class NonLinearViewTranslationX {

    public NonLinearViewTranslationX() {
    }

    public void animateViewTranslationX(View view, float start, float end, long duration) {
        if (view.getAnimation() != null && view.getAnimation().hasStarted()) {
            view.getAnimation().cancel();
        }

        ValueAnimator animator = ValueAnimator.ofFloat(start, end);
        animator.setInterpolator(new CDI());
        animator.setDuration(duration);
        animator.addUpdateListener(valueAnimator -> {
            float value = (Float) valueAnimator.getAnimatedValue();
            view.setTranslationX(value);
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
                onAnimationEnd(animation);
            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animation) {
            }
        });

        animator.start();
    }
}

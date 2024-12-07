package com.hbxr.animation;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

import androidx.annotation.NonNull;

import com.hbxr.animation.Interpolator.CDI;

public class NonLinearViewCornerRadius {

    public NonLinearViewCornerRadius() {
    }

    public void animateCornerRadius(View view, float startRadius, float endRadius, long duration) {
        ValueAnimator animator = ValueAnimator.ofFloat(startRadius, endRadius);
        animator.setInterpolator(new CDI());
        animator.setDuration(duration);

        animator.addUpdateListener(valueAnimator -> {
            float currentRadius = (Float) valueAnimator.getAnimatedValue();

            if (view.getBackground() instanceof GradientDrawable) {
                GradientDrawable background = (GradientDrawable) view.getBackground();
                background.setCornerRadius(currentRadius); // 设置当前的圆角半径
            }
        });

        // 设置动画监听器
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

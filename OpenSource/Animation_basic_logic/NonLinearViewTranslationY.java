package com.hbxr.animation;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.Interpolator;

import androidx.annotation.NonNull;

import com.hbx.hxplayer.R;
import com.hbxr.animation.Interpolator.Bezier_ControlCenter.BezierControl;
import com.hbxr.animation.Interpolator.CDI;

public class NonLinearViewTranslationY {
    private ValueAnimator animator;

    public NonLinearViewTranslationY() {
    }

    public void animateViewTranslationY(View view, float start, float end, long duration) {
        if (view.getAnimation() != null && view.getAnimation().hasStarted()) {
            view.getAnimation().cancel();
        }

        // 根据控件ID选择不同插值器
        Interpolator interpolator = getInterpolatorForView(view);

        animator = ValueAnimator.ofFloat(start, end);
        animator.setInterpolator(interpolator);
        animator.setDuration(duration);
        animator.addUpdateListener(valueAnimator -> {
            float value = (Float) valueAnimator.getAnimatedValue();
            view.setTranslationY(value);
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

    // 自适应插值器
    private Interpolator getInterpolatorForView(View view) {
        int viewId = view.getId();
        return BezierControl.getBezierInterpolator(2);
    }

    public boolean isAnimation() {
        if (animator != null) {
            return animator.isRunning();
        }
        return false;
    }
}

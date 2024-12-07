package com.hbxr.animation;

import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;

import com.hbxr.animation.Interpolator.SmoothCycleInterpolator;

import java.util.HashMap;
import java.util.Map;

public class BlurEffectController {
    private final View targetView;
    private float currentRadius = 0.0f;
    private float targetRadius = 20.0f;
    private boolean isAnimating = false;
    private Interpolator interpolator = new SmoothCycleInterpolator();
    private boolean hasErrorLogged = false;

    // 缓存
    private final Map<Float, RenderEffect> renderEffectCache = new HashMap<>();

    public BlurEffectController(View targetView) {
        this.targetView = targetView;
    }

    public void setInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
    }

    public Interpolator getInterpolator() {
        return interpolator;
    }

    public void applyBlur(float radius) {
        if (targetView != null) {
            // 半径量化
            float quantizedRadius = Math.round(radius * 10f) / 10f;
            if (Math.abs(quantizedRadius - currentRadius) < 0.1f) {
                return;
            }
            currentRadius = quantizedRadius;
            try {
                if (currentRadius > 0) {
                    RenderEffect effect = renderEffectCache.get(currentRadius);
                    if (effect == null) {
                        effect = RenderEffect.createBlurEffect(currentRadius, currentRadius, Shader.TileMode.MIRROR);
                        renderEffectCache.put(currentRadius, effect);
                    }
                    targetView.setRenderEffect(effect);
                } else {
                    targetView.setRenderEffect(null);
                }
            } catch (Exception e) {
                if (!hasErrorLogged) {
                    Log.e("BlurEffectController", "Error applying blur effect", e);
                    hasErrorLogged = true;
                }
            }
        }
    }

    private void scheduleBlurRefresh() {
        if (!isAnimating) {
            applyBlur(currentRadius);
        }
    }

    public void animateBlur(float startRadius, float endRadius, long duration) {
        if (targetView == null) return;
        isAnimating = true;

        Animation blurAnimation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolator != null) {
                    interpolatedTime = interpolator.getInterpolation(interpolatedTime);
                }
                float radius = startRadius + (endRadius - startRadius) * interpolatedTime;
                applyBlur(radius);
            }

            @Override
            public boolean willChangeBounds() {
                return false;
            }
        };

        blurAnimation.setDuration(duration);
        blurAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                isAnimating = false;
                scheduleBlurRefresh();
                //clearCache();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        targetView.startAnimation(blurAnimation);
        targetRadius = endRadius;
    }

    public void reverseBlur(long duration) {
        animateBlur(currentRadius, 0, duration);
    }

    public void toggleBlur(long duration) {
        if (currentRadius > 0) {
            reverseBlur(duration);
        } else {
            animateBlur(0.001f, targetRadius, duration);
        }
    }

    public float getCurrentBlurRadius() {
        return currentRadius;
    }

    public void clearCache() {
        renderEffectCache.clear();
    }
}

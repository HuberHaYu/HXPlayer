package com.hbxr.animation.Interpolator;

import android.view.animation.Interpolator;

public class FallAndBounceInterpolator implements Interpolator {
    @Override
    public float getInterpolation(float t) {
        // 🤯
        float base = (float) (1.0 - Math.pow((1.0 - t), 3));
        float bounce = (float) Math.sin(2 * Math.PI * t) * 0.015f; // Q
        return base + (t < 1.0 ? bounce * (1 - t) : 0);
    }
}
package com.jiazh.example.logindemo.interpolator;

import android.view.animation.LinearInterpolator;

/**
 * author：ZhiQiangJia
 * create time: 2018/3/21 0:29
 * description:
 */
public class JellyInterpolator extends LinearInterpolator {
    private float factor;

    public JellyInterpolator() {
        this.factor = 0.15f;
    }

    @Override
    public float getInterpolation(float input) {
        return (float) (Math.pow(2, -10 * input)
                * Math.sin((input - factor / 4) * (2 * Math.PI) / factor) + 1);
    }
}
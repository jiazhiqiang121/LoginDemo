package com.jiazh.example.logindemo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.jiazh.example.logindemo.interpolator.JellyInterpolator;

public class LoginActivity extends AppCompatActivity {

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mIsStartAnimation) {
                mIsStartAnimation = false;
                // 计算出控件的高与宽
                int loginWidth = mLoginBtn.getMeasuredWidth();
                int loginHeight = mLoginBtn.getMeasuredHeight();
                // 隐藏输入框
                mUserNameEt.setVisibility(View.INVISIBLE);
                mPasswordEt.setVisibility(View.INVISIBLE);
                inputAnimator(mLoginLayoutLl, loginWidth, loginHeight);
            } else {
                mIsStartAnimation = true;
                recovery();
            }

        }
    };
    private Button mLoginBtn;
    private LinearLayout mLoginLayoutLl;
    private EditText mUserNameEt;
    private EditText mPasswordEt;
    private View mProgressBarView;
    private boolean mIsStartAnimation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginBtn = findViewById(R.id.login_btn);
        mLoginLayoutLl = findViewById(R.id.login_layout_ll);
        mUserNameEt = findViewById(R.id.user_name_et);
        mPasswordEt = findViewById(R.id.password_et);
        mProgressBarView = findViewById(R.id.layout_progress);

        mLoginBtn.setOnClickListener(mClickListener);
    }

    private void inputAnimator(final View view, float w, float h) {
        AnimatorSet set = new AnimatorSet();
        ValueAnimator animator = ValueAnimator.ofFloat(0, w);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view
                        .getLayoutParams();
                params.leftMargin = (int) value;
                params.rightMargin = (int) value;
                view.setLayoutParams(params);
            }
        });

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mLoginLayoutLl,
                "scaleX", 1f, 0.5f);
        set.setDuration(500);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(animator, animator2);
        set.start();
        set.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressBarView.setVisibility(View.VISIBLE);
                progressAnimator(mProgressBarView);
                mLoginLayoutLl.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }
        });

    }

    private void progressAnimator(final View view) {
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX",
                0.5f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY",
                0.5f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view,
                animator, animator2);
        animator3.setDuration(1000);
        animator3.setInterpolator(new JellyInterpolator());
        animator3.start();

    }

    /**
     * 恢复初始状态
     */
    private void recovery() {
        mProgressBarView.setVisibility(View.GONE);
        mLoginLayoutLl.setVisibility(View.VISIBLE);
        mUserNameEt.setVisibility(View.VISIBLE);
        mPasswordEt.setVisibility(View.VISIBLE);

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mLoginLayoutLl.getLayoutParams();
        params.leftMargin = 0;
        params.rightMargin = 0;
        mLoginLayoutLl.setLayoutParams(params);

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mLoginLayoutLl, "scaleX", 0.5f, 1f);
        animator2.setDuration(500);
        animator2.setInterpolator(new AccelerateDecelerateInterpolator());
        animator2.start();
    }
}


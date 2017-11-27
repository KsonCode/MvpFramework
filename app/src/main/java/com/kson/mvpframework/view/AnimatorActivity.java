package com.kson.mvpframework.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;

import com.kson.mvpframework.App;
import com.kson.mvpframework.R;
import com.kson.mvpframework.base.BaseActivity;
import com.kson.mvpframework.widget.MyView;

public class AnimatorActivity extends BaseActivity {


    MyView myView;

    @Override
    public int bindLayout() {
        return R.layout.activity_animator;
    }

    @Override
    public void setListener() {

    }

    @Override
    public void Click(View view) {
        switch (view.getId()) {
//            case R.id.btn:

//                TranslateAnimation translateAnimation = new TranslateAnimation(0,500,0,0);
//                translateAnimation.setDuration(2000);
//                translateAnimation.setFillAfter(true);
//                iv.startAnimation(translateAnimation);

//                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv, "translationY", 0f, 500f, 400f, 0f, 200f, 1080f);
//                objectAnimator.setDuration(10000);
////                objectAnimator.setRepeatCount(-1);
////                objectAnimator.setRepeatMode(ValueAnimator.RESTART);//从头开始
//                objectAnimator.setRepeatMode(ValueAnimator.REVERSE);//从尾开始
//                objectAnimator.start();
//
//                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(iv,"alpha",1f,0f);
//
//                objectAnimator1.setDuration(10000);
//                objectAnimator1.start();
//                objectAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//
//                        float value = (float) animation.getAnimatedValue();
//
//                        System.out.println("value:"+value);
//
//                    }
//                });

//                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(iv,"rotation",0f,360f);
//
//                objectAnimator1.setDuration(5000);
//                objectAnimator1.start();
//
//                objectAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//
//                        float value = (float) animation.getAnimatedValue();
//
//                        System.out.println("value:"+value);
//
//                    }
//                });


//                //沿x轴放大
//                ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(iv, "scaleX", 1f, 2f, 1f);
////                scaleXAnimator.setRepeatCount(5);
//                //沿y轴放大
//                ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(iv, "scaleY", 1f, 2f, 1f);
//                //移动
//                ObjectAnimator translationXAnimator = ObjectAnimator.ofFloat(iv, "translationX", 0f, 200f, 0f);
//                //透明动画
//                ObjectAnimator animator = ObjectAnimator.ofFloat(iv, "alpha", 1f, 0f, 1f);
//
//
//                //set对象
//                AnimatorSet set = new AnimatorSet();
//                //同时沿X,Y轴放大，且改变透明度，然后移动
//                set.play(scaleXAnimator).with(scaleYAnimator).with(animator).after(translationXAnimator).after(5000);
//                //都设置3s，也可以为每个单独设置
//                set.setDuration(3000);
//
//                set.addListener(new Animator.AnimatorListener() {
//                    @Override
//                    public void onAnimationStart(Animator animation) {
//                        System.out.println("start");
//
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        System.out.println("end");
//                    }
//
//                    @Override
//                    public void onAnimationCancel(Animator animation) {
//                        System.out.println("cancle");
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animator animation) {
////                        System.out.println("repeat");
//                    }
//                });
//
//                set.start();

//                set.addPauseListener(new Animator.AnimatorPauseListener() {
//                    @Override
//                    public void onAnimationPause(Animator animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationResume(Animator animation) {
//
//                    }
//                });

//                set.addListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        super.onAnimationEnd(animation);
//                    }
//
//                    @Override
//                    public void onAnimationStart(Animator animation) {
//                        super.onAnimationStart(animation);
//                    }
//                });


//                Animator animator = AnimatorInflater.loadAnimator(this,R.animator.alpha);
//                animator.setTarget(iv);
//                animator.start();

//                Animator animator = AnimatorInflater.loadAnimator(this,R.animator.set);
//                animator.setTarget(iv);
//                animator.start();

//                ValueAnimator mAnimator = ValueAnimator.ofInt(0, MyApp.screen_width - myView.getWidth());
//                //2.为目标对象的属性变化设置监听器
//                mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        //3.为目标对象的属性设置计算好的属性值
//                        int animatorValue = (int) animation.getAnimatedValue();
//
//                        System.out.println("animatorvalue:" + animatorValue);
//
//                        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) myView.getLayoutParams();
//                        marginLayoutParams.leftMargin = animatorValue;
//                        myView.setLayoutParams(marginLayoutParams);
//
//
//                    }
//                });
//                //4.设置动画的持续时间、是否重复及重复次数等属性
//                mAnimator.setDuration(2000);
////                mAnimator.setRepeatCount(3);
//                mAnimator.setRepeatMode(ValueAnimator.REVERSE);
//                mAnimator.setInterpolator(new DecelerateInterpolator());
//                //5.为ValueAnimator设置目标对象并开始执行动画
//                mAnimator.setTarget(iv);
//                mAnimator.start();


//                break;

        }

    }

    @Override
    public void initView() {


        myView = (MyView) findViewById(R.id.myview);


    }

    @Override
    public void initData() {

        getSupportActionBar().hide();

    }

    public void alpha(View view) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(myView, "alpha", 1, 0, 1);
        objectAnimator.setDuration(2000);
        objectAnimator.start();
    }

    public void translate(View view) {

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(myView, "translationX", 0, 500);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(myView, "translationY", 0, 1000);
        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(myView, "alpha", 0, 1);
        objectAnimator3.setRepeatCount(-1);
        objectAnimator3.setDuration(100);
        objectAnimator3.start();

        AnimatorSet set = new AnimatorSet();
        set.playTogether(objectAnimator,objectAnimator2,objectAnimator3);
        set.setDuration(5000);
        set.start();

    }

    public void rotate(View view) {
    }

    public void scale(View view) {
    }

    public void value(View view) {

        System.out.println("==========");
        ValueAnimator mAnimator = ValueAnimator.ofInt(0, App.screen_width - myView.getWidth());
        //2.为目标对象的属性变化设置监听器
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //3.为目标对象的属性设置计算好的属性值
                int animatorValue = (int) animation.getAnimatedValue();

                System.out.println("animatorvalue:" + animatorValue);

                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) myView.getLayoutParams();
                marginLayoutParams.leftMargin = animatorValue;
                myView.setLayoutParams(marginLayoutParams);


            }
        });
        mAnimator.start();
    }

    public void object(View view) {
    }
}

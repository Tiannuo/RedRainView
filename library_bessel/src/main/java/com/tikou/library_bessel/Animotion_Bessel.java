package com.tikou.library_bessel;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.Random;

/**
 * Created by Administrator on 2016/10/12 0012.
 * 作者：TianNuo
 * 邮箱：1320917731@qq.com
 * 属性动画+自定义view
 */

public class Animotion_Bessel extends FrameLayout{
    private Drawable[] redbags=new Drawable[4];
    private Random random=new Random();
    private int img_Height;
    private int img_Width;
    private LayoutParams params;
    private int mar_left;
    //
    private int mWidth,mHeight;
    public Animotion_Bessel(Context context, AttributeSet attrs) {
        this(context, attrs,0);

    }

    public Animotion_Bessel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth=getMeasuredWidth();
        mHeight=getMeasuredHeight();

    }

    private void init() {
        redbags[0]=getResources().getDrawable(R.mipmap.hb_green);
        redbags[1]=getResources().getDrawable(R.mipmap.hb_red);
        redbags[2]=getResources().getDrawable(R.mipmap.hb_yellow);
        redbags[3]=getResources().getDrawable(R.mipmap.hb_zi);
        img_Width=redbags[0].getIntrinsicWidth();
        img_Height=redbags[0].getIntrinsicHeight();
        params=new LayoutParams(img_Width,img_Height);
        params.gravity= Gravity.TOP|Gravity.CENTER_HORIZONTAL;

    }
    public void addRedBag(){
        final ImageView iv_bag=new ImageView(getContext());
        iv_bag.setImageDrawable(redbags[random.nextInt(4)]);
        mar_left=random.nextInt(mWidth);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {

            //params.setMarginStart(mar_left);
        }
        //params.setMargins(mar_left,0,0,0);
        addView(iv_bag,params);
        AnimatorSet set=getStateListAnimator(iv_bag);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                removeView(iv_bag);
            }
        });
        set.start();


    }

    private AnimatorSet getStateListAnimator(ImageView iv_bag) {
        //属性
        ObjectAnimator Alpha=ObjectAnimator.ofFloat(iv_bag,View.ALPHA,0.4f,1.0f);
        ObjectAnimator ScaleX=  ObjectAnimator.ofFloat(iv_bag, View.SCALE_X,0.4f,0.8f);
        ObjectAnimator ScaleY=  ObjectAnimator.ofFloat(iv_bag, View.SCALE_Y,0.4f,0.8f);
        //ObjectAnimator TranslateY=ObjectAnimator.ofFloat(iv_bag,View.TRANSLATION_Y,0,-800,400);
        //ObjectAnimator TranslateX=ObjectAnimator.ofFloat(iv_bag,View.TRANSLATION_X,0,-200,400);
        AnimatorSet enterSet=new AnimatorSet();
        enterSet.setDuration(200);
        //enterSet.setInterpolator(new AccelerateInterpolator());
        enterSet.playTogether(ScaleX,ScaleY,Alpha);

        //贝塞尔
        ValueAnimator bessel=getBesselAnimator(iv_bag);
        //一起
        AnimatorSet togetheSet=new AnimatorSet();
        //按书序
        togetheSet.playSequentially(enterSet,bessel);
        togetheSet.setTarget(iv_bag);
        return togetheSet;
    }

    private ValueAnimator getBesselAnimator(final ImageView iv_bag) {
        PointF p3=new PointF((mWidth-img_Width)/2,mHeight-img_Height);
        //  PointF p0=new PointF(iv_bag.getX(),mHeight-img_Height);
        Log.e("===",iv_bag.getX()+"");
        //拐点
        PointF p2=getTogglePoint(1);
        PointF p1=getTogglePoint(0);
        PointF p0=new PointF(random.nextInt(mWidth),0);
        BesselEvaluetor besselEvaluetor=new BesselEvaluetor(p1,p2);
        ValueAnimator animator=ValueAnimator.ofObject(besselEvaluetor,p0,p3);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
               PointF pointF= (PointF) animation.getAnimatedValue();
                iv_bag.setX(pointF.x);
                iv_bag.setY(pointF.y);
                iv_bag.setAlpha(1-animation.getAnimatedFraction());
            }
        });
        animator.setDuration(4000);
        return animator;
    }

    private PointF getTogglePoint(int type) {
        PointF pointF=new PointF();
        pointF.x=random.nextInt(mWidth);
        if (type==1){
            //下面
           // pointF.y=random.nextInt(mHeight/2+random.nextInt(mHeight/2));
            pointF.y=random.nextInt(mHeight/2)+mHeight/2;
        }else {
            pointF.y=random.nextInt(mHeight/2);
        }

        return pointF;
    }


}

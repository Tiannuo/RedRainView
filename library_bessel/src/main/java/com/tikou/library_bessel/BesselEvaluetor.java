package com.tikou.library_bessel;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * Created by Administrator on 2016/10/15 0015.
 * 作者：TianNuo
 * 邮箱：1320917731@qq.com
 */

public class BesselEvaluetor implements TypeEvaluator<PointF>{
    private PointF p1;
    private PointF p2;

    public BesselEvaluetor() {
    }

    public BesselEvaluetor(PointF p1, PointF p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        PointF pointF=new PointF();
        pointF.x= (float) (startValue.x*Math.pow((1-fraction),3)+3*p1.x*fraction*Math.pow((1-fraction),2)
                        +3*p2.x*Math.pow(fraction,2)*(1-fraction)
                        +endValue.x*Math.pow(fraction,3));
        pointF.y= (float) (startValue.y*Math.pow((1-fraction),3)+3*p1.y*fraction*Math.pow((1-fraction),2)
                +3*p2.y*Math.pow(fraction,2)*(1-fraction)
                +endValue.y*Math.pow(fraction,3));

        PointF pointF1=new PointF();
        pointF1.x=startValue.x*(1-fraction)*(1-fraction)*(1-fraction)
                +3*p1.x*fraction*(1-fraction)*(1-fraction)
                +3*p2.x*fraction*fraction*(1-fraction)
                +endValue.x*fraction*fraction*fraction;

        pointF1.y=startValue.y*(1-fraction)*(1-fraction)*(1-fraction)
                +3*p1.y*fraction*(1-fraction)*(1-fraction)
                +3*p2.y*fraction*fraction*(1-fraction)
                +endValue.y*fraction*fraction*fraction;

        return pointF;
    }
}

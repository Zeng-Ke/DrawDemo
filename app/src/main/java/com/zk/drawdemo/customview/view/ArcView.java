package com.zk.drawdemo.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * author: ZK.
 * date:   On 2017/11/29.
 * description:弧形，扇形
 */
public class ArcView extends View {

    private Paint mPaint;


    public ArcView(Context context) {
        super(context);
        initPaint();
    }

    public ArcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(20);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(100, 300, 300, 500, 120, 120, false, mPaint);

        mPaint.setColor(Color.RED);
        canvas.drawArc(new RectF(100, 400, 500, 800), 90, 180, true, mPaint);

        mPaint.setColor(Color.BLUE);
        canvas.drawArc(100, 0, 300, 200, 0, 90, true, mPaint);


        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        canvas.drawArc(400, 0, 700, 200, 0, 120, false, mPaint);

    }

}

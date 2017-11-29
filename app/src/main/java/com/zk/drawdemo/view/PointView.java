package com.zk.drawdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * author: ZK.
 * date:   On 2017/11/29.
 */
public class PointView extends View {

    private Paint mPaint;
    private float[] points = {10, 400, 50, 400, 90, 400, 130, 400, 170, 600, 30, 600, 70, 600};

    public PointView(Context context) {
        super(context);
        initPaint();
    }

    public PointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(20);
        mPaint.setStrokeCap(Paint.Cap.ROUND);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPoint(50, 50, mPaint);

        mPaint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawPoint(100, 100, mPaint);

        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawPoint(200, 200, mPaint);

        mPaint.setColor(Color.GREEN);
        canvas.drawPoints(points, mPaint);


    }
}

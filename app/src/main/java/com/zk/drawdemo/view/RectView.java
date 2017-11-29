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
public class RectView extends View {

    private Paint mPaint;

    public RectView(Context context) {
        super(context);
        initPaint();
    }

    public RectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(50,100,250,300,mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(100,250,450,600,mPaint);

    }
}

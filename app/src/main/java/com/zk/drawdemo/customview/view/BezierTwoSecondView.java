package com.zk.drawdemo.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * author: ZK.
 * date:   On 2017/11/29.
 * description:弧形，扇形
 */
public class BezierTwoSecondView extends View {

    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private float mTouchX = 200;
    private float mTouchY = 200;


    public BezierTwoSecondView(Context context) {
        super(context);
        initPaint();
    }

    public BezierTwoSecondView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public void initPaint() {
        mPaint = new Paint();

        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
       // canvas.translate(mWidth / 2, mHeight / 2);
        mPaint.setColor(Color.parseColor("#cccccc"));
        canvas.drawLine(200, 300, mTouchX, mTouchY, mPaint);
        canvas.drawLine(400, 300, mTouchX, mTouchY, mPaint);

        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(200, 300, 10, mPaint);
        canvas.drawCircle(400, 300, 10, mPaint);
        canvas.drawCircle(mTouchX, mTouchY, 10, mPaint);

        mPaint.setColor(Color.RED);
        Path path = new Path();
        path.moveTo(200, 300);
        path.quadTo(mTouchX,mTouchY,400,300);
        canvas.drawPath(path,mPaint);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mTouchX = event.getX();
                mTouchY = event.getY();
                invalidate();
                break;

        }
        return true;

    }
}

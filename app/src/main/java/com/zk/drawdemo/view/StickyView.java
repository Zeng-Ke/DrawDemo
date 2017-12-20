package com.zk.drawdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * author: ZK.
 * date:   On 2017/12/7.
 */
public class StickyView extends View {

    private int mWidth;
    private int mHeight;
    private Paint mPaint1;
    private Paint mPaint2;
    private Path mPath1;
    private Path mPath2;
    private float c = 0.551915024494f;
    private float radius = 100;

    public StickyView(Context context) {
        super(context);
    }

    public StickyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mPaint1 = new Paint();
        mPaint1.setAntiAlias(true);
        mPaint1.setColor(Color.RED);
        mPaint1.setStyle(Paint.Style.STROKE);
        mPaint1.setStrokeWidth(5);

        mPaint2 = new Paint();
        mPaint2.setAntiAlias(true);
        mPaint2.setColor(Color.BLACK);
        mPaint2.setStyle(Paint.Style.STROKE);
        mPaint2.setStrokeWidth(5);


        mPath1 = new Path();
        mPath2 = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.drawPoint(radius * -1, 0, mPaint1);
        canvas.drawPoint(radius, 0, mPaint1);

        mPath1.moveTo(radius * -1, 0);
        mPath1.cubicTo(-radius, -radius * c, -radius * c, -radius, 0, -radius);
        mPath1.cubicTo(radius*c,radius*-1,radius,radius*-1*c,radius,0);


        mPath2.moveTo(radius * -1, 0);
        mPath2.cubicTo(-radius, (float) (-radius * c*1.5), -radius * c, (float) (-radius*1.5), 0, (float) (-radius*1.5));
        mPath2.cubicTo(radius*c, (float) (radius*-1*1.5),radius, (float) (radius*-1*c*1.5),radius,0);

        canvas.drawPath(mPath1, mPaint1);
        canvas.drawPath(mPath2, mPaint2);

    }
}

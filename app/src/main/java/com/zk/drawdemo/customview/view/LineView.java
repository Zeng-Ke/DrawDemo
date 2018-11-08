package com.zk.drawdemo.customview.view;

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
 * description:线条
 */
public class LineView extends View {

    private Paint mPaint;

    private float[] points = {100, 400, 300, 400, 200, 400, 200, 800, 100, 800, 600, 800};

    public LineView(Context context) {
        super(context);
        initPaint();
    }

    public LineView(Context context, @Nullable AttributeSet attrs) {
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
        canvas.drawLine(100, 100, 300, 300, mPaint);
        mPaint.setColor(Color.GREEN);
        canvas.drawLines(points, mPaint);
    }

}

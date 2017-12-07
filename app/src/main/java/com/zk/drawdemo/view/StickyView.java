package com.zk.drawdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
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
    private Paint mPaint;

    public StickyView(Context context) {
        super(context);
    }

    public StickyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(new RectF(0, 0, 200, 200), 90, 180, true, mPaint);
        Path path = new Path();
        path.moveTo(100,0);
        path.cubicTo(220,60,220,140,100,200);
        canvas.drawPath(path,mPaint
        );


    }
}

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
public class CircleView extends View {

    private Paint mPaint;

    public CircleView(Context context) {
        super(context);
        initPaint();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();

    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setColor(Color.YELLOW);//颜色
        mPaint.setStyle(Paint.Style.FILL);//填充状态
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(300, 300, 200, mPaint);//参数：X,Y坐标,半径，画笔

        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);//设置画笔宽度
        canvas.drawCircle(300, 500, 50, mPaint);

        mPaint.setAntiAlias(false);
        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(400, 600, 70, mPaint);
    }
}

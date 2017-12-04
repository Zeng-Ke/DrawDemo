package com.zk.drawdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


/**
 * author: ZK.
 * date:   On 2017/11/29.
 * description:图片
 *
 *
 * public void scale (float sx, float sy)
 *
 * public final void scale (float sx, float sy, float px, float py)
 *
 *这两个方法中前两个参数是相同的分别为x轴和y轴的缩放比例。而第二种方法比前一种多了两个参数，用来控制缩放中心位置的。
 *
 *
 */
public class ScaleView extends View {

    private float width;
    private float height;

    private Paint mPaint;
    private float scaleWidth = 0;
    private boolean isZoomOut = false;//是否在缩小
    private  int roateAngle = 0;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                try {
                    Thread.sleep(30);
                    invalidate();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }
    };


    public ScaleView(Context context) {
        super(context);
        initPaint();
    }

    public ScaleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }


    public void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(width / 2, height / 2);
        float varableWidth = scaleWidth;
        if (isZoomOut) {//缩小
            scaleWidth -= 30;
            canvas.rotate(++roateAngle);
            while (varableWidth >= 30) {
                canvas.drawRect(new RectF(varableWidth / 2 * -1, varableWidth / 2 * -1, varableWidth / 2, varableWidth / 2), mPaint);
                varableWidth -= 30;
            }
            if (scaleWidth < 30)
                isZoomOut = false;//达到最小值，改为扩大状态
        } else {//扩大
            scaleWidth += 30;
            canvas.rotate(++roateAngle);
            while (varableWidth <= width - 10) {
                canvas.drawRect(new RectF(varableWidth / 2 * -1, varableWidth / 2 * -1, varableWidth / 2, varableWidth / 2), mPaint);
                varableWidth += 30;
            }
            if (scaleWidth > width - 10)
                isZoomOut = true;//达到最大值，改为缩小状态
        }

        mHandler.sendEmptyMessage(0);
    }



    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);

    }

}

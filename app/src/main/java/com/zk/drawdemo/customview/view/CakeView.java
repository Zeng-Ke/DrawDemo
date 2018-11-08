package com.zk.drawdemo.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * author: ZK.
 * date:   On 2017/11/29.
 * description:饼图
 */
public class CakeView extends View {

    private Paint mCakePaint;


    private float radius = 200;
    private float centerX = 400;
    private float centerY = 400;

    private List<Element> mElements = new ArrayList<>();
    private Element otherElement = new Element(Color.CYAN, 0, "其他");
    private Paint mTextPaint;

    {
        mElements.add(new Element(Color.GRAY, (float) 0.01, "灰色"));
        mElements.add(new Element(Color.YELLOW, (float) 0.2, "黄色"));
        mElements.add(new Element(Color.BLACK, (float) 0.2, "黑色"));
        mElements.add(new Element(Color.RED, (float) 0.42, "红色"));
        mElements.add(new Element(Color.BLUE, (float) 0.1, "蓝色"));
        mElements.add(new Element(Color.GREEN, (float) 0.05, "绿色"));

    }


    public CakeView(Context context) {
        super(context);
        initPaint();
    }

    public CakeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();

    }

    private void initPaint() {
        mCakePaint = new Paint();
        mCakePaint.setAntiAlias(true);//抗锯齿
        mCakePaint.setStyle(Paint.Style.FILL);//填充状态

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(20);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float startAngle = 0;
        for (Element element : mElements) {
            if (element.per < 0.02) {
                otherElement.per += element.per;
                continue;
            }
            mCakePaint.setColor(element.color);//颜色
            float sweepAngle = 360 * element.per;
            Ponit ponit = getTranslationPoint(startAngle, sweepAngle);
            canvas.drawArc(new RectF(ponit.x - radius, ponit.y - radius, ponit.x + radius, ponit.y + radius), startAngle, sweepAngle,

                    true, mCakePaint);
            mTextPaint.setColor(element.color);
            canvas.drawText(element.name, ponit.peakX, ponit.peakY, mTextPaint);

            startAngle += sweepAngle;
        }
        if (360 * otherElement.per > 0) {
            mCakePaint.setColor(otherElement.color);
            Ponit ponit = getTranslationPoint(startAngle, 360 * otherElement.per);
            canvas.drawArc(new RectF(ponit.x - radius, ponit.y - radius, ponit.x + radius, ponit.y + radius), startAngle, 360 *
                            otherElement.per,
                    true, mCakePaint);
            mTextPaint.setColor(otherElement.color);
            canvas.drawText(otherElement.name, ponit.peakX, ponit.peakY, mTextPaint);
        }

    }


    private Ponit getTranslationPoint(float startAndle, float sweepAngle) {
        Ponit ponit = new Ponit();
        float middleAndle = (startAndle + startAndle + sweepAngle) / 2;
        if ((middleAndle > 0 && middleAndle < 90) || (middleAndle > 270 && middleAndle < 360)) {
            double angle = middleAndle / 180 * Math.PI;
            float k = (float) (Math.tan(angle));//系数
            float b = (float) (centerY - k * centerX);
            ponit.x = centerX + 5;
            ponit.y = k * ponit.x + b;
            ponit.peakX = (float) (ponit.x + Math.abs(Math.cos(angle) * radius)) + 20;
            ponit.peakY = k * ponit.peakX + b;

        } else if (middleAndle > 90 && middleAndle < 270) {
            double angle = middleAndle / 180 * Math.PI;
            float k = (float) (Math.tan(middleAndle / 180 * Math.PI));//系数
            float b = (float) (centerY - k * centerX);
            ponit.x = centerX - 5;
            ponit.y = k * ponit.x + b;
            ponit.peakX = (float) (ponit.x - Math.abs(Math.cos(angle) * radius)) - 20;
            ponit.peakY = k * ponit.peakX + b;

        } else if (middleAndle == 0 || middleAndle == 360) {
            ponit.x = centerX + 5;
            ponit.y = centerY;
            ponit.peakX = ponit.x + radius;
            ponit.peakY = ponit.y;

        } else if (middleAndle == 90) {
            ponit.x = centerX;
            ponit.y = centerY + 5;
            ponit.peakX = ponit.x;
            ponit.peakY = ponit.y + radius;

        } else if (middleAndle == 180) {
            ponit.x = centerX - 5;
            ponit.y = centerY;

            ponit.peakX = ponit.x - radius;
            ponit.peakY = ponit.y;

        } else if (middleAndle == 270) {
            ponit.x = centerX;
            ponit.y = centerY - 5;
            ponit.peakX = ponit.x;
            ponit.peakY = ponit.y - radius;

        }
        return ponit;

    }


    public class Element {
        public @ColorInt
        int color;
        public float per;
        public String name;

        public Element(int color, float per, String name) {
            this.color = color;
            this.per = per;
            this.name = name;
        }
    }


    public class Ponit {
        public float x;
        public float y;//圆心
        public float peakX;
        public float peakY;//扇形弧线中点坐标
    }

}

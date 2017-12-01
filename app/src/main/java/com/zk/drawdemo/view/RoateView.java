package com.zk.drawdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * author: ZK.
 * date:   On 2017/11/29.
 * description:饼图
 */
public class RoateView extends View {

    private Paint mCakePaint;


    private float radius = 200;
    private float width;
    private float height;

    private List<Element> mElements = new ArrayList<>();
    private Element otherElement = new Element(Color.CYAN, 0, "其他");
    private Paint mTextPaint;
    private Paint mLinePaint;

    {
        mElements.add(new Element(Color.GRAY, 60, "灰色"));
        mElements.add(new Element(Color.YELLOW, 100, "黄色"));
        mElements.add(new Element(Color.BLACK, 80, "黑色"));
        mElements.add(new Element(Color.RED, 30, "红色"));
        mElements.add(new Element(Color.BLUE, 20, "蓝色"));
        mElements.add(new Element(Color.GREEN, 70, "绿色"));

    }


    public RoateView(Context context) {
        super(context);
        initPaint();
    }

    public RoateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;

    }

    private void initPaint() {
        mCakePaint = new Paint();
        mCakePaint.setAntiAlias(true);//抗锯齿
        mCakePaint.setStyle(Paint.Style.FILL);//填充状态

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(20);

        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStrokeWidth(5);
        mLinePaint.setStyle(Paint.Style.STROKE);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float totalAngle = 0;
        canvas.translate(width / 2, height / 2);
        for (Element element : mElements) {
            mCakePaint.setColor(element.color);
            mLinePaint.setColor(element.color);
            mTextPaint.setColor(element.color);
            canvas.drawArc(new RectF(radius * -1+10, radius * -1, radius+10, radius), 0, element.per, true, mCakePaint);
            float x = (float) (radius * Math.cos((element.per * Math.PI) / (2 * 180)));
            float y = (float) (radius * Math.sin((element.per * Math.PI) / (2 * 180)));

            canvas.drawLines(new float[]{x, y, x + 20, y}, mLinePaint);
            canvas.drawText(element.name, x + 25, y, mTextPaint);
            canvas.rotate(element.per);

        }
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


}

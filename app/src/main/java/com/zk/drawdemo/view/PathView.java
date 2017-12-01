package com.zk.drawdemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.zk.drawdemo.R;

/**
 * author: ZK.
 * date:   On 2017/11/29.
 * description:路径
 */
public class PathView extends View {

    private Paint mPaint;
    private Path mPath;


    public PathView(Context context) {
        super(context);
        initPaint();
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(20);

        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.addCircle(200,200,100, Path.Direction.CW);//CW :顺时针，CCW：逆时针
        mPath.addCircle(300,200,100, Path.Direction.CCW);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPath.lineTo(500,500);
        mPath.rLineTo(100,0);

        canvas.drawPath(mPath,mPaint);

        mPath.reset();
        mPath.moveTo(200,400);
        mPath.quadTo(200,600,300,750);
        canvas.drawPath(mPath,mPaint);

        mPath.reset();
        mPath.moveTo(200,400);
        mPath.cubicTo(200,600,400,550,300,700);
        canvas.drawPath(mPath,mPaint);







    }

}

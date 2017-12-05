package com.zk.drawdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * author: ZK.
 * date:   On 2017/12/5.
 */
public class RadoView extends View {

    public int width;
    public int height;
    public float unitWidth = 0;//雷达网每一级横跨的宽度
    private Paint mGraddingPaint;
    private Paint mDataPaint;

    private int[] datas;
    private String[] tags = new String[]{"物理攻击", "法术攻击", "物理防御", "法术防御", "移速", "智力"};
    private Paint mTextPaint;
    private Paint mPointPaint;


    public RadoView(Context context) {
        super(context);
        initPaint();
    }

    public RadoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        setData(new int[]{5, 2, 4, 3, 3, 2});
    }


    private void initPaint() {
        mGraddingPaint = new Paint();
        mGraddingPaint.setColor(Color.parseColor("#bbbbbb"));
        mGraddingPaint.setStyle(Paint.Style.STROKE);
        mGraddingPaint.setStrokeWidth(2);
        mGraddingPaint.setAntiAlias(true);

        mDataPaint = new Paint();
        mDataPaint.setColor(Color.parseColor("#66FF5530"));
        mDataPaint.setAntiAlias(true);
       /* mDataPaint.setStyle(Paint.Style.STROKE);
        mDataPaint.setStrokeWidth(20);*/

        mTextPaint = new Paint();
        mTextPaint.setTextSize(18);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.parseColor("#666666"));

        mPointPaint = new Paint();
        mPointPaint.setColor(Color.parseColor("#FF5530"));
        mPointPaint.setAntiAlias(true);
    }


    public void setData(int[] datas) {
        this.datas = datas;

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        height = h;
        unitWidth = width / 16;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(width / 2, height / 2);

        Path path = new Path();
        //画雷达网.每60度地画一个区域，一个区域在画6级线。然后旋转60度再画下一个区域
        for (int i = 0; i < 6; i++) {
            for (int j = 1; j < 7; j++) {
                path.lineTo(unitWidth * j, 0);
                path.lineTo((float) (unitWidth * Math.cos(Math.PI / 3) * j), (float) (unitWidth * Math.sin(Math.PI / 3)) * j);
                path.close();
                canvas.drawPath(path, mGraddingPaint);
                path.reset();
            }
            canvas.rotate(60);
        }
        path.reset();
        //画数据
        if (datas != null && datas.length != 0) {
            path.moveTo(datas[0] * unitWidth, 0);
            canvas.drawCircle(datas[0] * unitWidth, 0, 5, mPointPaint);
            for (int i = 1; i < datas.length; i++) {
                double angle = Math.PI * i / 3;
                canvas.drawCircle((float) (datas[i] * Math.cos(angle) * unitWidth), (float) (datas[i] * Math.sin(angle)) * unitWidth, 5,
                        mPointPaint);
                path.lineTo((float) (datas[i] * Math.cos(angle) * unitWidth), (float) (datas[i] * Math.sin(angle)) * unitWidth);

                Log.d("=====", "X坐标：  " + (float) (datas[i] * Math.cos(angle) * unitWidth) + "    Y坐标： " + (float) (datas[i] * Math.sin
                        (angle)) * unitWidth);
            }
            path.close();
            canvas.drawPath(path, mDataPaint);
        }

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float textHeight = fontMetrics.descent - fontMetrics.ascent;//获取文本的高度

        //画文本
        for (int i = 0; i < tags.length; i++) {
            double angle = Math.PI * i / 3;
            if (angle > 0 && angle < Math.PI / 2)
                canvas.drawText(tags[i], (float) (6 * Math.cos(angle) * unitWidth), (float) (6 * Math.sin(angle)) *
                        unitWidth + textHeight, mTextPaint);
            else if (angle > Math.PI / 2 && angle < Math.PI) {
                float textWidth = mTextPaint.measureText(tags[i]);
                canvas.drawText(tags[i], (float) (6 * Math.cos(angle) * unitWidth) - textWidth, (float) (6 * Math.sin(angle)) *
                        unitWidth + textHeight, mTextPaint);
            } else if (angle > Math.PI && angle < Math.PI * 3 / 2) {
                float textWidth = mTextPaint.measureText(tags[i]);
                canvas.drawText(tags[i], (float) (6 * Math.cos(angle) * unitWidth) - textWidth, (float) (6 * Math.sin(angle)) *
                        unitWidth - textHeight, mTextPaint);
            } else if (angle > Math.PI * 3 / 2 && angle < Math.PI * 2) {
                canvas.drawText(tags[i], (float) (6 * Math.cos(angle) * unitWidth), (float) (6 * Math.sin(angle)) *
                        unitWidth - textHeight, mTextPaint);
            } else if (angle == 0){
                canvas.drawText(tags[i], (float) (6 * Math.cos(angle) * unitWidth) +10, (float) (6 * Math.sin(angle)) *
                        unitWidth, mTextPaint);
            }else if (angle == Math.PI / 2) {
                float textWidth = mTextPaint.measureText(tags[i]);
                canvas.drawText(tags[i], (float) (6 * Math.cos(angle) * unitWidth) - textWidth / 2, (float) (6 * Math.sin(angle)) *
                        unitWidth + textHeight, mTextPaint);
            } else if (angle == Math.PI) {
                float textWidth = mTextPaint.measureText(tags[i]);
                canvas.drawText(tags[i], (float) (6 * Math.cos(angle) * unitWidth) - textWidth-10, (float) (6 * Math.sin(angle)) *
                        unitWidth, mTextPaint);
            } else if (angle == Math.PI * 3 / 2) {
                float textWidth = mTextPaint.measureText(tags[i]);
                canvas.drawText(tags[i], (float) (6 * Math.cos(angle) * unitWidth) - textWidth / 2, (float) (6 * Math.sin(angle)) *
                        unitWidth, mTextPaint);
            }

        }


    }


}

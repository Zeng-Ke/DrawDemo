package com.zk.drawdemo.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * author: ZK.
 * date:   On 2017/11/29.
 * description:颜色
 */
public class ColorView extends View {


    public ColorView(Context context) {
        super(context);
    }


    public ColorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLUE);
    }
}

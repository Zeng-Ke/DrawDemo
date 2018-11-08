package com.zk.drawdemo.customview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.zk.drawdemo.R;


/**
 * author: ZK.
 * date:   On 2018-11-07.
 */
public class ProgressView extends View {

    private Paint mPregoressPaint;
    private Paint mContainerPaint;
    private Paint mTextPaint;

    private int mWidth;
    private int mHeight;
    private int mProgressHeight;
    private int mValidWidth;
    private float mRadius;

    private static int mMaxSacle = 100; // 最大刻度
    private int mMinScale = 0; // 最小刻度
    private float presentProgress = 5; // 当前进度
    private int mScaleTextSize = 20;

    private int mTextMarginBottom = 10;

    private ValueAnimator mAnimator;
    private int mPaddingLeft;
    private int mPaddingRight;

    private int mPartCount = 10;
    private int mTotalProgress;  //总进度(最大刻度-最小刻度)
    private int mProgressColor;
    private int mBackgroundColor;
    private int mScaleTextColor;
    private boolean mBooleanShowScale; // 是否展示刻度
    private int mPaddingBottom;


    public ProgressView(Context context) {
        super(context, null);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ProgressView);
        mProgressColor = array.getColor(R.styleable.ProgressView_progressColor, Color.RED);
        mBackgroundColor = array.getColor(R.styleable.ProgressView_backgroundColor, Color.GRAY);
        mScaleTextColor = array.getColor(R.styleable.ProgressView_scaleTextColor, Color.BLACK);
        mScaleTextSize = (int) array.getDimension(R.styleable.ProgressView_scaleTextSize, dpToPx(16));
        mPartCount = array.getInteger(R.styleable.ProgressView_partCount, 10);
        mMaxSacle = array.getInteger(R.styleable.ProgressView_maxScale, 100);
        mMinScale = array.getInteger(R.styleable.ProgressView_minScale, 0);
        mTextMarginBottom = array.getInteger(R.styleable.ProgressView_scaleMarginBottom, dpToPx(5));
        mBooleanShowScale = array.getBoolean(R.styleable.ProgressView_showScale, false);
        mProgressHeight = (int) array.getDimension(R.styleable.ProgressView_progressHeight, dpToPx(15));
        initPaint();
    }


    private void initPaint() {
        mPregoressPaint = new Paint();
        mPregoressPaint.setColor(mProgressColor);
        mPregoressPaint.setAntiAlias(true);

        mContainerPaint = new Paint();
        mContainerPaint.setColor(mBackgroundColor);
        mContainerPaint.setAntiAlias(true);

        mTextPaint = new Paint();
        mTextPaint.setColor(mScaleTextColor);
        mTextPaint.setAntiAlias(true);

        mTotalProgress = mMaxSacle - mMinScale;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPaddingLeft = getPaddingLeft();
        mPaddingRight = getPaddingRight();

        mPaddingBottom = getPaddingBottom();


        mWidth = w; // View宽度
        mHeight = h;
        mValidWidth = mWidth - mPaddingLeft - mPaddingRight; // 进度条宽度
        mRadius = mProgressHeight / 2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int measureWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightSpecMode == MeasureSpec.AT_MOST) { // 当高度为wrapContent时，需要重新计算控件高度 ： 进度条高度 + 进度条和字体间距+ 字体高度 + 控件内边距
            measureHeightSize = mProgressHeight + (mBooleanShowScale ? mTextMarginBottom + mScaleTextSize : 0) + getPaddingTop() +
                    getPaddingBottom();
        }
        setMeasuredDimension(measureWidthSize, measureHeightSize);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mWidth <= 0 || mHeight <= 0)
            return;

        if (presentProgress > mTotalProgress || presentProgress < 0)
            return;

        float arcProgress = (mRadius / mWidth) * mTotalProgress;
        canvas.translate(0, mHeight - mPaddingBottom);

        if (presentProgress <= mTotalProgress - arcProgress) {
            drawArc(canvas, mContainerPaint, mPaddingLeft, -mProgressHeight, 2 * mRadius + mPaddingLeft, 0, 90, 180, false);
            drawRect(canvas, mContainerPaint, mRadius + mPaddingLeft, -mProgressHeight, mValidWidth + mPaddingLeft - mRadius, 0);
            drawArc(canvas, mContainerPaint, mValidWidth + mPaddingLeft - 2 * mRadius, -mProgressHeight, mValidWidth + mPaddingLeft, 0,
                    270, 180, false);

            if (presentProgress <= arcProgress) {
                float inClinedAngle = (float) (Math.acos((mRadius - presentProgress / mTotalProgress * mValidWidth) / mRadius) / Math.PI)
                        * 180;
                drawArc(canvas, mPregoressPaint, mPaddingLeft, -mProgressHeight, 2 * mRadius + mPaddingLeft, 0, 180 - inClinedAngle, 2 *
                        inClinedAngle, false);
            } else {
                drawArc(canvas, mPregoressPaint, mPaddingLeft, -mProgressHeight, 2 * mRadius + mPaddingLeft, 0, 90, 180, false);
                drawRect(canvas, mPregoressPaint, mRadius + mPaddingLeft, -mProgressHeight, presentProgress / mTotalProgress * mValidWidth +
                        mPaddingLeft, 0);
            }
        } else {
            drawArc(canvas, mPregoressPaint, mPaddingLeft, -mProgressHeight, 2 * mRadius + mPaddingLeft, 0, 90, 180, false);
            drawRect(canvas, mPregoressPaint, mRadius + mPaddingLeft, -mProgressHeight, mValidWidth + mPaddingLeft - mRadius, 0);
            drawArc(canvas, mPregoressPaint, mValidWidth + mPaddingLeft - 2 * mRadius, -mProgressHeight, mValidWidth + mPaddingLeft, 0,
                    270, 180, false);


            float inClinedAngle = (float) (Math.acos((presentProgress - mTotalProgress + arcProgress) / mTotalProgress * mValidWidth /
                    mRadius) / Math.PI) * 180;

            drawArc(canvas, mContainerPaint, mValidWidth + mPaddingLeft - 2 * mRadius, -mProgressHeight, mValidWidth + mPaddingLeft, 0,
                    360 - inClinedAngle, 2 * inClinedAngle, false);
        }

        if (!mBooleanShowScale)
            return;
        canvas.translate(0, -(mProgressHeight + mTextMarginBottom));
        mTextPaint.setTextSize(mScaleTextSize);


        int interval = mTotalProgress / mPartCount;  // 每截占度数
        int intervalWidth = mValidWidth / mPartCount; // 每截长度

        for (int i = 1; i < mPartCount; i++) { // 画中间刻度
            String text = String.valueOf(mMinScale + i * interval);
            canvas.drawText(text, i * intervalWidth - getTextWidth(text, mTextPaint) / 2 + mPaddingLeft, 0, mTextPaint);
        }
        canvas.drawText(String.valueOf(mMinScale), mPaddingLeft, 0, mTextPaint); //第一个和最后一个刻度要特殊处理
        canvas.drawText(String.valueOf(mMinScale + mPartCount * interval), mValidWidth + mPaddingLeft - getTextWidth(String.valueOf
                        (mPartCount *
                                intervalWidth),
                mTextPaint), 0, mTextPaint);
    }


    /**
     * 设置刻度
     *
     * @param minScale
     * @param maxScale
     * @param partCount
     */
    public void setSacle(int minScale, int maxScale, int partCount) {
        mBooleanShowScale = true;
        if (maxScale <= minScale)
            return;
        mMaxSacle = maxScale;
        mMinScale = minScale;
        mPartCount = partCount;
        invalidate();
    }


    public void setColor(@ColorInt int progressColor, @ColorInt int backgroundColor, @ColorInt int scaleTextColor) {
        mProgressColor = progressColor;
        mBackgroundColor = backgroundColor;
        mScaleTextColor = scaleTextColor;
        invalidate();
    }


    public void setScaleMarginBottom(int marginBottom) {
        mTextMarginBottom = dpToPx(marginBottom);
        invalidate();
    }

    public void setScaleTextSize(float dp) {
        mScaleTextSize = dpToPx(dp);
        invalidate();
    }


    public void setScaleTextSize(int scaleTextSize) {
        mScaleTextSize = scaleTextSize;
        invalidate();
    }

    public void setProgress(float progress) {
        if (progress > mMaxSacle)
            progress = mMaxSacle;
        if (progress < mMinScale)
            progress = mMinScale;
        presentProgress = progress - mMinScale;
        invalidate();
    }


    public void setProgressWithAnimation(float progress) {
        if (progress > mMaxSacle)
            progress = mMaxSacle;
        if (progress < mMinScale)
            progress = mMinScale;
        if (mAnimator != null && mAnimator.isRunning()) {
            mAnimator.end();
        }
        mAnimator = ValueAnimator.ofFloat(presentProgress + mMinScale, progress);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.setDuration(1000);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                presentProgress = (float) animation.getAnimatedValue() - mMinScale;
                invalidate();
            }
        });
        mAnimator.start();

    }


    private void drawArc(Canvas canvas, Paint paint, float left, float top, float right, float bottom, float startAngle,
                         float sweepAngle, boolean useCenter) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawArc(left, top, right, bottom, startAngle, sweepAngle, useCenter, paint);
        } else canvas.drawArc(new RectF(left, top, right, bottom), startAngle, sweepAngle, useCenter, paint);
    }

    private void drawRect(Canvas canvas, Paint paint, float left, float top, float right, float bottom) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRect(left, top, right, bottom, paint);
        } else canvas.drawRect(new RectF(left, top, right, bottom), paint);
    }


    private int getTextWidth(String text, Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect.width();

    }

    private int dpToPx(float dip) {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, displayMetrics);
    }
}

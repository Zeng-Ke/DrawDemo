package com.zk.drawdemo.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


/**
 * From:
 *
 */
public class SearchView extends View {

    private int mWidth;
    private int mHeight;
    private Paint mPaint;


    private Path mPath_search;
    private Path mPathCircle;
    private float[] mPos;
    private STATE mCurrentState = STATE.NONE;
    private float mAnimationValue;
    private long defultDuration = 2000;

    private ValueAnimator.AnimatorUpdateListener mUpdateListener;
    private Animator.AnimatorListener mAnimatorListener;

    private PathMeasure mPathMeasure;

    private ValueAnimator mStartingAnimator;
    private ValueAnimator mSearchAnimator;
    private ValueAnimator mEndingAnimator;
    private int mCount;
    private boolean mIsOver;

    Handler mAnimatorHandler = new Handler() {



        @Override
        public void handleMessage(Message msg) {
            switch (mCurrentState) {
                case STRAT:
                    mIsOver = false;
                    mCurrentState = STATE.SEARCHING;
                    mStartingAnimator.removeAllListeners();
                    mSearchAnimator.start();
                    break;
                case SEARCHING:
                    if (!mIsOver) {
                        mSearchAnimator.start();
                        mCount++;
                        if (mCount > 2)
                            mIsOver = true;
                    }else {
                        mCurrentState = STATE.END;
                        mEndingAnimator.start();
                    }
                    break;
                case END:
                    mCurrentState = STATE.NONE;
                    break;
            }

        }
    };



    public enum STATE {
        NONE,
        STRAT,
        SEARCHING,
        END
    }


    public SearchView(Context context) {
        super(context);
        initAll();
    }


    public SearchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAll();
    }

    private void initAll() {
        initPaint();

        initPath();

        initListener();

        initAnimtor();

        mCurrentState = STATE.STRAT;
        mStartingAnimator.start();

    }


    private void initListener() {

        mUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimationValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        };

        mAnimatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mAnimatorHandler.sendEmptyMessage(0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };

    }


    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    private void initPath() {
        mPath_search = new Path();
        mPathCircle = new Path();

        mPathMeasure = new PathMeasure();
        RectF rectF1 = new RectF(-50, -50, 50, 50);
        mPath_search.addArc(rectF1, 45, (float) 359.9);

        RectF rectF2 = new RectF(-100, -100, 100, 100);
        mPathCircle.addArc(rectF2, 45, (float) 359.9);


        mPos = new float[2];
        mPathMeasure.setPath(mPathCircle, false);
        mPathMeasure.getPosTan(0, mPos, null);
        mPath_search.lineTo(mPos[0], mPos[1]);
        Log.i("===SearchView", "pos=" + mPos[0] + ":" + mPos[1]);


    }

    private void initAnimtor() {
        mStartingAnimator = ValueAnimator.ofFloat(0, 1).setDuration(defultDuration);
        mSearchAnimator = ValueAnimator.ofFloat(0, 1).setDuration(defultDuration);
        mEndingAnimator = ValueAnimator.ofFloat(1, 0).setDuration(defultDuration);

        mStartingAnimator.addUpdateListener(mUpdateListener);
        mSearchAnimator.addUpdateListener(mUpdateListener);
        mEndingAnimator.addUpdateListener(mUpdateListener);

        mStartingAnimator.addListener(mAnimatorListener);
        mSearchAnimator.addListener(mAnimatorListener);
        mEndingAnimator.addListener(mAnimatorListener);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }



    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#0082D7"));
        drawSearch(canvas);
    }

    private void drawSearch(Canvas canvas) {
        canvas.translate(mWidth / 2, mHeight / 2);

        switch (mCurrentState) {
            case NONE:
                canvas.drawPath(mPath_search, mPaint);
                break;
            case STRAT:
                mPathMeasure.setPath(mPath_search, false);
                Path dst1 = new Path();
                mPathMeasure.getSegment(mPathMeasure.getLength() * mAnimationValue, mPathMeasure.getLength(), dst1, true);
                canvas.drawPath(dst1, mPaint);
                break;
            case SEARCHING:
                Path dst2 = new Path();
                mPathMeasure.setPath(mPathCircle,false);
                float stop = mPathMeasure.getLength()*mAnimationValue;
                float start = (float) (stop - (0.5 - Math.abs(mAnimationValue - 0.5))*200);
                mPathMeasure.getSegment(start,stop,dst2,true);
                canvas.drawPath(dst2,mPaint);
                break;
            case END:
                mPathMeasure.setPath(mPath_search,false);
                Path dst3 = new Path();
                mPathMeasure.getSegment(mPathMeasure.getLength()* mAnimationValue,mPathMeasure.getLength(),dst3,true);
                canvas.drawPath(dst3,mPaint);
                break;
        }

    }


}

package org.virtao.togetherdemo.graphics;

import android.graphics.Canvas;
import android.graphics.Paint;

public class BokehPoint {
	int mStartX;
	int mStartY;
	int mGoalX;
	int mGoalY;
	int mNowX;
	int mNowY;
	long mDuration;
	float mRadius;
	Color mStartColor;
	Color mEndColor;
	Color mNowColor;
	long mStartTime;
	long mTimeNow;
	
	Paint mPaint;
	
	boolean isFinished;
	
	//Don't forget initialize density before use BokehPoint.
	private static float logicalDensity = 1;
	
	public static float getLogicalDensity() {
		return logicalDensity;
	}

	public static void setLogicalDensity(float logicalDensity) {
		BokehPoint.logicalDensity = logicalDensity;
	}

	public BokehPoint(int mStartX, int mStartY, int mGoalX, int mGoalY, long mDuration, float mRadius, Color mStartColor, Color mEndColor) {
		this.mStartX = mStartX;
		this.mStartY = mStartY;
		this.mGoalX = mGoalX;
		this.mGoalY = mGoalY;
		this.mDuration = mDuration;
		this.mStartColor = mStartColor;
		this.mEndColor = mEndColor;
		this.mNowX = mStartX;
		this.mNowY = mStartY;
		this.mNowColor = mStartColor;
		this.mRadius = mRadius;
		
		this.mStartTime = 0;
		this.isFinished = false;
		this.mPaint = new Paint();
		this.mPaint.setColor(android.graphics.Color.YELLOW);    
		this.mPaint.setStrokeJoin(Paint.Join.ROUND);    
		this.mPaint.setStrokeCap(Paint.Cap.ROUND);    
		this.mPaint.setStrokeWidth(3);
		this.mPaint.setAntiAlias(true);
	}
	
	public boolean updatePoint() {
		if (mStartTime == 0) {
			mStartTime = System.currentTimeMillis();
			mTimeNow = mStartTime;
			return true; //Don't update at first time.
		} else if ((mTimeNow = System.currentTimeMillis()) > mStartTime + mDuration) {
			if (isFinished) {
				return false; //Point has already moved to goal position.
			} else {
				mTimeNow = mStartTime + mDuration; //This is the last time to move.
				isFinished = true;
			}
		}

		double ratio = (double)(mTimeNow - mStartTime) / mDuration;
		mNowX = (int)((mGoalX - mStartX) * ratio + mStartX);
		mNowY = (int)((mGoalY - mStartY) * ratio + mStartY);
		//Color.evolution(ratio, mNowColor, mStartColor, mEndColor);
		mNowColor = mStartColor;

		return true;
	}
	
    public void doDraw(Canvas canvas) {
    	mPaint.setColor(this.mNowColor.getColor());
    	canvas.drawCircle(px2dp(this.mNowX), px2dp(this.mNowY), mRadius, mPaint);	
    }
    
    private int dp2px(float dp) {
    	return (int) Math.ceil(dp * logicalDensity);
    }
    private float px2dp(int pixel) {
    	return (float) pixel / (float)logicalDensity;
    }
	

}

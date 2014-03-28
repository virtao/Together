Each object has its own drawing functions. Sample code:

```
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
```

We use a separate thread to draw graphics. But still a little slow, maybe we should use OpenGL. Sample code:

```
		public void run() {
			while (mRun) {
				Canvas c = null;
				try {
					if (mMode == STATE_RUNNING) {
						c = mSurfaceHolder.lockCanvas(null);
						synchronized (mSurfaceHolder) {
							updateBokeh();
							doDraw(c);
						}
					} else {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
						}
					}
				} finally {
					// do this in a finally so that if an exception is thrown
					// during the above, we don't leave the Surface in an
					// inconsistent state
					if (c != null) {
						mSurfaceHolder.unlockCanvasAndPost(c);
					}
				}
			}
		}
```


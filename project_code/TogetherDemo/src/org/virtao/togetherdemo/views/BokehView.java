package org.virtao.togetherdemo.views;

import java.lang.Thread.State;
import java.util.Iterator;
import java.util.LinkedList;

import org.virtao.togetherdemo.graphics.BokehPoint;
import org.virtao.togetherdemo.utils.Utils;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BokehView extends SurfaceView implements SurfaceHolder.Callback {

	BokehThread mThread;

	int viewHeight = 0;
	int viewWidth = 0;

	public int getViewHeight() {
		return viewHeight;
	}

	public int getViewWidth() {
		return viewWidth;
	}

	public BokehView(Context context, AttributeSet attrs) {
		super(context, attrs);

		BokehPoint
				.setLogicalDensity(context.getResources().getDisplayMetrics().density);

		// register our interest in hearing about changes to our surface
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);

		// create thread only; it's started in surfaceCreated()
		mThread = new BokehThread(holder, context, new Handler() {
			@Override
			public void handleMessage(Message m) {

			}
		});

		setFocusable(true); // make sure we get key events
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		this.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		mThread.setSurfaceSize(width, height);
		viewHeight = height;
		viewWidth = width;
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		this.stop();
	}

	public void addBokehPoint(BokehPoint e) {
		mThread.addBokehPoint(e);
	}

	public void stop() {
		mThread.stopThread();
	}

	public void start() {
		mThread.startThread();
	}

	class BokehThread extends Thread {

		SurfaceHolder mSurfaceHolder;
		Handler mHandler;
		Context mContext;

		LinkedList<BokehPoint> mBokehPoints;

		boolean mRun;

		int mCanvasWidth = 1;
		int mCanvasHeight = 1;

		// Running status mode
		public static final int STATE_PAUSE = 0;
		public static final int STATE_RUNNING = 1;
		int mMode;

		public BokehThread(SurfaceHolder surfaceHolder, Context context,
				Handler handler) {

			mSurfaceHolder = surfaceHolder;
			mHandler = handler;
			mContext = context;
			mBokehPoints = new LinkedList<BokehPoint>();
			mRun = false;
			mMode = STATE_RUNNING;

		}

		private void updateBokeh() {
			for (Iterator<BokehPoint> iter = mBokehPoints.iterator(); iter
					.hasNext();) {
				BokehPoint e = iter.next();
				if (e.updatePoint()) {

				} else {
					iter.remove();
				}
			}

			for (int i = 0; i < 500 - mBokehPoints.size(); i++) {
				mBokehPoints.add(Utils.randomBokeh());
			}
		}

		private void doDraw(Canvas canvas) {
			canvas.drawColor(android.graphics.Color.WHITE);
			for (Iterator<BokehPoint> iter = mBokehPoints.iterator(); iter
					.hasNext();) {
				BokehPoint e = iter.next();
				e.doDraw(canvas);
			}
		}

		@Override
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

		public void setRun(boolean mRun) {
			this.mRun = mRun;
		}

		public void addBokehPoint(BokehPoint e) {
			synchronized (mSurfaceHolder) {
				mBokehPoints.add(e);
			}
		}

		public void setSurfaceSize(int width, int height) {
			// synchronized to make sure these all change atomically
			synchronized (mSurfaceHolder) {
				mCanvasWidth = width;
				mCanvasHeight = height;
			}
		}

		public void stopThread() {
			boolean retry = true;
			this.setRun(false);
			while (retry) {
				try {
					this.join();
					retry = false;
				} catch (InterruptedException e) {
				}
			}
		}

		public void startThread() {
			if (mRun == false) {
				this.setRun(true);
				this.start();
			}
		}
	}

}

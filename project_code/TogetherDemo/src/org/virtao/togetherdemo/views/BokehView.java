package org.virtao.togetherdemo.views;

import java.util.Iterator;
import java.util.LinkedList;

import org.virtao.togetherdemo.graphics.BokehPoint;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BokehView extends SurfaceView implements SurfaceHolder.Callback {

	BokehThread mThread;
	
	public BokehView(Context context, AttributeSet attrs) {
		super(context, attrs);

		BokehPoint.setLogicalDensity(context.getResources().getDisplayMetrics().density);		
		
        // register our interest in hearing about changes to our surface
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        // create thread only; it's started in surfaceCreated()
        mThread = new BokehThread(holder, context, new Handler() {
            @Override
            public void handleMessage(Message m) {
                //mStatusText.setVisibility(m.getData().getInt("viz"));
                //mStatusText.setText(m.getData().getString("text"));
            }
        });

        setFocusable(true); // make sure we get key events
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
        mThread.setRun(true);
        mThread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		mThread.setSurfaceSize(width, height);
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        mThread.setRun(false);
        while (retry) {
            try {
                mThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }		
	}
	
	public void addBokehPoint(BokehPoint e) {
		mThread.addBokehPoint(e);
	}
	

	
	class BokehThread extends Thread {
		
		SurfaceHolder mSurfaceHolder;
		Handler mHandler;
		Context mContext;
		
		LinkedList<BokehPoint> mBokehPoints;
		
		boolean mRun;
		
        int mCanvasWidth = 1;
        int mCanvasHeight = 1;
        
		//Running status mode
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
        	for (Iterator<BokehPoint> iter = mBokehPoints.iterator(); iter.hasNext(); ) {
        		BokehPoint e = iter.next();
        		if (e.updatePoint()) {
        			
        		} else {
        			iter.remove();
        		}	
        	}
        }
        
        private void doDraw(Canvas canvas) {
        	canvas.drawColor(android.graphics.Color.TRANSPARENT, android.graphics.PorterDuff.Mode.CLEAR);
        	for (Iterator<BokehPoint> iter = mBokehPoints.iterator(); iter.hasNext(); ) {
        		BokehPoint e = iter.next();
        		e.doDraw(canvas);
        	}        	
        }
        
        
		@Override
		public void run() {
            while (mRun) {
                Canvas c = null;
                try {
                    c = mSurfaceHolder.lockCanvas(null);
                    synchronized (mSurfaceHolder) {
                        if (mMode == STATE_RUNNING) updateBokeh();
                        doDraw(c);
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
	}
	

}

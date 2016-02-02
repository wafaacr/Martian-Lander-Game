package martian.lander;

/*
 *	Author : 1420543 Wafaa Che Rose
 *	Class : SurfaceAnimate.java
 * */
import martian.lander.R;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SurfaceAnimate extends SurfaceView implements SurfaceHolder.Callback, Runnable {
	
	// component
	Thread animation;
	boolean running;
	Canvas canvas;
	Paint paint;
	int x = 100;	// initial coordinate of space craft
	int y2 = 100;	// initial coordinate of space craft
	
	int y;// = 20;
	int x2 = 100;  
	int degree = 20;
	int Cx = 75;
	int fuel = 100; // initial fuel of space craft
	int score = 0; // initial score of the game
	
	// Source : http://mobile.dzone.com/articles/add-sound-effects-your-android
	SoundPool sp;	// to play sound effect when space craft crashes
	int expl; 		// sound effect from file
	
	public static final double INITIAL_TIME = 3.5;
	static final int REFRESH_RATE = 20;
	static final int GRAVITY = 1;
	double t = INITIAL_TIME;
	Path path;
	
	// to draw terrain
	int xcor[] = { 0, 200, 190, 218, 260, 275, 298, 309, 327, 336, 368, 382,
			448, 462, 476, 498, 527, 600, 600, 0, 0 };
	int ycor[] = { 616, 540, 550, 605, 605, 594, 530, 520, 520, 527, 626, 636,
			636, 623, 535, 504, 481, 481, 750, 750, 616 };
	
	public SurfaceAnimate(Context context) {
		super(context);
		getHolder().addCallback(this);
		setZOrderOnTop(true);
		getHolder().setFormat(PixelFormat.TRANSLUCENT);
		sp = new SoundPool(10,AudioManager.STREAM_MUSIC,0);
		expl = sp.load(context,R.raw.crash,1);
		// TODO Auto-generated constructor stub
	}
	
	public SurfaceAnimate(Context context,AttributeSet attrs)
	{
		super(context,attrs);
		getHolder().addCallback(this);
		setZOrderOnTop(true);
		getHolder().setFormat(PixelFormat.TRANSLUCENT);
		sp = new SoundPool(10,AudioManager.STREAM_MUSIC,0);
		expl = sp.load(context,R.raw.crash,1);
	}
	public SurfaceAnimate(Context context,AttributeSet attrs,int defStyle){
		super(context,attrs,defStyle);
		getHolder().addCallback(this);		
		setZOrderOnTop(true);
		getHolder().setFormat(PixelFormat.TRANSLUCENT);
		sp = new SoundPool(10,AudioManager.STREAM_MUSIC,0);
		expl = sp.load(context,R.raw.crash,1);
	}
	
	// set up terrain path
	public void setUpPath()
	{
		path = new Path();

		for (int i = 0; i < xcor.length; i++) {
			path.lineTo(xcor[i], ycor[i]);
		}

	}

	@Override
	public void run() {
		setUpPath();
		canvas = new Canvas();
		while(running){
			SurfaceHolder holder = getHolder();
			
			synchronized(holder){
				canvas = holder.lockCanvas();
				paint = new Paint();	
				paint.setColor(Color.WHITE); // colour of terrain
				canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);	// refresh the canvas after draw
				y2 = y2+10; 
				canvas.drawPath(path, paint);			
				checkBoundary(); // check for collision detection
				}
			
			try{
				Thread.sleep(REFRESH_RATE);
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
			holder.unlockCanvasAndPost(canvas);
		}
	}

	// check collision detection when landing
	private void checkBoundary() {
		
		boolean bottomLeft = contains(xcor, ycor, x-25, y2+25);
		boolean bottomRight = contains(xcor, ycor, x+25, y2+25);
		
			// if there is ground
			if (bottomLeft || bottomRight)
			{
				canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.rocket), x, y2-70, null);

				// if fuel is more than 0, space craft will not crash but bouncing
				if(fuel > 0)
				{
					y2 = y2 - 30;
					score += 10;
				}
				else // space craft crashes
				{
					setExplosion();
				}
			}
			else // no ground, keep drawing
			{
				canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.rocket), x, y2-70, null);
			}
	}

	// to check collision detection
	public boolean contains(int[] xcor, int[] ycor, double x0, double y0) {
		int crossings = 0;

		for (int i = 0; i < xcor.length - 1; i++) {
			int x1 = xcor[i];
			int x2 = xcor[i + 1];

			int y1 = ycor[i];
			int y2 = ycor[i + 1];

			int dy = y2 - y1;
			int dx = x2 - x1;

			double slope = 0;
			if (dx != 0) {
				slope = (double) dy / dx;
			}

			boolean cond1 = (x1 <= x0) && (x0 < x2); // is it in the range?
			boolean cond2 = (x2 <= x0) && (x0 < x1); // is it in the reverse
														// range?
			boolean above = (y0 < slope * (x0 - x1) + y1); // point slope y - y1

			if ((cond1 || cond2) && above) {
				crossings++;
			}
		}
		return (crossings % 2 != 0); // even or odd
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		animation = new Thread(this);
		running = true;
		animation.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		boolean retry = true;
		running = false;
		while(retry){
			try{
				animation.join();
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	// fire left thruster
	public void moveLeft() {
		// TODO Auto-generated method stub
		if(x >= 20 && x < 410)
		{
			if(x > 300)
				x = x - 20;
			else
				x = x - 10;
		}
		else
			x = x - 0;
		fuel = fuel - 5;
		drawFire(x+25, y2+30);
	}

	// fire right thruster
	public void moveRight() {
		if(x >= 20 && x < 410)
		{
			if(x > 300)
				x = x + 10;
			else
				x = x + 20;
		}
		else 
			x = x - 0;
		fuel = fuel - 5;
		drawFire(x-5, y2+30);
	}

	// fire main rocket
	public void moveUp() {
		// TODO Auto-generated method stub
		if(y2 > 0 && y2 < 570)
		{
			if(y2 > 400)
				y2 = y2 - 300;
			else
				y2 = y2 - 100;
			//if(y2 > 80)
				//y2 = y2 - 200;
		}
		else
			y2 = y2 - 0;
		fuel = fuel - 5;
		drawFire(x+10,y2+30);
	}

	// draw fire when thruster is fired
	public void drawFire(int i, int j){
		canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.fuel), i, j, null);
	}
	
	// draw explosion and stop game
	public void setExplosion() {
		// TODO Auto-generated method stub
		canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.explosion), x-10, y2-70, null);
		sp.play(expl, 1.0f, 1.0f, 0, 0, 1.5f);
		running = false;
	}
	
	// return space craft y coordinate
	public int checkSpaceY() {
		return y2;
	}
	
	// return space craft x coordinate
	public int checkSpaceX() {
		return x;
	}

	
	//public void checkFuel(int spaceFuel) {
	//	fuel = spaceFuel;
	//}
	
	// return game score
	public int getScore(){
		return score;
	}
	
	// return space fuel
	public int getFuel() {
		return fuel;
	}

	public void reset() {
		// TODO Auto-generated method stub
		running = true;
		x = 100;
		y2 = 100;
		score = 0;
		fuel = 100;
	}
}

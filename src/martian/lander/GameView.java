package martian.lander;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class GameView extends View {
	//private Bitmap bmp;
	private Canvas canvas;
	//private Bitmap canvasBitmap;	// canvas bitmap
	private Paint paint;
	int height,width;
	
	public GameView(Context context) {
		super(context);
		setup();
	}
	public GameView(Context context, AttributeSet attrs) {
	    super(context, attrs);
	    setup();	
	}

	public GameView(Context context, AttributeSet attrs, int defStyle) {
	    super(context, attrs, defStyle);
	   setup();
	}
	private void setup() {
		//bmp = BitmapFactory.decodeResource(getResources(), R.drawable.everest); 
		//canvasBitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
		//canvas = new Canvas(canvasBitmap);
		canvas = new Canvas();
		height = canvas.getHeight();
		width = canvas.getWidth();
		paint = new Paint();
		paint.setColor(Color.GREEN);
	}
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		//canvas.drawBitmap(canvasBitmap,0,0,paint);
		// draw rectangle first
		canvas.drawRect(10, 50, 50, 50, paint);
		
		// draw triangle next
		//canvas.drawCircle(width+30, height/2, 50, paint);
	}

}

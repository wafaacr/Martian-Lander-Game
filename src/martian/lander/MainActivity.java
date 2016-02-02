package martian.lander;

/*
 *	Author : 1420543 Wafaa Che Rose
 *	Class : SurfaceAnimate.java
 * */
import martian.lander.R;
import android.support.v7.app.ActionBarActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements OnClickListener {
	//component
	private SurfaceAnimate surfaceAnimate;
	private ImageButton buttonUp;
	private ImageButton buttonLeft;
	private ImageButton buttonRight;
	private ImageButton buttonRefresh;
	private ImageButton buttonExit;
	private TextView fuel;
	private TextView score;
	private TextView sCx;
	private TextView sCy;
	
	// variables
	private int spaceFuel = 100; // space fuel
	private int gameScore = 0; // score
	private int x = 0; // space craft coord
	private int y = 0; // space craft coord
	
	private MediaPlayer mp; // background music
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mp = MediaPlayer.create(getApplicationContext(),R.raw.spaceship);
		
		surfaceAnimate = (SurfaceAnimate)findViewById(R.id.surface_view);
		//gameView = (GameView)findViewById(R.id.game_view);
		buttonUp = (ImageButton)findViewById(R.id.up);
		buttonLeft = (ImageButton)findViewById(R.id.left);
		buttonRight = (ImageButton)findViewById(R.id.right);
		buttonRefresh = (ImageButton)findViewById(R.id.refresh);
		buttonExit = (ImageButton)findViewById(R.id.exit);
		
		buttonUp.setOnClickListener(this);
		buttonLeft.setOnClickListener(this);
		buttonRight.setOnClickListener(this);
		buttonRefresh.setOnClickListener(this);
		buttonExit.setOnClickListener(this);
		
		fuel = (TextView)findViewById(R.id.fuelLeft);
		score = (TextView)findViewById(R.id.totScore);
		
		sCx = (TextView)findViewById(R.id.cx);
		sCy = (TextView)findViewById(R.id.cy);
		
		fuel.setText(Integer.toString(spaceFuel));
		score.setText(Integer.toString(gameScore));

		mp.start(); // start playing background music
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
			if(v.getId() == R.id.exit)
			{
				mp.stop();
				finish();
			}
			else if(v.getId() == R.id.refresh)
			{
				surfaceAnimate.reset();
			}
			if(spaceFuel > 0) // while spaceFuel > 0 
			{
				if(v.getId() == R.id.left) // fire right thruster
				{
					surfaceAnimate.moveRight();
					updateFuel();	 // update space fuel
					updateCoord();   // update space craft coordinates
				}
				else if(v.getId() == R.id.right) // fire left thruster
				{
					surfaceAnimate.moveLeft();
					updateFuel();	 // update space fuel
					updateCoord();   // update space craft coordinates
				}
				else if(v.getId() == R.id.up) // fire up main rocket
				{
					surfaceAnimate.moveUp();
					updateFuel();	 // update space fuel
					updateCoord();   // update space craft coordinates
				}
				updateScore();	// update score
			}
			else
			{
				if(y >= 500)
					surfaceAnimate.setExplosion();	// set explosion 
				mp.stop();	// stop playing background music
			}
	}

	// get fuel from surfaceAnimate class and set to text view
	private void updateFuel() {
			spaceFuel = surfaceAnimate.getFuel();
			fuel.setText(Integer.toString(spaceFuel));
	}
	
	// get coordinates from surfaceAnimate class and set to text view
	private void updateCoord(){
		x = surfaceAnimate.checkSpaceX();
		y = surfaceAnimate.checkSpaceY();
		
		sCx.setText(Integer.toString(x));
		sCy.setText(Integer.toString(y));
	}
	
	// get score from surfaceAnimate class and set to text view
	private void updateScore(){
		gameScore = surfaceAnimate.getScore(); // get score
		score.setText(Integer.toString(gameScore)); 
	}
}

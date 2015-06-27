package com.vet.vet_app;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class VideoActivity extends Activity implements OnClickListener {

	static String chosenOccupation;
	StatusDatabase dbModifier1;
	int position;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.Change_Occupation:
			Intent intent = new Intent(this, MainPage.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			break;
		}
		
		return true;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		dbModifier1 = new StatusDatabase(this);

		Intent intent = getIntent();
		chosenOccupation = intent.getStringExtra("spinner text");

		Log.d("VideoActivity", "Updating");

		dbModifier1.update(1);

		setContentView(R.layout.activity_video);

		TextView title = (TextView) findViewById(R.id.textView1);
		TextView likeCounter = (TextView) findViewById(R.id.textView3);

		Button like = (Button) findViewById(R.id.button1);
		Button details = (Button) findViewById(R.id.button2);

		title.setText(chosenOccupation);
		like.setOnClickListener(this);
		details.setOnClickListener(this);

		Cursor c = dbModifier1.likeQuery();
		
		c.moveToFirst();
		
		int likeCount = c.getInt(c.getColumnIndex(StatusDatabase.KEY_LIKES));
		likeCounter.setText(Integer.toString(likeCount));

		VideoView videoView = (VideoView) findViewById(R.id.videoView);

		MediaController mediaController = new MediaController(this);
		mediaController.setAnchorView(videoView);

		Uri video = null;

		if (chosenOccupation == "Electronic Technician") {
			video = Uri
					.parse("http://www.berufe.tv/BA/ausbildung/?filmID=1000041");
		} else if (chosenOccupation == "Mechanic") {
			video = Uri
					.parse("http://www.berufe.tv/BA/ausbildung/?filmID=1000008");
		}
		videoView.setMediaController(mediaController);
		videoView.setVideoURI(video);
		videoView.setVisibility(View.VISIBLE);

		try {
			videoView.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Log.d("VideoActivity", "Passed All");
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			TextView likeCounter = (TextView) findViewById(R.id.textView3);
			String needed = likeCounter.getText().toString();
			likeCounter.setText(Integer.toString(Integer.parseInt(needed) + 1));
			dbModifier1.update(2);
			Log.d("VideoAcitivty", "Like Updated");
			break;
		case R.id.button2:
			dbModifier1.update(3);
			Log.d("VideoAcitivty", "Viewed Updated");
			Intent intent = new Intent(VideoActivity.this, CoverActivity.class);
			intent.putExtra("occupation chosen", chosenOccupation);
			startActivity(intent);
			break;
		}
	}

}

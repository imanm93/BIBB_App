package com.vet.vet_app;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MainPage extends Activity implements OnItemSelectedListener {

	public boolean Result;
	public AsyncTask<String, String, Boolean> checkOnline;
	StatusDatabase dbModifier;
	SimpleCursorAdapter adapterCursor;
	SQLiteDatabase db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_occupation);
				
		dbModifier = new StatusDatabase(this);
		
		Spinner chosenOccupation = (Spinner) findViewById(R.id.spinnerChooseOccupation);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.chooseOcuppation,
				android.R.layout.simple_spinner_item);

		chosenOccupation.setAdapter(adapter);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		chosenOccupation.setPrompt("Choose your preferred Occupation ...");
		chosenOccupation.setOnItemSelectedListener(this);
				
		ListView timeline = (ListView) findViewById(R.id.listTimeline);
		Cursor c = dbModifier.query();
			
		String[] from = {StatusDatabase.KEY_OCCUPATION, StatusDatabase.KEY_SELECTED, StatusDatabase.KEY_LIKES, StatusDatabase.KEY_DETAILVIEWED};
		int[] to = {R.id.textOccupationName, R.id.textOccupationSelected, R.id.textOccupationLikes, R.id.textOccupationDeatilViews};
		
		adapterCursor = new SimpleCursorAdapter(this, R.layout.row_database, c, from, to, RESULT_OK);
		timeline.setAdapter(adapterCursor);
		
		checkOnline = new CheckOnline().execute("google.com");

	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		
		if (checkOnline.getStatus() == AsyncTask.Status.FINISHED) {

			if (Result) {

				if (arg2 != 0) {
					Intent intent = new Intent(this, VideoActivity.class);
					intent.putExtra("spinner text", arg0
							.getItemAtPosition(arg2).toString());
					startActivity(intent);
				}

			} else {
				if (arg2 != 0) {
					Toast.makeText(
							this,
							"No Internet Connection. Please Check Network Settings and Restart Appliction.",
							Toast.LENGTH_LONG).show();
				}
			}

		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

	//
	// Check Internet Connection
	//

	private class CheckOnline extends AsyncTask<String, String, Boolean> {

		private ProgressDialog progressDialog;

		@Override
		protected Boolean doInBackground(String... arg0) {

			try {
				InetAddress.getByName(arg0[0]).isReachable(3);
				return true;
			} catch (UnknownHostException e) {
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}

		}

		@Override
		protected void onPreExecute() {

			progressDialog = new ProgressDialog(MainPage.this);
			progressDialog.setCancelable(false);
			progressDialog.setMessage("Checking Network Connection");
			progressDialog.show();

		}

		@Override
		protected void onPostExecute(Boolean result) {

			Result = result;
			
			if (result) {
			} else {
				Toast.makeText(
						MainPage.this,
						"No Internet Connection. Please Check Network Settings and Restart Appliction.",
						Toast.LENGTH_LONG).show();
			}
			
			progressDialog.dismiss();

		}

	}

}

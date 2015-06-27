package com.vet.vet_app;

import java.util.ArrayList;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class CoverActivity extends FragmentActivity {

	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	static String chosenOccupation;
	static String chosenItem = null;
	ProgressDialog progressDialog1;
	static int counter1 = 0;
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		chosenOccupation = intent.getStringExtra("occupation chosen");
		
		setContentView(R.layout.activity_cover);
		
		if (chosenOccupation.equals("Electronic Technician")) {
			
			chosenItem = "Electronic";
			
		} else {
			
			chosenItem = chosenOccupation;
			
		}
		
		// Create the adapter that will return a fragment for each of the three
		// primary sections
		// of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

	}

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



	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the primary sections of the app.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {
		
		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			switch (i) {
			case 1:
				Fragment fragment = new JobsFragment();
				return fragment;
			case 2:
				Fragment fragment1 = new TCFragment();
				return fragment1;
			case 0:
				Fragment fragment2 = new InfoFragment();
				return fragment2;
			}
			return null;
		}

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 1:
				return getString(R.string.title_section1).toUpperCase();
			case 2:
				return getString(R.string.title_section2).toUpperCase();
			case 0:
				return getString(R.string.title_section3).toUpperCase();
			}
			return null;
		}
	}


	public class JobsFragment extends Fragment {
		
		Document finalDoc;
		View jobResult;
		
		public JobsFragment() {
			
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
						
			jobResult = inflater.inflate(R.layout.fragment_jobs, container, false);			
			
			new ParsedJobHtml().execute(chosenItem);
			
			return jobResult;
			
		}

//		
//		Populates List in Fragment 		
//		
		private ArrayList<JobSearchResults> GetJobSearchResult() {
						
			ListPopulator listPopulator = new ListPopulator();
						
			ArrayList<JobSearchResults> result = new ArrayList<JobSearchResults>();
			
			Document finalJobDoc = finalDoc;
						
			String[] jobList = listPopulator.getJobTitles(finalJobDoc);			
						
			String[] jobDescriptionList = listPopulator.getJobDescription(finalJobDoc);
		    
			//			Only for use to Test Code at the Office
			//			String[] jobList = {"JobTitle1", "Job Title 2", "Job Title 3", "Job Title 4", "Job Title 5"};
			//			String[] jobDescriptionList = {"Job Description 1", "Job Description 2", "Job Description 3", "Job Description 4", "Job Description 5"};
			
			for (int x = 0; x < jobList.length; x++) {
				
				JobSearchResults item = new JobSearchResults();
				
				item.setJobTitle(jobList[x]);		    
				item.setJobDescription(jobDescriptionList[x]);
								
				result.add(item);
				
			}
			
			return result;
		
		} 
		
//		
//		Does the networking in an AsyncTask
//
		
		public class ParsedJobHtml extends AsyncTask<String, String, String> {
			
			@Override
			protected String doInBackground(String... params) {
				GetJobsInfoApplication jobApp = new GetJobsInfoApplication();		
				finalDoc = jobApp.getParsedHtml(jobApp.getCompleteJobSiteHtml(params[0]));
				String result = params[0];
				return result;
			}

			@Override
			protected void onPostExecute(String result) {

				ArrayList<JobSearchResults> jobResults = GetJobSearchResult();
				ListView listJobs = (ListView) jobResult.findViewById(R.id.listJobs);
				listJobs.setAdapter(new VETCustomJobListAdapter(JobsFragment.this, jobResults));
				progressDialog1.dismiss();
				
			}
			
		}
		
	}
	
	public class TCFragment extends Fragment {
		
		Document finalDoc;
		View tcResult;
		ProgressDialog progressDialog;
		
		public TCFragment() {
			
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			tcResult = inflater.inflate(R.layout.fragment_tc, container, false);			
			
			new ParsedTCHtml().execute(chosenItem);
			
			return tcResult;
		
		}
		
//
//		Populating List
//
		
		private ArrayList<TrainingCourseSearchResults> GetTrainingCourseSearchResult() {

//	 		Actual Code to get and display Data from the Internet

			ListPopulator listPopulator = new ListPopulator();
			ArrayList<TrainingCourseSearchResults> result = new ArrayList<TrainingCourseSearchResults>();
			
			Document finalTCDoc = finalDoc;
			
		    String[] trainingcourseDescriptionList = listPopulator.getTrainingCourseDescription(finalTCDoc);
		    
//			Only for use to Test Code at the Office
		    
//			String[] trainingcourseTitleList = {"TC Title 1", "TC Title 2", "TC Title 3", "TC Title 4", "TC Title 5", "TC Title 6", "TC Title 7", "TC Title 8", "TC Title 9", "TC Title 10"};
			
			for (int x = 0; x < trainingcourseDescriptionList.length; x++) {
								
				TrainingCourseSearchResults item = new TrainingCourseSearchResults();
				
				item.setTrainingCourseTitle(trainingcourseTitleList[x]);
				item.setTrainingCourseDescription(trainingcourseDescriptionList[x]);
				
				result.add(item);
							
			}
			
			return result;

		}
		
//
//		AsyncTask		
//	
		
		public class ParsedTCHtml extends AsyncTask<String, String, String> {
			
			@Override
			protected String doInBackground(String... params) {
				GetJobsInfoApplication jobApp = new GetJobsInfoApplication();	
				finalDoc = jobApp.getParsedHtml(jobApp.getCompleteTrainingSiteHtml(params[0]));
				String result = params[0];
				return result;
			}

			@Override
			protected void onPostExecute(String result) {

				ArrayList<TrainingCourseSearchResults> trainingCourseResults = GetTrainingCourseSearchResult();
				ListView listTC = (ListView) tcResult.findViewById(R.id.listTC);
				listTC.setAdapter(new VETCustomTrainingCourseListAdapter(TCFragment.this, trainingCourseResults));
				progressDialog.dismiss();
				
			}

			@Override
			protected void onPreExecute() {

				progressDialog = new ProgressDialog(CoverActivity.this);
				progressDialog.setCancelable(false);
				progressDialog.setMessage("Getting Data ...");
				progressDialog.show();
				
			}
			
		}

		
	}


	public class InfoFragment extends Fragment {
		
		Document finalDoc;
		
		public InfoFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
			View result = inflater.inflate(R.layout.fragment_information, container, false);
			TextView OccupationTitle = (TextView) result.findViewById(R.id.textOccupationTitle);
			
			OccupationTitle.setText(chosenOccupation);
			
			new ParsedInfoHtml().execute(chosenItem);
			
			if (counter1 == 0) {
				counter1 = 1;
			}
			
			return result;
			
		}
		
		public class ParsedInfoHtml extends AsyncTask<String, String, Document> {

			@Override
			protected Document doInBackground(String... params) {
				GetJobsInfoApplication jobApp = new GetJobsInfoApplication();	
				finalDoc = jobApp.getParsedHtml(jobApp.getInfoSiteHtml(params[0]));
				return finalDoc;
			}

			@Override
			protected void onPostExecute(Document result) {

				TextView description = (TextView) findViewById(R.id.textOccupationDescription);
				TextView occSkill = (TextView) findViewById(R.id.textOccupationalSkill);
				String tempSkill = "";
				
				Elements tempEDescription = result.select("div#Body").select("div.width1").select("div.minwidth1").select("div.layout").select("div.container").select("div#Content").select("div#ContentBorder").select("div.article").get(3).select("div.post").select("p");
				String tempDescription = tempEDescription.text().substring(0, 300) + "...";
				description.setText(tempDescription);
				
				Elements tempEPart1 = result.select("div#Body").select("div.width1").select("div.minwidth1").select("div.layout").select("div.container").select("div#Content").select("div#ContentBorder").select("div.article").get(4).select("div.post").select("p");
				tempSkill = tempEPart1.text();
				
				Elements tempEPart2 = result.select("div#Body").select("div.width1").select("div.minwidth1").select("div.layout").select("div.container").select("div#Content").select("div#ContentBorder").select("div.article").get(4).select("div.post").select("ul").select("li");
								
				for (Element skill : tempEPart2) {
					tempSkill = tempSkill + "\n\n    --" + skill.text();
				}
				
				occSkill.setText(tempSkill);
				
				// If loaded the first time then the dialog will not be dismissed here
				
				if (counter1 == 1) {
				progressDialog1.dismiss();
				}
				
			}

			@Override
			protected void onPreExecute() {
				
				progressDialog1 = new ProgressDialog(CoverActivity.this);
				progressDialog1.setCancelable(false);
				progressDialog1.setMessage("Getting Data ...");
				progressDialog1.show();
			
			}
			
		}
		
	}

}

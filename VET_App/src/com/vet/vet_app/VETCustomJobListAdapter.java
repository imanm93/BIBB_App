package com.vet.vet_app;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vet.vet_app.CoverActivity.JobsFragment;

public class VETCustomJobListAdapter extends BaseAdapter {

	private static ArrayList<JobSearchResults> jobArrayList;
	private LayoutInflater mInflater;
	
	public VETCustomJobListAdapter(JobsFragment jobFragment, ArrayList<JobSearchResults> jobSearchResult) {
		jobArrayList = jobSearchResult;
		Bundle args = jobFragment.getArguments();
		mInflater = jobFragment.getLayoutInflater(args);
	}
	
	@Override
	public int getCount() {
		return jobArrayList.size();
	}

	@Override
	public Object getItem(int position) {
		return jobArrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup group) {
		
		ViewHolder holder;
		
		if (convertView == null) {
			
			convertView = mInflater.inflate(R.layout.row_joblistview, null);
			
			holder = new ViewHolder();
			holder.jobTitle = (TextView) convertView.findViewById(R.id.textTitle);
			holder.jobDescription = (TextView) convertView.findViewById(R.id.textDescription);
		
			convertView.setTag(holder);
		
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.jobTitle.setText(jobArrayList.get(position).getJobTitle());
		holder.jobDescription.setText(jobArrayList.get(position).getJobDescription());
		
		return convertView;
		
	}

	static class ViewHolder {
		TextView jobTitle;
		TextView jobDescription;
	}
	
}

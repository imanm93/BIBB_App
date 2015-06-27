package com.vet.vet_app;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vet.vet_app.CoverActivity.TCFragment;

public class VETCustomTrainingCourseListAdapter extends BaseAdapter {

	private static ArrayList<TrainingCourseSearchResults> trainingcourseArrayList;
	private LayoutInflater mInflater;

	public VETCustomTrainingCourseListAdapter(TCFragment tcFragment,
			ArrayList<TrainingCourseSearchResults> trainingcourseSearchResult) {
		
		trainingcourseArrayList = trainingcourseSearchResult;
		Bundle args = tcFragment.getArguments();
		mInflater = tcFragment.getLayoutInflater(args);
		
	}

	@Override
	public int getCount() {
		return trainingcourseArrayList.size();
	}

	@Override
	public Object getItem(int position) {
		return trainingcourseArrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup group) {
		
		ViewHolder holder;
		
		if (convertView == null) {
			
			convertView = mInflater.inflate(R.layout.row_trainingcourselistview, null);
			
			holder = new ViewHolder();
			holder.trainingCourseTitle = (TextView) convertView.findViewById(R.id.textTitle1);
			holder.trainingCourseDescription = (TextView) convertView.findViewById(R.id.textDescription1);
		
			convertView.setTag(holder);
		
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.trainingCourseTitle.setText(trainingcourseArrayList.get(position).getTrainingCourseTitle());
		holder.trainingCourseDescription.setText(trainingcourseArrayList.get(position).getTrainingCourseDescription());
		
		return convertView;
		
	}

	static class ViewHolder {
		TextView trainingCourseTitle;
		TextView trainingCourseDescription;
	}
	
}

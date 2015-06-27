package com.vet.vet_app;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

public class ListPopulator {

	static final String TAG = ListPopulator.class.getSimpleName();

	public String[] getJobTitles(Document finalDoc) {

		int counter = 0;

		Elements jobTitles = finalDoc.select("[class=mod job-result]")
				.select("[class=inner]").select("[class=hd]");

		String[] resultTitles = new String[jobTitles.size()];

		for (Element jobTitle : jobTitles) {

			resultTitles[counter] = jobTitle.text();
			counter++;

		}

		return resultTitles;

	}

	public String[] getJobDescription(Document finalDoc) {

		int counter = 0;

		Elements jobDescriptions = finalDoc.select("[class=mod job-result]")
				.select("[class=inner]").select("[class=bd]").select("p");

		String[] resultDescriptions = new String[jobDescriptions.size()];

		for (Element jobDescription : jobDescriptions) {

			resultDescriptions[counter] = jobDescription.text().substring(0,
					100)
					+ "...";
			counter++;

		}

		return resultDescriptions;

	}

	public String[] getTrainingCourseTitle(Document finalDoc) {

		int counter = 0;
		Elements tableRows = finalDoc.select("table").select("tr").select("td")
				.select("div.prem-rel");

		String[] resultTrainingTitles = new String[tableRows.size()];

		for (Element trainingCourseTitle : tableRows) {

			resultTrainingTitles[counter] = trainingCourseTitle.text();
			counter++;

		}

		return resultTrainingTitles;

	}

	public String[] getTrainingCourseDescription(Document finalDoc) {

		int counter = 0;

		Elements trainingCourseDescriptions = finalDoc.select("table").select(
				"p");

		String[] resultTrainingDescriptions = new String[trainingCourseDescriptions
				.size()];

		for (Element trainingCourseDescription : trainingCourseDescriptions) {

			if (trainingCourseDescription.text().length() > 100) {
				resultTrainingDescriptions[counter] = trainingCourseDescription
						.text().substring(0, 100) + "...";
			} else {
				resultTrainingDescriptions[counter] = trainingCourseDescription
						.text() + "...";
			}
			counter++;

		}

		Log.d(TAG, "Got All Descriptions");

		return resultTrainingDescriptions;

	}

}

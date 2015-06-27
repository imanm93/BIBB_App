package com.vet.vet_app;

import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class GetJobsInfoApplication {

	static final String TAG = GetJobsInfoApplication.class.getSimpleName();
	DefaultHttpClient httpClient;
	ResponseHandler<String> resHandler;
	
	public Document getParsedHtml(String completeSiteHtml) {
		
		httpClient = new DefaultHttpClient();
		resHandler = new BasicResponseHandler();
		HttpGet httpGet = new HttpGet(completeSiteHtml);
		String page;
		
		try {
			page = httpClient.execute(httpGet, resHandler);
			Document pageDoc = Jsoup.parse(page);
			return pageDoc;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public String getCompleteJobSiteHtml(String html) {
		return "http://www.totaljobs.com/JobSeeking/" + html + ".html";
	}
	
	public String getCompleteTrainingSiteHtml(String html) {
		
		if (html == "Electronic") {
			return "http://www.coursesplus.co.uk/ittechnician-c1670";
		} else {
			return "http://www.coursesplus.co.uk/mechanic-c1150";
		}
		
	}
	
	public String getInfoSiteHtml(String html) {
		if (html == "Mechanic") {
		return "http://www.bibb.de/en/ausbildungsprofil_13838.htm";
		} else {
		return "http://www.bibb.de/en/ausbildungsprofil_12217.htm";
		}
	}

}

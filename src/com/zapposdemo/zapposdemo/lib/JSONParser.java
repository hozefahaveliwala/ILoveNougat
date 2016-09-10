package com.zapposdemo.zapposdemo.lib;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;


public class JSONParser {

	static JSONObject jObj = null;
	static String json = "";
	
	public JSONObject getJSONFromUrl(String inputURL) throws IOException, JSONException {

		//set parameters and buid connection to download 
		HttpURLConnection urlConnection = null;
		StringBuilder result = new StringBuilder();

		try {
			URL url = new URL(inputURL);
			urlConnection = (HttpURLConnection) url.openConnection();
			InputStream in = new BufferedInputStream(
					urlConnection.getInputStream());
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			String line;
			while ((line = reader.readLine()) != null) {
				result.append(line + "\n");
			}

			json = result.toString();
			Log.d("JSONdata", json);
			
			// try parse the string to a JSON object
			jObj = new JSONObject(json);

			// return JSON String
			return jObj;
			
			
		}  finally {
			urlConnection.disconnect();
		}
		
	}

}

package com.zapposdemo.zapposdemo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zapposdemo.zapposdemo.lib.Item;
import com.zapposdemo.zapposdemo.lib.JSONParser;
import com.zapposdemo.zapposdemo.lib.RecyclerViewAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

public class ActivityCompare extends Activity {

	
	//variables//
	RecyclerView recyclerView;
	private String searchURL;
	private List<Item> ItemList;
	private RecyclerViewAdapter adapter;
	private String productName;
	private float productFinalPrice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compare);
		// this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		recyclerView = (RecyclerView) findViewById(R.id.compare_recycler_view_);
		LinearLayoutManager LLM = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(LLM);
		recyclerView.setHasFixedSize(true);

		//get intents from calling activity
		productName = getIntent().getStringExtra("productName");
		
		//convert string price to float
		productFinalPrice = Float.parseFloat(getIntent().getStringExtra(
				"productFinalPrice").substring(1));

		searchURL = String.format(getString(R.string.search_api2), productName);
		Log.d("URL2", searchURL);
		new AsyncHttpTask().execute();
	}

	// ********** Inner Class for Async Task.***************

	private class AsyncHttpTask extends AsyncTask<String, Void, Integer> {
		ProgressDialog progressDialog;
		JSONObject jsonobject;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			progressDialog = new ProgressDialog(ActivityCompare.this);
			progressDialog.setMessage("Please Wait..");
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setCancelable(false);
			progressDialog.show();
			
		}

		protected void onProgressUpdate(String... progress) {
			Log.d("ANDRO_ASYNC", progress[0]);
			progressDialog.setProgress(Integer.parseInt(progress[0]));
		}

		@Override
		protected Integer doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			int result = 0;

			try {
				getJSON();
				checkJSON(jsonobject);
				result = 1;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return result;
		}

		//download result from api
		private void getJSON() throws IOException, JSONException {
			JSONParser JSONParser = new JSONParser();
			jsonobject = JSONParser.getJSONFromUrl(searchURL);
		}

		//parse the result of api
		private void checkJSON(JSONObject json) {

			JSONArray result = json.optJSONArray("results");
			ItemList = new ArrayList<Item>();

			for (int i = 0; i < result.length(); i++) {

				JSONObject post = result.optJSONObject(i);

				if (Float.parseFloat(post.optString("price").substring(1)) < productFinalPrice) {
					Item item = new Item();

					item.setBrandName(post.optString("brandName"));
					item.setProductName(post.optString("productName"));
					item.setOriginalPrice(post.optString("originalPrice"));
					item.setFinalPrice(post.optString("price"));
					item.setPriceOff(post.optString("percentOff"));
					item.setThumbnail(post.optString("thumbnailImageUrl"));

					ItemList.add(item);
				}
			}

		}

		@Override
		protected void onPostExecute(Integer result) {
			// Download complete. Let us update UI
			progressDialog.dismiss();

			if (result == 1) {
				if (ItemList.size() > 0) {
					adapter = new RecyclerViewAdapter(ActivityCompare.this,
							ItemList, 1);
					recyclerView.setAdapter(adapter);

				} else {
					Toast.makeText(ActivityCompare.this, "No match found!",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(ActivityCompare.this, "Error fetching data!",
						Toast.LENGTH_SHORT).show();
			}
		}

	}
	// ********** Inner Class for Async Task.***************

}

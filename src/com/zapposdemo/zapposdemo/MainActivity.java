package com.zapposdemo.zapposdemo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zapposdemo.zapposdemo.lib.Checkconn;
import com.zapposdemo.zapposdemo.lib.Item;
import com.zapposdemo.zapposdemo.lib.JSONParser;
import com.zapposdemo.zapposdemo.lib.RecyclerViewAdapter;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends Activity {
	
	////////variables/////////
	Checkconn conn;
	EditText inputSearch;
	ImageView imgSearch;
	RecyclerView recyclerView;
	private String searchURL;
	private List<Item> ItemList;
	private RecyclerViewAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		imgSearch = (ImageView) findViewById(R.id.p_img_search);
		inputSearch = (EditText) findViewById(R.id.editText1);
		recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		LinearLayoutManager LLM = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(LLM);
		recyclerView.setHasFixedSize(true);
		conn = new Checkconn(this);

		imgSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				////check for internet connect on phone
				if (inputSearch.getText().length() > 0) {
					if (conn.haveNetworkConnection() == false) {
						conn.shownetdialog();
					} else {

						//create URL from input search string
						searchURL = String.format(
								getString(R.string.search_api1),
								inputSearch.getText());
						Log.d("URL1", searchURL);
						
						//call the async download
						new AsyncHttpTask().execute();
					}
				} else {
					ItemList.clear();
					adapter.notifyDataSetChanged();
				}
			}

		});

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

	// ********** Inner Class for Async Task.***************

	private class AsyncHttpTask extends AsyncTask<String, Void, Integer> {
		ProgressDialog progressDialog;
		JSONObject jsonobject;
		

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			progressDialog = new ProgressDialog(MainActivity.this);
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
			Integer result = 0;
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

			JSONArray JSONResult = json.optJSONArray("results");
			ItemList = new ArrayList<Item>();

			for (int i = 0; i < JSONResult.length(); i++) {

				JSONObject post = JSONResult.optJSONObject(i);
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

		@Override
		protected void onPostExecute(Integer result) {
			// Download complete. Let us update UI
			progressDialog.dismiss();
			if (result == 1) {
				adapter = new RecyclerViewAdapter(MainActivity.this, ItemList,
						0);
				recyclerView.setAdapter(adapter);
			} else {
				Toast.makeText(MainActivity.this, "Error fetching data!",
						Toast.LENGTH_SHORT).show();
			}
		}

	}
	// ********** Inner Class for Async Task.***************
}

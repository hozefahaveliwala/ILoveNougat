package com.zapposdemo.zapposdemo.lib;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Checkconn {
	Context c;

	public Checkconn(Context mcontext) {
		this.c = mcontext;
	}

	public boolean haveNetworkConnection() {
		boolean isConnected = false;

		ConnectivityManager cm = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		 NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

		 if (activeNetwork != null) {
			 isConnected =true;
		 }
		 return isConnected;
	}

	public void shownetdialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(c);
		builder.setTitle("No Internet connection.");
		builder.setMessage("Unable to connect to internet.");
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				dialog.dismiss();
			}
		});

		builder.setPositiveButton("Settings",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						c.startActivity(new Intent(
								android.provider.Settings.ACTION_SETTINGS));

					}
				});

		builder.show();
	}
}

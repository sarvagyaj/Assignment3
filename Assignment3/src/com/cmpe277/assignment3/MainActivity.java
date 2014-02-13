package com.cmpe277.assignment3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Button bPreference, bSQLite, bClose;
	private TextView detail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		bPreference = (Button) findViewById(R.id.bPreference);
		bSQLite = (Button) findViewById(R.id.bSqlLite);
		bClose = (Button) findViewById(R.id.bClose);
		detail = (TextView) findViewById(R.id.detail);

		// shared preference timestamp history
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		String dateStringSP = prefs.getString("date", null);
		List<Long> datesSP = new ArrayList<Long>();
		// datesSP= new ArrayList<String>();
		if (dateStringSP != null) {
			List<String> datesSPString = new ArrayList<String>(
					Arrays.asList(dateStringSP.split(",")));
			for (String s : datesSPString) {
				long d = Long.parseLong(s);
				datesSP.add(d);
			}
		}

		// sqlite timestamp history
		DB db = new DB(this);
		db.open();
		List<Long> datesSQL = db.readAllDates();

		merge(datesSP, datesSQL);

		
		

		bPreference.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(
						"com.cmpe277.assignment3.PreferenceActivity");
				startActivity(intent);
			}
		});

		bSQLite.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(
						"com.cmpe277.assignment3.SQLLiteActivity");
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void merge(List<Long> datesSP, List<Long> datesSQL) {
		int counterSP = 1;
		int counterSQL = 1;
		if (datesSP == null && datesSQL == null) {
			detail.append("Nothing");
		}
		if (datesSP == null || datesSP.isEmpty()) {
			for (Long l : datesSQL) {
				Date date = new Date(l);
				detail.append("SQLite " + counterSQL + ", " + date + "\n");
				counterSQL++;
			}
		}
		if (datesSQL == null || datesSQL.isEmpty()) {
			for (Long l : datesSP) {
				Date date = new Date(l);
				detail.append("Saved Preference " + counterSP + ", " + date
						+ "\n");
				counterSP++;
			}
		}

		

		while (!datesSP.isEmpty() && !datesSQL.isEmpty()) {
			if (datesSP.get(0) < datesSQL.get(0)) {
				// result.add(datesSP.get(0));
				Date date = new Date(datesSP.get(0));
				detail.append("Saved Preference " + counterSP + ", " + date
						+ "\n");
				counterSP++;
				datesSP.remove(0);
			} else {
				Date date = new Date(datesSQL.get(0));
				detail.append("SQLite " + counterSQL + ", " + date + "\n");
				counterSQL++;
				datesSQL.remove(0);
			}
		}
		if (datesSP.isEmpty()) {
			for (Long l : datesSQL) {
				Date date = new Date(l);
				detail.append("SQLite " + counterSQL + ", " + date + "\n");
				counterSQL++;
			}
		} else {
			for (Long l : datesSP) {
				Date date = new Date(l);
				detail.append("Saved Preference " + counterSP + ", " + date
						+ "\n");
				counterSP++;
			}
		}

	}
}

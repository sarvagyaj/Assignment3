package com.cmpe277.assignment3;

import java.util.Date;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PreferenceActivity extends Activity {

	private Button bSave, bCancel;
	private EditText bookName, bookAuthor, bookDesc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preferences);

		bSave = (Button) findViewById(R.id.bSave);
		bCancel = (Button) findViewById(R.id.bCancel);
		bookName = (EditText) findViewById(R.id.bookName);
		bookAuthor = (EditText) findViewById(R.id.bookAuthor);
		bookDesc = (EditText) findViewById(R.id.bookDesc);

		bSave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				savePreferences(bookName.getText().toString(), bookAuthor
						.getText().toString(), bookDesc.getText().toString());

			}
		});
		
		bCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				readPreferences();				
			}
		});

		readPreferences();

	}

	private void savePreferences(String bookName, String bookAuthor,
			String bookDesc) {
		Date date = new Date(System.currentTimeMillis());
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("bookName", bookName);
		editor.putString("bookAuthor", bookAuthor);
		editor.putString("bookDesc", bookDesc);
		editor.putLong("date", date.getTime());
		
		String dateString =prefs.getString("date", "");
		dateString =dateString+ String.valueOf(date.getTime())+",";
		editor.putString("date", dateString);
		
		//editor.put
		/*
		 * Set<String> set = new HashSet<String>(); set.add(bookName);
		 * set.add(bookAuthor); set.add(bookDesc);
		 */
		editor.commit();
		
		Dialog d = new Dialog(this);
		d.setTitle("Saved");
		d.show();
	}

	private void readPreferences() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		bookName.setText(prefs.getString("bookName", ""));
		bookAuthor.setText(prefs.getString("bookAuthor", ""));
		bookDesc.setText(prefs.getString("bookDesc", ""));
	}

}

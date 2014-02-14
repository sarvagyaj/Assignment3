package com.cmpe277.assignment3;

import java.util.Date;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
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

		// method to call when Save button is clicked on screen
		bSave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				savePreferences(bookName.getText().toString(), bookAuthor
						.getText().toString(), bookDesc.getText().toString());

			}
		});

		// method to call when Save button is clicked on screen
		bCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				readPreferences();
			}
		});

		readPreferences();

	}

	//method to save preferences from shared preferences
	private void savePreferences(String bookName, String bookAuthor,
			String bookDesc) {
		Date date = new Date();
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("bookName", bookName);
		editor.putString("bookAuthor", bookAuthor);
		editor.putString("bookDesc", bookDesc);
		editor.putLong("date", date.getTime());

		String dateString = prefs.getString("date", "");
		dateString = dateString + String.valueOf(date.getTime()) + ",";
		editor.putString("date", dateString);
		editor.commit();

		Dialog d = new Dialog(this);
		d.setTitle("Saved");
		d.show();
	}

	//method to read preferences from shared preferences
	private void readPreferences() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		bookName.setText(prefs.getString("bookName", ""));
		bookAuthor.setText(prefs.getString("bookAuthor", ""));
		bookDesc.setText(prefs.getString("bookDesc", ""));
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent a = new Intent(this, MainActivity.class);
			a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(a);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}

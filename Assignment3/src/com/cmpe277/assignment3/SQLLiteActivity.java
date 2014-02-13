package com.cmpe277.assignment3;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SQLLiteActivity extends Activity implements View.OnClickListener{
	EditText bookDesc;
	Button bSave, bGet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sqllite);
		
		bookDesc = (EditText) findViewById(R.id.msg);
		bSave = (Button) findViewById(R.id.bSave);
		bGet=(Button)findViewById(R.id.bGet);
		
		bSave.setOnClickListener(this);
		bGet.setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bSave:
			String sqlFlag = "Message Saved !!";
			try {
				DB db = new DB(this);
				db.open();
				db.createEntry(bookDesc.getText().toString());
				db.close();
			}catch(Exception e) {
				sqlFlag = e.toString();
			}
			finally {
				Dialog d =  new Dialog(this);
				d.setTitle(sqlFlag);
				d.show();
			}
			break;

		case R.id.bGet:
			DB db = new DB(this);
			db.open();
			String result = db.readAllData();
			db.close();
			
			Dialog d = new Dialog(this);
			d.setTitle("All Messages");
			TextView tv = new TextView(this);
			tv.setText(result);
			d.setContentView(tv);
			d.show();
		default:
			break;
		}
		
	}
	
	

	
}

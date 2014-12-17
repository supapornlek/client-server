package com.example.androidfinal_421;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.androidfinal_421.EditActivity.SaveProductDetails;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class insert  extends Activity implements OnClickListener{
	
	
	private EditText e1, e2, e3;
	private Button btn1;
	private ProgressDialog dialog;
	String sid;
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_STUDENT = "student";
	private static final String TAG_ID = "id";
	private static final String TAG_STUID = "stu_id";
	private static final String TAG_NAME = "name";
	private static final String TAG_TEL = "tel";
	JSONParser jParser = new JSONParser();
	private static String url_create_student = "http://10.202.35.23:88/android/android/create_student.php";
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.insert);
		e1=(EditText) findViewById(R.id.ee1);
		e2=(EditText) findViewById(R.id.ee2);
		e3=(EditText) findViewById(R.id.ee3);
		btn1 = (Button) findViewById(R.id.btn6);
		btn1.setOnClickListener(this);
}
	class InsertProductDetails extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(insert.this);
			dialog.setMessage("Saving student ...");
			dialog.setIndeterminate(false);
			dialog.setCancelable(true);
			dialog.show();
		}

		protected String doInBackground(String... args) {
			String stu_id = e1.getText().toString();
			String name = e2.getText().toString();
			String tel = e3.getText().toString();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("id", sid));
			params.add(new BasicNameValuePair(TAG_STUID, stu_id));
			params.add(new BasicNameValuePair(TAG_NAME, name));
			params.add(new BasicNameValuePair(TAG_TEL, tel));
			JSONObject json = jParser.makeHttpRequest(url_create_student,
					"POST", params);
			try {
				int success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					Intent i = getIntent();
					setResult(100, i);
					finish();
				} else {
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(String file_url) {
			dialog.dismiss();
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn6:
			new InsertProductDetails().execute();
			break;
		default:
			break;
	}	;
	}
}

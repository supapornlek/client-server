package com.example.androidfinal_421;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class EditActivity extends Activity implements OnClickListener {
	private EditText edt1, edt2, edt3;
	private Button btn1, btn2,btn3;
	private ProgressDialog dialog;

	JSONParser jParser = new JSONParser();
	
	
	
	private static String url_update_student = "http://10.202.35.23:88/android/android/update_student.php";
	private static String url_details_student = "http://10.202.35.23:88/android/android/student_details.php";
	private static String url_delete_student = "http://10.202.35.23:88/android/android/delete_student.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_STUDENT = "student";
	private static final String TAG_ID = "id";
	private static final String TAG_STUID = "stu_id";
	private static final String TAG_NAME = "name";
	private static final String TAG_TEL = "tel";

	String sid;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit);

		Intent i = getIntent();
		sid = i.getStringExtra(TAG_ID);

		btn1 = (Button) findViewById(R.id.btn_update);
		btn1.setOnClickListener(this);
		btn2 = (Button) findViewById(R.id.btn_delete);
		btn2.setOnClickListener(this);
		btn3 = (Button) findViewById(R.id.btn5);
		btn3.setOnClickListener(this);
		new getStudentDetails().execute();

	}

	class getStudentDetails extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(EditActivity.this);
			dialog.setMessage("Looding student. Please waite...");
			dialog.setIndeterminate(false);
			dialog.setCancelable(false);
			dialog.show();
			super.onPreExecute();
		}

		protected String doInBackground(String... arg0) {
			int success;
			try {
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				list.add(new BasicNameValuePair("id", sid));

				JSONObject json = jParser.makeHttpRequest(url_details_student,
						"GET", list);
				Log.d("Single Student Delails", json.toString());

				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					JSONArray stuobj = json.getJSONArray(TAG_STUDENT);
					JSONObject student = stuobj.getJSONObject(0);

					edt1 = (EditText) findViewById(R.id.ee1);
					edt2 = (EditText) findViewById(R.id.ee2);
					edt3 = (EditText) findViewById(R.id.ee3);

					edt1.setText(student.getString(TAG_STUID));
					edt2.setText(student.getString(TAG_NAME));
					edt3.setText(student.getString(TAG_TEL));

				} else {

				}

			} catch (JSONException e) {
				e.printStackTrace();

			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			dialog.dismiss();

		}

	}

	class SaveProductDetails extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(EditActivity.this);
			dialog.setMessage("Saving student ...");
			dialog.setIndeterminate(false);
			dialog.setCancelable(true);
			dialog.show();
		}

		protected String doInBackground(String... args) {
			String stu_id = edt1.getText().toString();
			String name = edt2.getText().toString();
			String tel = edt3.getText().toString();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("id", sid));
			params.add(new BasicNameValuePair(TAG_STUID, stu_id));
			params.add(new BasicNameValuePair(TAG_NAME, name));
			params.add(new BasicNameValuePair(TAG_TEL, tel));
			JSONObject json = jParser.makeHttpRequest(url_update_student,
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
	class DeleteStudentDetails extends AsyncTask<String, String, String>{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(EditActivity.this);
			dialog.setMessage("Delete student ...");
			dialog.setIndeterminate(false);
			dialog.setCancelable(true);
			dialog.show();
		}
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			int success;
			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("id", sid));

				JSONObject json = jParser.makeHttpRequest(
						url_delete_student, "POST", params);

				Log.d("Delete Student", json.toString());

				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					Intent i = getIntent();
					setResult(100, i);
					finish();
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
		switch (v.getId()) {
		case R.id.btn_update:
			new SaveProductDetails().execute();
			break;
		case R.id.btn_delete:
			new DeleteStudentDetails().execute();
			break;
		case R.id.btn5:
			Intent j = new Intent(EditActivity.this, insert.class);
			startActivity(j);
			break;
		default:
			break;
		}

	}
}

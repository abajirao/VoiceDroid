package com.moslabs.voicedroid;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class VoiceDroid extends Activity {

	private static final String TAG = VoiceDroid.class.getSimpleName();
	Button mBtnSpeak;
	TextView mTextView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_voice_droid);
		mTextView = (TextView) findViewById(R.id.textView1);
		mBtnSpeak = (Button) findViewById(R.id.btnSpeak);
		mBtnSpeak.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(
						RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
				intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
						getClass().getPackage().getName());
				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
						RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
				startActivityForResult(intent, 1010);

			}	
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.voice_droid, menu);
		return true;
	}

	public void checkVoiceRecognition() {

		PackageManager pm = getPackageManager();
		List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		if (activities.size() == 0) {
			Log.v(TAG, "Voice disabled");
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1010) {
			String speechText="";
			if (resultCode == RESULT_OK) {
				ArrayList<String> textMatchList = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				Log.v(TAG,"data - " +textMatchList.get(0)+ " --- " +textMatchList.get(1));
				for (String string : textMatchList) {
					speechText+=string;
				}
				mTextView.setText(speechText);
			}

		}
	}

}

package com.cougar.gcpnavigation.main;

import com.cougar.gcpnavigation.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Welcome extends Activity implements Runnable {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);

		Handler handler = new Handler();
		handler.postDelayed(this, 2000);
	}

	@Override
	public void run() {
		startLogin();
	}

	private void startLogin() {
		startActivity(new Intent(getApplicationContext(), Login.class));
		this.finish();
	}
}

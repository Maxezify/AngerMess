package com.example.angermess;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.angermess.MainActivity.PlaceholderFragment;

public class MainActivity2 extends ActionBarActivity {
	
	private TextView Titre;
	private TextView Bienvenue;
	private Button ButtonList;
	private Button ButtonSend;
	String UserName;
	String UserPassword;
	
	private final String TAG = MainActivity.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu2);
		
		 Typeface tf = Typeface.createFromAsset(getAssets(),
	                "fonts/Molot.otf");
		
		Titre = (TextView) findViewById(R.id.textView1);
		Titre.setTypeface(tf);
		Bienvenue = (TextView) findViewById(R.id.textView2);
		Bienvenue.setTypeface(tf);
		ButtonList = (Button) findViewById(R.id.button3);
		ButtonList.setTypeface(tf);
		ButtonSend = (Button) findViewById(R.id.button4);
		ButtonSend.setTypeface(tf);
		ButtonList.setOnClickListener(ListHandler);
		ButtonSend.setOnClickListener(SendHandler);
		
		
		Bundle extras;
		
		if (savedInstanceState == null)
		{
		    extras = getIntent().getExtras();
		    if(extras == null) {
		    	UserName= null;
		    	UserPassword= null;
		    } else {
		    	UserName= extras.getString("UserName");
		    	UserPassword= extras.getString("UserPassword");
		    }
		} else {
			UserName= (String) savedInstanceState.getSerializable("UserName");
			UserPassword= (String) savedInstanceState.getSerializable("UserPassword");
		}
		
		

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		Log.i(TAG, UserName + "   gofuckyourself");
		Log.i(TAG, UserPassword + "   gofuckyourself");
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
	
	View.OnClickListener ListHandler = new View.OnClickListener(){
		public void onClick(View v){
			Intent myIntentList = new Intent(v.getContext(), MainActivityList.class);
			myIntentList.putExtra("UserName", UserName);
			myIntentList.putExtra("UserPassword", UserPassword);
			startActivity(myIntentList);
		}
	};
	
	View.OnClickListener SendHandler = new View.OnClickListener(){
		public void onClick(View v){
			Intent myIntentList = new Intent(v.getContext(), ActivitySend.class);
			myIntentList.putExtra("UserName", UserName);
			myIntentList.putExtra("UserPassword", UserPassword);
			startActivity(myIntentList);
		}
	};
	
}
	

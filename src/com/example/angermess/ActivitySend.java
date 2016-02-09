package com.example.angermess;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ActivitySend extends ActionBarActivity {
	
	private Button ButtonSend;
	private EditText messageToSend;
	private TextView SendEnvoyer;
	private TextView Confirme;
	private Button Message;
	
	private final String TAG = MainActivity.class.getSimpleName();
	
	String UserName;
	String UserPassword;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menusend);
		
		 Typeface tf = Typeface.createFromAsset(getAssets(),
	                "fonts/Molot.otf");
		
		ButtonSend = (Button) findViewById(R.id.button5);
		ButtonSend.setTypeface(tf);
		messageToSend = (EditText) findViewById(R.id.editText1);
		SendEnvoyer = (TextView) findViewById(R.id.textView3);
		SendEnvoyer.setTypeface(tf);
		ButtonSend.setOnClickListener(SendHandler);
		Confirme = (TextView) findViewById(R.id.textView4);
		Confirme.setTypeface(tf);
		Confirme.setTextColor(Color.parseColor("#004B00"));
		Confirme.setVisibility(View.INVISIBLE);
		Message = (Button) findViewById(R.id.button6);
		Message.setTypeface(tf);
		Message.setVisibility(View.INVISIBLE);
		Message.setOnClickListener(MessageHandler);
		
		
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
		
		
		
	}
	View.OnClickListener SendHandler = new View.OnClickListener(){
		public void onClick(View v){
		     new lanceMessage().execute();
		     
		     Confirme.setVisibility(View.VISIBLE);
		     Message.setVisibility(View.VISIBLE);
	};
};

View.OnClickListener MessageHandler = new View.OnClickListener(){
	public void onClick(View v){
		
		Confirme.setVisibility(View.INVISIBLE);
	    Message.setVisibility(View.INVISIBLE);
		Intent myIntentList = new Intent(v.getContext(), MainActivityList.class);
		myIntentList.putExtra("UserName", UserName);
		myIntentList.putExtra("UserPassword", UserPassword);
		startActivity(myIntentList);
	}
};


private class lanceMessage extends AsyncTask<Void,Void,Void>{

	protected Void doInBackground(Void... params) {
	 
     
     try {
    	 String a = messageToSend.getText().toString();
    	 a = a.replace(" ", "%20");
    	 a = a.replace("?", "%3F");
    	 a = a.replace("é", "%E9");
    	 a = a.replace("ê", "%EA");
    	 a = a.replace("è", "%E8");
    	 a = a.replace("ç", "%E7");
    	 a = a.replace("à", "%E0");
    	 
      DefaultHttpClient client = new DefaultHttpClient();
      HttpGet request = new HttpGet("http://parlezvous.herokuapp.com/message/" + UserName+ "/" + UserPassword +"/"+ a);
      
      HttpResponse response = client.execute(request);
      
      InputStream content = response.getEntity().getContent();
      
      String retour = InputStreamToString.convert(content);
      
      
     } catch (ClientProtocolException e) {
      e.printStackTrace();
     } catch (IOException e) {
      e.printStackTrace();
     }
     
     
     return null;
    }
   }
}

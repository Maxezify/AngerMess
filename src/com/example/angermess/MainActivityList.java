package com.example.angermess;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;








import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivityList extends ActionBarActivity {	
	
	private final String TAG = MainActivity.class.getSimpleName();
	String UserName;
	String UserPassword;
	String b;
	String[] separated;
	ListView lvListe;
	
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menulist);
		
		
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
		
		//Log.i(TAG, UserName + "   gofuckyourseList");
		//Log.i(TAG, UserPassword + "   gofuckyourseList");
		
		ParlezVousTaskList p = new ParlezVousTaskList();
		
		try {
			b = p.execute().get();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		separated = b.split(";");
		
		lvListe = (ListView)findViewById(R.id.lvListe);
		lvListe.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,separated));
		
		//Log.i(TAG, "thisisastring         " + separated[0]  + separated[1]);

	}
	
	private class ParlezVousTaskList extends AsyncTask<Void, Void, String>{
	
	  protected String doInBackground(Void... params) {
	      try {
	       DefaultHttpClient client = new DefaultHttpClient();
	       HttpGet request = new HttpGet("http://parlezvous.herokuapp.com/messages/" + UserName+ "/" + UserPassword);
	       HttpResponse response = client.execute(request);
	       
	       InputStream content = response.getEntity().getContent();
	       
	       String retour = InputStreamToString.convert(content);
	       
	       return (retour);
	       	      
	      }
	      catch (ClientProtocolException e)
	      {
	       e.printStackTrace();
	      }
	      catch (IOException e) {
	       e.printStackTrace();
	      }
		return "FAIL";
	      
	     }
	}
}

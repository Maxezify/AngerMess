package com.example.angermess;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	
	private Button ButtonValider;
	private Button ButtonVider;
	private EditText usernameField;
	private EditText passwordField;
	private MainActivity mainActivityContext = this;
	private ProgressBar wheeLoad;
	private Boolean b;
	private TextView Titre;
	private TextView Pseudo;
	private TextView Password;
	private TextView Wrong;
	
	
	
	private final String TAG = MainActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		 Typeface tf = Typeface.createFromAsset(getAssets(),
	                "fonts/Molot.otf");
		 
		Titre = (TextView) findViewById(R.id.textView1);
		Titre.setTypeface(tf);
		Pseudo  = (TextView) findViewById(R.id.textView2);
		Pseudo.setTypeface(tf);
		usernameField = (EditText) findViewById(R.id.editText1);
		Password = (TextView) findViewById(R.id.textView3);
		Password.setTypeface(tf);
		passwordField = (EditText) findViewById(R.id.editText2);
		ButtonValider = (Button) findViewById(R.id.button1);
		ButtonValider.setTypeface(tf);
		ButtonVider = (Button) findViewById(R.id.button2);
		ButtonVider.setTypeface(tf);
		ButtonVider.setOnClickListener(VidageHandler);
		ButtonValider.setOnClickListener(ValiderHandler);
		wheeLoad = (ProgressBar) findViewById(R.id.progressBar1);
		Wrong = (TextView) findViewById(R.id.textView4);
		Wrong.setTextColor(Color.parseColor("#CC0000"));
		Wrong.setTypeface(tf);
		
		Wrong.setVisibility(View.INVISIBLE);
		wheeLoad.setVisibility(View.INVISIBLE);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	View.OnClickListener VidageHandler = new View.OnClickListener(){
	     public void onClick(View v){
	    	 usernameField.setText("");
	    	 passwordField.setText("");
	    	 
	     }
	};
	
	View.OnClickListener ValiderHandler = new View.OnClickListener(){
		public void onClick(View v){
		      ParlezVousTask p = new ParlezVousTask();
		   try {
		    b = p.execute().get();
		   } catch (InterruptedException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		   } catch (ExecutionException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		   }
		   if(b == true)
		   {
			   Intent myIntent = new Intent(v.getContext(), MainActivity2.class);
			   String UserName = usernameField.getText().toString();
			   String UserPassword = passwordField.getText().toString();
			   myIntent.putExtra("UserName", UserName);
			   myIntent.putExtra("UserPassword", UserPassword);
			   startActivity(myIntent);
		   }
		   
		   if (b == false)
		   {
			   
			   Wrong.setVisibility(View.VISIBLE);  
			   
			   
		   }
		   
		   Log.i(TAG,String.valueOf(b));
		      if(usernameField.getText().toString().equals(""))
		      {
		       //error
		      }
		      //Toast.makeText(mainActivityContext, "Toast !", Toast.LENGTH_SHORT).show();
		      
		      
		     }
	};
	

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	private class ParlezVousTask extends AsyncTask<Void, Void, Boolean>{

	     protected void onPreExecute() {
	      
	      wheeLoad.setVisibility(View.VISIBLE);
	     }
	     
	     @Override
	  protected void onPostExecute(Boolean result) {
	   // TODO Auto-generated method stub
	   super.onPostExecute(result);
	   wheeLoad.setVisibility(View.GONE);
	  }


	  protected Boolean doInBackground(Void... params) {
		  Log.i(TAG, "   lalilo");
	      
	      try {
	       DefaultHttpClient client = new DefaultHttpClient();
	       HttpGet request = new HttpGet("http://parlezvous.herokuapp.com/connect/" + usernameField.getText().toString()+ "/" +passwordField.getText().toString());
	       HttpResponse response = client.execute(request);
	       
	       InputStream content = response.getEntity().getContent();
	       
	       String retour = InputStreamToString.convert(content);
	       
	       
	       return Boolean.valueOf(retour);
	      } catch (ClientProtocolException e) {
	       e.printStackTrace();
	      } catch (IOException e) {
	       e.printStackTrace();
	      }
	      
	      
	      return null;
	     }
	    }
}

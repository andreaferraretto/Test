package com.ferra.test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.example.fanta.R;
import com.google.ads.AdView;
import com.google.android.gms.*;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private List<String> myList;
	private ListView listView ;
    private File file;
	private ArrayAdapter<String> adapter;
	private String directory;
	private MediaPlayer mediaPlayer;
	private AssetFileDescriptor descriptor;
	
	//AdView
	private AdView mAdView;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		directory = "Audio";
    	mediaPlayer = new MediaPlayer();
    	mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			
		myList = new ArrayList<String>();
		listView = (ListView) findViewById(R.id.listView);
		
		listOfAudio();
		
		adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, myList);
        
		listView.setAdapter(adapter); //Set all the file in the list.
        
		
		
        // Create a banner ad. The ad size and ad unit ID must be set before calling loadAd.
		mAdView = (AdView) findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdUnitId("myAdUnitId");
		
		
        
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	   @Override
        	   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        	      String listItem = "";
        	      try{
        	    	  listItem = (String) listView.getItemAtPosition(position).toString();
        	      }catch(ClassCastException e){
        	    	  e.getLocalizedMessage();
        	      }

        	      int size = myList.size();
        	      for(int i=0;i<size;i++){
        	        if(myList.get(i).equalsIgnoreCase(listItem)){
        	        	
        	        	String myUri = "";
        	        	File myFile;
        	        	
        	        	myFile = new File(myUri);
        
        	        	

        	        	try{

        	        		mediaPlayer.stop();
        	        	}catch(Exception e){
        	        		Log.w("APP", "Stop not working");
        	        	}
        	        	
        	        	//mediaPlayer = new MediaPlayer();
        	        	//mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        	    		
        	        	mediaPlayer.reset();
        	        	
        	        	try {
        	        		descriptor = null;
        	        		descriptor = getAssets().openFd(directory + File.separator + listItem + File.separator + listItem +".mp3");
        	        		Log.i("APP", "Descriptor: " + directory + File.separator + listItem + File.separator + listItem +".mp3");
        	        		
        	        	    mediaPlayer.setDataSource( descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
        	        	    
        	        	    descriptor.close();
        	        	} catch (Exception e) {
							Log.w("APP", e.getMessage() + "" );
						}
        	        	
        	        	try {
							mediaPlayer.prepare();
							
						} catch (Exception e) {
							Log.w("APP", e.getMessage() + "");
						}

        	        	mediaPlayer.seekTo(0);
        	        	mediaPlayer.start();
        	        	Toast.makeText(getApplicationContext(), "Audio in corso...", Toast.LENGTH_LONG).show();
        	        	
        	        }	
        	      }
        	      
        	      
        	   } 
        });
		
	}
	
	
	
	public void listOfAudio(){
		AssetManager assetManager = getAssets(); 
		String list[];
		try { 
			list = assetManager.list(directory); 
			
		     for( int i=0; i< list.length; i++){
		    	 myList.add( list[i] );
		     }
			
	    } catch (IOException e) { 
	        Log.e("tag", e.getMessage()); 
	    } 
		//
		
	        

	}
	
	
	
	
}

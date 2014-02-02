package alex.myhomeworkforegar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	private static final String URL = "http://raspisaniye-vuzov.ru/api/v1/groups/5555";

	private static final int SORTED_BY_DATE = 1;
	private static final int SORTED_BY_NAME_LESSON = 2;
	private static final int NO_SORTED = 0;
	
	private String json;	
	private String nameLesson;
	private String dateLesson;
	private String timeStartLesson;
	private String timeEndLesson;
	private String weekdayLesson;
	
	private ArrayList<Lessons> listNoSorted = new ArrayList<Lessons>();	
	private TreeSet<Lessons> listSortedByName = new TreeSet<Lessons>();
	private TreeSet<Lessons> listSortedByDate = new TreeSet<Lessons>();
	
	private ListView listView;
	
	private AdapterShedule adapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		adapter = new AdapterShedule(MainActivity.this);
		
		listView = (ListView) findViewById(R.id.listView1);
		listView.setAdapter(adapter);
		
		downLoadSchedule();	
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	
		menu.add(0, SORTED_BY_DATE, 0, R.string.sorted_by_date);
		menu.add(0, SORTED_BY_NAME_LESSON, 0, R.string.sorted_by_name);
		
		return super.onCreateOptionsMenu(menu);
	}
		

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	
		switch (item.getItemId()) {
		case SORTED_BY_DATE:
			
			show(SORTED_BY_DATE);		
			break;

			
		case SORTED_BY_NAME_LESSON:
			
			show(SORTED_BY_NAME_LESSON);
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}

		
	private void downLoadSchedule () {
		
		final ProgressDialog progressDialog = new ProgressDialog(this);
		
		new AsyncTask<String, Integer, String>() {
			
			
			@Override
			   protected void onPreExecute() {
				
			    progressDialog.setMessage(getResources().getString(R.string.load_schedule));
			    progressDialog.setCancelable(false);
			    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);			 
			    progressDialog.show();
			   }
			
			

			@Override
			protected String doInBackground(String... params) {
	
				URL url;
			    HttpURLConnection urlConnection;
			    InputStream inputStream;

			    String textJson = "";
			    
			    try {
			    	
			    	url = new URL(params[0]);
			        urlConnection = (HttpURLConnection) url.openConnection();		    
			        urlConnection.setRequestMethod("GET");
			        urlConnection.setDoOutput(true);
			        urlConnection.connect();
			        
			        inputStream = urlConnection.getInputStream();
			        
			        Log.d("Alex", "responseCode " + urlConnection.getResponseCode());
			       
			        String inline = "";
			        
			        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			        
			        while ((inline = br.readLine()) != null){
			        	
			        	textJson += inline;			        	
			        }
			        
			        inputStream.close();
			        
			      //  Log.d("Alex", "JSON \n" + textJson);
			    
			        return textJson;
			        
			       } catch (MalformedURLException e) {
			        e.printStackTrace();
			        Log.d("Alex", "error " + e.toString());
			        
			       } catch (IOException e) {
			        e.printStackTrace();
			        Log.d("Alex", "error " + e.toString());
			       }
			    
				return null;
			}
			
			
			@Override
			   protected void onPostExecute(String textJson) {
			    
			    progressDialog.dismiss();
			    
			    json = textJson;
			    
			    parserJson(json);
			    
			   }
					
		}.execute(URL);
		
	
	}
	
	
	private void parserJson(String json) {
		
		try {
			
			JSONObject jobj = new JSONObject(json);	
			
//			Log.d("Alex", "success " + jobj.getString("success"));
			
			JSONObject data = jobj.getJSONObject("data");	
			
//			Log.d("Alex", "last_updated " + data.getString("last_updated"));
//			Log.d("Alex", " parity_countdown " + data.getString("parity_countdown"));
			
			JSONArray days = data.getJSONArray("days");
			
			for (int i = 0; i < days.length(); i++) {
//				
//			Log.d("Alex", " weekday " + days.getJSONObject(i).getString("weekday"));						
//			Log.d("Alex", " weekdayText " + days.getJSONObject(i).getString("weekdayText"));
			weekdayLesson = days.getJSONObject(i).getString("weekdayText");
						
			JSONArray lessons = days.getJSONObject(i).getJSONArray("lessons");
			
			for (int x = 0; x < lessons.length(); x++) {
				
//				Log.d("Alex", " ");
//				Log.d("Alex", " ");				
//				Log.d("Alex", " typeName " + lessons.getJSONObject(x).getString("typeName"));
//				Log.d("Alex", " date_end " + lessons.getJSONObject(x).getString("date_end"));
//				Log.d("Alex", " parity " + lessons.getJSONObject(x).getString("parity"));
//				Log.d("Alex", " date_start " + lessons.getJSONObject(x).getString("date_start"));	
//				Log.d("Alex", " last_updated " + lessons.getJSONObject(x).getString("last_updated"));								
//				Log.d("Alex", " subject " + lessons.getJSONObject(x).getString("subject"));
//				Log.d("Alex", " time_end " + lessons.getJSONObject(x).getString("time_end"));				
//				Log.d("Alex", " time_start " + lessons.getJSONObject(x).getString("time_start"));
				
				nameLesson = lessons.getJSONObject(x).getString("subject");
				timeEndLesson = lessons.getJSONObject(x).getString("time_end");
				timeStartLesson = lessons.getJSONObject(x).getString("time_start");
				
				JSONArray dates = lessons.getJSONObject(x).getJSONArray("dates");
				
				for (int y = 0; y < dates.length(); y++) {
					
					Lessons lesson = new Lessons(NO_SORTED);
//					Log.d("Alex", " dates " + dates.getString(y));
					dateLesson = dates.getString(y);
					
					lesson.setWeekdayLesson(weekdayLesson);
					lesson.setNameLesson(nameLesson);
					lesson.setTimeEndLesson(timeEndLesson);
					lesson.setTimeStartLesson(timeStartLesson);
					lesson.setDateLesson(dateLesson);	
					
					listNoSorted.add(lesson);
				}
				
//				JSONArray auditories = lessons.getJSONObject(x).getJSONArray("auditories");
//				
//				for (int z = 0; z < auditories.length(); z++) {
//					
//					Log.d("Alex", " auditory_address " + auditories.getJSONObject(z).getString("auditory_address"));
//					Log.d("Alex", " auditory_name " + auditories.getJSONObject(z).getString("auditory_name"));
//					Log.d("Alex", " auditory_id " + auditories.getJSONObject(z).getString("auditory_id"));					
//				}
//				
//				Log.d("Alex", " type " + lessons.getJSONObject(x).getString("type"));
//				Log.d("Alex", " lesson_id " + lessons.getJSONObject(x).getString("lesson_id"));
//				
//				JSONArray teachers = lessons.getJSONObject(x).getJSONArray("teachers");
//				
//				for (int j = 0; j < teachers.length(); j++) {
//					
//					Log.d("Alex", " teacher_name " + teachers.getJSONObject(j).getString("teacher_name"));
//					Log.d("Alex", " teacher_id " + teachers.getJSONObject(j).getString("teacher_id"));					
//				}
//				
//				Log.d("Alex", " les " + lessons.getJSONObject(x).toString());
				
			}
			
		//	Log.d("Alex", " days " + days.getJSONObject(i).toString());
						
			}
			
		} catch (JSONException e) {
			Log.d("Alex", " err " + e.toString());
			
			e.printStackTrace();
		}
	
		Log.d("Alex", " ");
		Log.d("Alex", " ");
		
		show(NO_SORTED);		
	}
	
	
	private void show(int sortedBy) {
		
		switch (sortedBy) {
		
		case NO_SORTED:
			
			for (Lessons lesson : listNoSorted) {
			
				adapter.addItem(lesson);
			}		
			
			Log.d("Alex", "adapetr " + adapter.getCount());
			
			listNoSorted.clear(); // освобождаем память - небольшая оптимизация, можно и ещё, но это если нужно 
			break;

			
		case SORTED_BY_DATE:
			
			onSortedByDay();		
			break;
			
			
		case SORTED_BY_NAME_LESSON:
			
			onSortedByName();
			break;
		}
				
	}
	
	
	private void onSortedByName() {
		
		for (int i = 0; i < adapter.getCount(); i++){
			
			Lessons lessonSortedByName = new Lessons(SORTED_BY_NAME_LESSON);
			
			lessonSortedByName.setWeekdayLesson(adapter.getItem(i).getWeekdayLesson());
			lessonSortedByName.setNameLesson(adapter.getItem(i).getNameLesson());
			lessonSortedByName.setTimeEndLesson(adapter.getItem(i).getTimeEndLesson());
			lessonSortedByName.setTimeStartLesson(adapter.getItem(i).getTimeStartLesson());
			lessonSortedByName.setDateLesson(adapter.getItem(i).getDateLesson());
			
			listSortedByName.add(lessonSortedByName);
		}
		
		adapter.clear();
		
        for (Lessons lesson : listSortedByName) {
			
			adapter.addItem(lesson);
		}		
		
        Log.d("Alex", "name " + adapter.getCount());
        
		listSortedByName.clear();	
	}
	
	
	private void onSortedByDay() {
		
		for (int iq = 0; iq < adapter.getCount(); iq++){
			
			Lessons lessonSortedByDay = new Lessons(SORTED_BY_DATE);
			
			lessonSortedByDay.setWeekdayLesson(adapter.getItem(iq).getWeekdayLesson());
			lessonSortedByDay.setNameLesson(adapter.getItem(iq).getNameLesson());
			lessonSortedByDay.setTimeEndLesson(adapter.getItem(iq).getTimeEndLesson());
			lessonSortedByDay.setTimeStartLesson(adapter.getItem(iq).getTimeStartLesson());
			lessonSortedByDay.setDateLesson(adapter.getItem(iq).getDateLesson());
			
			listSortedByDate.add(lessonSortedByDay);
		}
		
		adapter.clear();
		
        for (Lessons lesson : listSortedByDate) {
			
			adapter.addItem(lesson);
		}		
		
        Log.d("Alex", "day " + adapter.getCount());
        
		listSortedByDate.clear();
	}
	
	
	
}

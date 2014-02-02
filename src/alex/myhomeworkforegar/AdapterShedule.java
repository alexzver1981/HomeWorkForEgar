package alex.myhomeworkforegar;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterShedule extends BaseAdapter {

	private Context context;
	private ArrayList<Lessons> items = new ArrayList<Lessons>();
	
	public AdapterShedule(Context context) {
		
		this.context = context;
	}
	
	public void clear() {
		
		items.clear();
		notifyDataSetChanged();
	}

    public void addItem(Lessons obj){

		items.add(obj);
		notifyDataSetChanged();
	}
    
    public void removeItem(int position) {
    	
		items.remove(position);
		notifyDataSetChanged();
	}
    

	@Override
	public int getCount() {
		
		return items.size();
	}

	@Override
	public Lessons getItem(int position) {
		
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
		
		if (convertView == null){
			
		    convertView = ((Activity) context).getLayoutInflater().inflate(R.layout.item_shedule, null);
		    
		    holder = new ViewHolder();
		    holder.setDate((TextView) convertView.findViewById(R.id.tvdate));
		    holder.setTime((TextView) convertView.findViewById(R.id.tvtime));
		    holder.setLessonName((TextView) convertView.findViewById(R.id.tvlessonname));
		    		    
		    convertView.setTag(holder);
		}
		
		else {
			
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.getDate().setText(items.get(position).getDateLesson());
		holder.getTime().setText(items.get(position).getTimeStartLesson() + " - " + items.get(position).getTimeEndLesson());
		holder.getLessonName().setText(items.get(position).getNameLesson());
		
		return convertView;
	}
	
	
	protected class ViewHolder {
		
		private TextView date;
		private TextView time;
		private TextView lessonName;
		
		public TextView getDate() {
			return date;
		}
		
		public void setDate(TextView date) {
			this.date = date;
		} 
		
		public TextView getTime() {
			return time;
		}
		
		public void setTime(TextView time) {
			this.time = time;
		}
		
		public TextView getLessonName() {
			return lessonName;
		}
		
		public void setLessonName(TextView lessonName) {
			this.lessonName = lessonName;
		}
	}

}

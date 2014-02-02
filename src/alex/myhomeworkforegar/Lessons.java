package alex.myhomeworkforegar;



public class Lessons implements Comparable<Lessons> {

	private static final int SORTED_BY_DAY = 1;
	private static final int SORTED_BY_NAME_LESSON = 2;
	
	private String nameLesson;
	private String dateLesson; 
	private String timeStartLesson;
	private String timeEndLesson;
	private String weekdayLesson;
	private int typeComparable;
	
	public Lessons(int typeComparable) {
		this.typeComparable = typeComparable;
	}
	
	public void setNameLesson(String nameLesson) {		
		this.nameLesson = nameLesson;
	}
		
	
    public void setDateLesson(String dateLesson) {		
		this.dateLesson = dateLesson;
	}
       
    
    public void setTimeStartLesson(String timeStartLesson) {		
		this.timeStartLesson = timeStartLesson;
	}
        

    public void setTimeEndLesson(String timeEndLesson) {		
		this.timeEndLesson = timeEndLesson;
	}
        
    
    public void setWeekdayLesson(String weekdayLesson) {		
		this.weekdayLesson = weekdayLesson;
	}
    
    
    public String getNameLesson () {   	
    	return nameLesson;
    }
    
    
    public String getDateLesson () {    	
    	return dateLesson;
    }
    
    
    public String getTimeStartLesson () {   	
    	return timeStartLesson;
    }
    
    
    public String getTimeEndLesson () {   	
    	return timeEndLesson;
    }
    
    
    public String getWeekdayLesson () {  	
    	return weekdayLesson; 	
    }


	@Override
	public int compareTo(Lessons obj) {
		
		int res = 1;
		
		if (typeComparable == SORTED_BY_NAME_LESSON) {
			
			res = nameLesson.compareTo(obj.nameLesson);		
			
			if (res == 0) res = (nameLesson + dateLesson.substring(6, 10)).compareTo(obj.nameLesson + obj.dateLesson.substring(6, 10));
			if (res == 0) res = (nameLesson + dateLesson.substring(3, 5)).compareTo(obj.nameLesson + obj.dateLesson.substring(3, 5));
			if (res == 0) res = (nameLesson + dateLesson.substring(0, 2)).compareTo(obj.nameLesson + obj.dateLesson.substring(0, 2));	
			//if (res == 0) Log.d("Alex", "" + nameLesson + " " + dateLesson + " " + weekdayLesson + " " + timeStartLesson+ " " + timeEndLesson + " \n" + obj.nameLesson + " " + obj.dateLesson + " " + obj.weekdayLesson + " " + timeStartLesson + " " + timeEndLesson);
		}
		
		if (typeComparable == SORTED_BY_DAY) {

		    res = (dateLesson.substring(6, 10)).compareTo(obj.dateLesson.substring(6, 10));
		    
			if (res == 0) res = (dateLesson.substring(3, 5)).compareTo(obj.dateLesson.substring(3, 5));
			if (res == 0) res = (dateLesson.substring(0, 2)).compareTo(obj.dateLesson.substring(0, 2));
			if (res == 0) res = nameLesson.compareTo(obj.nameLesson);		
		}
		 
		
		return res;
	}


	
	
	
	
	
}

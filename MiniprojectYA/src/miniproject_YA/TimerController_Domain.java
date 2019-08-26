package miniproject_YA;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TimerController_Domain {
    String todo;
    String time;
    Calendar currTime;
    SimpleDateFormat sdf;
    Calendar requeTime;
   
    
    public String getTodo() {
        return todo;
    }
    public void settodo(String todo) {
        this.todo = todo;
    }
    public String gettime() {
        return time;
    }
    public void settime(String time) {
        this.time = time;
    }
    
    
    
}

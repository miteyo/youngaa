package NewTimer;

import java.util.ArrayList;
import java.util.Comparator;

import com.google.gson.Gson;

public class Schedule {
    String scheduleStart;
    String scheduleEnd;
    String scheduleContent;
    private String[] displaySchedule;
    int scheduleStartInt;
    int scheduleEndInt;
    

    public int getScheduleStartInt() {
        return scheduleStartInt;
    }

    public void setScheduleStartInt(int scheduleStartInt) {
        this.scheduleStartInt = scheduleStartInt;
    }

    public int getScheduleEndInt() {
        return scheduleEndInt;
    }

    public void setScheduleEndInt(int scheduleEndInt) {
        this.scheduleEndInt = scheduleEndInt;
    }

    public String getScheduleStart() {
        return scheduleStart;
    }

    public void setScheduleStart(String scheduleStart) {
        this.scheduleStart = scheduleStart;
    }

    public String getScheduleEnd() {
        return scheduleEnd;
    }

    public void setScheduleEnd(String scheduleEnd) {
        this.scheduleEnd = scheduleEnd;
    }

    public String getScheduleContent() {
        return scheduleContent;
    }

    public void setScheduleContent(String scheduleContent) {
        this.scheduleContent = scheduleContent;
    }

    public String comparingStartTime() {
        return this.getScheduleStart();
    }

    public void sortByTime(ArrayList<Schedule> scheduleList) {
        scheduleList.sort(new SortByStartTime());
    }


    public class SortByStartTime implements Comparator<Schedule> {
        public int compare(Schedule s1, Schedule s2) {
            return s1.comparingStartTime().compareTo(s2.comparingStartTime());
        }
    }
    
    public String toString() {
        Gson g = new Gson();
        return g.toJson(this);
    }

}

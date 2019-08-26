package NewTimer;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

import com.google.gson.Gson;

public class TimerDomain {
    String time;
    String todo;

   
    long hourDif;
    long minDif;
    LocalTime ld1;
    LocalTime ld2;

    public String gettime() {
        return time;
    }

    public void settime(String time) {
        this.time = time;
    }

    public String gettodo() {
        return todo;
    }

    public void settodo(String todo) {
        this.todo = todo;
    }


    public long getHourDif() {
        return hourDif;
    }

    public void setHourDif(long hourDif) {
        this.hourDif = hourDif;
    }

    public long getMinDif() {
        return minDif;
    }

    public void setMinDif(long minDif) {
        this.minDif = minDif;
    }

    public LocalTime getLd1() {
        return ld1;
    }

    public void setLd1(LocalTime ld1) {
        this.ld1 = ld1;
    }

    public LocalTime getLd2() {
        return ld2;
    }

    public void setLd2(LocalTime ld2) {
        this.ld2 = ld2;
    }
    
    public String toString() {
        Gson gSon = new Gson();
        return gSon.toJson(this);
    }
    

}

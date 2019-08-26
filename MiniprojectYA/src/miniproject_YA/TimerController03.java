package miniproject_YA;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;

public class TimerController03 {


    private static void timer() throws ParseException {
        
      
            
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm",Locale.KOREA);
            Calendar cal = Calendar.getInstance();
      
            String curTime =  sdf.format(cal.getTime()); //현재시간.

       
           int curHour = Integer.parseInt(curTime.substring(0,2));//현재시간 시
           int curMin = Integer.parseInt(curTime.substring(3)); //현재시간 분
        
           Scanner scan = new Scanner(System.in);
           
           System.out.println("원하는 시간을 24시간 형식으로 (ex: 1830) 입력해주세요.");
           String input =scan.next();
           
      
           
           int requeHour = Integer.parseInt(input.substring(0,2));
           int requeMin = Integer.parseInt(input.substring(2));

           System.out.println("현재시간 : " + curTime);
           System.out.println("일정시간: " + requeHour + ":" + requeMin);

        int remainHour;
        int remainMin;
           
           if(requeMin<curMin) {
               remainHour = (requeHour-curHour)-1;
               remainMin = 60+(requeMin-curMin);
               System.out.println("남은시간은 " +remainHour + "시간" + remainMin +"분");
           }else {
               remainHour = (requeHour-curHour);
               remainMin = (requeMin-curMin);
             System.out.println("남은시간은 " +remainHour + "시간" + remainMin +"분");
           }
    }  
    
    public static void main(String[] args) throws ParseException {
   
        timer();
  }

}

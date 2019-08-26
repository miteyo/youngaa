package NewTimer;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class TimerController {
    private static void init()  {
        
     
   
        showmenu();

    }

    private static void showmenu() {
        Scanner scan = new Scanner(System.in);
        ArrayList<TimerDomain> timerList = new ArrayList<>();
        TimerDomain t = new TimerDomain();

        boolean sw = true;
        while (sw) {

            System.out.println("타이머 프로그램");
            System.out.println("1.입력하기  2.확인하기  3.종료하기");

            int choice = scan.nextInt();
            scan.nextLine();
          
            
            while (choice > 3) {
                System.out.println("수를 다시 입력해주세요.");
                choice = scan.nextInt();
                scan.nextLine();
               
            }
            switch (choice) {
            case 1:
                insert(timerList, scan, t);
                break;
            case 2:
                showData(timerList);
                break;
            case 3:
                sw = false;
                break;

            }

        }
    }

    private static void showData(ArrayList<TimerDomain> timerList) {

        for (TimerDomain r : timerList) {

            if (r.getLd1().isAfter(r.getLd2())) {
                r.setHourDif(r.getHourDif() + 23);
                r.minDif += r.minDif < 0 ? 60 : 0;
            }
            System.out.println("\n할일: " + r.gettodo() + "\n현재시간: " + r.getLd1() + "\n일정시간: " + r.getLd2() +  "\n남은시간: "
                    + r.getHourDif() + ":" + r.getMinDif() + "\n");

        }
    }

    private static void insert(ArrayList<TimerDomain> timerList, Scanner scan, TimerDomain t) {

        boolean sw = true;
        while (sw) {
            t = new TimerDomain();
            System.out.println("할일을 입력해주세요.");
            String todo = scan.nextLine();

            System.out.println("시간을 입력해주세요.");
            String time = scan.nextLine();
            int hour = Integer.parseInt(time.substring(0, 2));
            int min = Integer.parseInt(time.substring(2));

            while (!validtime(time, hour, min)) {
                System.out.println("수를 다시 입력해주세요.");
                time = scan.nextLine();
                hour = Integer.parseInt(time.substring(0, 2));
                min = Integer.parseInt(time.substring(2));
            }

            calTime(timerList, t, hour, min);

            t.settodo(todo);
            t.settime(time);

            timerList.add(t);

            System.out.println("계속 입력하시겠습니까? 1.네    2.아니요.");
            int choice = scan.nextInt();
            scan.nextLine();
            if (choice == 2) {
                sw = false;
            } else {
                sw = true;
            }
        }
    }

    private static void calTime(ArrayList<TimerDomain> timerList, TimerDomain t, int hour, int min) {

        Calendar currTime = Calendar.getInstance();
        LocalTime ld1 = LocalTime.of(currTime.get(Calendar.HOUR_OF_DAY), currTime.get(Calendar.MINUTE));
        LocalTime ld2 = LocalTime.of(hour, min);

        long hourDif = ChronoUnit.HOURS.between(ld1, ld2); // 남은시간의'시'
        long minDif = ChronoUnit.MINUTES.between(ld1, ld2) - hourDif * 60;// 남은 시간의 '분'

        t.setHourDif(hourDif); // 남은시간의 '시'
        t.setMinDif(minDif); // 남은시간의 '분'
        t.setLd1(ld1); // 현재시간
        t.setLd2(ld2); // 요구시간

    }

 
    private static boolean validtime(String time, int hour, int min) {
        if (time.length() == 4 && time.matches("^[0-9]*$")) {
            if (hour >= 0 && hour < 24 && min >= 0 && min < 60) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        
        init();
    }
}

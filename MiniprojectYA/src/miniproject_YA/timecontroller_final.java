package miniproject_YA;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;

public class timecontroller_final {
    private static void timer() {

        boolean swi = true;
        while (swi) {

            System.out.println("남은 시간을 확인할 시각을 입력해주세요:(ex: 1530)");

            Calendar currTime = Calendar.getInstance();

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.KOREA);
            Calendar requeTime = Calendar.getInstance();
            Scanner scan = new Scanner(System.in);

            String inputTime = scan.next(); // 1530

            if (inputTime.length() == 4 && inputTime.matches("^[0-9]*$")) {

                int hour = Integer.parseInt(inputTime.substring(0, 2));
                int min = Integer.parseInt(inputTime.substring(2));
                if (hour >= 0 && hour < 24 && min >= 0 && min < 60) {

                    requeTime.set(Calendar.HOUR_OF_DAY, hour);
                    requeTime.set(Calendar.MINUTE, min);

                    System.out.println("현재시간은 " + sdf.format(currTime.getTime()) + "입니다");
                    System.out.println("일정시간은 " + sdf.format(requeTime.getTime()) + "입니다.");

                    LocalTime ld1 = LocalTime.of(currTime.get(Calendar.HOUR_OF_DAY), currTime.get(Calendar.MINUTE));
                    LocalTime ld2 = LocalTime.of(requeTime.get(Calendar.HOUR_OF_DAY), requeTime.get(Calendar.MINUTE));

                    long hourDif = ChronoUnit.HOURS.between(ld1, ld2);
                    long minDif = ChronoUnit.MINUTES.between(ld1, ld2) - hourDif * 60;

                    if (currTime.after(requeTime)) {
                        
                        if(currTime.get(Calendar.MINUTE)==requeTime.get(Calendar.MINUTE)) {
                            System.out.println("내일 일정까지 남은시간: " + (hourDif + 24) + "시간 " +  minDif + "분");
                        }
                        else {
                            
                        System.out.println("내일 일정까지 남은시간: " + (hourDif + 23) + "시간 " + (60 + minDif) + "분");
                        swi = false;
                        }

                    } else {
                        System.out.println("남은시간은 " + hourDif + "시간 " + minDif + "분입니다.");
                    swi = false;
                    }
                    
                }

                else {
                    System.out.println("다시 입력해주세요");
                    swi = true;
                }
            }
      
        
        }
    }
    
    

    public static void main(String[] args) {

        timer();

    }

}

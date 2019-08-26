package miniproject_YA;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;

public class TimerController_02 {
    //일정시간이 현재시간보다 앞선시간일 경우 막아논 버전.
    private static void timer() {
        System.out.println("남은 시간을 확인할 시각을 입력해주세요.(ex: 1530)");
        
        Calendar currTime = Calendar.getInstance(); 

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.KOREA); // 입력받고 출력하는 모든 시간 형태를 HH:mm으로 통일하기 위한 sdf
        Calendar requeTime = Calendar.getInstance(); //요청시간(requeTime)을 불러오기 위한 getInstance()
        Scanner scan = new Scanner(System.in); //요청시간을 받기위한 스캐너

        int inputTime = scan.nextInt(); //요청시간을 입력받아 input에 넣는다. ex)1530
        
 
        requeTime.set(Calendar.HOUR_OF_DAY,inputTime/100); //요청시간 1530의  1530/100의 몫인 15를 몫으로로 지정 
        requeTime.set(Calendar.MINUTE, inputTime%100); //요청시간 1530의 1530/100의 나머지인 30을 분으로 지정.

        System.out.println("현재시간: " + sdf.format(currTime.getTime())); //현재시간 한번 출력해줌
        System.out.println("일정시간: " + sdf.format(requeTime.getTime())); //요청시간 한번 출력해줌.

        LocalTime ld1 = LocalTime.of(currTime.get(Calendar.HOUR_OF_DAY), currTime.get(Calendar.MINUTE)); //ld1에 시간(HOUR_OF_DAY)과 분(MINUTE)을 넣어줌
        LocalTime ld2 = LocalTime.of(requeTime.get(Calendar.HOUR_OF_DAY), requeTime.get(Calendar.MINUTE));//ld2에 요청시간의 시간(HOUR_OF_DAY)과 분(MINUTE)을 넣어줌


        long hourDif = ChronoUnit.HOURS.between(ld1, ld2);
        long minDif = ChronoUnit.MINUTES.between(ld1, ld2) - hourDif * 60;
        
        if(currTime.get(Calendar.HOUR_OF_DAY)>=requeTime.get(Calendar.HOUR_OF_DAY)&& (
                currTime.get(Calendar.HOUR_OF_DAY)>=requeTime.get(Calendar.HOUR_OF_DAY)|| currTime.get(Calendar.MINUTE)>requeTime.get(Calendar.MINUTE))) {
            System.out.println("내일 일정까지 남은시간: " + (hourDif+23) + "시간 " + (60+minDif) + "분");
            
        } else if (currTime.get(Calendar.MINUTE) == requeTime.get(Calendar.MINUTE)) {
            System.out.println("내일 일정"
                    + ""
                    + " " + (hourDif + 24) + "시간 " + (minDif) + "분");

        } else
            System.out.println("남은시간: " + hourDif + "시간 " + minDif + "분입니다.");

    }


    public static void main(String[] args) {

        timer();

    }

}

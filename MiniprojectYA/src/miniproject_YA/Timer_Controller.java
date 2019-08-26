package miniproject_YA;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;

public class Timer_Controller {

    private static void init() {
        ArrayList<TimerController_Domain> timerList = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        showMenu(timerList, scan);

    }

    private static void showMenu(ArrayList<TimerController_Domain> timerList, Scanner scan) {
        TimerController_Domain td = new TimerController_Domain();
        while (true) {
            System.out.println("타이머 관리 프로그램");
            System.out.println("1.입력하기  2.현상황출력  3.종료");
            int choice = scan.nextInt();

            if (choice == 1) {
                insertTimer(timerList, scan, td);
            } else if (choice == 2) {
                outputData(timerList, td);
            } else if (choice == 3) {
                System.out.println("사용해주셔서 감사합니다.");
                break;
            }

        }
    }

    private static void outputData(ArrayList<TimerController_Domain> timerList, TimerController_Domain td) {

        boolean swi = true;
        while (true) {

            if (td.time.length() == 4 && td.time.matches("^[0-9]*$")) {

                int hour = Integer.parseInt(td.time.substring(0, 2));
                int min = Integer.parseInt(td.time.substring(2));
                if (hour >= 0 && hour < 24 && min >= 0 && min < 60) {

                    td.requeTime.set(Calendar.HOUR_OF_DAY, hour);
                    td.requeTime.set(Calendar.MINUTE, min);

                    System.out.println("현재시간은 " + td.sdf.format(td.currTime.getTime()) + "입니다");
                    System.out.println("일정시간은 " + td.sdf.format(td.requeTime.getTime()) + "입니다.");

                    LocalTime ld1 = LocalTime.of(td.currTime.get(Calendar.HOUR_OF_DAY),
                            td.currTime.get(Calendar.MINUTE));
                    LocalTime ld2 = LocalTime.of(td.requeTime.get(Calendar.HOUR_OF_DAY),
                            td.requeTime.get(Calendar.MINUTE));

                    long hourDif = ChronoUnit.HOURS.between(ld1, ld2);
                    long minDif = ChronoUnit.MINUTES.between(ld1, ld2) - hourDif * 60;

                    if (td.currTime.after(td.requeTime)) {

                        if (td.currTime.get(Calendar.MINUTE) == td.requeTime.get(Calendar.MINUTE)) {
                            System.out.println("내일 일정까지 남은시간: " + (hourDif + 24) + "시간 " + minDif + "분");
                        } else {

                            System.out.println("내일 일정까지 남은시간: " + (hourDif + 23) + "시간 " + (60 + minDif) + "분");
                           break;
                        }

                    } else
                        System.out.println("남은시간은 " + hourDif + "시간 " + minDif + "분입니다.");
                    break;
                }

                else {
                    System.out.println("다시 입력해주세요");
                    swi = true;
                }
            }
        }
    }

    private static void insertTimer(ArrayList<TimerController_Domain> timerList, Scanner scan, TimerController_Domain td) {
        
        boolean swi = true;
        while (true) {
            td.currTime = Calendar.getInstance();
            td.requeTime = Calendar.getInstance();
            td.sdf = new SimpleDateFormat("HH:mm", Locale.KOREA);
            Calendar currTime = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.KOREA);
//            Calendar requeTime = Calendar.getInstance();

            System.out.println("할일을 입력해주세요.");
            String todo = scan.nextLine();
        

            System.out.println("남은 시간을 확인할 시각을 입력해주세요:(ex: 1530)");
            scan.nextLine();
            String time = scan.nextLine(); // 1530

            td.settodo(todo);
            td.settime(time); // 1530

            timerList.add(td);

            System.out.println("다시입력하시겠습니까? 1.Y 2.N");
            int choice = scan.nextInt();
            if(choice==2) {
                swi = false;
            }
            
            
        }

    }

    public static void main(String[] args) {

        init();

    }

}

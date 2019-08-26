package miniproject_YA;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;

public class ReminderController03_method {
    ArrayList<String> reminderList = new ArrayList<>();
    Scanner scan = new Scanner(System.in);

    private void delete() {
        boolean flag = true;

        while (flag) {
            System.out.println("몇 번을 삭제하겠습니까?");
            int UserChoice = scan.nextInt();

            if (reminderList.size() >= (UserChoice - 1)) {
                System.out.println(UserChoice + "번째 스케줄" + reminderList.get(UserChoice - 1) + "삭제 되었습니다.");
                reminderList.remove(UserChoice - 1);
                flag = false;

            } else if (reminderList.size() < (UserChoice - 1)) {
                System.out.println("숫자를 다시 입력해주세요.");
            }
        }
        for (String s : reminderList) {
            System.out.println(s);
        }

    }

    private void timer() {

        boolean swi = true;
        while (swi) {

            System.out.println("남은 시간을 확인할 시각을 입력해주세요:(ex: 1530)");

            Calendar currTime = Calendar.getInstance();

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.KOREA);
            Calendar requeTime = Calendar.getInstance();

            String inputTime = scan.next(); // 1530

            if (inputTime.length() == 4 && inputTime.matches("^[0-9]*$")) {

                int hour = Integer.parseInt(inputTime.substring(0, 2));
                int min = Integer.parseInt(inputTime.substring(2));
                if (hour > 0 || hour < 23 || min > 0 || min < 59) {

                    hour = Integer.parseInt(inputTime.substring(0, 2));
                    min = Integer.parseInt(inputTime.substring(2));

                    requeTime.set(Calendar.HOUR_OF_DAY, hour);
                    requeTime.set(Calendar.MINUTE, min);

                    System.out.println("현재시간은 " + sdf.format(currTime.getTime()) + "입니다");
                    System.out.println("일정시간은 " + sdf.format(requeTime.getTime()) + "입니다.");

                    LocalTime ld1 = LocalTime.of(currTime.get(Calendar.HOUR_OF_DAY), currTime.get(Calendar.MINUTE));
                    LocalTime ld2 = LocalTime.of(requeTime.get(Calendar.HOUR_OF_DAY), requeTime.get(Calendar.MINUTE));

                    long hourDif = ChronoUnit.HOURS.between(ld1, ld2);
                    long minDif = ChronoUnit.MINUTES.between(ld1, ld2) - hourDif * 60;

                    if (currTime.after(requeTime)) {
                        System.out.println("내일 일정까지 남은시간: " + (hourDif + 23) + "시간 " + (60 + minDif) + "분");
                        swi = false;

                    } else
                        System.out.println("남은시간: " + hourDif + "시간 " + minDif + "분입니다.");
                    swi = false;
                } else {
                    System.out.println("다시 입력해주세요");
                    swi = true;
                }
            }
        }

    }

    private void movement() {
        if (reminderList.size() > 1) {
            for (String s : reminderList) {
                System.out.println(s);// TodoList의 임의의 값을 출력해서 한번 보여줌.
            }
            boolean flag = true; // while문을 위한 스위치를 true로 놓는다.
            while (flag) {// while문이 true일떄 반복.

                System.out.println("몇번 스케줄을 변경하기 원하십니까?");
                int UserChoice1 = scan.nextInt(); // 변경원하는 스케줄을 UserChoice1에 넣는다.

                System.out.println("해당스케줄을 몇번으로 옮기고 싶습니까?");
                int UserChoice2 = scan.nextInt(); // 바꾸고 싶은 자리를 UserChoice2에 넣는다.

                if (reminderList.size() >= UserChoice1 - 1 && reminderList.size() >= UserChoice2 - 1) { // 입력받은 값들이
                                                                                                        // List의 크기보다 작은
                                                                                                        // 경우에만 돌아가도록
                                                                                                        // 조건을 정해준다

                    String temp = reminderList.get(UserChoice1 - 1);
                    reminderList.set(UserChoice1 - 1, reminderList.get(UserChoice2 - 1));
                    reminderList.set(UserChoice2 - 1, temp);
                    System.out.println("위치가 변경되었습니다.");

                    flag = false; // 완료되면 스위치를 false로 바꾼다.

                } else if (reminderList.size() < UserChoice1 - 1 || reminderList.size() < UserChoice2 - 1) {// 만일 입력값과                                                                // 맞지않으면
                    System.out.println("수를 다시 입력해주세요"); // 다시 입력받는다.
                }
            }
        } System.out.println("리스트가 1개 이하입니다.");

        for (String s : reminderList) {
            System.out.println(s);
        }
        
        
        
    }

    public static void main(String[] args) {
        ReminderController03_method rcm = new ReminderController03_method();

        while (true) {
            System.out.println("1.작성하기 2.삭제하기 3.타이머  4.이동하기 5.종료");
            int num = rcm.scan.nextInt();
            rcm.scan.nextLine();
            if (num == 1) {

                System.out.println("할일을 입력하세요.");

                rcm.reminderList.add(rcm.scan.nextLine());

                for (String s : rcm.reminderList) {
                    System.out.println(s);
                }

            } else if (num == 2) {

                rcm.delete();

            } else if (num == 3) {

                rcm.timer();
            } else if (num == 4) {

                rcm.movement();

            } else if (num == 5) {
                System.out.println("종료합니다.");
                break;
            }
        }

    }

}

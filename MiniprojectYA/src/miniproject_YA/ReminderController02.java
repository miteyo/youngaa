package miniproject_YA;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;

public class ReminderController02 {
    
 

    public static void main(String[] args) {
        
        ArrayList<String> reminderList = new ArrayList<>();
        Scanner scan = new Scanner(System.in); 

        while (true) {
            System.out.println("1.작성하기 2.삭제하기 3.타이머  4.이동하기 5.종료");
            int num = scan.nextInt();
            scan.nextLine();
            if (num == 1) {
         
                System.out.println("할일을 입력하세요.");
         
                reminderList.add(scan.nextLine());
              
                for (String s : reminderList) {
                    System.out.println(s);
                }
                            
            } else if (num == 2) {

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
            } else if (num == 3) {

                System.out.println("일정시간을 24시간 형태로 입력하세요.(ex: 1530)");

                Calendar stdTime = Calendar.getInstance();

                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.KOREA);
                Calendar requeTime = Calendar.getInstance();

                int inputTime = scan.nextInt(); // 1530

                requeTime.set(Calendar.HOUR_OF_DAY, inputTime / 100);
                requeTime.set(Calendar.MINUTE, inputTime % 100);

                System.out.println("현재시간: " + sdf.format(stdTime.getTime()));
                System.out.println("일정시간: " + sdf.format(requeTime.getTime()));

                LocalTime ld1 = LocalTime.of(stdTime.get(Calendar.HOUR_OF_DAY), stdTime.get(Calendar.MINUTE));
                LocalTime ld2 = LocalTime.of(requeTime.get(Calendar.HOUR_OF_DAY), requeTime.get(Calendar.MINUTE));

                long hourDif = ChronoUnit.HOURS.between(ld1, ld2);
                long minDif = ChronoUnit.MINUTES.between(ld1, ld2) - hourDif * 60;

                System.out.println("남은시간: " + hourDif + "시간 " + minDif + "분");

            } else if(num==4) {
                
                
                for(String s : reminderList) {
                    System.out.println(s);//TodoList의 임의의 값을 출력해서 한번 보여줌.
                }

                 boolean flag = true; //while문을 위한 스위치를 true로 놓는다.
                 while(flag) {//while문이 true일떄 반복.

                     System.out.println("몇번 스케줄을 변경하기 원하십니까?");
                     int UserChoice1 = scan.nextInt(); //변경원하는 스케줄을 UserChoice1에 넣는다.

                     System.out.println("해당스케줄을 몇번으로 옮기고 싶습니까?");
                     int UserChoice2 = scan.nextInt(); //바꾸고 싶은 자리를 UserChoice2에 넣는다.

                         if (reminderList.size() >= UserChoice1-1 && reminderList.size() >= UserChoice2-1) { //입력받은 값들이 List의 크기보다 작은 경우에만 돌아가도록 조건을 정해준다(ex)입력값7 리스트크기-5이면 안됨.
                             
                             //MoveDomain temp = new MoveDomain();
                         
                             String temp = reminderList.get(UserChoice1-1); //임시 저장소 temp를 생성하여,temp에 UserChoice1의 인덱스값인 (UserChoice1-1)를 넣어준다.
                             reminderList.set(UserChoice1-1, reminderList.get(UserChoice2-1));//UserChoice1의 인덱스값인 (UserChoice1-1)에는 바꾸고 싶은자리인 UserChoice2(인덱스값은 UserChoice2-1)를 넣어준다.
                             reminderList.set(UserChoice2-1, temp);//UserChoice2-1 값에  temp에 있던 UserChoice1의 값을 넣어준다.
                             System.out.println("위치가 변경되었습니다.");
            
                             flag = false; //완료되면 스위치를 false로 바꾼다.
                             
                         }else if(reminderList.size() < UserChoice1-1 && reminderList.size() < UserChoice2-1) {//만일 입력값과 리스트 크기와의 조건이 맞지않으면
                                 System.out.println("수를 다시 입력해주세요"); //다시 입력받는다.
                             }
                         } 
                    for(String s : reminderList) {// 모든 실행이 완료되면 현재 list의 순서를 출력하요 보여줌으로써 바뀐 순서를 알려준다.
                     System.out.println(s);
                 }
 
            }

            
            else if (num == 5) {
                System.out.println("종료합니다.");
                break;
            }

        }
        
        
    }


}


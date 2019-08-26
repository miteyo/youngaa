package miniproject_YA;

import java.util.ArrayList;
import java.util.Scanner;

public class DeleteControl {
    
    private static void delete() {
  
       Scanner scan = new Scanner(System.in);
       
    //ArrayList<DeleteDomain> TodoList = new ArrayList<>();
       
       ArrayList<String> TodoList = new ArrayList<>(); //TodoList 생성
       TodoList.add("bob");
       TodoList.add("study");
       TodoList.add("coding");
       TodoList.add("sleep");     //총 4개의 TodoList 작성.

      for(String s : TodoList) {
          System.out.println(s);    //TodoList를 s에 넣고 s값 출력 (작성한 할일을 먼저 다 보여줌)
      }
    

        boolean flag = true; //반복문을 위한 스위치
 
        while (flag) { //스위치가 true일떄
            System.out.println("몇 번을 삭제하겠습니까?");
            int UserChoice = scan.nextInt();  //사용자가 입력한 값을 UserChoice에 넣음.
            
                if (TodoList.size() >= (UserChoice - 1)) { //todo리스트의 값(4개)가 입력한 값인 UserChoice보다 작아야지 작동가능.
                    System.out.println(UserChoice + "번째 스케줄" + TodoList.get(UserChoice - 1) + "삭제 되었습니다."); // UserChoice번째 스케줄이 UserChoice-1(인덱스값을 위해 -1)삭제되었습니다.
                    TodoList.remove(UserChoice - 1); //UserChoice에서 -1 함으로써 인덱스값을 remove메소드를 이용하여 삭제한다.
                    flag = false; //모든게 실행되면 스위치를 false로 바꾼다.
                    
                }else if (TodoList.size() < (UserChoice - 1)) { //리스트의 크기<사용자입력값이면 작동하지 않는다.(ex: 리스트크기5/사용자입력6이면 작동하지 않음)
                System.out.println("숫자를 다시 입력해주세요.");  //다시 입력받도록 유도.
                } 
        }

        for (String s : TodoList) {
            System.out.println(s); //삭제가 완료되었으면 리스트의 내용을 꼭 출력해 줌으로써 todo의 순서변동을 알려준다.

     }
       
  }

    public static void main(String[] args) {
        delete();

    }
}

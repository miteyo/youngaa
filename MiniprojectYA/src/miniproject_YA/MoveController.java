package miniproject_YA;

import java.awt.List;
import java.util.ArrayList;
import java.util.Scanner;

public class MoveController {

    private static void move() {
       
        Scanner scan = new Scanner(System.in);
        
     
        ArrayList<String> TodoList = new ArrayList<>(); //예시를 위한 임의의 TodoList


       for(String s : TodoList) {
           System.out.println(s);//TodoList의 임의의 값을 출력해서 한번 보여줌.
       }

        boolean flag = true; //while문을 위한 스위치를 true로 놓는다.
        while(flag) {//while문이 true일떄 반복.

            System.out.println("몇번 스케줄을 변경하기 원하십니까?");
            int UserChoice1 = scan.nextInt(); //변경원하는 스케줄을 UserChoice1에 넣는다.

            System.out.println("해당스케줄을 몇번으로 옮기고 싶습니까?");
            int UserChoice2 = scan.nextInt(); //바꾸고 싶은 자리를 UserChoice2에 넣는다.

                if (TodoList.size() >= UserChoice1-1 && TodoList.size() >= UserChoice2-1) { //입력받은 값들이 List의 크기보다 작은 경우에만 돌아가도록 조건을 정해준다(ex)입력값7 리스트크기-5이면 안됨.
                    
                    //MoveDomain temp = new MoveDomain();
                
                    String temp = TodoList.get(UserChoice1-1); //임시 저장소 temp를 생성하여,temp에 UserChoice1의 인덱스값인 (UserChoice1-1)를 넣어준다.
                    TodoList.set(UserChoice1-1, TodoList.get(UserChoice2-1));//UserChoice1의 인덱스값인 (UserChoice1-1)에는 바꾸고 싶은자리인 UserChoice2(인덱스값은 UserChoice2-1)를 넣어준다.
                    TodoList.set(UserChoice2-1, temp);//UserChoice2-1 값에  temp에 있던 UserChoice1의 값을 넣어준다.
                    System.out.println("위치가 변경되었습니다.");
   
                    flag = false; //완료되면 스위치를 false로 바꾼다.
                    
                }else if(TodoList.size() < UserChoice1-1 || TodoList.size() < UserChoice2-1) {//만일 입력값과 리스트 크기와의 조건이 맞지않으면
                        System.out.println("수를 다시 입력해주세요"); //다시 입력받는다.
                    }
                } 
           for(String s : TodoList) {// 모든 실행이 완료되면 현재 list의 순서를 출력하요 보여줌으로써 바뀐 순서를 알려준다.
            System.out.println(s);
        }
        
           
           
           
    }
       
    public static void main(String[] args) {
        move();

  }
        
}        

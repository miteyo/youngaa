package miniproject_YA;

import java.awt.List;
import java.util.ArrayList;
import java.util.Scanner;

public class MoveController03 {

    private static void move() {

        Scanner scan = new Scanner(System.in);

        ArrayList<String> TodoList = new ArrayList<>();

        for (String s : TodoList) {
            System.out.println(s);
        }

        boolean flag = true;
        while (flag) {

            System.out.println("몇번 스케줄을 변경하기 원하십니까?");
            int UserChoice1 = scan.nextInt();

            System.out.println("해당스케줄을 몇번으로 옮기고 싶습니까?");
            int UserChoice2 = scan.nextInt();

            
            
            if (TodoList.size() >= UserChoice1 - 1 && TodoList.size() >= UserChoice2 - 1) {

                String temp = TodoList.get(UserChoice1 - 1);
                TodoList.set(UserChoice1 - 1, TodoList.get(UserChoice2 - 1));
                TodoList.set(UserChoice2 - 1, temp);
                System.out.println("위치가 변경되었습니다.");

                flag = false;

            } else if (TodoList.size() < UserChoice1 - 1 || TodoList.size() < UserChoice2 - 1) {
                System.out.println("수를 다시 입력해주세요");
            }
        }
        for (String s : TodoList) {
            System.out.println(s);
        }

    }

    public static void main(String[] args) {
        move();

    }

}

package NewTimer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ReminderController {
    public void init(ArrayList<ReminderDomain> reminderList) throws NumberFormatException, IOException, ParseException {

//        ReminderView rv = new ReminderView();
//        rv.reading();

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        showmenu(reminderList, input);

        // 파일 입력
        String str = reminderList.toString();
        BufferedWriter bWriter = new BufferedWriter(new FileWriter("test2.txt"));
        bWriter.write(str);
        bWriter.close();

    }

    private void showmenu(ArrayList<ReminderDomain> reminderList, BufferedReader input)
            throws NumberFormatException, IOException, ParseException {
        boolean flag = true;
        while (flag) {
            System.out.println("=======REMINDER=======");
            System.out.println("1.쓰기 2.상세보기 3.뒤로가기  ");
            System.out.println("======================");
            System.out.print("번호를 입력해주세요: ");
            String choice = input.readLine();

            while (!validateChoiceNum(choice)) {
                System.out.println("잘못된 형식입니다");
                System.out.print("번호를 다시 입력해주세요: ");
                choice = input.readLine();
            }
            switch (choice) {
            case "1":
                write(reminderList, input);
                break;
            case "2":
                showDetailMenu(reminderList, input);
                break;
            case "3":// 뒤로가기
                flag = false;
                break;
            default:
                System.out.println("잘못 입력하셨습니다. 다시 입력해주세요");
            }
        }
    }

    private boolean validateChoiceNum(String choice) {// 선택하는 번호 검사 메소드
        if (choice.matches("^[0-9]*$")) {
            if (Integer.parseInt(choice) > 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    private void showDetailMenu(ArrayList<ReminderDomain> reminderList, BufferedReader input)
            throws NumberFormatException, IOException {
        boolean flag = true;
        while (flag) {
            System.out.println("-------------------------상세보기------------------------");
            System.out.println("1.삭제 2.완료체크하기 3.중요도순보기 4.완료목록보기 5.이동 6.뒤로가기 ");
            System.out.println("-------------------------------------------------------");
            String choice = input.readLine();

            while (!validateChoiceNum(choice)) {
                System.out.println("잘못된 형식입니다");
                System.out.print("번호를 다시 입력해주세요: ");
                choice = input.readLine();
            }
            switch (choice) {
            case "1":
                delete(reminderList, input);
                break;
            case "2":
                completeCheck(reminderList, input);
                break;
            case "3":
                sortPriority(reminderList);
                break;
            case "4":
                sortComplete(reminderList);
                break;
            case "5":
                moveList(reminderList, input);
                break;
            case "6":
                flag = false;
                break;
            default:
                System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
            }
        }
    }

    private void moveList(ArrayList<ReminderDomain> reminderList, BufferedReader input)
            throws NumberFormatException, IOException {
        if (reminderList.size() > 1) {
            for (int i = 0; i < reminderList.size(); i++) {// 리스트 보여주기
                System.out.println((i + 1) + ". " + reminderList.get(i).getTodo());
            }
            while (true) {
                System.out.print("이동할 리스트 번호를 입력해주세요: ");
                int moveNum1 = Integer.parseInt(input.readLine());// 변경할 리스트 번호입력

                System.out.print("이동할 번호를 입력해주세요: ");
                int moveNum2 = Integer.parseInt(input.readLine());// 바꾸고 싶은 자리 번호 입력

                if (reminderList.size() >= (moveNum1 - 1) && reminderList.size() >= (moveNum2 - 1)) {// 입력받은 값들이 list의
                                                                                                     // 크기 보다
                                                                                                     // 작은 경우에만 돌아가도록
                    ReminderDomain temp = reminderList.get(moveNum1 - 1);//
                    reminderList.set(moveNum1 - 1, reminderList.get(moveNum2 - 1));
                    reminderList.set(moveNum2 - 1, temp);
                    System.out.println("이동완료!");

                    break;
                } else if (reminderList.size() < moveNum1 - 1 || reminderList.size() < moveNum2 - 1) {
                    System.out.println("다시 입력해주세요 : ");
                }
            }
            for (int i = 0; i < reminderList.size(); i++) {
                System.out.println((i + 1) + ". " + reminderList.get(i).getTodo());
            }

        } else if (reminderList.size() == 1) {
            for (ReminderDomain list : reminderList) {
                System.out.println(list.getTodo());
            }
            System.out.println("이동할 공간이 없습니다.");
        } else {
            System.out.println("저장된 데이터가 없습니다.");
        }
    }

    private void delete(ArrayList<ReminderDomain> reminderList, BufferedReader input)
            throws NumberFormatException, IOException {
        if (reminderList.size() > 0) {

            System.out.println("삭제하기를 선택하셨습니다");
            for (int i = 0; i < reminderList.size(); i++) {// 리스트 보여주기
                System.out.println((i + 1) + ". " + reminderList.get(i).getTodo());
            }
            while (true) {
                System.out.println("삭제할 리스트 번호를 입력해주세요: ");
                int deleteNum = Integer.parseInt(input.readLine());

                if (reminderList.size() >= (deleteNum - 1)) {// 리스트 값이 작아야 작동
                    if (reminderList.size() > (deleteNum - 1)) {

                        System.out.println(
                                deleteNum + "번 리스트 " + reminderList.get(deleteNum - 1).getTodo() + "(이)가 삭제되었습니다.");
                        reminderList.remove(deleteNum - 1);// 리스트 삭제
                        break;
                    } else {
                        System.out.print("잘못입력하셨습니다.");
                    }
                } else if (reminderList.size() < (deleteNum - 1)) {
                    System.out.print("잘못입력하셨습니다.");
                }
            }
            for (ReminderDomain list : reminderList) {
                System.out.println(list.getTodo());
            }
        } else {
            System.out.println("저장된 데이터가 없습니다.");
        }
    }

    private void sortComplete(ArrayList<ReminderDomain> reminderList) {
        if (reminderList.size() > 0) {
            System.out.println("완료목록보기를 선택하였습니다.");
            System.out.println("-----완료안한 목록-----");
            for (ReminderDomain incomplete : reminderList) {
                if (!incomplete.getCheckValue()) {
                    System.out.println(incomplete.getTodo());
                }
            }
            System.out.println("-----완료 목록-----");
            for (ReminderDomain complete : reminderList) {
                if (complete.getCheckValue()) {
                    System.out.println(complete.getTodo());
                }
            }
        } else {
            System.out.println("저장된 데이터가 없습니다.");
        }
    }

    private void sortPriority(ArrayList<ReminderDomain> reminderList) {
        if (reminderList.size() > 0) {

            System.out.println("====================");

            System.out.println("중요도 : 높음(***)");
            for (ReminderDomain high : reminderList) {
                if (high.getPriority().equals("1")) {
                    System.out.println(high.getTodo());
                }
            }
            System.out.println("====================");
            System.out.println("중요도 : 보통(**)");
            for (ReminderDomain mid : reminderList) {
                if (mid.getPriority().equals("2")) {
                    System.out.println(mid.getTodo());
                }
            }
            System.out.println("====================");
            System.out.println("중요도 : 낮음(*)");
            for (ReminderDomain low : reminderList) {
                if (low.getPriority().equals("3")) {
                    System.out.println(low.getTodo());
                }
            }
            System.out.println("====================");
        } else {
            System.out.println("저장된 데이터가 없습니다.");
        }
    }

    private void completeCheck(ArrayList<ReminderDomain> reminderList, BufferedReader input)
            throws NumberFormatException, IOException {
        if (reminderList.size() > 0) {
            System.out.println("-----TO DO LIST-----");
            for (int i = 0; i < reminderList.size(); i++) {
                System.out.println((i + 1) + ". " + reminderList.get(i).getTodo());
            }
            while (true) {
                System.out.print("완료하신 리스트 번호를 입력해주세요: ");
                int listNum = Integer.parseInt(input.readLine());
                if (listNum > 0) {
                    if (reminderList.size() >= (listNum - 1)) {
                        if (reminderList.size() > (listNum - 1)) {
                            if (reminderList.get(listNum - 1).getCheckValue()) {
                                System.out.println("이미 완료되었습니다.다시 입력해주세요:");
                            } else {

                                reminderList.get(listNum - 1).setCheckValue(true);
                                System.out.println((listNum) + "번이 완료체크 되었습니다.");

                                break;
                            }
                        } else {
                            System.out.print("잘못입력하셨습니다.");
                        }
                    } else if (reminderList.size() < (listNum - 1)) {
                        System.out.println("잘못입력하셨습니다.");
                    }
                } else {
                    System.out.println("잘못입력하셨습니다.");
                }
            }
        } else {
            System.out.println("저장된 데이터가 없습니다.");
        }
    }

    private void write(ArrayList<ReminderDomain> reminderList, BufferedReader input)
            throws IOException, NumberFormatException, ParseException {
        while (true) {// 중요도 디데이 데이터 입력
            ReminderDomain rd = new ReminderDomain();

            System.out.print("중요도를 입력해주세요(1.높음 2.보통 3.낮음): ");
            String priority = input.readLine();

            while ((!validateChoiceNum(priority)) || (Integer.parseInt(priority)) > 3) {
                System.out.print("잘못된 형식입니다. 다시 입력해주세요: ");
                priority = input.readLine();
            }

            rd.setPriority(priority);

            System.out.print("할 일을 입력해주세요: ");
            String todo = input.readLine();
            while (todo.equals("")) {
                System.out.print("입력된 데이터가 없습니다. 다시 입력해주세요: ");
                todo = input.readLine();
            }
            rd.setTodo(todo);

            System.out.print("D-Day를 입력하시겠습니까? (1.yes 2.no): ");
            String inputDaychoice = input.readLine();

            while ((!validateChoiceNum(inputDaychoice)) || Integer.parseInt(inputDaychoice) > 2) {
                System.out.print("잘못된 형식입니다. 다시 입력해주세요: ");
                inputDaychoice = input.readLine();
            }

            if (inputDaychoice.equals("1")) {
                int d_day = dayCal(input);
                rd.setDay(d_day);
                System.out.println("입력하신 일정까지 " + d_day + "일 남았습니다.");

            }
            reminderList.add(rd);

            System.out.print("계속 입력하시겠습니까 ? (1.yes 2.no): ");
            String choice = input.readLine();
            while ((!validateChoiceNum(choice)) || Integer.parseInt(choice) > 2) {
                System.out.print("잘못된 형식입니다. 다시 입력해주세요: ");
                choice = input.readLine();
            }
            if (choice.equals("2")) {
                break;
            }
        }

    }

    private int dayCal(BufferedReader input) throws NumberFormatException, IOException, ParseException {// 디데이 계산 메소드
        while (true) {
            Calendar currentDay = Calendar.getInstance();

            SimpleDateFormat sdf = new SimpleDateFormat("MMdd");

            Calendar dDay = Calendar.getInstance();
            System.out.print("D-Day 날짜를 입력해주세요(MMdd): ");
            String inputDay = input.readLine();

            while (!validateDay(inputDay)) {
                System.out.println("잘못된 형식입니다.");
                System.out.print("D-Day 날짜를 다시 입력해주세요(MMdd): ");
                inputDay = input.readLine();
            }

            dDay.setTime(sdf.parse(inputDay));

            System.out.println("현재날짜: " + sdf.format(currentDay.getTime()));
            System.out.println("일정날짜: " + sdf.format(dDay.getTime()));

            // System.out.println("남은 일수: " + (dDay.get(Calendar.DAY_OF_YEAR) -
            // currentDay.get(Calendar.DAY_OF_YEAR)));
            int diffDay = dDay.get(Calendar.DAY_OF_YEAR) - currentDay.get(Calendar.DAY_OF_YEAR);
            return diffDay;

        }

    }

    private boolean validateDay(String inputDay) {
        if (inputDay.length() == 4 && inputDay.matches("^[0-9]*$")) {
            int month = Integer.parseInt(inputDay.substring(0, 2));
            int date = Integer.parseInt(inputDay.substring(2));

            if (month > 12 || month < 1 || date > 31 || date < 1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }

    }

}

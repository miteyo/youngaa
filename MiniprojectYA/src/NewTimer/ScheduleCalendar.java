package NewTimer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.google.gson.Gson;

public class ScheduleCalendar {

    // firstDayOfMonth : 해당연도의 1월 1일의 요일을 표시하는 필드변수
    // 0:일요일/1:월요일/2:화요일/3:수요일/4:목요일/5:금요일/6:토요일/
    private int firstDayOfMonth;
    private int firstDayOfMonthParameter;
    private int firstDayOfPreviousMonth;
    private int firstDayOfNextMonth;

    private String[] displayedDates = new String[7];
    private String[][] displayHrsAndMins = new String[7][96];
    private String[][] displayedContents = new String[7][96];
    private int year;

    // 특정 날짜에 대한 해당연도부터 1월 1일부터의 누적일수를 구하기 위한 배열.
    private int[] datesOfMonth = new int[12];

    // 달력 출력을 위해 사용되는 week 배열.
    // 처음에는 모두 공백으로 채워지고 달력을 그리기 전에 숫자로 대체된다.
    private String[][] week = new String[6][7];
    private String[][] previousWeek = new String[6][7];
    private String[][] nextWeek = new String[6][7];

    // 입력 관련
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private String input;
    private String inputYear;
    private String inputMonth;
    private String inputCurrentDate;
    private String inputStrForDigitChanging;
    private String inputTime;
    private String scheduleKey;
    private int inputYearInt;
    private int inputMonthInt;
    private int parsingNumber;
    
    boolean checkIfPreWeekDisplayed;
    boolean checkIfNextWeekDisplayed;

    public int getFirstDayOfPreviousMonth() {
        return firstDayOfPreviousMonth;
    }

    public void setFirstDayOfPreviousMonth(int firstDayOfPreviousMonth) {
        this.firstDayOfPreviousMonth = firstDayOfPreviousMonth;
    }

    public int getFirstDayOfNextMonth() {
        return firstDayOfNextMonth;
    }

    public void setFirstDayOfNextMonth(int firstDayOfNextMonth) {
        this.firstDayOfNextMonth = firstDayOfNextMonth;
    }

    public int getFirstDayOfMonth() {
        return firstDayOfMonth;
    }

    public void setFirstDayOfMonth(int firstDayOfMonth) {
        this.firstDayOfMonth = firstDayOfMonth;
    }

    private void changeCalendar(ScheduleCalendar cal) throws IOException {
        // 날짜 초기화
        for (int j = 0; j < cal.week.length; j++) {
            for (int i = 0; i < cal.week[j].length; i++) {
                cal.week[j][i] = "";
                cal.previousWeek[j][i] = "";
                cal.nextWeek[j][i] = "";
            }
        }
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        while (true) {
            System.out.print("출력할 연도 입력(YYYY): ");
            inputYear = br.readLine();
            
            if (checkBlank(inputYear)) {
                System.out.println("잘못된 입력입니다.");
            } else if (!validateYear(inputYear)) {
                System.out.println("잘못된 입력입니다.");
            } else {
                inputYearInt = Integer.parseInt(inputYear);
                break;
            }
        }
        
        
        while (true) {
            
            while (true) {
                //반복하고 싶은 내용
                System.out.print("출력할 월 입력(MM): ");
                inputMonth = br.readLine();
                
                if (checkBlank(inputMonth)) {
                    System.out.println("잘못된 입력입니다.");
                } else if (!validateMonth(inputMonth)) {
                    //잘못 되었을 때 표시할 내용
                    System.out.println("잘못된 입력입니다.");
                } else {
                    //맞으면 while문 빠져나오기
                    break;
                }
                
            }
            
            changeOneDigitIntoTwoDigit(inputMonth);

            inputMonth = inputStrForDigitChanging;
            inputMonthInt = Integer.parseInt(inputMonth);

            if (inputMonthInt > 12) {
                System.out.println("잘못된 숫자입니다.");
            } else {
                break;
            }
        }

        // firstDayOfMonth에 해당 월의 첫 날의 요일을 넘겨줌
        cal.takeTheFirstDay(inputYearInt, inputMonthInt);
        firstDayOfMonth = firstDayOfMonthParameter;

        // 1년 1월 에는 전월을 가져오면 안되고, 1월달에는 전월이 12월을, 12월달에는 다음달이 1월을 가져와야함
        if (inputYearInt == 1) {
            // 1년에는 전월이 없으므로 다음달의 firstDayOfMonth만을 받음
            cal.takeTheFirstDay(inputYearInt, inputMonthInt + 1);
            firstDayOfNextMonth = firstDayOfMonthParameter;
        } else if (inputMonthInt == 1) {
            // 전월의 firstDayOfMonth를 받음
            cal.takeTheFirstDay(inputYearInt - 1, inputMonthInt + 11);
            firstDayOfPreviousMonth = firstDayOfMonthParameter;

            // 다음달의 firstDayOfMonth를 받음
            cal.takeTheFirstDay(inputYearInt, inputMonthInt + 1);
            firstDayOfNextMonth = firstDayOfMonthParameter;
        } else if (inputMonthInt == 12) {
            // 전월의 firstDayOfMonth를 받음
            cal.takeTheFirstDay(inputYearInt, inputMonthInt - 1);
            firstDayOfPreviousMonth = firstDayOfMonthParameter;

            // 다음달의 firstDayOfMonth를 받음
            cal.takeTheFirstDay(inputYearInt + 1, inputMonthInt - 11);
            firstDayOfNextMonth = firstDayOfMonthParameter;
        } else {
            // 전월의 firstDayOfMonth를 받음
            cal.takeTheFirstDay(inputYearInt, inputMonthInt - 1);
            firstDayOfPreviousMonth = firstDayOfMonthParameter;

            // 다음달의 firstDayOfMonth를 받음
            cal.takeTheFirstDay(inputYearInt, inputMonthInt + 1);
            firstDayOfNextMonth = firstDayOfMonthParameter;
        }

        // 달력을 출력하기 위해 6행X7열의 배열에 각각 해당 연도/월의 일수를 할당
        // weekDayCount : 해당 월의 첫번째 날짜가 일요일이 아닐 수 있으므로
        // 첫번째 날짜부터 해서 일요일까지 출력되고 줄바꿈을 하기 위해 사용되는 변수
        int weekDayCount = cal.getFirstDayOfMonth();
        int previousWeekDayCount = cal.getFirstDayOfPreviousMonth();
        int nextWeekDayCount = cal.getFirstDayOfNextMonth();

        // 해당 월의 날짜를 출력하기 전에 week배열에 넣어주기 위한 날짜 카운트
        // (1월은 31일 까지, 2월은 28일 까지...)
        allocateDaysOnMonths(weekDayCount, week, inputMonthInt);
        if (inputYearInt == 1) {
            if (inputMonthInt == 12) {
                allocateDaysOnMonths(nextWeekDayCount, nextWeek, inputMonthInt - 11);
            }
        } else if (inputMonthInt == 1) {
            allocateDaysOnMonths(previousWeekDayCount, previousWeek, inputMonthInt + 11);
            allocateDaysOnMonths(nextWeekDayCount, nextWeek, inputMonthInt + 1);
        } else if (inputMonthInt == 12) {
            allocateDaysOnMonths(previousWeekDayCount, previousWeek, inputMonthInt - 1);
            allocateDaysOnMonths(nextWeekDayCount, nextWeek, inputMonthInt - 11);
        } else {
            allocateDaysOnMonths(previousWeekDayCount, previousWeek, inputMonthInt - 1);
            allocateDaysOnMonths(nextWeekDayCount, nextWeek, inputMonthInt + 1);
        }
    }

    public void takeTheFirstDay(int inputYearInt, int inputMonthInt) throws IOException {
        // 각 월의 날짜들 대입
        Calendar cal2 = Calendar.getInstance();
        for (int i = 0; i < 12; i++) {
            cal2.set(Calendar.MONTH, i);
            datesOfMonth[i] = cal2.getActualMaximum(Calendar.DAY_OF_MONTH);
        }

        // 1월이 아닌 달의 누적일수를 계산
        int dateSum = 0;
        for (int i = 0; i < inputMonthInt - 1; i++) {
            dateSum += datesOfMonth[i];
        }

        // 첫 해인 1년은 더해지는 해가 없으므로 -1
        year = inputYearInt - 1;
        firstDayOfMonthParameter = (year * 365 + year / 4 - year / 100 + year / 400 + 1 + dateSum) % 7;
    }

    private void allocateDaysOnMonths(int weekDayCount, String[][] week, int inputMonthInt) {
        int dayPlus = 1;
        for (int j = 0; j < week.length; j++) {
            if (weekDayCount != 0) {
                for (int i = weekDayCount; i < week[0].length; i++) {
                    week[j][i] = Integer.toString(dayPlus);
                    dayPlus++;
                }
                // 첫번째 줄이 끝났으므로 그 주의 카운트를 0으로 돌려놓는다.
                weekDayCount = 0;
            } else {
                for (int i = 0; i < week[j].length; i++) {
                    if (dayPlus > datesOfMonth[inputMonthInt - 1]) {
                        break;
                    } else {
                        week[j][i] = Integer.toString(dayPlus);
                        dayPlus++;
                    }
                }
            }
        }
    }

    private void printCalendar(ScheduleCalendar cal) throws IOException {
        System.out.println("\n\t\t       "+inputYear);
        System.out.println("\t\t        "+inputMonth);
        System.out.println("Su\tMo\tTu\tWe\tTh\tFr\tSa");

        for (int j = 0; j < cal.week.length; j++) {
            for (int i = 0; i < cal.week[j].length; i++) {
                System.out.print(cal.week[j][i] + "\t");
                if (i != 0 && i % 6 == 0) {
                    System.out.println();
                }
            }
        }
    }

    private void input(BufferedReader br, ScheduleCalendar cal) throws IOException {
        boolean loopSwitch = true;
        while (loopSwitch) {
            int input2=0;
            
            while (true) {
                System.out.println("1.상세보기/2.달력변경/9.뒤로가기");
                String input = br.readLine();
                
                if (checkBlank(input)) {
                    System.out.println("잘못된 입력입니다.");
                } else if (!validateSelect(input)) {
                    //잘못 되었을 때 표시할 내용
                    System.out.println("잘못된 입력입니다.");
                } else {
                    this.input=input;
                    input2 = Integer.parseInt(input);
                    break;
                }
            }

            if ((input2 >= 1 && input2 <= 2) || input2 == 9) {
                changeTwoDigitIntoOneDigit(input);
                this.input = inputStrForDigitChanging;
                loopSwitch = false;
            } else {
                System.out.println("잘못된 숫자입니다.");
            }
        }
    }

    private void showDetail(BufferedReader br, ScheduleCalendar cal, ArrayList<Schedule> scheduleList,
            HashMap<String, ArrayList<Schedule>> mapList) throws IOException {
        boolean loopSwitch = true;
        while (loopSwitch) {
            System.out.println("날짜를 2자리로 입력하세요. (DD)");
            inputCurrentDate = br.readLine();

            if (checkBlank(inputCurrentDate)) {
                System.out.println("잘못된 입력입니다.");
            } else if (inputCurrentDate.length() > 2) {
                System.out.println("잘못된 입력입니다.");
            } else {
                loopSwitch = false;
            }
        }
        validateSelect(inputCurrentDate);

        // 현재 날짜의 캘린더를 표시하기전 입력된 글짜가 01,02 등 두 자리면 1자리로 변환
        changeTwoDigitIntoOneDigit(inputCurrentDate);
        String inputStr2 = inputStrForDigitChanging;

        // 하지만 만약 입력한 숫자가 1자리라면 HashMap의 key를 20190105등으로 쓰기 위해 '0'을 붙여 두자리로 변환
        changeOneDigitIntoTwoDigit(inputCurrentDate);
        inputCurrentDate = inputStrForDigitChanging;

        cal.showTheDate(inputStr2, cal, br, scheduleList, mapList);
    }

    private void showTheDate(String inputStr2, ScheduleCalendar cal, BufferedReader br, ArrayList<Schedule> scheduleList,
            HashMap<String, ArrayList<Schedule>> mapList) throws IOException {
        String[] markingTheChosenDayOfThisWeek = { "", "", "", "", "", "", "" };
        String[] thisWeekDisplay = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };

        // dateOfThisWeek: 해당 월의 몇번째 주인지를 나타냄. (0~5)
        // 첫번째 주 전체에 날짜가 하나도 안 들어가면 첫번째 주가 0번 인덱스가 아닐 경우에 주의
        int dateOfThisWeek = 0;
        for (int j = 0; j < cal.week.length; j++) {
            for (int i = 0; i < cal.week[j].length; i++) {
                if (cal.week[j][i].equals(inputStr2)) {
                    markingTheChosenDayOfThisWeek[i] = "*";
                    dateOfThisWeek = j;
                }
            }
        }

        // ============================
        for (int i = 1; i <= 204; i++) {
            System.out.print("-");
        }
        System.out.println();

        for (int i = 0; i < markingTheChosenDayOfThisWeek.length; i++) {
            // 날짜를 표기하기 전 공백을 주는 부분
            System.out.print("|");
            if (markingTheChosenDayOfThisWeek[i].equals("")) {
                for (int l = 1; l <= 11; l++) {
                    System.out.print(" ");
                }
            } else {
                for (int l = 1; l <= 10; l++) {
                    System.out.print(" ");
                }
            }

            // 선택한 날짜가 첫번째 주인 경우 전월의 마지막 주 날짜들을 표시
            // 선택한 날짜가 마지막 주인 경우 다음달의 첫번째 주 날짜들의 표시
            int preAndNextWeekCheckCounter = 0;
            if (week[dateOfThisWeek][i].equals("")) {
                if (Integer.parseInt(inputStr2) >= 21) {
                    for (int j = 0; j < 7; j++) {
                        if (!nextWeek[0][j].equals("")) {
                            preAndNextWeekCheckCounter++;
                        }
                    }
                    if (preAndNextWeekCheckCounter == 0) {
                        System.out.print("0" + nextWeek[1][i] + " " + thisWeekDisplay[i]);
                        // 출력되는 날짜들의 배열을 저장해 두기위해 넘겨줌
                        checkIfNextWeekDisplayed = true;
                        displayedDates[i] = nextWeek[1][i];

                        for (int l = 1; l <= 11; l++) {
                            System.out.print(" ");
                        }

                    } else {
                        System.out.print("0" + nextWeek[0][i] + " " + thisWeekDisplay[i]);

                        // 출력되는 날짜들의 배열을 저장해 두기위해 넘겨줌
                        checkIfNextWeekDisplayed = true;
                        displayedDates[i] = nextWeek[0][i];
                        
                        for (int l = 1; l <= 11; l++) {
                            System.out.print(" ");
                        }
                    }
                } else if (Integer.parseInt(inputStr2) <= 7) {
                    for (int j = 0; j < 7; j++) {
                        if (!previousWeek[5][j].equals("")) {
                            preAndNextWeekCheckCounter++;
                        }
                    }
                    if (preAndNextWeekCheckCounter == 0) {
                        if (inputYearInt == 1 && inputMonthInt == 1) {
                            System.out.print("  " + thisWeekDisplay[i] + " ");
                        } else {
                            System.out.print(previousWeek[4][i] + " " + thisWeekDisplay[i]);

                            // 출력되는 날짜들의 배열을 저장해 두기위해 넘겨줌
                            checkIfPreWeekDisplayed = true;
                            displayedDates[i] = previousWeek[4][i];
                        }
                        for (int l = 1; l <= 11; l++) {
                            System.out.print(" ");
                        }
                    } else {
                        System.out.print(previousWeek[5][i] + " " + thisWeekDisplay[i]);

                        // 출력되는 날짜들의 배열을 저장해 두기위해 넘겨줌
                        checkIfPreWeekDisplayed = true;
                        displayedDates[i] = previousWeek[5][i];

                        for (int l = 1; l <= 11; l++) {
                            System.out.print(" ");
                        }
                    }
                }
            } else {
                // 그리고 선택한 달의 선택한 주의 나머지 날짜들을 표시
                // (저번달이나 다음달과 상관이 없으면 처음부터 끝까지 선택한 주를 표시한다.)
                // 1~9 사이의 날짜면 0을 더해줄 것 (자리 맞추기)
                if (Integer.parseInt(week[dateOfThisWeek][i]) < 10) {
                    week[dateOfThisWeek][i] = "0" + week[dateOfThisWeek][i];
                }
                // 출력되는 날짜들의 배열을 저장해 두기위해 넘겨줌
                displayedDates[i] = week[dateOfThisWeek][i];

                System.out.print(week[dateOfThisWeek][i] + " " + thisWeekDisplay[i] + markingTheChosenDayOfThisWeek[i]);

                for (int l = 1; l <= 11; l++) {
                    System.out.print(" ");
                }
            }
        }
        System.out.println("|");

        for (int i = 1; i <= 204; i++) {
            System.out.print("-");
        }
        System.out.println();
        // ============================

        for (int j = 0; j < displayHrsAndMins[0].length; j++) {
            for (int i = 0; i < 7; i++) {
                displayedContents[i][j] = "";
            }
        }

        for (int j = 0; j < 7; j++) {
            for (int i = 0; i < displayHrsAndMins[j].length; i++) {
                if (i % 4 == 0) {
                    // hr
                    if (i < 40) {
                        displayHrsAndMins[j][i] = "|Hr" + i / 4 + " |";
                    } else {
                        displayHrsAndMins[j][i] = "|Hr" + i / 4 + "|";
                    }
                } else if (i % 4 == 1) {
                    // 15m
                    displayHrsAndMins[j][i] = "| 15m|";
                } else if (i % 4 == 2) {
                    // 30m
                    displayHrsAndMins[j][i] = "| 30m|";
                } else if (i % 4 == 3) {
                    // 45m
                    displayHrsAndMins[j][i] = "| 45m|";
                }
            }
            // mapList에 무언가가 들어있으면 리스트를 체크해서 시간과 맞는 부분에 별을 마킹
            if (mapList.get(inputYear + inputMonth + displayedDates[j]) != null) {
                // k는 0부터 mapList의 키에 해당하는 부분에 값이 있는 만큼 반복
                for (int k = 0; k < mapList.get(inputYear + inputMonth + displayedDates[j]).size(); k++) {
                    int contentsDisplayCount = 0;
                    for (int i = 0; i < displayHrsAndMins[j].length; i++) {
                        int scheduleStartLv = mapList.get(inputYear + inputMonth + displayedDates[j])
                                .get(k).scheduleStartInt;
                        int scheduleEndLv = mapList.get(inputYear + inputMonth + displayedDates[j])
                                .get(k).scheduleEndInt;
                        String scdSLV = mapList.get(inputYear + inputMonth + displayedDates[j])
                                .get(k).scheduleStart;
                        String scdELV = mapList.get(inputYear + inputMonth + displayedDates[j])
                                .get(k).scheduleEnd;
                        String scheduleContentLv = mapList.get(inputYear + inputMonth + displayedDates[j])
                                .get(k).scheduleContent;

                        // 시간 부분을 체크하기 위해 1430등으로 입력된 스타트 타임을 100으로 나눈 몫을 체크하여
                        // 표시된 시간과 맞으면 if문으로 들어감 (시작시간)
                        if (scheduleStartLv / 100 == i / 4) {

                            // 시간이 아닌 분쪽을 체크. 분을 15로 나눠서 몫이
                            // 0이 나오면 시간쪽에 체크
                            // 1이 나오면 15분보다 크므로 15m에 체크
                            // 2가 나오면 30분보다 크므로 30m에 체크
                            // 3이 나오면 45분보다 크므로 45m에 체크
                            // 그것을 각각 해당하는 배열이 나오는 순간에 매칭시켜서 값을 넣어줌
                            if (i % 4 == 3 && (scheduleStartLv % 100) / 15 <= 3) {
                                if (contentsDisplayCount == 0) {
                                    displayedContents[j][i] += "* " + "("+(k + 1)+")" + " " + scdSLV.substring(0, 2) + ":"
                                            + scdSLV.substring(2,4) + "~" + scdELV.substring(0, 2) + ":"
                                            + scdELV.substring(2,4);
                                    contentsDisplayCount++;
                                }else if (contentsDisplayCount==1) {
                                    displayedContents[j][i] += "* " + scheduleContentLv;
                                    contentsDisplayCount++;
                                }else {
                                    displayedContents[j][i] += "* ";
                                }
                            } else if (i % 4 == 2 && (scheduleStartLv % 100) / 15 <= 2) {
                                if (contentsDisplayCount == 0) {
                                    displayedContents[j][i] += "* " + "("+(k + 1)+")" + " " + scdSLV.substring(0, 2) + ":"
                                            + scdSLV.substring(2,4) + "~" + scdELV.substring(0, 2) + ":"
                                            + scdELV.substring(2,4);
                                    contentsDisplayCount++;
                                }else if (contentsDisplayCount==1) {
                                    displayedContents[j][i] += "* " + scheduleContentLv;
                                    contentsDisplayCount++;
                                }else {
                                    displayedContents[j][i] += "* ";
                                }
                            } else if (i % 4 == 1 && (scheduleStartLv % 100) / 15 <= 1) {
                                if (contentsDisplayCount == 0) {
                                    displayedContents[j][i] += "* " + "("+(k + 1)+")" + " " + scdSLV.substring(0, 2) + ":"
                                            + scdSLV.substring(2,4) + "~" + scdELV.substring(0, 2) + ":"
                                            + scdELV.substring(2,4);
                                    contentsDisplayCount++;
                                }else if (contentsDisplayCount==1) {
                                    displayedContents[j][i] += "* " + scheduleContentLv;
                                    contentsDisplayCount++;
                                }else {
                                    displayedContents[j][i] += "* ";
                                }
                            } else if (i % 4 == 0 && (scheduleStartLv % 100) / 15 <= 0) {
                                if (contentsDisplayCount == 0) {
                                    displayedContents[j][i] += "* " + "("+(k + 1)+")" + " " + scdSLV.substring(0, 2) + ":"
                                            + scdSLV.substring(2,4) + "~" + scdELV.substring(0, 2) + ":"
                                            + scdELV.substring(2,4);
                                    contentsDisplayCount++;
                                }else if (contentsDisplayCount==1) {
                                    displayedContents[j][i] += "* " + scheduleContentLv;
                                    contentsDisplayCount++;
                                }else {
                                    displayedContents[j][i] += "* ";
                                }
                            }
                        }

                        // 중간 시간 체크
                        if (i / 4 > scheduleStartLv / 100 && i / 4 < scheduleEndLv / 100) {
                            displayedContents[j][i] += "* ";
                        }

                        // 끝나는 시간도 체크.
                        if (scheduleEndLv / 100 == i / 4) {
                            if (i % 4 == 3 && (scheduleEndLv % 100) / 15 >= 3) {
                                displayedContents[j][i] += "* ";
                            } else if (i % 4 == 2 && (scheduleEndLv % 100) / 15 >= 2) {
                                displayedContents[j][i] += "* ";
                            } else if (i % 4 == 1 && (scheduleEndLv % 100) / 15 >= 1) {
                                displayedContents[j][i] += "* ";
                            } else if (i % 4 == 0 && (scheduleEndLv % 100) / 15 >= 0) {
                                displayedContents[j][i] += "* ";
                            }
                        }
                    }
                }
            }
        }

        for (int j = 0; j < displayHrsAndMins[0].length; j++) {
            for (int i = 0; i < 7; i++) {
                System.out.print(displayHrsAndMins[i][j] + displayedContents[i][j]);
                if (displayedContents[i][j].equals("")) {
                    for (int k = 1; k <= 23; k++) {
                        System.out.print(" ");
                    }
                } else {
                    for (int k = 1; k <= 23 - displayedContents[i][j].length(); k++) {
                        System.out.print(" ");
                    }
                }

                if (i == 6) {
                    System.out.print("|");
                }
            }
            System.out.println();
        }

        for (int i = 1; i <= 204; i++) {
            System.out.print("-");
        }
        System.out.println();

        // ==============================

        String calendarDateInput;

        boolean loopSwitch = true;
        while (loopSwitch) {
            Schedule scd;
            
            // key를 얻기 위한 계산
            scheduleKey = inputYear + inputMonth + inputCurrentDate;

            System.out.println("1.일정입력/2.일정삭제/9.뒤로가기");
            calendarDateInput = br.readLine();

            if (calendarDateInput.equals("1")) {
                // 입력 전 새 인스턴스 생성
                scd = new Schedule();

                //다른 key에 현재 날짜의 일정이 들어가는 것을 방지 하기 위한 초기화
                scheduleList = new ArrayList<>();
                if (mapList.get(scheduleKey)!=null) {
                    for (Schedule s : mapList.get(scheduleKey)) {
                        scheduleList.add(s);
                    }
                }
                
                
                // 일정이 시작하는 시간 입력
                boolean loopSwitchForStartTime = true;
                while (loopSwitchForStartTime) {
                    System.out.println("일정이 시작하는 시간을 입력해주세요.(HHMM)");
                    inputTime = br.readLine();

                    if (checkBlank(inputTime)) {
                        System.out.println("잘못된 입력입니다.");
                    } else if (validateTime(inputTime) == false) {
                        System.out.println("잘못된 입력입니다.");
                    } else {
                        parsingNumber = Integer.parseInt(inputTime);
                        
                        if ((parsingNumber%100)%15!=0) {
                            System.out.println("분을 15분단위로 입력해주세요.");
                        } else if (scheduleList.size() != 0) {
                            for (Schedule s : mapList.get(scheduleKey)) {
                                if (parsingNumber >= s.scheduleStartInt && parsingNumber < s.scheduleEndInt) {
                                    System.out.println("입력한 시작시간이 기존의 시간과 겹칩니다.");
                                    loopSwitchForStartTime = true;
                                    break;
                                } else {
                                    scd.setScheduleStart(cal.inputTime);
                                    scd.setScheduleStartInt(parsingNumber);
                                    loopSwitchForStartTime = false;
                                }
                            }
                        } else {
                            scd.setScheduleStart(inputTime);
                            scd.setScheduleStartInt(parsingNumber);
                            loopSwitchForStartTime = false;
                        }
                    }

                }

                // 일정이 끝나는 시간 입력
                boolean loopSwitchForEndTime = true;
                while (loopSwitchForEndTime) {
                    System.out.println("일정이 끝나는 시간을 입력해주세요.(HHMM)");
                    cal.inputTime = br.readLine();

                    if (checkBlank(inputTime)) {
                        System.out.println("잘못된 입력입니다.");
                    } else if (validateTime(inputTime) == false) {
                        System.out.println("잘못된 숫자입니다.");
                    } else {
                        cal.parsingNumber = Integer.parseInt(inputTime);

                        if ((parsingNumber%100)%15!=0) {
                            System.out.println("분을 15분단위로 입력해주세요.");
                        } else if (scheduleList.size() != 0) {
                            for (Schedule s : scheduleList) {
                                if (scd.scheduleStartInt > parsingNumber) {
                                    System.out.println("끝나는 시간이 시작하는 시간보다 빠릅니다.");
                                    loopSwitchForEndTime = true;
                                    break;
                                } else {
                                    if (scd.scheduleStartInt < s.scheduleStartInt
                                            && parsingNumber > s.scheduleStartInt) {
                                        System.out.println("입력한 끝나는시간이 기존의 시간과 겹칩니다.");
                                        loopSwitchForEndTime = true;
                                        break;
                                    } else {
                                        scd.setScheduleEnd(cal.inputTime);
                                        scd.setScheduleEndInt(parsingNumber);
                                        loopSwitchForEndTime = false;
                                    }
                                }
                            }
                        } else {
                            if (scd.scheduleStartInt > parsingNumber) {
                                System.out.println("끝나는 시간이 시작하는 시간보다 빠릅니다.");
                            } else {
                                scd.setScheduleEnd(cal.inputTime);
                                scd.setScheduleEndInt(parsingNumber);
                                loopSwitchForEndTime = false;
                            }
                        }
                    }
                }
                
                while (true) {
                    System.out.println("내용을 입력해주세요.");
                    String input = br.readLine();
                    
                    if (!validateContentLength(input.trim())) {
                        System.out.println("베타버젼에서는 글자수가 22자로 제한됩니다. ^오^");
                    } else {
                        scd.setScheduleContent(input.trim());
                        break;
                    }
                }

                scheduleList.add(scd);
                
                System.out.println("입력전 scheduleKey: " + scheduleKey);
                
                //지금 까지 입력 받은 것들을 schedule 필드에 넘겨준후 map에 add해주거나 schedule을 만들고 put하기
                if (mapList.get(scheduleKey)!=null) {
                    mapList.get(scheduleKey).add(scd);
                }else {
                    mapList.put(scheduleKey, scheduleList);
                }
                
                // sorting
                scd.sortByTime(mapList.get(scheduleKey));
                
            } else if (calendarDateInput.equals("2")) {
                if (mapList.get(scheduleKey)==null) {
                    System.out.println("삭제할 일정이 없습니다.");
                    break;
                }
                
                String deleteInput;
                String deleteDateInput;
                String deleteOrNot;
                boolean loopSwitchForDeletingSchedule = true;
                while (loopSwitchForDeletingSchedule) {

                    System.out.println("삭제할 일정의 날짜를 입력해주세요.");
                    deleteDateInput = br.readLine();

                    if (!validateInput(deleteDateInput)) {
                        System.out.println("잘못된 입력입니다.");
                    } else {
                        System.out.println(Integer.parseInt(deleteDateInput) + "일의 삭제할 일정의 번호를 입력해주세요.");
                        deleteInput = br.readLine();

                        int deleteInputInt;

                        if (validateSelect(deleteInput) == false) {
                            System.out.println("잘못된 숫자입니다.");
                        } else {
                            
                            while (true) {
                                System.out.println("정말 삭제 하시겠습니까? (Y/N)");
                                deleteOrNot = br.readLine();
                                
                                if (!validateTrueSelect(deleteOrNot)) {
                                    System.out.println("잘못된 입력입니다.");
                                } else {
                                    break;
                                }
                                
                            }
                            if (deleteOrNot.equalsIgnoreCase("y")) {
                                deleteInputInt = Integer.parseInt(deleteInput);
                                changeOneDigitIntoTwoDigit(deleteDateInput);
                                deleteDateInput = inputStrForDigitChanging;
                                
                                mapList.get(inputYear + inputMonth + deleteDateInput).remove(deleteInputInt-1);
                                
                                System.out.println("삭제가 완료되었습니다.");
                            }
                            loopSwitchForDeletingSchedule = false;
                        }
                    }
                }
                loopSwitch = !loopSwitch;
            } else if (calendarDateInput.equals("9")) {
                loopSwitch = !loopSwitch;
            } else {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }

    private boolean validateTrueSelect(String deleteOrNot) {
        if (deleteOrNot.length() == 1 && (deleteOrNot.equalsIgnoreCase("Y") || deleteOrNot.equalsIgnoreCase("N"))) {
                return true;
        } else {
            return false;
        }
    }
    

    private boolean validateContentLength(String input) {
        if (input.length() <= 22) {
                return true;
        } else {
            return false;
        }
    }

    private boolean validateInput(String deleteDateInput) {
        if (deleteDateInput.length() <= 2 && deleteDateInput.matches("^[1-9]*$")) {
            int parsing = Integer.parseInt(deleteDateInput);
            if (parsing < 0) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    private boolean validateTime(String time) {
        if (time.length() == 4 && time.matches("^[0-9]*$")) {
            int hour = Integer.parseInt(time.substring(0, 2));
            int min = Integer.parseInt(time.substring(2));
            if (hour < 0 || hour > 23 || min < 0 || min > 59) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    
    private boolean validateYear(String year) {
        if (year.length() <= 4 && year.matches("^[0-9]*$")) {
            if (Integer.parseInt(year)!=0) {
                return true;
            }else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean validateSelect(String number) {
        if (number.length() <= 2 && number.matches("^[0-9]*$")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validateMonth(String inputMonth) {
        if (inputMonth.length() <= 2 && inputMonth.matches("^[0-9]*$")) {
            if (Integer.parseInt(inputMonth)<=12 && !inputMonth.equals("0")) {
                return true;
            }else {
                return false;
            }
        } else {
            return false;
        }
    }
    
    private boolean checkBlank(String input) {
        if (input.equals("")) {
            return true;
        } else {
            return false;
        }
    }
    
    private void changeOneDigitIntoTwoDigit(String inputStr) {
        if (inputStr.length() == 1) {
            this.inputStrForDigitChanging = "0" + inputStr;
        } else {
            this.inputStrForDigitChanging = inputStr;
        }
    }

    private void changeTwoDigitIntoOneDigit(String inputStr) {
        if (inputStr.length() == 2 && inputStr.charAt(0) == '0') {
            this.inputStrForDigitChanging = inputStr.substring(1);
        } else {
            this.inputStrForDigitChanging = inputStr;
        }
    }
    
    public String toString() {
        Gson g = new Gson();
        return g.toJson(this);
    }
    
    public static void calendarInit() throws IOException {
        ScheduleCalendar cal = new ScheduleCalendar();
        
        ArrayList<Schedule> scheduleList = new ArrayList<>();
        
        CalendarWrite cw = new CalendarWrite();
        CalendarRead cr = new CalendarRead();
        
        // 연도,월,일별 일정을 관리 하기 위한 HashMap
        // key ex) 20190109, value: ArrayList (해당 날짜의 일정을 key별로 따로 저장해두고 있음)
        HashMap<String, ArrayList<Schedule>> mapList = new HashMap<String, ArrayList<Schedule>>();
        
        cr.readFile(mapList, scheduleList);
        
        boolean startMenuLoop = true;
        while (startMenuLoop) {
            if (cal.input !=null) {
                
            }
            cal.changeCalendar(cal);
            cal.printCalendar(cal);
            cal.input(cal.br, cal);

            if (cal.checkBlank(cal.input)) {
                System.out.println("잘못된 입력입니다.");
            } else if (cal.input.equals("1")) {
                cal.showDetail(cal.br, cal, scheduleList, mapList);
            } else if (cal.input.equals("2")) {
                // changeCalendar를 호출해야 하지만 임시버전에서는 호출하지 않고 while을 다시 루프
            } else if (cal.input.equals("9")) {
                startMenuLoop = false;
            } else {
                System.out.println("잘못된 입력입니다.");
            }
        }
        cw.writeFile(mapList);
        
    }

//    public static void main(String[] args) throws IOException {
//        calendarInit();
//
//    }
}

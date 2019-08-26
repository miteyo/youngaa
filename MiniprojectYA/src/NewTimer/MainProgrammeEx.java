package NewTimer;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class MainProgrammeEx {

	public static void main(String[] args) throws NumberFormatException, IOException, ParseException {
		Scanner scan = new Scanner(System.in);
		ReminderView rc = new ReminderView();
		ArrayList<ReminderDomain> reminderList = new ArrayList<>();
		rc.reading(reminderList);
		
		while (true) {
			ReminderController starter = new ReminderController();
			Timer timer = new Timer();
			ScheduleCalendar calendar = new ScheduleCalendar();
			System.out.println("번호를 선택해주세요: ");
			System.out.println("1.Reminder 2.Timer 3. ScheduleCalendar");
			int choice = scan.nextInt();
			scan.nextLine();

			switch (choice) {
			case 1:
				starter.init(reminderList);
				break;
			case 2:
				timer.timer(scan);
				break;
			case 3:
				ScheduleCalendar.calendarInit();
				break;
			default:
				System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
			}
			
		}
		
	}

}

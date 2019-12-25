package app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * ToubanCalendar
 */
public class ToubanCalendar {

	private int numberOfPerson = 0;

	private Calendar calendar = null;

	private int lastDayOfMonth = 0;

	private List<DayInfo> dayInfoList = null;

	public ToubanCalendar(int year, int month) {
		this.calendar = Calendar.getInstance();
		this.calendar.clear();
		this.calendar.set(year, month - 1, 1);
		this.lastDayOfMonth = calendar.getActualMaximum(Calendar.DATE);
	}

	public void addSeihyokiTouban(int day, Person icePerson) {
		dayInfoList.get(day - 1).setIce(icePerson);
	}

	public void addNotWorkDay(int day) {
		dayInfoList.get(day - 1).setWorkDay(false);
	}

	public void createToubanCalendar(List<Person> personList) {
		this.numberOfPerson = personList.size();

		createWorkDayList();

		// まずは重複を考えずに上から詰めていく
		int personIndex = 0;
		for (DayInfo dayInfo : dayInfoList) {

			if (!dayInfo.isWorkDay()) {
				continue;
			}

			dayInfo.setTelephone1(personList.get(personIndex++));
			if (personIndex >= this.numberOfPerson) {
				personIndex = 0;
			}

			dayInfo.setTelephone2(personList.get(personIndex++));
			if (personIndex >= this.numberOfPerson) {
				personIndex = 0;
			}

			dayInfo.setCoffee(personList.get(personIndex++));
			if (personIndex >= this.numberOfPerson) {
				personIndex = 0;
			}
		}

		// 重複を解決していく

	}

	//単純に
	private void createWorkDayList() {

		this.dayInfoList = new ArrayList<>();

		int dayOfWeek = 0;

		for (int day = 1; day <= this.lastDayOfMonth; day++) {
			// 月を指定したカレンダーに日にちを入れている
			this.calendar.set(Calendar.DAY_OF_MONTH, day);
			// 月と日にちを入れたカレンダーから曜日を取得する
			dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

			if (dayOfWeek != Calendar.SUNDAY && dayOfWeek != Calendar.SATURDAY) {
				// 曜日が平日だった場合
				this.dayInfoList.add(new DayInfo(day, true));
			} else {
				// 曜日が土日だった場合
				this.dayInfoList.add(new DayInfo(day, false));
			}
		}

	}

	public int getLastDayOfMonth() {
		return lastDayOfMonth;
	}

}

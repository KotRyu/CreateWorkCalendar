package app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import static app.Constants.DuplicationType;
import static app.Constants.DuplicationType.*;

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
		createWorkDayList();
	}

	public void addSeihyokiTouban(int day, Person icePerson) {
		dayInfoList.get(day - 1).setIce(icePerson);
	}

	public void addNotWorkDay(int day) {
		dayInfoList.get(day - 1).setWorkDay(false);
	}

	public List<DayInfo> createToubanCalendar(List<Person> personList) {
		this.numberOfPerson = personList.size();

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
		for (DayInfo dayInfo : this.dayInfoList) {

			if (!dayInfo.isWorkDay()) {
				// 休日だった場合
				continue;
			}

			DuplicationType dt = null;
			if ((dt = dayInfo.getDuplicateType()) != NOT_DUPLICATE) {
				// 重複している人がいた場合
				resolveDuplicate(dt, dayInfo);
			}
		}

		return this.dayInfoList;
	}

	/**
	 * 重複を解決する関数
	 */
	private boolean resolveDuplicate(DuplicationType duplicationType, DayInfo dayInfo) {
		boolean canResolve = false;
		int indexOfDublicateDay = dayInfo.getToday() - 1;
		int indexOfSlideDay = 0;

		DayInfo duplicateDayInfo = dayInfoList.get(indexOfDublicateDay);
		Person tel1InDuplicateDay = duplicateDayInfo.getTelephone1();
		Person tel2InDuplicateDay = duplicateDayInfo.getTelephone2();
		Person coffeeInDuplicateDay = duplicateDayInfo.getCoffee();

		DayInfo slideDayInfo = null;
		Person tel1InSlideDay = null;
		Person tel2InSlideDay = null;
		Person coffeeInSlideDay = null;

		for (int slide = 1; slide < lastDayOfMonth; slide++) {
			for (int sign = -1; sign <= 1; sign += 2) {
				indexOfSlideDay = indexOfDublicateDay + sign * slide;

				try {
					slideDayInfo = dayInfoList.get(indexOfSlideDay);
				} catch (IndexOutOfBoundsException e) {
					continue;
				}
				tel1InSlideDay = slideDayInfo.getTelephone1();
				tel2InSlideDay = slideDayInfo.getTelephone2();
				coffeeInSlideDay = slideDayInfo.getCoffee();

				switch (duplicationType) {
					case TEL1_AND_TEL2: {
						if (!slideDayInfo.isDuplicate(tel1InDuplicateDay.getId())) {
							if (!duplicateDayInfo.isDuplicate(tel1InSlideDay.getId())) {
								// 交換する
								Person tmpPerson = duplicateDayInfo.getTelephone1();
								duplicateDayInfo.setTelephone1(slideDayInfo.getTelephone1());
								slideDayInfo.setTelephone1(tmpPerson);
								return true;
							}
							if (!duplicateDayInfo.isDuplicate(tel2InSlideDay.getId())) {
								// 交換する
								Person tmpPerson = duplicateDayInfo.getTelephone1();
								duplicateDayInfo.setTelephone1(slideDayInfo.getTelephone2());
								slideDayInfo.setTelephone2(tmpPerson);
								return true;
							}
						} else if (!slideDayInfo.isDuplicate(tel2InDuplicateDay.getId())) {
							if (!duplicateDayInfo.isDuplicate(tel1InSlideDay.getId())) {
								// 交換する
								Person tmpPerson = duplicateDayInfo.getTelephone2();
								duplicateDayInfo.setTelephone2(slideDayInfo.getTelephone1());
								slideDayInfo.setTelephone1(tmpPerson);
								return true;
							}
							if (!duplicateDayInfo.isDuplicate(tel2InSlideDay.getId())) {
								// 交換する
								Person tmpPerson = duplicateDayInfo.getTelephone2();
								duplicateDayInfo.setTelephone2(slideDayInfo.getTelephone2());
								slideDayInfo.setTelephone2(tmpPerson);
								return true;
							}
						}
						break;
					}
					case TEL1_AND_COFFEE:
						if (!slideDayInfo.isDuplicate(tel1InDuplicateDay.getId())) {
							if (!duplicateDayInfo.isDuplicate(tel1InSlideDay.getId())) {
								// 交換する
								Person tmpPerson = duplicateDayInfo.getTelephone1();
								duplicateDayInfo.setTelephone1(slideDayInfo.getTelephone1());
								slideDayInfo.setTelephone1(tmpPerson);
								return true;
							}
							if (!duplicateDayInfo.isDuplicate(tel2InSlideDay.getId())) {
								// 交換する
								Person tmpPerson = duplicateDayInfo.getTelephone1();
								duplicateDayInfo.setTelephone1(slideDayInfo.getTelephone2());
								slideDayInfo.setTelephone2(tmpPerson);
								return true;
							}
						} else if (!slideDayInfo.isDuplicate(coffeeInDuplicateDay.getId())) {
							if (!duplicateDayInfo.isDuplicate(coffeeInSlideDay.getId())) {
								// 交換する
								Person tmpPerson = duplicateDayInfo.getCoffee();
								duplicateDayInfo.setCoffee(slideDayInfo.getCoffee());
								slideDayInfo.setCoffee(tmpPerson);
								return true;
							}
						}
						break;
					case TEL1_AND_ICE:
						if (!slideDayInfo.isDuplicate(tel1InDuplicateDay.getId())) {
							if (!duplicateDayInfo.isDuplicate(tel1InSlideDay.getId())) {
								// 交換する
								Person tmpPerson = duplicateDayInfo.getTelephone1();
								duplicateDayInfo.setTelephone1(slideDayInfo.getTelephone1());
								slideDayInfo.setTelephone1(tmpPerson);
								return true;
							}
							if (!duplicateDayInfo.isDuplicate(tel2InSlideDay.getId())) {
								// 交換する
								Person tmpPerson = duplicateDayInfo.getTelephone1();
								duplicateDayInfo.setTelephone1(slideDayInfo.getTelephone2());
								slideDayInfo.setTelephone2(tmpPerson);
								return true;
							}
						}
						break;
					case TEL2_AND_COFFEE:
						if (!slideDayInfo.isDuplicate(tel2InDuplicateDay.getId())) {
							if (!duplicateDayInfo.isDuplicate(tel1InSlideDay.getId())) {
								// 交換する
								Person tmpPerson = duplicateDayInfo.getTelephone2();
								duplicateDayInfo.setTelephone2(slideDayInfo.getTelephone1());
								slideDayInfo.setTelephone1(tmpPerson);
								return true;
							}
							if (!duplicateDayInfo.isDuplicate(tel2InSlideDay.getId())) {
								// 交換する
								Person tmpPerson = duplicateDayInfo.getTelephone2();
								duplicateDayInfo.setTelephone2(slideDayInfo.getTelephone2());
								slideDayInfo.setTelephone2(tmpPerson);
								return true;
							}
						} else if (!slideDayInfo.isDuplicate(coffeeInDuplicateDay.getId())) {
							if (!duplicateDayInfo.isDuplicate(coffeeInSlideDay.getId())) {
								// 交換する
								Person tmpPerson = duplicateDayInfo.getCoffee();
								duplicateDayInfo.setCoffee(slideDayInfo.getCoffee());
								slideDayInfo.setCoffee(tmpPerson);
								return true;
							}
						}
						break;
					case TEL2_AND_ICE:
						if (!slideDayInfo.isDuplicate(tel2InDuplicateDay.getId())) {
							if (!duplicateDayInfo.isDuplicate(tel1InSlideDay.getId())) {
								// 交換する
								Person tmpPerson = duplicateDayInfo.getTelephone2();
								duplicateDayInfo.setTelephone2(slideDayInfo.getTelephone1());
								slideDayInfo.setTelephone1(tmpPerson);
								return true;
							}
							if (!duplicateDayInfo.isDuplicate(tel2InSlideDay.getId())) {
								// 交換する
								Person tmpPerson = duplicateDayInfo.getTelephone2();
								duplicateDayInfo.setTelephone2(slideDayInfo.getTelephone2());
								slideDayInfo.setTelephone2(tmpPerson);
								return true;
							}
						}
						break;
					case COFFEE_AND_ICE:
						if (!slideDayInfo.isDuplicate(coffeeInDuplicateDay.getId())) {
							if (!duplicateDayInfo.isDuplicate(coffeeInSlideDay.getId())) {
								// 交換する
								Person tmpPerson = duplicateDayInfo.getCoffee();
								duplicateDayInfo.setCoffee(slideDayInfo.getCoffee());
								slideDayInfo.setCoffee(tmpPerson);
								return true;
							}
						}
						break;
					case NOT_DUPLICATE:
						break;
				}
			}
		}

		return canResolve;
	}

	// 平日のDayInfoのisWorkDayフィールドににはtrueを、休日にはfalse
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

	public void toCSV(String outPutDirectoryPath) {
		String outPutPath = outPutDirectoryPath;
		if(outPutDirectoryPath == null || outPutDirectoryPath.isEmpty()){
			//パスの指定がない、もしくはnullだった場合はカレントディレクトリに出力します
			outPutPath = System.getProperty("user.dir");
		}

		try (FileOutputStream file = new FileOutputStream(outPutPath+"/calendar.csv");
				OutputStreamWriter in = new OutputStreamWriter(file);
				BufferedWriter br = new BufferedWriter(in)) {
			
					br.write("day,tel1,tel2,coffee,ice");
					for(DayInfo day:dayInfoList){
						br.write(Integer.toString(day.getToday()));
						br.write(",");
						if(day.isWorkDay()){
							br.write(day.getTelephone1().getName());
							br.write(",");
							br.write(day.getTelephone2().getName());
							br.write(",");
							br.write(day.getCoffee().getName());
							br.write(",");
							if(day.getIce() != null){
								br.write(day.getIce().getName());
							}
						}
						br.newLine();
					}

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Create csv file " + outPutPath);
	}

	public int getLastDayOfMonth() {
		return lastDayOfMonth;
	}

}

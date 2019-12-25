package app;

import java.util.List;
import java.util.ArrayList;

import static app.Constants.WorkDayOfWeek;

/**
 * Person
 */
public class Person {

    // 人数
    private static int numberOfPerson = 0;

    private int id = 0;

    // 名前
    private String name = null;

    /*
     * この曜日無理リスト 月曜日:2 , 火曜日:3 , ...
     */
    private List<Integer> konoYoubiMuriList = null;

    // この日無理リスト
    private List<Integer> konoHiMuriList = null;

    public Person(String name) {
        Person.numberOfPerson++;
        this.id = Person.numberOfPerson;
        this.name = name;
        this.konoYoubiMuriList = new ArrayList<>();
        this.konoHiMuriList = new ArrayList<>();
    }

    public void konoYoubiMuri(WorkDayOfWeek workDayOfWeek) {

        // 既に設定されているかチェック
        for (int settingedYoubi : this.konoYoubiMuriList) {
            if (settingedYoubi == workDayOfWeek.toInt()) {
                return;
            }
        }

        this.konoYoubiMuriList.add(workDayOfWeek.toInt());

    }

    public void koniHiMuri(int day) {

        // 既に設定されているかチェック
        for (int settingedDay : this.konoHiMuriList) {
            if (settingedDay == day) {
                return;
            }
        }

        konoHiMuriList.add(day);
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return id;
    }
}

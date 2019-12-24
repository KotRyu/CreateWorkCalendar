package app;

import java.util.List;
import java.util.ArrayList;

import app.Constants.WorkDayOfWeek;

/**
 * Person
 */
public class Person {

    //人数
    private static int numberOfPerson = 0;

    private int id = 0;

    // 名前
    private String name = null;

    /*
     * この曜日無理リスト 月曜日:0 火曜日:1 以下省略
     */
    private List<Integer> konoYoubiMuriList = null;

    //この日無理リスト
    private List<Integer> konoHiMuriList = null;

    public Person(String name) {
        this.numberOfPerson++;
        this.id = this.numberOfPerson;
        this.name = name;
        konoYoubiMuriList = new ArrayList<>();
        konoHiMuriList = new ArrayList<>();
    }

    public void addKonoYoubiMuriList(WorkDayOfWeek workDayOfWeek) {
        switch (workDayOfWeek) {
        case Monday:
            konoYoubiMuriList.add(0);
            break;
        case Tuesday:
            konoYoubiMuriList.add(1);
            break;
        case Wednesday:
            konoYoubiMuriList.add(2);
            break;
        case Thursday:
            konoYoubiMuriList.add(3);
            break;
        case Friday:
            konoYoubiMuriList.add(4);
            break;
        }
    }

    public void addKoniHiMuriList(int day){
        konoHiMuriList.add(day);
    }

    public String getName(){
        return this.name;
    }

    public int getId() {
        return id;
    }
}
package app;


/**
 * DayInfo
 */
public class DayInfo {

    private int today = 0;

    //平日？
    private boolean isWorkDay = false;


    private Person telephone1 = null;
    private Person telephone2 = null;
    private Person coffee = null;
    private Person ice = null;

    DayInfo(int today,boolean isWorkDay){
        this.today = today;
        this.isWorkDay = isWorkDay;
    }

    public Person getTelephone1() {
        return telephone1;
    }

    public void setTelephone1(Person telephone1) {
        this.telephone1 = telephone1;
    }

    public Person getTelephone2() {
        return telephone2;
    }

    public void setTelephone2(Person telephone2) {
        this.telephone2 = telephone2;
    }

    public Person getCoffee() {
        return coffee;
    }

    public void setCoffee(Person coffee) {
        this.coffee = coffee;
    }

    public int getToday() {
        return today;
    }

    public Person getIce() {
        return ice;
    }

    public void setIce(Person ice) {
        this.ice = ice;
    }

    public boolean isWorkDay() {
        return isWorkDay;
    }


}
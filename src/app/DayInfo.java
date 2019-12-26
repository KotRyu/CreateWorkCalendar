package app;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import static app.Constants.DuplicationType;
import static app.Constants.DuplicationType.*;

/**
 * DayInfo
 */
public class DayInfo implements Cloneable{

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

    /**
     * 電話番、コーヒー係、製氷機係で重複している人がいないかチェックする関数
     * @return 重複タイプ
     */
    public DuplicationType getDuplicateType(){
        DuplicationType ret = NOT_DUPLICATE;

        if(telephone1 != null && telephone2 != null && telephone1.getId() == telephone2.getId()){
            ret = TEL1_AND_TEL2;
        }
        if(telephone1 != null && coffee != null && telephone1.getId() == coffee.getId()){
            ret = TEL1_AND_COFFEE;
        }
        if(telephone2 != null && coffee != null && telephone2.getId() == coffee.getId()){
            ret = TEL2_AND_COFFEE;
        }
        if(telephone1 != null && ice != null && telephone1.getId() == ice.getId()){
            ret = TEL1_AND_ICE;
        }
        if(telephone2 != null && ice != null && telephone2.getId() == ice.getId()){
            ret = TEL2_AND_ICE;
        }
        if(coffee != null && ice != null && coffee.getId() == ice.getId()){
            ret = COFFEE_AND_ICE;
        }
        
        return ret;
    }

    /**
     * 引数で受け取った人物IDの仕事が既にあるかを調べる関数.
     */
    public boolean isDuplicate(int PersonID){

        if(telephone1 != null && telephone1.getId() == PersonID){
            return true;
        }else if(telephone2 != null && telephone2.getId() == PersonID){
            return true;
        }else if(coffee != null && coffee.getId() == PersonID){
            return true;
        }else if(ice != null && ice.getId() == PersonID){
            return true;
        }

        return false;
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

    public void setWorkDay(boolean isWorkDay) {
        this.isWorkDay = isWorkDay;
    }


}
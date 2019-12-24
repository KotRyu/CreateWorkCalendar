package app;

import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        Person ide = new Person("ide");
        Person kawamura = new Person("kawamura");
        Person saito = new Person("saito");
        Person tochimura = new Person("tochimura");
        Person nagashima = new Person("nagashima");
        Person hishida = new Person("hishida");
        Person maeda = new Person("maeda");

        List<Person> personList = new ArrayList<>();
        personList.add(ide);
        personList.add(kawamura);
        personList.add(saito);
        personList.add(tochimura);
        personList.add(nagashima);
        personList.add(hishida);
        personList.add(maeda);

        ToubanCalendar toubanCalendar = new ToubanCalendar(2020, 1);

        toubanCalendar.createToubanCalendar(personList);
    }
}
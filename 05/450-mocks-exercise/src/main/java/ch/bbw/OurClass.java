package ch.bbw;

import ch.bbw.otherpackage.Person;
import ch.bbw.otherpackage.SlowClass;

import java.time.LocalDate;

public class OurClass {
    SlowClass slowClass = new SlowClass();

    public String getSlowString() throws InterruptedException {
        return slowClass.slowString();
    }

    public String getSlowStringStars(int stars) throws InterruptedException {
        return slowClass.slowStringParameter(stars);
    }

    public void addJoeSmith(int year, int month, int day) throws InterruptedException {
        LocalDate birthdate = LocalDate.of(year, month, day);
        Person joe = new Person("Joe", "Smith", birthdate);
        slowClass.slowSave(joe);
    }
}

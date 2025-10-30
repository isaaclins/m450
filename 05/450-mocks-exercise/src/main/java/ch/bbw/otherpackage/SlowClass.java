package ch.bbw.otherpackage;

import static java.lang.Thread.sleep;

public class SlowClass {
    /**
     * returns "slow result" after some calculation
     * @return fixed string "slow result"
     * @throws InterruptedException
     */
    public String slowString() throws InterruptedException {
        sleep(5 * 1000);

        return "slow result";
    }

    /**
     * returns " middle " surrounded by {@code stars} "*" after some calculation
     * @param stars how many "*"
     * @return string surrounded with *
     * @throws InterruptedException
     */
    public String slowStringParameter(int stars) throws InterruptedException {
        sleep(5 * 1000);

        final String star = "*";
        String surround = star.repeat(Math.max(0, stars));
        return surround + " middle " + surround;
    }

    /**
     * saves {@code person} after some calculation
     * @param person person to be saved
     * @return status
     * @throws InterruptedException
     */
    public String slowSave(Person person) throws InterruptedException {
        sleep(5 * 1000);

        return "saved " + person;
    }
}
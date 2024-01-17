package org.timeUtil;

import org.bouncycastle.oer.Switch;

import java.time.Month;

public class MonthClass {

    public static Month getMonth(String month) {
        Month months = null;
        switch (month.toLowerCase()) {
            case "january":
                months= Month.JANUARY;
            break;
            case "february":
                months= Month.FEBRUARY;
            break;
            case "march":
                months= Month.MARCH;
            break;
            case "april":
                months= Month.APRIL;
            break;
            case "may":
                months= Month.MAY;
            break;
            case "june":
                months= Month.JUNE;
            break;
            case "july":
                months= Month.JULY;
            break;
            case "august":
                months= Month.AUGUST;
            break;
            case "september":
                months= Month.SEPTEMBER;
            break;
            case "october":
                months= Month.OCTOBER;
            break;
            case "november":
                months= Month.NOVEMBER;
            break;
            case "december":
                months= Month.DECEMBER;
            break;
            default:
                System.out.println("month should be in proper format and spelling");

        }
        return months;
    }
}

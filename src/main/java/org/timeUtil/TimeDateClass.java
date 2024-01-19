package org.timeUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;

public class TimeDateClass {

    /**
     * method is to convert seconds into hours minutes and seconds format
     *
     * @param seconds
     * @return string time format in HH:MM:SS
     */
    public static String convertSecondsToHHMMSSFormat(double seconds) {
        String time;
        int hours = (int) (seconds / 3600);
        int min = (int) ((seconds % 3600) / 60);
        int remainingSeconds = (int) (seconds % 60);
        time = String.format("%02d:%02d:%02d", hours, min, remainingSeconds).trim();
        try {
            if (time.equals("00:00:00") || time.equals("0"))
                time="-";
        }catch (Exception e)
        {

        }
        return time;
    }

    public static String convertSecondsToHHMMSS(double seconds) {
        String time;
        int hours = (int) (seconds / 3600);
        int min = (int) ((seconds % 3600) / 60);
        int remainingSeconds = (int) (seconds % 60);
        return String.format("%02d:%02d:%02d", hours, min, remainingSeconds).trim();
    }
    /**
     * method is to convert seconds into hours minutes and seconds in givenformat
     *
     * @param seconds
     * @return string time format in HH:MM:SS
     */
    public static String convertSecondsToHHMMSSFormat(double seconds,String format) {
        
        int hours = (int) (seconds / 3600);
        int min = (int) ((seconds % 3600) / 60);
        int remainingSeconds = (int) (seconds % 60);
        String form = null;
        if (format.equalsIgnoreCase("%02d:%02d:%02d")) {
             form= String.format("%02d:%02d:%02d", hours, min, remainingSeconds);
        }else if (format.equalsIgnoreCase("%02d:%02d"))
        {
            form=  String.format("%02d:%02d", hours, min, remainingSeconds);
        }
        return form;
    }

    /**
     * method to get currentDateWithTime
     * @return string date
     */
    public static String getCurrentDateWithHHMMSSTimeFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss");
        Date currentDateTime=new Date();
        return dateFormat.format(currentDateTime);
    }

    /**
     * method to get current date with custom time format
     * @param HH_MM it should be pass as hours and minutes format 12:30
     * @return string date
     */
    public static String getCustomDateTime(String HH_MM)
    {
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String currentDate=dateFormat.format(new Date());
        String combinedDateTime=currentDate+ "T"+HH_MM+":00";
        return combinedDateTime;
    }

    /**
     * method is to get toDate
     *
     * @return String date
     */
    public static String getToDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date currentDate = new Date();
        return dateFormat.format(currentDate);
    }


    /**
     * method to get from date/ yesterday date
     *
     * @return string yesterday date
     */
    public static String getFromDate() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calender = Calendar.getInstance();
        calender.add(Calendar.DAY_OF_YEAR, -1);
        Date yesterdayDate = calender.getTime();
        return dateFormat.format(yesterdayDate);
    }

    /**
     * method to get custom date
     * @param day date should be provided as integer 02
     * @return string date
     */
    public static String getCustomDate(int day)
    {
        Calendar calender = Calendar.getInstance();
        calender.set(Calendar.DAY_OF_MONTH,day);
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return dateFormat.format(calender.getTime());
    }
    public static String getCustomDDMMYYYY(int day)
    {
        Calendar calender = Calendar.getInstance();
        calender.set(Calendar.DAY_OF_MONTH,day);
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(calender.getTime());
    }
    public static String getCustomDate(int day, String month)
    {
        // Convert the month name to a Month enum
        Month monthEnum = Month.valueOf(month.toUpperCase());

        // Get the current year
        int currentYear = LocalDate.now().getYear();

        // Create a LocalDate object for the specified day, month, and current year
        LocalDate customDate = LocalDate.of(currentYear, monthEnum, day);

        // Format the LocalDate object to a String in the desired format
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'"+"00:00:00");
        Date date = java.sql.Date.valueOf(customDate);
        String formattedDate = formatter.format(date);

        return formattedDate;
    }
    public static String getCustomDateAndTime(int day , String HHMMSS)
    {
        Calendar calendar= Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,day);
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd'T'"+HHMMSS);
        return dateFormat.format(calendar.getTime());
    }
    public static String convertDateFormat(String inputDate, String inputFormat, String outputFormat) {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat(inputFormat);
        SimpleDateFormat outputDateFormat = new SimpleDateFormat(outputFormat);

        try {
            Date date = inputDateFormat.parse(inputDate);
            return outputDateFormat.format(date);
        } catch (ParseException e) {
            // Handle parsing exception, if any
            e.printStackTrace();
            return null;
        }
    }
    public static String convertTimeFormat(String inputTime, String inputFormat, String outputFormat) {
        SimpleDateFormat inputTimeFormat = new SimpleDateFormat(inputFormat);
        SimpleDateFormat outputTimeFormat = new SimpleDateFormat(outputFormat);

        try {
            Date time = inputTimeFormat.parse(inputTime);
            return outputTimeFormat.format(time);
        } catch (ParseException e) {
            // Handle parsing exception, if any
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args) {
        System.out.println(convertSecondsToHHMMSSFormat(3797));//3908 - 3797  01:05:08  01:03:17
        System.out.println(getToDate());
        System.out.println(getFromDate());
        System.out.println(getCurrentDateWithHHMMSSTimeFormat());
        System.out.println(getCustomDateTime("10:10"));
        System.out.println(getCustomDate(02));
        System.out.println(getCustomDateAndTime(9,"'T'06:00:00"));
        String newDate = convertDateFormat("1/3/2024 12:00:00 AM", "M/d/yyyy hh:mm:ss a", "dd/MM/yyyy");
        System.out.println(newDate);
        System.out.println(convertDateFormat("1/3/2024 4:07:00 PM","M/d/yyyy h:mm:ss a","hh:mm:ss a"));
        System.out.println(getCustomDate(3,"january"));
        System.out.println(convertTimeFormat("03:18:34 PM","h:mm:ss a", "hh:mm:ss a"));
        /*00:05:18
        2023-12-13
        2023-12-12
        2023-12-13T13:49:34
        2023-12-13T10:10:00
        2023-12-02T13:49:34*/
    }
}

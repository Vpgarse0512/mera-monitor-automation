package org.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtil {
    /**
     * method is to remove the specific index string
     * @param strRemove removed string
     * @return string
     */
    public static String removeStringFromSpecificIndex(String strRemove,int startIndex,int endIndex)
    {
        StringBuffer sb=new StringBuffer(strRemove);

        return sb.replace(startIndex,endIndex,"").toString();
    }
    public static String extractTime(String dateTimeString) throws ParseException {

    SimpleDateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
    SimpleDateFormat outputFormat = new SimpleDateFormat("hh:mm");

    Date date = inputFormat.parse(dateTimeString);
    String time = outputFormat.format(date);

        if (dateTimeString.contains("PM")) {
        // Convert to 24-hour format if PM
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        if (hours != 12) {
            hours += 12;
            time = hours + ":" + parts[1];
        }
    } else if (dateTimeString.contains("AM") && time.startsWith("12")) {
        // Handle special case: 12:xx AM should be converted to 00:xx
        time = "00:" + time.substring(3);
    }

        return time;
}

}

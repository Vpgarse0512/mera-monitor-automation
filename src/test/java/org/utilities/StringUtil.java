package org.utilities;

import org.json.simple.parser.ParseException;

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
    public static String extractTime(String dateTimeString) {

        // Specify the input date format
        SimpleDateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");

        // Specify the output time format
        SimpleDateFormat outputFormat = new SimpleDateFormat("hh:mm");

        try {
            // Parse the input date-time string
            Date date = inputFormat.parse(dateTimeString);

            // Format the date to get only the time part
            return outputFormat.format(date);
        } catch (java.text.ParseException e) {
            throw new RuntimeException(e);
        }
    }

}

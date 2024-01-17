package org.featureFiles;

public class Example {


        public static void main(String[] args) {
            String userInput = getUserInput(); // Assume you get user input from some source
            String result = checkTime(userInput);
            System.out.println("Result: " + result);
        }

        // Method to get user input (you can modify this based on your input source)
        private static String getUserInput() {
            // For simplicity, I'm using a hardcoded value here. In your application, you may get input differently.
            return "00:00:00";
        }

        // Method to check if the time is "00:00:00" and return a specific value
        private static String checkTime(String time) {
            if ("00:00:00".equals(time)) {
                return "-"; // Change this to the desired value you want to return
            } else {
                return "0";  // Change this to the desired value if the input is not "00:00:00"
            }
        }

}

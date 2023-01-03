package com.armaan.dematic.util;

/**
 * Util class to check if a string is an Integer or Decimal
 */
public class Check {

    /**
     * Function to check if s is an Integer
     * @param s String to be checked
     * @return boolean whether true or false
     */
    public static boolean isInteger(String s){
        if (s == null) {
            return false;
        }
        try {
            Integer i = Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


    /**
     * Function to check if s is a Double
     * @param s String to be checked
     * @return boolean whether true or false
     */
    public static boolean isDouble(String s){
        if (s == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}

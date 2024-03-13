package utils;

import java.util.ArrayList;

public class DisplayTypeUtility {

    private static ArrayList<String> displayTypes = new ArrayList<>(){{
        add("AMOLED");
        add("LCD");
        add("LED");
        add("TFT");
    }};


    public static boolean isValidDisplayType(String type) {
        //must not be case sensitive
        for (String disType:displayTypes){
            if (disType.equalsIgnoreCase(type)) {
                return true;
            }
        }
        return false;
    }
}

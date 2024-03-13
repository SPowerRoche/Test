package utils;
import java.util.*;
public class OperatingSystemUtility {


    private static ArrayList<String> operatingSystems = new ArrayList<>(){{
        add("iPad");
        add("Android");
        add("Chrome");
        add("Windows");
        add("Amazon Fire");
    }};

    public static ArrayList<String> getOperatingSystems() {
        return operatingSystems;
    }

    public static boolean isValidOperatingSystem(String os) {
        //must not be case sensitive
        for (String osName:operatingSystems){
            if (osName.equalsIgnoreCase(os)) {
                return true;
            }
        }
        return false;
    }



}
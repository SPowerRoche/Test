package main;

import controllers.ManufacturerAPI;
import controllers.TechnologyDeviceAPI;

import models.*;
import utils.ScannerInput;
import utils.Utilities;

import java.io.File;

public class Driver {



        private TechnologyDeviceAPI techAPI;
        private ManufacturerAPI manufacturerAPI;


        public static void main(String[] args) throws Exception {
            new Driver().start();
        }

        public void start() {


            //TODO - construct fields

            loadAllData();  //load all data once the serializers are set up
            runMainMenu();
        }

    private int mainMenu() {
        System.out.println("""
                         -------Technology Store-------------
                        |  1) Manufacturer CRUD MENU     |
                        |  2) Technology  CRUD MENU      |
                        |  3) Reports MENU               |
                        |--------------------------------|
                        |  4) Search Manufacturers       |
                        |  5) Search Technology Devices  |  
                        |  6) Sort Technology Devices    | 
                        |--------------------------------|
                        |  10) Save all                  |
                        |  11) Load all                  |
                        |--------------------------------|
                        |  0) Exit                       |
                         --------------------------------""");
        return ScannerInput.readNextInt("==>> ");
    }
    //// search todo by different criteria i.e. look at the list methods and give options based on that.
// sort todo (and give a list of options - not a recurring menu thou)
        private void runMainMenu() {
            int option = mainMenu();
            while (option != 0) {
                switch (option) {
                    case 1->  runManufacturerMenu();
                    //TODO - Add options
                    default ->  System.out.println("Invalid option entered" + option);
                }
                ScannerInput.readNextLine("\n Press the enter key to continue");
                option = mainMenu();
            }
            exitApp();
        }

        private void exitApp(){
            saveAllData();
            System.out.println("Exiting....");
            System.exit(0);
        }

        //----------------------
        //  Manufacturer Menu Items
        //----------------------
        private int manufacturerMenu() {
            System.out.println("""
                --------Manufacturer Menu---------
               |  1) Add a manufacturer           |
               |  2) Delete a manufacturer        |
               |  3) Update manufacturer details  |
               |  4) List all manufacturers       |
               |  5) Find a manufacturer          |
               |  0) Return to main menu          |
                ----------------------------------""");
            return ScannerInput.readNextInt("==>>");
        }

        private void runManufacturerMenu() {
            int option = manufacturerMenu();
            while (option != 0) {
                switch (option) {
                    case 1 -> addManufacturer();
                    case 2 -> deleteManufacturer();
                    case 3 -> updateManufacturer();
                    case 4 -> System.out.println(manufacturerAPI.listManufacturers());
                    case 5-> findManufacturer();
                    case 6-> listByManufacturerName();
                    default->  System.out.println("Invalid option entered" + option);
                }
                ScannerInput.readNextLine("\n Press the enter key to continue");
                option = manufacturerMenu();
            }
        }

        private void addManufacturer() {
            String manufacturerName = ScannerInput.readNextLine("Please enter the manufacturer name: ");
            int manufacturerNumEmployees = ScannerInput.readNextInt("Please enter the number of employees: ");

            if (manufacturerAPI.addManufacturer(new Manufacturer(manufacturerName, manufacturerNumEmployees))){
                System.out.println("Add successful");
            }
            else{
                System.out.println("Add not successful");
            }
        }

        private void deleteManufacturer() {
            String manufacturerName = ScannerInput.readNextLine("Please enter the manufacturer name: ");
            if (manufacturerAPI.removeManufacturerByName(manufacturerName) != null){
                System.out.println("Delete successful");
            }
            else{
                System.out.println("Delete not successful");
            }
        }

        private void updateManufacturer(){
            Manufacturer manufacturer = getManufacturerByName();
            if (manufacturer != null){
                int numEmployees= ScannerInput.readNextInt("Please enter number of Employees: ");
                if (manufacturerAPI.updateManufacturer(manufacturer.getManufacturerName(), numEmployees))
                    System.out.println("Number of Employees Updated");
                else
                    System.out.println("Number of Employees NOT Updated");
            }
            else
                System.out.println("Manufacturer name is NOT valid");
        }

        private void findManufacturer(){
            Manufacturer developer = getManufacturerByName();
            if (developer == null){
                System.out.println("No such manufacturer exists");
            }
            else{
                System.out.println(developer);
            }
        }

        private void listByManufacturerName(){
            String manufacturer = ScannerInput.readNextLine("Enter the manufacturer's name:  ");

            System.out.println(manufacturerAPI.listAllByManufacturerName(manufacturer));
        }


        //---------------------
        //  App Store Menu
        //---------------------

        private int techAPIMenu() {
            System.out.println(""" 
                -----Technology Store Menu----- 
               | 1) Add a Tech Device           |
               | 2) Delete a Tech Device        |
               | 3) List all Tech Devices       |
               | 4) Update Tech Device          |
               | 0) Return to main menu         |
                ----------------------------""");
            return ScannerInput.readNextInt("==>>");
        }

        private void runTechAPIMenu() {
            int option = techAPIMenu();
            while (option != 0) {
                switch (option) {
                    case 1-> addTech();
                    case 2-> deleteTech();
                    case 3->   System.out.println(techAPI.listAllTechnologyDevices());
                    case 4-> System.out.println("todo");
                    default->  System.out.println("Invalid option entered" + option);
                }
                ScannerInput.readNextLine("\n Press the enter key to continue");
                option = techAPIMenu();
            }
        }

    private void deleteTech() {
        String id = ScannerInput.readNextLine("Please enter id number to delete: ");

        if (techAPI.isValidId(id)){
            Technology t = techAPI.deleteTechnologyById(id);
            if(t!=null)
                System.out.println("Sucessful delete : "+t);
            else System.out.println("No Technology was deleted");
        }

    }

    // public Vehicle(String regNumber, String  model, float cost, Manufacturer manufacturer, int  year) {
        private void addTech(){
            int techType =  ScannerInput.readNextInt("""
        Which type of technology do you wish to add? 
        1) SmartBand
        2) SmartWatch
        3) Tablet """);

            Manufacturer manufacturer = getManufacturerByName();
            if (manufacturer != null){
                String id = ScannerInput.readNextLine("Please enter id number: ");


                if (techAPI.isValidId(id)){
                    String  name = ScannerInput.readNextLine("\tname : ");
                    double price = ScannerInput.readNextFloat("\tprice : ");
                    switch (techType) {
                        case 1, 2 -> { //wearables
                            String size = ScannerInput.readNextLine("\tsize : ");
                            String material = ScannerInput.readNextLine("\tmaterial : ");
                            switch (techType) {
                                case 1-> {
                                    //smartband
                                    char monit = ScannerInput.readNextChar("\theart monitor? y/n : ");
                                    boolean monitor = Utilities.YNtoBoolean(monit);

                                    techAPI.addTechnologyDevice(new SmartBand(name,price,manufacturer,id, size, material,monitor));
                                }
                                case 2 -> {
                                    //Smart Watch

                                    String display =ScannerInput.readNextLine("\tdisplay : ");
                                    techAPI.addTechnologyDevice(new SmartWatch(name,price,manufacturer,id, size, material,display));
                                }
                            }
                        }
                        case 3 -> {
                            //tablet

                            int storage = ScannerInput.readNextInt("\tstorage : ");
                            String processor = ScannerInput.readNextLine("\tprocessor : ");
                            String operSys = ScannerInput.readNextLine("\toperating system : ");
                            techAPI.addTechnologyDevice(new Tablet(name,price,manufacturer,id,processor, storage, operSys));

                        }
                    }
            }
                else{
                    System.out.println("This id number already exists.");
                }
            }

            else{
                System.out.println("Manufacturer name is NOT valid");
            }
        }



    public void runReportsMenu(){
        int option = reportsMenu();
        while (option != 0) {
            switch (option) {
                case 1-> runManufacturerReports();
                case 2-> runTechReportsMenu();
                default->  System.out.println("Invalid option entered" + option);
            }
            ScannerInput.readNextLine("\n Press the enter key to continue");
            option = reportsMenu();
        }
    }
    private int reportsMenu() {
        System.out.println(""" 
                --------Reports Menu ---------
               | 1) Manufacturers Overview    | 
               | 2) Technology Overview         |
               | 0) Return to main menu       | 
                 -----------------------------  """);
        return ScannerInput.readNextInt("==>>");
    }
    public void runTechReportsMenu(){
        int option = techReportsMenu();
        while (option != 0) {
            switch (option) {
                case 1-> System.out.println(techAPI.listAllTechnologyDevices());
                case 2-> System.out.println(techAPI.listAllSmartBands());
                case 3-> System.out.println(techAPI.listAllSmartWatches());
                case 4-> System.out.println(techAPI.listAllTablets());
                case 5 -> listAllTechnologyAbove();
                case 6-> listAllTechnologyBelowPrice();
                case 7-> listAllTabletsForOperatingSystem();
                case 8 -> System.out.println(techAPI.topFiveMostExpensiveSmartWatch());

                default->  System.out.println("Invalid option entered" + option);
            }
            ScannerInput.readNextLine("\n Press the enter key to continue");
            option = techReportsMenu();
        }
    }

    private int techReportsMenu() {
        System.out.println(""" 
                ---------- Technology Reports Menu  ---------------------
               | 1) List all technology                                 | 
               | 2) List all SmartBands                                 |
               | 3) List all Smart watch                                |
               | 4) List all Tablets                                    |
               | 5) List all devices above a price                      |
               | 6) List all devices below a price                      |
               | 7) List all tablets by operating system                |
               | 8) List the top five most expensive smart watches      |
               | 0) Return to main menu                                 | 
                 ----------------------------------------------------  """);
        return ScannerInput.readNextInt("==>>");
    }
    private int manufacturerReportsMenu() {
        System.out.println(""" 
                ---------- Manufacturers Reports Menu  -------------
               | 1) List Manufacturers                              | 
               | 2) List Manufacturers from a given manufacturer    |
               | 3) List Manufacturers by a given name              |
               | 0) Return to main menu                             | 
                 ---------------------------------------------------  """);
        return ScannerInput.readNextInt("==>>");
    }
    public void runManufacturerReports() {
        int option = manufacturerReportsMenu();
        while (option != 0) {
            switch (option) {
                case 1-> System.out.println(manufacturerAPI.listManufacturers());
                case 2-> listAllTechFromaGivenManufacturer();
                case 3-> System.out.println("todo");
                default->  System.out.println("Invalid option entered" + option);
            }
            ScannerInput.readNextLine("\n Press the enter key to continue");
            option =  manufacturerReportsMenu();
        }
    }

//todo update methods counting methods
    public void listAllTechnologyBelowPrice() {
        double price = ScannerInput.readNextDouble("What price do you want to see technology below (inclusive)?  : ");
        System.out.println(techAPI.listAllTechnologyBelowPrice(price));
    }
    public void listAllTechnologyAbove() {
        double price = ScannerInput.readNextDouble("What price do you want to see technology above (inclusive)?  : ");
        System.out.println(techAPI.listAllTechnologyAbovePrice(price));
    }
    public void listAllTabletsForOperatingSystem() {
        String os= ScannerInput.readNextLine("Which operating system do you want to view tablets from?  : ");
        System.out.println(techAPI.listAllTabletsByOperatingSystem(os));
    }
    public void listAllTechFromaGivenManufacturer() {
        String manu  = ScannerInput.readNextLine("What manufacturer you want a list of cars for?  : ");
        Manufacturer m = manufacturerAPI.getManufacturerByName(manu);
        if (!(m == null))
            System.out.println(techAPI.listAllTechDevicesByChosenManufacturer(m));
        else
            System.out.println("No manufacturer with tha name exists");
    }



        //---------------------
        //  General Menu Items
        //---------------------

        private void saveAllData(){
            System.out.println("Storing all data....");
            try {
                techAPI.save();
                manufacturerAPI.save();
            } catch (Exception e) {
                System.err.println("Error writing to file: " + e);
            }
        }

        private void loadAllData(){
            System.out.println("Loading all data....");
            try {
                techAPI.load();
                manufacturerAPI.load();
            } catch (Exception e) {
                System.err.println("Error loading from this file:  " + e);
            }
        }

        //---------------------
        //  Helper Methods
        //---------------------
//    private String getValidLicense() {
//        String license = ScannerInput.validNextLine("\tLicense options " + LicenseUtility.getLicenseKeys() + ": ");
//        if (LicenseUtility.isValidLicense(license)) {
//            return license;
//        } else {
//            System.err.println("\tLicense is not valid - setting default of: " + LicenseUtility.getDefaultLicense());
//            return LicenseUtility.getDefaultLicense();
//        }
//    }

        private String getValidId(){
            String id = ScannerInput.readNextLine("\tID Number (must be unique): ");
            if (techAPI.isValidId(id)) {
                return id;
            } else {
                System.err.println("\tId already exists / is not valid.");
                return "";
            }
        }

        private Manufacturer getManufacturerByName(){
            String manufacturerName = ScannerInput.readNextLine("Please enter the manufacturer's name: ");
            if (manufacturerAPI.isValidManufacturer(manufacturerName)){
                return manufacturerAPI.getManufacturerByName(manufacturerName);
            }
            else{
                return null;
            }
        }



    }


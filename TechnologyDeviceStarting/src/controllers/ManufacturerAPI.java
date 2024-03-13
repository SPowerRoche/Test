package controllers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import models.Manufacturer;
import utils.ISerializer;

import static utils.Utilities.isValidIndex;
//todo include Manufacturer API into starter code
public class ManufacturerAPI  implements ISerializer {

    private List<Manufacturer> manufacturers = new ArrayList<>();

    private File file;

    public ManufacturerAPI(File file)  {
        this.file = file;
    }

    //---------------------
    // Create methods
    //---------------------
    public boolean addManufacturer(Manufacturer manufacturer) {
        if (isValidManufacturer(manufacturer.getManufacturerName())){
            return false;
        }
        return manufacturers.add(manufacturer);
    }

    //---------------------
    // Read methods
    //---------------------
    public Manufacturer getManufacturerByIndex(int index){
        if (isValidIndex(manufacturers, index)){
            return manufacturers.get(index);
        }
        else{
            return null;
        }
    }

    public Manufacturer getManufacturerByName (String manName){
        int index = retrieveManufacturerIndex(manName);
        if (index != -1){
            return manufacturers.get(index);
        }
        return null;
    }


    public String listManufacturers(){
        String listManufacturers = "";
        for (Manufacturer manufacturer : manufacturers){
            listManufacturers += manufacturers.indexOf(manufacturer) + ": " + manufacturer + "\n";
        }
        if (listManufacturers.equals("")){
            return "No manufacturers";
        }
        else {
            return listManufacturers;
        }
    }
    public String listAllByManufacturerName(String manuName){
        if (!manufacturers.isEmpty()) {
            String listManufacturers = "";
            for (Manufacturer manufacturer : manufacturers) {
                if (manufacturer.getManufacturerName().equalsIgnoreCase(manuName))
                    listManufacturers += manufacturers.indexOf(manufacturer) + ": " + manufacturer + "\n";
            }
            if (listManufacturers.equals("")) {
                return "No manufacturers of that name";
            } else {
                return listManufacturers;
            }
        }
        else return "There are no manufacturers in the list.";
    }
    //---------------------
    // Update methods
    //---------------------
    public boolean updateManufacturer(String manufacturerName, int numEmployees){
        if (isValidManufacturer(manufacturerName)){
            Manufacturer manufacturerByName = getManufacturerByName(manufacturerName);
            manufacturerByName.setNumEmployees(numEmployees);
            return true;
        }
        return false;
    }

    //---------------------
    // Delete methods
    //---------------------
    public boolean removeManufacturer(Manufacturer manufacturer){
        if (manufacturers.contains(manufacturer)) {
            return manufacturers.remove(manufacturer);
        }
        return false;
    }

    public Manufacturer removeManufacturerByName(String manufacturerName){
        int index = retrieveManufacturerIndex(manufacturerName);
        if (index != -1) {
            return manufacturers.remove(index);
        }
        return null;
    }

    //---------------------
    // Validation Methods
    //---------------------
    public boolean isValidManufacturer(String manufacturerName){
        for (Manufacturer manufacturer : manufacturers){
            if (manufacturer.getManufacturerName().equalsIgnoreCase(manufacturerName)){
                return true;
            }
        }
        return false;
    }

    public int retrieveManufacturerIndex(String manufacturerName){
        for (Manufacturer manufacturer : manufacturers){
            if (isValidManufacturer(manufacturerName)){
                return manufacturers.indexOf(manufacturer);
            }
        }
        return -1;
    }

    //---------------------
    // Getters/Setters
    //---------------------

    public List<Manufacturer> getManufacturers() {
        return manufacturers;
    }


    //---------------------
    // Persistence Methods
    //---------------------

    @Override
    public String fileName() {
        return String.valueOf(file);
    }

    public void save() throws Exception {
        var xstream = new XStream(new DomDriver());
        ObjectOutputStream os = xstream.createObjectOutputStream(new FileWriter(file));
        os.writeObject(manufacturers);
        os.close();
    }


    public void load() throws Exception {
        //list of classes that you wish to include in the serialisation, separated by a comma
        Class<?>[] classes = new Class[]{ Manufacturer.class};

        //setting up the xstream object with default security and the above classes
        XStream xstream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);

        //doing the actual serialisation to an XML file
        ObjectInputStream in = xstream.createObjectInputStream(new FileReader(file));
        manufacturers = (List<Manufacturer>) in.readObject();
        in.close();
    }
}

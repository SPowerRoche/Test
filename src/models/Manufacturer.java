package models;

import utils.Utilities;

import java.util.Objects;

public class Manufacturer {
    private String manufacturerName = ""; // max 20 chars
    private int numEmployees = 1;   // >= 1, default 1

    public Manufacturer(String manufacturerName, int numEmployees) {
        this.manufacturerName = Utilities.truncateString(manufacturerName, 20);
        setNumEmployees(numEmployees);
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        if (Utilities.validStringlength(manufacturerName, 20))
        this.manufacturerName = manufacturerName;
    }

    public int getNumEmployees() {
        return numEmployees;
    }

    public void setNumEmployees(int numEmployees) {
        if (numEmployees >=1)
          this.numEmployees = numEmployees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Manufacturer that)) return false;
        return numEmployees == that.numEmployees && Objects.equals(manufacturerName, that.manufacturerName);
    }

    @Override
    public String toString() {
        return "Manufacturer{" +
                "manufacturerName='" + manufacturerName + '\'' +
                ", numEmployees=" + numEmployees + (numEmployees==1 ? " employee" : " employees")+
                '}';
    }
}

package models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TechnologyTest {



    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        validTablet=invalidTablet=null;
    }
    private Tablet validTablet, invalidTablet;

    @org.junit.jupiter.api.BeforeEach
    public void setUp() {
        Manufacturer manufacturer = new Manufacturer("Samsung", 333);
        Manufacturer invalidManufacturer = new Manufacturer("ABCDEFGHIJKLMNOPQRSTU", 0);
        validTablet = new Tablet("Galaxy Tab S7", 799.99, manufacturer, "123456", "Snapdragon 865", 64, "Android");
        invalidTablet = new Tablet("Galaxy Tab S7 version 1 c.09462b", 19, invalidManufacturer, "12345678910", "Snapdragon 865", 64, "Android");

    }

    @Test
    public void testGetModelName() {

        assertEquals("Galaxy Tab S7", validTablet.getModelName());
        assertEquals("Galaxy Tab S7 version 1 c.0946", invalidTablet.getModelName());
    }

    @Test
    public void testSetModelName() {
        validTablet.setModelName("iPad Pro");
        assertEquals("iPad Pro", validTablet.getModelName());
        validTablet.setModelName("Galaxy Tab S7 version 1 c.09462b");
        assertEquals("iPad Pro", validTablet.getModelName());
    }

    @Test
    public void testGetPrice() {

        assertEquals(799.99, validTablet.getPrice(), 0.01);
        assertEquals(20, invalidTablet.getPrice(), 0.01);
    }

    @Test
    public void testSetPrice() {
        validTablet.setPrice(899.99);
        assertEquals(899.99, validTablet.getPrice(), 0.01);
        validTablet.setPrice(19);
        assertEquals(899.99, validTablet.getPrice(), 0.01);
    }

    @Test
    public void testGetManufacturer() {

        assertEquals("Samsung", validTablet.getManufacturer().getManufacturerName());
        assertEquals("ABCDEFGHIJKLMNOPQRST", invalidTablet.getManufacturer().getManufacturerName());
    }

    @Test
    public void testSetManufacturer() {
        Manufacturer newManufacturer = new Manufacturer("Apple", 1200);
        validTablet.setManufacturer(newManufacturer);
        assertEquals("Apple", validTablet.getManufacturer().getManufacturerName());
    }

    @Test
    public void testGetId() {

        assertEquals("123456", validTablet.getId());
        assertEquals("unknown", invalidTablet.getId());
    }

    @Test
    public void testSetId() {
        validTablet.setId("789012");
        assertEquals("789012", validTablet.getId());
        validTablet.setId("789012ABCDE");
        assertEquals("789012", validTablet.getId());
    }
    @Test
    public void testToString() {
        String expected = "Model: Galaxy Tab S7, Price: €799.99, Manufacturer Details: Manufacturer{manufacturerName='Samsung', numEmployees=333 employees}, ID: 123456";
        assertTrue( validTablet.toString().contains(expected));
        expected = "Model: Galaxy Tab S7 version 1 c.0946, Price: €20.0, Manufacturer Details: Manufacturer{manufacturerName='ABCDEFGHIJKLMNOPQRST', numEmployees=1 employee}, ID: unknown";
        assertTrue( invalidTablet.toString().contains(expected));

    }
}

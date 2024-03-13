package models;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TabletTest {

    private Tablet validTablet;
    private Tablet invalidTablet;



    @After
    public void tearDown() {
        validTablet=invalidTablet=null;
    }

    @Before
    public void setUp() {
        Manufacturer manufacturer = new Manufacturer("Samsung", 333);
        Manufacturer invalidManufacturer = new Manufacturer("ABCDEFGHIJKLMNOPQRSTU", 0);
        validTablet = new Tablet("Galaxy Tab S7", 799.99, manufacturer, "123456", "Snapdragon 865", 64, "Android");
        invalidTablet = new Tablet("Galaxy Tab S7 version 1 c.09462b", 19, invalidManufacturer, "12345678910", "Snapdragon 8655678920", 19, "Android v1");

    }


    @Test
    public void testValidOperatingSystem() {
        assertEquals("Android", validTablet.getOperatingSystem());

    }

    @Test
    public void testInvalidOperatingSystem() {
        assertEquals("Windows", invalidTablet.getOperatingSystem()); // Invalid OS should default to "Windows OS"
    }
    @Test
    public void testSetValidOperatingSystem() {
        assertEquals("Android", validTablet.getOperatingSystem());
        validTablet.setOperatingSystem("iPad");
        assertEquals("iPad", validTablet.getOperatingSystem());
        validTablet.setOperatingSystem("Android");
        assertEquals("Android", validTablet.getOperatingSystem());
        validTablet.setOperatingSystem("Chrome");
        assertEquals("Chrome", validTablet.getOperatingSystem());
        validTablet.setOperatingSystem("Windows");
        assertEquals("Windows", validTablet.getOperatingSystem());
        validTablet.setOperatingSystem("Amazon Fire");
        assertEquals("Amazon Fire", validTablet.getOperatingSystem());

    }

    @Test
    public void testSetInvalidOperatingSystem() {
        assertEquals("Android", validTablet.getOperatingSystem());
        validTablet.setOperatingSystem("iPad12 OS");
        assertEquals("Android", validTablet.getOperatingSystem());
        validTablet.setOperatingSystem("Android OS");
        assertEquals("Android", validTablet.getOperatingSystem());
        validTablet.setOperatingSystem("Chrome OS");
        assertEquals("Android", validTablet.getOperatingSystem());
        validTablet.setOperatingSystem("Windows OS");
        assertEquals("Android", validTablet.getOperatingSystem());
        validTablet.setOperatingSystem("Amazon Fire OS");
        assertEquals("Android", validTablet.getOperatingSystem());
    }
    @Test
    public void testToString() {
        String expected = "Operating System: Android, Insurance Premium: €7.99";
        assertTrue( validTablet.toString().contains(expected));
        expected = "Operating System: Windows, Insurance Premium: €0.2";
        assertTrue( invalidTablet.toString().contains(expected));

    }

}

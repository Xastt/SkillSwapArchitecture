package sfedu.xast;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ConfigTest {
    @Test
    public void testGetPlanets() {
        List<String> planets = Arrays.asList("Земля", "Сатурн", "Марс", "Венера");
        assertEquals(4, planets.size());
        assertEquals("Земля", planets.get(0));
    }

    @Test
    public void testGetMonths() {
        Map<Integer, String> months = new HashMap<>();
        months.put(1, "Январь");
        months.put(2, "Февраль");
        months.put(3, "Март");
        months.put(4, "Апрель");
        months.put(5, "Май");
        months.put(6, "Июнь");
        months.put(7, "Июль");
        months.put(8, "Август");
        months.put(9, "Сентябрь");
        months.put(10, "Октябрь");
        months.put(11, "Ноябрь");
        months.put(12, "Декабрь");

        assertEquals("Март", months.get(3));
        assertEquals(12, months.size());
    }
}

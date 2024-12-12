package sfedu.xast;



import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        months.put(1, "Пн");
        months.put(2, "Вт");
        months.put(3, "Ср");
        months.put(4, "Чт");
        months.put(5, "Пт");
        months.put(6, "Сб");
        months.put(7, "Вс");

        assertEquals("Ср", months.get(3));
        assertEquals(7, months.size());
    }
}

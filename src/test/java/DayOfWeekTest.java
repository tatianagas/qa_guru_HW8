import data.DayOfWeek;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DayOfWeekTest {

    @ParameterizedTest (name = "Рабочий день")
    @EnumSource(DayOfWeek.class)
    void testIsWeekday(DayOfWeek day) {
        boolean isWeekday = isWeekday(day);
        if (day == DayOfWeek.SAT || day == DayOfWeek.SUN) {
            assertFalse(isWeekday);
        } else {
            assertTrue(isWeekday);
        }
    }

    private boolean isWeekday(DayOfWeek day) {
        return day != DayOfWeek.SAT && day != DayOfWeek.SUN;
    }
}

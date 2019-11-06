package test;

import weekend.NearestFriday;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class NearestFridayTest {

	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS");

	@Test
	@Parameters(method = "data_friday_DuringWholeWeek_IsCorrect")
	public void friday_DuringWholeWeek_IsCorrect(String expectation, String monday) {
		try {
			var friday = new NearestFriday(DATE_FORMATTER.parse(monday));
			assertEquals(DATE_FORMATTER.parse(expectation), friday.friday);
		} catch (ParseException ex) {}
	}

	@Test
	@Parameters(method = "data_friday_WhenFriday_IsCorrect")
	public void friday_WhenFriday_IsCorrect(String expectation, String suppliedFriday) {
		try {
			var friday = new NearestFriday(DATE_FORMATTER.parse(suppliedFriday));
			assertEquals(DATE_FORMATTER.parse(expectation), friday.friday);
		} catch (ParseException ex) {}
	}

	@Test
	@Parameters(method = "data_friday_WhenEndOfMonth_IsCorrect")
	public void friday_WhenEndOfMonth_IsCorrect(String expectation, String actual) {
		try {
			var friday = new NearestFriday(DATE_FORMATTER.parse(actual));
			assertEquals(DATE_FORMATTER.parse(expectation), friday.friday);
		} catch (ParseException ex) {}
	}

	@Test
	@Parameters(method = "data_friday_WhenEndOfYear_IsCorrect")
	public void friday_WhenEndOfYear_IsCorrect(String expectation, String actual) {
		try {
			var friday = new NearestFriday(DATE_FORMATTER.parse(actual));
			assertEquals(DATE_FORMATTER.parse(expectation), friday.friday);
		} catch (ParseException ex) {}
	}

	@Test
	@Parameters(method = "data_friday_WhenWeekend_IsNull")
	public void friday_WhenWeekend_IsNull(String weekendDate) {
		try {
			var friday = new NearestFriday(DATE_FORMATTER.parse(weekendDate));
			assertNull(friday.friday);
		} catch (ParseException ex) {}
	}

	@Test
	@Parameters(method = "data_friday_WhenEndOfFriday_IsNull")
	public void friday_WhenEndOfFriday_IsNull(String fridayDate) {
		try {
			var friday = new NearestFriday(DATE_FORMATTER.parse(fridayDate));
			assertNull(friday.friday);
		} catch (ParseException ex) {}
	}

	// --------------------------------------------------------------

	public Object[][] data_friday_DuringWholeWeek_IsCorrect() {
		return new String[][]{
			new String[]{
				"08.11.2019 18:00:00.000", "04.11.2019 08:55:35.432"
			},
			new String[]{
				"08.11.2019 18:00:00.000", "05.11.2019 13:55:00.432"
			},
			new String[]{
				"08.11.2019 18:00:00.000", "06.11.2019 18:25:00.000"
			},
			new String[]{
				"08.11.2019 18:00:00.000", "07.11.2019 00:00:00.000"
			},
			new String[]{
				"29.11.2019 18:00:00.000", "25.11.2019 00:00:00.000"
			},
			new String[]{
				"29.11.2019 18:00:00.000", "26.11.2019 01:00:12.200"
			},
			new String[]{
				"29.11.2019 18:00:00.000", "27.11.2019 16:04:12.200"
			},
			new String[]{
				"29.11.2019 18:00:00.000", "28.11.2019 17:59:59.999"
			},
		};
	}

	public Object[][] data_friday_WhenFriday_IsCorrect() {
		return new String[][]{
			new String[]{
				"08.11.2019 18:00:00.000", "08.11.2019 08:55:35.432"
			},
			new String[]{
				"08.11.2019 18:00:00.000", "08.11.2019 13:55:35.432"
			},
			new String[]{
				"08.11.2019 18:00:00.000", "08.11.2019 17:59:59.999"
			},
			new String[]{
				"08.11.2019 18:00:00.000", "08.11.2019 17:59:59.000"
			},
			new String[]{
				"08.11.2019 18:00:00.000", "08.11.2019 17:59:01.000"
			},
			new String[]{
				"29.11.2019 18:00:00.000", "29.11.2019 08:55:35.432"
			},
			new String[]{
				"29.11.2019 18:00:00.000", "29.11.2019 13:55:35.432"
			},
			new String[]{
				"29.11.2019 18:00:00.000", "29.11.2019 17:59:59.999"
			},
			new String[]{
				"29.11.2019 18:00:00.000", "29.11.2019 17:59:59.000"
			},
			new String[]{
				"29.11.2019 18:00:00.000", "29.11.2019 17:59:01.000"
			},
		};
	}

	public Object[][] data_friday_WhenEndOfMonth_IsCorrect() {
		return new String[][]{
			new String[]{
				"01.11.2019 18:00:00.000", "30.10.2019 13:45:33.443"
			},
			new String[]{
				"04.10.2019 18:00:00.000", "30.09.2019 13:45:33.443"
			},
			new String[]{
				"02.08.2019 18:00:00.000", "29.07.2019 13:45:33.443"
			},
		};
	}

	public Object[][] data_friday_WhenEndOfYear_IsCorrect() {
		return new String[][]{
			new String[]{
				"03.01.2020 18:00:00.000", "30.12.2019 23:32:22.232"
			},
			new String[]{
				"03.01.2020 18:00:00.000", "31.12.2019 23:32:22.232"
			},
			new String[]{
				"04.01.2019 18:00:00.000", "31.12.2018 04:32:22.232"
			},
		};
	}

	public Object[][] data_friday_WhenWeekend_IsNull() {
		return new String[][]{
			new String[]{
				"09.11.2019 14:23:43.432"
			},
			new String[]{
				"10.11.2019 14:23:43.432"
			},
			new String[]{
				"07.12.2019 00:00:00.000"
			},
			new String[]{
				"07.12.2019 23:59:59.999"
			},
			new String[]{
				"08.12.2019 00:00:00.000"
			},
			new String[]{
				"08.12.2019 09:23:43.432"
			},
			new String[]{
				"08.12.2019 23:59:59.999"
			},
		};
	}

	public Object[][] data_friday_WhenEndOfFriday_IsNull() {
		return new String[][]{
			new String[]{
				"08.11.2019 18:00:00.000"
			},
			new String[]{
				"08.11.2019 19:00:00.000"
			},
			new String[]{
				"01.11.2019 19:20:00.000"
			},
		};
	}
}

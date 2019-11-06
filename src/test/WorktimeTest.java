package test;

import weekend.Worktime;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.*;
import org.junit.Test;

public class WorktimeTest {

	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS");

	@Test
	public void isWeekend_AtWeekend_IsTrue() {
		String[] data = {
			"09.11.2019 00:00:00.001",
			"09.11.2019 13:32:43.221",
			"10.11.2019 23:59:59.000"
		};
		try {
			var wt = new Worktime(DATE_FORMATTER.parse(data[0]));
			for (String date : data) {
				wt.date = DATE_FORMATTER.parse(date);
				assertTrue(wt.isWeekend());
			}
		} catch (ParseException ex) {}
	}

	@Test
	public void isWeekend_AfterFriday_IsTrue() {
		String[] data = {
			"01.11.2019 18:00:00.000",
			"01.11.2019 19:32:43.221",
			"01.11.2019 23:59:59.999",
			"08.11.2019 18:00:00.000",
			"08.11.2019 19:32:43.221",
			"08.11.2019 23:59:59.999",
			"31.01.2020 18:00:00.000",
			"31.01.2020 19:32:43.221",
			"31.01.2020 23:59:59.999"
		};
		try {
			var wt = new Worktime(DATE_FORMATTER.parse(data[0]));
			for (String date : data) {
				wt.date = DATE_FORMATTER.parse(date);
				assertTrue(wt.isWeekend());
			}
		} catch (ParseException ex) {}
	}

	@Test
	public void isWeekend_AtWorktime_IsFalse() {
		String[] data = {
			"08.11.2019 09:00:00.000",
			"08.11.2019 13:30:00.000",
			"08.11.2019 16:32:43.221",
			"08.11.2019 17:59:59.999",
		};
		try {
			var wt = new Worktime(DATE_FORMATTER.parse(data[0]));
			for (String date : data) {
				wt.date = DATE_FORMATTER.parse(date);
				assertFalse(wt.isWeekend());
			}
		} catch (ParseException ex) {}
	}

	@Test
	public void isLunch_AtLunch_IsTrue() {
		String[] data = {
			"08.11.2019 13:00:00.000",
			"08.11.2019 13:30:00.000",
			"08.11.2019 13:59:59.999",
		};
		try {
			var wt = new Worktime(DATE_FORMATTER.parse(data[0]));
			for (String date : data) {
				wt.date = DATE_FORMATTER.parse(date);
				assertTrue(wt.isLunch());
			}
		} catch (ParseException ex) {}
	}

	@Test
	public void isLunch_AtWorktime_IsFalse() {
		String[] data = {
			"08.11.2019 09:00:00.000",
			"08.11.2019 12:34:59.999",
			"08.11.2019 12:59:59.999",
			"08.11.2019 14:00:00.999",
			"08.11.2019 16:00:00.999",
			"08.11.2019 17:59:59.999",
		};
		try {
			var wt = new Worktime(DATE_FORMATTER.parse(data[0]));
			for (String date : data) {
				wt.date = DATE_FORMATTER.parse(date);
				assertFalse(wt.isLunch());
			}
		} catch (ParseException ex) {}
	}

	@Test
	public void isWorktime_AtWorktime_IsTrue() {
		String[] data = {
			"01.11.2019 09:00:00.000",
			"02.11.2019 12:34:59.999",
			"03.11.2019 12:59:59.999",
			"04.11.2019 14:00:00.999",
			"05.11.2019 16:00:00.999",
			"06.11.2019 17:59:59.999",
		};
		try {
			var wt = new Worktime(DATE_FORMATTER.parse(data[0]));
			for (String date : data) {
				wt.date = DATE_FORMATTER.parse(date);
				assertTrue(wt.isWorktime());
			}
		} catch (ParseException ex) {}
	}

	@Test
	public void isWorktime_OutOfWorktime_IsFalse() {
		String[] data = {
			"01.11.2019 00:00:00.000",
			"02.11.2019 05:34:59.999",
			"03.11.2019 08:59:59.999",
			"04.11.2019 18:00:00.999",
			"05.11.2019 20:03:00.999",
			"06.11.2019 23:59:59.999",
		};
		try {
			var wt = new Worktime(DATE_FORMATTER.parse(data[0]));
			for (String date : data) {
				wt.date = DATE_FORMATTER.parse(date);
				assertFalse(date, wt.isWorktime());
			}
		} catch (ParseException ex) {}
	}

	@Test
	public void isWorktime_AtLunch_IsFalse() {
		String[] data = {
			"01.11.2019 13:00:00.000",
			"02.11.2019 13:34:59.999",
			"03.11.2019 13:59:59.999",
		};
		try {
			var wt = new Worktime(DATE_FORMATTER.parse(data[0]));
			for (String date : data) {
				wt.date = DATE_FORMATTER.parse(date);
				assertFalse(wt.isWorktime());
			}
		} catch (ParseException ex) {}
	}

	@Test
	public void isEndOfDay_AfterWork_IsTrue() {
		String[] data = {
			"01.11.2019 18:00:00.000",
			"02.11.2019 20:34:59.999",
			"03.11.2019 23:59:59.999",
		};
		try {
			var wt = new Worktime(DATE_FORMATTER.parse(data[0]));
			for (String date : data) {
				wt.date = DATE_FORMATTER.parse(date);
				assertTrue(wt.isEndOfDay());
			}
		} catch (ParseException ex) {}
	}

	@Test
	public void isEndOfDay_AtWorktime_IsFalse() {
		String[] data = {
			"01.11.2019 09:00:00.000",
			"02.11.2019 12:34:59.999",
			"03.11.2019 12:59:59.999",
			"04.11.2019 14:00:00.999",
			"05.11.2019 16:00:00.999",
			"06.11.2019 17:59:59.999",
		};
		try {
			var wt = new Worktime(DATE_FORMATTER.parse(data[0]));
			for (String date : data) {
				wt.date = DATE_FORMATTER.parse(date);
				assertFalse(wt.isEndOfDay());
			}
		} catch (ParseException ex) {}
	}

	@Test
	public void isEndOfDay_AtLunch_IsFalse() {
		String[] data = {
			"01.11.2019 13:00:00.000",
			"02.11.2019 13:34:59.999",
			"03.11.2019 13:59:59.999",
		};
		try {
			var wt = new Worktime(DATE_FORMATTER.parse(data[0]));
			for (String date : data) {
				wt.date = DATE_FORMATTER.parse(date);
				assertFalse(wt.isEndOfDay());
			}
		} catch (ParseException ex) {}
	}

	public void getWeekendLoadedStatus_AtVeryBeginning_Is0() {}
	public void getWeekendLoadedStatus_AtWeekend_Is100() {}
	public void getWeekendLoadedStatus_AfterFriday_Is100() {}
	public void getWeekendLoadedStatus_AtLunch_DoesNotChange() {}
	public void getWeekendLoadedStatus_OutOfWorktime_DoesNotChange() {}
	public void getWeekendLoadedStatus_IsCorrect() {}
}

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

	@Test
	public void getWeekendLoadedStatus_AtVeryBeginning_Is0() {
		String[] data = {
			"04.11.2019 09:00:00.000",
			"04.11.2019 09:00:01.000",
			"04.11.2019 09:01:01.000",
		};
		try {
			var wt = new Worktime(DATE_FORMATTER.parse(data[0]));
			for (String date : data) {
				wt.date = DATE_FORMATTER.parse(date);
				assertEquals(0d, wt.getWeekendLoadedStatus(), 6e-2d);
			}
		} catch (ParseException ex) {}
	}

	@Test
	public void getWeekendLoadedStatus_AtWeekend_Is100() {
		String[] data = {
			"09.11.2019 00:00:00.000",
			"09.11.2019 16:00:00.000",
			"10.11.2019 00:00:00.000",
			"10.11.2019 23:59:59.999",
		};
		try {
			var wt = new Worktime(DATE_FORMATTER.parse(data[0]));
			for (String date : data) {
				wt.date = DATE_FORMATTER.parse(date);
				assertEquals(100d, wt.getWeekendLoadedStatus(), 1e-10d);
			}
		} catch (ParseException ex) {}
	}

	@Test
	public void getWeekendLoadedStatus_AfterFriday_Is100() {
		String[] data = {
			"08.11.2019 17:59:59.000",
			"08.11.2019 18:00:00.000",
			"08.11.2019 21:00:00.000",
		};
		try {
			var wt = new Worktime(DATE_FORMATTER.parse(data[0]));
			for (String date : data) {
				wt.date = DATE_FORMATTER.parse(date);
				assertEquals(100d, wt.getWeekendLoadedStatus(), 1e-2d);
			}
		} catch (ParseException ex) {}
	}

	@Test
	public void getWeekendLoadedStatus_AtLunch_DoesNotChange() {
		String[] data = {
			"08.11.2019 13:00:00.000",
			"08.11.2019 13:30:00.000",
			"08.11.2019 13:59:00.000",
		};
		double[] status = new double[data.length];
		double percentage = ((double) (4*8*60*60*1000+4*60*60*1000))/((double) (5*8*60*60*1000))*100d;
		try {
			var wt = new Worktime(DATE_FORMATTER.parse(data[0]));
			for (int i = 0; i < data.length; i++) {
				var date = data[i];
				wt.date = DATE_FORMATTER.parse(date);
				status[i] = wt.getWeekendLoadedStatus();
			}
			for (double st : status) {
				assertEquals(percentage, st, 1e-5d);
			}
		} catch (ParseException ex) {}
	}

	@Test
	public void getWeekendLoadedStatus_OutOfWorktime_DoesNotChange() {
		String[] data_0 = {
			"04.11.2019 05:34:23.432",
			"04.11.2019 08:34:23.432",
			"04.11.2019 08:59:59.999",
		};
		String[] data_20 = {
			"04.11.2019 18:00:00.000",
			"04.11.2019 18:29:00.000",
			"04.11.2019 20:29:00.000",
		};
		String[] data_80 = {
			"08.11.2019 05:34:23.432",
			"08.11.2019 08:34:23.432",
			"08.11.2019 08:59:59.999",
		};
		double[] status_0 = new double[data_0.length];
		double[] status_20 = new double[data_20.length];
		double[] status_80 = new double[data_80.length];
		try {
			var wt = new Worktime(DATE_FORMATTER.parse(data_0[0]));
			for (int i = 0; i < data_0.length; i++) {
				wt.date = DATE_FORMATTER.parse(data_0[i]);
				status_0[i] = wt.getWeekendLoadedStatus();
			}
			for (double st : status_0) {
				assertEquals(0d, st, 1e-10);
			}
			wt = new Worktime(DATE_FORMATTER.parse(data_20[0]));
			for (int i = 0; i < data_20.length; i++) {
				wt.date = DATE_FORMATTER.parse(data_20[i]);
				status_20[i] = wt.getWeekendLoadedStatus();
			}
			for (double st : status_20) {
				assertEquals(20d, st, 1e-10);
			}
			wt = new Worktime(DATE_FORMATTER.parse(data_80[0]));
			for (int i = 0; i < data_80.length; i++) {
				wt.date = DATE_FORMATTER.parse(data_80[i]);
				status_80[i] = wt.getWeekendLoadedStatus();
			}
			for (double st : status_80) {
				assertEquals(80d, st, 1e-10);
			}
		} catch (ParseException ex) {}
	}
	public void getWeekendLoadedStatus_IsCorrect() {}
}

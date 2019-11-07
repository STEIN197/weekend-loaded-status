package weekend;

import java.util.Date;

public class Worktime {

	/** Milliseconds in a week */
	private static final double MS_WEEK = 144000000;
	/** Milliseconds in a day */
	private static final double MS_DAY = 28800000;
	/** Milliseconds in an hour */
	private static final double MS_HOUR = 3600000;
	/** Milliseconds in a minute */
	private static final double MS_MINUTE = 60000;
	/** Milliseconds in a second */
	private static final double MS_SECOND = 1000;

	public Date date;
	private final NearestFriday friday;

	public Worktime(Date date) {
		this.date = date;
		this.friday = new NearestFriday(date);
	}

	public Worktime() {
		this(new Date());
	}

	public boolean isWeekend() {
		return this.friday.friday == null || this.friday.friday.before(this.date);
	}

	public boolean isLunch() {
		return this.date.getHours() == 13;
	}

	public boolean isWorktime() {
		int hours = this.date.getHours();
		return 9 <= hours && hours <= 12 || 14 <= hours && hours <= 17;
	}

	public boolean isEndOfDay() {
		return this.date.getHours() >= 18;
	}

	public double getWeekendLoadedStatus() {
		if (this.friday.friday == null || this.friday.friday.before(this.date))
			return 100;
		double millisHasPassed = 0;
		millisHasPassed += (this.date.getDay() - 1) * MS_DAY;
		int hours = this.date.getHours();
		// TODO bit mask with switch instead of if's
		if (hours < 9) {
			return getPercentage(millisHasPassed);
		} else if (9 <= hours && hours <= 12) {
			millisHasPassed += (hours - 9) * MS_HOUR;
			millisHasPassed += this.date.getMinutes() * MS_MINUTE;
			millisHasPassed += this.date.getSeconds() * MS_SECOND;
			return getPercentage(millisHasPassed);
		} else if (hours == 13) {
			millisHasPassed += 4 * MS_HOUR;
			return getPercentage(millisHasPassed);
		} else if (14 <= hours && hours <= 17) {
			millisHasPassed += (hours - 10) * MS_HOUR;
			millisHasPassed += this.date.getMinutes() * MS_MINUTE;
			millisHasPassed += this.date.getSeconds() * MS_SECOND;
			return getPercentage(millisHasPassed);
		} else {
			millisHasPassed += MS_DAY;
			return getPercentage(millisHasPassed);
		}
	}

	private static double getPercentage(double value) {
		return value / MS_WEEK * 100d;
	}
}
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

	private static final byte DAY_BEGINNING = 0b00001;
	private static final byte DAY_FIRST_HALF = 0b00010;
	private static final byte DAY_LUNCH = 0b00100;
	private static final byte DAY_SECOND_HALF = 0b01000;
	private static final byte DAY_END = 0b10000;

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
		double millisHavePassed = 0;
		millisHavePassed += (this.date.getDay() - 1) * MS_DAY;
		int hours = this.date.getHours();
		byte state = getDayState(hours);
		switch (state) {
			case DAY_FIRST_HALF:
			case DAY_SECOND_HALF:
				millisHavePassed += (hours - (state == DAY_FIRST_HALF ? 9 : 10)) * MS_HOUR;
				millisHavePassed += this.date.getMinutes() * MS_MINUTE + this.date.getSeconds() * MS_SECOND;
				break;
			case DAY_LUNCH:
				millisHavePassed += 4 * MS_HOUR;
				break;
			case DAY_END:
				millisHavePassed += MS_DAY;
				break;
		}
		return getPercentage(millisHavePassed);
	}

	private static double getPercentage(double value) {
		return value / MS_WEEK * 100d;
	}

	private static byte getDayState(int hour) {
		if (hour < 9)
			return DAY_BEGINNING;
		if (hour < 13)
			return DAY_FIRST_HALF;
		if (hour == 13)
			return DAY_LUNCH;
		if (hour < 18)
			return DAY_SECOND_HALF;
		return DAY_END;
	}
}
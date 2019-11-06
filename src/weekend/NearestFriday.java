package weekend;

import java.util.Date;

public class NearestFriday {

	public final Date friday;

	private static final int FRIDAY = 5;
	private static final int SATURDAY = 6;
	private static final int SUNDAY = 0;

	public NearestFriday(Date dateFrom) {
		this.friday = this.getNextFriday(dateFrom);
	}

	/**
	 * Returns next nearest friday on a week.
	 * If it is a weekend, i.e. saturday or sunday,
	 * then it will return null.
	 * @param dateFrom Date from which next friday is calculated.
	 * @return Nearest next friday or null if it is weekend.
	 *         If date is returned, then its time is set to 18:00:00.
	 */
	private Date getNextFriday(Date dateFrom) {
		int day = dateFrom.getDay();
		final int millisecondsInDay = 86400000;
		boolean isWeekend = day == SATURDAY || day == SUNDAY;
		boolean isEndOfFriday = day == FRIDAY && dateFrom.getHours() >= 18;
		if (isWeekend || isEndOfFriday)
			return null;
		int daysToAdd = FRIDAY - day;
		var nextFriday = new Date();
		nextFriday.setTime(dateFrom.getTime() + daysToAdd * millisecondsInDay);
		nextFriday.setHours(18);
		nextFriday.setMinutes(0);
		nextFriday.setSeconds(0);
		nextFriday.setTime(nextFriday.getTime() / 1000 * 1000); // truncates milliseconds
		return nextFriday;
	}
}

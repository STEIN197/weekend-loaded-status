package weekend;

import java.util.Date;

public class Worktime {

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
		long totalMillis = 5 * 8 * 60 * 60 * 1000;
		long millisHasPassed = 0;
		millisHasPassed += (this.date.getDay() - 1) * 8 * 60 * 60 * 1000;
		int hours = this.date.getHours();
		if (hours < 13) {
			millisHasPassed += (hours - 9) * 60 * 60 * 1000;
		} else {
			millisHasPassed += (hours - 10) * 60 * 60 * 1000;
		}
		millisHasPassed += this.date.getMinutes() * 60 * 1000;
		millisHasPassed += this.date.getSeconds() * 1000;
		return ((double) millisHasPassed) / ((double) totalMillis) * 100d;
	}
}
package weekend;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ProgressbarAction {

	public final Application app;
	private Timer mainTimer;
	private Date nextFriday;

	public ProgressbarAction(Application app) {
		this.app = app;
		this.setNextFridayDate();
	}

	public boolean isWorktime() {
		var calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		boolean hourMatchesWorktime = 9 <= hour && hour < 13 || 14 <= hour && hour < 18;
		boolean dayMatchesWorktime = Calendar.MONDAY <= day && day <= Calendar.FRIDAY;
		return hourMatchesWorktime && dayMatchesWorktime;
	}

	public boolean isWeekend() {
		var calendar = Calendar.getInstance();
		var day = calendar.get(Calendar.DAY_OF_WEEK);
		return Calendar.SATURDAY <= day && day <= Calendar.SUNDAY;
	}

	public void start() {
		this.mainTimer = new Timer();
		var task = this.new Task();
		this.mainTimer.scheduleAtFixedRate(task, 0, 1000);
	}


	public float getLoadedStatus() {
		int totalSeconds = 60 * 60 * 8 * 5;
		var now = Calendar.getInstance();
		var daysHavePast = (Calendar.FRIDAY - now.get(Calendar.DAY_OF_WEEK)) - 1;
		int hoursHavePast;
		if (now.get(Calendar.HOUR_OF_DAY) < 13) {
			hoursHavePast = now.get(Calendar.HOUR_OF_DAY) - 9;
		} else {
			hoursHavePast = now.get(Calendar.HOUR_OF_DAY) - 14;
		}
		int minutesHavePast = now.get(Calendar.MINUTE);
		int secondsHavePast = now.get(Calendar.SECOND);
		int totalPassed = daysHavePast * 8 * 60 * 60 + hoursHavePast * 60 * 60 + minutesHavePast * 60 + secondsHavePast;
		return ((float) totalPassed) / ((float) totalSeconds) * 100;
	}

	private void setNextFridayDate() {
		var fridayDate = new Date();
		var day = fridayDate.getDay();
		int daysToAdd = 5 - day;
		fridayDate.setDate(fridayDate.getDate() + daysToAdd);
		fridayDate.setHours(18);
		fridayDate.setMinutes(0);
		fridayDate.setSeconds(0);
		this.nextFriday = fridayDate;
	}

	private class Task extends TimerTask {

		@Override
		public void run() {
			if (!ProgressbarAction.this.isWorktime())
				return;
			Application app = ProgressbarAction.this.app;
			if (ProgressbarAction.this.isWeekend()) {
				app.label.setText("Weekend has come!");
				ProgressbarAction.this.mainTimer.cancel();
			} else {
				var loaded = ProgressbarAction.this.getLoadedStatus();
				app.label.setText(loaded + "% of " + app.dateFormatter.format(ProgressbarAction.this.nextFriday) + " has loaded");
				app.progressbar.setValue((int) loaded);
			}
		}
	}
}
package time;

public class Time {
	private int year, month, day, hour, minute;
	private static long reftime;
	private static Time starttime;

	public Time(int year, int month, int day, int hour, int minute) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
	}

	public Time(int hour, int minute) {
		this.year = 0;
		this.month = 0;
		this.day = 0;
		this.hour = hour;
		this.minute = minute;
	}

	public Time(long seconds) {
		year = (int) (seconds / (60 * 60 * 24 * 365));
		seconds -= year * 60L * 60 * 24 * 365;
		month = (int) (seconds / (60 * 60 * 24 * 30));
		seconds -= month * 60L * 60 * 24 * 30;
		day = (int) (seconds / (60 * 60 * 24));
		seconds -= day * 60L * 60 * 24;
		hour = (int) (seconds / (60 * 60));
		seconds -= hour * 60L * 60;
		minute = (int) (seconds / (60));
		seconds -= minute * 60L;
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	public int getHour() {
		return hour;
	}

	public int getMinute() {
		return minute;
	}

	public long toSeconds() {
		return year * 31536000L + month * 2592000 + day * 86400 + hour * 3600
				+ minute * 60;
	}

	@Override
	public String toString() {
		String strMonth = Integer.toString(month);
		String strDay = Integer.toString(day);
		String strHour = Integer.toString(hour);
		String strMinute = Integer.toString(minute);
		if (strMonth.length() == 1)
			strMonth = "0" + strMonth;
		if (strDay.length() == 1)
			strDay = "0" + strDay;
		if (strHour.length() == 1)
			strHour = "0" + strHour;
		if (strMinute.length() == 1)
			strMinute = "0" + strMinute;
		return year + "." + strMonth + "." + strDay + " " + strHour + ":"
				+ strMinute;
	}

	static {
		java.util.Date date = new java.util.Date();
		reftime = date.getTime() / 1000;
	}

	public static void setStartTime(Time time) {
		starttime = time;
	}

	public static Time getStartTime() {
		return starttime;
	}

	public static Time now() {
		java.util.Date date = new java.util.Date();
		long seconds = date.getTime() / 1000 - reftime;
		seconds *= 60;
		return new Time(seconds + starttime.toSeconds());
	}

}

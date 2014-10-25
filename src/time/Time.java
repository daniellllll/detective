package time;

public class Time {
	private int year, month, day, hour, minute;
	private static long starttime;

	public Time(int year, int month, int day, int hour, int minute) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
	}

	public Time(long seconds) {		
		year = (int) (seconds / (60 * 60 * 24 * 365));
		seconds -= year * 60 * 60 * 24 * 365;
		month = (int) (seconds / (60 * 60 * 24 * 30));
		seconds -= month * 60 * 60 * 24 * 30;
		day = (int) (seconds / (60 * 60 * 24));
		seconds -= month * 60 * 60 * 24;
		hour = (int) (seconds / (60 * 60));
		seconds -= month * 60 * 60;
		minute = (int) (seconds / (60));
		seconds -= month * 60;
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

	@Override
	public String toString() {
		return year + "." + month + "." + day + " " + hour + ":" + minute;
	}

	static {
		java.util.Date date = new java.util.Date();
		starttime = date.getTime() / 1000;
	}

	public static Time now() {
		java.util.Date date = new java.util.Date();
		long seconds = date.getTime() / 1000 - starttime;
		return new Time(seconds);
	}

}

package time;

import java.util.Calendar;
import java.util.Date;

public class Time {
	public static enum Weekday {
		MON, TUE, WED, THU, FRI, SAT, SUN
	};

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

	public Time(String str) {
		String datestr = str.split(" ")[0];
		String timestr = str.split(" ")[1];
		String datedata[] = datestr.split("\\.");
		String timedata[] = timestr.split(":");
		year = Integer.parseInt(datedata[0]);
		month = Integer.parseInt(datedata[1]);
		day = Integer.parseInt(datedata[2]);
		hour = Integer.parseInt(timedata[0]);
		minute = Integer.parseInt(timedata[1]);
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

	public Weekday getWeekday() {
		Date d = new Date(year, month, day);
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		switch (dayOfWeek) {
		case Calendar.SUNDAY:
			return Weekday.SUN;
		case Calendar.MONDAY:
			return Weekday.MON;
		case Calendar.TUESDAY:
			return Weekday.TUE;
		case Calendar.WEDNESDAY:
			return Weekday.WED;
		case Calendar.THURSDAY:
			return Weekday.THU;
		case Calendar.FRIDAY:
			return Weekday.FRI;
		case Calendar.SATURDAY:
			return Weekday.SAT;
		}
		return null;
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

package generators.calendar;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import persons.Person;
import time.Time;
import time.Timespan;
import calendar.Calendar;
import calendar.Event;
import calendar.Event.Activity;

public abstract class CalendarGenerator {

	private class ActivityTime {
		public Activity activity;
		public Timespan timespan;
		public int duration;

		public ActivityTime(Activity activity, int startHour, int startMinute,
				int endHour, int endMinute, int duration) {
			this.activity = activity;
			this.timespan = new Timespan(startHour, startMinute, endHour,
					endMinute);
			this.duration = duration;
		}
	}

	protected Time from, to;
	protected Person person;

	public CalendarGenerator(Time from, Time to, Person person) {
		this.from = from;
		this.to = to;
		this.person = person;

	}

	public Queue<ActivityTime> getDailyActivities() {
		Queue<ActivityTime> q = new LinkedList<>();
		q.offer(new ActivityTime(Activity.SLEEPING, 18, 0, 12, 0, 320));
		q.offer(new ActivityTime(Activity.LUNCH, 10, 0, 18, 0, 45));
		q.offer(new ActivityTime(Activity.BREAKFAST, 4, 0, 12, 0, 30));
		q.offer(new ActivityTime(Activity.DINNER, 16, 0, 24, 0, 45));
		q.offer(new ActivityTime(Activity.BATH, 0, 0, 23, 59, 45));
		q.offer(new ActivityTime(Activity.SHOPPING, 0, 0, 23, 59, 60));
		q.offer(new ActivityTime(Activity.READING_A_BOOK, 0, 0, 23, 59, 60));
		q.offer(new ActivityTime(Activity.WALK, 0, 0, 23, 59, 60));
		return q;
	}

	public abstract void generate();

	protected void generateCalendar(Calendar dailyRoutine) {
		Calendar calendar = person.getCalendar();

		Time first = new Time(from.getHour(), from.getMinute());
		Event actEvent = dailyRoutine.get(first);
		Event lastEvent = actEvent;
		Time startTime = new Time(from.toSeconds());

		for (long t = from.toSeconds(); t <= to.toSeconds(); t += 60) {
			Time t1 = new Time(t);
			Time t2 = new Time(t1.getHour(), t1.getMinute());
			actEvent = dailyRoutine.get(t2);
			if (actEvent != lastEvent) {
				calendar.addEvent(lastEvent, startTime, new Time(t - 60));
				startTime = new Time(t);
				lastEvent = actEvent;
			}
		}
	}
}

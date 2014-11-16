package time;

public class Timespan {
	private Time start;
	private Time end;

	public Timespan(Time start, Time end) {
		this.start = start;
		if (end.toSeconds() < start.toSeconds()) {
			this.end = new Time(0, 0, 1, end.getHour(), end.getMinute());
		} else {
			this.end = end;
		}
	}

	public Timespan(int startHours, int startMinutes, int endHours,
			int endMinutes) {
		this(new Time(startHours, startMinutes), new Time(endHours, endMinutes));
	}

	public Time getStartTime() {
		return start;
	}

	public Time getEndTime() {
		return end;
	}
	
	@Override
	public String toString(){
		return start + " - " + end;
	}
}

package crime;

import time.Time;
import crime.motive.AffairGenerator;
import crime.motive.MotiveGenerator;
import crime.weapon.GunGenerator;
import crime.weapon.WeaponGenerator;

public class CrimeGenerator {

	public static void generate() {
		Time time = generateTime();
		System.out.println("The murder happened at " + time+ ".");
		MotiveGenerator motive = new AffairGenerator();
		WeaponGenerator weapon = new GunGenerator(motive.getOffender(), time);
		System.out.println(motive);
		System.out.println(weapon);
	}

	private static Time generateTime() {
		Time time = new Time(Time.now().toSeconds() - 60 * 60 * 24 * 7);
		return time;
	}
}

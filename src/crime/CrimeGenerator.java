package crime;

import crime.motive.AffairGenerator;
import crime.motive.MotiveGenerator;

public class CrimeGenerator {

	public static void generate() {
		MotiveGenerator motive = new AffairGenerator();
		System.out.println(motive);
	}
}

package crime;

import crime.motive.AffairGenerator;
import crime.motive.MotiveGenerator;
import crime.murder.MurderGenerator;
import crime.murder.TheatreMurder;
import crime.weapon.GunGenerator;
import crime.weapon.WeaponGenerator;

public class CrimeGenerator {

	public static void generate() {
		MotiveGenerator motive = new AffairGenerator();
		MurderGenerator murder = new TheatreMurder(motive.getOffender(),
				motive.getVictim());
		WeaponGenerator weapon = new GunGenerator(motive.getOffender(),
				murder.getTime());
		System.out.println(murder);
		System.out.println(motive);
		System.out.println(weapon);
	}

}

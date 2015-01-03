package crime.weapon;

import persons.Person;
import time.Time;
import items.Fingerprint;
import items.Item;

public abstract class WeaponGenerator {
	protected Item weapon;
	protected Person offender;
	protected Time time;

	public WeaponGenerator(Person offender, Time time) {
		this.offender = offender;
		this.time = time;
	}

	public Item getWeapon() {
		return weapon;
	}

	protected void addFingerprints() {
		Fingerprint fp = offender.createFingerprint();
		fp.insertInto(weapon);
	}

}

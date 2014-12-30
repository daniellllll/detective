package utils;

import java.util.List;

public class Random {
	private static java.util.Random rand;

	static {
		rand = new java.util.Random();
	}

	public static int randInt(int min, int max) {
		return rand.nextInt(max - min) + min;
	}

	public static <E> void getUniqueElems(E[] src, E[] random) {
		for (int i = 0; i < random.length; i++) {
			E randElem = src[randInt(0, src.length)];
			while (arrayContains(random, randElem)) {
				randElem = src[randInt(0, src.length)];
			}
			random[i] = randElem;
		}
	}

	public static <E> E getRandElem(E[] array) {
		return array[randInt(0, array.length)];
	}

	public static <E> E getRandElem(List<E> list) {
		return list.get(randInt(0, list.size()));
	}

	private static <E> boolean arrayContains(E[] array, E item) {
		for (E e : array) {
			if (e != null && e.equals(item)) {
				return true;
			}
		}
		return false;
	}
}

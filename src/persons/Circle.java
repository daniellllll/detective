package persons;

import java.util.ArrayList;
import java.util.List;

public class Circle {
	
	private List<Person> circle;
	public enum Type {FRIENDS, ENEMIES, ACQUAINTANCES, FAMILY};
	
	public Circle (){
		circle = new ArrayList();
		
	}
	
	public void add(Person person){
		circle.add(person);
	}
	
	public void remove(Person person){
		circle.remove(person);
	}
}

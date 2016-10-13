import java.util.*;

public class Compound
{
	public String name;
	public boolean substrate;
	public boolean makeable;
	public int price;
	public ArrayList<Reaction> madeFrom;
	public ArrayList<Reaction> madeTo;
	public boolean visited = false;

	public Compound(String name)
	{
		this.name = name;
		substrate = false;
		makeable = false;
		price = -1;
		madeFrom = new ArrayList<Reaction>();
		madeTo = new ArrayList<Reaction>();
	}
	
	public Compound(String name, int price)
	{
		this.name = name;
		substrate = false;
		makeable = false;
		this.price = price;
		madeFrom = new ArrayList<Reaction>();
		madeTo = new ArrayList<Reaction>();
		substrate = true;
	}
	
	public String toString() {
		String ret = "Compound " + name + " made from...used in\n";
		for (Reaction r : madeFrom) {
			ret += r.name + " ";
		}
		ret += "...";
		for (Reaction r : madeTo) {
			ret += r.name + " ";
		}
		return ret;
	}
}

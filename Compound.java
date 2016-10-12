import java.util.*;

public class Compound
{
	public String name;
	private boolean substrate;
	private boolean makeable;
	private int price;
	public ArrayList<Reaction> madeFrom;
	public ArrayList<Reaction> madeTo;

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

	public void set_substrate(boolean b)
	{
		substrate = b;
	}

	public boolean isSubstrate()
	{
		return substrate;
	}

	public void set_makeable(boolean b)
	{
		makeable = b;
	}

	public boolean isMakeable()
	{
		return makeable;
	}

	public void set_price(int price)
	{
		this.price = price;
	}

	public int getPrice()
	{
		return price;
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

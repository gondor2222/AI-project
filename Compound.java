import java.util.*;

public class Compound
{
	private int name;
	private boolean substrate;
	private boolean makeable;
	private int price;
	public ArrayList<Reaction> madeFrom;
	public ArrayList<Reaction> madeTo;

	public Compound(int name)
	{
		this.name = name;
		substrate = false;
		makeable = false;
		price = -1;
		madeFrom = new ArrayList<Reaction>();
		madeTo = new ArrayList<Reaction>();
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
}

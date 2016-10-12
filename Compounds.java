class Compounds
{
	private int name;
	private boolean substrate;
	private boolean makeable;
	private int price;

	public Compounds(int name)
	{
		this.name = name;
		substrate = false;
		makeable = false;
		price = -1;
	}

	public void set_substrate(boolean b)
	{
		substrate = b;
	}

	public boolean get_substrate()
	{
		return substrate;
	}

	public void set_makeable(boolean b)
	{
		makeable = b;
	}

	public boolean get_makeable()
	{
		return makeable();
	}

	public void set_price(int price)
	{
		this.price = price;
	}

	public int get_price()
	{
		return price;
	}
}

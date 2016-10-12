import java.util.*;

public class Reaction {

	public ArrayList<Compound> madeFrom;
	public ArrayList<Compound> madeTo;
	int price;
	
	public Reaction(int price, ArrayList<Compound> in, ArrayList<Compound> out) {
		this.price = price;
		madeFrom = in;
		madeTo = out;
	}

	public boolean isMakeable(ArrayList<Compound> compounds)
	{
		int a = madeFrom.size();
		int b = compounds.size();
		for(int i = 0; i < a; i++)
		{
			for(int j = 0; j < b; j++)
			{
				if(madeFrom.get(i).getName() == compounds.get(j).getName())
				{
					break;
				}
				if(j == b-1)
				{
					return false;
				}
			}
		}
		return true;
	}

}

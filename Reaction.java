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


}
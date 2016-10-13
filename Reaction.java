import java.util.*;

public class Reaction {

	public String name;
	public boolean viable;
	public boolean chosen;
	public ArrayList<Compound> madeFrom;
	public ArrayList<Compound> madeTo;
	int price;
	public boolean visited = false;
	
	public Reaction(String name, int price) {
		this.name = name;
		this.price = price;
		madeFrom = new ArrayList<Compound>();
		madeTo = new ArrayList<Compound>();
		viable = false;
		chosen = false;
	}
	
	public Reaction(int price, ArrayList<Compound> in, ArrayList<Compound> out) {
		this.price = price;
		madeFrom = in;
		madeTo = out;
		viable = false;
		chosen = false;
	}

	public String toString() {
		String ret = "Reaction " + name + " made from...creates\n";
		for (Compound c : madeFrom) {
			ret += c.name + " ";
		}
		ret += "          ...          ";
		for (Compound c : madeTo) {
			ret += c.name + " ";
		}
		return ret;
	}
}

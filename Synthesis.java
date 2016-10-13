import java.util.*;

class Synthesis
{
	public static void main(String[] args) 
	{
		int numCompounds = 10;
      	int numReactions = 10;
      	Graph g = new Graph(1, numCompounds, numReactions);
      	System.out.println(g.toString());
		ArrayList<Compound> targets = new ArrayList<Compound>();
		System.out.println("Targets are " + g.compounds.get(4).name +
			" " + g.compounds.get(8).name);
		g.compounds.get(9).substrate = true;
		g.compounds.get(7).substrate = true;
		for (Compound c : g.compounds) {
			System.out.println(c + "is " + (c.substrate? "" : "not ") + "a substrate");
		}
		targets.add(g.compounds.get(3));
		Plan p = new Plan(g.compounds, g.reactions, targets);
		p.generateInitialPlan(2);
		System.out.println(p.toString());
		System.out.println(p.isViable());
		System.out.println(p.deleteCompound(g.compounds.get(4)));
		System.out.println(p.deleteCompound(g.compounds.get(2)));
	}
}

import java.util.*;

class Synthesis
{
	public static void main(String[] args) 
	{
		int numCompounds = 30;
      	int numReactions = 90;
      	Graph g = new Graph(1, numCompounds, numReactions);
      	System.out.println(g.toString());
		ArrayList<Compound> targets = new ArrayList<Compound>();
		targets.add(g.compounds.get(3));
		targets.add(g.compounds.get(6));
		Plan p = new Plan(1, g.compounds, g.reactions, targets);
		p.generateInitialPlan(4);
		System.out.println(p.toString());
		if (!p.isViable()) {
			System.out.println("Could not locate viable plan from initial graph");
			return;
		}
		while (p.beta() < 1) {
			p.anneal();
		}
		
		System.out.println(p.toString());
	}
}

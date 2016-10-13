import java.util.*;

class Synthesis
{
	public static void main(String[] args) 
	{
		int numCompounds = 30;
      	int numReactions = 90;
		int seed = 1;
		ArrayList<Integer> targetIndices = new ArrayList<Integer>();
		
		//ensure user input is properly formatted
		if (args.length > 3) {
			try {
				numCompounds = Integer.parseInt(args[0]);
				numReactions = Integer.parseInt(args[1]);
				seed = Integer.parseInt(args[2]);
				for (int i = 3; i < args.length; i++) {
					targetIndices.add(Integer.parseInt(args[i]));
				}
			}
			catch (Exception e) {
				System.out.println("Usage: java Synthesis <numCompounds> <numReactions> <seed>");
				return;
			}
		}
		else {
			System.out.println("Usage: java Synthesis <numCompounds> <numReactions> <seed> <target1> <target2> ...");
			return;
		}
		if (numCompounds < 10 || numReactions < 20) {
			System.out.println("Compounds must be at least 10 and " +
			"reactions must be at least 20");
		}
		
      	Graph g = new Graph(1, numCompounds, numReactions);
		//print initial graph properties, for debug purposes like manually
		//solving a problem
      	System.out.println(g.toString());
		ArrayList<Compound> targets = new ArrayList<Compound>();
		//add all the user requested targets in
		for (Integer i : targetIndices) {
			targets.add(g.compounds.get(i));
		}
		
		Plan p = new Plan(1, g.compounds, g.reactions, targets);
		
		//generate initial full tree to depth depending on number of compounds
		p.generateInitialPlan((int)(Math.log(g.compounds.size())));
		System.out.println(p.toString());
		if (!p.isViable()) {
			//if not viable to begin with, notify the user and quit
			System.out.println("Could not locate viable plan from initial graph");
			return;
		}
		while (p.beta() < 1) {
			//while the temperature permits, anneal
			p.anneal();
		}
		
		//print final result
		System.out.println(p.toString());
	}
}

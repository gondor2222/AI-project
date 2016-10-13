import java.util.*;

class Synthesis
{
	public static void main(String[] args) 
	{
		int numCompounds = 10;
      		int numReactions = 20;
      		Graph g = new Graph(numCompounds, numReactions);
      		g.generateScaleFree();
      		//System.out.println(g.toString());
		Plan p = new Plan();
		ArrayList<Compound> targets = new ArrayList<Compound>();
		targets.add(g.compounds.get(5));
		p.generateInitialPlan(targets, 1);
		System.out.println(p.toString());
		System.out.println(p.isValid());
		p.deleteReaction(g.compounds.get(5).madeFrom.get(0));
		System.out.println(p.isValid());
	}
}

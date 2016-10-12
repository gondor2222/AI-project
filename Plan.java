import java.util.*;

class Plan
{
	private ArrayList<TreeSet<Integer>> compounds;
	private ArrayList<TreeSet<Integer>> reactions;
	private Graph g;
	
	public Plan(ArrayList<TreeSet<Integer>> compounds, ArrayList<TreeSet<Integer>> reactions, Graph g)
	{
		this.compounds = compounds;
		this.reactions = reactions;
		this.g = g;
	}

	public boolean isValid()
	{
		
	}
	
}

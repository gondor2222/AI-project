import java.util.*;

class Plan
{
	private ArrayList<Compound> compounds;
	private ArrayList<Reaction> reactions;
	private Graph g;
	
	public Plan(ArrayList<TreeSet<Integer>> compounds, ArrayList<TreeSet<Integer>> reactions, Graph g)
	{
		/*this.compounds = compounds;
		this.reactions = reactions;
		this.g = g;*/
	}

	public boolean isValid(ArrayList<Compound> targets)
	{
		int length = compounds.size();
		LinkedList<Compound> list = new LinkedList<Compound>();
		Compound c;
		Reaction r;
		ArrayList<Reaction> reactions;
		for(int i = 0; i < length; i++)
		{
			c = compounds.get(i);
			c.set_makeable(true);
			list.add(c);
		}
		while(list.size() > 0)
		{
			c = list.poll();
			reactions = c.madeFrom;
			length = reactions.size();
			for(int i = 0; i < length; i++)
			{
				r = reactions.get(i);
				if(r.isViable() && r.isMakeable(compounds))
				{
					r.set_viable(true);
					for(Compound com : r.madeTo)
					{
						com.set_makeable(true);
						list.add(com);
					}
				}
			}
		}
		for(Compound com : targets)
		{
			if(!com.isMakeable())
			{
				return false;
			}
		}
		return true;
	}
	
}

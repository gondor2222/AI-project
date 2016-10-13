import java.util.*;

class Plan
{
	private ArrayList<Compound> compounds;
	private ArrayList<Reaction> reactions;
	public ArrayList<Compound> targets;
	
	public Plan(ArrayList<Compound> compounds, ArrayList<Reaction> reactions, ArrayList<Compound> targets)
	{
		this.compounds = compounds;
		this.reactions = reactions;
		this.targets = targets;
	}

	public Plan(){}

	public boolean isValid()
	{
		for(Compound c : compounds)
		{
			c.makeable = false;
		}
		for(Reaction r : reactions)
		{
			r.viable = false;
		}
		int length = compounds.size();
		LinkedList<Compound> list = new LinkedList<Compound>();
		Compound c;
		Reaction r;
		ArrayList<Reaction> reactions;
		for(int i = 0; i < length; i++)
		{
			c = compounds.get(i);
			c.makeable = true;
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
				if(!r.viable && r.isMakeable(compounds))
				{
					r.viable = true;
					for(Compound com : r.madeTo)
					{
						if(!com.makeable)
						{
							com.makeable = true;
							list.add(com);
						}
					}
				}
			}
		}
		for(Compound com : targets)
		{
			if(!com.makeable)
			{
				return false;
			}
		}
		return true;
	}

	public void generateInitialPlan(ArrayList<Compound> targets, int depth)
	{
		this.targets = targets;
		if(depth == 0)
		{
			return;
		}
		for(Compound c : targets)
		{
			c.makeable = true;
		}
		compounds = new ArrayList<Compound>();
		reactions = new ArrayList<Reaction>();
		for(Compound c : targets)
		{
			c.visited = true;
			for(Reaction r : c.madeFrom)
			{
				if(!r.visited)
				{
					r.visited = true;
					reactions.add(r);
					DFS_Reaction(r, compounds, reactions, depth);
				}
			}
		}
	}

	private void DFS_Reaction(Reaction r, ArrayList<Compound> compounds, ArrayList<Reaction> reactions, int depth)
	{
		for(Compound c : r.madeFrom)
		{
			if(!c.visited)
			{
				c.visited = true;
				compounds.add(c);
				DFS_Compound(c, compounds, reactions, depth-1);
			}
		}
	}

	private void DFS_Compound(Compound c, ArrayList<Compound> compounds, ArrayList<Reaction> reactions, int depth)
	{
		if(depth == 0)
		{
			return;
		}
		for(Reaction r : c.madeFrom)
		{
			if(!r.visited)
			{
				r.visited = true;
				reactions.add(r);
				DFS_Reaction(r, compounds, reactions, depth-1);
			}
		}
	}

	public void deleteCompound(Compound c)
	{
		if(!compounds.contains(c))
		{
			return;
		}
		for(Compound com : compounds)
		{
			if(com.name == c.name)
			{
				compounds.remove(com);
				com.makeable = false;
				break;
			}
		}
		for(Reaction r : c.madeTo)
		{
			r.viable = false;
		}
		for(Reaction r : c.madeFrom)
		{
			deleteReaction(r);
		}
	}
	
	public void deleteReaction(Reaction r)
	{
		if(!reactions.contains(r))
		{
			return;
		}
		for(Reaction rea : reactions)
		{
			if(rea.name == r.name)
			{
				reactions.remove(rea);
				rea.viable = false;
				break;
			}
		}
		for(Compound c : r.madeTo)
		{
			c.makeable = false;
		}
		for(Compound c : r.madeFrom)
		{
			deleteCompound(c);
		}
	}

	public String toString()
	{
		String ans = "This plan used following compounds:\n";
		for(Compound c : compounds)
		{
			ans = ans + c.name + " ";
		}
		ans = ans + "\n" + "And following reactions:\n";
		for(Reaction r : reactions)
		{
			ans = ans + r.name + " ";
		}
		return ans;
	}
	
}

















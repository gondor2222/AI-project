import java.util.*;

class Plan
{
	private ArrayList<Compound> compounds;
	private ArrayList<Reaction> reactions;
	public ArrayList<Compound> targets;
	public HashMap<Compound, Boolean> visitedC;
	public HashMap<Reaction, Boolean> visitedR;
	
	public Plan(ArrayList<Compound> compounds, ArrayList<Reaction> reactions, ArrayList<Compound> targets)
	{
		this.compounds = compounds;
		this.reactions = reactions;
		this.targets = targets;
		visitedC = new HashMap<Compound, Boolean>();
		visitedR = new HashMap<Reaction, Boolean>();
	}

	public void setTarget(ArrayList<Compound> targets) {
		this.targets = targets;
	}

	public boolean isViable()
	{
		visitedC = new HashMap<Compound, Boolean>();
		visitedR = new HashMap<Reaction, Boolean>();
		boolean ret = true;
		for(Compound c : targets)
		{
			ret &= checkPlan(c);
		}
		return ret;
	}

	public void generateInitialPlan(int depth) {
		for (Compound c : targets) {
			generateChosen(c, depth + 1);
		}
	}
	
	
	
	public void generateChosen(Compound c, int depth) {
		c.chosen = true;
		for (Reaction r : c.madeFrom) {
			generateChosen(r, depth-1);
		}
	}
	
	public void generateChosen(Reaction r, int depth) {
		if (depth == 0) {
			return;
		}
		r.chosen = true;
		for (Compound c : r.madeFrom) {
			generateChosen(c, depth);
		}
	}
	
	public boolean checkPlan(Compound c) {
		if (c.substrate && c.chosen) {
			visitedC.put(c, true);
			return true;
		}
		if(!c.chosen)
		{
			visitedC.put(c, false);
			return false;
		}
		for (Reaction r : c.madeFrom) {
			if (visitedR.containsKey(r)) {
				visitedC.put(c, visitedR.get(r));
				continue;
			}
			boolean makeable = checkPlan(r);
			if (makeable) {
				visitedC.put(c, true);
				return true;
			}
		}
		visitedC.put(c, false);
		return false;
	}
	
	public boolean checkPlan(Reaction r) {
		if(!r.chosen)
		{
			visitedR.put(r, false);
			return false;
		}
		for (Compound c : r.madeFrom) {
			boolean viable = false;
			if (visitedC.containsKey(c)) {
				viable = visitedC.get(c);
			}
			else {
				viable = checkPlan(c);
			}
			if (!viable) {
				visitedR.put(r, false);
				return false;
			}
		}
		visitedR.put(r, true);
		return true;
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

	public boolean deleteCompound(Compound c)
	{
		if(!compounds.contains(c))
		{
			return false;
		}
		for(Compound com : compounds)
		{
			if(com.name.equals(c.name) && c.chosen)
			{
				com.chosen = false;
				if (!isViable()) {
					System.out.println("Attempted to remove " + c.name + " but result was unviable");
					com.chosen = true;
				}
				return !com.chosen;
			}
		}
		return false;
	}
	
	public boolean deleteReaction(Reaction r)
	{
		if(!reactions.contains(r))
		{
			return false;
		}
		for(Reaction rea : reactions)
		{
			if(rea.name.equals(r.name) && r.viable)
			{
				rea.viable = false;
				if (!isViable()) {
					rea.viable = true;
				}
				return !rea.viable;
			}
		}
		return false;
	}

	public String toString()
	{
		String ans = "This plan used following compounds:\n";
		for(Compound c : compounds)
		{
			if (c.chosen) {
				ans = ans + c.name + " ";
			}
		}
		ans = ans + "\n" + "And following reactions:\n";
		for(Reaction r : reactions)
		{
			if (r.chosen) {
				ans = ans + r.name + " ";
			}
		}
		return ans;
	}
	
}

















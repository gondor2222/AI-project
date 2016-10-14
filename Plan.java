import java.util.*;

class Plan
{
	private ArrayList<Compound> compounds;
	private ArrayList<Reaction> reactions;
	public ArrayList<Compound> targets;
	//visited hashmaps used to avoid cycles when checking if a graph is viable
	public HashMap<Compound, Boolean> visitedC;
	public HashMap<Reaction, Boolean> visitedR;
	private static final double B_i = 0.1;
	private int kMax;
	private int N;
	private int numMoves;
	private Random rand;
	
	public double beta() {
		//see the paralellization paper for details on what these values are.
		//beta refers to a sort of general inverse temperature, N to the maximum
		//allowed moves, numMoves number of moves made so far, kMax to granularity of
		//temperature, B_i is initial temperature.
		int k = numMoves * (kMax + 1) / (N + 1);
		return B_i * Math.pow(B_i, -k/kMax);
	}
	public Plan(int seed, ArrayList<Compound> compounds, ArrayList<Reaction> reactions, ArrayList<Compound> targets)
	{
		this.compounds = compounds;
		kMax = 10 * compounds.size();
		N = 30 * compounds.size();
		this.reactions = reactions;
		this.targets = targets;
		visitedC = new HashMap<Compound, Boolean>();
		visitedR = new HashMap<Reaction, Boolean>();
		numMoves = 0;
		rand = new Random(seed);
	}

	//count number of original substrates (ones that were generated at start)
	public int numSubstrates() {
		int ret = 0;
		for (Compound c : compounds) {
			if (c.originalSubstrate) {
				ret++;
			}
		}
		return ret;
	}
	
	//number of substrates currently in the plan
	public int numChosenSubstrates() {
		int ret = 0;
		for (Compound c : compounds) {
			if (c.substrate && c.chosen) {
				ret++;
			}
		}
		return ret;
	}
	
	//number of reactions currently in the plan
	public int numChosenR() {
		int ret = 0;
		for (Reaction r : reactions) {
			if (r.chosen) {
				ret++;
			}
		}
		return ret;
	}
	
	//number of reactions not in the plan, but viable using the plan
	public int numPossibleR() {
		int ret = 0;
		for (Reaction r : reactions) {
			if (!r.chosen && checkPlan(r)) {
				r.viable = true;
				ret++;
			}
		}
		return ret;
	}
	
	public void setTarget(ArrayList<Compound> targets) {
		this.targets = targets;
	}

	//check if an entire plan is viable
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

	//initializes targets recursively to create the initial plan
	public void generateInitialPlan(int depth) {
		visitedC = new HashMap<Compound, Boolean>();
		visitedR = new HashMap<Reaction, Boolean>();
		for(Compound c : targets)
		{
			visitedC.put(c, true);
		}
		for (Compound c : targets) {
			generateChosen(c, depth + 1);
		}
	}
	
	
	//recursive call for the above function for compounds
	public void generateChosen(Compound c, int depth) {
		c.chosen = true;
		for (Reaction r : c.madeFrom) {
			if(!visitedR.containsKey(r))
			{
				visitedR.put(r, true);
				generateChosen(r, depth-1);
			}
		}
	}
	
	//same as above function but for reactions
	public void generateChosen(Reaction r, int depth) {
		if (depth == 0) {
			return;
		}
		r.chosen = true;
		for (Compound c : r.madeFrom) {
			if(!visitedC.containsKey(c))
			{
				visitedC.put(c, true);
				generateChosen(c, depth);
			}
		}
	}
	
	//checks if a compound is satisfiable in the plan
	public boolean checkPlan(Compound c) {
		//if it's a substrate and chosen, we can use it
		if (c.substrate && c.chosen) {
			visitedC.put(c, true);
			return true;
		}
		//if not in the plan at all, can't use it
		if(!c.chosen)
		{
			visitedC.put(c, false);
			return false;
		}
		visitedC.put(c, false);
		//if any reaction is viable to produce this, we can use this compound
		for (Reaction r : c.madeFrom) {
			boolean makeable = false;
			if (visitedR.containsKey(r)) {
				makeable |= visitedR.get(r);
			}
			else {
				makeable = checkPlan(r);
			}
			if (makeable) {
				visitedC.put(c, true);
				c.makeable = true;
				return true;
			}
		}
		//else we cannot
		visitedC.put(c, false);
		c.makeable = false;
		return false;
	}
	
	public boolean checkPlan(Reaction r) {
		//if not in the plan at all, not viable
		if(!r.chosen)
		{
			visitedR.put(r, false);
			return false;
		}
		visitedR.put(r, false);
		//if any compound required is not available, we cannot perform it
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
				r.viable = false;
				return false;
			}
		}
		//else we can perform it
		visitedR.put(r, true);
		r.viable = true;
		return true;
	}

	//main annealing function
	public void anneal() {
		int N_chos = numChosenR();
		int N_pos = numPossibleR();
		//list of reactions we might remove: equal to all possible or chosen reactions
		ArrayList<Reaction> removal = new ArrayList<Reaction>();
		for (Reaction r : reactions) {
			if (r.viable || r.chosen) {
				removal.add(r);
			}
		}
		//pick a reaction to move and increment move count
		Reaction r = removal.get(rand.nextInt(removal.size()));
		numMoves++;
		//System.out.println(numMoves);
		if (r.chosen) {
			deleteReaction(r);
		}
		//if reaction wasn't in plan before and temperature permits, enable it
		else if (rand.nextDouble() < N_chos/(N_chos + N_pos) * Math.exp(-beta()*10)) {
			r.chosen = true;
		}
		int N_a = numSubstrates();
		int N_sub = numChosenSubstrates();
		ArrayList<Compound> removal2 = new ArrayList<Compound>();
		for (Compound c : compounds) {
			if (c.originalSubstrate) {
				removal2.add(c);
			}
		}
		//select a compound to move
		Compound c = removal2.get(rand.nextInt(removal2.size()));
		if (c.substrate) {
			deleteCompound(c);
		}
		//if this substrate was not chosen for use before and temperature permits,
		//readd it to the plan.
		else if (rand.nextDouble() < N_sub / (N_a - N_sub + 1) * Math.exp(-beta()*c.price)) {
			c.substrate = true;
		}
	}
	
	//attempt to set a compound as no longer a substrate.
	//returns whether the compound was successfully disabled (and the plan still works)
	public boolean deleteCompound(Compound c)
	{
		if(!compounds.contains(c))
		{
			return false;
		}
		for(Compound com : compounds)
		{
			if(com.name.equals(c.name) && c.substrate)
			{
				com.substrate = false;
				if (!isViable()) {
					com.substrate = true;
				}
				return !com.substrate;
			}
		}
		return false;
	}
	
	//attempts to delete a reaction from the plan. returns whether this was a success
	//is successful if the plan is still viable after deletion.
	public boolean deleteReaction(Reaction r)
	{
		if(!reactions.contains(r))
		{
			return false;
		}
		for(Reaction rea : reactions)
		{
			if(rea.name.equals(r.name) && r.chosen)
			{
				rea.chosen = false;
				if (!isViable()) {
					rea.chosen = true;
				}
				return !rea.chosen;
			}
		}
		return false;
	}

	//prints out the plan
	public String toString()
	{
		String ans = "This plan used following compounds:\n";
		for(Compound c : compounds)
		{
			if (c.chosen && c.substrate) {
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

















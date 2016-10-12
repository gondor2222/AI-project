import java.util.*;

class Graph
{
   private static final int maxProducts = 2;
   private ArrayList<Compound> compounds;
   private ArrayList<Reaction> reactions;
   private Random rand;

   public Graph(int numCompounds, int numReactions)
   {
      rand = new Random();
      compounds = new ArrayList<Compound>();
      reactions = new ArrayList<Reaction>();
      for (int i = 0; i < numCompounds; i++) {
         compounds.add(new Compound(i + ""));
      }
	  for (int i = 0; i < numReactions; i++) {
         reactions.add(new Reaction("R" + i, 1));
      }
   }

   public void generateScaleFree() {
      int randInt;
      for (int i = 0; i < reactions.size(); i++) {
		 Reaction r = reactions.get(i);
         int numInputs = rand.nextInt(2) + 2;
         boolean usesReagant = (numInputs == 3);
         int numOutputs = rand.nextInt(maxProducts) + 1;
         Compound reagant = new Compound("");
         while (numInputs != 0) {
            Compound input = compounds.get(rand.nextInt(compounds.size()));
            if (!input.madeTo.contains(r)) {
               reagant = input;
			   input.madeTo.add(r);
			   r.madeFrom.add(input);
               numInputs--;
            }
         }
         if (usesReagant) {
            r.madeTo.add(reagant);
         }
         while (numOutputs != 0) {
            Compound output = compounds.get(rand.nextInt(compounds.size()));
            if (!output.madeTo.contains(r)) {
               r.madeTo.add(output);
			   output.madeFrom.add(r);
               numOutputs--;
            }
         }
      }
   }

   public String toString() {
      String ret = compounds.size() + " compounds, " + reactions.size() + " reactions\n";
	  for (Compound c : compounds) {
		  ret += c.toString() + "\n";		  
	  }
	  for (Reaction r : reactions) {
		  ret += r.toString() + "\n";
	  }
      return ret;
   }

   public static void main(String[] args)
   {
      int numCompounds = 10;
      int numReactions = 20;
      Graph g = new Graph(numCompounds, numReactions);
      g.generateScaleFree();
      System.out.println(g.toString());
   }
}


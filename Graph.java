import java.util.*;

class Graph
{
   private int numCompounds;
   private int numReactions;
   private static final int maxProducts = 2;
   private ArrayList<TreeSet<Integer>> compounds;
   private ArrayList<TreeSet<Integer>> reactions;
   private Random rand;

   public Graph(int numCompounds, int numReactions)
   {
      this.numCompounds = numCompounds;
      this.numReactions = numReactions;
      rand = new Random();
      compounds = new ArrayList<TreeSet<Integer>>();
      reactions = new ArrayList<TreeSet<Integer>>();
      for (int i = 0; i < numCompounds;i++) {
         compounds.add(new TreeSet<Integer>());
      }
      for (int i = 0; i < numReactions; i++) {
         reactions.add(new TreeSet<Integer>());
      }
   }

   public void generateScaleFree() {
      int randInt;
      for (int i = 0; i < numReactions; i++) {
         int numInputs = rand.nextInt(2) + 2;
         boolean usesReagant = (numInputs == 3);
         int numOutputs = rand.nextInt(maxProducts) + 1;
         int reagant = 0;
         while (numInputs != 0) {
            int input = rand.nextInt(numCompounds);
            if (!compounds.get(input).contains(i)) {
               reagant = input;
               compounds.get(input).add(i);
               numInputs--;
            }
         }
         if (usesReagant) {
            reactions.get(i).add(reagant);
         }
         while (numOutputs != 0) {
            int output = rand.nextInt(numCompounds);
            if (!compounds.get(output).contains(i)) {
               reactions.get(i).add(output);
               numOutputs--;
            }
         }
      }
   }

   public String toString() {
         String ret = "Compounds:\n";
      for (int i = 0; i < numCompounds;i++) {
         ret = ret + "compound " + i + " participates in reactions " + compounds.get(i) + "\n";
      }
         ret += "Reactions:\n";
      for (int i =0; i < numReactions;i++) {
         ret = ret + "reaction " + i + " produces compounds " + reactions.get(i) + "\n";
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


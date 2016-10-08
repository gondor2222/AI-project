import java.util.*;

class Graph
{
        private int numCompounds;
        private int numReactions;
        private static final int maxProducts = 3;
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
                for (int i = 0; i < numCompounds; i++) {
                        int numEdges = (int)(1/Math.sqrt(rand.nextDouble()));
                        for (int j = 0; j < numEdges && j < numReactions;j++) {
                                compounds.get(i).add(rand.nextInt(numReactions));
                        }
                }
                for (int i = 0; i < numReactions; i++) {
                        int numEdges = rand.nextInt(maxProducts) + 1;
                        for (int j = 0; j < numEdges && j < numCompounds;j++) {
                                reactions.get(i).add(rand.nextInt(numCompounds));
                        }
                }
        }

        public static void main(String[] args)
        {
                Graph g = new Graph(40, 60);
                g.generateScaleFree();
        }
}

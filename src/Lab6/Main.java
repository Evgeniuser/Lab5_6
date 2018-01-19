package Lab6;

import java.security.SecureRandom;

public class Main  {

    public static void main(String[] args) {

        Graph gh = new Graph("Graph.txt");

        Alice alice = new Alice(gh);
        //alice.DisplaySolution();
        Bob bob = new Bob(gh);
        SecureRandom rnd = new SecureRandom();
        for(int i = 0;i <500;i++)
        {
            int k = rnd.nextInt();
            if(k%2==0)
            {
                alice.reset();
                bob.getGraph(alice.SendGraph());
                bob.getGraphKey(alice.SendVerticesCrypt(),alice.SendGraphKey(),alice.AlicePair);
                if(bob.CheckGraph()==-1)
                {
                    System.out.println("Graph,Alice is a liar!");
                    System.exit(-1);
                }

            }

            else
            {
                alice.reset();
                bob.getGraph(alice.SendGraph());
                bob.getPath(alice.SendPath(),alice.SendPathKey(),alice.AlicePair);
                if(bob.CheckPath()==-1)
                {
                    System.out.println("Path,Alice is a liar!");
                    System.exit(-1);
                }
            }
        }

        alice.DisplaySolution();





    }
}

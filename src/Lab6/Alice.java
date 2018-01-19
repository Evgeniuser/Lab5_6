package Lab6;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.TreeMap;


public class Alice extends Crypt{

    private int V, pathCount;
    int[] path;
    int[][] graph;
    Graph gh;
    int size;
    int [] sol;
    private LinkedList<Pair<BigInteger,BigInteger>> KeyPair = new LinkedList<>();
    Pair<BigInteger,BigInteger> AlicePair;
    private LinkedList<Pair<BigInteger,BigInteger>> pathPair = new LinkedList<>();
    private LinkedList<Pair<Integer,Integer>> newGraphList = new LinkedList<>();
    private LinkedList<Pair<Integer,Integer>> GraphList;
    private LinkedList<Pair<Integer,Integer>> GraphPath = new LinkedList<>();
    protected TreeMap<Integer,Integer> vertexCrypt = new TreeMap<>();
    LinkedList<Pair<Integer,Integer>> cryptGraph = new LinkedList<>();
    LinkedList<Pair<Integer,Integer>> cryptPath = new LinkedList<>();

    Alice(Graph gh)
    {
        this.gh = gh;
        GraphList = gh.getGraphList();
        size = this.gh.getGraph().length;
        findHamiltonianCycle(this.gh.getGraph());
        sol = new int[path.length];
        System.arraycopy(path,0,sol,0,path.length);
        CreateNewVertices();
        NewPath();
        CrtPairPath();

        GenKeyPair(newGraphList.size());
        KeyPair = keyPair;
        AlicePair = N_Fi;
        CryptGraph();
        CreatePathKey();
        SwapMap();

    }
    /** Function to find cycle **/
    private void findHamiltonianCycle(int[][] g)
    {
        V = g.length;
        path = new int[V];

        Arrays.fill(path, -1);
        graph = g;
        try
        {
            path[0] = 0;
            pathCount = 1;
            solve(0);
            System.out.println("No solution");
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            //display();
        }
    }
    /** function to find paths recursively **/
    private void solve(int vertex) throws Exception
    {
        /* solution **/
        if (graph[vertex][0] == 1 && pathCount == V)
            throw new Exception("Solution found");
        /* all vertices selected but last vertex not linked to 0 **/
        if (pathCount == V)
            return;

        for (int v = 0; v < V; v++)
        {
            /* if connected **/
            if (graph[vertex][v] == 1 )
            {
                /* add to path **/
                path[pathCount++] = v;
                /* remove connection **/
                graph[vertex][v] = 0;
                graph[v][vertex] = 0;

                /* if vertex not already selected  solve recursively **/
                if (!isPresent(v))
                    solve(v);

                /* restore connection **/
                graph[vertex][v] = 1;
                graph[v][vertex] = 1;
                /* remove path **/
                path[--pathCount] = -1;
            }
        }
    }
    /** function to check if path is already selected **/
    private boolean isPresent(int v)
    {
        for (int i = 0; i < pathCount - 1; i++)
            if (path[i] == v)
                return true;
        return false;
    }

    private void CreateNewVertices()
    {
        int k = 0;
        newGraphList.clear();
        vertexCrypt.clear();
        for(int i = 0;i<size;i++)
        {
            while(k == i || vertexCrypt.containsValue(k))
                k = Integer.parseInt(new BigInteger(5,new SecureRandom()).toString());
            vertexCrypt.put(i,k);
        }

        Pair<Integer,Integer> temp;

        for (Pair<Integer, Integer> aGraphList : GraphList) {

            temp = aGraphList;
            int j = vertexCrypt.get(temp.getFirst());
            int z = vertexCrypt.get(temp.getSecond());

            newGraphList.add(new Pair<>(j, z));
        }

            /*for(int i = 0; i < newGraphList.size(); i++)
                newGraphList.get(i).print();*/


    }

    private void NewPath()
    {
        for(int i = 0;i < path.length;i++)
        {
            path[i] = vertexCrypt.get(i);
        }

    }

    private void CrtPairPath()
    {
        GraphPath.clear();
        LinkedList<Integer> Path = new LinkedList<>();
        for (int i:path)Path.add(i);
        Path.add(Path.get(0));

        for(int i = 0;i<Path.size()-1;i++)
        {
            GraphPath.add(new Pair<>(Path.get(i),Path.get(i+1)));
        }

    }

    TreeMap<Integer, Integer> SendVerticesCrypt() {
        return vertexCrypt;
    }

    LinkedList<Pair<Integer,Integer>> SendPath()
    {
        return cryptPath;
    }

    LinkedList<Pair<Integer,Integer>> SendGraph()
    {
        return cryptGraph;
    }

    LinkedList<Pair<BigInteger,BigInteger>> SendGraphKey()
    {
        return KeyPair;
    }

    LinkedList<Pair<BigInteger,BigInteger>> SendPathKey()
    {
        return pathPair;
    }

    void CryptGraph()
    {
        SetKeyPair(KeyPair);
        cryptGraph.clear();
        cryptPath.clear();
        for(int i = 0; i < newGraphList.size(); i++)
        {
            cryptGraph.add(CryptPair(newGraphList.get(i),i));
            int k = gh.ExtPair(GraphPath,newGraphList.get(i));
            if(k!=-1) cryptPath.add(CryptPair(GraphPath.get(k),i));
        }
    }

    void reset()
    {
        CreateNewVertices();
        NewPath();
        CrtPairPath();
        GenKeyPair(newGraphList.size());
        KeyPair = keyPair;
        AlicePair = N_Fi;
        CryptGraph();
        CreatePathKey();
        SwapMap();
    }

    void CreatePathKey()
    {

        pathPair.clear();
        for (Pair<Integer, Integer> aGraphPath : cryptPath) {
            int k = gh.ExtPair(cryptGraph, aGraphPath);
            if (k != -1) {
                pathPair.add(KeyPair.get(k));
                //System.out.println(k);
            }
        }
    }

    void SwapMap()
    {
        TreeMap<Integer,Integer> temp = new TreeMap<>();
        while(!vertexCrypt.isEmpty())
        {
            int k = vertexCrypt.firstEntry().getKey();
            temp.put(vertexCrypt.get(k),k);
            vertexCrypt.remove(k);
        }
        vertexCrypt.clear();
        vertexCrypt.putAll(temp);
    }

    void test()
    {

        LinkedList<Pair<Integer,Integer>> tr = gh.InvGraph(newGraphList, vertexCrypt);
        System.out.println(gh.CompGraph(tr,GraphList));
    }

    void Display(LinkedList<Pair<Integer, Integer>> graph)
    {
        for(int i = 0; i < graph.size();i++)
        {
            graph.get(i).print();
        }
    }

    void DisplaySolution()
    {
        for(int i = 0;i<sol.length;i++)
        {
            System.out.print(sol[i] + " ");
        }
        System.out.print(sol[0]);
    }

}

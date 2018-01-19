package Lab6;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.Objects;
import java.util.TreeMap;

public class Bob extends Crypt{

    private LinkedList<Pair<BigInteger,BigInteger>> KeyPair = new LinkedList<>();
    private LinkedList<Pair<BigInteger,BigInteger>> pathPair = new LinkedList<>();
    private LinkedList<Pair<Integer,Integer>> graphList = new LinkedList<>();
    private LinkedList<Pair<Integer,Integer>> srcGh = new LinkedList<>();
    private LinkedList<Pair<Integer,Integer>> GraphPath = new LinkedList<>();
    private TreeMap<Integer,Integer> verticesCrypt = new TreeMap<>();
    private LinkedList<Pair<Integer,Integer>> cryptGraph = new LinkedList<>();
    private LinkedList<Pair<Integer,Integer>> cryptPath = new LinkedList<>();
    private Pair<BigInteger, BigInteger> n_Fi;

    Graph gh;

    Bob(Graph gh)
    {
        this.gh = gh;
    }

    int CheckPath()
    {
        LinkedList<Pair<Integer,Integer>> Temp = new LinkedList<>();

        for (Pair<Integer, Integer> aCryptPath : cryptPath) {
            int k = gh.ExtPair(cryptGraph, aCryptPath);
            if (k == -1) {
                System.out.println("Alice is a liar!");
                return -1;
            }

            Temp.add(cryptGraph.get(k));
        }

        DecryptGraphOrPath(Temp,Temp,pathPair);
        DecryptGraphOrPath(cryptPath,GraphPath,pathPair);
        if(CryptCheck(pathPair,GraphPath)==-1)
            return -1;
        if(gh.CompGraph(Temp,GraphPath)) {
            if (Objects.equals(GraphPath.get(0).getFirst(), GraphPath.getLast().getSecond())) {
                for (int i = 1; i < GraphPath.size() - 1; i++) {
                    if (!Objects.equals(GraphPath.get(i).getSecond(), GraphPath.get(i + 1).getFirst())) {
                        System.out.println("1It`s not cycle! Alice is a liar!");
                        return -1;
                    }
                }
            } else {
                System.out.println("2It`s not cycle! Alice is a liar!");
                return -1;
            }
        }
        else
        {
            System.out.println("Alice is a liar!");
            return -1;
        }
        System.out.println("All Ok, it`s cycle");
        GraphPath.clear();
        cryptPath.clear();
        return 1;

    }

    int CheckGraph()
    {
        DecryptGraphOrPath(cryptGraph,graphList,KeyPair);
        CryptCheck(KeyPair,graphList);
        srcGh.clear();
        srcGh.addAll(gh.InvGraph(graphList, verticesCrypt));
        if(gh.CompGraph(srcGh,gh.getGraphList()))
        {
            System.out.println("Graphs are isomorphic");
            return 1;
        }

        else
        {
            System.out.println("1Alice is a liar!");
            return -1;
        }
    }

    private void DecryptGraphOrPath(LinkedList<Pair<Integer,Integer>> CryptGraph, LinkedList<Pair<Integer,Integer>> Decrypt, LinkedList<Pair<BigInteger, BigInteger>> KeyPair)
    {
        SetKeyPair(KeyPair);
        SetN_Fi(n_Fi);
        LinkedList<Pair<Integer,Integer>> Temp = new LinkedList<>();

        for(int i = 0;i<CryptGraph.size();i++)
        {
            Temp.add(DecryptPair(CryptGraph.get(i),i));
        }

        Decrypt.clear();
        Decrypt.addAll(Temp);
    }

    private Pair SwapPair(Pair pair)
    {
        Object t;
        t = pair.getFirst();
        return pair = new Pair(pair.getSecond(),t);
    }

    private int CryptCheck(LinkedList<Pair<BigInteger, BigInteger>>  key, LinkedList<Pair<Integer,Integer>> testList)
    {
        for(int i = 0; i < key.size();i++)
        {
            key.set(i,SwapPair(key.get(i)));
        }

        LinkedList<Pair<Integer,Integer>> temp = new LinkedList<>();
        temp.addAll(testList);
        DecryptGraphOrPath(temp,temp,key);

        for(int i = 0; i < key.size();i++)
        {
            key.set(i,SwapPair(key.get(i)));
        }
        DecryptGraphOrPath(temp,temp,key);
        if(gh.CompGraph(temp,testList))
        {
            //System.out.println("Keys pair are ok");
        }
        else
            return -1;
        return 1;

    }

    void getGraphKey(TreeMap<Integer,Integer> VerticesCrypt, LinkedList<Pair<BigInteger, BigInteger>> Key, Pair<BigInteger, BigInteger> N_Fi)
    {
        this.verticesCrypt = VerticesCrypt;
        KeyPair = Key;
        n_Fi = N_Fi;
    }

    void getGraph(LinkedList<Pair<Integer, Integer>> CryptGraph)
    {
        cryptGraph =CryptGraph;

    }

    void getPath(LinkedList<Pair<Integer, Integer>> CryptPath, LinkedList<Pair<BigInteger, BigInteger>> Key, Pair<BigInteger, BigInteger> N_Fi)
    {
        cryptPath=CryptPath;
        pathPair.clear();
        pathPair.addAll(Key);
        n_Fi = N_Fi;
    }



}

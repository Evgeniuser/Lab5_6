package Lab6;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.lang.*;
import java.util.TreeMap;

public class Graph {

        private int[][] graph;
        private int size;
        private LinkedList<Pair<Integer,Integer>> GraphList = new LinkedList<>();

        private static int currentIndex = -1;

        private static Integer next(String numbers[]) {
            ++currentIndex;
            while (currentIndex < numbers.length
                    && numbers[currentIndex].equals(""))
                ++currentIndex;
            return currentIndex < numbers.length ? Integer
                    .parseInt(numbers[currentIndex]) : null;
        }

        Graph(String Filename) {


            FileInputStream inFile = null;
            try {
                inFile = new FileInputStream(Filename);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            try {
            byte[] str;
            str = new byte[inFile.available()];
            inFile.read(str);

            String text = new String(str);

                String[] numbers = text.split("\\D");
                int i, j;
                int n = next(numbers);
                size = n;
                graph = new int[n][n];

                for (i = 0; i < n; ++i)
                    for (j = 0; j < n; ++j)
                        graph[i][j] = next(numbers);



            }
            catch (IOException e) {
                e.printStackTrace();
            }
            convertToList();
        }

        int[][] getGraph() {
            return graph;
        }

        private void convertToList()
        {
            for(int i = 0; i != size; i++)
            {
                for(int j = 0; j != size; j++)
                {
                    if(graph[i][j] == 1)
                        GraphList.add(new Pair<>(i,j));
                }
            }
        }

        void Display()
        {
        for(int i = 0; i < GraphList.size();i++)
        {
            GraphList.get(i).print();
        }
        }

        boolean CompGraph(LinkedList<Pair<Integer,Integer>> Fst, LinkedList<Pair<Integer,Integer>> Scd)
        {
            if(Fst.size()!=Scd.size())
                return false;
            for(int i = 0; i<Fst.size();i++)
            {
                if(!Fst.get(i).equals(Scd.get(i)))
                    return false;
            }
            return true;
        }

        int ExtPair(LinkedList<Pair<Integer,Integer>> First,Pair<Integer,Integer> pair)
        {
            for(int i = 0; i<First.size();i++)
            {
                if(First.get(i).equals(pair))
                    return i;
            }
            return -1;
        }

        LinkedList InvGraph(LinkedList<Pair<Integer,Integer>> newGraph,TreeMap<Integer,Integer> injKey)
        {
            LinkedList<Pair<Integer,Integer>> chkGraph = new LinkedList<>();
            for (Pair<Integer, Integer> aNewGraph : newGraph)
                chkGraph.add(new Pair<>(injKey.get(aNewGraph.getFirst()), injKey.get(aNewGraph.getSecond())));
            return chkGraph;
        }

        LinkedList<Pair<Integer, Integer>> getGraphList() {
        return GraphList;
    }
}

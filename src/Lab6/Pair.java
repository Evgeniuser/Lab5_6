package Lab6;

public class Pair <T,E> {

    T first;
    E second;

    public Pair(T first, E second) {
        this.first = first;
        this.second = second;
    }

    public void add(T first, E second)
    {
        this.first = first;
        this.second = second;
    }

    E getSecond() {
        return second;
    }

    T getFirst() {
        return first;
    }

    void print()
    {
        System.out.println(first + " " + second);
    }


    boolean equals(Pair Z) {
        return (Z.getFirst().equals(first) && Z.getSecond().equals(second));

    }

}

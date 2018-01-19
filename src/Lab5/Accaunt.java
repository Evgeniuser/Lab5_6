package Lab5;



import java.math.BigInteger;

public class Accaunt {

    public String name;
    public int numb;
    public BigInteger Money;

    Accaunt(int Numb,String name)
    {
        this.numb = Numb;
        this.name = name;
        Money = BigInteger.ZERO;
    }

    public BigInteger AddMoney(BigInteger adM)
    {
        Money = Money.add (adM);
        return Money;
    }

    public BigInteger SubMoney(BigInteger gtM)
    {
        Money = Money.subtract (gtM);
        if(Money.compareTo (BigInteger.ZERO)<0)
            return BigInteger.ZERO;
        else
            return Money;
    }

    public BigInteger getMoney() {
        return Money;
    }
}

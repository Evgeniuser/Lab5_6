package Lab5;

import java.math.BigInteger;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;


import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;
import static java.math.BigInteger.probablePrime;

public class Bank {


    private BigInteger Fi;
    private BigInteger Q;
    private BigInteger P;
    private SecureRandom rnd = new SecureRandom();
    public BigInteger N;
    public String name;

    LinkedList<BigInteger> Money = new LinkedList<>();
    HashMap<Integer,RSA> KeyPair = new HashMap<>();
    TreeMap<Integer,Accaunt> accaunts = new TreeMap<>();

    public Bank(String name, int sizeKey)
    {
        this.name = name;
        Q = new BigInteger(String.valueOf(probablePrime(sizeKey , rnd)));
        P = new BigInteger(String.valueOf(probablePrime(sizeKey , rnd)));

        while(true)
        {
            if(Q.compareTo(P)==0)
            {
                Q = new BigInteger(String.valueOf(probablePrime(sizeKey , rnd)));
                P = new BigInteger(String.valueOf(probablePrime(sizeKey , rnd)));
            }
            break;
        }

        N = Q.multiply(P);
        Fi = P.subtract(ONE).multiply(Q.subtract(ONE));

        GenPair ();

    }

    private void GenPair()
    {
        KeyPair.put (100,new RSA(rnd,Fi));
        KeyPair.put (500,new RSA(rnd,Fi));
        KeyPair.put (1000,new RSA(rnd,Fi));
        KeyPair.put (5000,new RSA(rnd,Fi));
    }

    public BigInteger getD(int Nominal) {
        return KeyPair.get(Nominal).getD();
    }

    private BigInteger getC(int Nominal)
    {
        return KeyPair.get (Nominal).getC();
    }

    public BigInteger CreateSign(BigInteger H,int Nominal)
    {
        return H.modPow(getC(Nominal),N);
    }

    public int CheckMoney(Money money) throws Exception {

        BigInteger S = money.getSign();
        int Nominal = money.getNominal();
        BigInteger M = money.getNumber();
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

        BigInteger hash = new BigInteger(1,messageDigest.digest(M.toByteArray()));
        BigInteger temp = S.modPow(getD(Nominal),N);


        if(hash.compareTo(temp)==0)
        {
            Money.add(M);
            return 0;
        }

        return -2;
    }

    public int CreateAcc(String name,BigInteger money)
    {
        int number = rnd.nextInt ();
        accaunts.put (number,new Accaunt (rnd.nextInt (),name));
        if(money.compareTo (ZERO)==0)
            return number;
        else
        {
            accaunts.get (number).AddMoney (money);
            return number;
        }
    }

    public BigInteger ChkMoney(int numbA)
    {
        if(accaunts.containsKey (numbA))
            return accaunts.get (numbA).getMoney ();
        else
            return new BigInteger (Integer.toString (0));
    }

    public BigInteger AddMoney(int numbA,BigInteger money) {
        if(accaunts.containsKey (numbA))
        {
            accaunts.get (numbA).AddMoney (money);
            return accaunts.get (numbA).getMoney ();
        }
        else
            return new BigInteger (Integer.toString (-1));

    }

    public BigInteger SubMoney(int numbA, BigInteger money)
    {
        if(accaunts.containsKey (numbA))
        {
            BigInteger sb = accaunts.get (numbA).SubMoney (money);
            if(sb.compareTo(ZERO)<=0)
                return new BigInteger (Integer.toString (-1));
            else
            {
                return accaunts.get (numbA).getMoney ();
            }
        }
        else
            return new BigInteger (Integer.toString (-3));
    }

    public BigInteger getN() {
        return N;
    }
}

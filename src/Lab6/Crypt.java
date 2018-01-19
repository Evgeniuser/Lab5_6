package Lab6;

import java.math.BigInteger;
import java.util.LinkedList;

class Crypt extends RSACore{

    Pair<BigInteger,BigInteger> N_Fi;
    LinkedList<Pair<BigInteger,BigInteger>> keyPair;

    void GenKeyPair(int PairSize)
    {
        N_Fi = CreateStartPair();
        keyPair = new LinkedList<>();
        for(int i = 0; i!=PairSize; i++)
        {
            keyPair.add(CreatePairkey());
        }

    }

    void SetKeyPair(LinkedList<Pair<BigInteger,BigInteger>> KeyPair)
    {
        keyPair = KeyPair;
    }

    private BigInteger getC(int index)
    {
        return keyPair.get(index).getFirst();
    }

    private BigInteger getD(int index)
    {
        return keyPair.get(index).getSecond();
    }

    private BigInteger getN()
    {
        return N_Fi.getFirst();
    }

    private int crypt(int num, int index)
    {
        return Integer.parseInt(new BigInteger(Integer.toString(num)).modPow(getC(index),getN()).toString());
    }

    private int Decrypt(int num,int index)
    {
        return Integer.parseInt(new BigInteger(Integer.toString(num)).modPow(getD(index),getN()).toString());
    }

    Pair CryptPair(Pair<Integer,Integer> pair, int index)
    {
        return new Pair<>(crypt(pair.getFirst(),index), crypt(pair.getSecond(),index));
    }

    Pair DecryptPair(Pair<Integer,Integer> pair, int index)
    {
        return new Pair<>(Decrypt(pair.getFirst(),index),Decrypt(pair.getSecond(),index));
    }

    void SetN_Fi(Pair<BigInteger,BigInteger> nfi)
    {
        N_Fi = new Pair<>(nfi.getFirst(),nfi.getSecond());
    }




}

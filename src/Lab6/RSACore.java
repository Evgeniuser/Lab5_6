package Lab6;

import java.math.BigInteger;
import java.security.SecureRandom;

import static java.math.BigInteger.ONE;

public class RSACore {

    BigInteger N;
    private BigInteger P;
    private BigInteger G;
    BigInteger D;
    BigInteger C;
    BigInteger Fi;

    Pair CreateStartPair()
    {
        P = BigInteger.probablePrime(5,new SecureRandom());
        G = BigInteger.probablePrime(5,new SecureRandom());

        while(P.compareTo(G)==0)
        {
            P = BigInteger.probablePrime(5,new SecureRandom());
            G = BigInteger.probablePrime(5,new SecureRandom());
        }

        N = P.multiply(G);
        Fi = P.subtract(ONE).multiply(G.subtract(ONE));
        Pair<BigInteger,BigInteger> mPair = new Pair<>(N,Fi);
        return mPair;
    }

    Pair CreatePairkey()
    {

        D = BigInteger.probablePrime (Fi.bitLength (),new SecureRandom());
        C = D.modInverse (Fi);
        Pair<BigInteger,BigInteger> CD_pair = new Pair<>(C,D);
        return CD_pair;
    }


}

package Lab5;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA {

    public BigInteger C;
    public BigInteger D;

    RSA(SecureRandom rnd, BigInteger Fi)
    {
        D = BigInteger.probablePrime (Fi.bitLength (),rnd);
        C = D.modInverse (Fi);
    }

    public BigInteger getC() {
        return C;
    }

    public BigInteger getD() {
        return D;
    }
}


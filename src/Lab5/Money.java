package Lab5;

import java.math.BigInteger;

public class Money {

    public BigInteger Sign;
    public int Nominal;
    public BigInteger Number;

    public Money(BigInteger sign, int nominal, BigInteger number) {
        Sign = sign;
        Nominal = nominal;
        Number = number;
    }

    public BigInteger getSign() {
        return Sign;
    }

    public int getNominal() {
        return Nominal;
    }

    public BigInteger getNumber() {
        return Number;
    }

}

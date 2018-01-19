package Lab5;

import java.math.BigInteger;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.LinkedList;

public class Client {

    private LinkedList<Money> _100 = new LinkedList<>();
    private LinkedList<Money> _500 = new LinkedList<>();
    private LinkedList<Money> _1000 = new LinkedList<>();
    private LinkedList<Money> _5000 = new LinkedList<>();
    public int myAcc;
    public final Bank bank;
    private BigInteger bankN;

    public Client(Bank bank) {
        this.bank = bank;
        myAcc = this.bank.CreateAcc ("Client",new BigInteger (Long.toString (2_000_000)));
        bankN = this.bank.getN();
    }

    public void GetMoney(int nominal, int num) throws Exception {

        SecureRandom rnd = new SecureRandom();
        BigInteger r = BigInteger.probablePrime(16,rnd);
        BigInteger notR = r.modInverse(bankN);
        bank.SubMoney(myAcc,new BigInteger(Integer.toString(nominal*num)));
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

        for(int i = 0;i!=num;i++)
        {
            BigInteger numb = BigInteger.probablePrime(128,rnd);
            BigInteger hash = new BigInteger(1,messageDigest.digest(numb.toByteArray()));
            BigInteger sign = bank.CreateSign(hash.multiply(r.modPow(bank.getD(nominal),bankN)).mod(bankN),nominal);
            BigInteger test = bank.CreateSign(hash,nominal);
            System.out.println(test.compareTo(sign.multiply(notR).mod(bankN)));

            Money money = new Money(sign.multiply(notR).mod(bankN),nominal,numb);



            if(nominal == 100)
                _100.addFirst(money);
            else if(nominal == 500)
                _500.addFirst(money);
            else if(nominal == 1000)
                _1000.addFirst(money);
            else if(nominal == 5000)
                _5000.addFirst(money);
        }
    }

    public long CountCash()
    {
        long Cash=0;
        Cash+= 100*_100.size();
        Cash+= 500*_500.size();
        Cash+= 1000*_1000.size();
        Cash+= 5000*_5000.size();
        return Cash;
    }

    public LinkedList<Money> createPocket(int _100,int _500,int _1000,int _5000)
    {
        LinkedList<Money> pocket = new LinkedList<>();
        int i;
        try {
            for(i=0;i!=_100;i++)
                pocket.add(this._100.pollFirst());
            for(i=0;i!=_500;i++)
                pocket.add(this._500.pollFirst());
            for(i=0;i!=_1000;i++)
                pocket.add(this._1000.pollFirst());
            for(i=0;i!=_5000;i++)
                pocket.add(this._5000.pollFirst());
        }
        catch (IndexOutOfBoundsException e)
        {
            System.out.println("ERROR");
        }

        return pocket;
    }

    public void ReturnMoney(LinkedList<Money> money)
    {
        int nominal;
        for (Money aMoney : money) {
            nominal = aMoney.getNominal();

            if (nominal == 100)
                _100.addLast(aMoney);
            else if (nominal == 500)
                _500.addLast(aMoney);
            else if (nominal == 1000)
                _1000.addLast(aMoney);
            else if (nominal == 5000)
                _5000.addLast(aMoney);
        }

    }

}

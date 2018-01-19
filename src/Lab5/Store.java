package Lab5;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.TreeMap;

public class Store {

    private final Bank bank;
    int MyAcc;

    public Store(Bank bank) {
        this.bank = bank;
        MyAcc = this.bank.CreateAcc ("Shop",BigInteger.ZERO);
    }

    public int SellProduct(long cost, LinkedList<Money> money,Client client) throws Exception {
        long Cash = 0;
        int st;
        if(money.isEmpty())
        {
             return -3;
        }
        else {
            for (Money aMoney1 : money) Cash += aMoney1.getNominal();

            if (cost > Cash) {
                client.ReturnMoney(money);
                return -1;
            }
            if (cost < Cash) {
                client.ReturnMoney(money);
                return -10;
            }
            for (Money aMoney : money) {

                st = bank.CheckMoney(aMoney);
                if (st == -2)
                {
                    client.ReturnMoney(money);
                    System.out.println("return money");
                    return -2;
                }
            }

            bank.AddMoney(MyAcc, new BigInteger(Long.toString(Cash)));

            return 0;
        }
    }
}

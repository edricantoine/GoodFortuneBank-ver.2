package database;
import java.io.*;





public class SavingsAccount implements Serializable{

    Double money;

    public SavingsAccount(Double m) {
        this.money = m;
    }

    public void accrueInterest() {

        this.money += (this.money * 0.0125);

    }

}
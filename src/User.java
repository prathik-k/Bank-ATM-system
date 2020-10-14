import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.security.MessageDigest;

public class User {

    private String fname;
    private String lname;
    private String uuid;
    private byte pinHash[];
    private ArrayList<Account> accounts;

    public User(String fname,String lname,String pin,Bank theBank){
        this.fname = fname;
        this.lname = lname;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error, no such hashing algorithm");
            e.printStackTrace();
            System.exit(1);
        }
        this.uuid = theBank.getNewUserUUID();

        //Empty list of accounts
        this.accounts = new ArrayList<Account>();

        System.out.printf("New user %s, %s with ID %s created \n", lname,fname,this.uuid);
    }

    public void addAccount(Account acct){
        this.accounts.add(acct);
    }

    public String getUUID(){
        return this.uuid;
    }

    public boolean validatePin(String thepin){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(thepin.getBytes()),this.pinHash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error, no such hashing algorithm");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }
    public String getFname(){
        return this.fname;
    }

    public void printAccountsSummary(){
        System.out.printf("\n %s's account summary: \n",this.fname);
        for (int a=0;a<this.accounts.size();a++) {
            System.out.printf("%d) %s\n", a+1,this.accounts.get(a).getSummaryLine());
        }
        System.out.println();
    }

    public int numAccounts(){
        return this.accounts.size();
    }

    public void printAcctHist(int acctIdx){
        this.accounts.get(acctIdx).printTransHist();
    }

    public double getAcctBal(int acctIdx){
        return this.accounts.get(acctIdx).getBalance();
    }

    public String getActUUID(int acctIdx){
        return this.accounts.get(acctIdx).getUUID();
    }

    public void addAcctTrans(int acctIdx,double amount,String memo){
        this.accounts.get(acctIdx).addTransaction(amount,memo);
    }




}

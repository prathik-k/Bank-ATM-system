import java.io.Console;
import java.util.Scanner;

public class ATM {

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        Bank theBank = new Bank("Chase Bank");
        User a = theBank.addUser("John","Doe","1221");

        Account newAccount = new Account("Checking",a,theBank);
        a.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User currUser;
        while(true){
             currUser = ATM.mainMenuPrompt(theBank,sc);
             ATM.printUserMenu(currUser,sc);
        }
    }

    public static User mainMenuPrompt(Bank theBank,Scanner sc){
        String userID,pin;
        User authUser;
        Console console = System.console();
        do{
            System.out.printf("\n Welcome to %s\n",theBank.getName());
            System.out.print("Enter User ID: ");
            userID = sc.nextLine();
            System.out.printf("Enter pin: ");
            pin = sc.nextLine();
            //char[] pinArray = console.readPassword("Enter Password: ");
            //pin = new String(pinArray);

            authUser = theBank.userLogin(userID,pin);
            if(authUser==null){
                System.out.println("Incorrect combination. Please try again. ");
            }
        }while(authUser==null);
        return authUser;
    }

    public static void printUserMenu(User theUser,Scanner sc){
        theUser.printAccountsSummary();

        int choice;

        do{
            System.out.printf("Welcome %s, what would you like to do?\n",theUser.getFname());
            System.out.println("1. Account transaction history");
            System.out.println("2. Account withdrawal");
            System.out.println("3. Account Deposit");
            System.out.println("4. Account transfer");
            System.out.println("5. Quit");
            System.out.println("Enter choice: ");
            choice = sc.nextInt();

            if (choice<1 || choice>5){
                System.out.println("Invaluid choice; Please choose between 1-5");
            }
        }while(choice<1 || choice>5);
        switch(choice) {
            case 1:
                ATM.showTransHist(theUser, sc);
                break;
            case 2:
                ATM.withdraw(theUser, sc);
                break;
            case 3:
                ATM.deposit(theUser, sc);
                break;
            case 4:
                ATM.transfer(theUser, sc);
                break;
            case 5:
                System.exit(0);
        }
        if (choice!=5){
            ATM.printUserMenu(theUser,sc);
        }
    }

    public static void showTransHist(User theUser,Scanner sc){
        int theAcct;
        do{
            System.out.printf("Enter the number (1-%d) of the account whose transactions you want to see: ",theUser.numAccounts());
            theAcct = sc.nextInt()-1;
            if (theAcct<0 || theAcct>=theUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        }while(theAcct<0||theAcct>=theUser.numAccounts());

        theUser.printAcctHist(theAcct);
    }
    public static void transfer(User theUser,Scanner sc){
        int fromAcct,toAcct;
        double amount,acctBal;

        do{
            System.out.printf("Enter the number (1-%d) of the account to transfer from: ",theUser.numAccounts());
            fromAcct = sc.nextInt()-1;
            if (fromAcct<0||fromAcct>=theUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        }while(fromAcct<0||fromAcct>=theUser.numAccounts());
        acctBal = theUser.getAcctBal(fromAcct);

        do{
            System.out.printf("Enter the number (1-%d) of the account to transfer to: ",theUser.numAccounts());
            toAcct = sc.nextInt()-1;
            if (toAcct<0||toAcct>=theUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        }while(toAcct<0||toAcct>=theUser.numAccounts());

        do{
            System.out.printf("Enter amount to transfer: (max $%.02f): $",acctBal);
            amount = sc.nextDouble();
            if(amount<0){
                System.out.println("Amount must be greater than 0.");
            } else if(amount>acctBal){
                System.out.println("Amount too much to withdraw (greater than available balance).");
            }
        }while(amount<0||amount>acctBal);

        theUser.addAcctTrans(fromAcct,-1*amount,String.format("Transfer to account %s",theUser.getActUUID(toAcct)));
        theUser.addAcctTrans(toAcct,amount,String.format("Transfer from account %s",theUser.getActUUID(fromAcct)));
    }

    public static void withdraw(User theUser,Scanner sc){
        int fromAcct;
        double amount,acctBal;
        String memo;

        do{
            System.out.printf("Enter the number (1-%d) of the account to withdraw from: ",theUser.numAccounts());
            fromAcct = sc.nextInt()-1;
            if (fromAcct<0||fromAcct>=theUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        }while(fromAcct<0||fromAcct>=theUser.numAccounts());
        acctBal = theUser.getAcctBal(fromAcct);

        do{
            System.out.printf("Enter amount to withdraw: (max $%.02f): $",acctBal);
            amount = sc.nextDouble();
            if(amount<0){
                System.out.println("Amount must be greater than 0.");
            } else if(amount>acctBal){
                System.out.println("Amount too much to withdraw (greater than available balance).");
            }
        }while(amount<0||amount>acctBal);
        sc.nextLine();
        System.out.println("Enter memo: ");
        memo = sc.nextLine();
        theUser.addAcctTrans(fromAcct,-1*amount,memo);
    }

    public static void deposit(User theUser,Scanner sc){
        int toAcct;
        double amount,acctBal;
        String memo;

        do{
            System.out.printf("Enter the number (1-%d) of the account to transfer to: ",theUser.numAccounts());
            toAcct = sc.nextInt()-1;
            if (toAcct<0||toAcct>=theUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        }while(toAcct<0||toAcct>=theUser.numAccounts());

        do{
            System.out.printf("Enter amount to deposit: ");
            amount = sc.nextDouble();
            if(amount<0){
                System.out.println("Amount must be greater than 0.");
            }
        }while(amount<0);

        sc.nextLine();
        System.out.println("Enter memo: ");
        memo = sc.nextLine();
        theUser.addAcctTrans(toAcct,amount,memo);
    }
}
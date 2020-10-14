import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class ATM_GUI {
    private JFrame frame;
    private JPanel panel;
    public ATM_GUI(){
        frame = new JFrame();
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(300,400,300,300));

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args){
        new ATM_GUI();
        Scanner sc = new Scanner(System.in);

        Bank theBank = new Bank("Chase Bank");
        User a = theBank.addUser("John","Doe","1234");

        Account newAccount = new Account("Checking",a,theBank);
        a.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User currUser;
        while(true){
            currUser = ATM.main_prompt(theBank,sc);
            ATM.printUserMenu(currUser,sc);
        }
    }
}

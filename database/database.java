package database;
import java.util.ArrayList;   
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import database.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.io.*;




public class database implements Serializable {
    /** ArrayLists used to hold accounts and usernames*/
    public static ArrayList<database> userList = new ArrayList<database>();
    public static ArrayList<String> nameList = new ArrayList<String>();

    /** Constants for the main screen (seen when launching application) */
    public static JFrame frame = new JFrame("Good Fortune Bank");
    public static JPanel panel = new JPanel();
    public static JLabel label = new JLabel("Welcome to Good Fortune Bank", JLabel.CENTER);
    public static JButton button = new JButton("Login");
    public static JButton buttonCA = new JButton("Create Account");
    public static JButton buttonQ = new JButton("Quit");
    public static JButton options = new JButton("Options");

    /**Constants for the Create Account screen */
    public static JFrame cframe = new JFrame("Good Fortune Bank");
    public static JPanel cpanel = new JPanel();
    public static JLabel clabel = new JLabel("Enter username and password...", JLabel.CENTER);
    public static JTextField usernameField = new JTextField(20);
    public static JPasswordField pwField = new JPasswordField(20);
    public static JButton submitButton = new JButton("Create Account");
    public static JButton backButton = new JButton("Back");

    /** Constants for the Account Created screen */
    public static JFrame createdFrame = new JFrame("Success");
    public static JPanel createdPanel = new JPanel();
    public static JLabel createdLabel = new JLabel("Account successfully created.");
    public static JButton okButton = new JButton("OK");

    /** Constants for the Login screen */
    public static JFrame logFrame = new JFrame("Login");
    public static JPanel logPanel = new JPanel();
    public static JLabel logLabel = new JLabel("Enter username and password.");
    public static JTextField logUser = new JTextField(20);
    public static JPasswordField logPass = new JPasswordField(20);
    public static JButton logButton = new JButton("Login");
    public static JButton logBack = new JButton("Back");

    /**DecimalFormat for deposit & withdrawal textboxes */
    public static DecimalFormat cd = new DecimalFormat("#.00");

    /**Constants for the options screen */
    public static JFrame opFrame = new JFrame("Options");
    public static JPanel opPanel = new JPanel();
    public static JButton deleteButton = new JButton("Delete one account");
    public static JButton opBack = new JButton("Back");

    /** Constants for the delete account screeen */

    public static JFrame delFrame = new JFrame("Delete an account");
    public static JPanel delPanel = new JPanel();
    public static JLabel delLabel = new JLabel("Enter the username and password of the account you wish to delete.");
    public static JTextField delUser = new JTextField(20);
    public static JPasswordField delPass = new JPasswordField(20);
    public static JButton delButton = new JButton("Delete Account");
    public static JButton delBack = new JButton("Back");
    
    
    

    String name;
    String pass;  
    double money;
    SavingsAccount sAcct;
    

    public database(String n, String p, double m) {
        this.name = n;
        this.pass = p;
        this.money = m;
        this.sAcct = new SavingsAccount(0.00);
        
    }
  

    public static void finalCreate(String n, String p) {
        
        userList.add(new database(n, p, 0.00));
        nameList.add(n);
        try {
            FileOutputStream writeData = new FileOutputStream("bankdata.ser");
            ObjectOutputStream writeStream = new ObjectOutputStream(writeData);
            writeStream.writeObject(userList);
            writeStream.flush();
            writeStream.close();
        } catch(IOException e) {
            e.printStackTrace();
        }

        try {
            FileOutputStream writeName = new FileOutputStream("namedata.ser");
            ObjectOutputStream nameStream = new ObjectOutputStream(writeName);
            nameStream.writeObject(nameList);
            nameStream.flush();
            nameStream.close();

        } catch(IOException e) {
            e.printStackTrace();
        }
        return;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
    
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static Boolean validEntry(String n) {
        for(database d : userList) {
            if(d.name.equals(n)) {
                return false;
                
            }
        }

        return true;
    }

    public static Boolean validLogin(String u, String pw, database d) {
        if(d.name.equals(u) && d.pass.equals(pw)) {
            return true;
        }
        return false;
    }

    public void addFunds(Double amount) {
        this.money += amount;
    }

    public void removeFunds(Double amount) {
        this.money -= amount;
    }

    public void transfer(database d, Double amount) {
        this.money -= amount;
        d.money += amount;
    }

    public void transferToSavings(Double amount) {
        this.money -= amount;
        this.sAcct.money += amount;
    }

    public void transferFromSavings(Double amount) {
        this.money += amount;
        this.sAcct.money -= amount;
    }

    public void manipAccount() {
        
        
        /** "Home screen" settings & constants */
        database temp = this; 

        JFrame mainFrame = new JFrame(this.name + "'s Account");
        mainFrame.setSize(300, 300);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final String MENU = "Main menu";
        final String DEPOSIT = "Deposit";
        final String WITHDRAW = "Withdraw";
        final String TRANSFER = "Transfer money";
        final String SAVINGS = "Savings account";

        JLabel mainLabel = new JLabel("Hello, " + this.name + ". You currently have $" + round(this.money, 2) + " in your account.");
        mainLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel savingsLabel = new JLabel("Your savings account currently has $" + round(this.sAcct.money, 2) + " deposited.");
        savingsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel mainCard = new JPanel();
        mainCard.setLayout(new BoxLayout(mainCard, BoxLayout.Y_AXIS));
        mainCard.add(mainLabel);
        mainCard.add(savingsLabel);
        mainCard.add(logoutButton);

        /** TransferBetweenAccounts stuff */

        JLabel transferLabel = new JLabel("Enter the name of the account you would like to transfer to.");
        transferLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField recieveName = new JTextField(20);
        recieveName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel tMoneyLabel = new JLabel("Enter the amount of money to transfer to that account.");
        tMoneyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JFormattedTextField amountToTransfer = new JFormattedTextField(cd);
        amountToTransfer.setColumns(10);
        amountToTransfer.setAlignmentX(Component.CENTER_ALIGNMENT);

       

        JButton transferButton = new JButton("Transfer");
        transferButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel transCard = new JPanel();
        transCard.setLayout(new BoxLayout(transCard, BoxLayout.Y_AXIS));
        transCard.add(transferLabel);
        transCard.add(recieveName);
        transCard.add(tMoneyLabel);
        transCard.add(amountToTransfer);
        
        transCard.add(transferButton);

        /** Savings account screen stuff */

        JLabel savLabel1 = new JLabel("Enter the amount of money to transfer from main account to savings account.");
        savLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);

        JFormattedTextField toSavings = new JFormattedTextField(cd);
        toSavings.setColumns(10);
        toSavings.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton toTransfer = new JButton("Transfer");
        toTransfer.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel savLabel2 = new JLabel("Enter the amount of money to transfer from savings account to main account.");
        savLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);

        JFormattedTextField fromSavings = new JFormattedTextField(cd);
        fromSavings.setColumns(10);
        fromSavings.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton fromTransfer = new JButton("Transfer");
        fromTransfer.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel savCard = new JPanel();
        savCard.setLayout(new BoxLayout(savCard, BoxLayout.Y_AXIS));
        savCard.add(savLabel1);
        savCard.add(toSavings);
        savCard.add(toTransfer);
        savCard.add(savLabel2);
        savCard.add(fromSavings);
        savCard.add(fromTransfer);

        /** Deposit card stuff */

        JLabel depositLabel = new JLabel("Enter amount of money to deposit, then press the button.");
        depositLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JFormattedTextField amountToDeposit = new JFormattedTextField(cd);
        amountToDeposit.setColumns(10);
        amountToDeposit.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton depositButton = new JButton("Deposit");
        depositButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel depCard = new JPanel();
        depCard.setLayout(new BoxLayout(depCard, BoxLayout.Y_AXIS));
        depCard.add(depositLabel);
        depCard.add(amountToDeposit);
        depCard.add(depositButton);

        /** Withdraw card stuff */

        JLabel withLabel = new JLabel("Enter amount of money to withdraw, then press the button.");
        withLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


        JFormattedTextField amountToWith = new JFormattedTextField(cd);
        amountToWith.setColumns(10);
        amountToWith.setAlignmentX(Component.CENTER_ALIGNMENT);
       
        JButton withButton = new JButton("Withdraw");
        withButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel withCard = new JPanel();
        withCard.setLayout(new BoxLayout(withCard, BoxLayout.Y_AXIS));
        withCard.add(withLabel);
        withCard.add(amountToWith);
        withCard.add(withButton);

        /** Final touches on the CardLayout frame */

        JPanel cards = new JPanel(new CardLayout());
        cards.add(mainCard, MENU);
        cards.add(depCard, DEPOSIT);
        cards.add(withCard, WITHDRAW);
        cards.add(transCard, TRANSFER);
        cards.add(savCard, SAVINGS);

        JPanel comboBoxPane = new JPanel();
        String comboBoxItems[] = {MENU, DEPOSIT, WITHDRAW, TRANSFER, SAVINGS};

        JComboBox<String> cb = new JComboBox<>(comboBoxItems);
        cb.setEditable(false);

        /** ItemListener handlers */

        for(ItemListener il : cb.getItemListeners()) {
            cb.removeItemListener(il);
        }
        for(ActionListener al : logoutButton.getActionListeners()) {
            logoutButton.removeActionListener(al);
        }
        for(ActionListener al : depositButton.getActionListeners()) {
            depositButton.removeActionListener(al);
        }
        for(ActionListener al : withButton.getActionListeners()) {
            withButton.removeActionListener(al);
        }
        for(ActionListener al : transferButton.getActionListeners()) {
            transferButton.removeActionListener(al);
        }
        for(ActionListener al : toTransfer.getActionListeners()) {
            toTransfer.removeActionListener(al);
        }
        for(ActionListener al : fromTransfer.getActionListeners()) {
            fromTransfer.removeActionListener(al);
        }

        cb.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent evt) {
                depositLabel.setText("Enter amount of money to deposit, then press the button.");
                withLabel.setText("Enter amount of money to withdraw, then press the button.");
                tMoneyLabel.setText("Enter the amount of money to transfer to that account.");
                savLabel1.setText("Enter the amount of money to transfer from main account to savings account.");
                savLabel2.setText("Enter the amount of money to transfer from savings account to main account.");
               
                CardLayout cl = (CardLayout)(cards.getLayout());
                cl.show(cards, (String)evt.getItem());
            }

        });

        logoutButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                mainFrame.setVisible(false);
                frame.setVisible(true);


            }

        });

        depositButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                final double depAmount;
                depAmount = Double.parseDouble(amountToDeposit.getText());

                depositLabel.setText("Deposit successful.");

                temp.addFunds(depAmount);
                amountToDeposit.setText("");
                mainLabel.setText("Hello, " + temp.name + ". You currently have $" + round(temp.money, 2) + " in your account.");
                try {
                    FileOutputStream writeData = new FileOutputStream("bankdata.ser");
                    ObjectOutputStream writeStream = new ObjectOutputStream(writeData);
                    writeStream.writeObject(userList);
                    writeStream.flush();
                    writeStream.close();
                } catch(IOException f) {
                    f.printStackTrace();
                }
                


            }

        });

        withButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                final double withAmount;
                withAmount = Double.parseDouble(amountToWith.getText());

                if(withAmount <= temp.money) {
                    withLabel.setText("Withdrawal successful.");

                    temp.removeFunds(withAmount);
                    amountToWith.setText("");
                    mainLabel.setText("Hello, " + temp.name + ". You currently have $" + round(temp.money, 2) + " in your account.");
                    try {
                        FileOutputStream writeData = new FileOutputStream("bankdata.ser");
                        ObjectOutputStream writeStream = new ObjectOutputStream(writeData);
                        writeStream.writeObject(userList);
                        writeStream.flush();
                        writeStream.close();
                    } catch(IOException f) {
                        f.printStackTrace();
                    }
                } else {
                    
                    withLabel.setText("Insufficent funds in account.");
                    amountToWith.setText("");
                    mainLabel.setText("Hello, " + temp.name + ". You currently have $" + round(temp.money, 2)+ " in your account.");
                }


            }

        });

        transferButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                final double transAmount;
                final String transName;
                transAmount = Double.parseDouble(amountToTransfer.getText());
                transName = recieveName.getText();

                for(database d : userList) {
                    if(transName.equals(d.name) && !transName.equals(temp.name) && transAmount <= temp.money) {
                        tMoneyLabel.setText("Transfer successful.");
                        temp.transfer(d, transAmount);
                        mainLabel.setText("Hello, " + temp.name + ". You currently have $" + round(temp.money, 2) + " in your account.");
                        try {
                            FileOutputStream writeData = new FileOutputStream("bankdata.ser");
                            ObjectOutputStream writeStream = new ObjectOutputStream(writeData);
                            writeStream.writeObject(userList);
                            writeStream.flush();
                            writeStream.close();
                        } catch(IOException f) {
                            f.printStackTrace();
                        }
                        break;
                    }
                }

                if(!nameList.contains(transName)) {
                    tMoneyLabel.setText("No accounts with that name.");
                } else if (transAmount > temp.money) {
                    tMoneyLabel.setText("Insufficient funds in account.");
                }

                
            }

        });

        toTransfer.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                final double tAmount;
                tAmount = Double.parseDouble(toSavings.getText());

                if(tAmount <= temp.money) {
                    savLabel1.setText("Transfer successful.");
                    temp.transferToSavings(tAmount);
                    toSavings.setText("");
                    mainLabel.setText("Hello, " + temp.name + ". You currently have $" + round(temp.money, 2) + " in your account.");
                    savingsLabel.setText("Your savings account currently has $" + round(temp.sAcct.money, 2) + " deposited.");
                } else if (tAmount > temp.money) {
                    savLabel1.setText("Not enough money in main account.");
                }
                
                try {
                    FileOutputStream writeData = new FileOutputStream("bankdata.ser");
                    ObjectOutputStream writeStream = new ObjectOutputStream(writeData);
                    writeStream.writeObject(userList);
                    writeStream.flush();
                    writeStream.close();
                } catch(IOException f) {
                    f.printStackTrace();
                }
                


            }

        });

        fromTransfer.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                final double tAmount;
                tAmount = Double.parseDouble(fromSavings.getText());

                if(tAmount <= temp.sAcct.money) {
                    savLabel2.setText("Transfer successful.");
                    temp.transferFromSavings(tAmount);
                    fromSavings.setText("");
                    mainLabel.setText("Hello, " + temp.name + ". You currently have $" + round(temp.money, 2) + " in your account.");
                    savingsLabel.setText("Your savings account currently has $" + round(temp.sAcct.money, 2) + " deposited.");
                } else if (tAmount > temp.sAcct.money) {
                    savLabel2.setText("Not enough money in savings account.");
                }
                
                try {
                    FileOutputStream writeData = new FileOutputStream("bankdata.ser");
                    ObjectOutputStream writeStream = new ObjectOutputStream(writeData);
                    writeStream.writeObject(userList);
                    writeStream.flush();
                    writeStream.close();
                } catch(IOException f) {
                    f.printStackTrace();
                }
                


            }

        });


        comboBoxPane.add(cb);
        
        mainFrame.getContentPane().add(comboBoxPane, BorderLayout.PAGE_START);
        mainFrame.getContentPane().add(cards, BorderLayout.CENTER);

        mainFrame.pack();

        mainFrame.setVisible(true);

        

        
        

    }

    public static void main(String [] args) {

        /** Write to file stuff */

        

        try {
            FileInputStream readData = new FileInputStream("bankdata.ser");
            ObjectInputStream readStream = new ObjectInputStream(readData);
            ArrayList<database> userList2 = (ArrayList<database>) readStream.readObject();
            userList = userList2;
            readStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        

        try {
            FileInputStream readName = new FileInputStream("namedata.ser");
            ObjectInputStream readNStream = new ObjectInputStream(readName);
            ArrayList<String> nameList2 = (ArrayList<String>) readNStream.readObject();
            nameList = nameList2;
            readNStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        
        
        /** Initial frame settings */
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,300);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);

        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(button);

        buttonCA.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(buttonCA);

        options.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(options);

        buttonQ.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(buttonQ);

        

        frame.getContentPane().add(panel); 
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        buttonCA.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                frame.setVisible(false);
                
                cframe.setVisible(true);


            }

        });

        options.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                frame.setVisible(false);
                opFrame.setVisible(true);


            }

        });

        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                logButton.setText("Login");

                frame.setVisible(false);
                
                logFrame.setVisible(true);


            }

        });

        buttonQ.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);

                System.exit(0);
            }

        });

        /** Create Account screen settings */
        cframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cframe.setSize(300,300);

        cpanel.setLayout(new BoxLayout(cpanel, BoxLayout.Y_AXIS));

        clabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cpanel.add(clabel);

        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        cpanel.add(usernameField);

        pwField.setAlignmentX(Component.CENTER_ALIGNMENT);
        cpanel.add(pwField);

        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cpanel.add(submitButton);

        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cpanel.add(backButton);
        
        cframe.getContentPane().add(cpanel); 
        cframe.setLocationRelativeTo(null);
        cframe.pack();
        

        backButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                submitButton.setText("Submit");
                

                cframe.setVisible(false);
                frame.setVisible(true);
                logUser.setText("");
                logPass.setText("");
                

            }

        });

        submitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                

                final String chosenName;
                final String chosenPass;
                chosenName = usernameField.getText();
                chosenPass = new String(pwField.getPassword());
                Boolean isValid = validEntry(chosenName);
                
                
                if(chosenName.isEmpty() == true || chosenPass.isEmpty() == true) {

                    submitButton.setText("Empty fields.");
                    
                } else if (isValid == false) {

                    submitButton.setText("Name in use already.");
                    usernameField.setText("");
                    pwField.setText("");
   

                } else {

                    submitButton.setText("Submit");

                                  
                    finalCreate(chosenName, chosenPass);
                    usernameField.setText("");
                    pwField.setText("");

                    cframe.setVisible(false);

                    createdFrame.setVisible(true);
                    


                }

               

            }

            

        });

        


         /** Create successful settings */
         createdFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         createdFrame.setSize(300, 300);
 
 
         createdPanel.setLayout(new BoxLayout(createdPanel, BoxLayout.Y_AXIS));
 
         createdLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
         createdPanel.add(createdLabel);
 
         okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
         createdPanel.add(okButton);
         createdFrame.getContentPane().add(createdPanel);
         createdFrame.setLocationRelativeTo(null);
         createdFrame.pack();

         okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                createdFrame.setVisible(false);
                frame.setVisible(true);
                
                

            }

        });

         /** Login screen settings */

         logFrame.setDefaultCloseOperation(logFrame.EXIT_ON_CLOSE);
         logFrame.setSize(300, 300);
         logFrame.setLocationRelativeTo(null);

         logPanel.setLayout(new BoxLayout(logPanel, BoxLayout.Y_AXIS));

         logLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
         logPanel.add(logLabel);

         logUser.setAlignmentX(Component.CENTER_ALIGNMENT);
         logPanel.add(logUser);

         logPass.setAlignmentX(Component.CENTER_ALIGNMENT);
         logPanel.add(logPass);

         logButton.setAlignmentX(Component.CENTER_ALIGNMENT);
         logPanel.add(logButton);

         logBack.setAlignmentX(Component.CENTER_ALIGNMENT);
         logPanel.add(logBack);

         logFrame.getContentPane().add(logPanel);
         logFrame.pack();

         logBack.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                logFrame.setVisible(false);
                frame.setVisible(true);
                
            }

        });

        logButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                

                final String logName;
                final String logPword;
                logName = logUser.getText();
                logPword = new String(logPass.getPassword());
                
                
                
                if(logName.isEmpty() == true || logPword.isEmpty() == true) {

                    logButton.setText("Empty fields.");

                } else {
                    for(database d : userList) {

                        if(validLogin(logName, logPword, d)) {
                            logButton.setText("Login");
                            logUser.setText("");
                            logPass.setText("");
                            logFrame.setVisible(false);
                            d.manipAccount();
                            break;
                        }

                    }

                    logButton.setText("Invalid login.");
                    
                }
                    
               

               

            }

            

        });

        /** Options screen settings */

        opFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        opFrame.setSize(300, 300);
        opFrame.setLocationRelativeTo(null);
        opPanel.setLayout(new BoxLayout(opPanel, BoxLayout.Y_AXIS));
        opBack.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        opPanel.add(deleteButton);
        opPanel.add(opBack);
        opFrame.getContentPane().add(opPanel);
        opFrame.pack();

        opBack.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                opFrame.setVisible(false);
                
                frame.setVisible(true);


            }

        });

        deleteButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                opFrame.setVisible(false);
                
                delFrame.setVisible(true);


            }

        });

        /** Delete account screen settings */

        delFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        delFrame.setSize(300, 300);
        delFrame.setLocationRelativeTo(null);
        delPanel.setLayout(new BoxLayout(delPanel, BoxLayout.Y_AXIS));
        delLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        delUser.setAlignmentX(Component.CENTER_ALIGNMENT);
        delPass.setAlignmentX(Component.CENTER_ALIGNMENT);
        delButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        delBack.setAlignmentX(Component.CENTER_ALIGNMENT);
        

        delPanel.add(delLabel);
        delPanel.add(delUser);
        delPanel.add(delPass);
        delPanel.add(delButton);
        delPanel.add(delBack);

        delFrame.getContentPane().add(delPanel);

        delFrame.pack();

        delBack.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                delLabel.setText("Enter the username and password of the account you wish to delete.");
                

                delFrame.setVisible(false);
                
                opFrame.setVisible(true);


            }

        });

        delButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                final String deleteName;
                final String deletePass;

                deleteName = delUser.getText();
                deletePass = new String(delPass.getPassword());

                if(!nameList.contains(deleteName)) {
                    delLabel.setText("No account with that name found.");
                } else {

                for(database d : userList) {
                    if(d.name.equals(deleteName) && d.pass.equals(deletePass)) {
                        delLabel.setText("Account successfully deleted.");
                        userList.remove(d);
                        nameList.remove(d.name);
                        try {
                            FileOutputStream writeData = new FileOutputStream("bankdata.ser");
                            ObjectOutputStream writeStream = new ObjectOutputStream(writeData);
                            writeStream.writeObject(userList);
                            writeStream.flush();
                            writeStream.close();
                        } catch(IOException f) {
                            f.printStackTrace();
                        }
                
                        try {
                            FileOutputStream writeName = new FileOutputStream("namedata.ser");
                            ObjectOutputStream nameStream = new ObjectOutputStream(writeName);
                            nameStream.writeObject(nameList);
                            nameStream.flush();
                            nameStream.close();
                
                        } catch(IOException f) {
                            f.printStackTrace();
                        }
                        break;
                    }
                }
            }

                

                delUser.setText("");
                delPass.setText("");




            }

        });
        


    }
        
}

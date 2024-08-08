
package bank;


import java.io.*;
import java.util.*;

//  bank contains many accounts 
public class Bank{
    // array list of object account
    ArrayList<Account> AL = new ArrayList<Account>();
    // add new customer account
    public void addNewRecord(){
        //******************* Start Reading Inputs **********************************//
        Scanner input = new Scanner(System.in);
        System.out.print("\nEnter name of Account Holder: ");
        String name = input.nextLine();
        System.out.print("\nEnter gender of Account Holder (male or female): ");
        String gender = input.next();
        System.out.print("\nEnter email of Account Holder: ");
        String email = input.next();
        System.out.print("\nEnter address of Account Holder: ");
        String address = input.next();

        String account_no;
        boolean unique = true;
        do{ // continue loop if digit account number less than 8
            System.out.print("Enter an 8 digit Account Number (must start with 0) (contact manager for its allocation): ");
            account_no = input.next();
            for(int i = 0; i<AL.size(); i++){
                if(AL.get(i).getAccountNo().equals(account_no)){
                    unique = false;
                    System.out.println("This account is already exist...try another account");
                    break;
                }
            }
            if(account_no.charAt(0) != '0')
                System.out.println("account number must start with 0");
        }while(account_no.length() < 8 || unique == false || account_no.charAt(0) != '0');
        System.out.print("Enter PIN for Account Holder: ");
        String pin = input.next();
        System.out.println("You have at least one currency (SAR)...");
        System.out.print("Default amount of 1000 SAR is already added to the account, to add more money, write that amount else enter zero: ");
        double amount = input.nextDouble();
        //******************* End Reading Inputs **********************************//

        //  default account currency is SAR and default amount is 1000
        Currency curr = new Currency(account_no, "SAR", 1000+amount);
        ArrayList<Currency> al_curr = new ArrayList<Currency>(); // define array lis of currencies
        al_curr.add(curr);  // add currency to array list

        // create object of account
        Account acount = new Account(name, gender, email, address, account_no, pin, al_curr);
        AL.add(acount); // add account object to array list
        return;
    }
    // add currency to a specific customer
    public void addCustCurr(){
        Scanner input = new Scanner(System.in);
        String customer_acc;
        do{ // continue loop if digit account number less than 8
            System.out.print("\nEnter Customer's 8 digit account number: ");
            customer_acc = input.next();
        }while(customer_acc.length() < 8);
        System.out.print("Enter Sender's pin code: ");
        String customer_pin = input.next();

        int customer_index = -1;
        for(int i = 0; i< AL.size(); i++){  // check if account is exist and pin is correct
            if (AL.get(i).getAccountNo().equals(customer_acc) && AL.get(i).getPIN().equals(customer_pin))
                customer_index = i; // change index to i
        }
        if(customer_index == -1){
            System.out.println("\n Account not Found Or PIN Not Correct");
            return;
        }

        System.out.print("Enter Customer Currency code: ");
        String cur = input.next().toUpperCase();    // convert currency code to upper case letters

        // create new currency object
        Currency new_curr = new Currency(customer_acc, cur, 0.0);
        AL.get(customer_index).curr.add(new_curr); // add currency object to array list
        System.out.println("Customer Currency code Added Successfully ");
        return;
    }
    // transfer amount from a specific account exist to another account exist
    public void transfer(){
        Scanner input = new Scanner(System.in);
        String sender_acc; // sender account
        do{ // continue loop if digit account number less than 8
            System.out.print("\nEnter sender's 8 digit account number: ");
            sender_acc = input.next();
        }while(sender_acc.length() < 8);
        System.out.print("Enter Sender's pin code: ");
        String sender_pin = input.next();

        int sender_index = -1;
        for(int i = 0; i< AL.size(); i++){  // check if sender account is exist and pin is correct
            if (AL.get(i).getAccountNo().equals(sender_acc) && AL.get(i).getPIN().equals(sender_pin))
                sender_index = i; // change index to i
        }
        if(sender_index == -1){
            System.out.println("\n Account not Found Or PIN Not Correct");
            return;
        }
        String receiver_acc; // receiver account
        do{ // continue loop if digit account number less than 8
            System.out.print("\nEnter receiver's 8 digit account number: ");
            receiver_acc = input.next();
        }while(receiver_acc.length() < 8);

        int receiver_index = -1;
        for(int i = 0; i< AL.size(); i++){  // check if receiver account is exist
            if (AL.get(i).getAccountNo().equals(receiver_acc))
                receiver_index = i; // change index to i
        }
        if(receiver_index == -1){
            System.out.println("\n Receiver's account not Found");
            return;
        }
        int sender_cur_indx = -1;
        String code = null;
        boolean wrong_cur = true;
        while(wrong_cur){   // continue loop until code currency is correct
            printCustomerCurrencies(sender_index);  // display all currencies that have it sender
            System.out.print("\nEnter sender currency code(or enter 'exit' to exit): ");
            code = input.next();

            if(code.equals("exit")) // check if user enter exist then will return back
                return;
            
            // check if code currency existed for sender account is exist
            for(int i = 0; i< AL.get(sender_index).curr.size(); i++){
                if(code.toUpperCase().equals(AL.get(sender_index).curr.get(i).getCode()))
                    sender_cur_indx = i; // change index to i
            }
            if(sender_cur_indx != -1){
                wrong_cur = false;  // code currency is valid then change value to false
            }else{
                System.out.println("\n Sender's currency not Found..try again");
            }
        }
        int receiver_cur_indx = -1;
        // check if code currency existed for receiver account is exist
        for(int i = 0; i< AL.get(receiver_index).curr.size(); i++){
            // code is currency for sender
            if(code.toUpperCase().equals(AL.get(receiver_index).curr.get(i).getCode().toUpperCase()))
                receiver_cur_indx = i; // change index to i
        }
        if(receiver_cur_indx == -1){
            System.out.println("\n Receiver's currency not Found, he doesn't have "+code+" currency");
            return;
        }

        System.out.print("\nAmount to be transferred: ");
        double amount = input.nextDouble(); // enter amount that you want to transfered
        
        try{
            if(amount > 0){
                double old_sender_amount = AL.get(sender_index).curr.get(sender_cur_indx).getAmount();
                double old_receiver_amount = AL.get(receiver_index).curr.get(receiver_cur_indx).getAmount();
                if(old_sender_amount >= amount){    // check if balance sender is greater or equal than a given amount
                    // receiver balance will increased
                    AL.get(receiver_index).curr.get(receiver_cur_indx).setAmount(old_receiver_amount + amount );

                    // receiver balance will reduced
                    AL.get(sender_index).curr.get(sender_cur_indx).setAmount(old_sender_amount - amount );

                    System.out.println("\nAmount transferred Successfully....... ");
                    return;
                }
                else {  // otherwise the blalance is low
                    System.out.println("\nSender doesn't have this much balance in his account");
                    return;
                }	
            }else{
                throw new MyException("Amount cannot be negative");
            }
        }catch(MyException e){
            System.out.println(e.getMessage());
            System.out.println("TRANSACTION FAILURE!!");
        }
    }
    // Withdraw/Remittance to reduced/increased of customer balance
    public void withdrawOrRemittance(String type){
        Scanner input = new Scanner(System.in);
        String customer_acc;
        do{ // continue loop if digit account number less than 8
            System.out.print("\nEnter Customer's 8 digit account number: ");
            customer_acc = input.next();
        }while(customer_acc.length() < 8);
        System.out.print("Enter Customer's pin code: ");
        String customer_pin = input.next();

        int customer_index = -1;
        for(int i = 0; i< AL.size(); i++){  // check if customer account is exist and pin is correct
            if ((AL.get(i).getAccountNo().equals(customer_acc)) && (AL.get(i).getPIN().equals(customer_pin))){
                customer_index = i; // change index to i
            }
        }
        if(customer_index == -1){
            System.out.println("\n Account not Found Or PIN Not Correct");
            return;
        }
        int customer_cur_indx = -1;
        String code = "";
        boolean wrong_cur = true;
        while(wrong_cur){   // continue loop until code currency is correct
            printCustomerCurrencies(customer_index);  // display all currencies that have it customer
            System.out.print("\nEnter customer currency code(or enter 'exit' to exit): ");
            code = input.next();

            if(code.equals("exit")) // check if user enter exist then will return back
                return;
            customer_cur_indx = -1;
            // check if code currency existed for customer account is exist
            for(int i = 0; i< AL.get(customer_index).curr.size(); i++){
                if(code.toUpperCase().equals(AL.get(customer_index).curr.get(i).getCode()))
                    customer_cur_indx = i; // change index to i
            }
            if(customer_cur_indx != -1){
                wrong_cur = false;  // code currency is valid then change value to false
            }else{
                System.out.println("\n Customer's currency not Found..try again");
            }
        }
        if("withdraw".equals(type))  // check if type of methode is withdraw
            System.out.print("\nAmount to be Withdrawn: ");
        else    // otherwise is remittance
            System.out.print("\nAmount to be Remittance: ");
        double amount = input.nextDouble();  // enter amount that you want to Withdrawn or Remittance

        try{
            if(amount > 0){
                double old_cust_amount = AL.get(customer_index).curr.get(customer_cur_indx).getAmount();
                if("withdraw".equals(type)){// check if type of methode is withdraw
                    if(old_cust_amount >= amount){ // check if balance customer is greater or equal than a given amount
                        // customer balance will reduced
                        AL.get(customer_index).curr.get(customer_cur_indx).setAmount(old_cust_amount - amount );
                        System.out.println("\nAmount Withdrawn Successfully....... ");
                        return;
                    }
                    else {
                        System.out.println("\nThis person doesn't have this much balance in his account");
                        return;
                    }
                }else{  // otherwise is remittance
                    // customer balance will increased
                    AL.get(customer_index).curr.get(customer_cur_indx).setAmount(old_cust_amount + amount );
                    System.out.println("\nAmount Withdrawn Successfully....... ");
                    return;
                }
            }else{
                throw new MyException("Amount cannot be negative");
            }
        }catch(MyException e){
            System.out.println(e.getMessage());
            System.out.println("TRANSACTION FAILURE!!");
        }
    }
    // print information of all customers
//    @Override
    public void print(){
        if(AL.size() < 1){
            System.out.println("\nThere is no account...");
            return;
        }
        for(int i = 0; i<AL.size(); i++){   // loop on all array list
            System.out.println("************************** "+(i+1)+" ******************************");
            System.out.print("Name: " + AL.get(i).getName()+" , ");
            System.out.println("Account Number: " + AL.get(i).getAccountNo());
            System.out.print("Gender: " + AL.get(i).getGender()+" , ");
            System.out.print("Email: " + AL.get(i).getEmail()+" , ");
            System.out.println("Address: " + AL.get(i).getAddress());
            AL.get(i).print();  // called method print of currency
        }
    }
    // print information of a specific customer
    public void printCust(){
        if(AL.size() < 1){
            System.out.println("\nThere is no account...");
            return;
        }
        Scanner input = new Scanner(System.in);
        String customer_acc; // customer account
        do{ // continue loop if digit account number less than 8
            System.out.print("\nEnter customer's 8 digit account number: ");
            customer_acc = input.next();
        }while(customer_acc.length() < 8);
        boolean found = false;
        for(int i = 0; i<AL.size(); i++){   // loop on all array list to find account customer
            if(customer_acc.equals(AL.get(i).getAccountNo())){
                System.out.print("Name: " + AL.get(i).getName()+" , ");
                System.out.println("Account Number: " + AL.get(i).getAccountNo());
                AL.get(i).print();  // called method print of currency
                found = true;
                break;
            }
        }
        if(!found)
            System.out.println("\nThere is no customer with account number "+customer_acc);
    }
    // print customer currencies 
    public void printCustomerCurrencies(int indx){
        System.out.print("This account have the following currencies: ");
        for(int i = 0; i<AL.get(indx).curr.size(); i++){ // loop on customer currencies
            System.out.print(AL.get(indx).curr.get(i).getCode()+" , ");
        }
    }
    // load data from files(BankRecord.txt, BankCurrencies.txt) to array list of (accounts, currencies)
    public void load(){
        try{
            // to read from BankRecord.txt file
            Scanner read_acc = new Scanner(new File("BankRecord.txt"));
            while(read_acc.hasNext()){  // read until end of file
                // each line read to variables
                String name = read_acc.next();
                String gender = read_acc.next();
                String email = read_acc.next();
                String address = read_acc.next();
                String account_no = read_acc.next();
                String pin = read_acc.next();

                // to read from BankCurrencies.txt file
                Scanner read_curr = new Scanner(new File("BankCurrencies.txt"));
                ArrayList<Currency> AL_cur = new ArrayList<>(); // define new array list of currency object
                while(read_curr.hasNext()){ // read until end of file
                    // each line read to variables
                    String account_no2 = read_curr.next();
                    String code = read_curr.next();
                    double amount = Double.parseDouble(read_curr.next()); // convert to double
                    if(account_no.equals(account_no2)){  // if account number is exist in BankCurrencies.txt file
                        Currency obj_cur = new Currency(account_no2, code, amount); // create new currency object
                        AL_cur.add(obj_cur);    // add currency object to array list
                    }
                }
                read_curr.close();  // close variable read BankCurrencies.txt file
                Account obj_account = new Account(name, gender, email, address, account_no, pin, AL_cur); // create new account object
                AL.add(obj_account);    // add account object to array list
            }   
            read_acc.close();   // close variable read BankRecord.txt file
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    // save data from array list of (accounts, currencies) to files(BankRecord.txt, BankCurrencies.txt)
    public void save(){
        try{
            // write to BankRecord.txt file
            PrintWriter writer_acc = new PrintWriter(new FileOutputStream("BankRecord.txt"));
            // write to BankCurrencies.txt file
            PrintWriter writer_curr = new PrintWriter(new FileOutputStream("BankCurrencies.txt"));
            for(int i = 0; i<AL.size(); i++){   // loop on array list of accounts
                // write elements array list of account to BankRecord.txt file
                writer_acc.print(AL.get(i).getName()+"  ");
                writer_acc.print(AL.get(i).getGender()+"  ");
                writer_acc.print(AL.get(i).getEmail()+"  ");
                writer_acc.print(AL.get(i).getAddress()+"  ");
                writer_acc.print(AL.get(i).getAccountNo()+"  ");
                writer_acc.print(AL.get(i).getPIN()+"  ");
                writer_acc.println();   // move to new line in file

                for(int j = 0; j<AL.get(i).curr.size(); j++){   // loop on array list of currencies
                    // if account number of account equal to account number of currencies
                    if(AL.get(i).getAccountNo().equals(AL.get(i).curr.get(j).getAccount_no())){
                        // write elements array list of currencies to BankCurrencies.txt file
                        writer_curr.print(AL.get(i).curr.get(j).getAccount_no()+"  ");
                        writer_curr.print(AL.get(i).curr.get(j).getCode()+"  ");
                        writer_curr.println(AL.get(i).curr.get(j).getAmount());
                    }
                }
            }
            writer_curr.close();   // close variable write BankCurrencies.txt file
            writer_acc.close();   // close variable write BankRecord.txt file
        }
        catch(Exception e){
            System.out.println("\nError Saving Data to File");
        }
    }
}


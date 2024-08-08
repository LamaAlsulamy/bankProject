/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bank;

import java.util.ArrayList;

//  customer account may have one or more currency
public class Account extends Customer{
//    private String name;
    private String account_no;
    private String pin;
    // every account can have more than one object of currency
    // array list of object currency
    ArrayList<Currency> curr = null;

    // no-arguments constructor
    Account(){
        super();
        account_no = "0";
        pin = null;
        // default currency constructor
        curr = new ArrayList<Currency>(){
            {
                add(new Currency());
            };
        };
    }
    // with arguments constructor
    Account(String name, String gender, String email, String address, String account_no, String pin, ArrayList<Currency> curr){
        super(name, gender, email, address);
        this.account_no = account_no;
        this.pin = pin;
        this.curr = curr;
    }

    // getter and setter of attributes
//    public void setName(String name){this.name = name;}
    public void setAccountNo(String n){account_no = n;}
    public void setPIN(String pin){this.pin = pin;}
    public void setCurr(ArrayList<Currency> curr) {this.curr = curr;}
//    public String getName(){return name;}
    public String getAccountNo(){return account_no;}
    public String getPIN(){return pin;}
    public ArrayList<Currency> getCurr() {return curr;}

    //  print the ammount of all currencies customer
    public void print(){
        System.out.println("The Amount:");
        for(int i = 0; i<curr.size(); i++){
            System.out.print(curr.get(i).getAmount()+" "+curr.get(i).getCode()+" , ");
            if((i+1)%3 == 0)    // for format printing
                System.out.println();
        }
        System.out.println();
    }
}

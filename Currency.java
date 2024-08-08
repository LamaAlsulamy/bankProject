/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bank;

/* 
    currency contains:- 
    1- code: (ex. SAR), 
    2- account number: to linked with BankRecords file,
    3- amount: amount of currency

*/
public class Currency {
   private String account_no;
    private String code;    // code of currency SAR or USD
    private double amount;
    public Currency() { // no-arguments constructor
        account_no = "0";
        amount = 0.0;
        code = "SAR"; // default currency is Saudi Arabia Riyal
    }
    public Currency(String account_no, String code, double amount) {
        // with arguments constructor
        this.account_no = account_no;
        this.code = code;
        this.amount = amount;
    }
    
    //  getter and setter of attributes
    public String getAccount_no() {return account_no;}
    public String getCode() {return code;}
    public double getAmount() {return amount;}
    public void setAccount_no(String account_no) {this.account_no = account_no;}
    public void setCode(String code) {this.code = code;}
    public void setAmount(double amount) {this.amount = amount;}
}


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package bank;

import java.util.Scanner;

class BankManagementSystem{
    public static void main(String args[]){
	Bank obj = new Bank();
	obj.load(); // load data from files to object
	try{	
            Scanner input = new Scanner(System.in);
            System.out.println("\n**********************Welcome to our Bank*************************");
            int choice = 0;
            if(obj.AL.size() <= 0){ // if there is no account in bank you must create new account
                System.out.println("\nThere is no account you must Create new Account");
                obj.addNewRecord();
                obj.save();
                System.out.println("\nAccount Created Successfully");
            }
            while(choice != 7){
		System.out.println("\n1 - Create new Account");
                System.out.println("2 - Transfer money from an existing account to another existing account");
                System.out.println("3 - Withdraw money from existing account");
                System.out.println("4 - Remittance money to existing account");
                System.out.println("5 - Printing");
                System.out.println("6 - Add new currency to customer");
                System.out.println("7 - Exit");
		System.out.print("Enter your choice: ");
		choice = input.nextInt();

		if(choice == 1){
                    obj.addNewRecord();
			System.out.println("\nAccount Created Successfully");
		}
		else if(choice == 2){
                    obj.transfer();
		}
		else if(choice == 3){
                    obj.withdrawOrRemittance("withdraw");
		}
		else if(choice == 4){
                    obj.withdrawOrRemittance("remittance");
		}
		else if(choice == 5){
                    int choice2 = 0;
                        System.out.println("\n1 - Print details about specific customer");
                        System.out.println("2 - Print all details customers");
                        System.out.println("3 - Back");
                        System.out.print("Enter your choice: ");
                        choice2 = input.nextInt();
                        if(choice2 == 1)
                            obj.printCust();
                        else if(choice2 == 2)
                            obj.print();
                        else
                            System.out.println("\nWrong Input");
                }
		else if(choice == 6){
                    obj.addCustCurr();
                }
		else if(choice == 7){
                    System.out.println("\nData saved to File \"BankRecord.txt and BankCurrencies.txt\"");
		}
		else
                    System.out.println("\nWrong Input");
                obj.save();
            }
        }
        catch(Exception e){
            System.out.println("\nWe triggered an Error");
        }
        finally{
            obj.save();
        }  
    }
}

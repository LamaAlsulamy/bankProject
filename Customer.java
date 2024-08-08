/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bank;

public class Customer{
    private String name;
    private String gender;
    private String email;
    private String address;

    public Customer() {
        name = "";
        gender = "";
        email = "";
        address = "";
    }

    public Customer(String name, String gender, String email, String address) {
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.address = address;
    }

    public String getName() {return name;}
    public String getGender() {return gender;}
    public String getEmail() {return email;}
    public String getAddress() {return address;}

    public void setName(String name) {this.name = name;}
    public void setGender(String gender) {this.gender = gender;}
    public void setEmail(String email) {this.email = email;}
    public void setAddress(String address) {this.address = address;}

    @Override
    public String toString() {
        return "Customer{" + "name=" + name + ", gender=" + gender + ", email=" + email + ", address=" + address + "}";
    }
    
}


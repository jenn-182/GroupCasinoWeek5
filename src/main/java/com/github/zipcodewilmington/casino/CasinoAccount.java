package com.github.zipcodewilmington.casino;

/**
 * Created by leon on 7/21/2020.
 * `ArcadeAccount` is registered for each user of the `Arcade`.
 * The `ArcadeAccount` is used to log into the system to select a `Game` to play.
 */
public class CasinoAccount {
    private Double balance;
    private String username;
    private String password;

    CasinoAccount(Double initBalance, String username, String password){
        balance = initBalance;
        this.username = username;
        this.password = password;
    }
    public Double getBalance(){
        return balance;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String password){
        if(this.password.equals(password))
            return true;
        return false;
    }

    public boolean resetPassword(String currentPassword, String newPassword){
        if(checkPassword(currentPassword)){
            password = newPassword;
            System.out.println("Current password is correct, password has changed");
            return true;
        }else{
            System.out.println("Current password is incorrect, did not change password");
        }
        return false;
    }

    public void deposit(double deposit){
        balance+=deposit;
    }

    public void withdraw(double ammount){
        balance-=ammount;
    }


}

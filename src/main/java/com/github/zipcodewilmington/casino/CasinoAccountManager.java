package com.github.zipcodewilmington.casino;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by leon on 7/21/2020.
 * `Casino Account Manager` stores, manages, and retrieves `Casino Account` objects
 * it is advised that every instruction in this class is logged
 */
public class CasinoAccountManager {

    private Map<String, CasinoAccount> accounts;

    //Constructs a new CasinoAccountManager with an empty account list
    public CasinoAccountManager() {
        this.accounts = new HashMap<>();
    }


    //Load all Casino Account objects
    public Map<String, CasinoAccount> loadAccounts() {
        return accounts;
    }
    /**
     * @param accountName     name of account to be returned
     * @param accountPassword password of account to be returned
     * @return `CasinoAccount` with specified `accountName` and `accountPassword`
     */
    public CasinoAccount getAccount(String accountName, String accountPassword) {
        String currentMethodName = new Object(){}.getClass().getEnclosingMethod().getName();
        String currentClassName = getClass().getName();
        String errorMessage = "Method with name [ %s ], defined in class with name [ %s ] has  not yet been implemented";
        throw new RuntimeException(String.format(errorMessage, currentMethodName, currentClassName));
    }

    /**
     * logs & creates a new `CasinoAccount`
     *
     * @param accountName     name of account to be created
     * @param accountPassword password of account to be created
     * @return new instance of `CasinoAccount` with specified `accountName` and `accountPassword`
     */
    public CasinoAccount createAccount(String accountName, String accountPassword) {
        String currentMethodName = new Object(){}.getClass().getEnclosingMethod().getName();
        String currentClassName = getClass().getName();
        String errorMessage = "Method with name [ %s ], defined in class with name [ %s ] has  not yet been implemented";
        throw new RuntimeException(String.format(errorMessage, currentMethodName, currentClassName));
    }

    /**
     * logs & registers a new `CasinoAccount` to `this.getCasinoAccountList()`
     *
     * @param casinoAccount the casinoAccount to be added to `this.getCasinoAccountList()`
     */
    public void registerAccount(CasinoAccount casinoAccount) {
        String currentMethodName = new Object(){}.getClass().getEnclosingMethod().getName();
        String currentClassName = getClass().getName();
        String errorMessage = "Method with name [ %s ], defined in class with name [ %s ] has  not yet been implemented";
        throw new RuntimeException(String.format(errorMessage, currentMethodName, currentClassName));
    }
}

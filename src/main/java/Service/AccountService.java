package Service;

import DAO.AccountDAO;
import Model.Account;
import Model.Message;

public class AccountService {
    AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account registerAccount(Account account) {
        Account a = accountDAO.getAccountByUsername(account.getUsername());
        if (a != null | account.getPassword().length() < 4 | account.getUsername().equals("")) {
            return null;
        }
        return accountDAO.registerAccount(account);
    }

    public Account loginAccount(Account account) {
        String username = account.getUsername();
        String password = account.getPassword();
        Account a = accountDAO.authenticanAccount(username, password);
        return a;
    }

    
}

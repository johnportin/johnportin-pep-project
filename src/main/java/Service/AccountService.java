package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account registerAccount(Account account) {
        Account a = accountDAO.getAccountByUsername(account);
        if (a == null | account.getPassword().length() < 4 | account.getUsername().equals("")) {
            return null;
        }
        return accountDAO.registerAccount(account);
    }
}

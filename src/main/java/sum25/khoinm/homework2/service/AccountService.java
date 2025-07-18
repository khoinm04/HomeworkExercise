package sum25.khoinm.homework2.service;

import sum25.khoinm.homework2.pojo.Account;
import java.util.List;
import java.util.Optional;

public interface AccountService {
    Account isAccountExist(String email, String password) throws Exception;
    Optional<Account> findByEmail(String email);
    Account saveAccount(Account account) throws Exception;
    boolean existsByEmail(String email);
    List<Account> getAllAccounts();
    Optional<Account> getAccountById(Long id);
    void deleteAccount(Long id);
    Account updateAccount(Account account);
}
package sum25.khoinm.homework2.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import sum25.khoinm.homework2.pojo.Account;
import sum25.khoinm.homework2.repository.AccountRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Configuration
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account isAccountExist(String email, String password) throws Exception{
        // Kiểm tra input
        if (email == null || email.trim().isEmpty()) {
            throw new Exception("Email không được để trống");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new Exception("Mật khẩu không được để trống");
        }
        // Kiểm tra tài khoản có tồn tại với email và mật khẩu
        Optional<Account> accountOptional = accountRepository.findByEmailAndPassword(email, password);
        return accountOptional.orElse(null);
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public Account saveAccount(Account account) throws Exception {
        // Kiểm tra input
        if(accountRepository.existsByEmail(account.getEmail())){
            throw new Exception("Email đã tồn tại");
        }
        return accountRepository.save(account);
    }

    @Override
    public boolean existsByEmail(String email) {
        return accountRepository.existsByEmail(email);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Account updateAccount(Account account) {
        return accountRepository.save(account);
    }
}

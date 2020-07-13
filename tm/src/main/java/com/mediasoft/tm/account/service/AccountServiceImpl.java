package com.mediasoft.tm.account.service;

import com.mediasoft.tm.account.dto.AccountDto;
import com.mediasoft.tm.account.mapper.AccountMapper;
import com.mediasoft.tm.account.model.Account;
import com.mediasoft.tm.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final AccountMapper accountMapper;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository,
                              AccountMapper accountMapper,
                              PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Получение аккаунта по его id.
     *
     * @param accountId
     */
    @Override
    public AccountDto getById(Long accountId) {
        var account = this.accountRepository
                .findById(accountId)
                .orElseGet(Account::new);
        return accountMapper.toDto(account);
    }

    /**
     * Получение аккаунта по его email.
     *
     * @param email
     */
    @Override
    public AccountDto getByEmail(String email) {
        var account = this.accountRepository
                .findByEmail(email)
                .orElseGet(Account::new);
        return accountMapper.toDto(account);
    }

    /**
     * Создание аккаунта.
     *
     * @param accountDto
     */
    public void create(AccountDto accountDto) {
        if (isNickNotUniq(accountDto.getNick())) {
            throw new RuntimeException("Account with nick: " + accountDto.getNick()
                    + " already exists.");
        }
        if (isEmailNotUniq(accountDto.getEmail())) {
            throw new RuntimeException("Account with email: " + accountDto.getEmail()
                    + " already exists.");
        }

        var account = this.createNewFrom(accountDto);
        this.accountRepository.save(account);
    }

    /**
     * Обновление аккаунта.
     *
     * @param accountDto
     */
    public void update(Long accountId, AccountDto accountDto) {
        var account = this.accountRepository
                .findById(accountId)
                .orElseThrow(() -> new RuntimeException(
                                "Account with id=" + accountId +
                                        " not exists")
                );

        if (isNickNotUniq(accountDto.getNick(), accountId)) {
            throw new RuntimeException("Account with nick: " + accountDto.getNick()
                    + " already exists.");
        }
        if (isEmailNotUniq(accountDto.getEmail(), accountId)) {
            throw new RuntimeException("Account with email: " + accountDto.getEmail()
                    + " already exists.");
        }

        account.setNick(accountDto.getNick());
        account.setPassword(accountDto.getPassword());

        this.accountRepository.save(account);
    }

    private boolean isNickNotUniq(String nick) {
        return accountRepository.existsAccountByNick(nick);
    }

    private boolean isEmailNotUniq(String email) {
        return accountRepository.existsAccountByEmail(email);
    }

    private boolean isNickNotUniq(String nick, Long id) {
        return accountRepository.existsAccountByNickAndIdIsNot(nick, id);
    }

    private boolean isEmailNotUniq(String email, Long id) {
        return accountRepository.existsAccountByEmailAndIdIsNot(email, id);
    }

    private Account createNewFrom(AccountDto accountDto) {
        return Account.builder()
                .id(accountDto.getId())
                .nick(accountDto.getNick())
                .email(accountDto.getEmail())
                .password(passwordEncoder.encode(accountDto.getPassword()))
                .build();
    }
}

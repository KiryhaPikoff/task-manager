package com.mediasoft.tm.account.repository;

import com.mediasoft.tm.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmail(String email);

    boolean existsAccountByNick(String nick);

    boolean existsAccountByNickAndIdIsNot(String nick, Long accountId);

    boolean existsAccountByEmail(String email);

    boolean existsAccountByEmailAndIdIsNot(String email, Long accountId);
}

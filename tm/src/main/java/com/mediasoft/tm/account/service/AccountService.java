package com.mediasoft.tm.account.service;

import com.mediasoft.tm.account.dto.AccountDto;

public interface AccountService {

    /**
     * Получение аккаунта по его id.
     * @param accountId
     */
    AccountDto getById(Long accountId);

    /**
     * Получение аккаунта по его email.
     * @param email
     */
    AccountDto getByEmail(String email);

    /**
     * Создание аккаунта.
     * @param accountDto
     */
    void create(AccountDto accountDto);

    /**
     * Обновление аккаунта.
     * @param accountDto
     */
    void update(Long accountId, AccountDto accountDto);
}

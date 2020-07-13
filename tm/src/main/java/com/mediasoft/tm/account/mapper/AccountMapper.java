package com.mediasoft.tm.account.mapper;

import com.mediasoft.tm.account.dto.AccountDto;
import com.mediasoft.tm.account.model.Account;

public interface AccountMapper {

    AccountDto toDto(Account account);
}

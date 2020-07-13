package com.mediasoft.tm.account.mapper;

import com.mediasoft.tm.account.dto.AccountDto;
import com.mediasoft.tm.account.model.Account;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AccountMapperImpl implements AccountMapper {

    @Override
    public AccountDto toDto(Account account) {
        return Objects.isNull(account) ? null :
                AccountDto.builder()
                        .id(account.getId())
                        .nick(account.getNick())
                        .email(account.getEmail())
                        .password(account.getPassword())
                        .build();
    }
}

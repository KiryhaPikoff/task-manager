package com.mediasoft.tm.account.controller;

import com.mediasoft.tm.account.dto.AccountDto;
import com.mediasoft.tm.account.producer.AccountRegistrationProducer;
import com.mediasoft.tm.account.producer.dto.RegistrationDataDto;
import com.mediasoft.tm.account.service.AccountService;
import lombok.AllArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class AccountController {

    private final AccountService accountService;

    private final AccountRegistrationProducer accountRegistrationProducer;

    @GetMapping("/teststringendpoint/{num}")
    public ResponseEntity<String> testStringEndpoint(@PathVariable Long num) {
        return new ResponseEntity<>("it works! with num = " + num, HttpStatus.OK);
    }

    @GetMapping("/testaccountendpoint")
    public ResponseEntity<AccountDto> testAccountEndpoint() {
        var account = AccountDto.builder()
                .id(9999L)
                .email("testEmail@mail.ru")
                .nick("testNick")
                .password("testPW")
                .build();
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @GetMapping("/{accountId}")
    @PreAuthorize("@authDecider.canGetAccount(authentication, #accountId)")
    public ResponseEntity<AccountDto> getById(@PathVariable Long accountId) {
        var account = accountService.getById(accountId);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(HttpServletRequest request,
                                 @RequestBody @Valid AccountDto accountDto) {
        accountService.create(accountDto);

        var registrationDataDto = RegistrationDataDto.builder()
                .ip(request.getRemoteAddr())
                .nick(accountDto.getNick())
                .email(accountDto.getEmail())
                .password(accountDto.getPassword())
                .build();
        accountRegistrationProducer.sendNewAccountInfo(registrationDataDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{accountId}")
    @PreAuthorize("@authDecider.canGetAccount(authentication, #accountId)")
    public ResponseEntity update(@PathVariable Long accountId,
                                 @RequestBody @Valid AccountDto accountDto) {
        accountService.update(accountId, accountDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

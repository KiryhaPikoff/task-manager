package com.mediasoft.tm.account.controller;

import com.mediasoft.tm.account.dto.AccountDto;
import com.mediasoft.tm.account.service.AccountService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class AccountController {

    private final AccountService accountService;

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @GetMapping("/{accountId}")
    @PreAuthorize("@authDecider.canGetAccount(authentication, #accountId)")
    public ResponseEntity<AccountDto> getById(@PathVariable Long accountId) {
        var account = accountService.getById(accountId);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid AccountDto accountDto) {
        accountService.create(accountDto);
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

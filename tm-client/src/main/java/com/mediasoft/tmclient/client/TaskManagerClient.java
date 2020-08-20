package com.mediasoft.tmclient.client;

import com.mediasoft.tmclient.account.AccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "task-manager")
public interface TaskManagerClient {

    /**
     * Bearer token can be obtained from "Authorization" header.
     */
    @PostMapping("/login")
    ResponseEntity<Void> authenticate(@RequestBody Credentials credentials);

    @GetMapping("/accounts/{accountId}")
    AccountDto getAccountInfo(@PathVariable Long accountId,
                              @RequestHeader("Authorization") String token);
}
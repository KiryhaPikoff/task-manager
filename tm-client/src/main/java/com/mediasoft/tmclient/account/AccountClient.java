package com.mediasoft.tmclient.account;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "task-manager")
public interface AccountClient {

    @RequestMapping(method = RequestMethod.GET, value = "/accounts/teststringendpoint/{num}")
    String getTestString(@PathVariable Long num);
}
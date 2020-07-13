package com.mediasoft.tm.config.security.authentication;

import com.mediasoft.tm.account.repository.AccountRepository;
import com.mediasoft.tm.config.security.aurhorization.ContributionGrantedAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Autowired
    public UserDetailsServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var account = accountRepository.findByEmail(email);
        if (account.isEmpty()) {
            throw new UsernameNotFoundException(email);
        }
        var authorities = account.get().getContributors().stream()
                .map(contributor -> ContributionGrantedAuthority.builder()
                        .projectId(contributor.getProject().getId())
                        .role(contributor.getRole())
                        .build()
                ).collect(Collectors.toList());
        var u =new User(
                account.get().getEmail(),
                account.get().getPassword(),
                authorities);
        return u;
    }
}
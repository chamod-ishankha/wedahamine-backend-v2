package org.bytecub.WedahamineBackend.service.impl.master;

import org.bytecub.WedahamineBackend.dao.master.WHMUserDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class WedahamineUserDetailsService implements UserDetailsService {

    private final WHMUserDao userDao;

    public WedahamineUserDetailsService(WHMUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.findByEmail(username).orElseThrow();
    }
}

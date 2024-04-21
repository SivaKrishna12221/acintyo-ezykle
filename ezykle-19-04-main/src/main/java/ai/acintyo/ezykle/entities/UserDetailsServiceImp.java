package ai.acintyo.ezykle.entities;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import ai.acintyo.ezykle.repositories.UserRegistrationRepo;

@Component
public class UserDetailsServiceImp implements UserDetailsService{

	 private final UserRegistrationRepo repo;

	    public UserDetailsServiceImp(UserRegistrationRepo repo) {
	        this.repo = repo;
	    }
	    
	    @Override
	    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	        return repo.findByEmail(email)
	                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
	    }
}
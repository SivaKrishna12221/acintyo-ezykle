package ai.acintyo.ezykle.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ai.acintyo.ezykle.bindings.AuthenticationResponse;
import ai.acintyo.ezykle.bindings.UserRegistrationForm;
import ai.acintyo.ezykle.entities.EzUserAccount;
import ai.acintyo.ezykle.entities.EzUserRegistration;
import ai.acintyo.ezykle.entities.Token;
import ai.acintyo.ezykle.exception.DataNotFoundException;
import ai.acintyo.ezykle.jwtservice.JwtService;
import ai.acintyo.ezykle.repositories.TokenRepository;
import ai.acintyo.ezykle.repositories.UserRegistrationRepo;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserRegistrationImpl implements UserRegistrationService {

//	@Autowired
//	EncodingData encodingData;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	UserRegistrationRepo registrationRepo;

	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public AuthenticationResponse authenticate(UserRegistrationForm registrationForm) {

		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(registrationForm.getEmail(), registrationForm.getPassword()));

		EzUserRegistration user = registrationRepo.findByEmail(registrationForm.getEmail()).orElseThrow();

		String jwt = jwtService.generateToken(user);

		System.out.println(user + jwt);

		revokeAllTokenByUser(user);
		saveUserToken(jwt, user);

		return new AuthenticationResponse(jwt, "User login was successful");

	}

	private void revokeAllTokenByUser(EzUserRegistration user) {
		List<Token> validTokens = tokenRepository.findAllTokensByUserId(user.getId());
		if (validTokens.isEmpty()) {
			return;
		}

		validTokens.forEach(t -> {
			t.setLoggedOut(true);
		});

		tokenRepository.saveAll(validTokens);
	}

	private void saveUserToken(String jwt, EzUserRegistration user) {
		Token token = new Token();
		token.setToken(jwt);
		token.setLoggedOut(false);
		token.setUser(user);
		tokenRepository.save(token);
	}

	@Override
	public AuthenticationResponse saveRegistration(UserRegistrationForm registrationForm) {

		if (registrationRepo.findByEmail(registrationForm.getEmail()).isPresent()) {
			return new AuthenticationResponse(null, "User already exist");
		}

		EzUserRegistration ezUserRegistration = new EzUserRegistration();

		ezUserRegistration.setName(registrationForm.getName());
		ezUserRegistration.setMobile(registrationForm.getMobileNumber());
		ezUserRegistration.setEmail(registrationForm.getEmail());
		ezUserRegistration.setPassword(passwordEncoder.encode(registrationForm.getPassword()));
		ezUserRegistration.setConfirmPassword(passwordEncoder.encode(registrationForm.getConfirmPassword()));
		ezUserRegistration.setRegistrationDate(LocalDate.now());

		EzUserAccount ezUserAccount = new EzUserAccount();

		ezUserAccount.setBankName(passwordEncoder.encode(registrationForm.getBankName()));
		ezUserAccount.setAccountNumber(passwordEncoder.encode(registrationForm.getAccountNumber()));
		ezUserAccount.setIfscCode(passwordEncoder.encode(registrationForm.getIfscCode()));
		ezUserAccount.setBranch(passwordEncoder.encode(registrationForm.getAccountNumber()));
		ezUserAccount.setRegistrationDate(LocalDate.now());

		ezUserRegistration.setUserAccount(ezUserAccount);
		try {
			log.info("ai.acintyo.ezykle.services.UserRegistrationImpl::Successfully registered user: {}");

			registrationRepo.save(ezUserRegistration);

		} catch (Exception e) {
			log.error("ai.acintyo.ezykle.services.UserRegistrationImpl::Failed to register user: {}, Error: {}",
					e.getMessage(), e);
			throw new RuntimeException("{user.registration.saveError}", e);
		}

		String jwt = jwtService.generateToken(ezUserRegistration);
		saveUserToken(jwt, ezUserRegistration);
		return new AuthenticationResponse(jwt, "User registration was successful");

	}

	@Override
	public List<EzUserRegistration> fetchAllUsers(Pageable pageable) {
		// TODO Auto-generated method stub
		log.info("ai.acintyo.ezykle.services.UserRegistrationImpl:: fetch all User method executed:");
		Page<EzUserRegistration> page = registrationRepo.findAllUsers(pageable);
		if (page.isEmpty()) {
			throw new DataNotFoundException("Data not found in find all method.");
		} else {
			return page.getContent();
		}

	}

	@Override
	public EzUserRegistration fetchUserById(Integer id) {

		Optional<EzUserRegistration> opt = registrationRepo.findById(id);

		if (opt.isEmpty()) {
			throw new IllegalArgumentException("No data found with this id:" + id);
		} else {
			return opt.get();
		}
	}

	@Override
	public EzUserRegistration UpdateUserById(Integer id, UserRegistrationForm registrationForm) {

		EzUserRegistration existUser = registrationRepo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("User not found "));

		existUser.setName(registrationForm.getName());
		existUser.setMobile(registrationForm.getMobileNumber());
		existUser.setEmail(registrationForm.getEmail());
		existUser.setPassword(registrationForm.getPassword());
		existUser.setConfirmPassword(registrationForm.getConfirmPassword());
		EzUserAccount existUserAccount = existUser.getUserAccount();
		if (existUserAccount == null) {
			existUserAccount = new EzUserAccount();
		}

	
		existUserAccount.setBankName(registrationForm.getBankName());
		existUserAccount.setAccountNumber(registrationForm.getAccountNumber());
		existUserAccount.setIfscCode(registrationForm.getIfscCode());
		existUserAccount.setBranch(registrationForm.getAccountNumber());
		existUser.setUserAccount(existUserAccount);

		return registrationRepo.save(existUser);
	}

	@Override
	public String deleteUserById(Integer id) {
		// TODO Auto-generated method stub

		EzUserRegistration existUser = registrationRepo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("User not found by given Id:" + id));
		registrationRepo.delete(existUser);
		return "User is deleted";
	}
}

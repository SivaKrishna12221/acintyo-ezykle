package ai.acintyo.ezykle.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.acintyo.ezykle.bindings.UserRegistrationForm;
import ai.acintyo.ezykle.entities.EzUserAccount;
import ai.acintyo.ezykle.entities.EzUserRegistration;
import ai.acintyo.ezykle.repositories.UserRegistrationRepo;
import ai.acintyo.ezykle.util.EncodingData;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserRegistrationImpl implements UserRegistrationService {

	@Autowired
	EncodingData encodingData;

	@Autowired
	UserRegistrationRepo registrationRepo;

	@Override
	public EzUserRegistration saveRegistration(UserRegistrationForm registrationForm) {

		EzUserRegistration ezUserRegistration = new EzUserRegistration();

		ezUserRegistration.setName(registrationForm.getName());
		ezUserRegistration.setMobile(registrationForm.getMobileNumber());
		ezUserRegistration.setEmail(registrationForm.getEmail());
		ezUserRegistration.setPassword(registrationForm.getPassword());
		ezUserRegistration.setConfirmPassword(registrationForm.getConfirmPassword());
		ezUserRegistration.setRegistrationDate(LocalDate.now());

		EzUserAccount ezUserAccount = new EzUserAccount();

		String encodedBankName = encodingData.getEncodedData(registrationForm.getBankName());
		String encodedAccountNumber = encodingData.getEncodedData(registrationForm.getAccountNumber());
		String encodedIfscCode = encodingData.getEncodedData(registrationForm.getIfscCode());
		String encodedBranch = encodingData.getEncodedData(registrationForm.getAccountNumber());

		ezUserAccount.setBankName(encodedBankName);
		ezUserAccount.setAccountNumber(encodedAccountNumber);
		ezUserAccount.setIfscCode(encodedIfscCode);
		ezUserAccount.setBranch(encodedBranch);
		ezUserAccount.setRegistrationDate(LocalDate.now());

		ezUserRegistration.setUserAccount(ezUserAccount);

		try {
			log.info("ai.acintyo.ezykle.services.UserRegistrationImpl::Successfully registered user: {}");

			EzUserRegistration registration = registrationRepo.save(ezUserRegistration);
			return registration;

		} catch (Exception e) {
			log.error("ai.acintyo.ezykle.services.UserRegistrationImpl::Failed to register user: {}, Error: {}",
					e.getMessage(), e);
			throw new RuntimeException("{user.registration.saveError}", e);
		}

	}

}

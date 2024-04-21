package ai.acintyo.ezykle.controller;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.acintyo.ezykle.entities.EzUserRegistration;
import ai.acintyo.ezykle.entities.ForgotPassword;
import ai.acintyo.ezykle.model.ChangePassword;
import ai.acintyo.ezykle.model.MailBody;
import ai.acintyo.ezykle.repositories.ForgotPasswordRepository;
import ai.acintyo.ezykle.repositories.UserRegistrationRepo;
import ai.acintyo.ezykle.services.EmailService;

@RestController
@RequestMapping("/ezycle/forgotPassword")
public class ForgotPasswordController {

    private final EmailService emailService;
    private final UserRegistrationRepo userRegistrationRepository;
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final PasswordEncoder passwordEncoder;

    public ForgotPasswordController(EmailService emailService, UserRegistrationRepo userRegistrationRepository, ForgotPasswordRepository forgotPasswordRepository, PasswordEncoder passwordEncoder) {
        this.emailService = emailService;
        this.userRegistrationRepository = userRegistrationRepository;
        this.forgotPasswordRepository = forgotPasswordRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // send mail for email verification
    @PostMapping(value = "/verifyMail/{email}")
    public ResponseEntity<String> verifyEmail(@PathVariable String email) {
        EzUserRegistration user = userRegistrationRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Please provide an valid email!"));
        int otp = otpGenerator();
        MailBody mailBody = MailBody.builder()
                .to(email)
                .text("This is the OTP for your Forgot Password request: " + otp)
                .subject("OTP for Forgot Password Request")
                .build();

        ForgotPassword fp = ForgotPassword.builder()
                        .otp(otp)
                        .expirationTime(new Date(System.currentTimeMillis() + 70 * 1000))
                        .user(user)
                        .build();

        emailService.sendSimpleMessage(mailBody);

        forgotPasswordRepository.save(fp);

        return ResponseEntity.ok("Email sent for verification!");
    }


    // OTP verification
    @PostMapping("/verifyOtp/{otp}/{email}")
    public ResponseEntity<String> verifyOtp(@PathVariable Integer otp, @PathVariable String email) {
    	EzUserRegistration user = userRegistrationRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Please provide an valid email!"));

        ForgotPassword fp = forgotPasswordRepository.findByOtpAndUser(otp, user).orElseThrow(() -> new RuntimeException("Invalid OTP for email : " + email));

        if (fp.getExpirationTime().before(Date.from(Instant.now()))) {
            forgotPasswordRepository.deleteById(fp.getFpid());
            return new ResponseEntity<>("OTP has expired!", HttpStatus.EXPECTATION_FAILED);
        }

        return ResponseEntity.ok("OTP verified!");
    }

    // set the new password
    @PostMapping("/changePassword/{email}")
    public ResponseEntity<?> forgotPasswordHandler(@RequestBody ChangePassword changePassword,
                                                   @PathVariable String email) {
        if (!Objects.equals(changePassword.password(), changePassword.repeatPassword())) {
            return new ResponseEntity<>("Please enter the password again!", HttpStatus.EXPECTATION_FAILED);
        }

        String encodedPassword = passwordEncoder.encode(changePassword.password());

        userRegistrationRepository.updatePassword(email, encodedPassword);

        return ResponseEntity.ok("Password has been changed!");
    }


    private Integer otpGenerator() {
        Random random = new Random();
        return random.nextInt(100_000, 999_999);
    }
}

package PersonalBudget.business.user.domain.service;

import PersonalBudget.business.user.domain.model.UserAccountEntity;
import PersonalBudget.business.user.domain.service.exception.FailedToSendEmailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    private static final String VERIFICATION_LINK = "http://localhost:8080/api/v1/signup/confirmation?token=";
    private static final String RESET_LINK = "http://localhost:8080/api/v1/password/reset?token=";
    private static final String CONFIRMATION_SUBJECT_IN_POLISH = "Budżet Osobisty - Potwierdzenie konta użytkownika";
    private static final String CONFIRMATION_SUBJECT_IN_GERMAN = "Persönliches Budget - Benutzerkonto Bestätigung";
    private static final String CONFIRMATION_SUBJECT_IN_ENGLISH = "Personal Budget - User account confirmation";
    private static final String RESET_SUBJECT_IN_POLISH = "Budżet Osobisty - Reset hasła";
    private static final String RESET_SUBJECT_IN_GERMAN = "Persönliches Budget - Zurücksetzung des Passworts";
    private static final String RESET_SUBJECT_IN_ENGLISH = "Personal Budget - Password Reset";


    @Async
    public void sendEmail(String emailMessage, String recipient, String subject) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
            mimeMessageHelper.setText(emailMessage, true);
            mimeMessageHelper.setTo(recipient);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setFrom("personal-budget@outlook.com");
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("Failed to send an email", e);
            throw new FailedToSendEmailException("Failed to send an email");
        }
    }

    public String buildConfirmationAccountSubject() {
        String language = LocaleContextHolder.getLocale().getLanguage();

        return switch (language) {
            case "pl" -> CONFIRMATION_SUBJECT_IN_POLISH;
            case "de" -> CONFIRMATION_SUBJECT_IN_GERMAN;
            default -> CONFIRMATION_SUBJECT_IN_ENGLISH;
        };
    }

    public String buildConfirmationAccountEmail(UserAccountEntity userAccount) {
        String name = userAccount.getName();
        String token = userAccount.getAccountConfirmationToken();
        String link = VERIFICATION_LINK + token;

        String language = LocaleContextHolder.getLocale().getLanguage();

        return switch (language) {
            case "pl" -> prepareConfirmationEmailMessageInPolish(name, link);
            case "de" -> prepareConfirmationEmailMessageInGerman(name, link);
            default -> prepareConfirmationEmailMessageInEnglish(name, link);
        };
    }

    public String buildResetPasswordSubject() {
        String language = LocaleContextHolder.getLocale().getLanguage();

        return switch (language) {
            case "pl" -> RESET_SUBJECT_IN_POLISH;
            case "de" -> RESET_SUBJECT_IN_GERMAN;
            default -> RESET_SUBJECT_IN_ENGLISH;
        };
    }

    public String buildResetPasswordEmail(String token) {
        String link = RESET_LINK + token;

        String language = LocaleContextHolder.getLocale().getLanguage();

        return switch (language) {
            case "pl" -> preparePasswordResetEmailMessageInPolish(link);
            case "de" -> preparePasswordResetEmailMessageInGerman(link);
            default -> preparePasswordResetEmailMessageInEnglish(link);
        };
    }

    public String prepareConfirmationEmailMessageInPolish(String name, String link) {
        return "<p>Cześć " + name + "!</p>" +
                "<p>Cieszymy się, że jesteś z nami!</p>" +
                "<p>Aby zakończyć rejestrację, kliknij proszę w poniższy link, aby zweryfikować swój adres e-mail!</p>" +
                "<p>" + link + "</p>";
    }

    public String prepareConfirmationEmailMessageInGerman(String name, String link) {
        return "<p>Hallo " + name + "!</p>" +
                "<p>Wir freuen uns, Sie an Bord zu haben!</p>" +
                "<p>Um Ihre Registrierung abzuschließen, klicken Sie bitte auf den folgenden Link, um Ihre E-Mail-Adresse zu bestätigen!</p>" +
                "<p>" + link + "</p>";
    }

    public String prepareConfirmationEmailMessageInEnglish(String name, String link) {
        return "<p>Hello " + name + "!</p>" +
                "<p>We're excited to have you on board!</p>" +
                "<p>To complete your registration, kindly click on the following link to verify your email address!</p>" +
                "<p>" + link + "</p>";
    }

    public String preparePasswordResetEmailMessageInPolish(String link) {
        return "<p>Aby zresetować hasło, kliknij w poniższy link:</p>" +
                "<p>" + link + "</p>" +
                "<p>Jeśli nie zażądałeś resetu hasła, prosimy o zignorowanie tej wiadomości!</p>";
    }

    public String preparePasswordResetEmailMessageInGerman(String link) {
        return "<p>Um Ihr Passwort zurückzusetzen, klicken Sie bitte auf den folgenden Link:</p>" +
                "<p>" + link + "</p>" +
                "<p>Wenn Sie keine Zurücksetzung des Passworts angefordert haben, ignorieren Sie bitte diese E-Mail!</p>";
    }

    public String preparePasswordResetEmailMessageInEnglish(String link) {
        return "<p>To reset your password, please click on the following link:</p>" +
                "<p>" + link + "</p>" +
                "<p>If you did not request a password reset, please ignore this email!</p>";
    }
}

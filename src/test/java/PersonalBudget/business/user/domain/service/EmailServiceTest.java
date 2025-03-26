package PersonalBudget.business.user.domain.service;

import PersonalBudget.business.user.domain.service.exception.FailedToSendEmailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class EmailServiceTest {

    private static final String EMAIL_MESSAGE = "Test email content";
    private static final String RECIPIENT = "test@example.com";
    private static final String SUBJECT = "Test Subject";
    private static final String CONFIRMATION_SUBJECT_IN_POLISH = "Budżet Osobisty - Potwierdzenie konta użytkownika";
    private static final String CONFIRMATION_SUBJECT_IN_GERMAN = "Persönliches Budget - Benutzerkonto Bestätigung";
    private static final String CONFIRMATION_SUBJECT_IN_ENGLISH = "Personal Budget - User account confirmation";
    private static final String RESET_SUBJECT_IN_POLISH = "Budżet Osobisty - Reset hasła";
    private static final String RESET_SUBJECT_IN_GERMAN = "Persönliches Budget - Zurücksetzung des Passworts";
    private static final String RESET_SUBJECT_IN_ENGLISH = "Personal Budget - Password Reset";

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailService emailService;

    @Test
    void sendEmailTest() throws MessagingException {
        MimeMessage mimeMessage = mock(MimeMessage.class);
        MimeBodyPart mimePart = new MimeBodyPart();
        mimePart.setContent("Test message", "text/plain");

        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        emailService.sendEmail(EMAIL_MESSAGE, RECIPIENT, SUBJECT);

        verify(javaMailSender, times(1)).send(mimeMessage);
    }

    @Test
    void sendEmailTestThrowsMessagingExceptionTest()  {
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        doAnswer(invocation -> {
            throw new MessagingException("Simulated MessagingException");}).when(javaMailSender).send(mimeMessage);

        try {
            emailService.sendEmail(EMAIL_MESSAGE, RECIPIENT, SUBJECT);
        } catch (FailedToSendEmailException ex) {
            assert ex.getMessage().equals("Failed to send an email");
        }

        verify(javaMailSender, times(1)).send(mimeMessage);
    }

    @Test
    void buildConfirmationAccountSubjectInPolishTest() {
        try (MockedStatic<LocaleContextHolder> mockedLocale = Mockito.mockStatic(LocaleContextHolder.class)) {
            mockedLocale.when(LocaleContextHolder::getLocale).thenReturn(new Locale("pl"));

            String result = emailService.buildConfirmationAccountSubject();

            assertEquals(CONFIRMATION_SUBJECT_IN_POLISH, result);
        }
    }

    @Test
    void buildConfirmationAccountSubjectInGermanTest() {
        try (MockedStatic<LocaleContextHolder> mockedLocale = Mockito.mockStatic(LocaleContextHolder.class)) {
            mockedLocale.when(LocaleContextHolder::getLocale).thenReturn(new Locale("de"));

            String result = emailService.buildConfirmationAccountSubject();

            assertEquals(CONFIRMATION_SUBJECT_IN_GERMAN, result);
        }
    }

    @Test
    void buildConfirmationAccountSubjectInEnglishTest() {
        try (MockedStatic<LocaleContextHolder> mockedLocale = Mockito.mockStatic(LocaleContextHolder.class)) {
            mockedLocale.when(LocaleContextHolder::getLocale).thenReturn(new Locale("en"));

            String result = emailService.buildConfirmationAccountSubject();

            assertEquals(CONFIRMATION_SUBJECT_IN_ENGLISH, result);
        }
    }

    @Test
    void buildResetPasswordSubjectInPolishTest () {
        try (MockedStatic<LocaleContextHolder> mockedLocale = Mockito.mockStatic(LocaleContextHolder.class)) {
            mockedLocale.when(LocaleContextHolder::getLocale).thenReturn(new Locale("pl"));

            String result = emailService.buildResetPasswordSubject();

            assertEquals(RESET_SUBJECT_IN_POLISH, result);
        }
    }


    @Test
    void buildResetPasswordSubjectInGermanTest () {
        try (MockedStatic<LocaleContextHolder> mockedLocale = Mockito.mockStatic(LocaleContextHolder.class)) {
            mockedLocale.when(LocaleContextHolder::getLocale).thenReturn(new Locale("de"));

            String result = emailService.buildResetPasswordSubject();

            assertEquals(RESET_SUBJECT_IN_GERMAN, result);
        }
    }

    @Test
    void buildResetPasswordSubjectInEnglishTest () {
        try (MockedStatic<LocaleContextHolder> mockedLocale = Mockito.mockStatic(LocaleContextHolder.class)) {
            mockedLocale.when(LocaleContextHolder::getLocale).thenReturn(new Locale("en"));

            String result = emailService.buildResetPasswordSubject();

            assertEquals(RESET_SUBJECT_IN_ENGLISH, result);
        }
    }
}

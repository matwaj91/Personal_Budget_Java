package PersonalBudget.business.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserEmailDTO (

        @Email(message = "{email.valid}")
        @NotEmpty(message = "{email.empty}")
        String email ) {
}

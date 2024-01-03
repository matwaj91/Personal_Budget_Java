package PersonalBudget.business.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserDTO(

        @NotEmpty(message = "{name.empty}")
        String name,
        @Email(message = "{email.valid}")
        @NotEmpty(message = "{email.empty}")
        String email,
        @Size(min = 1, message = "{password.six}")
        String password) {
}

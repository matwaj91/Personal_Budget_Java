package PersonalBudget.business.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationDto extends UserDto{

    @NotEmpty(message = "{name.empty}")
    private String name;

    @Email(message = "{email.valid}")
    @NotEmpty(message = "{email.empty}")
    private String email;

    @Size(min = 6, message = "{password.six}")
    private String password;

}

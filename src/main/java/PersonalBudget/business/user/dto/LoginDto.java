package PersonalBudget.business.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto extends UserDto{

    @Email(message = "{email.valid}")
    @NotEmpty(message = "{email.empty}")
    private String email;

    @NotEmpty(message = "{password.empty}")
    private String password;

    public LoginDto() {
    }

    public LoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}

package PersonalBudget.business.user.dto;

import jakarta.validation.constraints.Size;

public record UserProfileDTO (

        String name,
        @Size(min = 6, message = "{password.six}")
        String password) {
}

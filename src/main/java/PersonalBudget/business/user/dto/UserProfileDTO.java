package PersonalBudget.business.user.dto;

import jakarta.validation.constraints.Size;

public record UserProfileDTO (

        String name,
        String password) {
}

package PersonalBudget.common.error;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record ErrorResponse(
        UUID uuid,
        LocalDateTime timestamp,
        String requestPath,
        String message,
        String details
) { }

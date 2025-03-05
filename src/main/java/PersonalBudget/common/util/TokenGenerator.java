package PersonalBudget.common.util;

import java.util.UUID;

public class TokenGenerator {

    public static String getGeneratedToken() {
        return UUID.randomUUID().toString();
    }
}

package PersonalBudget.business.income.domain.model;

import java.util.stream.Stream;

public enum DefaultCategoryEnum {

    SALARY,
    INTEREST,
    ALLEGRO,
    ANOTHER;

    public static Stream<DefaultCategoryEnum> stream() {
        return Stream.of(DefaultCategoryEnum.values());
    }
}

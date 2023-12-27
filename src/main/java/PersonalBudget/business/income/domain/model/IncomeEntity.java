package PersonalBudget.business.income.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "incomes")
public class IncomeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "income_seq")
    @SequenceGenerator(name = "income_seq",
            sequenceName = "income_seq",
            allocationSize = 1)
    @Column(columnDefinition = "serial")
    private Long id;

    private Long user_id;

    private Long income_category_assigned_to_user_id;

    private String amount;

    private String date_of_income;

    private String income_comment;
}

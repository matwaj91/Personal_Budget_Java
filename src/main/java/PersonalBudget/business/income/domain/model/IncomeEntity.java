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
@Table(name = "income")
public class IncomeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "income_seq")
    @SequenceGenerator(name = "income_seq", sequenceName = "income_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "income_category_assigned_to_user")
    private Long assignedIncomeCategory;

    @Column(name = "amount")
    private String amount;

    @Column(name = "date_of_income")
    private String incomeDate;

    @Column(name = "income_comment")
    private String incomeComment;
}

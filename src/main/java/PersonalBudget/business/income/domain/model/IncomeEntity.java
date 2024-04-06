package PersonalBudget.business.income.domain.model;

import PersonalBudget.business.user.domain.model.UserAccountEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "income")
public class IncomeEntity {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "income_sg")
    @SequenceGenerator(name = "income_sg", sequenceName = "income_sq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserAccountEntity userAccount;

    @NotNull
    @Column(name = "income_category_id")
    private Long incomeCategoryId;

    @ManyToOne
    @JoinColumn(name = "income_category_id", insertable = false, updatable = false)
    private IncomeCategoryEntity incomeCategory;

    @NotNull
    @Column(name = "amount")
    private BigDecimal amount;

    @NotNull
    @Column(name = "income_date")
    private LocalDate incomeDate;

    @Column(name = "income_comment")
    private String incomeComment;
}

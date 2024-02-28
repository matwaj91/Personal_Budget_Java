package PersonalBudget.business.expense.domain.model;

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
@Table(name = "expense")
public class ExpenseEntity {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "expense_sq")
    @SequenceGenerator(name = "expense_sq", sequenceName = "expense_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserAccountEntity userAccount;

    @NotNull
    @Column(name = "expense_category_id")
    private Long expenseCategoryId;

    @ManyToOne
    @JoinColumn(name = "expense_category_id", insertable = false, updatable = false)
    private ExpenseCategoryEntity expenseCategory;

    @NotNull
    @Column(name = "payment_method_id")
    private Long expensePaymentMethodId;

    @ManyToOne
    @JoinColumn(name = "payment_method_id", insertable = false, updatable = false)
    private ExpensePaymentMethodEntity expensePaymentMethod;

    @NotNull
    @Column(name = "amount")
    private BigDecimal amount;

    @NotNull
    @Column(name = "expense_date")
    private LocalDate expenseDate;

    @Column(name = "expense_comment")
    private String expenseComment;
}

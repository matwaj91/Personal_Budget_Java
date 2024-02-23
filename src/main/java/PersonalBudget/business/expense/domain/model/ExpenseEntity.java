package PersonalBudget.business.expense.domain.model;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "expense")
public class ExpenseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "expense_sq")
    @SequenceGenerator(name = "expense_sq", sequenceName = "expense_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

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
    @Column(name = "date_of_expense")
    private LocalDate expenseDate;

    @Column(name = "expense_comment")
    private String expenseComment;
}

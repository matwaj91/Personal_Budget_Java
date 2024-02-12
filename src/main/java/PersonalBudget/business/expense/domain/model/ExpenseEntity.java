package PersonalBudget.business.expense.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
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

    @ManyToOne //wiele expense moze byc przypisane do tej samej kategorii wydatku
    @JoinColumn(name = "expense_category_assigned_to_user")
    private ExpenseCategoryEntity expenseCategoryId;

    @Column(name = "payment_method_assigned_to_user")
    private Long expensePaymentMethodId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "date_of_expense")
    private LocalDate expenseDate;

    @Column(name = "expense_comment")
    private String expenseComment;
}

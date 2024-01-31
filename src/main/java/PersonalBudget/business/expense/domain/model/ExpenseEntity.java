package PersonalBudget.business.expense.domain.model;

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
@Table(name = "expense")
public class ExpenseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "expense_seq")
    @SequenceGenerator(name = "expense_seq", sequenceName = "expense_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "expense_category_assigned_to_user")
    private Long assignedExpenseCategory;

    @Column(name = "payment_method_assigned_to_user")
    private Long assignedPaymentMethod;

    @Column(name = "amount")
    private String amount;

    @Column(name = "date_of_expense")
    private String expenseDate;

    @Column(name = "expense_comment")
    private String expenseComment;
}

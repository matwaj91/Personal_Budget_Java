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
@Table(name = "incomes_category_assigned_to_users")
public class IncomeCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "category_seq")
    @SequenceGenerator(name = "category_seq",
                    sequenceName = "category_seq",
                    allocationSize = 1)
    @Column(columnDefinition = "serial") //INSERT statement can simply omit the id column
    private Long id;

    private Long user_id;

    private String name;
}

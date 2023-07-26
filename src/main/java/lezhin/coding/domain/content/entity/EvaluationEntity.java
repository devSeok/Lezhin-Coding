package lezhin.coding.domain.content.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "evaluation")
public class EvaluationEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evaluationId")
    @Id
    private Long id;
}

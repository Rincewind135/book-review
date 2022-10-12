package application.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(catalog = "review", name = "review")
@Builder
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bog_id", nullable = false)
    private Bog bog;

    @Column(name = "score", nullable = false)
    private int score;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "reviewAuthor", nullable = false)
    private String reviewAuthor;
}

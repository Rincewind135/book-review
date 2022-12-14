package application.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "review")
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

    @Column(name = "beskrivelse", nullable = false)
    private String beskrivelse;

    @Column(name = "reviewForfatter", nullable = false)
    private String reviewForfatter;
}

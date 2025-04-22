package Deepin.TripPlus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Table(name = "RATING")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Rating {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RATING_ID")
    private Long id;

    @Column(name = "SCORE")
    private Double score;

    @Column(name = "CREATE_DATE")
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "MODEL_NAME")
    private String modelName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;
}

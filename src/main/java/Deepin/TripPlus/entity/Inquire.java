package Deepin.TripPlus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Table(name = "INQUIRE")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Inquire {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="INQUIRE_ID")
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT" , columnDefinition = "TEXT")
    private String content;

    @Column(name = "CREATE_DATE")
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "IS_ANSWERED")
    private boolean isAnswered;

    @Column(name = "ANSWER", columnDefinition = "TEXT")
    private String answer;

    @Column(name = "ANSWERED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime answeredDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;



}

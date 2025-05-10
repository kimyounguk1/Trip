package Deepin.TripPlus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "NOTICE")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Notice implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTICE_ID")
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT", columnDefinition = "TEXT")
    private String content;

    @Column(name = "NOTICE_TYPE")
    private String noticeType;

    @Column(name = "CREATE_DATE")
    @CreatedDate
    private LocalDateTime createdDate;

}

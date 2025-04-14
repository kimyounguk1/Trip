package Deepin.TripPlus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;

@Table(name = "Model")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Model {

    @Id @GeneratedValue
    @Column(name = "MODEL_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "MODEL_TYPE")
    private String modelType;

    @Column(name = "PARAMA")
    private int parama;

    @Column(name = "INFORMATION", columnDefinition = "TEXT")
    private String information;

    @Column(name = "APPLY_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date applyDate;

    @Column(name = "CREATE_DATE")
    @CreatedDate
    private LocalDateTime createdDate;


}

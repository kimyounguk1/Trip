package Deepin.TripPlus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "COURSE")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Course {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COURSE_ID")
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "AREA")
    private String area;

    @Column(name = "TRIP_TYPE")
    private String tripType;

    @Column(name = "MEANS_TP")
    private String meansTp;

    @Column(name = "PERSON")
    private String person;

    @Column(name = "START_DATE")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "END_DATE")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(name = "CREATE_DATE")
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "RATED")
    private boolean rated;

    @Column(name = "MODEL_NAME")
    private String modelName;

    @Column(name = "MODEL_TYPE")
    private String modelType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings = new ArrayList<>();

    public void addRating(Rating rating){
        ratings.add(rating);
        rating.setCourse(this);
    }

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseDetail> courseDts;

    public void addCourseDt(CourseDetail courseDt) {
        courseDts.add(courseDt);
        courseDt.setCourse(this);
    }

}

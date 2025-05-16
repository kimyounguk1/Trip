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

@Table(name = "USERS")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ROLE")
    private String role;

    @Column(name = "USER_TRIP_TYPE")
    private String tripType;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "BIRTH")
    @Temporal(TemporalType.DATE)
    private Date birth;

    @Column(name = "IS_FIRST")
    private boolean isFirst;

    @Column(name = "IS_SUSPENDED")
    private boolean isSuspended;

    @Column(name = "CREATE_DATE")
    @CreatedDate
    private LocalDateTime createdDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "users")
    private List<Course> courses = new ArrayList<>();

    public void addCourse(Course course) {
        courses.add(course);
        course.setUsers(this);
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "users")
    private List<Inquire> inquires = new ArrayList<>();

    public void addInquire(Inquire inquire) {
        inquires.add(inquire);
        inquire.setUsers(this);
    }

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings = new ArrayList<>();

    public void addRating(Rating rating) {
        ratings.add(rating);
        rating.setUsers(this);
    }






}

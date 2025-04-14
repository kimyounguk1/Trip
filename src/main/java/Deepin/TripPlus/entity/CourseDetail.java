package Deepin.TripPlus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Table(name = "COURSE_DETAILS")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class CourseDetail {

    @Id @GeneratedValue
    @Column(name = "COURSE_DT_ID")
    private Long id;

    @Column(name = "PLACE_NAME")
    private String placeName;

    @Column(name = "PLACE_TYPE")
    private String placeType;

    @Column(name = "PLACE_ADDRESS")
    private String placeAddress;

    @Column(name = "PLACE_LAT") //장소 위도
    private String placeLat;

    @Column(name = "PLACE_LON") //장소 경도
    private String placeLon;

    @Column(name = "DAY")
    @Temporal(TemporalType.DATE)
    private Date day;

    @Column(name = "SEQUENCE")
    private int sequence;

}

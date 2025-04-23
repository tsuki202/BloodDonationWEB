package entity;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Date;

@Entity
@Table(name = "recipients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipient {
    @Id
    private Integer id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private Integer year;

    @Column(name = "needed_blood_type")
    private String neededBloodType;

    @Column(name = "request_date")
    private Date requestDate;

    @Column(name = "valid_until")
    private Date validUntil;

    @Column(name = "medical_condition")
    private String medicalCondition;

    @OneToOne
    @JoinColumn(name = "id")
    private UserData userData;
}
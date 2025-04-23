package entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "donors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Donor {
    @Id
    private Integer id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private Integer year;

    @Column(name = "blood_type")
    private String bloodType;

    @Column
    private Integer weight;

    @Column
    private Integer height;

    @OneToOne
    @JoinColumn(name = "id")
    private UserData userData;
}
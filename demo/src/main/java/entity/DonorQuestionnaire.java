package entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "donor_questionnaires")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonorQuestionnaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "donor_id")
    private Integer donorId;

    @Column
    private Integer weight;

    @Column
    private Integer height;

    @Column(name = "feels_good")
    private String feelsGood;

    @Column(name = "had_symptoms")
    private String hadSymptoms;

    @Column
    private String medications;

    @Column(name = "chronic_diseases")
    private String chronicDiseases;

    @Column
    private String allergies;

    @Column(name = "recent_surgeries")
    private String recentSurgeries;

    @Column
    private String tattoos;

    @Column
    private String alcohol;

    @ManyToOne
    @JoinColumn(name = "donor_id", insertable = false, updatable = false)
    private Donor donor;
}

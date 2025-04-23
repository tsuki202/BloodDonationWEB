package entity;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Date;

@Entity
@Table(name = "donations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "donor_id")
    private Integer donorId;

    @Column(name = "donation_date")
    private Date donationDate;

    @Column
    private Integer amount;

    @Column
    private String location;

    @ManyToOne
    @JoinColumn(name = "donor_id", insertable = false, updatable = false)
    private Donor donor;
}
package com.example.travel_system.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "travel_agent")
@Getter
@Setter
@NoArgsConstructor
public class TravelAgent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_agent_id")
    private Integer travelAgentId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @Column(name = "travel_agent_name")
    private String travelAgentName;

    @Column(name = "travel_agent_address")
    private String travelAgentAddress;

    @Column(name = "travel_agent_email")
    private String travelAgentEmail;

    @Column(name = "hot_line")
    private String hotLine;

    @Column(name = "tax_code")
    private String taxCode;

    @Column(name = "business_license")
    private String businessLicense;

    @Column(name = "establishment_date")
    private LocalDate establishmentDate;

    @Column(name = "front_id_card")
    private String frontIdCard;

    @Column(name = "back_id_card")
    private String backIdCard;

    @Column(name = "representative_id_card")
    private String representativeIdCard;

    @Column(name = "date_of_issue")
    private LocalDate dateOfIssue;
}
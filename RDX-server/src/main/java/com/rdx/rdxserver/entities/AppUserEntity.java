package com.rdx.rdxserver.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "app_user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUserEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
    )
    @Access(AccessType.PROPERTY)
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String textCV;
    private Boolean verified;

    @ManyToMany(mappedBy = "appUserEntities")
    private Set<ContractEntity> contractEntities = new LinkedHashSet<>();

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "embeddings_cv_id", referencedColumnName = "id")
    private EmbeddingsEntity embeddingsEntity;

    @ManyToOne
    @JoinColumn(name = "wallet_id", referencedColumnName = "id")
    private WalletEntity walletEntity;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "company_id")
    private CompanyEntity companyEntity;

}

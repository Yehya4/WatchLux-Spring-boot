package com.example.miniecommerce.entities;

import com.example.miniecommerce.enums.paiementEnum;
import com.example.miniecommerce.enums.statutPaiementEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class paiements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private paiementEnum MethodePaiement;
    private statutPaiementEnum statutPaiement;
    private Double montant;
    private Date datePaiement;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commande_id")
    private commandes commandes;

}

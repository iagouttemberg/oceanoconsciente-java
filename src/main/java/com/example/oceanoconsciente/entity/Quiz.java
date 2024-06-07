package com.example.oceanoconsciente.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "TB_QUIZ")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_QUIZ")
    @SequenceGenerator(name = "SQ_QUIZ", sequenceName = "SQ_QUIZ", allocationSize = 50)
    @Column(name = "ID_QUIZ")
    private Long id;
    private Integer pontuacao;
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "USUARIO",
            referencedColumnName = "ID_USUARIO",
            foreignKey = @ForeignKey(
                    name = "FK_USUARIO_QUIZ"
            )
    )
    private Usuario usuario;
}

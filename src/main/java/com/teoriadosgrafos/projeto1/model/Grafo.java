package com.teoriadosgrafos.projeto1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Grafo {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private Integer vertices;
    private Integer edges;
    private Integer minimum;
    private Integer maximum;
    private String adjacency;
    private boolean isRelated;
    private String relatedComponents;

    @Override
    public String toString() {
        return "Vértices: " + vertices +
                "\nArestas: " + edges +
                "\nGrau mínimo: " + minimum +
                "\nGrau máximo: " + maximum +
                "\nRepresentação: \n" + adjacency +
                "\nExiste componente conexo: " + isConnected() +
                "\nComponentes conexos: '" + relatedComponents + '\'';
    }

    private String isConnected() {
        if (isRelated) {
            return "Existe";
        }

        return "Não existe";
    }
}

package com.teoriadosgrafos.projeto1.model;

import com.teoriadosgrafos.projeto1.utils.StringConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    private String verticesValues;
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
                "\nComponentes conexos: '" + relate() + '\'';
    }

    private String isConnected() {
        if (isRelated) {
            return "Existe";
        }

        return "Não existe";
    }

    private String relate() {
        List<Integer> valuesList = new ArrayList<>();

        Integer[][] graphValues = StringConverter.stringToMatrixConverter(verticesValues);

        for (int i = 0; i < graphValues.length; i++) {
            for (int z = 0; z < graphValues[i].length; z++) {
                if (!valuesList.contains(graphValues[i][z])) {
                    valuesList.add(graphValues[i][z]);
                }
            }
        }

        Integer[] values = new Integer[valuesList.size()];
        int counter = 0;

        for (Integer i :
                valuesList) {
            values[counter] = i;
            counter++;
        }

        StringBuilder string = new StringBuilder();

        string.append("{");

        int[] relatedArray = StringConverter.stringToArray(relatedComponents);

        for (int i = 0; i < relatedArray.length; i++) {
            string.append(values[relatedArray[i]]);
            if (i != relatedArray.length - 1) {
                string.append(", ");
            }
        }
        string.append("}");

        return string.toString();
    }
}

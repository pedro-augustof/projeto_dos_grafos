package com.teoriadosgrafos.projeto1.utils;

import java.util.List;
import java.util.Set;

public class StringConverter {

    public static String convertListSetToString(List<Set<Integer>> list) {
        StringBuilder sb = new StringBuilder();

        for (Set<Integer> set : list) {
            sb.append("{");
            for (int num : set) {
                sb.append(num).append(", ");
            }
            // Remove a última vírgula e espaço
            if (!set.isEmpty()) {
                sb.delete(sb.length() - 2, sb.length());
            }
            sb.append("}, ");
        }
        // Remove a última vírgula e espaço
        if (!list.isEmpty()) {
            sb.delete(sb.length() - 2, sb.length());
        }

        return sb.toString();
    }

    public static String matrixToStringConverter(Integer[][] matrix) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                sb.append(matrix[i][j]);

                // Adicione um delimitador, exceto para o último elemento da linha
                if (j < matrix[i].length - 1) {
                    sb.append(", ");
                }
            }

            // Adicione uma quebra de linha após cada linha, exceto para a última linha
            if (i < matrix.length - 1) {
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    public static Integer[][] stringToMatrixConverter(String matrixString) {
        String[] lines = matrixString.split("\n");
        int numRows = lines.length;
        int numCols = lines[0].split(", ").length;

        Integer[][] matrix = new Integer[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            String[] elements = lines[i].split(", ");
            for (int j = 0; j < numCols; j++) {
                matrix[i][j] = Integer.parseInt(elements[j]);
            }
        }

        return matrix;
    }

    public static int[] stringToArray(String input) {
        // Remove os caracteres '{' e '}' e os espaços em branco extras
        String cleanInput = input.replace("{", "").replace("}", "").trim();

        // Divide a string em substrings separadas por vírgula e espaço
        String[] strArray = cleanInput.split(", ");

        int[] intArray = new int[strArray.length];

        for (int i = 0; i < strArray.length; i++) {
            intArray[i] = Integer.parseInt(strArray[i]); // Converte cada substring para um inteiro e armazena no array de inteiros
        }

        return intArray;
    }
}

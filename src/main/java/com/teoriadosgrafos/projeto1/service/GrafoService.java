package com.teoriadosgrafos.projeto1.service;

import com.teoriadosgrafos.projeto1.DTO.FindConnectedDTO;
import com.teoriadosgrafos.projeto1.DTO.GrafoDTO;
import com.teoriadosgrafos.projeto1.DTO.BfsDTO;
import com.teoriadosgrafos.projeto1.model.Grafo;
import com.teoriadosgrafos.projeto1.repositories.GrafoRepository;
import com.teoriadosgrafos.projeto1.utils.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GrafoService {
    private Integer[][] adjacencyMatrix;
    private boolean[] visited;
    private StringBuilder path;

    @Autowired
    GrafoRepository grafoRepository;


    public String createGrafo(GrafoDTO grafoDTO) {
        ArrayList<Integer> edges = new ArrayList<>();
        HashMap<Integer, Integer> degree = new HashMap<>();
        HashMap<Integer, ArrayList<Integer>> adjacency = new HashMap<>();
        Integer[][] adjacencyMatrix = new Integer[grafoDTO.getVertices()][grafoDTO.getVertices()];
        Grafo grafo = new Grafo();

        grafo.setVertices(grafoDTO.getVertices());

        grafo.setVerticesValues(StringConverter.matrixToStringConverter(grafoDTO.getGrafo()));

        for (int i = 0; i < grafoDTO.getGrafo().length; i++) {
            for (int y = 0; y < grafoDTO.getGrafo()[i].length; y++) {
                boolean equal = false;
                for (int x = 0; x < edges.size(); x++) {
                    if (edges.get(x) == grafoDTO.getGrafo()[i][y]) {
                        equal = true;
                    }
                }
                if (!equal) {
                    edges.add(grafoDTO.getGrafo()[i][y]);
                }
            }
        }

        grafo.setEdges(edges.size());

        for (int i = 0; i < grafoDTO.getGrafo().length; i++) {
            for (int y = 0; y < grafoDTO.getGrafo()[i].length; y++) {
                try {
                    if (degree.get(grafoDTO.getGrafo()[i][y]) == null) {
                        degree.put(grafoDTO.getGrafo()[i][y], 1);
                    } else {
                        degree.put(grafoDTO.getGrafo()[i][y], degree.get(grafoDTO.getGrafo()[i][y]) + 1);
                    }
                } catch (NullPointerException e) {
                    degree.put(grafoDTO.getGrafo()[i][y], 1);
                }
            }
        }

        Integer minimum = 99;
        Integer maximum = 0;

        for (Integer i :
                degree.values()) {
            if (i > maximum) {
                maximum = i;
            }

            if (i < minimum) {
                minimum = i;
            }
        }

        grafo.setMinimum(minimum);
        grafo.setMaximum(maximum);

        for (Integer i :
                degree.keySet()) {
            adjacency.put(i, new ArrayList<>());
        }

        for (int i = 0; i < grafoDTO.getGrafo().length; i++) {
            for (int y = 0; y < grafoDTO.getGrafo()[i].length; y++) {
                for (Integer x :
                        adjacency.keySet()) {
                    if (x == grafoDTO.getGrafo()[i][y]) {
                        if (y == 0) {
                            ArrayList<Integer> addNumber = adjacency.get(x);
                            addNumber.add(grafoDTO.getGrafo()[i][1]);
                            adjacency.put(x, addNumber);
                        } else {
                            ArrayList<Integer> addNumber = adjacency.get(x);
                            addNumber.add(grafoDTO.getGrafo()[i][0]);
                            adjacency.put(x, addNumber);
                        }
                    }
                }
            }
        }

        for (int i = 0; i < adjacency.size(); i++) {
            ArrayList<Integer> adjacencies = adjacency.get(i+1);

            for (int y = 0; y < adjacency.size(); y++) {
                adjacencyMatrix[i][y] = 0;
            }

            for (Integer number :
                    adjacencies) {
                adjacencyMatrix[i][number-1] = 1;
            }
        }

        grafo.setAdjacency(StringConverter.matrixToStringConverter(adjacencyMatrix));

        FindConnectedDTO findConnectedDTO = findConnectedComponents(StringConverter.stringToMatrixConverter(grafo.getAdjacency()));

        grafo.setRelatedComponents(StringConverter.convertListSetToString(findConnectedDTO.getComponents()));
        grafo.setRelated(findConnectedDTO.isConnected());
        grafo.setId(1L);

        grafoRepository.save(grafo);

        return grafo.toString();
    }

    private BfsDTO bfs(Integer[][] graph, int source) {
        int n = graph.length;
        boolean[] visited = new boolean[n];
        int[] distances = new int[n];
        int[] predecessors = new int[n];

        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;
        distances[source] = 0;
        predecessors[source] = -1;

        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int i = 0; i < n; i++) {
                if (graph[current][i] == 1 && !visited[i]) {
                    visited[i] = true;
                    distances[i] = distances[current] + 1;
                    predecessors[i] = current;
                    queue.add(i);
                }
            }
        }

        return getPath(source, visited, distances, predecessors);
    }

    private BfsDTO getPath(int source, boolean[] visited, int[] distances, int[] predecessors) {
        StringBuilder path = new StringBuilder();

        int n = visited.length;
        boolean isConnected = true;

        for (int i = 0; i < n; i++) {
            if (visited[i]) {
                if (distances[i] == 0) {
                    path.append(source + 1);
                } else {
                    List<Integer> vertices = new ArrayList<>();
                    int current = i;
                    while (current != -1) {
                        vertices.add(current);
                        current = predecessors[current];
                    }
                    Collections.reverse(vertices);
                    for (int j = 0; j < vertices.size(); j++) {
                        path.append(vertices.get(j) + 1);
                        if (j != vertices.size() - 1) {
                            path.append(" -> ");
                        }
                    }
                }
                path.append("\n");
            } else {
                isConnected = false;
            }
        }

        return new BfsDTO(path.toString(), isConnected);
    }

    private FindConnectedDTO findConnectedComponents(Integer[][] grafo) {
        int n = grafo.length;
        boolean[] visitados = new boolean[n];
        List<Set<Integer>> componentes = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (!visitados[i]) {
                Set<Integer> componente = new HashSet<>();
                Queue<Integer> fila = new LinkedList<Integer>();

                visitados[i] = true;
                componente.add(i);
                fila.add(i);

                while (!fila.isEmpty()) {
                    int atual = fila.poll();
                    for (int j = 0; j < n; j++) {
                        if (grafo[atual][j] == 1 && !visitados[j]) {
                            visitados[j] = true;
                            componente.add(j);
                            fila.add(j);
                        }
                    }
                }

                componentes.add(componente);
            }
        }

        return new FindConnectedDTO(componentes, bfs(grafo, 0).isConnected());
    }

    public String breadthSearch(int source) {
        Optional<Grafo> graphOPT = grafoRepository.findById(1L);

        if (!graphOPT.isPresent()) {
            return "Grafo não cadastrado";
        }

        Grafo graph = graphOPT.get();

        List<Integer> valuesList = new ArrayList<>();

        Integer[][] graphValues = StringConverter.stringToMatrixConverter(graph.getVerticesValues());

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

        counter = 99;

        for (int i = 0; i < values.length; i++) {
            if (values[i] == source) {
                counter = i;
            }
        }

        if (counter == 99) {
            return "Vertice não encontrado!";
        }

        return depthFirstSearch(StringConverter.stringToMatrixConverter(graph.getAdjacency()), counter, values);
    }

    public String depthFirstSearch(Integer[][] matrix, int startVertex, Integer[] values) {
        adjacencyMatrix = matrix;
        int numVertices = matrix.length;
        visited = new boolean[numVertices];
        path = new StringBuilder();

        // Inicializa todos os vértices como não visitados
        Arrays.fill(visited, false);

        // Inicia a busca em profundidade a partir do vértice fornecido
        dfs(startVertex, values);

        return path.toString();
    }

    private void dfs(int vertex, Integer[] values) {
        visited[vertex] = true;
        path.append(values[vertex]).append(" ");

        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if (adjacencyMatrix[vertex][i] == 1 && !visited[i] && i > vertex) {
                dfs(i, values);
            }
        }
    }
}

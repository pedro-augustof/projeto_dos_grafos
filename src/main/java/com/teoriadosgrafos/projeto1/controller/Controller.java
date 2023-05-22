package com.teoriadosgrafos.projeto1.controller;

import com.teoriadosgrafos.projeto1.DTO.GrafoDTO;
import com.teoriadosgrafos.projeto1.model.Grafo;
import com.teoriadosgrafos.projeto1.service.GrafoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    @Autowired
    GrafoService service;

    @PostMapping("/grafo")
    public String inserirGrafo(@RequestBody GrafoDTO grafoDTO){
        try {
            if (grafoDTO.getGrafo() == null || grafoDTO.getVertices() == null) {
                return null;
            }
        } catch (NullPointerException e) {
            return null;
        }

        return service.createGrafo(grafoDTO);
    }

    @GetMapping("/grafo/busca")
    public String bfs(@RequestParam int source) {

        return service.breadthSearch(source);
    }
}

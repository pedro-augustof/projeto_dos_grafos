package com.teoriadosgrafos.projeto1.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class FindConnectedDTO {

    private List<Set<Integer>> components;
    private boolean connected;
}

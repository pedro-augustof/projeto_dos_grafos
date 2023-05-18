package com.teoriadosgrafos.projeto1.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BfsDTO {

    private String path;
    private boolean connected;
}

package com.apmvc.apartamento.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Apartamento<Propietario> {

    private int idAp;
    private int numPortas;
    private int qtdQuartos;
    private String tipo;
    private Propietario propietario;

    
}

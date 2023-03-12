package br.com.ulkiorra.model;

import java.io.Serializable;

public enum Cursos implements Serializable {
    ADS("Análise e Desenvolvimento de Sistemas"),
    ECMP("Egenharia de Computação"),
    CCMP("Ciência da Computação"),
    OUTROS("Outros");

    private final String nomeCurso;
    Cursos(String nomeCurso){
        this.nomeCurso = nomeCurso;
    }
    public String nomeCurso(){
        return this.nomeCurso;
    }
}

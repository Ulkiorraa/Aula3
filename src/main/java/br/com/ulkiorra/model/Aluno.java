package br.com.ulkiorra.model;

public class Aluno {
    private Long matricula;
    private String nome;
    private boolean maioridade;
    private String curso;
    private String sexo;

    public Aluno() {
    }

    public Aluno(Long matricula, String nome, boolean maioridade, String curso, String sexo) {
        this.matricula = matricula;
        this.nome = nome;
        this.maioridade = maioridade;
        this.curso = curso;
        this.sexo = sexo;
    }

    public void setMatricula(Long matricula) {
        this.matricula = matricula;
    }

    public Long getMatricula() {
        return matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isMaioridade() {
        return maioridade;
    }

    public void setMaioridade(boolean maioridade) {
        this.maioridade = maioridade;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}

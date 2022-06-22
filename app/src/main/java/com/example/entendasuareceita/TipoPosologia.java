package com.example.entendasuareceita;

public class TipoPosologia {

    private int id;
    private String nome;

    public TipoPosologia() {
    }

    public TipoPosologia(String nome) {
        this.nome = nome;
    }

    public TipoPosologia(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}

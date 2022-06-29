package com.example.entendasuareceita;

public class Receita {

    public String id;
    public String nomeMedicamento;
    public int quantidade;
    public TipoPosologia tipo;

    public String comoTomar;

    public Receita(){

    }

    public Receita(String id,String nomeMedicamento, int quantidade, TipoPosologia tipo, String comoTomar){
        this.id = id;
        this.nomeMedicamento = nomeMedicamento;
        this.quantidade = quantidade;
        this.tipo = tipo;
        this.comoTomar = comoTomar;
    }

    public Receita(String nomeMedicamento, int quantidade, TipoPosologia tipo, String comoTomar){
        this.nomeMedicamento = nomeMedicamento;
        this.quantidade = quantidade;
        this.tipo = tipo;
       this.comoTomar = comoTomar;
    }

     @Override
     public String toString() {
     return nomeMedicamento + "\n \n " + "Tomar " +  quantidade + " " + tipo.getNome() + "\n"+ comoTomar;
     }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getnomeMedicamento() {
        return nomeMedicamento;
    }

    public void setNomeMedicamento(String nomeMedicamento) {
        this.nomeMedicamento = nomeMedicamento;
    }


    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }


    public TipoPosologia getTipo() {
        return tipo;
    }

    public void setTipo(TipoPosologia tipo) {
        this.tipo = tipo;
    }

    public String getComoTomar() {
        return comoTomar;
    }

    public void setComoTomar(String comoTomar) {
        this.comoTomar = comoTomar;
    }





}

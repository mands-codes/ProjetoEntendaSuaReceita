package com.example.entendasuareceita;

public class Receita {

    public String id;
    public String NomeMedicamento;
    public int Quantidade;
    public TipoPosologia Tipo;

    public String ComoTomar;

    public Receita(){

    }

    public Receita(String id,String NomeMedicamento, int Quantidade, TipoPosologia Tipo, String ComoTomar){
        this.id = id;
        this.NomeMedicamento = NomeMedicamento;
        this.Quantidade = Quantidade;
        this.Tipo = Tipo;
        this.ComoTomar = ComoTomar;
    }

    public Receita(String NomeMedicamento, int Quantidade, TipoPosologia Tipo, String ComoTomar){
        this.NomeMedicamento = NomeMedicamento;
        this.Quantidade = Quantidade;
        this.Tipo = Tipo;
       this.ComoTomar = ComoTomar;
    }

     @Override
     public String toString() {
     return  NomeMedicamento;
     }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeMedicamento() {
        return NomeMedicamento;
    }

    public void setNomeMedicamento(String NomeMedicamento) {
        this.NomeMedicamento= NomeMedicamento;
    }


    public int getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(int Quantidade) {
        this.Quantidade = Quantidade;
    }


    public TipoPosologia getTipo() {
        return Tipo;
    }

    public void setTipo(TipoPosologia Tipo) {
        this.Tipo = Tipo;
    }

    public String getComoTomar() {
        return ComoTomar;
    }

    public void setComoTomar(String ComoTomar) {
        this.ComoTomar = ComoTomar;
    }





}

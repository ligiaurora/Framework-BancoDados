package com.classes.model;

public class Campo {
    private String nome;
    private Tipo tipo;
    private boolean ehNulo;
    private boolean ehChavePrimaria;
    private boolean ehAutoIncremento;
    private int tamanho;

    public enum Tipo {
        INT, DOUBLE, FLOAT, VARCHAR, BOOLEAN, DATETIME, DATE, TIME
    }

    public Campo(String nome, Tipo tipo, boolean ehNulo, boolean ehChavePrimaria, boolean ehAutoIncremento) {
        this(nome, tipo, ehNulo, ehChavePrimaria, 255,ehAutoIncremento);
    }

    public Campo(String nome, Tipo tipo, boolean ehNulo, boolean ehChavePrimaria, int tamanho,boolean ehAutoIncremento) {
        this.nome = nome;
        this.tipo = tipo;
        this.ehNulo = ehNulo;
        this.ehChavePrimaria = ehChavePrimaria;
        this.ehAutoIncremento = ehAutoIncremento;
        this.tamanho = tamanho;
    }

    public String getNome() { 
    	return nome; 
    }
    
    public Tipo getTipo() { 
    	return tipo; 
    }
    
    public boolean isEhNulo() { 
    	return ehNulo; 
    }
    
    public boolean isEhChavePrimaria() { 
    	return ehChavePrimaria; 
    }
    
    public boolean isEhAutoIncremento() {
    	return ehAutoIncremento;
    }
    
    public int getTamanho() { 
    	return tamanho; 
    }

    public String gerarScript() {
        StringBuilder script = new StringBuilder();
        script.append(nome).append(" ").append(tipo);
        if (tipo == Tipo.VARCHAR) {
            script.append("(").append(tamanho).append(")");
        }
        if (!ehNulo) {
            script.append(" NOT NULL");
        }
        if (ehChavePrimaria) {
            script.append(" PRIMARY KEY");
        }
        if (ehAutoIncremento) {
            script.append(" AUTO_INCREMENT");
        }
        return script.toString();
    }

	@Override
	public String toString() {
		return "Campo [nome=" + nome + ", tipo=" + tipo + ", ehNulo=" + ehNulo + ", ehChavePrimaria=" + ehChavePrimaria
				+ ", ehAutoIncremento=" + ehAutoIncremento + ", tamanho=" + tamanho + "]";
	}
}
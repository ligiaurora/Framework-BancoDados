package com.classes.model;


import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Tabela {
    private List<Campo> campos;
    private String nomeTabela; 

    public Tabela(String nomeTabela) {
        this.campos = new ArrayList<>();
        this.nomeTabela = nomeTabela;
    }
    
    public List<Campo> getColunas() {
		return campos;
	}

	public void setColunas(List<Campo> campos) {
		this.campos = campos;
	}

	public String getNomeTabela() {
		return nomeTabela;
	}

	public void setNomeTabela(String nomeTabela) {
		this.nomeTabela = nomeTabela;
	}


	public Tabela(List<Campo> campos, String nomeTabela) {
		super();
		this.campos = campos;
		this.nomeTabela = nomeTabela;
	}
	
	
	//Desenvolvimento dos métodos para CRUD

	public void adicionarCampo(Campo campo) {
        campos.add(campo);
    }
	
	  public void alterarCampo(String nomeCampo, Campo novoCampo) {
	        for (int i = 0; i < campos.size(); i++) {
	            if (campos.get(i).getNome().equalsIgnoreCase(nomeCampo)) {
	                campos.set(i, novoCampo);
	                break;
	            }
	        }
	    }
	
	 public void removerCampo(String nomeColuna) {
	        campos.removeIf(coluna -> coluna.getNome().equalsIgnoreCase(nomeColuna));
	 }

    public List<Campo> listarCampos() {
        return campos;
    }
    
    
    /***********************************************************************************/
    
    public String gerarScript() {
        StringBuilder script = new StringBuilder();
        script.append("CREATE TABLE ").append(nomeTabela).append(" (\n");
        for (Campo campo : campos) {
            script.append(campo.gerarScript()).append(",\n");
        }
        script.deleteCharAt(script.length() - 2); 
        script.append(");");
        return script.toString();
    }
    
    
    public void salvarScriptParaArquivo(String caminhoArquivo) {
        String script = gerarScript();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            writer.write(script);
        } catch (IOException e) {
            System.out.println("Erro ao salvar script para o arquivo: " + e.getMessage());
        }
    }
    
}

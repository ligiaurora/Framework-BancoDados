package com.classes.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ConstroiBD {
    private String nomeBD;

    public ConstroiBD(String nomeBD) {
        this.nomeBD = nomeBD;
    }

    public String getNomeBD() { 
    	return nomeBD; 
    }
    
    public void setNomeBD(String nomeBD) { 
    	this.nomeBD = nomeBD; 
    }

    public void criarBD(Connection conexao) {
        try {
            Statement statement = conexao.createStatement();
            String sql = "CREATE DATABASE " + nomeBD;      
            statement.executeUpdate(sql);
            System.out.println("Banco de dados criado com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao criar banco de dados: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "GeradorBD [nomeBD=" + nomeBD + "]";
    }
}
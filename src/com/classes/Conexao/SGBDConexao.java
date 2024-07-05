package com.classes.Conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SGBDConexao {
	
	/*Declaração das variaveis*/
	private String host;
	private String usuario;
	private String senha;
	private int porta;
	
	
	/*Desenvolvimento do Construtor*/
	public SGBDConexao(String host, String usuario, String senha, int porta) {
		this.host = host;
		this.usuario = usuario;
		this.senha = senha;
		this.porta = porta;
	}
	
	
	/*Getters e Setters*/

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public int getPorta() {
		return porta;
	}

	public void setPorta(int porta) {
		this.porta = porta;
	}
	
	/*metodo para conseguir ter conexão com o bd*/
    public Connection conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(host + porta, usuario, senha);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    public Connection AbrirConexaoBD(String nomeBD) {
        try {
            String url = "jdbc:mysql://" + host + ":" + porta + "/" + nomeBD;
            return DriverManager.getConnection(url, usuario, senha);
        } catch (SQLException e) {
            System.out.println("Erro ao abrir conexão com o banco de dados: " + e.getMessage());
            return null;
        }
    }
    
    /*Vai executar o Query no BD*/

    public void ExecutarQuery(Connection conexao, String query) {
        try {
            Statement statement = conexao.createStatement();
            statement.executeUpdate(query);
            System.out.println("Query executada com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao executar query: " + e.getMessage());
        }
    }
    
    
	@Override
	public String toString() {
		return "SGBDConexao [host=" + host + ", usuario=" + usuario + ", senha=" + senha + ", porta=" + porta + "]";
	}	
}
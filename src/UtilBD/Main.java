package UtilBD;



import com.classes.Conexao.SGBDConexao;
import com.classes.model.Campo;
import com.classes.model.ConstroiBD;
import com.classes.model.Tabela;

import java.sql.Connection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SGBDConexao sgbdConexao = new SGBDConexao("localhost", "root", "", 3306);
        Connection conexao = sgbdConexao.AbrirConexaoBD("");

        if (conexao != null) {
            System.out.println("Conexão com o banco de dados estabelecida com sucesso!");
            
            Scanner entrada = new Scanner(System.in);

            System.out.print("Digite o nome do banco que deseja criar: ");
            String nomeBanco = entrada.nextLine();

            ConstroiBD geradorBD = new ConstroiBD(nomeBanco);
            geradorBD.criarBD(conexao);

            try {
                conexao.close();
            } catch (Exception e) {
                System.out.println("Erro ao fechar a conexão inicial: " + e.getMessage());
            }

            conexao = sgbdConexao.AbrirConexaoBD(nomeBanco);

            if (conexao != null) {
                System.out.print("Digite o nome da tabela que deseja criar: ");
                String nomeTabela = entrada.nextLine();
                Tabela tabela = new Tabela(nomeTabela);

                int acao;
                do {
                    System.out.println("Escolha uma ação:");
                    System.out.println("1 - Adicionar chave primária");
                    System.out.println("2 - Adicionar coluna");
                    System.out.println("3 - Alterar coluna");
                    System.out.println("4 - Excluir coluna");
                    System.out.println("5 - Gerar script e criar tabela");
                    System.out.println("6 - Sair");

                    acao = Integer.parseInt(entrada.nextLine());

                    switch (acao) {
                        case 1:
                            System.out.print("Digite o nome da coluna chave primária: ");
                            String nomeChavePrimaria = entrada.nextLine().replaceAll(" ", "_");
                            tabela.adicionarCampo(new Campo(nomeChavePrimaria, Campo.Tipo.INT, false, true, true));
                            break;
                        case 2:
                            System.out.print("Digite o nome do campo que quer adicionar: ");
                            String nomeColuna = entrada.nextLine().replaceAll(" ", "_");
                            System.out.print("Digite o tipo de campo (INT, DOUBLE, FLOAT, VARCHAR, BOOLEAN, DATETIME, DATE, TIME): ");
                            Campo.Tipo tipo = Campo.Tipo.valueOf(entrada.nextLine().toUpperCase());
                            System.out.print("O Campo pode ser nulo? (true/false): ");
                            boolean ehNulo = Boolean.parseBoolean(entrada.nextLine());
                            int tamanho = 255;
                            if (tipo == Campo.Tipo.VARCHAR) {
                                System.out.print("Digite o tamanho do VARCHAR: ");
                                tamanho = Integer.parseInt(entrada.nextLine());
                            }
                            tabela.adicionarCampo(new Campo(nomeColuna, tipo, ehNulo, false, tamanho, false));
                            break;
                        case 3:
                            System.out.print("Digite o nome do campo que deseja alterar: ");
                            String nomeCampoAntigo = entrada.nextLine().replaceAll(" ", "_");
                            System.out.print("Digite o novo nome do campo: ");
                            String novoNomeCampo = entrada.nextLine().replaceAll(" ", "_");
                            System.out.print("Digite o novo tipo do campo (INT, DOUBLE, FLOAT, VARCHAR, BOOLEAN, DATETIME, DATE, TIME): ");
                            Campo.Tipo novoTipo = Campo.Tipo.valueOf(entrada.nextLine().toUpperCase());
                            System.out.print("O Campo pode ser nulo? (true/false): ");
                            boolean novoEhNulo = Boolean.parseBoolean(entrada.nextLine());
                            int novoTamanho = 255;
                            if (novoTipo == Campo.Tipo.VARCHAR) {
                                System.out.print("Digite o tamanho do VARCHAR: ");
                                novoTamanho = Integer.parseInt(entrada.nextLine());
                            }
                            Campo novoCampo = new Campo(novoNomeCampo, novoTipo, novoEhNulo, false, novoTamanho, false);
                            tabela.alterarCampo(nomeCampoAntigo, novoCampo);
                            break;
                        case 4:
                            System.out.print("Digite o nome da coluna que deseja excluir: ");
                            String nomeColunaExcluir = entrada.nextLine().replaceAll(" ", "_");
                            tabela.removerCampo(nomeColunaExcluir);
                            break;
                        case 5:
                            String script = tabela.gerarScript();
                            System.out.println("Script gerado: \n" + script);

                            tabela.salvarScriptParaArquivo(nomeTabela + ".sql");
                            System.out.println("Script salvo no arquivo " + nomeTabela + ".sql");

                            sgbdConexao.ExecutarQuery(conexao, script);
                            break;
                        case 6:
                            System.out.println("Saindo...");
                            break;
                        default:
                            System.out.println("Ação inválida! Escolha um valor válido");
                            break;
                    }

                } while (acao != 6);

                entrada.close();
            } else {
                System.out.println("Falha ao conectar com o banco de dados recém-criado.");
            }

        } else {
            System.out.println("Falha ao conectar com o banco de dados.");
        }
    }
}
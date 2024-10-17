package br.com.tarefas;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class GerenciadorDeTarefas {
    private ArrayList<Tarefa> tarefas;
    private Scanner scanner;

    public GerenciadorDeTarefas() {  
        tarefas = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        GerenciadorDeTarefas gerenciador = new GerenciadorDeTarefas();
        gerenciador.carregarTarefas();  
        gerenciador.executar();
        gerenciador.salvarTarefas();  
    }

    public void executar() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Adicionar nova tarefa");
            System.out.println("2. Listar todas as tarefas");
            System.out.println("3. Marcar tarefa como concluída");
            System.out.println("4. Remover tarefa");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine(); 
            } else {
                System.out.println("Por favor, insira um número válido.");
                scanner.nextLine();  
                continue;
            }

            switch (opcao) {
                case 1:
                    adicionarTarefa();
                    break;
                case 2:
                    listarTarefas();
                    break;
                case 3:
                    marcarTarefaConcluida();
                    break;
                case 4:
                    removerTarefa();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    public void adicionarTarefa() {
        String descricao = "";
        while (descricao.isEmpty()) {
            System.out.print("Digite a descrição da nova tarefa: ");
            descricao = scanner.nextLine().trim();
            if (descricao.isEmpty()) {
                System.out.println("A descrição da tarefa não pode estar vazia. Tente novamente.");
            }
        }

        System.out.print("Digite a categoria da nova tarefa: ");
        String categoria = scanner.nextLine().trim();

        Tarefa novaTarefa = new Tarefa(descricao, categoria);
        tarefas.add(novaTarefa);
        System.out.println("Tarefa adicionada com sucesso!");
    }

    public void listarTarefas() {
        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada.");
        } else {
            System.out.println("\n--- Lista de Tarefas ---");
            for (int i = 0; i < tarefas.size(); i++) {  
                System.out.println((i + 1) + ". " + tarefas.get(i)); 
            }
        }
    }

    public void marcarTarefaConcluida() {
        listarTarefas();  
        if (!tarefas.isEmpty()) {
            System.out.print("Digite o número da tarefa a ser marcada como concluída: ");
            int indice = scanner.nextInt() - 1;  
            scanner.nextLine();  

            if (indice >= 0 && indice < tarefas.size()) {  
                tarefas.get(indice).marcarComoConcluida();  
                System.out.println("Tarefa marcada como concluída.");
            } else {
                System.out.println("Número de tarefa inválido.");  
            }
        }
    }

    public void removerTarefa() {
        listarTarefas();  
        if (!tarefas.isEmpty()) {
            System.out.print("Digite o número da tarefa a ser removida: ");
            int indice = scanner.nextInt() - 1;  
            scanner.nextLine();  

            if (indice >= 0 && indice < tarefas.size()) {  
                tarefas.remove(indice);  
                System.out.println("Tarefa removida com sucesso.");
            } else {
                System.out.println("Número de tarefa inválido.");  
            }
        }
    }

    public void salvarTarefas() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tarefas.txt"))) {
            for (Tarefa tarefa : tarefas) {
                writer.write(tarefa.getDescricao() + ";" + tarefa.isConcluida());
                writer.newLine();
            }
            System.out.println("Tarefas salvas com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar as tarefas: " + e.getMessage());
        }
    }

    public void carregarTarefas() {
        File arquivo = new File("tarefas.txt");
        if (arquivo.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
                String linha;
                while ((linha = reader.readLine()) != null) {
                    String[] partes = linha.split(";");
                    Tarefa tarefa = new Tarefa(partes[0], ""); 
                    if (Boolean.parseBoolean(partes[1])) {
                        tarefa.marcarComoConcluida();
                    }
                    tarefas.add(tarefa);
                }
                System.out.println("Tarefas carregadas com sucesso.");
            } catch (IOException e) {
                System.out.println("Erro ao carregar as tarefas: " + e.getMessage());
            }
        }
    }
}

package br.com.tarefas;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
            System.out.println("5. Editar tarefa");
            System.out.println("6. Filtrar tarefas");
            System.out.println("7. Listar tarefas atrasadas");
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
                case 5:
                    editarTarefa();
                    break;
                case 6:
                    filtrarTarefas();
                    break;
                case 7:
                    listarTarefasAtrasadas();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
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

        LocalDate prazo = null;
        while (prazo == null) {
            System.out.print("Digite o prazo da tarefa (AAAA-MM-DD): ");
            String prazoInput = scanner.nextLine();
            try {
                prazo = LocalDate.parse(prazoInput);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data inválido. Tente novamente.");
            }
        }

        System.out.print("Digite a prioridade da tarefa (baixa, média, alta): ");
        String prioridade = scanner.nextLine().trim();

        Tarefa novaTarefa = new Tarefa(descricao, categoria, prazo, prioridade);
        tarefas.add(novaTarefa);
        System.out.println("Tarefa adicionada com sucesso!");
    }

    public void salvarTarefas() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tarefas.txt"))) {
            for (Tarefa tarefa : tarefas) {
                writer.write(tarefa.getDescricao() + ";" + tarefa.getCategoria() + ";" + tarefa.isConcluida() + ";" + tarefa.getPrazo() + ";" + tarefa.getPrioridade());
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
                    Tarefa tarefa = new Tarefa(partes[0], partes[1], LocalDate.parse(partes[3]), partes[4]);
                    if (Boolean.parseBoolean(partes[2])) {
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

    public void editarTarefa() {
        listarTarefas();
        if (!tarefas.isEmpty()) {
            System.out.print("Digite o número da tarefa a ser editada: ");
            int indice = scanner.nextInt() - 1;
            scanner.nextLine();

            if (indice >= 0 && indice < tarefas.size()) {
                System.out.print("Digite a nova descrição: ");
                String novaDescricao = scanner.nextLine().trim();
                System.out.print("Digite a nova categoria: ");
                String novaCategoria = scanner.nextLine().trim();
                System.out.print("Digite o novo prazo (AAAA-MM-DD): ");
                LocalDate novoPrazo = LocalDate.parse(scanner.nextLine().trim());
                System.out.print("Digite a nova prioridade (baixa, média, alta): ");
                String novaPrioridade = scanner.nextLine().trim();

                if (!novaDescricao.isEmpty() && !novaCategoria.isEmpty()) {
                    tarefas.get(indice).setDescricao(novaDescricao);
                    tarefas.get(indice).setCategoria(novaCategoria);
                    tarefas.get(indice).setPrazo(novoPrazo);
                    tarefas.get(indice).setPrioridade(novaPrioridade);
                    System.out.println("Tarefa editada com sucesso.");
                } else {
                    System.out.println("A descrição e a categoria não podem ser vazias.");
                }
            } else {
                System.out.println("Número de tarefa inválido.");
            }
        }
    }

    public void filtrarTarefas() {
        System.out.println("Escolha um filtro:");
        System.out.println("1. Tarefas concluídas");
        System.out.println("2. Tarefas por categoria");
        System.out.println("3. Tarefas por prioridade");
        System.out.println("4. Tarefas por prazo");

        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1:
                for (Tarefa tarefa : tarefas) {
                    if (tarefa.isConcluida()) {
                        System.out.println(tarefa);
                    }
                }
                break;
            case 2:
                System.out.print("Digite a categoria para filtrar: ");
                String categoria = scanner.nextLine();
                for (Tarefa tarefa : tarefas) {
                    if (tarefa.getCategoria().equalsIgnoreCase(categoria)) {
                        System.out.println(tarefa);
                    }
                }
                break;
            case 3:
                System.out.print("Digite a prioridade para filtrar (baixa, média, alta): ");
                String prioridade = scanner.nextLine();
                for (Tarefa tarefa : tarefas) {
                    if (tarefa.getPrioridade().equalsIgnoreCase(prioridade)) {
                        System.out.println(tarefa);
                    }
                }
                break;
            case 4:
                System.out.print("Digite a data limite para o prazo (AAAA-MM-DD): ");
                LocalDate dataLimite = LocalDate.parse(scanner.nextLine());
                for (Tarefa tarefa : tarefas) {
                    if (tarefa.getPrazo().isBefore(dataLimite) || tarefa.getPrazo().isEqual(dataLimite)) {
                        System.out.println(tarefa);
                    }
                }
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    public void listarTarefasAtrasadas() {
        LocalDate hoje = LocalDate.now();
        System.out.println("\n--- Tarefas Atrasadas ---");
        boolean encontrouAtrasadas = false;
        for (Tarefa tarefa : tarefas) {
            if (!tarefa.isConcluida() && tarefa.getPrazo().isBefore(hoje)) {
                System.out.println(tarefa);
                encontrouAtrasadas = true;
            }
        }
        if (!encontrouAtrasadas) {
            System.out.println("Nenhuma tarefa atrasada.");
        }
    }
}

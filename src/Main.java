import org.example.dao.AlunoDAO;
import org.example.dao.AutenticadorDAO;
import org.example.dao.CursoDAO;
import org.example.factory.CreateTable;
import org.example.model.Admin;
import org.example.model.Aluno;
import org.example.model.Curso;
import org.example.dao.AdminDAO;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        AutenticadorDAO autenticadorDAO = new AutenticadorDAO();
        Scanner sc = new Scanner(System.in);
        /*CreateTable ct = new CreateTable();
        ct.createTableAluno();
        ct.createTableCurso();
        ct.createTableAdmin();*/
        while (true) {
            try {
                System.out.println("=== Tela de Login ===");

                System.out.print("Usuário: ");
                String usuario = sc.nextLine();

                System.out.print("Senha: ");
                String senha = sc.nextLine();
                if (usuario.matches("^\\d+$") && autenticadorDAO.autenticarUsuario(Long.parseLong(usuario), senha)) {
                    System.out.println("Login efetuado com sucesso!");
                    System.out.println("Bem Vindo ao Sistema!");
                    menuUsuario();
                }
                if (!usuario.matches("^\\d+$") && autenticadorDAO.autenticarAdmin(usuario, senha)) {
                    System.out.println("Login efetuado com sucesso!");
                    System.out.println("Bem Vindo ao Sistema!");
                    menuAdministrador();

                } else {
                    System.out.println("Usuário ou senha incorreto.");
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }


    public static void menuCurso() {
        while (true) {
            CursoDAO cursoDAO = new CursoDAO();
            Scanner entry = new Scanner(System.in);

            System.out.println("=====Curso=====");
            System.out.println("1 - inserir novo curso");
            System.out.println("2 - alterar curso");
            System.out.println("3 - remover curso");
            System.out.println("4 - listar curso");
            System.out.println("0 - Sair");

            int opcao = entry.nextInt();
            entry.nextLine();
            switch (opcao) {
                case 0:
                    String saida = "voltando para o menu";
                    System.out.println("=================================");
                    System.out.printf("   🔄 %s   \n", saida);
                    System.out.print("   Carregando");

                    for (int i = 0; i < 3; i++) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        System.out.print(".");
                    }

                    System.out.println("\n=================================");
                    menuAdministrador();
                case 1:
                    System.out.println("Nome do curso: ");
                    String nome = entry.nextLine();
                    Curso curso = new Curso(nome);
                    cursoDAO.insert(curso);
                    break;

                case 2:
                    System.out.println("Curso a ser alterado");
                    String nomeCurso = entry.nextLine();
                    Curso cursoAlterar = cursoDAO.existeCurso(nomeCurso);
                    if (cursoAlterar != null) {
                        System.out.println("Novo nome:");
                        String nomeNovo = entry.next();
                        cursoDAO.update(cursoAlterar, nomeNovo);
                    }
                    break;
                case 3:
                    System.out.println("Nome do Curso: ");
                    String cursoNome = entry.nextLine();
                    try {
                        cursoDAO.delete(cursoNome);
                        break;
                    } catch (Exception e) {
                        System.out.println("Curso não cadastrado.");
                        ;
                    }
                case 4:
                    cursoDAO.select();
                    break;
                default:
                    System.out.println("Opção invalida.");
            }
        }
    }

    public static void menuAluno() {
        try {
            while (true) {
                Scanner entry = new Scanner(System.in);
                AlunoDAO alunoDAO = new AlunoDAO();

                System.out.println("=====Aluno=====");
                System.out.println("1 - Cadastrar aluno.");
                System.out.println("3 - Excluir aluno.");
                System.out.println("4 - Exibir dados do aluno.");
                System.out.println("0 - Sair");

                int opcao = entry.nextInt();
                entry.nextLine();
                switch (opcao) {
                    case 0:
                        String saida = "voltando para o menu";
                        System.out.println("=================================");
                        System.out.printf("   🔄 %s   \n", saida);
                        System.out.print("   Carregando");

                        for (int i = 0; i < 3; i++) {
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                            System.out.print(".");
                        }

                        System.out.println("\n=================================");
                        menuAdministrador();
                    case 1:
                        while (true) {
                            try {
                                System.out.println("=======Cadastrar aluno======");
                                System.out.println("Nome do aluno: ");
                                String nomeAluno = entry.nextLine();

                                System.out.println("Telefone: ");
                                long telefone = entry.nextLong();
                                entry.nextLine();

                                System.out.println("Data de nascimento:");
                                System.out.println("Ex: 12051997 (Dia mês ano)");
                                String dataString = entry.nextLine();

                                System.out.println("CPF: ");
                                System.out.println("Ex: 12345678900");
                                String cpf = entry.nextLine();

                                System.out.println("Senha: ");
                                String senha = entry.nextLine();

                                System.out.println("Escolha o curso:");
                                System.out.println("Digite o nome do curso.");
                                CursoDAO cursoDAO = new CursoDAO();
                                cursoDAO.select();
                                String nomeCurso = entry.nextLine();
                                Curso curso = cursoDAO.existeCurso(nomeCurso);
                                Aluno aluno = new Aluno(nomeAluno, telefone, dataString, cpf, curso.getId(), senha);
                                if (aluno.VerificaDataNascimento() && aluno.verificaNome() && aluno.verificarSenha()) {
                                    aluno.DatedataNascimento();
                                    aluno.gerarMatricula();
                                    alunoDAO.insert(aluno);
                                    break;
                                }
                            } catch (Exception e) {
                                System.out.println("Erro: Aluno não cadastrado." + e.getMessage());
                            }
                        }
                    case 2:
                        try {
                            boolean vef = true;
                            System.out.println("==Alterar dados do aluno==");
                            System.out.println("Matrícula do aluno: ");
                            long matriculaAntiga = entry.nextLong();
                            entry.nextLine();

                            System.out.println("Nome do aluno: ");
                            String nomeAluno = entry.nextLine();

                            System.out.println("Telefone: ");
                            long telefone = entry.nextLong();
                            entry.nextLine();

                            System.out.println("Data de nascimento:");
                            System.out.println("Ex: 12051997 (Dia mês ano)");
                            String dataString = entry.nextLine();

                            System.out.println("CPF: ");
                            System.out.println("Ex: 12345678900");
                            String cpf = entry.nextLine();

                            System.out.println("Escolha o curso:");
                            System.out.println("Digite o nome do curso.");
                            CursoDAO cursoDAO = new CursoDAO();
                            cursoDAO.select();
                            String nomeCurso = entry.nextLine();
                            Curso curso = cursoDAO.existeCurso(nomeCurso);
                            int idNovoCurso = curso.getId();
                            Aluno aluno = alunoDAO.getAluno(matriculaAntiga);
                            if (aluno == null) {
                                throw new IllegalArgumentException("Aluno nao encontrado.");
                            }
                            aluno.setNome(nomeAluno);
                            aluno.setTelefone(telefone);
                            aluno.setDataString(dataString);
                            aluno.setCpf(cpf);
                            aluno.setId_curso(idNovoCurso);

                            aluno.VerificaDataNascimento();
                            aluno.DatedataNascimento();
                            aluno.gerarMatricula();
                            alunoDAO.update(aluno, matriculaAntiga);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 3:
                        System.out.println("matricula do aluno a ser excluido");
                        long matriculaDelete = entry.nextInt();
                        alunoDAO.delete(matriculaDelete);
                        break;
                    case 4:
                        System.out.println("Dados");
                        System.out.println("Qual aluno que deseja ver os dados:\n1. esqueci minha matricula");
                        Long matriculaVef = entry.nextLong();
                        if (matriculaVef == 1) {
                            System.out.println("Função ainda não disponivel.");
                            System.out.println("Digite seu cpf: ");
                            entry.nextLine();
                            String cpf = entry.nextLine();
                            alunoDAO.recuperarMatricula(cpf);
                            break;
                        }
                        try {
                            alunoDAO.select(matriculaVef);
                            break;
                        } catch (Exception e) {
                            System.out.println("Erro:" + e.getMessage());
                            break;
                        }
                    default:
                        System.out.println("Opção invalida.");
                }


            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void menuAdministrador() {
        try {
            while (true) {
                Scanner entry = new Scanner(System.in);

                System.out.println("======MENU======");
                System.out.println("1 - Curso");
                System.out.println("2 - Aluno");
                System.out.println("3 - Administrador");
                System.out.println("0 - Sair");
                System.out.println("Escolha uma opção:\n");
                int opcao = entry.nextInt();
                switch (opcao) {
                    case 1:
                        menuCurso();
                        break;
                    case 2:
                        menuAluno();
                        break;
                    case 3:
                        menuAdmin();
                    case 0:
                        String saida = "voltando para o menu";
                        System.out.println("=================================");
                        System.out.printf("   🔄 %s   \n", saida);
                        System.out.print("   Carregando");

                        for (int i = 0; i < 3; i++) {
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                            System.out.print(".");
                        }

                        System.out.println("\n=================================");
                        main(null);
                    default:
                        System.out.println("Opção não disponivel.");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void menuUsuario() {
        try {
            while (true) {
                AlunoDAO alunoDAO = new AlunoDAO();
                Scanner entry = new Scanner(System.in);

                System.out.println("======MENU======");
                System.out.println("Bem vindo a Uninós.");
                System.out.println("1 - Dados do aluno");
                System.out.println("2 - alterar dados");
                System.out.println("0 - Sair");

                int opcao = entry.nextInt();
                entry.nextLine();
                switch (opcao) {
                    case 1:
                        System.out.println("Dados");
                        System.out.println("Digite a matricula:\n1. esqueci minha matricula");
                        Long matriculaVef = entry.nextLong();
                        entry.nextLine();
                        if (matriculaVef == 1) {
                            System.out.println("Digite seu cpf: ");
                            String cpf = entry.nextLine();
                            alunoDAO.recuperarMatricula(cpf);
                        }
                        break;
                    case 2:
                        while(true) {
                            try {
                                boolean vef = true;
                                System.out.println("==Alterar dados do aluno==");
                                System.out.println("Matrícula do aluno: ");
                                long matriculaAntiga = entry.nextLong();
                                entry.nextLine();

                                System.out.println("Nome do aluno: ");
                                String nomeAluno = entry.nextLine();

                                System.out.println("Telefone: ");
                                long telefone = entry.nextLong();
                                entry.nextLine();

                                System.out.println("Data de nascimento:");
                                System.out.println("Ex: 12051997 (Dia mês ano)");
                                String dataString = entry.nextLine();

                                System.out.println("CPF: ");
                                System.out.println("Ex: 12345678900");
                                String cpf = entry.nextLine();

                                System.out.println("Digite a nova senha: ");
                                String novaSenha = entry.nextLine();

                                System.out.println("Escolha o curso:");
                                System.out.println("Digite o nome do curso.");
                                CursoDAO cursoDAO = new CursoDAO();
                                cursoDAO.select();
                                String nomeCurso = entry.nextLine();
                                Curso curso = cursoDAO.existeCurso(nomeCurso);
                                int idNovoCurso = curso.getId();
                                Aluno aluno = alunoDAO.getAluno(matriculaAntiga);
                                if (aluno == null) {
                                    throw new IllegalArgumentException("Aluno nao encontrado.");
                                }
                                aluno.setNome(nomeAluno);
                                aluno.setTelefone(telefone);
                                aluno.setDataString(dataString);
                                aluno.setCpf(cpf);
                                aluno.setId_curso(idNovoCurso);
                                aluno.setSenha(novaSenha);
                                if (aluno.VerificaDataNascimento() && aluno.verificarSenha() && aluno.verificaNome()) {
                                    aluno.DatedataNascimento();
                                    aluno.gerarMatricula();
                                    alunoDAO.update(aluno, matriculaAntiga);
                                    break;
                                }
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    case 0:
                        String saida = "voltando para o menu";
                        System.out.println("=================================");
                        System.out.printf("   🔄 %s   \n", saida);
                        System.out.print("   Carregando");

                        for (int i = 0; i < 3; i++) {
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                            System.out.print(".");
                        }

                        System.out.println("\n=================================");
                        main(null);
                    default:
                        System.out.println("Opção inválida.");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void menuAdmin() {
        try {
            AutenticadorDAO autenticadorDAO = new AutenticadorDAO();
            AdminDAO adminDao = new AdminDAO();

            while (true) {
                Scanner entry = new Scanner(System.in);
                System.out.println("======MENU======");
                System.out.println("1 - Cadastrar administrador.");
                System.out.println("2 - Deletar administrador.");
                System.out.println("3 - Alterar dados do administrador.");
                System.out.println("4 - Exibir dados do administrador.");
                System.out.println("0 - Sair.");

                int opcao = entry.nextInt();
                entry.nextLine();
                switch (opcao) {
                    case 1:
                        while (true) {
                            try {
                                System.out.println("====Cadastrar administrador====");
                                System.out.println("Digite seu nome:\ncaso queira sair digite 'sair'");
                                String nome = entry.nextLine().toUpperCase();
                                if (nome == "SAIR") {
                                    break;
                                }
                                System.out.println("Digite uma  senha: ");
                                System.out.println("deve conter:\n•Letra maúscula e minuscula;\n•Caracter especial;\n•Número;\n•Entre 8 a 12 Caracteres.");
                                String senha = entry.nextLine();
                                Admin admin = new Admin(nome, senha);
                                if (admin.verificaNome() && admin.verificarSenha()) {
                                    admin.criarLogin();
                                    adminDao.insert(admin);
                                    break;
                                }
                            } catch (IllegalArgumentException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                    case 2:
                        System.out.println("===Deletar===");
                        System.out.println("digite o login do administrador:");
                        String loginDeletar = entry.nextLine();
                        Admin admindeletar = adminDao.select(loginDeletar);
                        System.out.printf("Deseja deletar o admin: %s", admindeletar.getNome());
                        System.out.println("1 - sim");
                        System.out.println("2 - não");
                        int opcao2 = entry.nextInt();
                        entry.nextLine();
                        switch (opcao2) {
                            case 1:
                                System.out.println("Digite seu login:");
                                String loginVerificar = entry.nextLine();
                                System.out.println("Digite sua senha: ");
                                String senha = entry.nextLine();
                                if (autenticadorDAO.autenticarAdmin(loginVerificar, senha)) {
                                    adminDao.delete(loginDeletar);
                                    break;
                                }
                            case 2:
                                break;
                        }
                        break;
                    case 3:
                        System.out.println("===Alterar dados===");
                        System.out.println("Digite seu login:");
                        String login = entry.nextLine();
                        Admin adminAntigo = adminDao.select(login);
                        if (adminAntigo != null) {
                            while (true) {
                                System.out.println("Digite o nome:");
                                String nomeAlterar = entry.nextLine().toUpperCase();
                                System.out.println("Digite uma senha:");
                                System.out.println("deve conter:\n•Letra maúscula e minuscula;\n•Caracter especial;\n•Número;\n•Entre 8 a 12 Caracteres.");
                                String senhaAlterar = entry.nextLine();
                                Admin admin = new Admin(nomeAlterar, senhaAlterar);
                                if (admin.verificaNome() && admin.verificarSenha()) {
                                    admin.criarLogin();
                                    int id = adminAntigo.getId();
                                    String loginNovo = admin.getLogin();
                                    adminDao.update(nomeAlterar, senhaAlterar, loginNovo, id);
                                    break;
                                } else {
                                    System.out.println("Erro ao alterar dados.");
                                }
                            }
                        }
                        break;
                    case 4:
                        System.out.println("===Exibir dados===");
                        System.out.println("Digite seu login:");
                        String loginExibir = entry.nextLine();
                        Admin admin = adminDao.select(loginExibir);
                        System.out.printf("Nome: %s\n", admin.getNome());
                        System.out.printf("Login: %s", admin.getLogin());
                        break;
                    case 0:
                        menuAdministrador();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
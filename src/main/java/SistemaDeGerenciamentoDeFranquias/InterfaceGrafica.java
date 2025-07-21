package SistemaDeGerenciamentoDeFranquias;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;

public class InterfaceGrafica {
    private JFrame frame = new JFrame("Sistema da franquia X");
    protected GerenciadorSistemaDono gerenciaDono = new GerenciadorSistemaDono();
    protected GerenciadorSistemaGerente gerenciaGerente = new GerenciadorSistemaGerente();
    protected GerenciadorSistemaVendedor gerenciaVendedor = new GerenciadorSistemaVendedor();

    public InterfaceGrafica(){
        //menu1SelecionaCargo();
        menuLogin();
        //sistemaDono();

        frame.setSize(250, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    void menu1SelecionaCargo(){
        JPanel selecionaCargo = new JPanel();

        //selecionaCargo.setLayout (new BorderLayout ());






        JButton Dono = new JButton("Dono");
        JButton Gerente = new JButton("Gerente");
        JButton Vendedor = new JButton("Vendedor");

        selecionaCargo.add(Dono);
        selecionaCargo.add(Gerente);
        selecionaCargo.add(Vendedor);

        //selecionaCargo.add (new JButton ("teste"), BorderLayout.CENTER);

        frame.setContentPane(selecionaCargo); //função que elimina painel anterior e adiciona outro
        frame.revalidate();
        frame.repaint();

        Dono.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botão 'Dono' clicado");
                selecionaCargo.setVisible(false);
                //menuLoginDono();
            }
        });
    }

    void menuLogin(){
        JPanel menuLogin = new JPanel();
        menuLogin.setLayout(new BoxLayout(menuLogin, BoxLayout.Y_AXIS));

        JRadioButton Dono = new JRadioButton("Dono");
        JRadioButton Gerente = new JRadioButton("Gerente");
        JRadioButton Vendedor = new JRadioButton("Vendedor");
        ButtonGroup group = new ButtonGroup();
        group.add(Dono);
        group.add(Gerente);
        group.add(Vendedor);

        menuLogin.add(Dono);
        menuLogin.add(Gerente);
        menuLogin.add(Vendedor);


        JTextField escreveCpf = new JTextField(20); // Campo de entrada de 20 colunas
        menuLogin.add(new JLabel("Cpf:"));
        menuLogin.add(escreveCpf);

        JTextField escreveSenha = new JTextField(20); // Campo de entrada de 20 colunas
        menuLogin.add(new JLabel("Senha:"));
        menuLogin.add(escreveSenha);


        escreveCpf.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    System.out.println("Tecla Enter pressionada");
                    escreveSenha.requestFocusInWindow();
                }
            }
        });

        escreveSenha.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    System.out.println("Tecla Enter pressionada");
                    String cpf = escreveCpf.getText();
                    String senha = escreveSenha.getText();

                    boolean validaLog = false;
                    if(Dono.isSelected())
                        validaLog = gerenciaDono.login(cpf,senha);
                    if(Gerente.isSelected())
                        validaLog = gerenciaGerente.login(cpf,senha);
                    if(Vendedor.isSelected())
                        validaLog = gerenciaVendedor.login(cpf,senha);

                    if(validaLog) {
                        exibeInformacao("CPF e senha corretos", "Login feito com sucesso");
                        menuLogin.setVisible(false);
                        sistemaDono();
                    }else
                        exibeException("CPF ou senha incorretos, digite novamente!", "Erro: LoginException: ");
                }
            }
        });

        frame.setContentPane(menuLogin); //função que elimina painel anterior e adiciona outro
        frame.revalidate();
        frame.repaint();
    }

    void confereLogin(){

    }

    void sistemaDono(){
        JPanel sistemaDono = new JPanel();

        JButton Voltar = new JButton("Voltar");
        sistemaDono.add(Voltar);


        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu menu1 = new JMenu ("Inico");
        menuBar.add (menu1);
        JMenuItem  menu1Item1 = new JMenuItem ("Item de Menu", KeyEvent.VK_T);
        menu1.add(menu1Item1);

        JMenu menu2 = new JMenu ("Lojas");
        menuBar.add(menu2);
        JMenuItem  menu2Item2 = new JMenuItem ("Cadastrar loja", KeyEvent.VK_T);
        menu2.add(menu2Item2);

        menu1Item1.addActionListener(e -> trocarTela(criarTela1()));
        menu2Item2.addActionListener(e -> trocarTela(cadastraLojas()));


        frame.setContentPane(sistemaDono); //função que elimina painel anterior e adiciona outro
        frame.revalidate();
        frame.repaint();

        Voltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botão 'Voltar' clicado");
                menuBar.removeAll();
                sistemaDono.setVisible(false);
                menuLogin();
            }
        });
    }


    // Metodo que troca paineis
    private void trocarTela(JPanel novaTela) {
        frame.setContentPane(novaTela);
        frame.revalidate(); // atualiza o layout
        frame.repaint();    // repinta a janela
    }

    // Primeira tela
    private JPanel criarTela1() {
        JPanel painel = new JPanel();
        painel.add(new JLabel("Você está na Tela 1"));
        painel.add(new JButton("Botão da Tela 1"));
        return painel;
    }

    // Tela cadastro de loja
    private JPanel cadastraLojas() {
        JPanel painelCadastroLoja = new JPanel(new BorderLayout());

        JLabel titulo = new JLabel("Cadastro de loja", SwingConstants.CENTER);
        painelCadastroLoja.add(titulo, BorderLayout.NORTH);

        JPanel subPainel = new JPanel();
        subPainel.setLayout(new BoxLayout(subPainel, BoxLayout.Y_AXIS));
        subPainel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel linhaEndereco = new JPanel(new FlowLayout(FlowLayout.CENTER));
        linhaEndereco.add(new JLabel("Endereço da loja:"));
        JTextField escreveEndereco = new JTextField(15);
        linhaEndereco.add(escreveEndereco);
        subPainel.add(linhaEndereco);

        JPanel linhaNome = new JPanel(new FlowLayout(FlowLayout.CENTER));
        linhaNome.add(new JLabel("Nome do gerente responsável:"));
        JTextField escreveNome = new JTextField(15);
        linhaNome.add(escreveNome);
        subPainel.add(linhaNome);

        JPanel linhaCpf = new JPanel(new FlowLayout(FlowLayout.CENTER));
        linhaCpf.add(new JLabel("Cpf do gerente responsável:"));
        JTextField escreveCpf = new JTextField(15);
        linhaCpf.add(escreveCpf);
        subPainel.add(linhaCpf);

        JPanel linhaEmail = new JPanel(new FlowLayout(FlowLayout.CENTER));
        linhaEmail.add(new JLabel("E-mail do gerente responsável:"));
        JTextField escreveEmail = new JTextField(15);
        linhaEmail.add(escreveEmail);
        subPainel.add(linhaEmail);

        painelCadastroLoja.add(subPainel, BorderLayout.CENTER);


        escreveEndereco.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    System.out.println("Tecla Enter pressionada");
                    escreveNome.requestFocusInWindow();
                }
            }
        });
        escreveNome.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    System.out.println("Tecla Enter pressionada");
                    escreveCpf.requestFocusInWindow();
                }
            }
        });
        escreveCpf.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    System.out.println("Tecla Enter pressionada");
                    escreveEmail.requestFocusInWindow();
                }
            }
        });

        escreveEmail.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    System.out.println("Tecla Enter pressionada");
                    String nome = escreveNome.getText();
                    String senha = escreveEmail.getText();

                    boolean validaLog = false;
                }
            }
        });

        return painelCadastroLoja;
    }






    public void exibeException(String menssagem, String titulo){
        JOptionPane.showMessageDialog(frame,
                menssagem,
                titulo,JOptionPane.ERROR_MESSAGE);
    }
    public void exibeInformacao(String menssagem, String titulo){
        JOptionPane.showMessageDialog(frame,
                menssagem,
                titulo,JOptionPane.INFORMATION_MESSAGE);
    }
}

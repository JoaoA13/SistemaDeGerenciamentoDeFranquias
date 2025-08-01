package SistemaDeGerenciamentoDeFranquias.Vision;

import SistemaDeGerenciamentoDeFranquias.Control.*;
import SistemaDeGerenciamentoDeFranquias.Exceptions.CadastroException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.LoginException;
import SistemaDeGerenciamentoDeFranquias.Model.Loja;
import SistemaDeGerenciamentoDeFranquias.Model.TipoUsuario;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class InterfaceGrafica {

    private static JFrame frame = new JFrame("Sistema da franquia X");
    protected GerenciadorSistemaDono gerenciaDono = new GerenciadorSistemaDono();
    protected GerenciadorSistemaGerente gerenciaGerente = new GerenciadorSistemaGerente();
    protected GerenciadorSistemaVendedor gerenciaVendedor = new GerenciadorSistemaVendedor();
    private GerenciadorDeLojas gerenciaDeLojas = new GerenciadorDeLojas();
    String cpf = "";

   public InterfaceGrafica(){
       menuLogin();
       //sistemaDono();
       frame.setSize(400, 400);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setLocationRelativeTo(null);
       frame.setVisible(true);
    }

    public void setFrame(JFrame frame){this.frame = frame;}

    public JFrame getFrame(){return frame;}

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

    public void menuLogin(){
        JPanel menuLogin = new JPanel();
        menuLogin.setLayout(new BoxLayout(menuLogin, BoxLayout.Y_AXIS));
        menuLogin.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel titulo = new JLabel("Login");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(new Color(34, 139, 34));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        menuLogin.add(titulo);

        JLabel labelCpf = new JLabel("Cpf");
        labelCpf.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuLogin.add(labelCpf);
        JTextField escreveCpf = new JTextField(20);
        escreveCpf.setMaximumSize(new Dimension(400, 25));
        menuLogin.add(escreveCpf);
        menuLogin.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel labelSenha = new JLabel("Senha");
        labelSenha.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuLogin.add(labelSenha);
        JTextField escreveSenha = new JTextField(20);
        escreveSenha.setMaximumSize(new Dimension(400, 25));
        menuLogin.add(escreveSenha);
        menuLogin.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton botaoLogin = new JButton("Fazer Login");
        botaoLogin.setBackground(new Color(34, 139, 34));
        botaoLogin.setForeground(Color.WHITE);
        botaoLogin.setFocusPainted(false);
        botaoLogin.setFont(new Font("Arial", Font.BOLD, 14));
        botaoLogin.setMaximumSize(new Dimension(200, 30));
        botaoLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuLogin.add(botaoLogin);

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
                    botaoLogin.doClick();
                }
            }
        });

        botaoLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    System.out.println("Tecla Enter pressionada");
                    cpf = escreveCpf.getText();
                    String senha = escreveSenha.getText();
                    System.out.println(cpf + " " + senha);

                    try{
                        String validaLog = null;
                        validaLog = GerenciadorSistema.login(cpf,senha);
                        if(GerenciadorSistema.identificaTipoUsuario(cpf).getTipo() == TipoUsuario.DONO) {
                            sistemaDono();
                            exibeInformacao(validaLog, "Login feito com sucesso");
                            menuLogin.setVisible(false);
                        }else if(GerenciadorSistema.identificaTipoUsuario(cpf).getTipo() == TipoUsuario.GERENTE) {
                            sistemaGerente();
                            exibeInformacao(validaLog, "Login feito com sucesso");
                            menuLogin.setVisible(false);
                        }else if(GerenciadorSistema.identificaTipoUsuario(cpf).getTipo() == TipoUsuario.VENDEDOR){
                            sistemaVendedor();
                            exibeInformacao(validaLog, "Login feito com sucesso");
                            menuLogin.setVisible(false);
                        }
                    }catch (LoginException mes) {
                        exibeException(mes.getMessage(),"Login falhou");
                    }
                }

        });

        frame.pack();
        frame.setContentPane(menuLogin);
        frame.setSize(400, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.revalidate();
        frame.repaint();
    }

    void sistemaDono(){
        IGAcoesDono acoes = new IGAcoesDono(this,gerenciaDeLojas);

        JPanel sistemaDono = new JPanel(new BorderLayout());

        for (Loja loja : GerenciadorDeLojas.getLojas().values()) {
            if(loja.getCpfGerente() == null)
                exibeException("A loja: " + loja.getCodigo() + " está sem gerente!","Loja sem gerente!");
        }

        JLabel titulo = new JLabel("Menu Inicial", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        sistemaDono.add(titulo, BorderLayout.NORTH);

        JPanel subPainel = new JPanel();
        subPainel.setLayout(new BoxLayout(subPainel, BoxLayout.Y_AXIS));

        subPainel.add(Box.createVerticalStrut(20));

        JPanel descricao = new JPanel(new FlowLayout(FlowLayout.CENTER));
        descricao.add(new JLabel("Esse é o menu inicial do sistema de gerenciamento destinado ao dono da franquia."));
        subPainel.add(descricao);

        subPainel.add(Box.createVerticalStrut(15));

        JButton sair = new JButton("Sair");
        sair.setAlignmentX(Component.CENTER_ALIGNMENT);
        subPainel.add(sair);

        subPainel.add(Box.createVerticalStrut(20));

        sistemaDono.add(subPainel, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();

        JMenu menu1 = new JMenu ("Inico");
        menuBar.add (menu1);
        JMenuItem  menu1Item1 = new JMenuItem ("Menu inicial", KeyEvent.VK_T);
        menu1.add(menu1Item1);

        JMenu lojas = new JMenu ("Lojas");
        menuBar.add(lojas);
        JMenuItem  cadastrarLoja = new JMenuItem ("Cadastrar loja", KeyEvent.VK_T);
        lojas.add(cadastrarLoja);
        JMenuItem  listaLoja = new JMenuItem ("Lista de lojas", KeyEvent.VK_T);
        lojas.add(listaLoja);

        JMenu gerentes = new JMenu ("Gerentes");
        menuBar.add(gerentes);
        JMenuItem  cadastrarGerente = new JMenuItem ("Cadastrar gerente", KeyEvent.VK_T);
        gerentes.add(cadastrarGerente);
        JMenuItem  listaGerente = new JMenuItem ("Lista de gerentes", KeyEvent.VK_T);
        gerentes.add(listaGerente);

        JMenu donos = new JMenu ("Donos");
        menuBar.add(donos);
        JMenuItem  cadastrarDono = new JMenuItem ("Cadastrar donos", KeyEvent.VK_T);
        donos.add(cadastrarDono);
        JMenuItem  listaDono = new JMenuItem ("Lista de donos", KeyEvent.VK_T);
        donos.add(listaDono);

        frame.setJMenuBar(menuBar);


        menu1Item1.addActionListener(e -> trocarTela(sistemaDono, 200, 200));

        cadastrarLoja.addActionListener(e -> trocarTela(acoes.cadastraLojas(), 500, 500));
        listaLoja.addActionListener(e -> trocarTela(acoes.listaLojas(), 500, 500));

        cadastrarGerente.addActionListener(e -> trocarTela(acoes.cadastraGerentes(), 500, 500));
        listaGerente.addActionListener(e -> trocarTela(acoes.listaGerente(), 500, 500));

        cadastrarDono.addActionListener(e -> trocarTela(acoes.cadastraDono(), 300, 400));
        listaDono.addActionListener(e -> trocarTela(acoes.listaDono(), 300, 400));


        frame.pack();
        frame.setContentPane(sistemaDono);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.revalidate();
        frame.repaint();

        sair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botão 'Sair' clicado");
                menuBar.removeAll();
                sistemaDono.setVisible(false);
                menuLogin();
            }
        });
    }

    void sistemaGerente()
    {
        JPanel sistemaGerente = new JPanel();
        IGAcoesGerente acoes = new IGAcoesGerente(this,gerenciaDeLojas,cpf);

        JLabel titulo = new JLabel("Menu Inicial", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        sistemaGerente.add(titulo, BorderLayout.NORTH);

        JPanel subPainel = new JPanel();
        subPainel.setLayout(new BoxLayout(subPainel, BoxLayout.Y_AXIS));

        subPainel.add(Box.createVerticalStrut(20));

        JPanel descricao = new JPanel(new FlowLayout(FlowLayout.CENTER));
        descricao.add(new JLabel("Esse é o menu inicial do sistema de gerenciamento destinado ao gerente da franquia."));
        subPainel.add(descricao);

        subPainel.add(Box.createVerticalStrut(15));

        JButton sair = new JButton("Sair");
        sair.setAlignmentX(Component.CENTER_ALIGNMENT);
        subPainel.add(sair);

        subPainel.add(Box.createVerticalStrut(20));

        sistemaGerente.add(subPainel, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu menu1 = new JMenu ("Inico");
        menuBar.add (menu1);
        JMenuItem  menu1Item1 = new JMenuItem ("Menu inicial", KeyEvent.VK_T);
        menu1.add(menu1Item1);

        menu1Item1.addActionListener(e -> trocarTela(sistemaGerente, 200, 200));


        JMenu menu2 = new JMenu ("Vendedores");
        menuBar.add(menu2);
        JMenuItem  menu2Item1 = new JMenuItem ("Cadastrar", KeyEvent.VK_T);
        menu2.add(menu2Item1);
        JMenuItem  menu2Item2 = new JMenuItem ("Excluir", KeyEvent.VK_T);
        menu2.add(menu2Item2);
        JMenuItem  menu2Item4 = new JMenuItem ("Lista de Vendedores", KeyEvent.VK_T);
        menu2.add(menu2Item4);

        menu2Item1.addActionListener(e -> trocarTela(acoes.cadastrar(cpf), 400, 300));
        menu2Item2.addActionListener(e -> trocarTela(acoes.excluir(cpf), 400, 200));
        menu2Item4.addActionListener(e -> trocarTela(acoes.listaDeVendedores(cpf), 400, 300));


        JMenu menu3 = new JMenu ("Pedidos");
        menuBar.add(menu3);
        JMenuItem  menu3Item1 = new JMenuItem ("Visualisar Pedidos", KeyEvent.VK_T);
        menu3.add(menu3Item1);
        JMenuItem  menu3Item2 = new JMenuItem ("Solicitações de Alteração", KeyEvent.VK_T);
        menu3.add(menu3Item2);

        menu3Item1.addActionListener(e -> trocarTela(acoes.visualizarPedidos(cpf), 800, 300));
        menu3Item2.addActionListener(e -> trocarTela(acoes.solicitacoesDeAlteracao(), 400, 300));


        JMenu menu4 = new JMenu ("Estoque");
        menuBar.add(menu4);
        JMenuItem  menu4Item1 = new JMenuItem ("Cadastrar Produtos", KeyEvent.VK_T);
        menu4.add(menu4Item1);
        JMenuItem  menu4Item2 = new JMenuItem ("Visualizar Lista de Produtos", KeyEvent.VK_T);
        menu4.add(menu4Item2);

        menu4Item1.addActionListener(e -> trocarTela(acoes.cadastrarProduto(cpf), 400, 300));
        menu4Item2.addActionListener(e -> trocarTela(acoes.visualizarListaDeProdutos(cpf), 600, 400));


        JMenu menu5 = new JMenu ("Relatórios");
        menuBar.add(menu5);
        JMenuItem  menu5Item1 = new JMenuItem ("Histórico de Vendas", KeyEvent.VK_T);
        menu5.add(menu5Item1);
        JMenuItem  menu5Item2 = new JMenuItem ("Lista de Clientes Recorrentes", KeyEvent.VK_T);
        menu5.add(menu5Item2);

        menu5Item1.addActionListener(e -> trocarTela(acoes.historicoDeVendas(), 400, 300));
        menu5Item2.addActionListener(e -> trocarTela(acoes.listaDeClientesRecorrentes(cpf), 400, 300));


        frame.pack();
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(sistemaGerente);
        frame.revalidate();
        frame.repaint();

        sair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botão 'Sair' clicado");
                menuBar.removeAll();
                sistemaGerente.setVisible(false);
                menuLogin();
            }
        });
    }

    void sistemaVendedor(){
        JPanel sistemaVendedor = new JPanel();
        IGAcoesVendedor acoes = new IGAcoesVendedor(this);

        JLabel titulo = new JLabel("Menu Inicial", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        sistemaVendedor.add(titulo, BorderLayout.NORTH);

        JPanel subPainel = new JPanel();
        subPainel.setLayout(new BoxLayout(subPainel, BoxLayout.Y_AXIS));

        subPainel.add(Box.createVerticalStrut(20));

        JPanel descricao = new JPanel(new FlowLayout(FlowLayout.CENTER));
        descricao.add(new JLabel("Esse é o menu inicial do sistema de gerenciamento destinado a um vendedor da franquia."));
        subPainel.add(descricao);

        subPainel.add(Box.createVerticalStrut(15));

        JButton sair = new JButton("Sair");
        sair.setAlignmentX(Component.CENTER_ALIGNMENT);
        subPainel.add(sair);

        subPainel.add(Box.createVerticalStrut(20));

        sistemaVendedor.add(subPainel, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu menu0 = new JMenu ("Inicio");
        menuBar.add(menu0);
        JMenuItem  menu0Item1 = new JMenuItem ("Menu inicial", KeyEvent.VK_T);
        menu0.add(menu0Item1);

        menu0Item1.addActionListener(e -> trocarTela(sistemaVendedor, 200, 200));

        JMenu menu1 = new JMenu ("Pedidos");
        menuBar.add(menu1);

        JMenuItem  menu1Item1 = new JMenuItem ("Cadastrar Pedidos", KeyEvent.VK_T);
        menu1.add(menu1Item1);
        JMenuItem  menu1Item2 = new JMenuItem ("Lista de Pedidos", KeyEvent.VK_T);
        menu1.add(menu1Item2);

        menu1Item1.addActionListener(e -> trocarTela(acoes.lancarPedido(cpf), 400, 400));
        menu1Item2.addActionListener(e -> trocarTela(acoes.listaDePedidos(cpf), 700, 600));

        frame.pack();
        frame.setSize(520, 300);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(sistemaVendedor);
        frame.revalidate();
        frame.repaint();

        sair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botão 'Sair' clicado");
                menuBar.removeAll();
                sistemaVendedor.setVisible(false);
                menuLogin();
            }
        });
    }

    // Metodo que troca paineis
    static void trocarTela(JPanel novaTela, int tam1, int tam2) {
        frame.setSize(tam1, tam2);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(novaTela);
        frame.revalidate();
        frame.repaint();
    }

    // Primeira tela
    private JPanel menuInicial() {
        JPanel painel1 = new JPanel();
        painel1.add(new JLabel("Você está na tela inicial"));
        JButton Sair = new JButton("Sair");
        painel1.add(Sair);

        Sair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botão 'Sair' clicado");
                painel1.setVisible(false);
                menuLogin();
            }
        });
        return painel1;
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
    protected JLabel criaCelula(String valor) {
        JLabel label = new JLabel(valor, SwingConstants.CENTER);
        label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        return label;
    }

    protected void atualizaFrame(JPanel painel,int larg,int alt){
        getFrame().pack();
        getFrame().setContentPane(painel);
        getFrame().setSize(larg, alt);
        getFrame().setLocationRelativeTo(null);
        getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getFrame().setVisible(true);
        getFrame().revalidate();
        getFrame().repaint();
    }
}
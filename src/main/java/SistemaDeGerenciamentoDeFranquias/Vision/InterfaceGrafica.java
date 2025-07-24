package SistemaDeGerenciamentoDeFranquias.Vision;

import SistemaDeGerenciamentoDeFranquias.Exceptions.CadastroException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.LoginException;
import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorDeLojas;
import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorSistemaDono;
import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorSistemaGerente;
import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorSistemaVendedor;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class InterfaceGrafica {
    private JFrame frame = new JFrame("Sistema da franquia X");
    protected GerenciadorSistemaDono gerenciaDono = new GerenciadorSistemaDono();
    protected GerenciadorSistemaGerente gerenciaGerente = new GerenciadorSistemaGerente();
    protected GerenciadorSistemaVendedor gerenciaVendedor = new GerenciadorSistemaVendedor();
    protected GerenciadorDeLojas gerenciador = new GerenciadorDeLojas();
   String cpf = "";

   public InterfaceGrafica(){
       //menuLogin();
       sistemaDono();
       frame.setSize(200, 200);
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

    public void menuLogin(){
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

        JTextField escreveCpf = new JTextField(20);// Campo de entrada de 20 colunas
        escreveCpf.setMaximumSize(new Dimension(400, 25));
        menuLogin.add(new JLabel("Cpf:"));
        menuLogin.add(escreveCpf);

        JTextField escreveSenha = new JTextField(20); // Campo de entrada de 20 colunas
        escreveSenha.setMaximumSize(new Dimension(400, 25));
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
                    cpf = escreveCpf.getText();
                    String senha = escreveSenha.getText();
                    System.out.println(cpf + " " + senha);
                    try{
                        String validaLog = null;
                        if(Dono.isSelected()) {
                            validaLog = gerenciaDono.login(cpf,senha);
                            exibeInformacao(validaLog, "Login feito com sucesso");
                            menuLogin.setVisible(false);
                            sistemaDono();
                        } else if (Gerente.isSelected()) {
                            validaLog = gerenciaGerente.login(cpf,senha);
                            exibeInformacao(validaLog, "Login feito com sucesso");
                            menuLogin.setVisible(false);
                            sistemaGerente();
                        } else if (Vendedor.isSelected()) {
                            validaLog = gerenciaVendedor.login(cpf,senha);
                            exibeInformacao(validaLog, "Login feito com sucesso");
                            menuLogin.setVisible(false);
                            sistemaDono();
                        }else
                            exibeException("Deve selecionar seu cargo!", "Login falhou");
                    }catch (LoginException mes) {
                        exibeException(mes.getMessage(),"Login falhou");
                    }
                }
            }
        });

        frame.setContentPane(menuLogin); //função que elimina painel anterior e adiciona outro
        frame.revalidate();
        frame.repaint();
    }

    void sistemaDono(){
        JPanel sistemaDono = new JPanel();
        IGAcoesDono acoes = new IGAcoesDono(this);

        JButton Sair = new JButton("Sair");
        sistemaDono.add(Sair);

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

        menu1Item1.addActionListener(e -> trocarTela(criarTela1(), 200, 200));
        menu2Item2.addActionListener(e -> trocarTela(acoes.cadastraLojas(), 500, 500));

        frame.setContentPane(sistemaDono); //função que elimina painel anterior e adiciona outro
        frame.revalidate();
        frame.repaint();

        Sair.addActionListener(new ActionListener() {
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
        IGAcoesGerente acoes = new IGAcoesGerente(this,cpf);

        JButton Sair = new JButton("Sair");
        sistemaGerente.add(Sair);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu menu1 = new JMenu ("Inico");
        menuBar.add (menu1);
        JMenuItem  menu1Item1 = new JMenuItem ("Item de Menu", KeyEvent.VK_T);
        menu1.add(menu1Item1);

        menu1Item1.addActionListener(e -> trocarTela(criarTela1(), 200, 200));


        JMenu menu2 = new JMenu ("Vendedores");
        menuBar.add(menu2);
        JMenuItem  menu2Item1 = new JMenuItem ("Cadastrar", KeyEvent.VK_T);
        menu2.add(menu2Item1);
        JMenuItem  menu2Item2 = new JMenuItem ("Excluir", KeyEvent.VK_T);
        menu2.add(menu2Item2);
        JMenuItem  menu2Item3 = new JMenuItem ("Editar", KeyEvent.VK_T);
        menu2.add(menu2Item3);
        JMenuItem  menu2Item4 = new JMenuItem ("Lista de Vendedores", KeyEvent.VK_T);
        menu2.add(menu2Item4);

        menu2Item1.addActionListener(e -> trocarTela(acoes.cadastrar(cpf), 400, 300));
        menu2Item2.addActionListener(e -> trocarTela(acoes.excluir(), 400, 200));
        menu2Item3.addActionListener(e -> trocarTela(acoes.editar(), 400, 200));
        menu2Item4.addActionListener(e -> trocarTela(acoes.listaDeVendedores(), 400, 300));


        JMenu menu3 = new JMenu ("Pedidos");
        menuBar.add(menu3);
        JMenuItem  menu3Item1 = new JMenuItem ("Visualisar Pedidos", KeyEvent.VK_T);
        menu3.add(menu3Item1);
        JMenuItem  menu3Item2 = new JMenuItem ("Solicitações de Alteração", KeyEvent.VK_T);
        menu3.add(menu3Item2);

        menu3Item1.addActionListener(e -> trocarTela(acoes.visualizarPedidos(), 400, 300));
        menu3Item2.addActionListener(e -> trocarTela(acoes.solicitacoesDeAlteracao(), 400, 300));


        JMenu menu4 = new JMenu ("Estoque");
        menuBar.add(menu4);
        JMenuItem  menu4Item1 = new JMenuItem ("Cadastrar Produtos", KeyEvent.VK_T);
        menu4.add(menu4Item1);
        JMenuItem  menu4Item2 = new JMenuItem ("Visualizar Lista de Produtos", KeyEvent.VK_T);
        menu4.add(menu4Item2);

        menu4Item1.addActionListener(e -> trocarTela(acoes.cadastrarProduto(), 400, 300));
        menu4Item2.addActionListener(e -> trocarTela(acoes.visualizarListaDeProdutos(), 400, 300));


        JMenu menu5 = new JMenu ("Relatórios");
        menuBar.add(menu5);
        JMenuItem  menu5Item1 = new JMenuItem ("Histórico de Vendas", KeyEvent.VK_T);
        menu5.add(menu5Item1);
        JMenuItem  menu5Item2 = new JMenuItem ("Lista de Clientes Recorrentes", KeyEvent.VK_T);
        menu5.add(menu5Item2);

        menu5Item1.addActionListener(e -> trocarTela(acoes.historicoDeVendas(), 400, 300));
        menu5Item2.addActionListener(e -> trocarTela(acoes.listaDeClientesRecorrentes(), 400, 300));


        frame.pack();
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(sistemaGerente);
        frame.revalidate();
        frame.repaint();

        Sair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botão 'Sair' clicado");
                menuBar.removeAll();
                sistemaGerente.setVisible(false);
                menuLogin();
            }
        });
    }

    // Metodo que troca paineis
    private void trocarTela(JPanel novaTela, int tam1, int tam2) {
        frame.setSize(tam1, tam2);
        frame.setLocationRelativeTo(null);
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
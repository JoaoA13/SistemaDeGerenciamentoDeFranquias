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

        frame.setSize(900, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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


        JTextField escreveNome = new JTextField(20); // Campo de entrada de 20 colunas
        menuLogin.add(new JLabel("Nome:"));
        menuLogin.add(escreveNome);

        JTextField escreveSenha = new JTextField(20); // Campo de entrada de 20 colunas
        menuLogin.add(new JLabel("Senha:"));
        menuLogin.add(escreveSenha);


        escreveNome.addKeyListener(new KeyAdapter() {
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
                    String nome = escreveNome.getText();
                    String senha = escreveSenha.getText();

                    boolean validaLog = false;
                    if(Dono.isSelected())
                        validaLog = gerenciaDono.login(nome,senha);
                    if(Gerente.isSelected())
                        validaLog = gerenciaGerente.login(nome,senha);
                    if(Vendedor.isSelected())
                        validaLog = gerenciaVendedor.login(nome,senha);

                    if(validaLog) {
                        exibeSucesso("CPF e senha corretos", "Login feito com sucesso");
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

    void sistemaDono(){
        JPanel sistemaDono = new JPanel();

        JButton Voltar = new JButton("Voltar");
        sistemaDono.add(Voltar);

//
//
//        JMenuBar menuBar = new JMenuBar();
//        frame.setJMenuBar(menuBar);
//
//        JMenu menu = new JMenu ("Menu");
//        menuBar.add (menu);
//        JMenu menu2 = new JMenu ("Menu2");
//        menuBar.add(menu2);
//
//        JMenuItem  menuItem = new JMenuItem ("Item de Menu", KeyEvent.VK_T);
//        menu.add(menuItem);
//
//        menu.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//
//            }
//        });


        frame.setContentPane(sistemaDono); //função que elimina painel anterior e adiciona outro
        frame.revalidate();
        frame.repaint();

        Voltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botão 'Voltar' clicado");
                sistemaDono.setVisible(false);
                menuLogin();
            }
        });


    }

    public void exibeException(String menssagem, String titulo){
        JOptionPane.showMessageDialog(frame,
                menssagem,
                titulo,JOptionPane.ERROR_MESSAGE);
    }
    public void exibeSucesso(String menssagem, String titulo){
        JOptionPane.showMessageDialog(frame,
                menssagem,
                titulo,JOptionPane.INFORMATION_MESSAGE);
    }
}

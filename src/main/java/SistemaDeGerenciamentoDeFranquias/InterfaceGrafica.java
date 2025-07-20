package SistemaDeGerenciamentoDeFranquias;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;

public class InterfaceGrafica {
    private JFrame frame = new JFrame("Sistema da franquia X");
    public GerenciadorSistemaDono ger = new GerenciadorSistemaDono();

    public InterfaceGrafica(){
        menu1SelecionaCargo();

        frame.setSize(900, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    void menu1SelecionaCargo(){
        JPanel selecionaCargo = new JPanel();


        JButton Dono = new JButton("Dono");
        JButton Gerente = new JButton("Gerente");
        JButton Vendedor = new JButton("Vendedor");

        selecionaCargo.add(Dono);
        selecionaCargo.add(Gerente);
        selecionaCargo.add(Vendedor);

        frame.setContentPane(selecionaCargo); //função que elimina painel anterior e adiciona outro
        frame.revalidate();
        frame.repaint();

        Dono.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selecionaCargo.setVisible(false);
                menuLoginDono();
            }
        });
    }

    void menuLoginDono(){
        JPanel loginDono = new JPanel();

        JButton Voltar = new JButton("Voltar");
        loginDono.add(Voltar);

        JTextField escreveNome = new JTextField(20); // Campo de entrada de 20 colunas
        loginDono.add(new JLabel("Nome:"));
        loginDono.add(escreveNome);

        JTextField escreveSenha = new JTextField(20); // Campo de entrada de 20 colunas
        loginDono.add(new JLabel("Senha:"));
        loginDono.add(escreveSenha);

        escreveNome.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String nome = escreveNome.getText();
                    String senha = escreveSenha.getText();
                    ger.login(nome,senha);
                    JOptionPane.showMessageDialog(frame, "Você digitou: " + nome);
                }
            }
        });

        escreveSenha.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String nome = escreveNome.getText();
                    String senha = escreveSenha.getText();
                    ger.login(nome,senha);
                    JOptionPane.showMessageDialog(frame, "Você digitou: " + nome);
                }
            }
        });




        frame.setContentPane(loginDono); //função que elimina painel anterior e adiciona outro
        frame.revalidate();
        frame.repaint();

        Voltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginDono.setVisible(false);
                menu1SelecionaCargo();
            }
        });
    }

}

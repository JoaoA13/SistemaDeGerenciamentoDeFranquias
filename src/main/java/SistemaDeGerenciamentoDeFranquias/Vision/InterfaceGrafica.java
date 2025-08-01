package SistemaDeGerenciamentoDeFranquias.Vision;

import SistemaDeGerenciamentoDeFranquias.Control.*;
import SistemaDeGerenciamentoDeFranquias.Exceptions.CadastroException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.LoginException;
import SistemaDeGerenciamentoDeFranquias.Model.Loja;
import SistemaDeGerenciamentoDeFranquias.Model.TipoUsuario;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class InterfaceGrafica{

    private static JFrame frame = new JFrame("Sistema da franquia X");
    String cpf = "";

    public InterfaceGrafica(){
    }

    public void setFrame(JFrame frame){this.frame = frame;}

    static public JFrame getFrame(){return frame;}



    // Metodo que troca paineis
    static void trocarTela(JPanel novaTela, int tam1, int tam2) {
        frame.setSize(tam1, tam2);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(novaTela);
        frame.revalidate();
        frame.repaint();
    }

    // Primeira tela
//    private JPanel menuInicial() {
//        JPanel painel1 = new JPanel();
//
//        JLabel titulo = new JLabel("Menu Inicial", SwingConstants.CENTER);
//        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
//        painel1.add(titulo, BorderLayout.NORTH);
//
//        JPanel subPainel = new JPanel();
//        subPainel.setLayout(new BoxLayout(subPainel, BoxLayout.Y_AXIS));
//
//        subPainel.add(Box.createVerticalStrut(20));
//
//        JPanel descricao = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        descricao.add(new JLabel("Esse é o menu inicial do sistema de gerenciamento destinado ao dono da franquia."));
//        subPainel.add(descricao);
//
//        subPainel.add(Box.createVerticalStrut(15));
//
//        JButton sair = new JButton("Sair");
//        sair.setAlignmentX(Component.CENTER_ALIGNMENT);
//        subPainel.add(sair);
//
//        subPainel.add(Box.createVerticalStrut(20));
//
//        painel1.add(subPainel, BorderLayout.CENTER);
//
//        sair.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("Botão 'Sair' clicado");
//                painel1.setVisible(false);
//                menuLogin();
//            }
//        });
//        return painel1;
//    }
}
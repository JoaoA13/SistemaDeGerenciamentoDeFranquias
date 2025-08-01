package SistemaDeGerenciamentoDeFranquias.Vision;

import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorDeLojas;
import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorSistemaDono;
import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorSistemaGerente;
import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorSistemaVendedor;
import SistemaDeGerenciamentoDeFranquias.Model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static SistemaDeGerenciamentoDeFranquias.Vision.InterfaceGrafica.getFrame;

public abstract class InterfaceBase {
     GerenciadorSistemaDono gerenciaDono = new GerenciadorSistemaDono();
     GerenciadorSistemaGerente gerenciaGerente = new GerenciadorSistemaGerente();
     GerenciadorSistemaVendedor gerenciaVendedor = new GerenciadorSistemaVendedor();
    GerenciadorDeLojas gerenciaDeLojas = new GerenciadorDeLojas();

    private InterfaceGrafica interfaceGrafica;
    public InterfaceBase(InterfaceGrafica interfaceGrafica){
        this.interfaceGrafica = interfaceGrafica;


    }
    public static void exibeException(String menssagem, String titulo){
        JOptionPane.showMessageDialog(getFrame(),
                menssagem,
                titulo,JOptionPane.ERROR_MESSAGE);
    }
    public static void exibeInformacao(String menssagem, String titulo){
        JOptionPane.showMessageDialog(getFrame(),
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

    public static String formatarCPF(String cpf) {
        return String.format("%s.%s.%s-%s", cpf.substring(0, 3), cpf.substring(3, 6), cpf.substring(6, 9), cpf.substring(9, 11));
    }

    private JPanel menuInicial() {
        JPanel painel1 = new JPanel();

        JLabel titulo = new JLabel("Menu Inicial", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        painel1.add(titulo, BorderLayout.NORTH);

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

        painel1.add(subPainel, BorderLayout.CENTER);

        sair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botão 'Sair' clicado");
                painel1.setVisible(false);
                menuLogin();
            }
        });
        return painel1;
    }
}

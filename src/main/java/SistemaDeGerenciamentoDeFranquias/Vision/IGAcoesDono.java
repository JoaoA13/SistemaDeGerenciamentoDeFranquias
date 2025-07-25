package SistemaDeGerenciamentoDeFranquias.Vision;

import SistemaDeGerenciamentoDeFranquias.Exceptions.CadastroException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class IGAcoesDono {
    private InterfaceGrafica interfaceGrafica;

    IGAcoesDono(InterfaceGrafica interfaceGrafica){
        this.interfaceGrafica = interfaceGrafica;
    }

    // Tela cadastro de loja
    protected JPanel cadastraLojas() {
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

        JButton Cadastrar = new JButton("Cadastrar");
        subPainel.add(Cadastrar);

        JButton Sair = new JButton("Sair");
        subPainel.add(Sair);

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
                    if(botaoCadastrar(escreveEndereco.getText(), escreveNome.getText(), escreveCpf.getText(), escreveEmail.getText())) {

                        escreveEndereco.setText("");
                        escreveNome.setText("");
                        escreveCpf.setText("");
                        escreveEmail.setText("");
                    }
                }
            }
        });

        Cadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botão 'Cadastrar' clicado");
                if(botaoCadastrar(escreveEndereco.getText(), escreveNome.getText(), escreveCpf.getText(), escreveEmail.getText())) {

                    escreveEndereco.setText("");escreveNome.setText("");escreveCpf.setText("");escreveEmail.setText("");
                }
            }
        });

        Sair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botão 'Sair' clicado");
                //menuBar.removeAll();
                painelCadastroLoja.setVisible(false);
                interfaceGrafica.menuLogin();
            }
        });
        return painelCadastroLoja;
    }

    protected JPanel excluirLojas(){
        JPanel painelExcluirLoja = new JPanel(new BorderLayout());

        JLabel titulo = new JLabel("Exclusão de loja", SwingConstants.CENTER);
        painelExcluirLoja.add(titulo, BorderLayout.NORTH);

        JPanel subPainel = new JPanel();
        subPainel.setLayout(new BoxLayout(subPainel, BoxLayout.Y_AXIS));
        subPainel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel linhaCodigo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        linhaCodigo.add(new JLabel("Código da loja:"));
        JTextField escreveEndereco = new JTextField(15);
        linhaCodigo.add(escreveEndereco);
        subPainel.add(linhaCodigo);

        JButton Excluir = new JButton("Excluir");
        subPainel.add(Excluir);

        JButton Sair = new JButton("Sair");
        subPainel.add(Sair);

        painelExcluirLoja.add(subPainel, BorderLayout.CENTER);

        Excluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botão 'Excluir' clicado");
//                if(botaoCadastrar(escreveEndereco.getText(), escreveNome.getText(), escreveCpf.getText(), escreveEmail.getText())) {
//
//                    escreveEndereco.setText("");
//                    escreveNome.setText("");
//                    escreveCpf.setText("");
//                    escreveEmail.setText("");
//                }
            }
        });

        Sair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botão 'Sair' clicado");
                //menuBar.removeAll();
                painelExcluirLoja.setVisible(false);
                interfaceGrafica.menuLogin();
            }
        });

        return painelExcluirLoja;
    }

    void exibeInformacaoLoja(){
        JPanel exibeInformacaoLoja = new JPanel(new BorderLayout());


    }


    boolean botaoCadastrar(String endereco, String nome, String cpf,String email){
            System.out.println("Tecla Enter pressionada");

        try{
                interfaceGrafica.gerenciaDono.cadastroLoja(endereco,nome,cpf,email);
                interfaceGrafica.exibeInformacao("Cadastro de Loja e gerente feitos corretamente","Cadastro de Loja e gerente feito com sucesso");
                return true;
            }catch (CadastroException mes) {
                interfaceGrafica.exibeException(mes.getMessage(),"Cadastro falhou");
            }
            //gerenciaDono.cadastroLoja(nome,senha);
        return false;
    }
}

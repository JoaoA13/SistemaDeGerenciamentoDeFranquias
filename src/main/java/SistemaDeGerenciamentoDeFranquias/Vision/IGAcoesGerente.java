package SistemaDeGerenciamentoDeFranquias.Vision;

import SistemaDeGerenciamentoDeFranquias.Exceptions.CadastroException;
import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorSistemaGerente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IGAcoesGerente {
    private InterfaceGrafica interfaceGrafica;
    private String cpf;

    GerenciadorSistemaGerente gerenciaGerente = new GerenciadorSistemaGerente();

    public IGAcoesGerente(InterfaceGrafica interfaceGrafica, String cpf) {
        this.interfaceGrafica = interfaceGrafica;
        this.cpf = cpf;
    }


    JPanel cadastrar(String cpfGerente){
        JPanel cadastro = new JPanel();
        cadastro.setLayout(new BoxLayout(cadastro, BoxLayout.Y_AXIS));
        cadastro.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel labelNome = new JLabel("Digite o nome do vendedor:");
        labelNome.setAlignmentX(Component.LEFT_ALIGNMENT);
        cadastro.add(labelNome);

        JTextField escreveNome = new JTextField(20);
        escreveNome.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        escreveNome.setAlignmentX(Component.LEFT_ALIGNMENT);
        cadastro.add(escreveNome);

        JLabel labelCpf = new JLabel("Digite o CPF do vendedor:");
        labelCpf.setAlignmentX(Component.LEFT_ALIGNMENT);
        cadastro.add(labelCpf);

        JTextField escreveCpf = new JTextField(20);
        escreveCpf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        escreveCpf.setAlignmentX(Component.LEFT_ALIGNMENT);
        cadastro.add(escreveCpf);

        JLabel labelSenha = new JLabel("Digite a senha do vendedor:");
        labelSenha.setAlignmentX(Component.LEFT_ALIGNMENT);
        cadastro.add(labelSenha);

        JTextField escreveSenha = new JTextField(20);
        escreveSenha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        escreveSenha.setAlignmentX(Component.LEFT_ALIGNMENT);
        cadastro.add(escreveSenha);

        JButton Sair = new JButton("Sair");
        JButton confirmar = new JButton("Confirmar");

        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new BoxLayout(botoesPanel, BoxLayout.X_AXIS));
        botoesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        botoesPanel.setMaximumSize(new Dimension(400, 30));

        botoesPanel.add(Sair);
        botoesPanel.add(Box.createHorizontalGlue());
        botoesPanel.add(confirmar);
        cadastro.add(botoesPanel);

        escreveNome.addActionListener(e -> escreveCpf.requestFocusInWindow());
        escreveCpf.addActionListener(e -> escreveSenha.requestFocusInWindow());
        escreveSenha.addActionListener(e -> confirmar.doClick());

        confirmar.addActionListener(e -> {
            System.out.println("Botão Confirmar clicado");
            String nome = escreveNome.getText().trim();
            String cpf = escreveCpf.getText().trim();
            String senha = escreveSenha.getText().trim();
            try {
                String msg = gerenciaGerente.lancarCadastro(nome, cpf, senha, cpfGerente);
                JOptionPane.showMessageDialog(null, msg, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                escreveNome.setText("");escreveCpf.setText("");escreveSenha.setText("");
            } catch (CadastroException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        Sair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botão 'Sair' clicado");
                cadastro.setVisible(false);
                interfaceGrafica.menuLogin();
            }
        });

        return cadastro;
    }

    JPanel excluir(){
        JPanel exclusao = new JPanel();
        exclusao.setLayout(new BoxLayout(exclusao, BoxLayout.Y_AXIS));
        exclusao.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel labelCpf = new JLabel("Digite o CPF do vendedor que deseja excluir do sistema:");
        labelCpf.setAlignmentX(Component.LEFT_ALIGNMENT);
        exclusao.add(labelCpf);

        JTextField escreveCpf = new JTextField(20);
        escreveCpf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        escreveCpf.setAlignmentX(Component.LEFT_ALIGNMENT);
        exclusao.add(escreveCpf);

        JButton Sair = new JButton("Sair");
        JButton confirmar = new JButton("Confirmar");

        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new BoxLayout(botoesPanel, BoxLayout.X_AXIS));
        botoesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        botoesPanel.setMaximumSize(new Dimension(400, 30));

        botoesPanel.add(Sair);
        botoesPanel.add(Box.createHorizontalGlue());
        botoesPanel.add(confirmar);
        exclusao.add(botoesPanel);

        escreveCpf.addActionListener(e -> confirmar.requestFocusInWindow());
        return exclusao;
    }

    JPanel editar(){
        JPanel edicao = new JPanel();
        edicao.setLayout(new BoxLayout(edicao, BoxLayout.Y_AXIS));
        edicao.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel labelCpf = new JLabel("Digite o CPF do vendedor que deseja editar as informações:");
        labelCpf.setAlignmentX(Component.LEFT_ALIGNMENT);
        edicao.add(labelCpf);

        JTextField escreveCpf = new JTextField(20);
        escreveCpf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        escreveCpf.setAlignmentX(Component.LEFT_ALIGNMENT);
        edicao.add(escreveCpf);

        JButton Sair = new JButton("Sair");
        JButton confirmar = new JButton("Confirmar");

        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new BoxLayout(botoesPanel, BoxLayout.X_AXIS));
        botoesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        botoesPanel.setMaximumSize(new Dimension(400, 30));

        botoesPanel.add(Sair);
        botoesPanel.add(Box.createHorizontalGlue());
        botoesPanel.add(confirmar);
        edicao.add(botoesPanel);

        escreveCpf.addActionListener(e -> confirmar.requestFocusInWindow());
        return edicao;
    }

    JPanel listaDeVendedores(){
        JPanel lista = new JPanel();
        lista.setLayout(new BoxLayout(lista, BoxLayout.Y_AXIS));
        lista.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton voltar = new JButton("Voltar");

        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new BoxLayout(botoesPanel, BoxLayout.X_AXIS));
        botoesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        botoesPanel.add(voltar);
        botoesPanel.add(Box.createHorizontalGlue());
        lista.add(botoesPanel);

        return lista;
    }

    JPanel visualizarPedidos(){
        JPanel visualizacao = new JPanel();
        visualizacao.setLayout(new BoxLayout(visualizacao, BoxLayout.Y_AXIS));
        visualizacao.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton voltar = new JButton("Voltar");

        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new BoxLayout(botoesPanel, BoxLayout.X_AXIS));
        botoesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        botoesPanel.add(voltar);
        botoesPanel.add(Box.createHorizontalGlue());
        visualizacao.add(botoesPanel);

        return visualizacao;
    }

    JPanel solicitacoesDeAlteracao(){
        JPanel solicitacoes = new JPanel();
        solicitacoes.setLayout(new BoxLayout(solicitacoes, BoxLayout.Y_AXIS));
        solicitacoes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton voltar = new JButton("Voltar");

        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new BoxLayout(botoesPanel, BoxLayout.X_AXIS));
        botoesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        botoesPanel.add(voltar);
        botoesPanel.add(Box.createHorizontalGlue());
        solicitacoes.add(botoesPanel);

        return solicitacoes;
    }

    JPanel cadastrarProduto(){
        JPanel cadastro = new JPanel();
        cadastro.setLayout(new BoxLayout(cadastro, BoxLayout.Y_AXIS));
        cadastro.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel labelNome = new JLabel("Digite o nome do produto:");
        labelNome.setAlignmentX(Component.LEFT_ALIGNMENT);
        cadastro.add(labelNome);

        JTextField escreveNome = new JTextField(20);
        escreveNome.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        escreveNome.setAlignmentX(Component.LEFT_ALIGNMENT);
        cadastro.add(escreveNome);

        JLabel labelPreco = new JLabel("Digite o preço do produto:");
        labelPreco.setAlignmentX(Component.LEFT_ALIGNMENT);
        cadastro.add(labelPreco);

        JTextField escrevePreco = new JTextField(20);
        escrevePreco.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        escrevePreco.setAlignmentX(Component.LEFT_ALIGNMENT);
        cadastro.add(escrevePreco);

        JLabel labelCaracteristicas = new JLabel("Digite as características do produto:");
        labelCaracteristicas.setAlignmentX(Component.LEFT_ALIGNMENT);
        cadastro.add(labelCaracteristicas);

        JTextField escreveCaracteristicas = new JTextField(20);
        escreveCaracteristicas.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        escreveCaracteristicas.setAlignmentX(Component.LEFT_ALIGNMENT);
        cadastro.add(escreveCaracteristicas);

        JLabel labelQuantidade = new JLabel("Digite a quantidade em estoque:");
        labelQuantidade.setAlignmentX(Component.LEFT_ALIGNMENT);
        cadastro.add(labelQuantidade);

        JTextField escreveQuantidade = new JTextField(20);
        escreveQuantidade.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        escreveQuantidade.setAlignmentX(Component.LEFT_ALIGNMENT);
        cadastro.add(escreveQuantidade);

        JButton voltar = new JButton("Voltar");
        JButton confirmar = new JButton("Confirmar");

        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new BoxLayout(botoesPanel, BoxLayout.X_AXIS));
        botoesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        botoesPanel.setMaximumSize(new Dimension(400, 30));

        botoesPanel.add(voltar);
        botoesPanel.add(Box.createHorizontalGlue());
        botoesPanel.add(confirmar);
        cadastro.add(botoesPanel);

        escreveNome.addActionListener(e -> escrevePreco.requestFocusInWindow());
        escrevePreco.addActionListener(e -> escreveCaracteristicas.requestFocusInWindow());
        escreveCaracteristicas.addActionListener(e -> escreveQuantidade.requestFocusInWindow());
        escreveQuantidade.addActionListener(e -> confirmar.requestFocusInWindow());

        return cadastro;
    }

    JPanel visualizarListaDeProdutos(){
        JPanel listaDeProd = new JPanel();
        listaDeProd.setLayout(new BoxLayout(listaDeProd, BoxLayout.Y_AXIS));
        listaDeProd.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton voltar = new JButton("Voltar");

        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new BoxLayout(botoesPanel, BoxLayout.X_AXIS));
        botoesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        botoesPanel.add(voltar);
        botoesPanel.add(Box.createHorizontalGlue());
        listaDeProd.add(botoesPanel);

        return listaDeProd;
    }

    JPanel historicoDeVendas(){
        JPanel historico = new JPanel();
        historico.setLayout(new BoxLayout(historico, BoxLayout.Y_AXIS));
        historico.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton voltar = new JButton("Voltar");

        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new BoxLayout(botoesPanel, BoxLayout.X_AXIS));
        botoesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        botoesPanel.add(voltar);
        botoesPanel.add(Box.createHorizontalGlue());
        historico.add(botoesPanel);

        return historico;
    }

    JPanel listaDeClientesRecorrentes(){
        JPanel listaClientes = new JPanel();
        listaClientes.setLayout(new BoxLayout(listaClientes, BoxLayout.Y_AXIS));
        listaClientes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton voltar = new JButton("Voltar");

        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new BoxLayout(botoesPanel, BoxLayout.X_AXIS));
        botoesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        botoesPanel.add(voltar);
        botoesPanel.add(Box.createHorizontalGlue());
        listaClientes.add(botoesPanel);

        return listaClientes;
    }
}

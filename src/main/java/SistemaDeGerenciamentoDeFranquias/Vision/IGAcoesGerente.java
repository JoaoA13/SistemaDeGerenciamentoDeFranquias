package SistemaDeGerenciamentoDeFranquias.Vision;

import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorDeLojas;
import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorSistema;
import SistemaDeGerenciamentoDeFranquias.Exceptions.CadastroException;
import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorSistemaGerente;
import SistemaDeGerenciamentoDeFranquias.Exceptions.EntradaException;
import SistemaDeGerenciamentoDeFranquias.Model.Loja;
import SistemaDeGerenciamentoDeFranquias.Model.Vendedor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class IGAcoesGerente {
    private InterfaceGrafica interfaceGrafica;
    private String cpf;
    private GerenciadorDeLojas gerenciaDeLojas;

    GerenciadorSistemaGerente gerenciaGerente = new GerenciadorSistemaGerente();

    public IGAcoesGerente(InterfaceGrafica interfaceGrafica,GerenciadorDeLojas gerenciaDeLojas, String cpf) {
        this.interfaceGrafica = interfaceGrafica;
        this.gerenciaDeLojas = gerenciaDeLojas;
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

        JLabel labelEmail = new JLabel("Digite o e-mail do vendedor:");
        labelEmail.setAlignmentX(Component.LEFT_ALIGNMENT);
        cadastro.add(labelEmail);

        JTextField escreveEmail = new JTextField(20);
        escreveEmail.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        escreveEmail.setAlignmentX(Component.LEFT_ALIGNMENT);
        cadastro.add(escreveEmail);

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
        escreveCpf.addActionListener(e -> escreveEmail.requestFocusInWindow());
        escreveEmail.addActionListener(e -> escreveSenha.requestFocusInWindow());
        escreveSenha.addActionListener(e -> confirmar.doClick());

        confirmar.addActionListener(e -> {
            System.out.println("Botão Confirmar clicado");
            String nome = escreveNome.getText().trim();
            String cpf = escreveCpf.getText().trim();
            String email = escreveEmail.getText().trim();
            String senha = escreveSenha.getText().trim();
            try {
                String msg = gerenciaGerente.lancarCadastro(nome, cpf, email, senha, cpfGerente);
                JOptionPane.showMessageDialog(null, msg, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                escreveNome.setText("");escreveCpf.setText("");escreveEmail.setText("");escreveSenha.setText("");
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

    JPanel excluir(String cpfGerente){
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

        escreveCpf.addActionListener(e -> confirmar.doClick());

        confirmar.addActionListener(e -> {
            System.out.println("Botão Confirmar clicado");
            String cpf = escreveCpf.getText().trim();
            try {
                String msg = gerenciaGerente.excluirVendedor(cpf, cpfGerente);
                JOptionPane.showMessageDialog(null, msg, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                escreveCpf.setText("");
            } catch (EntradaException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        return exclusao;
    }

    void editar(String cpfGerente, String cfpSelecionado){
        JPanel edicao = new JPanel();
        edicao.setLayout(new BoxLayout(edicao, BoxLayout.Y_AXIS));
        edicao.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] opcoes = {"Nome", "Email", "CPF", "Senha"};
        int escolha = JOptionPane.showOptionDialog(
                null,
                "Qual informação deseja editar?",
                "Editar Vendedor",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );

        JButton confirmar = new JButton("Confirmar");

        switch (escolha) {
            case 0:
                JLabel labelNome = new JLabel("Digite o novo Nome do vendedor:");
                labelNome.setAlignmentX(Component.LEFT_ALIGNMENT);
                edicao.add(labelNome);

                JTextField escreveNome = new JTextField(20);
                escreveNome.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
                escreveNome.setAlignmentX(Component.LEFT_ALIGNMENT);
                edicao.add(escreveNome);

                escreveNome.addActionListener(e -> confirmar.doClick());
                confirmar.addActionListener(e -> {
                    System.out.println("Botão Confirmar clicado");
                    String nome = escreveNome.getText().trim();
                    try {
                        String msg = gerenciaGerente.editarVendedor(nome, "", "", "", cpfGerente, cfpSelecionado);
                        JOptionPane.showMessageDialog(null, msg, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        escreveNome.setText("");
                    } catch (EntradaException ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao cadastrar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                });
                break;
            case 1:
                JLabel labelEmail = new JLabel("Digite o novo E-mail do vendedor:");
                labelEmail.setAlignmentX(Component.LEFT_ALIGNMENT);
                edicao.add(labelEmail);

                JTextField escreveEmail = new JTextField(20);
                escreveEmail.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
                escreveEmail.setAlignmentX(Component.LEFT_ALIGNMENT);
                edicao.add(escreveEmail);

                escreveEmail.addActionListener(e -> confirmar.doClick());
                confirmar.addActionListener(e -> {
                    System.out.println("Botão Confirmar clicado");
                    String email = escreveEmail.getText().trim();
                    try {
                        String msg = gerenciaGerente.editarVendedor("", "", email, "", cpfGerente, cfpSelecionado);
                        JOptionPane.showMessageDialog(null, msg, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        escreveEmail.setText("");
                    } catch (EntradaException ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao cadastrar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                });
                break;
            case 2:
                JLabel labelCpf = new JLabel("Digite o novo CPF do vendedor:");
                labelCpf.setAlignmentX(Component.LEFT_ALIGNMENT);
                edicao.add(labelCpf);

                JTextField escreveCpf = new JTextField(20);
                escreveCpf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
                escreveCpf.setAlignmentX(Component.LEFT_ALIGNMENT);
                edicao.add(escreveCpf);

                escreveCpf.addActionListener(e -> confirmar.doClick());
                confirmar.addActionListener(e -> {
                    System.out.println("Botão Confirmar clicado");
                    String cpf = escreveCpf.getText().trim();
                    try {
                        String msg = gerenciaGerente.editarVendedor("", cpf, "", "", cpfGerente, cfpSelecionado);
                        JOptionPane.showMessageDialog(null, msg, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        escreveCpf.setText("");
                    } catch (EntradaException ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao cadastrar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                });
                break;
            case 3:
                JLabel labelSenha = new JLabel("Digite a nova senha do vendedor:");
                labelSenha.setAlignmentX(Component.LEFT_ALIGNMENT);
                edicao.add(labelSenha);

                JTextField escreveSenha = new JTextField(20);
                escreveSenha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
                escreveSenha.setAlignmentX(Component.LEFT_ALIGNMENT);
                edicao.add(escreveSenha);

                escreveSenha.addActionListener(e -> confirmar.doClick());
                confirmar.addActionListener(e -> {
                    System.out.println("Botão Confirmar clicado");
                    String senha = escreveSenha.getText().trim();
                    try {
                        String msg = gerenciaGerente.editarVendedor("", "", "", senha, cpfGerente, cfpSelecionado);
                        JOptionPane.showMessageDialog(null, msg, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        escreveSenha.setText("");
                    } catch (EntradaException ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao cadastrar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                });
                break;
            default:}

        JButton Sair = new JButton("Sair");

        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new BoxLayout(botoesPanel, BoxLayout.X_AXIS));
        botoesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        botoesPanel.setMaximumSize(new Dimension(400, 30));

        botoesPanel.add(Sair);
        botoesPanel.add(Box.createHorizontalGlue());
        botoesPanel.add(confirmar);
        edicao.add(botoesPanel);

        InterfaceGrafica.trocarTela(edicao, 400, 200);
    }

    JPanel listaDeVendedores(String cpfGerente){
        JPanel lista = new JPanel();
        lista.setLayout(new BoxLayout(lista, BoxLayout.Y_AXIS));
        lista.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton voltar = new JButton("Voltar");

        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new BoxLayout(botoesPanel, BoxLayout.X_AXIS));
        botoesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        String[] colunas = {"Nome", "CPF", "e-mail"};
        Loja loja = gerenciaDeLojas.getLoja(cpfGerente);

        String[][] dados = new String[loja.getArmazenaVendedores().size()][3];
        int i = 0;
        for (Vendedor v : loja.getArmazenaVendedores().values()) {
            dados[i][0] = v.getNome();
            dados[i][1] = v.getCpf();
            dados[i][2] = v.getEmail();
            i++;
        }

        DefaultTableModel modelo = new DefaultTableModel(dados, colunas) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabela = new JTable(modelo); // <-- corrigido aqui
        JScrollPane scroll = new JScrollPane(tabela);
        lista.add(scroll, BorderLayout.CENTER);

        JPopupMenu menuPopup = new JPopupMenu();
        JMenuItem editarItem = new JMenuItem("Editar");
        JMenuItem excluirItem = new JMenuItem("Excluir");
        menuPopup.add(editarItem);
        menuPopup.add(excluirItem);

        tabela.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger() || SwingUtilities.isRightMouseButton(e)) {
                    int linha = tabela.rowAtPoint(e.getPoint());
                    if (linha >= 0 && linha < tabela.getRowCount()) {
                        tabela.setRowSelectionInterval(linha, linha);
                        String cpfSelecionado = (String) tabela.getValueAt(linha, 1);

                        editarItem.addActionListener(ae -> {
                            editar(cpfGerente, cpfSelecionado);
                        });

                        excluirItem.addActionListener(ae -> {
                            int confirm = JOptionPane.showConfirmDialog(lista,
                                    "Tem certeza que deseja excluir o vendedor com CPF " + cpfSelecionado + "?",
                                    "Confirmar exclusão",
                                    JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                try {
                                    gerenciaGerente.excluirVendedor(cpfSelecionado, cpfGerente);
                                } catch (EntradaException ex) {
                                    interfaceGrafica.exibeException(ex.getMessage(),"Exclusão falhou");
                                }
                                ((DefaultTableModel) tabela.getModel()).removeRow(linha);
                            }
                        });

                        menuPopup.show(tabela, e.getX(), e.getY());
                    }
                }
            }
        });

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

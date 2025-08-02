package SistemaDeGerenciamentoDeFranquias.Vision;

import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorDeLojas;
import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorSistema;
import SistemaDeGerenciamentoDeFranquias.Exceptions.CadastroException;
import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorSistemaGerente;
import SistemaDeGerenciamentoDeFranquias.Exceptions.EntradaException;
import SistemaDeGerenciamentoDeFranquias.Model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import static SistemaDeGerenciamentoDeFranquias.Control.GerenciadorDeLojas.getLoja;

public class IGAcoesGerente {
    private InterfaceGrafica interfaceGrafica;
    private String cpf;
    private GerenciadorDeLojas gerenciaDeLojas;

    GerenciadorSistemaGerente gerenciaGerente = new GerenciadorSistemaGerente();
    DecimalFormat formatadorReais = new DecimalFormat("R$ #,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
    DecimalFormat formatadorPreco = new DecimalFormat("R$ #,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));


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

        JButton voltar = new JButton("voltar");
        JButton confirmar = new JButton("Confirmar");

        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new BoxLayout(botoesPanel, BoxLayout.X_AXIS));
        botoesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        botoesPanel.setMaximumSize(new Dimension(400, 30));

        botoesPanel.add(voltar);
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

        voltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botão 'voltar' clicado");
                cadastro.setVisible(false);
                interfaceGrafica.sistemaGerente();
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

        JButton voltar = new JButton("Voltar");
        JButton confirmar = new JButton("Confirmar");

        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new BoxLayout(botoesPanel, BoxLayout.X_AXIS));
        botoesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        botoesPanel.setMaximumSize(new Dimension(400, 30));

        botoesPanel.add(voltar);
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

        voltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botão 'voltar' clicado");
                exclusao.setVisible(false);
                interfaceGrafica.sistemaGerente();
            }
        });

        return exclusao;
    }

    void editar(String cpfGerente, String cpfSelecionado){
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
                JLabel labelNome = new JLabel("Digite o novo nome para o Vendedor:");
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
                        String msg = gerenciaGerente.editarVendedor(nome, "", "", "", cpfGerente, cpfSelecionado);
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
                        String msg = gerenciaGerente.editarVendedor("", "", email, "", cpfGerente, cpfSelecionado);
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
                        String msg = gerenciaGerente.editarVendedor("", cpf, "", "", cpfGerente, cpfSelecionado);
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
                        String msg = gerenciaGerente.editarVendedor("", "", "", senha, cpfGerente, cpfSelecionado);
                        JOptionPane.showMessageDialog(null, msg, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        escreveSenha.setText("");
                    } catch (EntradaException ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao cadastrar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                });
                break;
            default:}

        JButton voltar = new JButton("Voltar");

        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new BoxLayout(botoesPanel, BoxLayout.X_AXIS));
        botoesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        botoesPanel.setMaximumSize(new Dimension(400, 30));

        botoesPanel.add(voltar);
        botoesPanel.add(Box.createHorizontalGlue());
        botoesPanel.add(confirmar);
        edicao.add(botoesPanel);

        voltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botão 'voltar' clicado");
                edicao.setVisible(false);
                listaDeVendedores(cpfGerente);
            }
        });

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

        String[] colunas = {"Nome", "CPF", "e-mail","Valor total de vendas","Volume de vendas"};

        Loja loja = getLoja(cpfGerente);
        String[][] dados = new String[loja.getArmazenaVendedores().size()][5];
        Vendedor[] v = loja.vendedoresVolume();
        int i = 0;
        for (int j = 0; j < loja.getArmazenaVendedores().size();j++ ) {
            dados[i][0] = v[j].getNome();
            dados[i][1] = v[j].getCpf();
            dados[i][2] = v[j].getEmail();
            dados[i][3] = formatadorReais.format(v[j].getValorVenda());
            dados[i][4] = Integer.toString(v[j].getPedidosOficial().size());
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

        voltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botão 'voltar' clicado");
                lista.setVisible(false);
                interfaceGrafica.sistemaGerente();
            }
        });

        interfaceGrafica.atualizaFrame(lista,500,300);

        return lista;

    }

    JPanel visualizarPedidos(String cpf){
        JPanel visualizacao = new JPanel();
        visualizacao.setLayout(new BoxLayout(visualizacao, BoxLayout.Y_AXIS));
        visualizacao.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton voltar = new JButton("Voltar");

        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new BoxLayout(botoesPanel, BoxLayout.X_AXIS));
        botoesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        Loja loja = getLoja(cpf);
        int tamanho= 0;
        if(getLoja(cpf) != null) {
            for (Vendedor vendedor : loja.getArmazenaVendedores().values())
                tamanho += vendedor.getPedidosOficial().size();
        }
        String[] colunas = {"Código", "CPF do vendedor" , "CPF do cliente", "Data", "Hora", "Forma de Pagamento","Taxa de entrega", "Valor total"};

        String[][] dados = new String[tamanho][8];

        DecimalFormat formatadorPreco = new DecimalFormat("R$ #,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        DecimalFormat formatadorQuant = new DecimalFormat("00");

        int i = 0;
        for (Vendedor vendedor: loja.getArmazenaVendedores().values())
            for (Pedido p : vendedor.getPedidosOficial().values()) {
                dados[i][0] = p.getCodigo();
                dados[i][1] = vendedor.getCpf();
                dados[i][2] = p.getCliente().getCpf();
                dados[i][3] = String.valueOf(p.getData());
                dados[i][4] = String.valueOf(p.getHora());
                dados[i][5] = p.getFormaDePagamento();
                dados[i][6] = formatadorPreco.format(p.getTaxaEntrega());
                dados[i][7] = formatadorPreco.format(p.getValorTotal());
                i++;
            }

        DefaultTableModel modelo = new DefaultTableModel(dados, colunas) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);
        visualizacao.add(scroll, BorderLayout.CENTER);

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
                            //editar( cpf, cpfSelecionado);
                        });

                        excluirItem.addActionListener(ae -> {
//                            int confirm = JOptionPane.showConfirmDialog(visualizacao,
//                                    "Tem certeza que deseja excluir o vendedor com CPF " + cpfSelecionado + "?",
//                                    "Confirmar exclusão",
//                                    JOptionPane.YES_NO_OPTION);
//                            if (confirm == JOptionPane.YES_OPTION) {
//                                try {
//                                    gerenciaGerente.excluirVendedor(cpfSelecionado, cpf);
//                                } catch (EntradaException ex) {
//                                    interfaceGrafica.exibeException(ex.getMessage(),"Exclusão falhou");
//                                }
//                                ((DefaultTableModel) tabela.getModel()).removeRow(linha);
//                            }
                        });

                        menuPopup.show(tabela, e.getX(), e.getY());
                    }
                }
            }
        });

        botoesPanel.add(voltar);
        botoesPanel.add(Box.createHorizontalGlue());
        visualizacao.add(botoesPanel);

        voltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botão 'voltar' clicado");
                visualizacao.setVisible(false);
                interfaceGrafica.sistemaGerente();
            }
        });


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

        //Lista de pedidos
        Loja loja = getLoja(cpf);
        int tamanho= 0;
        if(getLoja(cpf) != null && loja.getArmazenaPedidosAltera() != null) {
                tamanho += loja.getArmazenaPedidosAltera().size();
        }
        String[] colunas = {"Código", "CPF do vendedor" , "CPF do cliente", "Data", "Hora", "Forma de Pagamento","Taxa de entrega", "Valor total"};

        String[][] dados = new String[tamanho][8];

        DecimalFormat formatadorPreco = new DecimalFormat("R$ #,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        DecimalFormat formatadorQuant = new DecimalFormat("00");

        int i = 0;
        if(loja.getArmazenaPedidosAltera() != null)
            for (Pedido p : loja.getArmazenaPedidosAltera().values()) {
                dados[i][0] = p.getCodigo();
                dados[i][1] = p.getCpfVendedor();
                dados[i][2] = p.getCliente().getCpf();
                dados[i][3] = String.valueOf(p.getData());
                dados[i][4] = String.valueOf(p.getHora());
                dados[i][5] = p.getFormaDePagamento();
                dados[i][6] = formatadorPreco.format(p.getTaxaEntrega());
                dados[i][7] = formatadorPreco.format(p.getValorTotal());
                i++;
            }

        DefaultTableModel modelo = new DefaultTableModel(dados, colunas) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);
        solicitacoes.add(scroll, BorderLayout.CENTER);

        JPopupMenu menuPopup = new JPopupMenu();
        JMenuItem visualizar = new JMenuItem("Visualizar");
        JMenuItem editarItem = new JMenuItem("Editar");
        JMenuItem excluirItem = new JMenuItem("Excluir");
        menuPopup.add(editarItem);
        menuPopup.add(excluirItem);
        menuPopup.add(visualizar);

        //---------------------------------

        tabela.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger() || SwingUtilities.isRightMouseButton(e)) {
                    int linha = tabela.rowAtPoint(e.getPoint());
                    if (linha >= 0 && linha < tabela.getRowCount()) {
                        tabela.setRowSelectionInterval(linha, linha);
                        String codigoSelecionado = (String) tabela.getValueAt(linha, 4);

                        editarItem.addActionListener(ae -> {
                            //editarProd(cpfGerente, codigoSelecionado);
                        });

                        excluirItem.addActionListener(ae -> {
//                            int confirm = JOptionPane.showConfirmDialog(lista,
//                                    "Tem certeza que deseja excluir o produto com o " + codigoSelecionado + "?",
//                                    "Confirmar exclusão",
//                                    JOptionPane.YES_NO_OPTION);
//                            if (confirm == JOptionPane.YES_OPTION) {
//                                gerenciaGerente.excluirProdutos(codigoSelecionado, cpfGerente);
//                                ((DefaultTableModel) tabela.getModel()).removeRow(linha);
//                            }
                        });

                        for (ActionListener al : visualizar.getActionListeners()) {
                            visualizar.removeActionListener(al);
                        }
                        visualizar.addActionListener(ae -> {
                            //exibeAlteracao(codigo,cpfVendedor);
                        });

                        menuPopup.show(tabela, e.getX(), e.getY());
                    }
                }
            }
        });

        //botao voltar -------------
        botoesPanel.add(voltar);
        botoesPanel.add(Box.createHorizontalGlue());
        solicitacoes.add(botoesPanel);

        voltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botão 'voltar' clicado");
                solicitacoes.setVisible(false);
                interfaceGrafica.sistemaGerente();
            }
        });
        return solicitacoes;
    }

    protected void exibeAlteracao(String codigo,String cpfVendedor){
        Pedido pedido = getLoja(cpf).getVendedor(cpfVendedor).getPedido(codigo);

        JFrame exibe = new JFrame("Informações da venda de pedido: " + codigo);
        exibe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        exibe.setSize(450, 600);
        exibe.setLocationRelativeTo(null);

        JPanel exibeInformacaoLoja = new JPanel(new BorderLayout(10, 10));
        exibeInformacaoLoja.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] colunas = {"Atual", "Solicitação de alteração"};
        Loja loja = getLoja(cpf);

        String[][] dados = new String[loja.getArmazenaAlteracao().getAlteracoes().size()][2];

        //DecimalFormat formatadorPreco = new DecimalFormat("R$ #,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        DecimalFormat formatadorQuant = new DecimalFormat("00");

        int i = 0;
        for (Produto p : pedido.getProdutos().values()) { /// VOU TER QUE CRIAR UM ETODO NO GERENCIADOR DO GERENTE, QUE RECEBE O CODIGO DO PEDIDO, PROCURA NO PARA DE ALTERAÇÕES FAZ UM IF PRA VER QUAL O TIPO E RETORNA UMA STRING, SO PARA EXIBIÇÃO
        /// DESSE METODO TEM QUE TER UM TREM PRA SELECIONAR ALTERAÇÃO IGUAL SELECIONA PRODUTO, UM BOTAO PRA APROVAR OU REJEITAR
            dados[i][0] = p.getNomeProd();
            dados[i][1] = formatadorPreco.format(p.getPreco()); // Ex: R$ 12,34
            dados[i][2] = p.getCarac();
            dados[i][3] = formatadorQuant.format(p.getQuant()); // Ex: 3.000
            dados[i][4] = p.getCodigoProd();
            i++;
        }
        DefaultTableModel modelo = new DefaultTableModel(dados, colunas) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabela1 = new JTable(modelo);

        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        for (int col = 0; col < tabela1.getColumnCount(); col++) {
            tabela1.getColumnModel().getColumn(col).setCellRenderer(centralizado);
        }

        JScrollPane scroll = new JScrollPane(tabela1);
        exibeInformacaoLoja.add(scroll, BorderLayout.CENTER);

        JPanel painelBotao = new JPanel();
        JButton sair = new JButton("Fechar");
        sair.addActionListener(e -> exibe.dispose());
        sair.addActionListener(e -> exibe.removeAll());
        painelBotao.add(sair);

        exibeInformacaoLoja.add(painelBotao, BorderLayout.SOUTH);

        exibe.setContentPane(exibeInformacaoLoja);
        exibe.setVisible(true);
        exibe.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.out.println("Janela foi fechada");
            }
        });
    }

    JPanel cadastrarProduto(String cpfGerente){
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

        JLabel labelCodigo = new JLabel("Digite código do produto:");
        labelCodigo.setAlignmentX(Component.LEFT_ALIGNMENT);
        cadastro.add(labelCodigo);

        JTextField escreveCodigo = new JTextField(20);
        escreveCodigo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        escreveCodigo.setAlignmentX(Component.LEFT_ALIGNMENT);
        cadastro.add(escreveCodigo);

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
        escreveQuantidade.addActionListener(e -> escreveCodigo.requestFocusInWindow());
        escreveCodigo.addActionListener(e -> confirmar.doClick());

        confirmar.addActionListener(e -> {
            System.out.println("Botão Confirmar clicado");
            String nome = escreveNome.getText().trim();
            String preco = escrevePreco.getText().trim();
            String carac = escreveCaracteristicas.getText().trim();
            String quant = escreveQuantidade.getText().trim();
            String codigo = escreveCodigo.getText().trim();
            try {
                String msg = gerenciaGerente.lancarProduto(nome, preco, carac, quant, codigo, cpfGerente);
                JOptionPane.showMessageDialog(null, msg, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                escreveNome.setText("");escrevePreco.setText("");escreveCaracteristicas.setText("");escreveQuantidade.setText("");escreveCodigo.setText("");
            } catch (CadastroException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        voltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botão 'voltar' clicado");
                cadastro.setVisible(false);
                interfaceGrafica.sistemaGerente();
            }
        });

        return cadastro;
    }

    JPanel visualizarListaDeProdutos(String cpfGerente){
        JPanel lista = new JPanel();
        lista.setLayout(new BoxLayout(lista, BoxLayout.Y_AXIS));
        lista.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton voltar = new JButton("Voltar");

        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new BoxLayout(botoesPanel, BoxLayout.X_AXIS));
        botoesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        String[] colunas = {"Nome", "preço", "características", "quantidade", "código"};
        Loja loja = getLoja(cpfGerente);

        String[][] dados = new String[loja.getArmazenaProdutos().size()][5];

        DecimalFormat formatadorPreco = new DecimalFormat("R$ #,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        DecimalFormat formatadorQuant = new DecimalFormat("00");

        int i = 0;
        for (Produto p : loja.getArmazenaProdutos().values()) {
            dados[i][0] = p.getNomeProd();
            dados[i][1] = formatadorPreco.format(p.getPreco()); // Ex: R$ 12,34
            dados[i][2] = p.getCarac();
            dados[i][3] = formatadorQuant.format(p.getQuant()); // Ex: 3.000
            dados[i][4] = p.getCodigoProd();
            i++;
        }

        DefaultTableModel modelo = new DefaultTableModel(dados, colunas) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabela = new JTable(modelo);

        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        for (int col = 0; col < tabela.getColumnCount(); col++) {
            tabela.getColumnModel().getColumn(col).setCellRenderer(centralizado);
        }

        tabela.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.CENTER);

                try {
                    int quant = Integer.parseInt(value.toString());

                    if (quant < 10) {
                        setText("⚠️ " + String.format("%02d", quant));
                        setForeground(Color.RED);
                        setToolTipText("Estoque baixo!");
                    } else {
                        setText(String.format("%02d", quant));
                        setForeground(Color.BLACK);
                        setToolTipText(null); // Remove o tooltip quando não for necessário
                    }

                } catch (NumberFormatException e) {
                    setText(value.toString());
                    setForeground(Color.BLACK);
                    setToolTipText(null);
                }

                return c;
            }
        });

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
                        String codigoSelecionado = (String) tabela.getValueAt(linha, 4);

                        editarItem.addActionListener(ae -> {
                            editarProd(cpfGerente, codigoSelecionado);
                        });

                        excluirItem.addActionListener(ae -> {
                            int confirm = JOptionPane.showConfirmDialog(lista,
                                    "Tem certeza que deseja excluir o produto com o " + codigoSelecionado + "?",
                                    "Confirmar exclusão",
                                    JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                    gerenciaGerente.excluirProdutos(codigoSelecionado, cpfGerente);
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

        voltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botão 'voltar' clicado");
                lista.setVisible(false);
                interfaceGrafica.sistemaGerente();
            }
        });

        interfaceGrafica.atualizaFrame(lista,500,300);

        return lista;
    }

    void editarProd(String cpfGerente, String codigoSelecionado){
        JPanel edicao = new JPanel();
        edicao.setLayout(new BoxLayout(edicao, BoxLayout.Y_AXIS));
        edicao.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] opcoes = {"Nome", "Preço", "Características", "Quantidade", "Código"};
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
                JLabel labelNome = new JLabel("Digite o novo Nome do produto:");
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
                        String msg = gerenciaGerente.editarProduto(nome, "", "", "","", cpfGerente, codigoSelecionado);
                        JOptionPane.showMessageDialog(null, msg, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        escreveNome.setText("");
                    } catch (EntradaException ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao editar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                });
                break;
            case 1:
                JLabel labelPreco = new JLabel("Digite o novo Preço do produto:");
                labelPreco.setAlignmentX(Component.LEFT_ALIGNMENT);
                edicao.add(labelPreco);

                JTextField escrevePreco = new JTextField(20);
                escrevePreco.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
                escrevePreco.setAlignmentX(Component.LEFT_ALIGNMENT);
                edicao.add(escrevePreco);

                escrevePreco.addActionListener(e -> confirmar.doClick());
                confirmar.addActionListener(e -> {
                    System.out.println("Botão Confirmar clicado");
                    String preco = escrevePreco.getText().trim();
                    try {
                        String msg = gerenciaGerente.editarProduto("", preco, "", "","", cpfGerente, codigoSelecionado);
                        JOptionPane.showMessageDialog(null, msg, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        escrevePreco.setText("");
                    } catch (EntradaException ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao editar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                });
                break;
            case 2:
                JLabel labelCarac = new JLabel("Digite as novas características:");
                labelCarac.setAlignmentX(Component.LEFT_ALIGNMENT);
                edicao.add(labelCarac);

                JTextField escreveCarac = new JTextField(20);
                escreveCarac.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
                escreveCarac.setAlignmentX(Component.LEFT_ALIGNMENT);
                edicao.add(escreveCarac);

                escreveCarac.addActionListener(e -> confirmar.doClick());
                confirmar.addActionListener(e -> {
                    System.out.println("Botão Confirmar clicado");
                    String carac = escreveCarac.getText().trim();
                    try {
                        String msg = gerenciaGerente.editarProduto("", "", carac, "","", cpfGerente, codigoSelecionado);
                        JOptionPane.showMessageDialog(null, msg, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        escreveCarac.setText("");
                    } catch (EntradaException ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao editar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                });
                break;
            case 3:
                JLabel labelQuant = new JLabel("Digite a nova quantidade do produto:");
                labelQuant.setAlignmentX(Component.LEFT_ALIGNMENT);
                edicao.add(labelQuant);

                JTextField escreveQuant = new JTextField(20);
                escreveQuant.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
                escreveQuant.setAlignmentX(Component.LEFT_ALIGNMENT);
                edicao.add(escreveQuant);

                escreveQuant.addActionListener(e -> confirmar.doClick());
                confirmar.addActionListener(e -> {
                    System.out.println("Botão Confirmar clicado");
                    String quant = escreveQuant.getText().trim();
                    try {
                        String msg = gerenciaGerente.editarProduto("", "", "", quant,"", cpfGerente, codigoSelecionado);
                        JOptionPane.showMessageDialog(null, msg, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        escreveQuant.setText("");
                    } catch (EntradaException ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao editar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                });
                break;
            case 4:
                JLabel labelCod = new JLabel("Digite o novo código:");
                labelCod.setAlignmentX(Component.LEFT_ALIGNMENT);
                edicao.add(labelCod);

                JTextField escreveCod = new JTextField(20);
                escreveCod.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
                escreveCod.setAlignmentX(Component.LEFT_ALIGNMENT);
                edicao.add(escreveCod);

                escreveCod.addActionListener(e -> confirmar.doClick());
                confirmar.addActionListener(e -> {
                    System.out.println("Botão Confirmar clicado");
                    String codigo = escreveCod.getText().trim();
                    try {
                        String msg = gerenciaGerente.editarProduto("", "", "", "", codigo, cpfGerente, codigoSelecionado);
                        JOptionPane.showMessageDialog(null, msg, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        escreveCod.setText("");
                    } catch (EntradaException ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao editar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                });
                break;
            default:}

        JButton voltar = new JButton("Voltar");

        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new BoxLayout(botoesPanel, BoxLayout.X_AXIS));
        botoesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        botoesPanel.setMaximumSize(new Dimension(400, 30));

        botoesPanel.add(voltar);
        botoesPanel.add(Box.createHorizontalGlue());
        botoesPanel.add(confirmar);
        edicao.add(botoesPanel);

        voltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botão 'voltar' clicado");
                edicao.setVisible(false);
                visualizarListaDeProdutos(cpfGerente);
            }
        });

        InterfaceGrafica.trocarTela(edicao, 400, 200);
    }

    JPanel historicoDeVendas(){
        JPanel historico = new JPanel();
        historico.setLayout(new BoxLayout(historico, BoxLayout.Y_AXIS));
        historico.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton voltar = new JButton("Voltar");

        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new BoxLayout(botoesPanel, BoxLayout.X_AXIS));
        botoesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        Loja loja = getLoja(cpf);
        int tamanho= 0;
        if(getLoja(cpf) != null) {
            for (Vendedor vendedor : loja.getArmazenaVendedores().values())
                tamanho += vendedor.getPedidosOficial().size();
        }
        String[] colunas = {"Código de pedido", "CPF do vendedor" , "CPF do cliente", "Data", "Hora", "Forma de Pagamento","Taxa de entrega", "Valor total"};

        String[][] dados = new String[tamanho][8];

        DecimalFormat formatadorQuant = new DecimalFormat("00");

        int i = 0;
        for (Vendedor vendedor: loja.getArmazenaVendedores().values())
            for (Pedido p : vendedor.getPedidosOficial().values()) {
                dados[i][0] = p.getCodigo();
                dados[i][1] = interfaceGrafica.formatarCPF(vendedor.getCpf());
                dados[i][2] = interfaceGrafica.formatarCPF(p.getCliente().getCpf());
                dados[i][3] = String.valueOf(p.getData());
                dados[i][4] = String.valueOf(p.getHora());
                dados[i][5] = p.getFormaDePagamento();
                dados[i][6] = formatadorPreco.format(p.getTaxaEntrega());
                dados[i][7] = formatadorPreco.format(p.getValorTotal());
                i++;
            }

        DefaultTableModel modelo = new DefaultTableModel(dados, colunas) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);
        historico.add(scroll, BorderLayout.CENTER);

        JPopupMenu menuPopup = new JPopupMenu();
        JMenuItem visualizar = new JMenuItem("Visualizar");
        menuPopup.add(visualizar);

        tabela.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger() || SwingUtilities.isRightMouseButton(e)) {
                    int linha = tabela.rowAtPoint(e.getPoint());
                    if (linha >= 0 && linha < tabela.getRowCount()) {
                        tabela.setRowSelectionInterval(linha, linha);
                        String codigo = (String) tabela.getValueAt(linha, 0);
                        String cpfVendedor = (String) tabela.getValueAt(linha, 1);

                        for (ActionListener al : visualizar.getActionListeners()) {
                            visualizar.removeActionListener(al);
                        }
                        visualizar.addActionListener(ae -> {
                            exibeVenda(codigo,cpfVendedor);
                        });

                        menuPopup.show(tabela, e.getX(), e.getY());
                    }
                }
            }
        });

        botoesPanel.add(voltar);
        botoesPanel.add(Box.createHorizontalGlue());
        historico.add(botoesPanel);

        voltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botão 'voltar' clicado");
                historico.setVisible(false);
                interfaceGrafica.sistemaGerente();
            }
        });

        return historico;
    }

    protected void exibeVenda(String codigo,String cpfVendedor) {
        Pedido pedido = getLoja(cpf).getVendedor(cpfVendedor).getPedido(codigo);

        JFrame exibe = new JFrame("Informações da venda de pedido: " + codigo);
        exibe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        exibe.setSize(450, 600);
        exibe.setLocationRelativeTo(null);

        JPanel exibeInformacaoLoja = new JPanel(new BorderLayout(10, 10));
        exibeInformacaoLoja.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Informações de venda", SwingConstants.CENTER);
        exibeInformacaoLoja.add(titulo, BorderLayout.NORTH);

        JPanel tabela = new JPanel(new GridLayout(9, 2, 10, 10));

        tabela.add(interfaceGrafica.criaCelula("Código do pedido: "));
        tabela.add(interfaceGrafica.criaCelula(pedido.getCodigo()));

        tabela.add(interfaceGrafica.criaCelula("Cpf do vendedor: "));
        tabela.add(interfaceGrafica.criaCelula(interfaceGrafica.formatarCPF(pedido.getCpfVendedor())));

        tabela.add(interfaceGrafica.criaCelula("CPF do cliente: "));
        tabela.add(interfaceGrafica.criaCelula(interfaceGrafica.formatarCPF(pedido.getCliente().getCpf())));

        tabela.add(interfaceGrafica.criaCelula("Data: "));
        tabela.add(interfaceGrafica.criaCelula(String.valueOf(pedido.getData())));

        tabela.add(interfaceGrafica.criaCelula("Hora: "));
        tabela.add(interfaceGrafica.criaCelula(String.valueOf(pedido.getHora())));

        tabela.add(interfaceGrafica.criaCelula("Forma de pagamente: "));
        tabela.add(interfaceGrafica.criaCelula(pedido.getFormaDePagamento()));

        tabela.add(interfaceGrafica.criaCelula("Taxa de entrega: "));
        tabela.add(interfaceGrafica.criaCelula(formatadorPreco.format(pedido.getTaxaEntrega())));

        tabela.add(interfaceGrafica.criaCelula("Valor total pago: "));
        tabela.add(interfaceGrafica.criaCelula(formatadorPreco.format(pedido.getValorTotal())));

        tabela.add(interfaceGrafica.criaCelula("Valor total recebido pela loja: "));
        tabela.add(interfaceGrafica.criaCelula(formatadorPreco.format(pedido.getValorTotal().subtract(pedido.getTaxaEntrega()))));

        String[] colunas = {"Nome", "preço", "características", "quantidade", "código"};
        Loja loja = getLoja(cpf);

        String[][] dados = new String[pedido.getProdutos().size()][5];

       //DecimalFormat formatadorPreco = new DecimalFormat("R$ #,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
       DecimalFormat formatadorQuant = new DecimalFormat("00");

        int i = 0;
        for (Produto p : pedido.getProdutos().values()) {
            dados[i][0] = p.getNomeProd();
            dados[i][1] = formatadorPreco.format(p.getPreco()); // Ex: R$ 12,34
            dados[i][2] = p.getCarac();
            dados[i][3] = formatadorQuant.format(p.getQuant()); // Ex: 3.000
            dados[i][4] = p.getCodigoProd();
            i++;
        }
        DefaultTableModel modelo = new DefaultTableModel(dados, colunas) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabela1 = new JTable(modelo);

        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        for (int col = 0; col < tabela1.getColumnCount(); col++) {
            tabela1.getColumnModel().getColumn(col).setCellRenderer(centralizado);
        }

        JScrollPane scroll = new JScrollPane(tabela1);
        exibeInformacaoLoja.add(scroll, BorderLayout.CENTER);

        exibeInformacaoLoja.add(tabela, BorderLayout.NORTH);

        JPanel painelBotao = new JPanel();
        JButton sair = new JButton("Fechar");
        sair.addActionListener(e -> exibe.dispose());
        sair.addActionListener(e -> exibe.removeAll());
        painelBotao.add(sair);

        exibeInformacaoLoja.add(painelBotao, BorderLayout.SOUTH);

        exibe.setContentPane(exibeInformacaoLoja);
        exibe.setVisible(true);
        exibe.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.out.println("Janela foi fechada");
            }
        });
    }

    JPanel listaDeClientesRecorrentes(String cpfGerente){
        JPanel listaClientes = new JPanel();
        listaClientes.setLayout(new BoxLayout(listaClientes, BoxLayout.Y_AXIS));
        listaClientes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton voltar = new JButton("Voltar");

        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new BoxLayout(botoesPanel, BoxLayout.X_AXIS));
        botoesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);


        String[] colunas = {"Nome", "CPF", "Quantidade de compras","Valor total de compras"};

        int tamanho = 0;
        Loja loja = getLoja(cpfGerente);
        if(loja != null && loja.getArmazenaClientes() != null)
            tamanho = loja.getArmazenaClientes().size();

        String[][] dados = new String[tamanho][4];
        Cliente[] c = loja.ordenaClientes();
        int i = 0;
        for (int j = 0; j < tamanho;j++ ) {
            dados[i][0] = c[j].getNome();
            dados[i][1] = c[j].getCpf();
            dados[i][2] = String.valueOf(c[j].getQuantidadeCompras());
            dados[i][3] = formatadorReais.format(c[j].getValorGasto());
            i++;
        }

        DefaultTableModel modelo = new DefaultTableModel(dados, colunas) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabela = new JTable(modelo); // <-- corrigido aqui
        JScrollPane scroll = new JScrollPane(tabela);
        listaClientes.add(scroll, BorderLayout.CENTER);

        JPopupMenu menuPopup = new JPopupMenu();
        JMenuItem editarItem = new JMenuItem("Editar");
        JMenuItem excluirItem = new JMenuItem("Excluir");
        menuPopup.add(editarItem);
        menuPopup.add(excluirItem);

        botoesPanel.add(voltar);
        botoesPanel.add(Box.createHorizontalGlue());
        listaClientes.add(botoesPanel);

        voltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botão 'voltar' clicado");
                listaClientes.setVisible(false);
                interfaceGrafica.sistemaGerente();
            }
        });

        return listaClientes;
    }
}
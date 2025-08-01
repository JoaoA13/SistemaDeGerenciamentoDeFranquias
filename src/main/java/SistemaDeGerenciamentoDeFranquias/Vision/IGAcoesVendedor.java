package SistemaDeGerenciamentoDeFranquias.Vision;

import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorDeLojas;
import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorSistemaVendedor;
import SistemaDeGerenciamentoDeFranquias.Exceptions.CadastroException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.EntradaException;
import SistemaDeGerenciamentoDeFranquias.Model.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.text.*;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

import static SistemaDeGerenciamentoDeFranquias.Control.GerenciadorDeLojas.getLoja;

public class IGAcoesVendedor {
    private InterfaceGrafica interfaceGrafica;
    static GerenciadorSistemaVendedor gerenciaVendedor = new GerenciadorSistemaVendedor();
    Loja loja;

    DecimalFormat formatadorReais = new DecimalFormat("R$ #,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
    DecimalFormat formatadorPreco = new DecimalFormat("R$ #,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));

    IGAcoesVendedor(InterfaceGrafica interfaceGrafica){
        this.interfaceGrafica = interfaceGrafica;

    }

    JPanel lancarPedido(String cpfVendedor){

        JPanel cadastro = new JPanel();
        cadastro.setLayout(new BoxLayout(cadastro, BoxLayout.Y_AXIS));
        cadastro.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Vendedor vendedor = (Vendedor) GerenciadorDeLojas.getVendedorGeral(cpfVendedor);
        System.out.println(vendedor.getCodigoLoja() + " códogo da loja");
        this.loja = GerenciadorDeLojas.getLoja(vendedor.getCodigoLoja());

        JLabel labelNome = new JLabel("Digite o nome do cliente:");
        labelNome.setAlignmentX(Component.LEFT_ALIGNMENT);
        cadastro.add(labelNome);

        JTextField escreveNome = new JTextField(20);
        escreveNome.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        escreveNome.setAlignmentX(Component.LEFT_ALIGNMENT);
        cadastro.add(escreveNome);

        JLabel labelCpf = new JLabel("Digite o cpf do cliente:");
        labelCpf.setAlignmentX(Component.LEFT_ALIGNMENT);
        cadastro.add(labelCpf);

        JTextField escreveCpf = new JTextField(20);
        escreveCpf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        escreveCpf.setAlignmentX(Component.LEFT_ALIGNMENT);
        cadastro.add(escreveCpf);

        JLabel labelData = new JLabel("Digite a data do pedido:");
        labelData.setAlignmentX(Component.LEFT_ALIGNMENT);
        cadastro.add(labelData);

        JFormattedTextField escreveData = null;
        try {
            MaskFormatter mascaraData = new MaskFormatter("##/##/####");
            mascaraData.setPlaceholderCharacter('_');
            escreveData = new JFormattedTextField(mascaraData);
            escreveData.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
            escreveData.setAlignmentX(Component.LEFT_ALIGNMENT);
            escreveData.setColumns(10);
            escreveData.setBorder(BorderFactory.createEmptyBorder());
            escreveData.setOpaque(false);
            cadastro.add(escreveData);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JFormattedTextField escreveHora = null;
        try {
            MaskFormatter mascaraHora = new MaskFormatter("##:##");
            mascaraHora.setPlaceholderCharacter('_');
            escreveHora = new JFormattedTextField(mascaraHora);
            escreveHora.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
            escreveHora.setAlignmentX(Component.LEFT_ALIGNMENT);
            escreveHora.setColumns(5);
            escreveHora.setBorder(BorderFactory.createEmptyBorder());
            escreveHora.setOpaque(false);
            cadastro.add(new JLabel("Digite a hora do pedido:"));
            cadastro.add(escreveHora);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JPanel painelPagamentos = new JPanel();
        painelPagamentos.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));

        String[] opcoesPagamento = { "Dinheiro Físico", "Pix", "Cartão" };
        JComboBox<String> comboPagamento = new JComboBox<>(opcoesPagamento);
        comboPagamento.setSelectedIndex(0);

        comboPagamento.setMaximumSize(new Dimension(200, 25));
        comboPagamento.setAlignmentX(Component.LEFT_ALIGNMENT);

        cadastro.add(new JLabel("Forma de Pagamento:"));
        cadastro.add(comboPagamento);

        JPanel taxaEntregaPanel = new JPanel();
        taxaEntregaPanel.setLayout(new BoxLayout(taxaEntregaPanel, BoxLayout.Y_AXIS));
        taxaEntregaPanel.add(new JLabel("Taxa de Entrega:"));
        JTextField campoTaxaEntrega = new JTextField(10);
        campoTaxaEntrega.setMaximumSize(new Dimension(100, 25));
        taxaEntregaPanel.add(campoTaxaEntrega);
        cadastro.add(taxaEntregaPanel);

        JLabel labelCodigo = new JLabel("Digite o codigo do pedido:");
        labelCodigo.setAlignmentX(Component.LEFT_ALIGNMENT);
        cadastro.add(labelCodigo);

        JTextField escreveCodigo = new JTextField(20);
        escreveCodigo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        escreveCodigo.setAlignmentX(Component.LEFT_ALIGNMENT);
        cadastro.add(escreveCodigo);

        JButton Sair = new JButton("Sair");
        JButton confirmar = new JButton("Confirmar");

        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new BoxLayout(botoesPanel, BoxLayout.X_AXIS));
        botoesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        botoesPanel.setMaximumSize(new Dimension(400, 30));

        JFormattedTextField finalEscreveData = escreveData;
        escreveNome.addActionListener(e -> escreveCpf.requestFocusInWindow());
        escreveCpf.addActionListener(e -> finalEscreveData.requestFocusInWindow());

        if (escreveData != null) {
            JFormattedTextField finalEscreveHora = escreveHora;
            escreveData.addActionListener(e -> finalEscreveHora.requestFocusInWindow());
        }
        if (escreveHora != null) {
            escreveHora.addActionListener(e -> campoTaxaEntrega.requestFocusInWindow());
        }
        campoTaxaEntrega.addActionListener(e -> escreveCodigo.requestFocusInWindow());
        escreveCodigo.addActionListener(e -> confirmar.doClick());

        botoesPanel.add(Sair);
        botoesPanel.add(Box.createHorizontalGlue());
        botoesPanel.add(confirmar);
        cadastro.add(botoesPanel);

        JFormattedTextField finalEscreveData2 = escreveData;
        JFormattedTextField finalEscreveHora1 = escreveHora;
        JFormattedTextField finalEscreveData3 = escreveData;
        JFormattedTextField finalEscreveHora2 = escreveHora;
        confirmar.addActionListener(e -> {
            System.out.println("Botão Confirmar clicado");
            String nome = escreveNome.getText().trim();
            String dataTexto = finalEscreveData2.getText();
            String horaTexto = finalEscreveHora1.getText();
            String formaDePagamento = (String) comboPagamento.getSelectedItem();
            String taxaEntrega = campoTaxaEntrega.getText().trim();
            String cpfCliente = escreveCpf.getText().trim();
            String codigo = escreveCodigo.getText().trim();

            try {
                Pedido pedido = gerenciaVendedor.lancarPedido(nome, dataTexto, horaTexto,formaDePagamento, taxaEntrega, cpfCliente, codigo, vendedor, loja);
                JOptionPane.showMessageDialog(null, "Escolha a lista de produtos do pedido", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                escreveNome.setText("");finalEscreveData3.setText("");finalEscreveHora2.setText("");campoTaxaEntrega.setText("");escreveCpf.setText("");escreveCodigo.setText("");
                produtosPedido(vendedor, pedido, cpfCliente);
            } catch (CadastroException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao registrar pedido: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (EntradaException ex) {
                JOptionPane.showMessageDialog(null, "Erro nos dados inseridos: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        return cadastro;
    }

    public void produtosPedido(Vendedor vendedor, Pedido pedido, String cpf) {
        String[] colunas = {"Selecionar", "Nome", "Preço", "Estoque", "Código", "Qtd. Desejada"};

        DecimalFormat formatadorPreco = new DecimalFormat("R$ #,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));

        Object[][] dados = new Object[loja.getArmazenaProdutos().size()][6];
        int i = 0;
        for (Produto p : loja.getArmazenaProdutos().values()) {
            dados[i][0] = Boolean.FALSE;
            dados[i][1] = p.getNomeProd();
            dados[i][2] = formatadorPreco.format(p.getPreco());
            dados[i][3] = p.getQuant();
            dados[i][4] = p.getCodigoProd();
            dados[i][5] = 0;
            i++;
        }

        DefaultTableModel modelo = new DefaultTableModel(dados, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 5) {
                    Boolean selecionado = (Boolean) getValueAt(row, 0);
                    return selecionado != null && selecionado;
                }
                return column == 0;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return switch (columnIndex) {
                    case 0 -> Boolean.class;
                    case 3, 5 -> Integer.class;
                    default -> String.class;
                };
            }
        };

        JTable tabela = new JTable(modelo) {
            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                if (column == 5) {
                    return new QuantidadeDesejadaRenderer();
                }
                return super.getCellRenderer(row, column);
            }
        };

        tabela.setRowHeight(30);

        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);

        for (int col = 0; col < tabela.getColumnCount(); col++) {
            if (col != 0 && col != 5) {
                tabela.getColumnModel().getColumn(col).setCellRenderer(centralizado);
            }
        }

        modelo.addTableModelListener(e -> {
            if (e.getColumn() == 0 && e.getFirstRow() >= 0) {
                int row = e.getFirstRow();
                Boolean selecionado = (Boolean) modelo.getValueAt(row, 0);

                if (selecionado == Boolean.FALSE) {
                    modelo.setValueAt(0, row, 5);
                }

                SwingUtilities.invokeLater(tabela::repaint);
            }
        });

        JPanel painelTabela = new JPanel(new BorderLayout());
        painelTabela.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painelTabela.add(new JScrollPane(tabela), BorderLayout.CENTER);

        JButton confirmar = new JButton("Confirmar Pedido");
        JPanel painelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelInferior.add(confirmar);
        painelTabela.add(painelInferior, BorderLayout.SOUTH);

        confirmar.addActionListener(e -> {
            System.out.println("Botão Confirmar clicado");

            boolean houveErro = false;
            boolean algumaSelecionada = false;

            for (int row = 0; row < tabela.getRowCount(); row++) {
                Boolean selecionado = (Boolean) tabela.getValueAt(row, 0);
                if (selecionado != null && selecionado) {
                    algumaSelecionada = true;
                    String codigo = tabela.getValueAt(row, 4).toString();
                    int qtdInt = (Integer) tabela.getValueAt(row, 5);
                    BigDecimal quantidade = BigDecimal.valueOf(qtdInt);

                    try {
                        gerenciaVendedor.validarNovosProdutos(codigo, quantidade, loja);
                    } catch (EntradaException ex) {
                        JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage(), "Entrada inválida", JOptionPane.ERROR_MESSAGE);
                        houveErro = true;
                        break;
                    }
                }
            }

            if(!algumaSelecionada){
                JOptionPane.showMessageDialog(null, "Erro: " + "Selecione um produto", "Entrada inválida", JOptionPane.ERROR_MESSAGE);
            }

            if (!houveErro && algumaSelecionada) {
                Cliente cliente;
                if (loja.getCliente(cpf) == null) {
                    cliente = new Cliente(pedido.getNomeCliente(), cpf);
                    loja.addCliente(cliente);
                } else {
                    cliente = loja.getCliente(cpf);
                }

                for (int row = 0; row < tabela.getRowCount(); row++) {
                    Boolean selecionado = (Boolean) tabela.getValueAt(row, 0);
                    if (selecionado != null && selecionado) {
                        String codigo = tabela.getValueAt(row, 4).toString();
                        int qtdInt = (Integer) tabela.getValueAt(row, 5);
                        BigDecimal quantidade = BigDecimal.valueOf(qtdInt);
                        gerenciaVendedor.adicionaoAoPedido(pedido, codigo, quantidade, loja, cliente);
                    }
                }
                JOptionPane.showMessageDialog(null, "pedido feito", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                InterfaceGrafica.trocarTela(lancarPedido(vendedor.getCpf()), 400, 400);
                vendedor.setValorVenda(pedido.getValorTotal().subtract(pedido.getTaxaEntrega()));
                vendedor.addPedido(pedido);
                pedido.setCliente(cliente);
                BigDecimal valorPago = pedido.getValorTotal().subtract(pedido.getTaxaEntrega());
                cliente.setValorGasto(valorPago);
            }
        });

        painelTabela.add(painelInferior, BorderLayout.SOUTH);
        InterfaceGrafica.trocarTela(painelTabela, 600, 400);
    }

    JPanel listaDePedidos(String cpfVendedor) {
        JPanel lista = new JPanel();
        lista.setLayout(new BoxLayout(lista, BoxLayout.Y_AXIS));
        lista.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton voltar = new JButton("Voltar");

        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new BoxLayout(botoesPanel, BoxLayout.X_AXIS));
        botoesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        String[] colunas = {"Código", "CPF do cliente", "Data", "Hora", "Forma de Pagamento", "Taxa de entrega", "Valor total"};
        Vendedor vendedor = (Vendedor) GerenciadorDeLojas.getVendedorGeral(cpfVendedor);
        System.out.println(vendedor.getCodigoLoja() + " códogo da loja");
        this.loja = GerenciadorDeLojas.getLoja(vendedor.getCodigoLoja());

        String[][] dados = new String[vendedor.getPedidosOficial().size()][7];

        DecimalFormat formatadorPreco = new DecimalFormat("R$ #,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        DecimalFormat formatadorQuant = new DecimalFormat("00");

        int i = 0;
        for (Pedido p : vendedor.getPedidosOficial().values()) {
            dados[i][0] = p.getCodigo();
            dados[i][1] = interfaceGrafica.formatarCPF(p.getCliente().getCpf());
            dados[i][2] = String.valueOf(p.getData());
            dados[i][3] = String.valueOf(p.getHora());
            dados[i][4] = p.getFormaDePagamento();
            dados[i][5] = formatadorPreco.format(p.getTaxaEntrega());
            dados[i][6] = formatadorPreco.format(p.getValorTotal());
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

        JScrollPane scroll = new JScrollPane(tabela);
        lista.add(scroll, BorderLayout.CENTER);

        JPopupMenu menuPopup = new JPopupMenu();
        JMenuItem visualizar = new JMenuItem("Visualizar");
        JMenuItem editarItem = new JMenuItem("Editar");
        JMenuItem excluirItem = new JMenuItem("Excluir");
        menuPopup.add(visualizar);
        menuPopup.add(editarItem);
        menuPopup.add(excluirItem);

        tabela.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger() || SwingUtilities.isRightMouseButton(e)) {
                    int linha = tabela.rowAtPoint(e.getPoint());
                    if (linha >= 0 && linha < tabela.getRowCount()) {
                        tabela.setRowSelectionInterval(linha, linha);
                        String codigoPedido = (String) tabela.getValueAt(linha, 0);
                        String codigoSelecionado = (String) tabela.getValueAt(linha, 4);

                        editarItem.addActionListener(ae -> {
                            editarProd();
                        });

                        excluirItem.addActionListener(ae -> {
                            int confirm = JOptionPane.showConfirmDialog(lista,
                                    "Tem certeza que deseja solicitar a exclusão do produto?",
                                    "Confirmar exclusão",
                                    JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                excluirProd();
                                ((DefaultTableModel) tabela.getModel()).removeRow(linha);
                            }
                        });

                        for (ActionListener al : visualizar.getActionListeners()) {
                            visualizar.removeActionListener(al);
                        }
                        visualizar.addActionListener(ae -> {
                            exibePedido(codigoPedido,vendedor);
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

    protected void exibePedido(String codigo,Vendedor vendedor) {
        Pedido pedido = vendedor.getPedido(codigo);
        JFrame exibe = new JFrame("Informações de pedido: " + codigo);
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

        String[][] dados = new String[pedido.getProdutos().size()][5];

        //DecimalFormat formatadorPreco = new DecimalFormat("R$ #,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        DecimalFormat formatadorQuant = new DecimalFormat("00");

        int i = 0;
        for (Produto p : pedido.getProdutos().values()) {
            dados[i][0] = p.getNomeProd();
            dados[i][1] = formatadorPreco.format(p.getPreco());
            dados[i][2] = p.getCarac();
            dados[i][3] = formatadorQuant.format(pedido.getQuantProduto(p.getCodigoProd()));
            dados[i][4] = p.getCodigoProd();
            i++;
        }
        DefaultTableModel modelo = new DefaultTableModel(dados, colunas) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabela1 = new JTable(modelo);

        JPopupMenu menuPopup = new JPopupMenu();
        JMenuItem editarItem = new JMenuItem("Editar");
        JMenuItem excluirItem = new JMenuItem("Excluir");
        menuPopup.add(editarItem);
        menuPopup.add(excluirItem);

        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        for (int col = 0; col < tabela1.getColumnCount(); col++) {
            tabela1.getColumnModel().getColumn(col).setCellRenderer(centralizado);
        }

        JScrollPane scroll = new JScrollPane(tabela1);
        exibeInformacaoLoja.add(scroll, BorderLayout.CENTER);

        exibeInformacaoLoja.add(tabela, BorderLayout.NORTH);

        tabela1.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger() || SwingUtilities.isRightMouseButton(e)) {
                    int linha = tabela1.rowAtPoint(e.getPoint());
                    if (linha >= 0 && linha < tabela1.getRowCount()) {
                        tabela1.setRowSelectionInterval(linha, linha);
                        String codigoPedido = (String) tabela1.getValueAt(linha, 0);
                        String codigoSelecionado = (String) tabela1.getValueAt(linha, 4);

                        editarItem.addActionListener(ae -> {
                            editarProd();
                        });

                        excluirItem.addActionListener(ae -> {
                            int confirm = JOptionPane.showConfirmDialog(exibeInformacaoLoja,
                                    "Tem certeza que deseja solicitar a exclusão do produto?",
                                    "Confirmar exclusão",
                                    JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                //excluirProd();
                                //((DefaultTableModel) tabela1.getModel()).removeRow(linha);
                            }
                        });

                        menuPopup.show(tabela1, e.getX(), e.getY());
                    }
                }
            }
        });

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

    public void editarProd(){
        JPanel edicao = new JPanel();
        edicao.setLayout(new BoxLayout(edicao, BoxLayout.Y_AXIS));
        edicao.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        String texto = "";
        String[] opcoes = {"Código", "CPF do Cliente", "data", "hora", "Forma de Pagamento", "Taxa de Entrega"};
        int escolha = JOptionPane.showOptionDialog(
                null,
                "Qual informação deseja editar?",
                "Editar Pedido",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );

        switch (escolha){
            case 0: texto = "Código"; break;
            case 1: texto = "CPF do Cliente"; break;
            case 2: texto = "data"; break;
            case 3: texto = "hora"; break;
            case 4: texto = "Forma de Pagamento"; break;
            case 5: texto = "Taxa de Entrega"; break;
        }

        JButton confirmar = new JButton("Confirmar");

        final String[] texto2 = {""};
        JTextField escreveTexto;
        String[] opcoesPagamento = { "Dinheiro Físico", "Pix", "Cartão" };
        JComboBox<String> comboPagamento = new JComboBox<>(opcoesPagamento);
        edicao.add(criarJLabel(texto));
        if(escolha != 4) {
            escreveTexto = new JTextField(20);
            escreveTexto.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
            escreveTexto.setAlignmentX(Component.LEFT_ALIGNMENT);
            edicao.add(escreveTexto);
            escreveTexto.addActionListener(e -> confirmar.doClick());
            escreveTexto.setText("");
        }
        else{
            escreveTexto = null;
            comboPagamento.setSelectedIndex(0);
            comboPagamento.setMaximumSize(new Dimension(200, 25));
            comboPagamento.setAlignmentX(Component.LEFT_ALIGNMENT);
            edicao.add(new JLabel("Forma de Pagamento:"));
            edicao.add(comboPagamento);
        }

        edicao.setLayout(new BoxLayout(edicao, BoxLayout.Y_AXIS));

        edicao.add(Box.createVerticalStrut(10));
        edicao.add(confirmar);


        confirmar.addActionListener(e -> {
            System.out.println("Botão Confirmar clicado");
            if(escolha == 4)
                texto2[0] = (String) comboPagamento.getSelectedItem();
            else
                texto2[0] = escreveTexto.getText().trim();
            try {
                String msg = gerenciaVendedor.solicitarEdicao(texto2[0], escolha);
                JOptionPane.showMessageDialog(null, msg, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (EntradaException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao editar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        InterfaceGrafica.trocarTela(edicao, 400, 200);
    }

    public JLabel criarJLabel(String texto) {
        JLabel label = new JLabel("Digite o(a) novo(a) " + texto);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }


    public void excluirProd(){
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
    }
}
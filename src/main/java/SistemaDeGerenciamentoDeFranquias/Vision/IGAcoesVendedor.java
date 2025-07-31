package SistemaDeGerenciamentoDeFranquias.Vision;

import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorDeLojas;
import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorSistemaVendedor;
import SistemaDeGerenciamentoDeFranquias.Exceptions.CadastroException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.EntradaException;
import SistemaDeGerenciamentoDeFranquias.Model.Loja;
import SistemaDeGerenciamentoDeFranquias.Model.Produto;
import SistemaDeGerenciamentoDeFranquias.Model.Vendedor;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.math.BigDecimal;
import java.text.*;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

public class IGAcoesVendedor {
    static GerenciadorSistemaVendedor gerenciaVendedor = new GerenciadorSistemaVendedor();
    Loja loja;

    IGAcoesVendedor(){

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

        JButton Sair = new JButton("Sair");
        JButton confirmar = new JButton("Confirmar");

        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new BoxLayout(botoesPanel, BoxLayout.X_AXIS));
        botoesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        botoesPanel.setMaximumSize(new Dimension(400, 30));

        JFormattedTextField finalEscreveData = escreveData;
        escreveNome.addActionListener(e -> finalEscreveData.requestFocusInWindow());

        if (escreveData != null) {
            JFormattedTextField finalEscreveHora = escreveHora;
            escreveData.addActionListener(e -> finalEscreveHora.requestFocusInWindow());
        }
        if (escreveHora != null) {
            escreveHora.addActionListener(e -> campoTaxaEntrega.requestFocusInWindow());
        }
        campoTaxaEntrega.addActionListener(e -> confirmar.doClick());

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
            String  taxaEntrega = campoTaxaEntrega.getText().trim();

            try {
                String msg = gerenciaVendedor.lancarPedido(nome, dataTexto, horaTexto,formaDePagamento, taxaEntrega, vendedor, loja);
                JOptionPane.showMessageDialog(null, msg, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                escreveNome.setText("");finalEscreveData3.setText("");finalEscreveHora2.setText("");campoTaxaEntrega.setText("");
                produtosPedido(vendedor);
            } catch (CadastroException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao registrar pedido: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (EntradaException ex) {
                JOptionPane.showMessageDialog(null, "Erro nos dados inseridos: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        return cadastro;
    }

    public void produtosPedido(Vendedor vendedor) {
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
            JOptionPane.showMessageDialog(null, "pedido feito", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            InterfaceGrafica.trocarTela(lancarPedido(vendedor.getCpf()), 400, 300);
        });

        painelTabela.add(painelInferior, BorderLayout.SOUTH);
        InterfaceGrafica.trocarTela(painelTabela, 600, 400);
    }

    JPanel listaDePedidos(){
        return null;
    }
}

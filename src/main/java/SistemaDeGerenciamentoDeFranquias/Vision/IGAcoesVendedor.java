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
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

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

        JLabel labelCodigo = new JLabel("Digite o código do produto desejado:");
        labelCodigo.setAlignmentX(Component.LEFT_ALIGNMENT);
        cadastro.add(labelCodigo);

        JTextField escreveCodigo = new JTextField(20);
        escreveCodigo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        escreveCodigo.setAlignmentX(Component.LEFT_ALIGNMENT);
        cadastro.add(escreveCodigo);

        JLabel labelQuantidade = new JLabel("Digite a quantidade:");
        labelQuantidade.setAlignmentX(Component.LEFT_ALIGNMENT);
        cadastro.add(labelQuantidade);

        JTextField escreveQuantidade = new JTextField(20);
        escreveQuantidade.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        escreveQuantidade.setAlignmentX(Component.LEFT_ALIGNMENT);
        cadastro.add(escreveQuantidade);

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


        JPanel valoresPanel = new JPanel();
        valoresPanel.setLayout(new BoxLayout(valoresPanel, BoxLayout.X_AXIS));
        valoresPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel valorPedidoPanel = new JPanel();
        valorPedidoPanel.setLayout(new BoxLayout(valorPedidoPanel, BoxLayout.Y_AXIS));
        valorPedidoPanel.add(new JLabel("Valor do Pedido:"));
        JTextField campoValorPedido = new JTextField(10);
        campoValorPedido.setEditable(false);
        campoValorPedido.setMaximumSize(new Dimension(200, 25));
        valorPedidoPanel.add(campoValorPedido);
        valoresPanel.add(valorPedidoPanel);

        valoresPanel.add(Box.createRigidArea(new Dimension(20, 0)));

        JPanel taxaEntregaPanel = new JPanel();
        taxaEntregaPanel.setLayout(new BoxLayout(taxaEntregaPanel, BoxLayout.Y_AXIS));
        taxaEntregaPanel.add(new JLabel("Taxa de Entrega:"));
        JTextField campoTaxaEntrega = new JTextField(10);
        campoTaxaEntrega.setMaximumSize(new Dimension(100, 25));
        taxaEntregaPanel.add(campoTaxaEntrega);
        valoresPanel.add(taxaEntregaPanel);
        cadastro.add(valoresPanel);

        String codigo = escreveCodigo.getText();
        if(loja != null) {
            Produto produto = loja.getProduto(codigo);
            escreveQuantidade.getDocument().addDocumentListener(new DocumentListener() {
                public void insertUpdate(DocumentEvent e) {
                    calcular();
                }

                public void removeUpdate(DocumentEvent e) {
                    calcular();
                }

                public void changedUpdate(DocumentEvent e) {
                    calcular();
                }

                private void calcular() {
                    try {
                        int qtd = Integer.parseInt(escreveQuantidade.getText());
                        BigDecimal total = BigDecimal.valueOf(0);
                        if (produto != null)
                            total = BigDecimal.valueOf(qtd).multiply(produto.getPreco());
                        campoValorPedido.setText(String.format("R$ %.2f", total));
                    } catch (NumberFormatException ex) {
                        campoValorPedido.setText("R$ 0.00");
                    }
                }
            });
        }


        JButton Sair = new JButton("Sair");
        JButton confirmar = new JButton("Confirmar");

        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new BoxLayout(botoesPanel, BoxLayout.X_AXIS));
        botoesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        botoesPanel.setMaximumSize(new Dimension(400, 30));

        escreveCodigo.addActionListener(e -> escreveQuantidade.requestFocusInWindow());
        escreveQuantidade.addActionListener(e -> escreveNome.requestFocusInWindow());
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
            String cod = escreveCodigo.getText().trim();
            String quantidade = escreveQuantidade.getText().trim();
            String nome = escreveNome.getText().trim();
            String dataTexto = finalEscreveData2.getText();
            String horaTexto = finalEscreveHora1.getText();
            String formaDePagamento = (String) comboPagamento.getSelectedItem();
            String  taxaEntrega = campoTaxaEntrega.getText().trim();

            try {
                String msg = gerenciaVendedor.lancarPedido(cod, quantidade, nome, dataTexto, horaTexto,formaDePagamento, taxaEntrega, vendedor, loja);
                JOptionPane.showMessageDialog(null, msg, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                escreveCodigo.setText("");escreveQuantidade.setText("");escreveNome.setText("");finalEscreveData3.setText("");finalEscreveHora2.setText("");campoTaxaEntrega.setText("");
            } catch (CadastroException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao registrar pedido: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (EntradaException ex) {
                JOptionPane.showMessageDialog(null, "Erro nos dados inseridos: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        return cadastro;
    }

    public static boolean maisProdutos(){
        int resposta = JOptionPane.showConfirmDialog(
                null,
                "Deseja registrar outro produto para o pedido?",
                "Registrar Produto",
                JOptionPane.YES_NO_OPTION
        );

        if (resposta != JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, "Registro finalizado.");
            return false;
        }
        return true;
    }

    public static void outrosProdutos(Vendedor vendedor, Loja loja){
        JPanel cadastro = new JPanel();
        cadastro.setLayout(new BoxLayout(cadastro, BoxLayout.Y_AXIS));
        cadastro.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel labelCodigo = new JLabel("Digite o código do produto desejado:");
        labelCodigo.setAlignmentX(Component.LEFT_ALIGNMENT);
        cadastro.add(labelCodigo);

        JTextField escreveCodigo = new JTextField(20);
        escreveCodigo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        escreveCodigo.setAlignmentX(Component.LEFT_ALIGNMENT);
        cadastro.add(escreveCodigo);

        JLabel labelQuantidade = new JLabel("Digite a quantidade:");
        labelQuantidade.setAlignmentX(Component.LEFT_ALIGNMENT);
        cadastro.add(labelQuantidade);

        JTextField escreveQuantidade = new JTextField(20);
        escreveQuantidade.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        escreveQuantidade.setAlignmentX(Component.LEFT_ALIGNMENT);
        cadastro.add(escreveQuantidade);

        JPanel valoresPanel = new JPanel();
        valoresPanel.setLayout(new BoxLayout(valoresPanel, BoxLayout.X_AXIS));
        valoresPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel valorPedidoPanel = new JPanel();
        valorPedidoPanel.setLayout(new BoxLayout(valorPedidoPanel, BoxLayout.Y_AXIS));
        valorPedidoPanel.add(new JLabel("Valor do Pedido:"));
        JTextField campoValorPedido = new JTextField(10);
        campoValorPedido.setEditable(false);
        campoValorPedido.setMaximumSize(new Dimension(200, 25));
        valorPedidoPanel.add(campoValorPedido);
        valoresPanel.add(valorPedidoPanel);

        valoresPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        cadastro.add(valoresPanel);

        String codigo = escreveCodigo.getText();
        if(loja != null) {
            Produto produto = loja.getProduto(codigo);
            escreveQuantidade.getDocument().addDocumentListener(new DocumentListener() {
                public void insertUpdate(DocumentEvent e) {
                    calcular();
                }

                public void removeUpdate(DocumentEvent e) {
                    calcular();
                }

                public void changedUpdate(DocumentEvent e) {
                    calcular();
                }

                private void calcular() {
                    try {
                        int qtd = Integer.parseInt(escreveQuantidade.getText());
                        BigDecimal total = BigDecimal.valueOf(0);
                        if (produto != null)
                            total = BigDecimal.valueOf(qtd).multiply(produto.getPreco());
                        campoValorPedido.setText(String.format("R$ %.2f", total));
                    } catch (NumberFormatException ex) {
                        campoValorPedido.setText("R$ 0.00");
                    }
                }
            });
        }


        JButton voltar = new JButton("Voltar");
        JButton confirmar = new JButton("Confirmar");

        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new BoxLayout(botoesPanel, BoxLayout.X_AXIS));
        botoesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        botoesPanel.setMaximumSize(new Dimension(400, 30));

        escreveCodigo.addActionListener(e -> escreveQuantidade.requestFocusInWindow());
        escreveQuantidade.addActionListener(e -> confirmar.doClick());

        botoesPanel.add(voltar);
        botoesPanel.add(Box.createHorizontalGlue());
        botoesPanel.add(confirmar);
        cadastro.add(botoesPanel);

        confirmar.addActionListener(e -> {
            System.out.println("Botão Confirmar clicado");
            String cod = escreveCodigo.getText().trim();
            String quantidade = escreveQuantidade.getText().trim();

            try {
                gerenciaVendedor.registraNovosProdutos(cod, quantidade, vendedor, loja);
                JOptionPane.showMessageDialog(null, "", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                escreveCodigo.setText("");escreveQuantidade.setText("");
            } catch (CadastroException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao registrar pedido: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (EntradaException ex) {
                JOptionPane.showMessageDialog(null, "Erro nos dados inseridos: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        JFrame frame = new JFrame();
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(cadastro);
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);

    }

    JPanel listaDePedidos(){
        return null;
    }
}

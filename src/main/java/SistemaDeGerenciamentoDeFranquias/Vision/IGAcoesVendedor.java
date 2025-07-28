package SistemaDeGerenciamentoDeFranquias.Vision;

import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorDeLojas;
import SistemaDeGerenciamentoDeFranquias.Exceptions.CadastroException;
import SistemaDeGerenciamentoDeFranquias.Model.Loja;
import SistemaDeGerenciamentoDeFranquias.Model.Produto;
import SistemaDeGerenciamentoDeFranquias.Model.Vendedor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class IGAcoesVendedor {
    IGAcoesVendedor(){

    }

    JPanel lancarPedido(String cpfVendedor){

        /*JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GerenciadorDeLojas gerenciaDeLojas = new GerenciadorDeLojas();
        Vendedor vendedor = (Vendedor) gerenciaDeLojas.getVendedorGeral(cpfVendedor);
        Loja loja = gerenciaDeLojas.getLoja(vendedor.getCodigoLoja());
        Collection<Produto> produtos = loja.getArmazenaProdutos().values();

        String[] colunas = {"Selecionar", "Nome", "Preço", "Qtd. Disponível", "Qtd. Pedido"};
        Object[][] dados = new Object[loja.getArmazenaProdutos().size()][5];

        DecimalFormat formatador = new DecimalFormat("R$ #,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));

        int i = 0;
        for (Produto p : produtos) {
            dados[i][0] = false;
            dados[i][1] = p.getNomeProd();
            dados[i][2] = formatador.format(p.getPreco());
            dados[i][3] = p.getQuant();
            dados[i][4] = 0;
            i++;
        }

        DefaultTableModel modelo = new DefaultTableModel(dados, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 || column == 4; // só checkbox e qtd. pedido
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return Boolean.class; // checkbox
                if (columnIndex == 4) return Integer.class; // qtd. pedido
                return String.class;
            }
        };

        JTable tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);
        painel.add(scroll, BorderLayout.CENTER);

        JButton confirmar = new JButton("Confirmar Pedido");
        confirmar.addActionListener(e -> {
            List<String> produtosSelecionados = new ArrayList<>();
            for (int linha = 0; linha < modelo.getRowCount(); linha++) {
                boolean selecionado = (boolean) modelo.getValueAt(linha, 0);
                int qtd = (int) modelo.getValueAt(linha, 4);
                if (selecionado && qtd > 0) {
                    String nome = (String) modelo.getValueAt(linha, 1);
                    produtosSelecionados.add(nome + " - Qtd: " + qtd);
                }
            }

            if (produtosSelecionados.isEmpty()) {
                JOptionPane.showMessageDialog(painel, "Nenhum produto selecionado com quantidade válida.");
            } else {
                JOptionPane.showMessageDialog(painel, "Pedido confirmado com os produtos:\n" +
                        String.join("\n", produtosSelecionados));
            }
        });

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botoes.add(confirmar);
        painel.add(botoes, BorderLayout.SOUTH);

        return painel;*/

        return null;

        /*JPanel cadastro = new JPanel();
        cadastro.setLayout(new BoxLayout(cadastro, BoxLayout.Y_AXIS));
        cadastro.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel labelNome = new JLabel("Digite o código do produto desejado:");
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

        return cadastro;*/
    }

    JPanel listaDePedidos(){
        return null;
    }
}

package SistemaDeGerenciamentoDeFranquias.Vision;

import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorDeLojas;
import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorSistemaDono;
import SistemaDeGerenciamentoDeFranquias.Exceptions.CadastroException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.EntradaException;
import SistemaDeGerenciamentoDeFranquias.Model.Loja;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class IGAcoesDono {
    private InterfaceGrafica interfaceGrafica;
    private GerenciadorDeLojas gerenciaDeLojas;

    IGAcoesDono(InterfaceGrafica interfaceGrafica,GerenciadorDeLojas gerenciaDeLojas){
        this.interfaceGrafica = interfaceGrafica;
        this.gerenciaDeLojas = gerenciaDeLojas;
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
                painelCadastroLoja.setVisible(false);
                interfaceGrafica.menuLogin();
            }
        });
        return painelCadastroLoja;
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

    JPanel listaDeLojas(){
        JPanel lista = new JPanel();
        lista.setLayout(new BoxLayout(lista, BoxLayout.Y_AXIS));
        lista.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton voltar = new JButton("Voltar");

        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new BoxLayout(botoesPanel, BoxLayout.X_AXIS));
        botoesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        String[] colunas = {"Endereço", "CPF do gerente", "Código ou ticketmedio"};

            String[][] dados = new String[GerenciadorDeLojas.getLojas().size()][3];
            int i = 0;
            for (Loja loja : GerenciadorDeLojas.getLojas().values()) {
                if (loja == null) continue;
                dados[i][0] = loja.getEndereco();
                dados[i][1] = loja.getCpfGerente();
                dados[i][2] = loja.getCodigo();
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
        JMenuItem visualizarItem = new JMenuItem("Visualizar");
        menuPopup.add(editarItem);
        menuPopup.add(excluirItem);
        menuPopup.add(visualizarItem);

        tabela.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger() || SwingUtilities.isRightMouseButton(e)) {
                    int linha = tabela.rowAtPoint(e.getPoint());
                    if (linha >= 0 && linha < tabela.getRowCount()) {
                        tabela.setRowSelectionInterval(linha, linha);
                        String codigo = (String) tabela.getValueAt(linha, 1);
                        String cpfGerente = (String) tabela.getValueAt(linha, 2);

//                        editarItem.addActionListener(ae -> {
//                            editar(cpfGerente);
//                        });

                        excluirItem.addActionListener(ae -> {
                            int confirm = JOptionPane.showConfirmDialog(lista,
                                    "Tem certeza que deseja excluir a loja com o código: " + codigo + "?",
                                    "Confirmar exclusão",
                                    JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                try {
                                    GerenciadorSistemaDono.excluirLoja(cpfGerente);
                                } catch (EntradaException ex) {
                                        interfaceGrafica.exibeException(ex.getMessage(),"Exclusão falhou");
                                }
                                ((DefaultTableModel) tabela.getModel()).removeRow(linha);
                            }
                        });

                        visualizarItem.addActionListener(ae -> {
                            exibeLoja(cpfGerente,GerenciadorDeLojas.getLoja(cpfGerente));
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

    protected void exibeLoja(String codigo, Loja loja) {
        JFrame exibe = new JFrame("Informações da Loja: " + codigo);
        exibe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha só essa janela

        JPanel exibeInformacaoLoja = new JPanel(new BorderLayout());

        JLabel titulo = new JLabel("Informações de loja", SwingConstants.CENTER);
        exibeInformacaoLoja.add(titulo, BorderLayout.NORTH);

        JPanel subPainel = new JPanel();
        subPainel.setLayout(new BoxLayout(subPainel, BoxLayout.Y_AXIS));
        subPainel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel linhaCodigo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        linhaCodigo.add(new JLabel("Código da loja: " + loja.getCodigo()));
        subPainel.add(linhaCodigo);

        JPanel linhaEndereco = new JPanel(new FlowLayout(FlowLayout.CENTER));
        linhaEndereco.add(new JLabel("Endereço: " + loja.getEndereco()));
        subPainel.add(linhaEndereco);

        JPanel linhaGerente = new JPanel(new FlowLayout(FlowLayout.CENTER));
        linhaGerente.add(new JLabel("CPF do gerente: " + loja.getCpfGerente()));
        subPainel.add(linhaGerente);

        JButton Sair = new JButton("Fechar");
        Sair.addActionListener(e -> exibe.dispose());
        subPainel.add(Sair);

        exibeInformacaoLoja.add(subPainel, BorderLayout.CENTER);
        exibe.setContentPane(exibeInformacaoLoja);

        exibe.pack(); // Calcula o tamanho ideal baseado nos componentes
        exibe.setLocationRelativeTo(null); // Centraliza na tela
        exibe.setVisible(true); // Torna visível
    }

}

package SistemaDeGerenciamentoDeFranquias.Vision;

import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorDeLojas;
import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorSistemaDono;
import SistemaDeGerenciamentoDeFranquias.Exceptions.CadastroException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.EntradaException;
import SistemaDeGerenciamentoDeFranquias.Model.Gerente;
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

        JPanel linhaCpf = new JPanel(new FlowLayout(FlowLayout.CENTER));
        linhaCpf.add(new JLabel("Cpf do gerente responsável:"));
        JTextField escreveCpf = new JTextField(15);
        linhaCpf.add(escreveCpf);
        subPainel.add(linhaCpf);

        JButton Cadastrar = new JButton("Cadastrar");
        subPainel.add(Cadastrar);

        JButton Sair = new JButton("Sair");
        subPainel.add(Sair);

        painelCadastroLoja.add(subPainel, BorderLayout.CENTER);

        escreveEndereco.addKeyListener(new KeyAdapter() {
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
                    if(botaoCadastrarLoja(escreveEndereco.getText(), escreveCpf.getText())){

                        escreveEndereco.setText("");
                        escreveCpf.setText("");
                    }
                }
            }
        });

        Cadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botão 'Cadastrar' clicado");
                if(botaoCadastrarLoja(escreveEndereco.getText(),escreveCpf.getText())) {

                    escreveEndereco.setText("");escreveCpf.setText("");
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
    boolean botaoCadastrarLoja(String endereco,String cpf){
            System.out.println("Tecla Enter pressionada");

        try{
                interfaceGrafica.gerenciaDono.cadastroLoja(endereco,cpf);
                interfaceGrafica.exibeInformacao("Cadastro de Loja e atribuição de gerente feitos corretamente","Cadastro de Loja e gerente feito com sucesso");
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
        if (loja == null) {
            JOptionPane.showMessageDialog(null, "Gerente não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JFrame exibe = new JFrame("Informações da Loja: " + codigo);
        exibe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        exibe.setSize(450, 250);
        exibe.setLocationRelativeTo(null);

        JPanel exibeInformacaoLoja = new JPanel(new BorderLayout(10, 10));
        exibeInformacaoLoja.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Informações de loja", SwingConstants.CENTER);
        exibeInformacaoLoja.add(titulo, BorderLayout.NORTH);

        JPanel tabela = new JPanel(new GridLayout(3, 2, 10, 10));

        tabela.add(criaCelula("Código da loja: "));
        tabela.add(criaCelula(loja.getCodigo()));

        tabela.add(criaCelula("Endereço: "));
        tabela.add(criaCelula(loja.getEndereco()));

        tabela.add(criaCelula("CPF do gerente: "));
        tabela.add(criaCelula(loja.getCpfGerente()));

        exibeInformacaoLoja.add(tabela, BorderLayout.CENTER);

        JPanel painelBotao = new JPanel();
        JButton sair = new JButton("Fechar");
        sair.addActionListener(e -> exibe.dispose());
        painelBotao.add(sair);

        exibeInformacaoLoja.add(painelBotao, BorderLayout.SOUTH);

        exibe.setContentPane(exibeInformacaoLoja);
        exibe.setVisible(true);
    }

    JPanel cadastraGerentes(){
        JPanel cadastraGerentes = new JPanel();

        JLabel titulo = new JLabel("Cadastro de gerente", SwingConstants.CENTER);
        cadastraGerentes.add(titulo, BorderLayout.NORTH);

        JPanel subPainel = new JPanel();
        subPainel.setLayout(new BoxLayout(subPainel, BoxLayout.Y_AXIS));
        subPainel.setAlignmentX(Component.CENTER_ALIGNMENT);

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

        cadastraGerentes.add(subPainel, BorderLayout.CENTER);

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
                        if(botaoCadastrarGerente( escreveNome.getText(), escreveCpf.getText(), escreveEmail.getText())) {
                            escreveNome.setText("");escreveCpf.setText("");escreveEmail.setText("");
                        }
                    }
                }
            });

            Cadastrar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Botão 'Cadastrar' clicado");
                    if(botaoCadastrarGerente(escreveNome.getText(), escreveCpf.getText(), escreveEmail.getText())) {
                        escreveNome.setText("");escreveCpf.setText("");escreveEmail.setText("");
                    }
                }
            });
        return cadastraGerentes;
    }

    boolean botaoCadastrarGerente(String nome, String cpf,String email){
        System.out.println("Tecla Enter pressionada");

        try{
            interfaceGrafica.gerenciaDono.cadastroGerente(nome,cpf,email);
            interfaceGrafica.exibeInformacao("Cadastro de gerente feitos corretamente","Cadastro de gerente feito com sucesso");
            return true;
        }catch (CadastroException mes) {
            interfaceGrafica.exibeException(mes.getMessage(),"Cadastro falhou");
        }
        return false;
    }

    JPanel listaDeGerente(){
        JPanel lista = new JPanel();
        lista.setLayout(new BoxLayout(lista, BoxLayout.Y_AXIS));
        lista.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new BoxLayout(botoesPanel, BoxLayout.X_AXIS));
        botoesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        String[] colunas = {"Nome", "CPF", "Código da loja"};

        String[][] dados = new String[GerenciadorDeLojas.getGerentes().size()][3];
        int i = 0;
        for (Gerente gerente: GerenciadorDeLojas.getGerentes().values()) {
            if (gerente == null) continue;
            dados[i][0] = gerente.getNome();
            dados[i][1] = gerente.getCpf();
            dados[i][2] = GerenciadorDeLojas.getCodigoLoja(gerente.getCpf());
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
                        String cpf = (String) tabela.getValueAt(linha, 1);


//                        editarItem.addActionListener(ae -> {
//                            editar(cpfGerente);
//                        });

                        excluirItem.addActionListener(ae -> {
                            int confirm;
                            if(GerenciadorDeLojas.getLoja(cpf) == null) {
                                confirm = JOptionPane.showConfirmDialog(lista,
                                        "Tem certeza que deseja excluir o gerente com o CPF: " + cpf + "?",
                                        "Confirmar exclusão",
                                        JOptionPane.YES_NO_OPTION);
                            }else {
                                confirm = JOptionPane.showConfirmDialog(lista,
                                        "Tem certeza que deseja excluir o gerente com o CPF: " + cpf + "? A UNIDADE" + GerenciadorDeLojas.getCodigoLoja(cpf) + " FICARÁ SEM GERENTE",
                                        "Confirmar exclusão",
                                        JOptionPane.YES_NO_OPTION);

                                if (confirm == JOptionPane.YES_OPTION) {
                                    int confirma = JOptionPane.showConfirmDialog(lista,
                                            "Deseja excluir tambem a unidade do gerente: " + cpf + "?",
                                            "Confirmar exclusão",
                                            JOptionPane.YES_NO_OPTION);

                                    if (confirma == JOptionPane.YES_OPTION) {
                                        try {
                                            GerenciadorSistemaDono.excluirLoja(cpf);
                                            interfaceGrafica.exibeInformacao("Loja excluida com sucesso", "Exclusão concluida");
                                        } catch (EntradaException ex) {
                                            interfaceGrafica.exibeException(ex.getMessage(), "Exclusão falhou");
                                        }
                                    }
                                }
                            }

                                try {
                                    GerenciadorSistemaDono.excluirGerente(cpf);
                                    interfaceGrafica.exibeInformacao("Gerente excluido com sucesso", "Exclusão concluida");
                                    ((DefaultTableModel) tabela.getModel()).removeRow(linha);
                                } catch (EntradaException ex) {
                                    interfaceGrafica.exibeException(ex.getMessage(),"Exclusão falhou");
                                }

                        });

                        visualizarItem.addActionListener(ae -> {
                            exibeGerente(GerenciadorDeLojas.getGerente(cpf));
                        });

                        menuPopup.show(tabela, e.getX(), e.getY());
                    }
                }
            }
        });

        botoesPanel.add(Box.createHorizontalGlue());
        lista.add(botoesPanel);

        return lista;
    }

    protected void exibeGerente(Gerente gerente) {
        if (gerente == null) {
            JOptionPane.showMessageDialog(null, "Gerente não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JFrame exibe = new JFrame("Informações do Gerente: " + gerente.getNome());
        exibe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        exibe.setSize(450, 250);
        exibe.setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Informações do Gerente", SwingConstants.CENTER);
        painelPrincipal.add(titulo, BorderLayout.NORTH);

        JPanel tabela = new JPanel(new GridLayout(3, 2, 10, 10));

        tabela.add(criaCelula("Nome do gerente:"));
        tabela.add(criaCelula(gerente.getNome()));

        tabela.add(criaCelula("CPF:"));
        tabela.add(criaCelula(gerente.getCpf()));

        tabela.add(criaCelula("Email:"));
        tabela.add(criaCelula(gerente.getEmail()));

        painelPrincipal.add(tabela, BorderLayout.CENTER);

        // Botão de sair
        JButton sair = new JButton("Fechar");
        sair.addActionListener(e -> exibe.dispose());

        JPanel painelBotao = new JPanel();
        painelBotao.add(sair);

        painelPrincipal.add(painelBotao, BorderLayout.SOUTH);

        exibe.setContentPane(painelPrincipal);
        exibe.setVisible(true);
    }

    private JLabel criaCelula(String valor) {
        JLabel label = new JLabel(valor, SwingConstants.CENTER);
        label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        return label;
    }

}

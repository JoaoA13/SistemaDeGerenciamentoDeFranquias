package SistemaDeGerenciamentoDeFranquias.Vision;

import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorDeLojas;
import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorSistemaDono;
import SistemaDeGerenciamentoDeFranquias.Exceptions.CadastroException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.EntradaException;
import SistemaDeGerenciamentoDeFranquias.Model.Gerente;
import SistemaDeGerenciamentoDeFranquias.Model.Loja;
import SistemaDeGerenciamentoDeFranquias.Model.Vendedor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

import static SistemaDeGerenciamentoDeFranquias.Control.GerenciadorDeLojas.getCpfPorCodigo;

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

        JPanel linhaCodigo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        linhaCodigo.add(new JLabel("Código da loja:"));
        JTextField escreveCodigo = new JTextField(15);
        linhaCodigo.add(escreveCodigo);
        subPainel.add(linhaCodigo);

        JPanel linhaCpf = new JPanel(new FlowLayout(FlowLayout.CENTER));
        linhaCpf.add(new JLabel("Cpf do gerente responsável:"));
        JTextField escreveCpf = new JTextField(15);
        linhaCpf.add(escreveCpf);
        subPainel.add(linhaCpf);

        JButton Cadastrar = new JButton("Cadastrar");
        subPainel.add(Cadastrar);

        JButton voltar = new JButton("voltar");
        subPainel.add(voltar);

        painelCadastroLoja.add(subPainel, BorderLayout.CENTER);

        escreveEndereco.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    System.out.println("Tecla Enter pressionada");
                    escreveCodigo.requestFocusInWindow();
                }
            }
        });

        escreveCodigo.addKeyListener(new KeyAdapter() {
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
                    if(botaoCadastrarLoja(escreveEndereco.getText(),escreveCodigo.getText(), escreveCpf.getText())){
                        escreveEndereco.setText("");escreveCodigo.setText("");escreveCpf.setText("");
                    }
                }
            }
        });

        Cadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botão 'Cadastrar' clicado");
                if(botaoCadastrarLoja(escreveEndereco.getText(),escreveCodigo.getText(),escreveCpf.getText())) {
                    escreveEndereco.setText("");escreveCodigo.setText("");escreveCpf.setText("");
                }
            }
        });

        voltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botão 'voltar' clicado");
                painelCadastroLoja.setVisible(false);
                interfaceGrafica.sistemaDono();
            }
        });
        return painelCadastroLoja;
    }
    boolean botaoCadastrarLoja(String endereco,String codigo,String cpf){
            System.out.println("Tecla Enter pressionada");

        try{
                interfaceGrafica.gerenciaDono.cadastroLoja(endereco,codigo,cpf);
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

        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new BoxLayout(botoesPanel, BoxLayout.X_AXIS));
        botoesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        String[] colunas = {"Endereço", "CPF do gerente", "Código"};

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
                        String codigo = (String) tabela.getValueAt(linha, 2);
                        String cpfGerente = (String) tabela.getValueAt(linha, 1);

                        editarItem.addActionListener(ae -> {
                            editarLoja(codigo);
                        });

                        excluirItem.addActionListener(ae -> {
                            int confirm = JOptionPane.showConfirmDialog(lista,
                                    "Tem certeza que deseja excluir a loja com o código: " + codigo + "?",
                                    "Confirmar exclusão",
                                    JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                try {
                                    GerenciadorSistemaDono.excluirLoja(codigo);
                                } catch (EntradaException ex) {
                                        interfaceGrafica.exibeException(ex.getMessage(),"Exclusão falhou");
                                }
                                ((DefaultTableModel) tabela.getModel()).removeRow(linha);
                            }
                        });

                        visualizarItem.addActionListener(ae -> {
                            exibeLoja(codigo,GerenciadorDeLojas.getLoja(codigo));
                        });

                        menuPopup.show(tabela, e.getX(), e.getY());
                    }
                }
            }
        });

        JButton voltar = new JButton("Voltar");
        botoesPanel.add(voltar);
        botoesPanel.add(Box.createHorizontalGlue());
        lista.add(botoesPanel);

        voltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botão 'voltar' clicado");
                lista.setVisible(false);
                interfaceGrafica.sistemaDono();
            }
        });

        return lista;
    }

    protected void exibeLoja(String codigo, Loja loja) {

        JFrame exibe = new JFrame("Informações da Loja: " + codigo);
        exibe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        exibe.setSize(450, 250);
        exibe.setLocationRelativeTo(null);

        JPanel exibeInformacaoLoja = new JPanel(new BorderLayout(10, 10));
        exibeInformacaoLoja.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Informações de loja", SwingConstants.CENTER);
        exibeInformacaoLoja.add(titulo, BorderLayout.NORTH);

        JPanel tabela = new JPanel(new GridLayout(3, 2, 10, 10));

        tabela.add(interfaceGrafica.criaCelula("Código da loja: "));
        tabela.add(interfaceGrafica.criaCelula(loja.getCodigo()));

        tabela.add(interfaceGrafica.criaCelula("Endereço: "));
        tabela.add(interfaceGrafica.criaCelula(loja.getEndereco()));

        tabela.add(interfaceGrafica.criaCelula("CPF do gerente: "));
        tabela.add(interfaceGrafica.criaCelula(loja.getCpfGerente()));

        String[] colunas = {"Nome", "CPF", "e-mail","Valor atual de vendas"};

        String[][] dados = new String[loja.getArmazenaVendedores().size()][4];
        Vendedor[] v = loja.rankVendedores();
        int i = 0;
        for (int j = 0; j < loja.getArmazenaVendedores().size();j++ ) {
            dados[i][0] = v[j].getNome();
            dados[i][1] = v[j].getCpf();
            dados[i][2] = v[j].getEmail();
            dados[i][3] = String.valueOf(v[j].getValorVenda());
            i++;
        }

        DefaultTableModel modelo = new DefaultTableModel(dados, colunas) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        exibeInformacaoLoja.add(tabela, BorderLayout.NORTH);

        JPanel painelBotao = new JPanel();
        JButton sair = new JButton("Fechar");
        sair.addActionListener(e -> exibe.dispose());
        painelBotao.add(sair);

        JTable tabela2 = new JTable(modelo); // <-- corrigido aqui
        JScrollPane scroll = new JScrollPane(tabela2);
        exibeInformacaoLoja.add(scroll, BorderLayout.CENTER);

        exibeInformacaoLoja.add(painelBotao, BorderLayout.SOUTH);

        exibe.setContentPane(exibeInformacaoLoja);
        exibe.setVisible(true);
    }

    void editarLoja(String codigo) {
        JPanel edicao = new JPanel();
        edicao.setLayout(new BoxLayout(edicao, BoxLayout.Y_AXIS));
        edicao.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] opcoes = {"Endereco","Novo gerente","Editar gerente","codigo"};
        int escolha = JOptionPane.showOptionDialog(
                null,
                "Qual informação deseja editar?",
                "Editar Loja",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );

        if (escolha < 0 || escolha >= opcoes.length) return;
        if(escolha == 2)
            editarGerente(GerenciadorDeLojas.getLoja(codigo).getCpfGerente());
        else{
        String campoSelecionado = opcoes[escolha];
        String labelTexto = "Digite o novo " + campoSelecionado + " da Loja:";

        JLabel label = new JLabel(labelTexto);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        edicao.add(label);

        JTextField campoTexto = new JTextField(20);
        campoTexto.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        campoTexto.setAlignmentX(Component.LEFT_ALIGNMENT);
        edicao.add(campoTexto);

        JButton confirmar = new JButton("Confirmar");

        campoTexto.addActionListener(e -> confirmar.doClick());
        confirmar.addActionListener(e -> {
            System.out.println("Botão Confirmar clicado");
            String valor = campoTexto.getText();

            try {
                String Endereco = "", novoGerente = "", novoCodigo = "";
                switch (escolha) {
                    case 0: Endereco = valor; break;
                    case 1: novoGerente = valor; break;
                    case 3: novoCodigo = valor; break;
                }
                String msg = GerenciadorSistemaDono.editarLoja(Endereco, novoGerente,novoCodigo,codigo);
                JOptionPane.showMessageDialog(null, msg, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                campoTexto.setText("");
            } catch (EntradaException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao editar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new BoxLayout(botoesPanel, BoxLayout.X_AXIS));
        botoesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        botoesPanel.setMaximumSize(new Dimension(400, 30));

        botoesPanel.add(Box.createHorizontalGlue());
        botoesPanel.add(confirmar);
        edicao.add(botoesPanel);

        InterfaceGrafica.trocarTela(edicao, 400, 200);
        }
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

        JButton voltar = new JButton("voltar");
        subPainel.add(voltar);

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

        voltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botão 'voltar' clicado");
                cadastraGerentes.setVisible(false);
                interfaceGrafica.sistemaDono();
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


                        editarItem.addActionListener(ae -> {
                            editarGerente(cpf);
                        });

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

        JButton voltar = new JButton("Voltar");
        botoesPanel.add(voltar);

        botoesPanel.add(Box.createHorizontalGlue());
        lista.add(botoesPanel);

        voltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botão 'voltar' clicado");
                lista.setVisible(false);
                interfaceGrafica.sistemaDono();
            }
        });

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

        tabela.add(interfaceGrafica.criaCelula("Nome do gerente:"));
        tabela.add(interfaceGrafica.criaCelula(gerente.getNome()));

        tabela.add(interfaceGrafica.criaCelula("CPF:"));
        tabela.add(interfaceGrafica.criaCelula(gerente.getCpf()));

        tabela.add(interfaceGrafica.criaCelula("Email:"));
        tabela.add(interfaceGrafica.criaCelula(gerente.getEmail()));

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

    void editarGerente(String cpf) {
        JPanel edicao = new JPanel();
        edicao.setLayout(new BoxLayout(edicao, BoxLayout.Y_AXIS));
        edicao.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] opcoes = {"Nome", "Email", "CPF", "Senha"};
        int escolha = JOptionPane.showOptionDialog(
                null,
                "Qual informação deseja editar?",
                "Editar Gerente",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );

        if (escolha < 0 || escolha >= opcoes.length) return;

        String campoSelecionado = opcoes[escolha];
        String labelTexto = "Digite o novo " + campoSelecionado + " do gerente:";

        JLabel label = new JLabel(labelTexto);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        edicao.add(label);

        JTextField campoTexto = new JTextField(20);
        campoTexto.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        campoTexto.setAlignmentX(Component.LEFT_ALIGNMENT);
        edicao.add(campoTexto);

        JButton confirmar = new JButton("Confirmar");

        campoTexto.addActionListener(e -> confirmar.doClick());
        confirmar.addActionListener(e -> {
            System.out.println("Botão Confirmar clicado");
            String valor = campoTexto.getText().trim();

            try {
                String nome = "", cpfNovo = "", email = "", senha = "";
                switch (escolha) {
                    case 0: nome = valor; break;
                    case 1: email = valor; break;
                    case 2: cpfNovo = valor; break;
                    case 3: senha = valor; break;
                }

                String msg = GerenciadorSistemaDono.editarGerente(nome, cpfNovo, email, senha, cpf);
                JOptionPane.showMessageDialog(null, msg, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                campoTexto.setText("");
            } catch (EntradaException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao editar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton sair = new JButton("voltar");
        //sair.addActionListener(e -> interfaceGrafica.voltar());

        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new BoxLayout(botoesPanel, BoxLayout.X_AXIS));
        botoesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        botoesPanel.setMaximumSize(new Dimension(400, 30));

        botoesPanel.add(sair);
        botoesPanel.add(Box.createHorizontalGlue());
        botoesPanel.add(confirmar);
        edicao.add(botoesPanel);

        InterfaceGrafica.trocarTela(edicao, 400, 200);
    }


}

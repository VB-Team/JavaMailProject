/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.views;

import com.vbteam.views.visualUtils.UIHandler;
import com.vbteam.models.Command;
import com.vbteam.models.User;
import com.vbteam.controller.socket.ConnectionService;
import com.vbteam.views.services.vAuth;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author BatuPC
 */
public class FrmAuth extends javax.swing.JFrame implements MouseListener {

    public static UIHandler uihandler = new UIHandler();
    public static ConnectionService conService;
    static FrmDialog popupPanel;

    public FrmDashboard dashboard;
    public User accountUser = new User();
    private int pX, pY;
    private String pwString = "";
    private CardLayout mainLayout, registerLayout;

    public FrmAuth() {

        uihandler.setAuthFrame(this);
        setUndecorated(true);

        popupPanel = new FrmDialog();

        initComponents();
        Connection();
        moveTitlebar();

        setListeners();

        setLocationRelativeTo(null);
        this.setSize(1370, 819);
        pack();

        mainLayout = (CardLayout) cardPanel.getLayout();
        registerLayout = (CardLayout) register_screen.getLayout();
    }

    public void hoverColor(Component jpanel, Color color) {
        jpanel.setBackground(color);
    }

    private void Connection() {
        JFrame frame = this;
        Runnable connectServerRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    conService = new ConnectionService("127.0.0.1", frame);
                    conService.connectServer();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        };

        Thread t = new Thread(connectServerRunnable);
        t.start();
    }

    public static void popupDialog(String type, String message) {
        try {

            popupPanel.setIcon(type);
            popupPanel.setMessage(message);
            popupPanel.setVisible(true);
        } catch (Exception ex) {
            popupPanel.dispose();
        }
    }

    public static void disposeDialog() {
        popupPanel.setVisible(false);
        popupPanel.dispose();
    }

    private void setListeners() {
        pnl_login.addMouseListener(this);
        pnl_register.addMouseListener(this);
        pnl_login_arrow.addMouseListener(this);
        pnl_register_username_arrow.addMouseListener(this);
        pnl_register_password_arrow.addMouseListener(this);
        pnl_register_detail_arrow.addMouseListener(this);

        anamenu_geri_login.addMouseListener(this);
        anamenu_geri_register.addMouseListener(this);
        anamenu_geri_register_details.addMouseListener(this);
        anamenu_geri_register_password.addMouseListener(this);
    }

    public void moveTitlebar() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2 && !evt.isConsumed()) {
                    evt.consume();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                pX = e.getX();
                pY = e.getY();
            }
        });

        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent evt) {
                setLocation(evt.getXOnScreen() - pX, evt.getYOnScreen() - pY);
            }
        });
    }

    @Override
    public void mouseClicked(MouseEvent evt) {

        if (evt.getSource() == pnl_login) {
            if (conService.isConnected()) {
                mainLayout.show(cardPanel, "login");
            } else {
                popupDialog("error", "Sunucu ile bağlantı kurulmaya çalışıyor.Lütfen tekrar deneyin.");
            }
        }

        if (evt.getSource() == anamenu_geri_login || evt.getSource() == anamenu_geri_register) {
            mainLayout.show(cardPanel, "home");
        }

        if (evt.getSource() == anamenu_geri_register_details) {
            mainLayout.show(register_card, "password");
        }

        if (evt.getSource() == anamenu_geri_register_password) {
            mainLayout.show(register_card, "username");
        }

        if (evt.getSource() == pnl_register) {
            if (conService.isConnected()) {
                mainLayout.show(cardPanel, "register");
            } else {
                popupDialog("error", "Sunucu ile bağlantı kurulmaya çalışıyor.Lütfen tekrar deneyin.");
            }
        }

        if (evt.getSource() == pnl_register_username_arrow) {
            popupDialog("wait", "Lütfen bekleyiniz.");

            User nameCheck = new User();
            nameCheck.setUserName(register_field_username.getText());

            conService.SendCommand(new Command("auth-exist", null, nameCheck, null));
        }
        if (evt.getSource() == pnl_register_password_arrow) {
            pwString = new String(register_password_field.getPassword());
            String controlString = new String(register_password_field_control.getPassword());
            if (vAuth.passwordControl(pwString, controlString)) {
                accountUser.setPassword(pwString);
                registerLayout.show(register_screen, "details");
            }
        }

        if (evt.getSource() == pnl_login_arrow) {
            User _user = new User();

            String username = login_field_username.getText();
            String password = new String(login_field_password.getPassword());
            if (!username.isEmpty() && !password.isEmpty()) {
                try {

                    _user.setUserName(username);
                    _user.setPassword(password);
                    _user.setRole("User");

                    conService.SendCommand(new Command("auth-login", null, _user, null));
                    popupDialog("wait", "Lütfen bekleyiniz.");

                    //Command _command = (Command) conService.getInputStream().readObject();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                popupDialog("error", "Kullanıcı adı ve şifre girdiğinizden emin olun.");
            }

        }

        if (evt.getSource() == pnl_register_detail_arrow) {
            //popupDialog("wait", "Lütfen bekleyiniz.");
            String name = register_detail_field_name.getText();
            String surName = register_detail_field_surname.getText();
            if (!name.isEmpty() && !surName.isEmpty()) {
                try {

                    User regUser = new User();
                    regUser.setFirstName(name);
                    regUser.setLastName(surName);
                    regUser.setUserName(register_field_username.getText());
                    regUser.setPassword(pwString);
                    regUser.setRole("User");
                    regUser.setLastLogin(new java.sql.Timestamp(new java.util.Date().getTime()));

                    registerLayout.show(register_screen, "finish");
                    conService.SendCommand(new Command("auth-register", null, regUser, null));

                } catch (Exception ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            } else {
                popupDialog("error", "İsminizi ve Soy isminizi girdiğinizden emin olun.");
            }
        }
    }

    public void userExist() {
        disposeDialog();
        registerLayout.show(register_screen, "password");
        accountUser.setUserName(register_field_username.getText());
    }

    public void loginCompleted(User _user) {
        try {
            disposeDialog();
            //login Process
            if (!(_user == null)) {

                dashboard = new FrmDashboard();

                uihandler.setDashboardFrame(dashboard);

                accountUser = _user;
                dashboard.setUserDetails(accountUser, "login");

                this.setVisible(false);
            } else {
                popupDialog("error", "Giriş başarısız");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void registerCompleted(User _user) {
        disposeDialog();
        //Register Process
        if (!(_user == null)) {
            dashboard = new FrmDashboard();

            uihandler.setDashboardFrame(dashboard);

            accountUser = _user;
            dashboard.setUserDetails(accountUser, "register");
            this.setVisible(false);
        } else {
            popupDialog("error", "Kayıt başarısız");
            registerLayout.show(register_screen, "username");
        }
    }

    @Override
    public void mousePressed(MouseEvent evt) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent evt) {

    }

    @Override
    public void mouseEntered(MouseEvent evt) {
        if (evt.getSource() == pnl_login || evt.getSource() == pnl_register || evt.getSource() == pnl_login_arrow || evt.getSource() == pnl_register_username_arrow) {
            JPanel panel = (JPanel) evt.getSource();
            panel.setOpaque(true);
            repaint();
        }

    }

    @Override
    public void mouseExited(MouseEvent evt) {
        if (evt.getSource() == pnl_login || evt.getSource() == pnl_register || evt.getSource() == pnl_login_arrow || evt.getSource() == pnl_register_username_arrow) {
            JPanel panel = (JPanel) evt.getSource();
            panel.setOpaque(false);
            repaint();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cardPanel = new javax.swing.JPanel();
        home_card = new javax.swing.JPanel();
        home_panel = new javax.swing.JPanel();
        home_screen = new javax.swing.JPanel();
        home_Buttons = new javax.swing.JPanel();
        pnl_login = new javax.swing.JPanel();
        lbl_login = new javax.swing.JLabel();
        pnl_register = new javax.swing.JPanel();
        lbl_register = new javax.swing.JLabel();
        home_Logo = new javax.swing.JPanel();
        home_logo = new javax.swing.JLabel();
        background1 = new javax.swing.JLabel();
        register_card = new javax.swing.JPanel();
        register_panel = new javax.swing.JPanel();
        register_screen = new javax.swing.JPanel();
        register_username = new javax.swing.JPanel();
        register_username_input = new javax.swing.JPanel();
        register_username_label = new javax.swing.JLabel();
        register_field_username = new javax.swing.JTextField();
        register_seperator_username = new javax.swing.JSeparator();
        pnl_register_username_arrow = new javax.swing.JPanel();
        lbl_register_username_arrow = new javax.swing.JLabel();
        pnl_progress = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        username_seperator_username = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        anamenu_geri_register = new javax.swing.JLabel();
        register_username_logo = new javax.swing.JPanel();
        lbl_register_username_logo = new javax.swing.JLabel();
        register_password = new javax.swing.JPanel();
        register_password_input = new javax.swing.JPanel();
        register_password_label_check = new javax.swing.JLabel();
        register_seperator_password = new javax.swing.JSeparator();
        pnl_register_password_arrow = new javax.swing.JPanel();
        lbl_register_password_arrow = new javax.swing.JLabel();
        register_seperator_password_check = new javax.swing.JSeparator();
        register_password_label = new javax.swing.JLabel();
        pnl_progress1 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        password_seperator_username = new javax.swing.JSeparator();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        register_password_field = new javax.swing.JPasswordField();
        register_password_field_control = new javax.swing.JPasswordField();
        jScrollPane1 = new javax.swing.JScrollPane();
        politika = new javax.swing.JTextArea();
        anamenu_geri_register_password = new javax.swing.JLabel();
        register_password_logo = new javax.swing.JPanel();
        lbl_register_password_logo = new javax.swing.JLabel();
        register_details = new javax.swing.JPanel();
        register_password_logo1 = new javax.swing.JPanel();
        lbl_register_password_logo1 = new javax.swing.JLabel();
        register_password_input1 = new javax.swing.JPanel();
        register_detail_label_check = new javax.swing.JLabel();
        register_detail_field_name = new javax.swing.JTextField();
        register_seperator_detail = new javax.swing.JSeparator();
        pnl_register_detail_arrow = new javax.swing.JPanel();
        lbl_register_password_arrow1 = new javax.swing.JLabel();
        register_detail_field_surname = new javax.swing.JTextField();
        register_seperator_detail_check = new javax.swing.JSeparator();
        register_detail_label = new javax.swing.JLabel();
        pnl_progress2 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        password_seperator_username1 = new javax.swing.JSeparator();
        jPanel15 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jPanel17 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        anamenu_geri_register_details = new javax.swing.JLabel();
        register_finished = new javax.swing.JPanel();
        pnl_regis = new javax.swing.JPanel();
        pnl_progress3 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        password_seperator_username2 = new javax.swing.JSeparator();
        jPanel21 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        jPanel23 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        register_password_logo2 = new javax.swing.JPanel();
        lbl_register_password_logo2 = new javax.swing.JLabel();
        background2 = new javax.swing.JLabel();
        login_card = new javax.swing.JPanel();
        login_panel = new javax.swing.JPanel();
        login_screen = new javax.swing.JPanel();
        login_logo_panel = new javax.swing.JPanel();
        login_logo = new javax.swing.JLabel();
        login_inputs = new javax.swing.JPanel();
        separator_username = new javax.swing.JSeparator();
        login_username_label = new javax.swing.JLabel();
        login_password_label = new javax.swing.JLabel();
        login_field_username = new javax.swing.JTextField();
        login_field_password = new javax.swing.JPasswordField();
        separator_password = new javax.swing.JSeparator();
        pnl_login_arrow = new javax.swing.JPanel();
        lbl_login_arrow = new javax.swing.JLabel();
        anamenu_geri_login = new javax.swing.JLabel();
        background3 = new javax.swing.JLabel();
        topBar = new javax.swing.JPanel();
        buttons = new javax.swing.JPanel();
        pnl_titlebuttons = new javax.swing.JPanel();
        pnl_btnclose = new javax.swing.JPanel();
        btn_minimize = new javax.swing.JButton();
        pnl_btnminimize = new javax.swing.JPanel();
        btn_close = new javax.swing.JButton();
        titlebar = new javax.swing.JPanel();
        title = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1370, 819));

        cardPanel.setLayout(new java.awt.CardLayout());

        home_card.setLayout(new java.awt.BorderLayout());

        home_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        home_screen.setOpaque(false);
        home_screen.setLayout(new java.awt.BorderLayout());

        home_Buttons.setBackground(new java.awt.Color(31, 31, 31));
        home_Buttons.setOpaque(false);
        home_Buttons.setPreferredSize(new java.awt.Dimension(1370, 70));
        home_Buttons.setLayout(new java.awt.BorderLayout());

        pnl_login.setBackground(new java.awt.Color(30, 29, 32));
        pnl_login.setOpaque(false);
        pnl_login.setPreferredSize(new java.awt.Dimension(690, 15));
        pnl_login.setLayout(new java.awt.BorderLayout());

        lbl_login.setBackground(new java.awt.Color(31, 31, 31));
        lbl_login.setForeground(new java.awt.Color(165, 165, 172));
        lbl_login.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_login.setText("Giriş Yap");
        lbl_login.setPreferredSize(new java.awt.Dimension(700, 15));
        pnl_login.add(lbl_login, java.awt.BorderLayout.LINE_START);

        home_Buttons.add(pnl_login, java.awt.BorderLayout.LINE_START);

        pnl_register.setBackground(new java.awt.Color(30, 29, 32));
        pnl_register.setOpaque(false);
        pnl_register.setPreferredSize(new java.awt.Dimension(680, 15));
        pnl_register.setLayout(new java.awt.BorderLayout());

        lbl_register.setBackground(new java.awt.Color(30, 29, 32));
        lbl_register.setForeground(new java.awt.Color(165, 165, 172));
        lbl_register.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_register.setText("Kayıt Ol");
        lbl_register.setPreferredSize(new java.awt.Dimension(690, 15));
        pnl_register.add(lbl_register, java.awt.BorderLayout.LINE_END);

        home_Buttons.add(pnl_register, java.awt.BorderLayout.LINE_END);

        home_screen.add(home_Buttons, java.awt.BorderLayout.PAGE_END);

        home_Logo.setOpaque(false);
        home_Logo.setLayout(new java.awt.BorderLayout());

        home_logo.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        home_logo.setForeground(new java.awt.Color(165, 165, 172));
        home_logo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        home_logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/vblogowhite.png"))); // NOI18N
        home_logo.setText("Veysel ve Batu Mail Projesi");
        home_logo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        home_logo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        home_Logo.add(home_logo, java.awt.BorderLayout.CENTER);

        home_screen.add(home_Logo, java.awt.BorderLayout.CENTER);

        home_panel.add(home_screen, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1370, 770));

        background1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        background1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/mailbackground.png"))); // NOI18N
        background1.setPreferredSize(new java.awt.Dimension(1366, 768));
        home_panel.add(background1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -90, 1370, 860));

        home_card.add(home_panel, java.awt.BorderLayout.CENTER);

        cardPanel.add(home_card, "home");

        register_card.setLayout(new java.awt.BorderLayout());

        register_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        register_screen.setOpaque(false);
        register_screen.setLayout(new java.awt.CardLayout());

        register_username.setOpaque(false);
        register_username.setLayout(new java.awt.BorderLayout());

        register_username_input.setBackground(new java.awt.Color(20, 20, 22));
        register_username_input.setOpaque(false);
        register_username_input.setPreferredSize(new java.awt.Dimension(500, 500));
        register_username_input.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        register_username_label.setForeground(new java.awt.Color(165, 165, 172));
        register_username_label.setText("Kullanıcı Adı");
        register_username_input.add(register_username_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 110, -1, -1));

        register_field_username.setBackground(new java.awt.Color(20, 20, 22));
        register_field_username.setForeground(new java.awt.Color(254, 254, 254));
        register_field_username.setBorder(null);
        register_username_input.add(register_field_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 130, 220, 40));

        register_seperator_username.setBackground(new java.awt.Color(25, 25, 28));
        register_seperator_username.setForeground(new java.awt.Color(25, 25, 28));
        register_username_input.add(register_seperator_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 170, 220, 40));

        pnl_register_username_arrow.setBackground(new java.awt.Color(30, 29, 32));
        pnl_register_username_arrow.setOpaque(false);
        pnl_register_username_arrow.setLayout(new java.awt.BorderLayout());

        lbl_register_username_arrow.setBackground(new java.awt.Color(30, 29, 32));
        lbl_register_username_arrow.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_register_username_arrow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/right-arrow.png"))); // NOI18N
        lbl_register_username_arrow.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pnl_register_username_arrow.add(lbl_register_username_arrow, java.awt.BorderLayout.CENTER);

        register_username_input.add(pnl_register_username_arrow, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 120, 60, 60));

        pnl_progress.setOpaque(false);
        pnl_progress.setLayout(new java.awt.BorderLayout());

        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(450, 450));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel4.setOpaque(false);
        jPanel4.setPreferredSize(new java.awt.Dimension(45, 45));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jLabel1.setForeground(new java.awt.Color(165, 165, 172));
        jLabel1.setText("Kullanıcı Adı");
        jLabel1.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel4.add(jLabel1, java.awt.BorderLayout.CENTER);

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(165, 165, 172));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel2.setText("01   ");
        jLabel2.setPreferredSize(new java.awt.Dimension(200, 200));
        jPanel4.add(jLabel2, java.awt.BorderLayout.LINE_START);

        jPanel1.add(jPanel4, java.awt.BorderLayout.PAGE_START);
        jPanel1.add(username_seperator_username, java.awt.BorderLayout.CENTER);

        pnl_progress.add(jPanel1, java.awt.BorderLayout.LINE_START);

        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(450, 450));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel5.setOpaque(false);
        jPanel5.setPreferredSize(new java.awt.Dimension(45, 45));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jLabel3.setForeground(new java.awt.Color(165, 165, 172));
        jLabel3.setText("Şifre");
        jLabel3.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel5.add(jLabel3, java.awt.BorderLayout.CENTER);

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(165, 165, 172));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel4.setText("02   ");
        jLabel4.setPreferredSize(new java.awt.Dimension(200, 200));
        jPanel5.add(jLabel4, java.awt.BorderLayout.LINE_START);

        jPanel2.add(jPanel5, java.awt.BorderLayout.PAGE_START);
        jPanel2.add(jSeparator2, java.awt.BorderLayout.CENTER);

        pnl_progress.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(450, 450));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel6.setOpaque(false);
        jPanel6.setPreferredSize(new java.awt.Dimension(45, 45));
        jPanel6.setLayout(new java.awt.BorderLayout());

        jLabel5.setForeground(new java.awt.Color(165, 165, 172));
        jLabel5.setText("Kullanıcı Bilgileri");
        jLabel5.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel6.add(jLabel5, java.awt.BorderLayout.CENTER);

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(165, 165, 172));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel6.setText("03   ");
        jLabel6.setPreferredSize(new java.awt.Dimension(200, 200));
        jPanel6.add(jLabel6, java.awt.BorderLayout.LINE_START);

        jPanel3.add(jPanel6, java.awt.BorderLayout.PAGE_START);
        jPanel3.add(jSeparator3, java.awt.BorderLayout.CENTER);

        pnl_progress.add(jPanel3, java.awt.BorderLayout.LINE_END);

        register_username_input.add(pnl_progress, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 430, 1370, 70));

        anamenu_geri_register.setForeground(new java.awt.Color(254, 254, 254));
        anamenu_geri_register.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        anamenu_geri_register.setText("Geri");
        register_username_input.add(anamenu_geri_register, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 80, 50));

        register_username.add(register_username_input, java.awt.BorderLayout.PAGE_END);

        register_username_logo.setOpaque(false);
        register_username_logo.setLayout(new java.awt.BorderLayout());

        lbl_register_username_logo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_register_username_logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/vblogowhite.png"))); // NOI18N
        lbl_register_username_logo.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lbl_register_username_logo.setPreferredSize(new java.awt.Dimension(300, 300));
        register_username_logo.add(lbl_register_username_logo, java.awt.BorderLayout.PAGE_START);

        register_username.add(register_username_logo, java.awt.BorderLayout.CENTER);

        register_screen.add(register_username, "username");

        register_password.setForeground(new java.awt.Color(254, 254, 254));
        register_password.setOpaque(false);
        register_password.setLayout(new java.awt.BorderLayout());

        register_password_input.setBackground(new java.awt.Color(20, 20, 22));
        register_password_input.setOpaque(false);
        register_password_input.setPreferredSize(new java.awt.Dimension(500, 500));
        register_password_input.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        register_password_label_check.setForeground(new java.awt.Color(165, 165, 172));
        register_password_label_check.setText("Yeniden Şifre");
        register_password_input.add(register_password_label_check, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 180, -1, -1));

        register_seperator_password.setBackground(new java.awt.Color(25, 25, 28));
        register_seperator_password.setForeground(new java.awt.Color(25, 25, 28));
        register_password_input.add(register_seperator_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 170, 220, 40));

        pnl_register_password_arrow.setBackground(new java.awt.Color(30, 29, 32));
        pnl_register_password_arrow.setOpaque(false);
        pnl_register_password_arrow.setLayout(new java.awt.BorderLayout());

        lbl_register_password_arrow.setBackground(new java.awt.Color(30, 29, 32));
        lbl_register_password_arrow.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_register_password_arrow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/right-arrow.png"))); // NOI18N
        lbl_register_password_arrow.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pnl_register_password_arrow.add(lbl_register_password_arrow, java.awt.BorderLayout.CENTER);

        register_password_input.add(pnl_register_password_arrow, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 130, 60, 110));

        register_seperator_password_check.setBackground(new java.awt.Color(25, 25, 28));
        register_seperator_password_check.setForeground(new java.awt.Color(25, 25, 28));
        register_password_input.add(register_seperator_password_check, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 240, 220, 40));

        register_password_label.setForeground(new java.awt.Color(165, 165, 172));
        register_password_label.setText("Şifre");
        register_password_input.add(register_password_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 110, -1, -1));

        pnl_progress1.setOpaque(false);
        pnl_progress1.setLayout(new java.awt.BorderLayout());

        jPanel7.setOpaque(false);
        jPanel7.setPreferredSize(new java.awt.Dimension(450, 450));
        jPanel7.setLayout(new java.awt.BorderLayout());

        jPanel8.setOpaque(false);
        jPanel8.setPreferredSize(new java.awt.Dimension(45, 45));
        jPanel8.setLayout(new java.awt.BorderLayout());

        jLabel7.setForeground(new java.awt.Color(254, 254, 254));
        jLabel7.setText("Kullanıcı Adı");
        jLabel7.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel8.add(jLabel7, java.awt.BorderLayout.CENTER);

        jLabel8.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(254, 254, 254));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel8.setText("01   ");
        jLabel8.setPreferredSize(new java.awt.Dimension(200, 200));
        jPanel8.add(jLabel8, java.awt.BorderLayout.LINE_START);

        jPanel7.add(jPanel8, java.awt.BorderLayout.PAGE_START);

        password_seperator_username.setBackground(new java.awt.Color(0, 0, 0));
        password_seperator_username.setForeground(new java.awt.Color(0, 0, 0));
        password_seperator_username.setToolTipText("");
        password_seperator_username.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 0, 0, 0, new java.awt.Color(220, 82, 38)));
        jPanel7.add(password_seperator_username, java.awt.BorderLayout.CENTER);

        pnl_progress1.add(jPanel7, java.awt.BorderLayout.LINE_START);

        jPanel9.setOpaque(false);
        jPanel9.setPreferredSize(new java.awt.Dimension(450, 450));
        jPanel9.setLayout(new java.awt.BorderLayout());

        jPanel10.setOpaque(false);
        jPanel10.setPreferredSize(new java.awt.Dimension(45, 45));
        jPanel10.setLayout(new java.awt.BorderLayout());

        jLabel9.setForeground(new java.awt.Color(165, 165, 172));
        jLabel9.setText("Şifre");
        jLabel9.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel10.add(jLabel9, java.awt.BorderLayout.CENTER);

        jLabel10.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(165, 165, 172));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel10.setText("02   ");
        jLabel10.setPreferredSize(new java.awt.Dimension(200, 200));
        jPanel10.add(jLabel10, java.awt.BorderLayout.LINE_START);

        jPanel9.add(jPanel10, java.awt.BorderLayout.PAGE_START);
        jPanel9.add(jSeparator5, java.awt.BorderLayout.CENTER);

        pnl_progress1.add(jPanel9, java.awt.BorderLayout.CENTER);

        jPanel11.setOpaque(false);
        jPanel11.setPreferredSize(new java.awt.Dimension(450, 450));
        jPanel11.setLayout(new java.awt.BorderLayout());

        jPanel12.setOpaque(false);
        jPanel12.setPreferredSize(new java.awt.Dimension(45, 45));
        jPanel12.setLayout(new java.awt.BorderLayout());

        jLabel11.setForeground(new java.awt.Color(165, 165, 172));
        jLabel11.setText("Kullanıcı Bilgileri");
        jLabel11.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel12.add(jLabel11, java.awt.BorderLayout.CENTER);

        jLabel12.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(165, 165, 172));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel12.setText("03   ");
        jLabel12.setPreferredSize(new java.awt.Dimension(200, 200));
        jPanel12.add(jLabel12, java.awt.BorderLayout.LINE_START);

        jPanel11.add(jPanel12, java.awt.BorderLayout.PAGE_START);
        jPanel11.add(jSeparator6, java.awt.BorderLayout.CENTER);

        pnl_progress1.add(jPanel11, java.awt.BorderLayout.LINE_END);

        register_password_input.add(pnl_progress1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 430, 1370, 70));

        register_password_field.setBackground(new java.awt.Color(20, 20, 22));
        register_password_field.setForeground(new java.awt.Color(254, 254, 254));
        register_password_field.setBorder(null);
        register_password_input.add(register_password_field, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 200, 220, 40));

        register_password_field_control.setBackground(new java.awt.Color(20, 20, 22));
        register_password_field_control.setForeground(new java.awt.Color(255, 254, 255));
        register_password_field_control.setBorder(null);
        register_password_input.add(register_password_field_control, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 130, 220, 40));

        jScrollPane1.setBackground(new java.awt.Color(20, 20, 22));
        jScrollPane1.setBorder(null);
        jScrollPane1.setForeground(new java.awt.Color(20, 20, 22));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        politika.setEditable(false);
        politika.setBackground(new java.awt.Color(20, 20, 22));
        politika.setColumns(20);
        politika.setForeground(new java.awt.Color(51, 51, 55));
        politika.setRows(5);
        politika.setText("Şifre Politikası \n-  En az 9  karakter uzunluğunda olmalıdır,\n-  En az 1 büyük harf, 1 küçük harf, 1 rakam \n-  1 özel (Örn; @#$% )karakter");
        jScrollPane1.setViewportView(politika);

        register_password_input.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 270, 290, 130));

        anamenu_geri_register_password.setForeground(new java.awt.Color(254, 254, 254));
        anamenu_geri_register_password.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        anamenu_geri_register_password.setText("Geri");
        register_password_input.add(anamenu_geri_register_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 80, 50));

        register_password.add(register_password_input, java.awt.BorderLayout.PAGE_END);

        register_password_logo.setOpaque(false);
        register_password_logo.setLayout(new java.awt.BorderLayout());

        lbl_register_password_logo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_register_password_logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/vblogowhite.png"))); // NOI18N
        lbl_register_password_logo.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lbl_register_password_logo.setPreferredSize(new java.awt.Dimension(300, 300));
        register_password_logo.add(lbl_register_password_logo, java.awt.BorderLayout.PAGE_START);

        register_password.add(register_password_logo, java.awt.BorderLayout.CENTER);

        register_screen.add(register_password, "password");

        register_details.setOpaque(false);
        register_details.setLayout(new java.awt.BorderLayout());

        register_password_logo1.setOpaque(false);
        register_password_logo1.setLayout(new java.awt.BorderLayout());

        lbl_register_password_logo1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_register_password_logo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/vblogowhite.png"))); // NOI18N
        lbl_register_password_logo1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lbl_register_password_logo1.setPreferredSize(new java.awt.Dimension(300, 300));
        register_password_logo1.add(lbl_register_password_logo1, java.awt.BorderLayout.PAGE_START);

        register_details.add(register_password_logo1, java.awt.BorderLayout.CENTER);

        register_password_input1.setBackground(new java.awt.Color(20, 20, 22));
        register_password_input1.setOpaque(false);
        register_password_input1.setPreferredSize(new java.awt.Dimension(500, 500));
        register_password_input1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        register_detail_label_check.setForeground(new java.awt.Color(165, 165, 172));
        register_detail_label_check.setText("Soyadınız");
        register_password_input1.add(register_detail_label_check, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 180, -1, -1));

        register_detail_field_name.setBackground(new java.awt.Color(20, 20, 22));
        register_detail_field_name.setForeground(new java.awt.Color(254, 254, 254));
        register_detail_field_name.setBorder(null);
        register_password_input1.add(register_detail_field_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 130, 220, 40));

        register_seperator_detail.setBackground(new java.awt.Color(25, 25, 28));
        register_seperator_detail.setForeground(new java.awt.Color(25, 25, 28));
        register_password_input1.add(register_seperator_detail, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 170, 220, 40));

        pnl_register_detail_arrow.setBackground(new java.awt.Color(30, 29, 32));
        pnl_register_detail_arrow.setOpaque(false);
        pnl_register_detail_arrow.setLayout(new java.awt.BorderLayout());

        lbl_register_password_arrow1.setBackground(new java.awt.Color(30, 29, 32));
        lbl_register_password_arrow1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_register_password_arrow1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/right-arrow.png"))); // NOI18N
        lbl_register_password_arrow1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pnl_register_detail_arrow.add(lbl_register_password_arrow1, java.awt.BorderLayout.CENTER);

        register_password_input1.add(pnl_register_detail_arrow, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 130, 60, 110));

        register_detail_field_surname.setBackground(new java.awt.Color(20, 20, 22));
        register_detail_field_surname.setForeground(new java.awt.Color(254, 254, 254));
        register_detail_field_surname.setBorder(null);
        register_password_input1.add(register_detail_field_surname, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 200, 220, 40));

        register_seperator_detail_check.setBackground(new java.awt.Color(25, 25, 28));
        register_seperator_detail_check.setForeground(new java.awt.Color(25, 25, 28));
        register_password_input1.add(register_seperator_detail_check, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 240, 220, 40));

        register_detail_label.setForeground(new java.awt.Color(165, 165, 172));
        register_detail_label.setText("Adınız");
        register_password_input1.add(register_detail_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 110, -1, -1));

        pnl_progress2.setOpaque(false);
        pnl_progress2.setLayout(new java.awt.BorderLayout());

        jPanel13.setOpaque(false);
        jPanel13.setPreferredSize(new java.awt.Dimension(450, 450));
        jPanel13.setLayout(new java.awt.BorderLayout());

        jPanel14.setOpaque(false);
        jPanel14.setPreferredSize(new java.awt.Dimension(45, 45));
        jPanel14.setLayout(new java.awt.BorderLayout());

        jLabel13.setForeground(new java.awt.Color(254, 254, 254));
        jLabel13.setText("Kullanıcı Adı");
        jLabel13.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel14.add(jLabel13, java.awt.BorderLayout.CENTER);

        jLabel14.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(254, 254, 254));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel14.setText("01   ");
        jLabel14.setPreferredSize(new java.awt.Dimension(200, 200));
        jPanel14.add(jLabel14, java.awt.BorderLayout.LINE_START);

        jPanel13.add(jPanel14, java.awt.BorderLayout.PAGE_START);

        password_seperator_username1.setBackground(new java.awt.Color(0, 0, 0));
        password_seperator_username1.setForeground(new java.awt.Color(0, 0, 0));
        password_seperator_username1.setToolTipText("");
        password_seperator_username1.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 0, 0, 0, new java.awt.Color(220, 82, 38)));
        jPanel13.add(password_seperator_username1, java.awt.BorderLayout.CENTER);

        pnl_progress2.add(jPanel13, java.awt.BorderLayout.LINE_START);

        jPanel15.setForeground(new java.awt.Color(254, 254, 254));
        jPanel15.setOpaque(false);
        jPanel15.setPreferredSize(new java.awt.Dimension(450, 450));
        jPanel15.setLayout(new java.awt.BorderLayout());

        jPanel16.setOpaque(false);
        jPanel16.setPreferredSize(new java.awt.Dimension(45, 45));
        jPanel16.setLayout(new java.awt.BorderLayout());

        jLabel15.setForeground(new java.awt.Color(254, 254, 254));
        jLabel15.setText("Şifre");
        jLabel15.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel16.add(jLabel15, java.awt.BorderLayout.CENTER);

        jLabel16.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(254, 254, 254));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel16.setText("02   ");
        jLabel16.setPreferredSize(new java.awt.Dimension(200, 200));
        jPanel16.add(jLabel16, java.awt.BorderLayout.LINE_START);

        jPanel15.add(jPanel16, java.awt.BorderLayout.PAGE_START);

        jSeparator7.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 0, 0, 0, new java.awt.Color(220, 82, 38)));
        jPanel15.add(jSeparator7, java.awt.BorderLayout.CENTER);

        pnl_progress2.add(jPanel15, java.awt.BorderLayout.CENTER);

        jPanel17.setOpaque(false);
        jPanel17.setPreferredSize(new java.awt.Dimension(450, 450));
        jPanel17.setLayout(new java.awt.BorderLayout());

        jPanel18.setOpaque(false);
        jPanel18.setPreferredSize(new java.awt.Dimension(45, 45));
        jPanel18.setLayout(new java.awt.BorderLayout());

        jLabel17.setForeground(new java.awt.Color(165, 165, 172));
        jLabel17.setText("Kullanıcı Bilgileri");
        jLabel17.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel18.add(jLabel17, java.awt.BorderLayout.CENTER);

        jLabel18.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(165, 165, 172));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel18.setText("03   ");
        jLabel18.setPreferredSize(new java.awt.Dimension(200, 200));
        jPanel18.add(jLabel18, java.awt.BorderLayout.LINE_START);

        jPanel17.add(jPanel18, java.awt.BorderLayout.PAGE_START);
        jPanel17.add(jSeparator8, java.awt.BorderLayout.CENTER);

        pnl_progress2.add(jPanel17, java.awt.BorderLayout.LINE_END);

        register_password_input1.add(pnl_progress2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 430, 1370, 70));

        anamenu_geri_register_details.setForeground(new java.awt.Color(254, 254, 254));
        anamenu_geri_register_details.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        anamenu_geri_register_details.setText("Geri");
        register_password_input1.add(anamenu_geri_register_details, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 80, 50));

        register_details.add(register_password_input1, java.awt.BorderLayout.PAGE_END);

        register_screen.add(register_details, "details");

        register_finished.setOpaque(false);
        register_finished.setLayout(new java.awt.BorderLayout());

        pnl_regis.setBackground(new java.awt.Color(20, 20, 22));
        pnl_regis.setOpaque(false);
        pnl_regis.setPreferredSize(new java.awt.Dimension(500, 500));
        pnl_regis.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnl_progress3.setOpaque(false);
        pnl_progress3.setLayout(new java.awt.BorderLayout());

        jPanel19.setOpaque(false);
        jPanel19.setPreferredSize(new java.awt.Dimension(450, 450));
        jPanel19.setLayout(new java.awt.BorderLayout());

        jPanel20.setOpaque(false);
        jPanel20.setPreferredSize(new java.awt.Dimension(45, 45));
        jPanel20.setLayout(new java.awt.BorderLayout());

        jLabel19.setForeground(new java.awt.Color(255, 254, 255));
        jLabel19.setText("Kullanıcı Adı");
        jLabel19.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel20.add(jLabel19, java.awt.BorderLayout.CENTER);

        jLabel20.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 254, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel20.setText("01   ");
        jLabel20.setPreferredSize(new java.awt.Dimension(200, 200));
        jPanel20.add(jLabel20, java.awt.BorderLayout.LINE_START);

        jPanel19.add(jPanel20, java.awt.BorderLayout.PAGE_START);

        password_seperator_username2.setBackground(new java.awt.Color(0, 0, 0));
        password_seperator_username2.setForeground(new java.awt.Color(0, 0, 0));
        password_seperator_username2.setToolTipText("");
        password_seperator_username2.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 0, 0, 0, new java.awt.Color(220, 82, 38)));
        jPanel19.add(password_seperator_username2, java.awt.BorderLayout.CENTER);

        pnl_progress3.add(jPanel19, java.awt.BorderLayout.LINE_START);

        jPanel21.setOpaque(false);
        jPanel21.setPreferredSize(new java.awt.Dimension(450, 450));
        jPanel21.setLayout(new java.awt.BorderLayout());

        jPanel22.setOpaque(false);
        jPanel22.setPreferredSize(new java.awt.Dimension(45, 45));
        jPanel22.setLayout(new java.awt.BorderLayout());

        jLabel21.setForeground(new java.awt.Color(255, 254, 255));
        jLabel21.setText("Şifre");
        jLabel21.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel22.add(jLabel21, java.awt.BorderLayout.CENTER);

        jLabel22.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 254, 255));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel22.setText("02   ");
        jLabel22.setPreferredSize(new java.awt.Dimension(200, 200));
        jPanel22.add(jLabel22, java.awt.BorderLayout.LINE_START);

        jPanel21.add(jPanel22, java.awt.BorderLayout.PAGE_START);

        jSeparator9.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 0, 0, 0, new java.awt.Color(220, 82, 38)));
        jPanel21.add(jSeparator9, java.awt.BorderLayout.CENTER);

        pnl_progress3.add(jPanel21, java.awt.BorderLayout.CENTER);

        jPanel23.setOpaque(false);
        jPanel23.setPreferredSize(new java.awt.Dimension(450, 450));
        jPanel23.setLayout(new java.awt.BorderLayout());

        jPanel24.setOpaque(false);
        jPanel24.setPreferredSize(new java.awt.Dimension(45, 45));
        jPanel24.setLayout(new java.awt.BorderLayout());

        jLabel23.setForeground(new java.awt.Color(255, 254, 255));
        jLabel23.setText("Kullanıcı Bilgileri");
        jLabel23.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel24.add(jLabel23, java.awt.BorderLayout.CENTER);

        jLabel24.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 254, 255));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel24.setText("03   ");
        jLabel24.setPreferredSize(new java.awt.Dimension(200, 200));
        jPanel24.add(jLabel24, java.awt.BorderLayout.LINE_START);

        jPanel23.add(jPanel24, java.awt.BorderLayout.PAGE_START);

        jSeparator10.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 0, 0, 0, new java.awt.Color(220, 82, 38)));
        jPanel23.add(jSeparator10, java.awt.BorderLayout.CENTER);

        pnl_progress3.add(jPanel23, java.awt.BorderLayout.LINE_END);

        pnl_regis.add(pnl_progress3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 430, 1370, 70));

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/confirm.png"))); // NOI18N
        pnl_regis.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 150, -1, -1));

        jLabel26.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 254, 255));
        jLabel26.setText("Kayıt başarıyla tamamlandı. Sunucudan geri bildirim bekleniyor. Yönlendiriliyorsunuz.");
        pnl_regis.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 170, -1, -1));

        register_finished.add(pnl_regis, java.awt.BorderLayout.PAGE_END);

        register_password_logo2.setOpaque(false);
        register_password_logo2.setLayout(new java.awt.BorderLayout());

        lbl_register_password_logo2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_register_password_logo2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/vblogowhite.png"))); // NOI18N
        lbl_register_password_logo2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lbl_register_password_logo2.setPreferredSize(new java.awt.Dimension(300, 300));
        register_password_logo2.add(lbl_register_password_logo2, java.awt.BorderLayout.PAGE_START);

        register_finished.add(register_password_logo2, java.awt.BorderLayout.CENTER);

        register_screen.add(register_finished, "finish");

        register_panel.add(register_screen, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1370, 770));

        background2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        background2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/mailbackground.png"))); // NOI18N
        background2.setPreferredSize(new java.awt.Dimension(1366, 768));
        register_panel.add(background2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -90, 1370, 860));

        register_card.add(register_panel, java.awt.BorderLayout.CENTER);

        cardPanel.add(register_card, "register");

        login_card.setLayout(new java.awt.BorderLayout());

        login_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        login_screen.setOpaque(false);
        login_screen.setLayout(new java.awt.BorderLayout());

        login_logo_panel.setOpaque(false);
        login_logo_panel.setPreferredSize(new java.awt.Dimension(300, 300));
        login_logo_panel.setLayout(new java.awt.BorderLayout());

        login_logo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        login_logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/vblogowhite.png"))); // NOI18N
        login_logo.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        login_logo.setPreferredSize(new java.awt.Dimension(300, 300));
        login_logo_panel.add(login_logo, java.awt.BorderLayout.PAGE_START);

        login_screen.add(login_logo_panel, java.awt.BorderLayout.CENTER);

        login_inputs.setBackground(new java.awt.Color(20, 20, 22));
        login_inputs.setOpaque(false);
        login_inputs.setPreferredSize(new java.awt.Dimension(500, 500));
        login_inputs.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        separator_username.setBackground(new java.awt.Color(25, 25, 28));
        separator_username.setForeground(new java.awt.Color(25, 25, 28));
        login_inputs.add(separator_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 240, 220, 40));

        login_username_label.setForeground(new java.awt.Color(165, 165, 172));
        login_username_label.setText("Kullanıcı Adı");
        login_inputs.add(login_username_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 110, -1, -1));

        login_password_label.setForeground(new java.awt.Color(165, 165, 172));
        login_password_label.setText("Şifre");
        login_inputs.add(login_password_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 180, -1, -1));

        login_field_username.setBackground(new java.awt.Color(20, 20, 22));
        login_field_username.setForeground(new java.awt.Color(254, 254, 254));
        login_field_username.setBorder(null);
        login_inputs.add(login_field_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 130, 220, 40));

        login_field_password.setBackground(new java.awt.Color(20, 20, 22));
        login_field_password.setForeground(new java.awt.Color(254, 254, 254));
        login_field_password.setBorder(null);
        login_inputs.add(login_field_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 210, 220, 40));

        separator_password.setBackground(new java.awt.Color(25, 25, 28));
        separator_password.setForeground(new java.awt.Color(25, 25, 28));
        login_inputs.add(separator_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 170, 220, 40));

        pnl_login_arrow.setBackground(new java.awt.Color(30, 29, 32));
        pnl_login_arrow.setOpaque(false);
        pnl_login_arrow.setLayout(new java.awt.BorderLayout());

        lbl_login_arrow.setBackground(new java.awt.Color(30, 29, 32));
        lbl_login_arrow.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_login_arrow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/right-arrow.png"))); // NOI18N
        lbl_login_arrow.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pnl_login_arrow.add(lbl_login_arrow, java.awt.BorderLayout.CENTER);

        login_inputs.add(pnl_login_arrow, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 110, 60, 140));

        anamenu_geri_login.setForeground(new java.awt.Color(254, 254, 254));
        anamenu_geri_login.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        anamenu_geri_login.setText("Geri");
        login_inputs.add(anamenu_geri_login, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 80, 70));

        login_screen.add(login_inputs, java.awt.BorderLayout.PAGE_END);

        login_panel.add(login_screen, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1370, 770));

        background3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        background3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/mailbackground.png"))); // NOI18N
        background3.setPreferredSize(new java.awt.Dimension(1366, 768));
        login_panel.add(background3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -90, 1370, 860));

        login_card.add(login_panel, java.awt.BorderLayout.CENTER);

        cardPanel.add(login_card, "login");

        getContentPane().add(cardPanel, java.awt.BorderLayout.CENTER);

        topBar.setBackground(new java.awt.Color(20, 20, 22));
        topBar.setPreferredSize(new java.awt.Dimension(50, 50));
        topBar.setLayout(new java.awt.BorderLayout());

        buttons.setPreferredSize(new java.awt.Dimension(150, 150));
        buttons.setLayout(new java.awt.BorderLayout());

        pnl_titlebuttons.setBackground(new java.awt.Color(43, 49, 68));
        pnl_titlebuttons.setPreferredSize(new java.awt.Dimension(200, 50));
        pnl_titlebuttons.setLayout(new java.awt.BorderLayout());

        pnl_btnclose.setBackground(new java.awt.Color(20, 20, 22));
        pnl_btnclose.setToolTipText("");
        pnl_btnclose.setLayout(new java.awt.BorderLayout());

        btn_minimize.setBackground(new java.awt.Color(51, 53, 65));
        btn_minimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/dry-clean-green.png"))); // NOI18N
        btn_minimize.setBorderPainted(false);
        btn_minimize.setContentAreaFilled(false);
        btn_minimize.setPreferredSize(new java.awt.Dimension(20, 20));
        btn_minimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_minimizeMouseClicked(evt);
            }
        });
        pnl_btnclose.add(btn_minimize, java.awt.BorderLayout.CENTER);

        pnl_titlebuttons.add(pnl_btnclose, java.awt.BorderLayout.CENTER);

        pnl_btnminimize.setBackground(new java.awt.Color(20, 20, 22));
        pnl_btnminimize.setPreferredSize(new java.awt.Dimension(70, 70));
        pnl_btnminimize.setLayout(new java.awt.BorderLayout());

        btn_close.setBackground(new java.awt.Color(51, 53, 65));
        btn_close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/dry-clean.png"))); // NOI18N
        btn_close.setBorder(null);
        btn_close.setBorderPainted(false);
        btn_close.setContentAreaFilled(false);
        btn_close.setPreferredSize(new java.awt.Dimension(32, 32));
        btn_close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_closeMouseClicked(evt);
            }
        });
        pnl_btnminimize.add(btn_close, java.awt.BorderLayout.CENTER);

        pnl_titlebuttons.add(pnl_btnminimize, java.awt.BorderLayout.LINE_END);

        buttons.add(pnl_titlebuttons, java.awt.BorderLayout.CENTER);

        topBar.add(buttons, java.awt.BorderLayout.LINE_END);

        titlebar.setBackground(new java.awt.Color(20, 20, 22));
        titlebar.setLayout(new java.awt.BorderLayout());

        title.setBackground(new java.awt.Color(62, 62, 63));
        title.setForeground(new java.awt.Color(62, 62, 63));
        title.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        title.setText("        VBMail");
        titlebar.add(title, java.awt.BorderLayout.CENTER);

        topBar.add(titlebar, java.awt.BorderLayout.CENTER);

        getContentPane().add(topBar, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_minimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_minimizeMouseClicked
        this.setState(ICONIFIED);
    }//GEN-LAST:event_btn_minimizeMouseClicked

    private void btn_closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_closeMouseClicked
        System.exit(0);
    }//GEN-LAST:event_btn_closeMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmAuth.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmAuth.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmAuth.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmAuth.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmAuth().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel anamenu_geri_login;
    private javax.swing.JLabel anamenu_geri_register;
    private javax.swing.JLabel anamenu_geri_register_details;
    private javax.swing.JLabel anamenu_geri_register_password;
    private javax.swing.JLabel background1;
    private javax.swing.JLabel background2;
    private javax.swing.JLabel background3;
    private javax.swing.JButton btn_close;
    private javax.swing.JButton btn_minimize;
    private javax.swing.JPanel buttons;
    private javax.swing.JPanel cardPanel;
    private javax.swing.JPanel home_Buttons;
    private javax.swing.JPanel home_Logo;
    private javax.swing.JPanel home_card;
    private javax.swing.JLabel home_logo;
    private javax.swing.JPanel home_panel;
    private javax.swing.JPanel home_screen;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JLabel lbl_login;
    private javax.swing.JLabel lbl_login_arrow;
    private javax.swing.JLabel lbl_register;
    private javax.swing.JLabel lbl_register_password_arrow;
    private javax.swing.JLabel lbl_register_password_arrow1;
    private javax.swing.JLabel lbl_register_password_logo;
    private javax.swing.JLabel lbl_register_password_logo1;
    private javax.swing.JLabel lbl_register_password_logo2;
    private javax.swing.JLabel lbl_register_username_arrow;
    private javax.swing.JLabel lbl_register_username_logo;
    private javax.swing.JPanel login_card;
    private javax.swing.JPasswordField login_field_password;
    private javax.swing.JTextField login_field_username;
    private javax.swing.JPanel login_inputs;
    private javax.swing.JLabel login_logo;
    private javax.swing.JPanel login_logo_panel;
    private javax.swing.JPanel login_panel;
    private javax.swing.JLabel login_password_label;
    private javax.swing.JPanel login_screen;
    private javax.swing.JLabel login_username_label;
    private javax.swing.JSeparator password_seperator_username;
    private javax.swing.JSeparator password_seperator_username1;
    private javax.swing.JSeparator password_seperator_username2;
    private javax.swing.JPanel pnl_btnclose;
    private javax.swing.JPanel pnl_btnminimize;
    private javax.swing.JPanel pnl_login;
    private javax.swing.JPanel pnl_login_arrow;
    private javax.swing.JPanel pnl_progress;
    private javax.swing.JPanel pnl_progress1;
    private javax.swing.JPanel pnl_progress2;
    private javax.swing.JPanel pnl_progress3;
    private javax.swing.JPanel pnl_regis;
    private javax.swing.JPanel pnl_register;
    private javax.swing.JPanel pnl_register_detail_arrow;
    private javax.swing.JPanel pnl_register_password_arrow;
    private javax.swing.JPanel pnl_register_username_arrow;
    private javax.swing.JPanel pnl_titlebuttons;
    private javax.swing.JTextArea politika;
    private javax.swing.JPanel register_card;
    private javax.swing.JTextField register_detail_field_name;
    private javax.swing.JTextField register_detail_field_surname;
    private javax.swing.JLabel register_detail_label;
    private javax.swing.JLabel register_detail_label_check;
    private javax.swing.JPanel register_details;
    private javax.swing.JTextField register_field_username;
    private javax.swing.JPanel register_finished;
    private javax.swing.JPanel register_panel;
    private javax.swing.JPanel register_password;
    private javax.swing.JPasswordField register_password_field;
    private javax.swing.JPasswordField register_password_field_control;
    private javax.swing.JPanel register_password_input;
    private javax.swing.JPanel register_password_input1;
    private javax.swing.JLabel register_password_label;
    private javax.swing.JLabel register_password_label_check;
    private javax.swing.JPanel register_password_logo;
    private javax.swing.JPanel register_password_logo1;
    private javax.swing.JPanel register_password_logo2;
    private javax.swing.JPanel register_screen;
    private javax.swing.JSeparator register_seperator_detail;
    private javax.swing.JSeparator register_seperator_detail_check;
    private javax.swing.JSeparator register_seperator_password;
    private javax.swing.JSeparator register_seperator_password_check;
    private javax.swing.JSeparator register_seperator_username;
    private javax.swing.JPanel register_username;
    private javax.swing.JPanel register_username_input;
    private javax.swing.JLabel register_username_label;
    private javax.swing.JPanel register_username_logo;
    private javax.swing.JSeparator separator_password;
    private javax.swing.JSeparator separator_username;
    private javax.swing.JLabel title;
    private javax.swing.JPanel titlebar;
    private javax.swing.JPanel topBar;
    private javax.swing.JSeparator username_seperator_username;
    // End of variables declaration//GEN-END:variables

}

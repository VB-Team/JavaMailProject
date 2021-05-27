/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.views;

import com.vbteam.models.Command;
import com.vbteam.models.IMail;
import com.vbteam.models.SentMail;
import com.vbteam.models.User;
import static com.vbteam.views.FrmAuth.conService;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author BatuPC
 */
public class FrmDashboard1 extends javax.swing.JFrame implements MouseListener {

    private AbstractTableModel tableModel;
    static FrmDialog popupPanel;

    CardLayout mainLayout;
    boolean maxCheck;
    int selectedRow, pX, pY;
    User user;
    
    byte[] fileByteArray;
    String fileTypeString;

    List<IMail> incomeMails = new ArrayList<>();
    List<IMail> testMails = new ArrayList<>();

    public FrmDashboard1() {


        popupPanel = new FrmDialog();
        tableModel = (AbstractTableModel) new MailTableModel(incomeMails);

        initGui();
        initComponents();
        customizeTable();
        moveTitlebar();

        centerLocationAndSetSize();
        setListeners();
        
        mailgonder_label_dosyaismi.setVisible(false);

        mainLayout = (CardLayout) cardPanel.getLayout();
    }

    public void customizeTable() {

        jScrollPane2.getViewport().setBackground(new Color(30, 30, 30));
        jScrollPane2.getViewport().setForeground(new Color(30, 30, 30));

        jTable2.setForeground(new Color(255, 215, 0));
        jTable2.setOpaque(false);
        jTable2.setBorder(null);

        jTable2.setDefaultRenderer(Object.class, new TableRenderer());
    }

    public static void popupDialog(String type, String message) {
        popupPanel.setIcon(type);
        popupPanel.setMessage(message);
        popupPanel.setVisible(true);
    }

    public void sendEmail() {
        SentMail mail = new SentMail();
        if (!(mailgonder_field_baslik.getText().isEmpty() || mailgonder_field_icerik.getText().isEmpty() || mailgonder_field_kime.getText().isEmpty())) {
            mail.setSubject(mailgonder_field_baslik.getText());
            mail.setBody(mailgonder_field_icerik.getText());
            mail.setSenderUser(user.getUserName());
            mail.setRecipientUser(mailgonder_field_kime.getText());
            mail.setAttachment(fileByteArray);
            mail.setAttachmentType(fileTypeString);
            FrmAuth.conService.SendCommand(new Command("mail-send", null, user, mail));
            
            mailgonder_label_dosyaismi.setVisible(false);
            mailgonder_label_dosyaismi.setText(null);
            popupDialog("confirm", "Email gönderildi.");
        } else {
            popupDialog("error", "Boşlukları doldurunuz lütfen.");
        }
    }

    public void getMails() {
        try {
            FrmAuth.conService.SendCommand(new Command("mail-get-sent", null, user, null));
            Command _command = (Command) conService.getInputStream().readObject();

            incomeMails = _command.getMailList();
        } catch (Exception ex) {
            System.out.println("Client Get Mail Exception : " + ex.getLocalizedMessage());
        }

    }

    public void setUserDetails(User _user) {
        user = _user;
        welcomeText.setText("Hoşgeldin , " + user.getFirstName() + " " + user.getLastName());
        txt_LastTime.setText("Son Giriş Tarihi  : " + user.getLastLogin());
    }

    public MailTableModel getMailTable() {
        return (MailTableModel) tableModel;
    }

    public void moveTitlebar() {
        titlebar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2 && !evt.isConsumed()) {
                    evt.consume();
                    maximize();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                pX = e.getX();
                pY = e.getY();
            }
        });

        titlebar.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent evt) {
                setLocation(evt.getXOnScreen() - pX, evt.getYOnScreen() - pY);
            }
        });
    }

    private void centerLocationAndSetSize() {
        this.setSize(1366, 768);
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Dimension screenSize = toolkit.getScreenSize();
        final int x = (screenSize.width - this.getWidth()) / 2;
        final int y = (screenSize.height - this.getHeight()) / 2;
        this.setLocation(x, y);
    }

    private void maximize() {
        if (maxCheck) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            double width = screenSize.getWidth();
            double height = screenSize.getHeight();
            this.setLocation(0, 0);
            this.setSize((int) width, (int) height - 40);
        } else {
            centerLocationAndSetSize();
        }
        maxCheck = !maxCheck;
    }

    private void initGui() {
        this.setUndecorated(true); // title barı kaldırıyor
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        
    }

    private void setListeners() {
        btn_pnl_anasayfa.addMouseListener(this);
        btn_pnl_mail.addMouseListener(this);
        btn_pnl_ayarlar.addMouseListener(this);
        btn_mail_1.addMouseListener(this);
        btn_mail_2.addMouseListener(this);

        mailgonder_btn_gonder.addMouseListener(this);
        mailgonder_btn_dosyaekle.addMouseListener(this);

        jTable2.addMouseListener(this);

        gelenpanel.addMouseListener(this);
        gidenpanel.addMouseListener(this);

        yenimailpanel.addMouseListener(this);

    }

    private IMail getMailFromId(List<IMail> list, int id) {
        for (IMail mail : list) {
            if (mail.getId() == id) {
                return mail;
            }
        }
        return null;
    }

    private void setMailCredentials(IMail mail) {
        mail_author_text.setText(mail.getRecipientUser());
        pnl_mail_body_text.setText(mail.getBody());
    }

    private void dosyaGonder() {
        try {
            JFileChooser jfc = new JFileChooser(System.getProperty("user.dir", "."));

            jfc.removeChoosableFileFilter(jfc.getAcceptAllFileFilter());

            if (jfc.showDialog(this, "Dosya Seç") == JFileChooser.APPROVE_OPTION) {
                fileByteArray = Files.readAllBytes(Paths.get(jfc.getSelectedFile().getPath()));
                fileTypeString = jfc.getSelectedFile().getName();
                

                mailgonder_label_dosyaismi.setVisible(true);
                mailgonder_label_dosyaismi.setText(jfc.getSelectedFile().getName());
                
                repaint();
                
                /*
                FileOutputStream fos = new FileOutputStream("veyselicintumturkiye.tar.xz");
                fos.write(array);
                fos.close();
                 */
            }

        } catch (Exception ex) {
            mailgonder_label_dosyaismi.setVisible(false);
            System.out.println("File Transfer Exception : " + ex.getMessage());
        }

    }

    @Override
    public void mouseClicked(MouseEvent evt) {

        if (evt.getSource() == mailgonder_btn_dosyaekle) {
            dosyaGonder();
        }

        if (evt.getSource() == mailgonder_btn_gonder) {
            sendEmail();
        }

        if (evt.getSource() == yenimailpanel) {
            mainLayout.show(cardPanel, "mailgonder");
        }

        if (evt.getSource() == gelenpanel) {
            getMails();
            tableModel = (AbstractTableModel) new MailTableModel(incomeMails);
            jTable2.setModel(tableModel);
            mainLayout.show(cardPanel, "mail");
        }
        if (evt.getSource() == btn_mail_1) {
            if (!(selectedRow == 0)) {
                selectedRow--;
            }
            setMailCredentials(getMailFromId(getMailTable().getList(), (int) tableModel.getValueAt(selectedRow, 4)));
        }
        if (evt.getSource() == btn_mail_2) {
            if (!(selectedRow == tableModel.getRowCount() - 1)) {
                selectedRow++;
            }
            setMailCredentials(getMailFromId(getMailTable().getList(), (int) tableModel.getValueAt(selectedRow, 4)));
        }
        if (evt.getSource() == gidenpanel) {
            tableModel = (AbstractTableModel) new MailTableModel(testMails);
            jTable2.setModel(tableModel);
            mainLayout.show(cardPanel, "mail");
        }
        if (evt.getSource() == btn_pnl_anasayfa) {
            mainLayout.show(cardPanel, "anasayfa");
        }
        if (evt.getSource() == btn_pnl_ayarlar) {
            mainLayout.show(cardPanel, "ayarlar");
        }
        if (evt.getSource() == btn_pnl_mail) {
            mainLayout.show(cardPanel, "mailcategory");
            //setIncomeMails();
        }
    }

    @Override
    public void mousePressed(MouseEvent evt) {
        if (evt.getSource() == jTable2) {
            selectedRow = jTable2.getSelectedRow();
            setMailCredentials(getMailFromId(getMailTable().getList(), (int) tableModel.getValueAt(selectedRow, 4)));
        }
    }

    @Override
    public void mouseReleased(MouseEvent evt) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent evt) {
        if (evt.getSource() == mailgonder_btn_gonder || evt.getSource() == mailgonder_btn_dosyaekle || evt.getSource() == btn_pnl_anasayfa || evt.getSource() == btn_pnl_ayarlar
                || evt.getSource() == btn_pnl_mail || evt.getSource() == yenimailpanel
                || evt.getSource() == gelenpanel || evt.getSource() == gidenpanel) {
            JPanel panel = (JPanel) evt.getSource();
            panel.setOpaque(true);
            repaint();
        }
    }

    @Override
    public void mouseExited(MouseEvent evt) {
        if (evt.getSource() == mailgonder_btn_gonder || evt.getSource() == mailgonder_btn_dosyaekle || evt.getSource() == btn_pnl_anasayfa
                || evt.getSource() == btn_pnl_ayarlar || evt.getSource() == btn_pnl_mail || evt.getSource() == yenimailpanel
                || evt.getSource() == gelenpanel || evt.getSource() == gidenpanel) {
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

        topBar = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        pnl_titlebuttons = new javax.swing.JPanel();
        pnl_btnclose = new javax.swing.JPanel();
        btn_minimize = new javax.swing.JButton();
        pnl_btnminimize = new javax.swing.JPanel();
        btn_close = new javax.swing.JButton();
        pnl_btnmaximize = new javax.swing.JPanel();
        btn_maximize = new javax.swing.JButton();
        titlebar = new javax.swing.JPanel();
        title = new javax.swing.JLabel();
        cardPanel = new javax.swing.JPanel();
        pnl_anasayfa = new javax.swing.JPanel();
        pnl_mainIcon = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pnl_welcomer = new javax.swing.JPanel();
        welcomeText = new javax.swing.JLabel();
        icon_sendMail = new javax.swing.JLabel();
        txt_sendMail = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_LastTime = new javax.swing.JLabel();
        pnl_lastTime = new javax.swing.JLabel();
        btn_sendMail = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        pnl_mail_category = new javax.swing.JPanel();
        yenimailpanel = new javax.swing.JPanel();
        yenimail_text = new javax.swing.JLabel();
        yenimail_icon = new javax.swing.JLabel();
        gelenpanel = new javax.swing.JPanel();
        gelen_Text = new javax.swing.JLabel();
        gelen_icon = new javax.swing.JLabel();
        gidenpanel = new javax.swing.JPanel();
        giden_Text = new javax.swing.JLabel();
        gelen_icon1 = new javax.swing.JLabel();
        taslakpanel = new javax.swing.JPanel();
        taslak_Text = new javax.swing.JLabel();
        gelen_icon3 = new javax.swing.JLabel();
        coppanel = new javax.swing.JPanel();
        cop_Text = new javax.swing.JLabel();
        gelen_icon4 = new javax.swing.JLabel();
        pnl_mail_gonder = new javax.swing.JPanel();
        mailgonder_baslik = new javax.swing.JLabel();
        pnl_mailgonder_icerik = new javax.swing.JPanel();
        mailgonder_field_label_govde = new javax.swing.JLabel();
        mailgonder_field_label_baslik = new javax.swing.JLabel();
        mailgonder_field_baslik = new javax.swing.JTextField();
        mailgonder_field_seperator = new javax.swing.JSeparator();
        mailgonder_field_icerik_scrollpane = new javax.swing.JScrollPane();
        mailgonder_field_icerik = new javax.swing.JTextArea();
        mailgonder_btn_gonder = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        mailgonder_btn_dosyaekle = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        mailgonder_field_kime = new javax.swing.JTextField();
        mailgonder_field_label_kime = new javax.swing.JLabel();
        mailgonder_field_seperator1 = new javax.swing.JSeparator();
        mailgonder_label_dosyaismi = new javax.swing.JLabel();
        pnl_mail = new javax.swing.JPanel();
        pnl_main_mail = new javax.swing.JPanel();
        pnl_gelen_splitpane = new javax.swing.JSplitPane();
        pnl_main_details = new javax.swing.JPanel();
        pnl_mail_header = new javax.swing.JPanel();
        mail_author = new javax.swing.JPanel();
        mail_author_text = new javax.swing.JLabel();
        mail_buttons = new javax.swing.JPanel();
        btn_mail_1 = new javax.swing.JLabel();
        btn_mail_2 = new javax.swing.JLabel();
        btn_mail_3 = new javax.swing.JLabel();
        mail_time = new javax.swing.JPanel();
        mail_time_text = new javax.swing.JLabel();
        pnl_mail_body = new javax.swing.JPanel();
        mail_body_split = new javax.swing.JSplitPane();
        mail_body_message = new javax.swing.JPanel();
        pnl_mail_detail_header = new javax.swing.JPanel();
        pnl_mail_detail_header_scrollpane = new javax.swing.JScrollPane();
        pnl_mail_detail_header_text = new javax.swing.JTextArea();
        pnl_mail_detail_body = new javax.swing.JPanel();
        pnl_mail_detail_attachment = new javax.swing.JPanel();
        pnl_mail_body_scrollpane = new javax.swing.JScrollPane();
        pnl_mail_body_text = new javax.swing.JTextArea();
        mail_body_reply = new javax.swing.JPanel();
        pnl_mail_body_reply_btn = new javax.swing.JPanel();
        btn_reply = new javax.swing.JLabel();
        pnl_mail_body_reply = new javax.swing.JPanel();
        pnl_mail_body_reply_scrollpane = new javax.swing.JScrollPane();
        pnl_mail_body_reply_text = new javax.swing.JTextArea();
        pnl_main_mails = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        pnl_ayarlar = new javax.swing.JPanel();
        bottomBar = new javax.swing.JPanel();
        Profile = new javax.swing.JPanel();
        profile_icon = new javax.swing.JLabel();
        Menus = new javax.swing.JPanel();
        btn_pnl_ayarlar = new javax.swing.JPanel();
        txt_ayarlar = new javax.swing.JLabel();
        btn_pnl_anasayfa = new javax.swing.JPanel();
        txt_anasayfa = new javax.swing.JLabel();
        btn_pnl_mail = new javax.swing.JPanel();
        txt_mailkutusu = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        topBar.setBackground(new java.awt.Color(20, 20, 22));
        topBar.setPreferredSize(new java.awt.Dimension(50, 50));
        topBar.setLayout(new java.awt.BorderLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(150, 150));
        jPanel1.setLayout(new java.awt.BorderLayout());

        pnl_titlebuttons.setBackground(new java.awt.Color(43, 49, 68));
        pnl_titlebuttons.setPreferredSize(new java.awt.Dimension(200, 50));
        pnl_titlebuttons.setLayout(new java.awt.BorderLayout());

        pnl_btnclose.setBackground(new java.awt.Color(20, 20, 22));
        pnl_btnclose.setToolTipText("");
        pnl_btnclose.setPreferredSize(new java.awt.Dimension(55, 0));
        pnl_btnclose.setLayout(new java.awt.BorderLayout());

        btn_minimize.setBackground(new java.awt.Color(51, 53, 65));
        btn_minimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/dry-clean-green.png"))); // NOI18N
        btn_minimize.setBorderPainted(false);
        btn_minimize.setContentAreaFilled(false);
        btn_minimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_minimizeMouseClicked(evt);
            }
        });
        pnl_btnclose.add(btn_minimize, java.awt.BorderLayout.CENTER);

        pnl_titlebuttons.add(pnl_btnclose, java.awt.BorderLayout.LINE_START);

        pnl_btnminimize.setBackground(new java.awt.Color(20, 20, 22));
        pnl_btnminimize.setPreferredSize(new java.awt.Dimension(55, 0));
        pnl_btnminimize.setLayout(new java.awt.BorderLayout());

        btn_close.setBackground(new java.awt.Color(51, 53, 65));
        btn_close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/dry-clean.png"))); // NOI18N
        btn_close.setBorder(null);
        btn_close.setBorderPainted(false);
        btn_close.setContentAreaFilled(false);
        btn_close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_closeMouseClicked(evt);
            }
        });
        pnl_btnminimize.add(btn_close, java.awt.BorderLayout.CENTER);

        pnl_titlebuttons.add(pnl_btnminimize, java.awt.BorderLayout.LINE_END);

        pnl_btnmaximize.setBackground(new java.awt.Color(20, 20, 22));
        pnl_btnmaximize.setLayout(new java.awt.BorderLayout());

        btn_maximize.setBackground(new java.awt.Color(37, 40, 44));
        btn_maximize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/dry-clean-yellow.png"))); // NOI18N
        btn_maximize.setToolTipText("");
        btn_maximize.setBorderPainted(false);
        btn_maximize.setContentAreaFilled(false);
        btn_maximize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_maximizeMouseClicked(evt);
            }
        });
        pnl_btnmaximize.add(btn_maximize, java.awt.BorderLayout.CENTER);

        pnl_titlebuttons.add(pnl_btnmaximize, java.awt.BorderLayout.CENTER);

        jPanel1.add(pnl_titlebuttons, java.awt.BorderLayout.PAGE_START);

        topBar.add(jPanel1, java.awt.BorderLayout.LINE_END);

        titlebar.setBackground(new java.awt.Color(20, 20, 22));
        titlebar.setLayout(new java.awt.BorderLayout());

        title.setBackground(new java.awt.Color(62, 62, 63));
        title.setForeground(new java.awt.Color(62, 62, 63));
        title.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        title.setText("        VBMail");
        titlebar.add(title, java.awt.BorderLayout.CENTER);

        topBar.add(titlebar, java.awt.BorderLayout.CENTER);

        getContentPane().add(topBar, java.awt.BorderLayout.PAGE_START);

        cardPanel.setBackground(new java.awt.Color(20, 20, 22));
        cardPanel.setLayout(new java.awt.CardLayout());

        pnl_anasayfa.setBackground(new java.awt.Color(20, 20, 22));
        pnl_anasayfa.setLayout(new java.awt.BorderLayout());

        pnl_mainIcon.setOpaque(false);
        pnl_mainIcon.setLayout(new java.awt.BorderLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/mainIcon.png"))); // NOI18N
        jLabel1.setToolTipText("");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel1.setPreferredSize(new java.awt.Dimension(600, 600));
        pnl_mainIcon.add(jLabel1, java.awt.BorderLayout.CENTER);

        pnl_anasayfa.add(pnl_mainIcon, java.awt.BorderLayout.LINE_END);

        pnl_welcomer.setOpaque(false);
        pnl_welcomer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        welcomeText.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        welcomeText.setForeground(new java.awt.Color(238, 217, 217));
        welcomeText.setText("Hoşgeldin , [User]");
        pnl_welcomer.add(welcomeText, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, 580, 40));

        icon_sendMail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/mail_menu_icon.png"))); // NOI18N
        pnl_welcomer.add(icon_sendMail, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 580, -1, -1));

        txt_sendMail.setForeground(new java.awt.Color(238, 217, 217));
        txt_sendMail.setText("Mail Gönder");
        pnl_welcomer.add(txt_sendMail, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 580, 350, 20));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/clock.png"))); // NOI18N
        pnl_welcomer.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 190, 40, 40));

        txt_LastTime.setText("Son Giriş Tarihi  : ");
        pnl_welcomer.add(txt_LastTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 200, 410, 20));

        pnl_lastTime.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/rect600.png"))); // NOI18N
        pnl_lastTime.setToolTipText("");
        pnl_welcomer.add(pnl_lastTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 180, 620, 60));

        btn_sendMail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/rect600.png"))); // NOI18N
        btn_sendMail.setToolTipText("");
        pnl_welcomer.add(btn_sendMail, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 560, 620, 60));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/rect600.png"))); // NOI18N
        jLabel8.setToolTipText("");
        pnl_welcomer.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 420, 620, 60));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/rect600.png"))); // NOI18N
        jLabel9.setToolTipText("");
        pnl_welcomer.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 290, 620, 60));
        pnl_welcomer.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 440, -1, -1));

        pnl_anasayfa.add(pnl_welcomer, java.awt.BorderLayout.CENTER);

        cardPanel.add(pnl_anasayfa, "anasayfa");

        pnl_mail_category.setBackground(new java.awt.Color(20, 20, 22));
        pnl_mail_category.setLayout(new java.awt.GridLayout(1, 0));

        yenimailpanel.setBackground(new java.awt.Color(30, 29, 32));
        yenimailpanel.setOpaque(false);
        yenimailpanel.setLayout(new java.awt.BorderLayout());

        yenimail_text.setForeground(new java.awt.Color(238, 217, 217));
        yenimail_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        yenimail_text.setText("Yeni Mail");
        yenimail_text.setPreferredSize(new java.awt.Dimension(300, 300));
        yenimailpanel.add(yenimail_text, java.awt.BorderLayout.CENTER);

        yenimail_icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        yenimail_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/new-email.png"))); // NOI18N
        yenimail_icon.setPreferredSize(new java.awt.Dimension(400, 400));
        yenimailpanel.add(yenimail_icon, java.awt.BorderLayout.PAGE_START);

        pnl_mail_category.add(yenimailpanel);

        gelenpanel.setBackground(new java.awt.Color(30, 29, 32));
        gelenpanel.setOpaque(false);
        gelenpanel.setLayout(new java.awt.BorderLayout());

        gelen_Text.setForeground(new java.awt.Color(238, 217, 217));
        gelen_Text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gelen_Text.setText("Gelen Kutusu");
        gelen_Text.setPreferredSize(new java.awt.Dimension(300, 300));
        gelenpanel.add(gelen_Text, java.awt.BorderLayout.CENTER);

        gelen_icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gelen_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/mail_menu_icon.png"))); // NOI18N
        gelen_icon.setPreferredSize(new java.awt.Dimension(400, 400));
        gelenpanel.add(gelen_icon, java.awt.BorderLayout.PAGE_START);

        pnl_mail_category.add(gelenpanel);

        gidenpanel.setBackground(new java.awt.Color(20, 20, 22));
        gidenpanel.setOpaque(false);
        gidenpanel.setLayout(new java.awt.BorderLayout());

        giden_Text.setForeground(new java.awt.Color(238, 217, 217));
        giden_Text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        giden_Text.setText("Giden Kutusu");
        giden_Text.setPreferredSize(new java.awt.Dimension(300, 300));
        gidenpanel.add(giden_Text, java.awt.BorderLayout.CENTER);

        gelen_icon1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gelen_icon1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/mail_menu_icon_giden.png"))); // NOI18N
        gelen_icon1.setPreferredSize(new java.awt.Dimension(400, 400));
        gidenpanel.add(gelen_icon1, java.awt.BorderLayout.PAGE_START);

        pnl_mail_category.add(gidenpanel);

        taslakpanel.setBackground(new java.awt.Color(20, 20, 22));
        taslakpanel.setLayout(new java.awt.BorderLayout());

        taslak_Text.setForeground(new java.awt.Color(238, 217, 217));
        taslak_Text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        taslak_Text.setText("Taslak Kutusu");
        taslak_Text.setPreferredSize(new java.awt.Dimension(300, 300));
        taslakpanel.add(taslak_Text, java.awt.BorderLayout.CENTER);

        gelen_icon3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gelen_icon3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/mail_menu_icon_taslak.png"))); // NOI18N
        gelen_icon3.setPreferredSize(new java.awt.Dimension(400, 400));
        taslakpanel.add(gelen_icon3, java.awt.BorderLayout.PAGE_START);

        pnl_mail_category.add(taslakpanel);

        coppanel.setBackground(new java.awt.Color(20, 20, 22));
        coppanel.setLayout(new java.awt.BorderLayout());

        cop_Text.setForeground(new java.awt.Color(238, 217, 217));
        cop_Text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cop_Text.setText("Çöp Kutusu");
        cop_Text.setPreferredSize(new java.awt.Dimension(300, 300));
        coppanel.add(cop_Text, java.awt.BorderLayout.CENTER);

        gelen_icon4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gelen_icon4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/mail_menu_icon_cop.png"))); // NOI18N
        gelen_icon4.setPreferredSize(new java.awt.Dimension(400, 400));
        coppanel.add(gelen_icon4, java.awt.BorderLayout.PAGE_START);

        pnl_mail_category.add(coppanel);

        cardPanel.add(pnl_mail_category, "mailcategory");

        pnl_mail_gonder.setBackground(new java.awt.Color(20, 20, 22));
        pnl_mail_gonder.setLayout(new java.awt.BorderLayout());

        mailgonder_baslik.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        mailgonder_baslik.setForeground(new java.awt.Color(238, 217, 217));
        mailgonder_baslik.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mailgonder_baslik.setText("Mail Gönder");
        pnl_mail_gonder.add(mailgonder_baslik, java.awt.BorderLayout.PAGE_START);

        pnl_mailgonder_icerik.setBackground(new java.awt.Color(20, 20, 22));
        pnl_mailgonder_icerik.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        mailgonder_field_label_govde.setForeground(new java.awt.Color(238, 217, 217));
        mailgonder_field_label_govde.setText("İçerik");
        pnl_mailgonder_icerik.add(mailgonder_field_label_govde, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 200, -1, -1));

        mailgonder_field_label_baslik.setForeground(new java.awt.Color(238, 217, 217));
        mailgonder_field_label_baslik.setText("Başlık");
        pnl_mailgonder_icerik.add(mailgonder_field_label_baslik, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 120, -1, -1));

        mailgonder_field_baslik.setBackground(new java.awt.Color(20, 20, 22));
        mailgonder_field_baslik.setForeground(new java.awt.Color(238, 217, 217));
        mailgonder_field_baslik.setBorder(null);
        pnl_mailgonder_icerik.add(mailgonder_field_baslik, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 140, 820, 40));

        mailgonder_field_seperator.setBackground(new java.awt.Color(25, 25, 28));
        mailgonder_field_seperator.setForeground(new java.awt.Color(25, 25, 28));
        pnl_mailgonder_icerik.add(mailgonder_field_seperator, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 110, 820, 40));

        mailgonder_field_icerik.setBackground(new java.awt.Color(20, 20, 22));
        mailgonder_field_icerik.setColumns(20);
        mailgonder_field_icerik.setForeground(new java.awt.Color(238, 217, 217));
        mailgonder_field_icerik.setRows(5);
        mailgonder_field_icerik_scrollpane.setViewportView(mailgonder_field_icerik);

        pnl_mailgonder_icerik.add(mailgonder_field_icerik_scrollpane, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 230, 820, 230));

        mailgonder_btn_gonder.setBackground(new java.awt.Color(30, 29, 32));
        mailgonder_btn_gonder.setOpaque(false);
        mailgonder_btn_gonder.setLayout(new java.awt.BorderLayout());

        jLabel3.setForeground(new java.awt.Color(238, 217, 217));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Gönder");
        mailgonder_btn_gonder.add(jLabel3, java.awt.BorderLayout.CENTER);

        pnl_mailgonder_icerik.add(mailgonder_btn_gonder, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 480, 110, 40));

        mailgonder_btn_dosyaekle.setBackground(new java.awt.Color(30, 29, 32));
        mailgonder_btn_dosyaekle.setOpaque(false);
        mailgonder_btn_dosyaekle.setLayout(new java.awt.BorderLayout());

        jLabel4.setBackground(new java.awt.Color(30, 29, 32));
        jLabel4.setForeground(new java.awt.Color(238, 217, 217));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Dosya Ekle");
        mailgonder_btn_dosyaekle.add(jLabel4, java.awt.BorderLayout.CENTER);

        pnl_mailgonder_icerik.add(mailgonder_btn_dosyaekle, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 480, 110, 40));

        mailgonder_field_kime.setBackground(new java.awt.Color(20, 20, 22));
        mailgonder_field_kime.setForeground(new java.awt.Color(238, 217, 217));
        mailgonder_field_kime.setBorder(null);
        pnl_mailgonder_icerik.add(mailgonder_field_kime, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 70, 820, 40));

        mailgonder_field_label_kime.setForeground(new java.awt.Color(238, 217, 217));
        mailgonder_field_label_kime.setText("Kime");
        pnl_mailgonder_icerik.add(mailgonder_field_label_kime, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 50, -1, -1));

        mailgonder_field_seperator1.setBackground(new java.awt.Color(25, 25, 28));
        mailgonder_field_seperator1.setForeground(new java.awt.Color(25, 25, 28));
        pnl_mailgonder_icerik.add(mailgonder_field_seperator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 180, 820, 40));

        mailgonder_label_dosyaismi.setBackground(new java.awt.Color(255, 0, 0));
        mailgonder_label_dosyaismi.setForeground(new java.awt.Color(255, 0, 0));
        mailgonder_label_dosyaismi.setText("Dosya ismi : ");
        pnl_mailgonder_icerik.add(mailgonder_label_dosyaismi, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 490, 520, -1));

        pnl_mail_gonder.add(pnl_mailgonder_icerik, java.awt.BorderLayout.CENTER);

        cardPanel.add(pnl_mail_gonder, "mailgonder");

        pnl_mail.setBackground(new java.awt.Color(20, 20, 22));
        pnl_mail.setLayout(new java.awt.BorderLayout());

        pnl_main_mail.setBackground(new java.awt.Color(37, 40, 44));
        pnl_main_mail.setLayout(new java.awt.CardLayout());

        pnl_gelen_splitpane.setBackground(new java.awt.Color(20, 20, 22));
        pnl_gelen_splitpane.setDividerLocation(700);
        pnl_gelen_splitpane.setDividerSize(3);
        pnl_gelen_splitpane.setForeground(new java.awt.Color(37, 40, 44));
        pnl_gelen_splitpane.setPreferredSize(new java.awt.Dimension(1250, 700));

        pnl_main_details.setBackground(new java.awt.Color(20, 20, 22));
        pnl_main_details.setLayout(new java.awt.BorderLayout());

        pnl_mail_header.setBackground(new java.awt.Color(20, 20, 22));
        pnl_mail_header.setPreferredSize(new java.awt.Dimension(574, 60));
        pnl_mail_header.setLayout(new java.awt.BorderLayout());

        mail_author.setOpaque(false);
        mail_author.setLayout(new java.awt.BorderLayout());

        mail_author_text.setBackground(new java.awt.Color(45, 48, 53));
        mail_author_text.setForeground(new java.awt.Color(254, 254, 254));
        mail_author_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mail_author_text.setText("[Author]");
        mail_author_text.setPreferredSize(new java.awt.Dimension(89, 15));
        mail_author.add(mail_author_text, java.awt.BorderLayout.CENTER);

        pnl_mail_header.add(mail_author, java.awt.BorderLayout.LINE_START);

        mail_buttons.setOpaque(false);
        mail_buttons.setLayout(new java.awt.BorderLayout());

        btn_mail_1.setBackground(new java.awt.Color(45, 48, 53));
        btn_mail_1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_mail_1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/left-arrow.png"))); // NOI18N
        btn_mail_1.setPreferredSize(new java.awt.Dimension(40, 15));
        mail_buttons.add(btn_mail_1, java.awt.BorderLayout.LINE_START);

        btn_mail_2.setBackground(new java.awt.Color(45, 48, 53));
        btn_mail_2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_mail_2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/right-arrow.png"))); // NOI18N
        btn_mail_2.setPreferredSize(new java.awt.Dimension(40, 15));
        mail_buttons.add(btn_mail_2, java.awt.BorderLayout.LINE_END);

        btn_mail_3.setBackground(new java.awt.Color(45, 48, 53));
        btn_mail_3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_mail_3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/mail_menu_icon_cop.png"))); // NOI18N
        btn_mail_3.setPreferredSize(new java.awt.Dimension(40, 15));
        mail_buttons.add(btn_mail_3, java.awt.BorderLayout.CENTER);

        pnl_mail_header.add(mail_buttons, java.awt.BorderLayout.LINE_END);

        mail_time.setBackground(new java.awt.Color(20, 20, 22));
        mail_time.setLayout(new java.awt.BorderLayout());

        mail_time_text.setBackground(new java.awt.Color(20, 20, 22));
        mail_time_text.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        mail_time_text.setText("[Time]");
        mail_time.add(mail_time_text, java.awt.BorderLayout.CENTER);

        pnl_mail_header.add(mail_time, java.awt.BorderLayout.CENTER);

        pnl_main_details.add(pnl_mail_header, java.awt.BorderLayout.PAGE_START);

        pnl_mail_body.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_body.setPreferredSize(new java.awt.Dimension(100, 100));
        pnl_mail_body.setLayout(new java.awt.BorderLayout());

        mail_body_split.setBackground(new java.awt.Color(45, 48, 53));
        mail_body_split.setDividerLocation(595);
        mail_body_split.setDividerSize(3);
        mail_body_split.setForeground(new java.awt.Color(45, 48, 53));
        mail_body_split.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        mail_body_message.setBackground(new java.awt.Color(45, 48, 53));
        mail_body_message.setForeground(new java.awt.Color(45, 48, 53));
        mail_body_message.setLayout(new java.awt.BorderLayout());

        pnl_mail_detail_header.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_detail_header.setPreferredSize(new java.awt.Dimension(60, 60));
        pnl_mail_detail_header.setLayout(new java.awt.BorderLayout());

        pnl_mail_detail_header_scrollpane.setBorder(null);
        pnl_mail_detail_header_scrollpane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        pnl_mail_detail_header_scrollpane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        pnl_mail_detail_header_text.setEditable(false);
        pnl_mail_detail_header_text.setBackground(new java.awt.Color(20, 20, 22));
        pnl_mail_detail_header_text.setColumns(5);
        pnl_mail_detail_header_text.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        pnl_mail_detail_header_text.setLineWrap(true);
        pnl_mail_detail_header_text.setRows(2);
        pnl_mail_detail_header_text.setTabSize(2);
        pnl_mail_detail_header_text.setWrapStyleWord(true);
        pnl_mail_detail_header_text.setCaretColor(new java.awt.Color(255, 255, 255));
        pnl_mail_detail_header_text.setOpaque(false);
        pnl_mail_detail_header_scrollpane.setViewportView(pnl_mail_detail_header_text);

        pnl_mail_detail_header.add(pnl_mail_detail_header_scrollpane, java.awt.BorderLayout.CENTER);

        mail_body_message.add(pnl_mail_detail_header, java.awt.BorderLayout.PAGE_START);

        pnl_mail_detail_body.setBackground(new java.awt.Color(20, 20, 22));
        pnl_mail_detail_body.setPreferredSize(new java.awt.Dimension(50, 100));
        pnl_mail_detail_body.setLayout(new java.awt.BorderLayout());

        pnl_mail_detail_attachment.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3), "Email Eki", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), new java.awt.Color(165, 165, 172))); // NOI18N
        pnl_mail_detail_attachment.setOpaque(false);
        pnl_mail_detail_attachment.setPreferredSize(new java.awt.Dimension(60, 60));
        pnl_mail_detail_attachment.setLayout(new java.awt.BorderLayout());
        pnl_mail_detail_body.add(pnl_mail_detail_attachment, java.awt.BorderLayout.PAGE_END);

        pnl_mail_body_scrollpane.setBackground(new java.awt.Color(20, 20, 22));
        pnl_mail_body_scrollpane.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2));
        pnl_mail_body_scrollpane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        pnl_mail_body_scrollpane.setOpaque(false);
        pnl_mail_body_scrollpane.setPreferredSize(new java.awt.Dimension(40, 40));

        pnl_mail_body_text.setEditable(false);
        pnl_mail_body_text.setBackground(new java.awt.Color(20, 20, 22));
        pnl_mail_body_text.setColumns(5);
        pnl_mail_body_text.setForeground(new java.awt.Color(165, 165, 172));
        pnl_mail_body_text.setLineWrap(true);
        pnl_mail_body_text.setRows(2);
        pnl_mail_body_text.setTabSize(2);
        pnl_mail_body_text.setWrapStyleWord(true);
        pnl_mail_body_text.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2));
        pnl_mail_body_text.setOpaque(false);
        pnl_mail_body_text.setPreferredSize(new java.awt.Dimension(90, 90));
        pnl_mail_body_scrollpane.setViewportView(pnl_mail_body_text);

        pnl_mail_detail_body.add(pnl_mail_body_scrollpane, java.awt.BorderLayout.CENTER);

        mail_body_message.add(pnl_mail_detail_body, java.awt.BorderLayout.CENTER);

        mail_body_split.setTopComponent(mail_body_message);

        mail_body_reply.setBackground(new java.awt.Color(45, 48, 53));
        mail_body_reply.setForeground(new java.awt.Color(45, 48, 53));
        mail_body_reply.setPreferredSize(new java.awt.Dimension(100, 100));
        mail_body_reply.setLayout(new java.awt.BorderLayout());

        pnl_mail_body_reply_btn.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_body_reply_btn.setPreferredSize(new java.awt.Dimension(40, 40));
        pnl_mail_body_reply_btn.setLayout(new java.awt.BorderLayout());

        btn_reply.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_reply.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/send.png"))); // NOI18N
        btn_reply.setPreferredSize(new java.awt.Dimension(30, 30));
        pnl_mail_body_reply_btn.add(btn_reply, java.awt.BorderLayout.CENTER);

        mail_body_reply.add(pnl_mail_body_reply_btn, java.awt.BorderLayout.LINE_END);

        pnl_mail_body_reply.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_body_reply.setForeground(new java.awt.Color(12, 12, 12));
        pnl_mail_body_reply.setPreferredSize(new java.awt.Dimension(300, 124));
        pnl_mail_body_reply.setLayout(new java.awt.BorderLayout());

        pnl_mail_body_reply_scrollpane.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2));
        pnl_mail_body_reply_scrollpane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        pnl_mail_body_reply_scrollpane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        pnl_mail_body_reply_scrollpane.setAutoscrolls(true);

        pnl_mail_body_reply_text.setBackground(new java.awt.Color(44, 48, 53));
        pnl_mail_body_reply_text.setColumns(50);
        pnl_mail_body_reply_text.setFont(new java.awt.Font("Bahnschrift", 0, 11)); // NOI18N
        pnl_mail_body_reply_text.setRows(5);
        pnl_mail_body_reply_text.setText("Cevap göndermek için tıklayın.\n");
        pnl_mail_body_reply_text.setToolTipText("");
        pnl_mail_body_reply_text.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        pnl_mail_body_reply_text.setCaretColor(new java.awt.Color(255, 255, 255));
        pnl_mail_body_reply_text.setMinimumSize(new java.awt.Dimension(1, 1));
        pnl_mail_body_reply_text.setSelectionColor(new java.awt.Color(255, 255, 255));
        pnl_mail_body_reply_scrollpane.setViewportView(pnl_mail_body_reply_text);

        pnl_mail_body_reply.add(pnl_mail_body_reply_scrollpane, java.awt.BorderLayout.CENTER);

        mail_body_reply.add(pnl_mail_body_reply, java.awt.BorderLayout.CENTER);

        mail_body_split.setRightComponent(mail_body_reply);

        pnl_mail_body.add(mail_body_split, java.awt.BorderLayout.CENTER);

        pnl_main_details.add(pnl_mail_body, java.awt.BorderLayout.CENTER);

        pnl_gelen_splitpane.setRightComponent(pnl_main_details);

        pnl_main_mails.setBackground(new java.awt.Color(45, 48, 53));
        pnl_main_mails.setOpaque(false);
        pnl_main_mails.setPreferredSize(new java.awt.Dimension(700, 750));
        pnl_main_mails.setLayout(new java.awt.BorderLayout());

        jTable2.setModel(tableModel);
        jTable2.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTable2);

        pnl_main_mails.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        pnl_gelen_splitpane.setLeftComponent(pnl_main_mails);

        pnl_main_mail.add(pnl_gelen_splitpane, "gelencard");

        pnl_mail.add(pnl_main_mail, java.awt.BorderLayout.CENTER);

        cardPanel.add(pnl_mail, "mail");

        pnl_ayarlar.setBackground(new java.awt.Color(20, 20, 22));
        pnl_ayarlar.setLayout(new java.awt.BorderLayout());
        cardPanel.add(pnl_ayarlar, "ayarlar");

        getContentPane().add(cardPanel, java.awt.BorderLayout.CENTER);

        bottomBar.setBackground(new java.awt.Color(0, 0, 0));
        bottomBar.setForeground(new java.awt.Color(0, 0, 0));
        bottomBar.setPreferredSize(new java.awt.Dimension(1366, 60));
        bottomBar.setLayout(new java.awt.BorderLayout());

        Profile.setBackground(new java.awt.Color(43, 152, 123));
        Profile.setLayout(new java.awt.BorderLayout());

        profile_icon.setBackground(new java.awt.Color(43, 152, 123));
        profile_icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        profile_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/profile.png"))); // NOI18N
        profile_icon.setPreferredSize(new java.awt.Dimension(100, 100));
        Profile.add(profile_icon, java.awt.BorderLayout.CENTER);

        bottomBar.add(Profile, java.awt.BorderLayout.LINE_END);

        Menus.setOpaque(false);
        Menus.setLayout(new java.awt.BorderLayout());

        btn_pnl_ayarlar.setBackground(new java.awt.Color(30, 29, 32));
        btn_pnl_ayarlar.setOpaque(false);
        btn_pnl_ayarlar.setLayout(new java.awt.BorderLayout());

        txt_ayarlar.setForeground(new java.awt.Color(219, 218, 220));
        txt_ayarlar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_ayarlar.setText("Ayarlar");
        txt_ayarlar.setPreferredSize(new java.awt.Dimension(400, 400));
        btn_pnl_ayarlar.add(txt_ayarlar, java.awt.BorderLayout.CENTER);

        Menus.add(btn_pnl_ayarlar, java.awt.BorderLayout.LINE_END);

        btn_pnl_anasayfa.setBackground(new java.awt.Color(30, 29, 32));
        btn_pnl_anasayfa.setOpaque(false);
        btn_pnl_anasayfa.setLayout(new java.awt.BorderLayout());

        txt_anasayfa.setBackground(new java.awt.Color(1, 0, 1));
        txt_anasayfa.setForeground(new java.awt.Color(219, 218, 220));
        txt_anasayfa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_anasayfa.setText("Anasayfa");
        txt_anasayfa.setPreferredSize(new java.awt.Dimension(400, 400));
        btn_pnl_anasayfa.add(txt_anasayfa, java.awt.BorderLayout.CENTER);

        Menus.add(btn_pnl_anasayfa, java.awt.BorderLayout.LINE_START);

        btn_pnl_mail.setBackground(new java.awt.Color(30, 29, 32));
        btn_pnl_mail.setOpaque(false);
        btn_pnl_mail.setLayout(new java.awt.BorderLayout());

        txt_mailkutusu.setBackground(new java.awt.Color(1, 0, 1));
        txt_mailkutusu.setForeground(new java.awt.Color(219, 218, 220));
        txt_mailkutusu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_mailkutusu.setText("Mail Kutusu");
        btn_pnl_mail.add(txt_mailkutusu, java.awt.BorderLayout.CENTER);

        Menus.add(btn_pnl_mail, java.awt.BorderLayout.CENTER);

        bottomBar.add(Menus, java.awt.BorderLayout.CENTER);

        getContentPane().add(bottomBar, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_closeMouseClicked
        System.exit(0);
    }//GEN-LAST:event_btn_closeMouseClicked

    private void btn_minimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_minimizeMouseClicked
        this.setState(ICONIFIED);
    }//GEN-LAST:event_btn_minimizeMouseClicked

    private void btn_maximizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_maximizeMouseClicked
        maximize();
    }//GEN-LAST:event_btn_maximizeMouseClicked

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
            java.util.logging.Logger.getLogger(FrmDashboard1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmDashboard1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmDashboard1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmDashboard1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmDashboard1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Menus;
    private javax.swing.JPanel Profile;
    private javax.swing.JPanel bottomBar;
    private javax.swing.JButton btn_close;
    private javax.swing.JLabel btn_mail_1;
    private javax.swing.JLabel btn_mail_2;
    private javax.swing.JLabel btn_mail_3;
    private javax.swing.JButton btn_maximize;
    private javax.swing.JButton btn_minimize;
    private javax.swing.JPanel btn_pnl_anasayfa;
    private javax.swing.JPanel btn_pnl_ayarlar;
    private javax.swing.JPanel btn_pnl_mail;
    private javax.swing.JLabel btn_reply;
    private javax.swing.JLabel btn_sendMail;
    private javax.swing.JPanel cardPanel;
    private javax.swing.JLabel cop_Text;
    private javax.swing.JPanel coppanel;
    private javax.swing.JLabel gelen_Text;
    private javax.swing.JLabel gelen_icon;
    private javax.swing.JLabel gelen_icon1;
    private javax.swing.JLabel gelen_icon3;
    private javax.swing.JLabel gelen_icon4;
    private javax.swing.JPanel gelenpanel;
    private javax.swing.JLabel giden_Text;
    private javax.swing.JPanel gidenpanel;
    private javax.swing.JLabel icon_sendMail;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JPanel mail_author;
    private javax.swing.JLabel mail_author_text;
    private javax.swing.JPanel mail_body_message;
    private javax.swing.JPanel mail_body_reply;
    private javax.swing.JSplitPane mail_body_split;
    private javax.swing.JPanel mail_buttons;
    private javax.swing.JPanel mail_time;
    private javax.swing.JLabel mail_time_text;
    private javax.swing.JLabel mailgonder_baslik;
    private javax.swing.JPanel mailgonder_btn_dosyaekle;
    private javax.swing.JPanel mailgonder_btn_gonder;
    private javax.swing.JTextField mailgonder_field_baslik;
    private javax.swing.JTextArea mailgonder_field_icerik;
    private javax.swing.JScrollPane mailgonder_field_icerik_scrollpane;
    private javax.swing.JTextField mailgonder_field_kime;
    private javax.swing.JLabel mailgonder_field_label_baslik;
    private javax.swing.JLabel mailgonder_field_label_govde;
    private javax.swing.JLabel mailgonder_field_label_kime;
    private javax.swing.JSeparator mailgonder_field_seperator;
    private javax.swing.JSeparator mailgonder_field_seperator1;
    private javax.swing.JLabel mailgonder_label_dosyaismi;
    private javax.swing.JPanel pnl_anasayfa;
    private javax.swing.JPanel pnl_ayarlar;
    private javax.swing.JPanel pnl_btnclose;
    private javax.swing.JPanel pnl_btnmaximize;
    private javax.swing.JPanel pnl_btnminimize;
    private javax.swing.JSplitPane pnl_gelen_splitpane;
    private javax.swing.JLabel pnl_lastTime;
    private javax.swing.JPanel pnl_mail;
    private javax.swing.JPanel pnl_mail_body;
    private javax.swing.JPanel pnl_mail_body_reply;
    private javax.swing.JPanel pnl_mail_body_reply_btn;
    private javax.swing.JScrollPane pnl_mail_body_reply_scrollpane;
    private javax.swing.JTextArea pnl_mail_body_reply_text;
    private javax.swing.JScrollPane pnl_mail_body_scrollpane;
    private javax.swing.JTextArea pnl_mail_body_text;
    private javax.swing.JPanel pnl_mail_category;
    private javax.swing.JPanel pnl_mail_detail_attachment;
    private javax.swing.JPanel pnl_mail_detail_body;
    private javax.swing.JPanel pnl_mail_detail_header;
    private javax.swing.JScrollPane pnl_mail_detail_header_scrollpane;
    private javax.swing.JTextArea pnl_mail_detail_header_text;
    private javax.swing.JPanel pnl_mail_gonder;
    private javax.swing.JPanel pnl_mail_header;
    private javax.swing.JPanel pnl_mailgonder_icerik;
    private javax.swing.JPanel pnl_mainIcon;
    private javax.swing.JPanel pnl_main_details;
    private javax.swing.JPanel pnl_main_mail;
    private javax.swing.JPanel pnl_main_mails;
    private javax.swing.JPanel pnl_titlebuttons;
    private javax.swing.JPanel pnl_welcomer;
    private javax.swing.JLabel profile_icon;
    private javax.swing.JLabel taslak_Text;
    private javax.swing.JPanel taslakpanel;
    private javax.swing.JLabel title;
    private javax.swing.JPanel titlebar;
    private javax.swing.JPanel topBar;
    private javax.swing.JLabel txt_LastTime;
    private javax.swing.JLabel txt_anasayfa;
    private javax.swing.JLabel txt_ayarlar;
    private javax.swing.JLabel txt_mailkutusu;
    private javax.swing.JLabel txt_sendMail;
    private javax.swing.JLabel welcomeText;
    private javax.swing.JLabel yenimail_icon;
    private javax.swing.JLabel yenimail_text;
    private javax.swing.JPanel yenimailpanel;
    // End of variables declaration//GEN-END:variables

}

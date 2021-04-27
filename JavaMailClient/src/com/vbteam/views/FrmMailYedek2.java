/**
 * TODO :
 *
 * Mail menü butonlarını buttondan labele geçir.
 * Resize Adapter bellek kullanımını azalt veya geçici çözüm bul
 * CardPaneye geçir menüleri.
 *
 */
package com.vbteam.views;

import com.vbteam.models.Mail;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author BatuPC
 */
public class FrmMailYedek2 extends javax.swing.JFrame implements MouseListener, MouseMotionListener {

    boolean maxCheck = true, gelenState = false, gidenState = false, taslakState = false, copState = false;
    private int mouseX1, mouseX2, mouseY1, mouseY2, pX, pY, pageNumber = 1;
    ArrayList<JPanel> mailPanelList = new ArrayList<>();
    private List<Mail> mailList = new ArrayList<Mail>();

    public FrmMailYedek2() {
        initGui();
        initComponents();
        centerLocationAndSetSize();

        pnl_mail_detail_header_text.setText("Mail sistemi hakkında , Aenean euismod maximus pharetra. Nunc a urna commodo, egestas sem ac");
        pnl_mail_body_text.setText("Aenean euismod maximus pharetra. Nunc a urna commodo, egestas sem ac, condimentum erat. Sed facilisis ipsum in ipsum vehicula, eu blandit dui porta. Etiam diam justo, facilisis id vestibulum ac, rhoncus vitae magna. Cras bibendum condimentum mollis. Praesent dui erat, semper sed convallis ut, imperdiet at mi. Suspendisse nec leo efficitur tellus rutrum rhoncus vel ac risus. Vestibulum ultricies euismod nisi, eu pulvinar ligula pretium in. Nunc fringilla nibh vel ex egestas consequat.\n"
                + "\n"
                + "Vivamus consectetur pretium felis et viverra. Suspendisse sagittis ultricies malesuada. Praesent fermentum metus sit amet massa vulputate, sit amet vehicula metus efficitur. Aenean vulputate nulla leo, a consectetur enim venenatis vel. Quisque a nibh non lectus sodales dictum in sit amet elit. Nam sapien felis, viverra ac eros et, malesuada tempus nulla. Suspendisse potenti. Mauris at sapien nec erat rhoncus sodales. Phasellus elementum tempus ultrices. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Fusce vel elit mauris. Etiam sit amet erat consectetur, feugiat sapien nec, convallis sem. Quisque felis elit, viverra id convallis et, interdum sed mauris.");
        moveTitlebar();
        setMouseListeners();
        mailList.add(new Mail("Batuhan", "12 Temmuz", "Test maili"));
        mailList.add(new Mail("Veysel", "12 Temmuz", "Test maili"));
        mailList.add(new Mail("Mahmut", "12 Temmuz", "Nunc a urna commodo"));
        mailList.add(new Mail("Selami", "12 Temmuz", "uspendisse sagittis ultricies malesuada"));
        mailList.add(new Mail("Emre", "12 Temmuz", "Test maili"));
        mailList.add(new Mail("Mahmut", "12 Temmuz", "Test maili"));
        mailList.add(new Mail("Selami", "12 Temmuz", "Test maili"));
        mailList.add(new Mail("Emre", "12 Temmuz", "Test maili"));

        setMailPanels();
    }

    private void setMouseListeners() {
        //Buttons
        btn_close.addMouseListener(this);
        btn_minimize.addMouseListener(this);
        btn_maximize.addMouseListener(this);

        //Panels
        pnl_sidebar_gelen.addMouseListener(this);
        pnl_sidebar_giden.addMouseListener(this);
        pnl_sidebar_cop.addMouseListener(this);
        pnl_sidebar_taslak.addMouseListener(this);

        //Slider Panels
        pnl_slider_right_arrow.addMouseListener(this);
        pnl_slider_left_arrow.addMouseListener(this);

        /**
         * Resize bars
         *
         * resizeBarUP.addMouseListener(this);
         * resizeBarDown.addMouseListener(this);
         *
         * WindowResizeAdapter.install(resizeBarUP, SwingConstants.NORTH);
         * WindowResizeAdapter.install(resizeBarDown, SwingConstants.SOUTH);
         */
    }

    /**
     * Mouse Active Effect Hem exit hem pressed çalıştığı için çalışmıyor FIXLE!
     *
     * @param panel
     */
    private void menuButtonActiveEffect(JPanel panel) {
        Component[] menuPanels = pnl_sidebar_menus.getComponents();
        for (Component menuPanel : menuPanels) {
            if (menuPanel == panel) {
                hoverColor(menuPanel, new Color(45, 48, 53));
                setMenuButtonState(menuPanel.getName(), true);
            } else {
                hoverColor(menuPanel, new Color(37, 40, 44));
                setMenuButtonState(menuPanel.getName(), false);
            }
        }
    }

    private void setMenuButtonState(String panelName, Boolean control) {
        switch (panelName) {
            case "gelenKutu":
                gelenState = control;
                break;
            case "gidenKutu":
                gidenState = control;
                break;
            case "taslakKutu":
                taslakState = control;
                break;
            case "copKutu":
                copState = control;
                break;
        }
    }

    private Boolean getMenuButtonState(JPanel panel) {
        Boolean menuButtonState = false;
        switch (panel.getName()) {
            case "gelenKutu":
                menuButtonState = gelenState;
                break;
            case "gidenKutu":
                menuButtonState = gidenState;
                break;
            case "taslakKutu":
                menuButtonState = taslakState;
                break;
            case "copKutu":
                menuButtonState = copState;
                break;
        }
        return menuButtonState;
    }

    private void setMailPanels() {
        Component myComps[] = pnl_mail_list.getComponents();
        for (int i = 0; i < mailList.size(); i++) {
            JPanel panel = (JPanel) myComps[i];
            panel.addMouseListener(this); // Paneller arası gezinirken mouse eventlerini kullanabilmek için mouse eventleri ekleniyor
            panel.setName("mail_list" + i);
            Component panelComp[] = panel.getComponents();
            for (int y = 0; y < panelComp.length; y++) {
                JPanel subPanel = (JPanel) panelComp[y];
                Component mailPanelComp[] = subPanel.getComponents();
                for (int z = 0; z < mailPanelComp.length; z++) {
                    try {
                        if (mailPanelComp[z].getAccessibleContext().getAccessibleName().equals("[Author]")) {
                            JLabel authorLabel = (JLabel) mailPanelComp[z];
                            authorLabel.setText(mailList.get(i).getAuthor());
                        }
                        if (mailPanelComp[z].getAccessibleContext().getAccessibleName().equals("[Header.Message]")) {
                            JLabel headerLabel = (JLabel) mailPanelComp[z];
                            headerLabel.setText(mailList.get(i).getHeaderMessage());
                        }
                        if (mailPanelComp[z].getAccessibleContext().getAccessibleName().equals("[Time]")) {
                            JLabel timeLabel = (JLabel) mailPanelComp[z];
                            timeLabel.setText(mailList.get(i).getTime());
                        }
                    } catch (IndexOutOfBoundsException ex) {
                        System.out.println(ex.getMessage());
                    }
                }

            }
        }
        for (int i = mailList.size(); i < 10; i++) {
            pnl_mail_list.remove((JPanel) myComps[i]);
        }
    }

    public void pushMailsToList() {
        //  ArrayList
    }

    private void centerLocationAndSetSize() {
        this.setSize(1366, 768);
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Dimension screenSize = toolkit.getScreenSize();
        final int x = (screenSize.width - this.getWidth()) / 2;
        final int y = (screenSize.height - this.getHeight()) / 2;
        this.setLocation(x, y);
    }

    private void initGui() {
        this.setUndecorated(true); // title barı kaldırıyor
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    public void moveTitlebar() {
        pnl_main_titlebar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2 && !evt.isConsumed()) {
                    evt.consume();
                    maximize();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                pX = e.getX() + 160;
                pY = e.getY();
            }
        });

        pnl_main_titlebar.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent evt) {
                setLocation(evt.getXOnScreen() - pX, evt.getYOnScreen() - pY);

            }
        });
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_sidebar = new javax.swing.JPanel();
        pnl_titlebuttons = new javax.swing.JPanel();
        pnl_btnclose = new javax.swing.JPanel();
        btn_close = new javax.swing.JButton();
        pnl_btnminimize = new javax.swing.JPanel();
        btn_minimize = new javax.swing.JButton();
        pnl_btnmaximize = new javax.swing.JPanel();
        btn_maximize = new javax.swing.JButton();
        pnl_email_text = new javax.swing.JPanel();
        email_text = new javax.swing.JLabel();
        pnl_sidebar_menus = new javax.swing.JPanel();
        pnl_sidebar_text = new javax.swing.JPanel();
        btn_yeni_mail = new javax.swing.JButton();
        pnl_sidebar_gelen = new javax.swing.JPanel();
        pnl_gelen_text = new javax.swing.JPanel();
        gelen_text = new javax.swing.JLabel();
        pnl_gelen_icon = new javax.swing.JPanel();
        gelen_icon = new javax.swing.JLabel();
        pnl_sidebar_cop = new javax.swing.JPanel();
        pnl_cop_text = new javax.swing.JPanel();
        cop_text = new javax.swing.JLabel();
        pnl_cop_icon = new javax.swing.JPanel();
        cop_icon = new javax.swing.JLabel();
        pnl_sidebar_taslak = new javax.swing.JPanel();
        pnl_taslak_text = new javax.swing.JPanel();
        taslak_text = new javax.swing.JLabel();
        pnl_taslak_icon = new javax.swing.JPanel();
        taslak_icon = new javax.swing.JLabel();
        pnl_sidebar_giden = new javax.swing.JPanel();
        pnl_giden_text = new javax.swing.JPanel();
        giden_text = new javax.swing.JLabel();
        pnl_giden_icon = new javax.swing.JPanel();
        giden_icon = new javax.swing.JLabel();
        pnl_main = new javax.swing.JPanel();
        pnl_main_titlebar = new javax.swing.JPanel();
        pnl_main_mail = new javax.swing.JPanel();
        pnl_gelen_splitpane = new javax.swing.JSplitPane();
        pnl_main_mails = new javax.swing.JPanel();
        pnl_mail_slider = new javax.swing.JPanel();
        pnl_slider_left = new javax.swing.JPanel();
        pnl_slider_left_arrow = new javax.swing.JLabel();
        pnl_slider_right = new javax.swing.JPanel();
        pnl_slider_right_arrow = new javax.swing.JLabel();
        pnl_slider_middle = new javax.swing.JPanel();
        lbl_slider_number = new javax.swing.JLabel();
        pnl_mail_list = new javax.swing.JPanel();
        pnl_mail_1 = new javax.swing.JPanel();
        pnl_mail_1_author = new javax.swing.JPanel();
        pnl_mail_1_author_text = new javax.swing.JLabel();
        pnl_mail_1_time = new javax.swing.JPanel();
        pnl_mail_1_time_text = new javax.swing.JLabel();
        pnl_mail_1_message = new javax.swing.JPanel();
        pnl_mail_1_message_text = new javax.swing.JLabel();
        pnl_mail_2 = new javax.swing.JPanel();
        pnl_mail_2_author = new javax.swing.JPanel();
        pnl_mail_2_author_text = new javax.swing.JLabel();
        pnl_mail_2_time = new javax.swing.JPanel();
        pnl_mail_2_time_text = new javax.swing.JLabel();
        pnl_mail_2_message = new javax.swing.JPanel();
        pnl_mail_2_message_text = new javax.swing.JLabel();
        pnl_mail_3 = new javax.swing.JPanel();
        pnl_mail_3_author = new javax.swing.JPanel();
        pnl_mail_3_author_text = new javax.swing.JLabel();
        pnl_mail_3_time = new javax.swing.JPanel();
        pnl_mail_3_time_text = new javax.swing.JLabel();
        pnl_mail_3_message = new javax.swing.JPanel();
        pnl_mail_3_message_text = new javax.swing.JLabel();
        pnl_mail_4 = new javax.swing.JPanel();
        pnl_mail_4_time = new javax.swing.JPanel();
        pnl_mail_4_time_text = new javax.swing.JLabel();
        pnl_mail_4_author = new javax.swing.JPanel();
        pnl_mail_4_author_text = new javax.swing.JLabel();
        pnl_mail_4_message = new javax.swing.JPanel();
        pnl_mail_4_message_text = new javax.swing.JLabel();
        pnl_mail_5 = new javax.swing.JPanel();
        pnl_mail_5_author = new javax.swing.JPanel();
        pnl_mail_5_author_text = new javax.swing.JLabel();
        pnl_mail_5_time = new javax.swing.JPanel();
        pnl_mail_5_time_text = new javax.swing.JLabel();
        pnl_mail_5_message = new javax.swing.JPanel();
        pnl_mail_5_message_text = new javax.swing.JLabel();
        pnl_mail_6 = new javax.swing.JPanel();
        pnl_mail_6_author = new javax.swing.JPanel();
        pnl_mail_6_author_text = new javax.swing.JLabel();
        pnl_mail_6_time = new javax.swing.JPanel();
        pnl_mail_6_time_text = new javax.swing.JLabel();
        pnl_mail_6_message = new javax.swing.JPanel();
        pnl_mail_6_message_text = new javax.swing.JLabel();
        pnl_mail_7 = new javax.swing.JPanel();
        pnl_mail_7_author = new javax.swing.JPanel();
        pnl_mail_7_author_text = new javax.swing.JLabel();
        pnl_mail_7_time = new javax.swing.JPanel();
        pnl_mail_7_time_text = new javax.swing.JLabel();
        pnl_mail_7_message = new javax.swing.JPanel();
        pnl_mail_7_message_text = new javax.swing.JLabel();
        pnl_mail_8 = new javax.swing.JPanel();
        pnl_mail_8_author = new javax.swing.JPanel();
        pnl_mail_8_author_text = new javax.swing.JLabel();
        pnl_mail_8_time = new javax.swing.JPanel();
        pnl_mail_8_time_text = new javax.swing.JLabel();
        pnl_mail_8_message = new javax.swing.JPanel();
        pnl_mail_8_message_text = new javax.swing.JLabel();
        pnl_mail_9 = new javax.swing.JPanel();
        pnl_mail_9_author = new javax.swing.JPanel();
        pnl_mail_9_author_text = new javax.swing.JLabel();
        pnl_mail_9_time = new javax.swing.JPanel();
        pnl_mail_9_time_text = new javax.swing.JLabel();
        pnl_mail_9_message = new javax.swing.JPanel();
        pnl_mail_9_message_text = new javax.swing.JLabel();
        pnl_mail_10 = new javax.swing.JPanel();
        pnl_mail_10_author = new javax.swing.JPanel();
        pnl_mail_10_author_text = new javax.swing.JLabel();
        pnl_mail_10_time = new javax.swing.JPanel();
        pnl_mail_10_time_text = new javax.swing.JLabel();
        pnl_mail_10_message = new javax.swing.JPanel();
        pnl_mail_10_message_text = new javax.swing.JLabel();
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
        pnl_giden_splitpane = new javax.swing.JSplitPane();
        pnl_main_mails1 = new javax.swing.JPanel();
        pnl_mail_slider1 = new javax.swing.JPanel();
        pnl_slider_left1 = new javax.swing.JPanel();
        pnl_slider_left_arrow1 = new javax.swing.JLabel();
        pnl_slider_right1 = new javax.swing.JPanel();
        pnl_slider_right_arrow1 = new javax.swing.JLabel();
        pnl_slider_middle1 = new javax.swing.JPanel();
        lbl_slider_number1 = new javax.swing.JLabel();
        pnl_mail_list1 = new javax.swing.JPanel();
        pnl_mail_11 = new javax.swing.JPanel();
        pnl_mail_1_author1 = new javax.swing.JPanel();
        pnl_mail_1_author_text1 = new javax.swing.JLabel();
        pnl_mail_1_time1 = new javax.swing.JPanel();
        pnl_mail_1_time_text1 = new javax.swing.JLabel();
        pnl_mail_1_message1 = new javax.swing.JPanel();
        pnl_mail_1_message_text1 = new javax.swing.JLabel();
        pnl_mail_12 = new javax.swing.JPanel();
        pnl_mail_2_author1 = new javax.swing.JPanel();
        pnl_mail_2_author_text1 = new javax.swing.JLabel();
        pnl_mail_2_time1 = new javax.swing.JPanel();
        pnl_mail_2_time_text1 = new javax.swing.JLabel();
        pnl_mail_2_message1 = new javax.swing.JPanel();
        pnl_mail_2_message_text1 = new javax.swing.JLabel();
        pnl_mail_13 = new javax.swing.JPanel();
        pnl_mail_3_author1 = new javax.swing.JPanel();
        pnl_mail_3_author_text1 = new javax.swing.JLabel();
        pnl_mail_3_time1 = new javax.swing.JPanel();
        pnl_mail_3_time_text1 = new javax.swing.JLabel();
        pnl_mail_3_message1 = new javax.swing.JPanel();
        pnl_mail_3_message_text1 = new javax.swing.JLabel();
        pnl_mail_14 = new javax.swing.JPanel();
        pnl_mail_4_time1 = new javax.swing.JPanel();
        pnl_mail_4_time_text1 = new javax.swing.JLabel();
        pnl_mail_4_author1 = new javax.swing.JPanel();
        pnl_mail_4_author_text1 = new javax.swing.JLabel();
        pnl_mail_4_message1 = new javax.swing.JPanel();
        pnl_mail_4_message_text1 = new javax.swing.JLabel();
        pnl_mail_15 = new javax.swing.JPanel();
        pnl_mail_5_author1 = new javax.swing.JPanel();
        pnl_mail_5_author_text1 = new javax.swing.JLabel();
        pnl_mail_5_time1 = new javax.swing.JPanel();
        pnl_mail_5_time_text1 = new javax.swing.JLabel();
        pnl_mail_5_message1 = new javax.swing.JPanel();
        pnl_mail_5_message_text1 = new javax.swing.JLabel();
        pnl_mail_16 = new javax.swing.JPanel();
        pnl_mail_6_author1 = new javax.swing.JPanel();
        pnl_mail_6_author_text1 = new javax.swing.JLabel();
        pnl_mail_6_time1 = new javax.swing.JPanel();
        pnl_mail_6_time_text1 = new javax.swing.JLabel();
        pnl_mail_6_message1 = new javax.swing.JPanel();
        pnl_mail_6_message_text1 = new javax.swing.JLabel();
        pnl_mail_17 = new javax.swing.JPanel();
        pnl_mail_7_author1 = new javax.swing.JPanel();
        pnl_mail_7_author_text1 = new javax.swing.JLabel();
        pnl_mail_7_time1 = new javax.swing.JPanel();
        pnl_mail_7_time_text1 = new javax.swing.JLabel();
        pnl_mail_7_message1 = new javax.swing.JPanel();
        pnl_mail_7_message_text1 = new javax.swing.JLabel();
        pnl_mail_18 = new javax.swing.JPanel();
        pnl_mail_8_author1 = new javax.swing.JPanel();
        pnl_mail_8_author_text1 = new javax.swing.JLabel();
        pnl_mail_8_time1 = new javax.swing.JPanel();
        pnl_mail_8_time_text1 = new javax.swing.JLabel();
        pnl_mail_8_message1 = new javax.swing.JPanel();
        pnl_mail_8_message_text1 = new javax.swing.JLabel();
        pnl_mail_19 = new javax.swing.JPanel();
        pnl_mail_9_author1 = new javax.swing.JPanel();
        pnl_mail_9_author_text1 = new javax.swing.JLabel();
        pnl_mail_9_time1 = new javax.swing.JPanel();
        pnl_mail_9_time_text1 = new javax.swing.JLabel();
        pnl_mail_9_message1 = new javax.swing.JPanel();
        pnl_mail_9_message_text1 = new javax.swing.JLabel();
        pnl_mail_20 = new javax.swing.JPanel();
        pnl_mail_10_author1 = new javax.swing.JPanel();
        pnl_mail_10_author_text1 = new javax.swing.JLabel();
        pnl_mail_10_time1 = new javax.swing.JPanel();
        pnl_mail_10_time_text1 = new javax.swing.JLabel();
        pnl_mail_10_message1 = new javax.swing.JPanel();
        pnl_mail_10_message_text1 = new javax.swing.JLabel();
        pnl_main_details1 = new javax.swing.JPanel();
        pnl_mail_header1 = new javax.swing.JPanel();
        mail_author1 = new javax.swing.JPanel();
        mail_author_text1 = new javax.swing.JLabel();
        mail_buttons1 = new javax.swing.JPanel();
        btn_mail_4 = new javax.swing.JLabel();
        btn_mail_5 = new javax.swing.JLabel();
        btn_mail_6 = new javax.swing.JLabel();
        mail_time1 = new javax.swing.JPanel();
        mail_time_text1 = new javax.swing.JLabel();
        pnl_mail_body1 = new javax.swing.JPanel();
        mail_body_split1 = new javax.swing.JSplitPane();
        mail_body_message1 = new javax.swing.JPanel();
        pnl_mail_detail_header1 = new javax.swing.JPanel();
        pnl_mail_detail_header_scrollpane1 = new javax.swing.JScrollPane();
        pnl_mail_detail_header_text1 = new javax.swing.JTextArea();
        pnl_mail_detail_body1 = new javax.swing.JPanel();
        pnl_mail_detail_attachment1 = new javax.swing.JPanel();
        pnl_mail_body_scrollpane1 = new javax.swing.JScrollPane();
        pnl_mail_body_text1 = new javax.swing.JTextArea();
        mail_body_reply1 = new javax.swing.JPanel();
        pnl_mail_body_reply_btn1 = new javax.swing.JPanel();
        btn_reply1 = new javax.swing.JLabel();
        pnl_mail_body_reply1 = new javax.swing.JPanel();
        pnl_mail_body_reply_scrollpane1 = new javax.swing.JScrollPane();
        pnl_mail_body_reply_text1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);

        pnl_sidebar.setBackground(new java.awt.Color(43, 49, 68));
        pnl_sidebar.setPreferredSize(new java.awt.Dimension(160, 0));
        pnl_sidebar.setLayout(new java.awt.BorderLayout());

        pnl_titlebuttons.setBackground(new java.awt.Color(43, 49, 68));
        pnl_titlebuttons.setPreferredSize(new java.awt.Dimension(200, 50));
        pnl_titlebuttons.setLayout(new java.awt.BorderLayout());

        pnl_btnclose.setBackground(new java.awt.Color(37, 40, 44));
        pnl_btnclose.setToolTipText("");
        pnl_btnclose.setPreferredSize(new java.awt.Dimension(55, 0));
        pnl_btnclose.setLayout(new java.awt.BorderLayout());

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
        pnl_btnclose.add(btn_close, java.awt.BorderLayout.CENTER);

        pnl_titlebuttons.add(pnl_btnclose, java.awt.BorderLayout.LINE_START);

        pnl_btnminimize.setBackground(new java.awt.Color(37, 40, 44));
        pnl_btnminimize.setPreferredSize(new java.awt.Dimension(55, 0));
        pnl_btnminimize.setLayout(new java.awt.BorderLayout());

        btn_minimize.setBackground(new java.awt.Color(51, 53, 65));
        btn_minimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/dry-clean-green.png"))); // NOI18N
        btn_minimize.setBorderPainted(false);
        btn_minimize.setContentAreaFilled(false);
        btn_minimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_minimizeMouseClicked(evt);
            }
        });
        pnl_btnminimize.add(btn_minimize, java.awt.BorderLayout.CENTER);

        pnl_titlebuttons.add(pnl_btnminimize, java.awt.BorderLayout.LINE_END);

        pnl_btnmaximize.setBackground(new java.awt.Color(37, 40, 44));
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

        pnl_sidebar.add(pnl_titlebuttons, java.awt.BorderLayout.PAGE_START);

        pnl_email_text.setBackground(new java.awt.Color(37, 40, 44));
        pnl_email_text.setPreferredSize(new java.awt.Dimension(33, 50));
        pnl_email_text.setLayout(new java.awt.BorderLayout());

        email_text.setBackground(new java.awt.Color(37, 40, 44));
        email_text.setFont(new java.awt.Font("Dialog", 0, 9)); // NOI18N
        email_text.setForeground(new java.awt.Color(102, 102, 102));
        email_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        email_text.setText("batuhansann@vbteam.com");
        pnl_email_text.add(email_text, java.awt.BorderLayout.CENTER);

        pnl_sidebar.add(pnl_email_text, java.awt.BorderLayout.PAGE_END);

        pnl_sidebar_menus.setBackground(new java.awt.Color(37, 40, 44));
        pnl_sidebar_menus.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnl_sidebar_text.setBackground(new java.awt.Color(37, 40, 44));
        pnl_sidebar_text.setName("btnMesaj"); // NOI18N
        pnl_sidebar_text.setPreferredSize(new java.awt.Dimension(100, 110));
        pnl_sidebar_text.setLayout(new java.awt.BorderLayout());

        btn_yeni_mail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/email.png"))); // NOI18N
        btn_yeni_mail.setBorder(null);
        btn_yeni_mail.setContentAreaFilled(false);
        btn_yeni_mail.setFocusPainted(false);
        pnl_sidebar_text.add(btn_yeni_mail, java.awt.BorderLayout.CENTER);

        pnl_sidebar_menus.add(pnl_sidebar_text, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, -1));

        pnl_sidebar_gelen.setBackground(new java.awt.Color(37, 40, 44));
        pnl_sidebar_gelen.setName("gelenKutu"); // NOI18N
        pnl_sidebar_gelen.setLayout(new java.awt.BorderLayout());

        pnl_gelen_text.setBackground(new java.awt.Color(37, 40, 44));
        pnl_gelen_text.setOpaque(false);
        pnl_gelen_text.setPreferredSize(new java.awt.Dimension(100, 100));
        pnl_gelen_text.setLayout(new java.awt.BorderLayout());

        gelen_text.setBackground(new java.awt.Color(37, 40, 44));
        gelen_text.setForeground(new java.awt.Color(165, 165, 172));
        gelen_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gelen_text.setText("Gelen Kutusu");
        gelen_text.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gelen_text.setName("gelenKutu"); // NOI18N
        gelen_text.setPreferredSize(new java.awt.Dimension(60, 60));
        pnl_gelen_text.add(gelen_text, java.awt.BorderLayout.CENTER);

        pnl_sidebar_gelen.add(pnl_gelen_text, java.awt.BorderLayout.LINE_END);

        pnl_gelen_icon.setBackground(new java.awt.Color(37, 40, 44));
        pnl_gelen_icon.setOpaque(false);
        pnl_gelen_icon.setPreferredSize(new java.awt.Dimension(60, 60));
        pnl_gelen_icon.setLayout(new java.awt.BorderLayout());

        gelen_icon.setBackground(new java.awt.Color(37, 40, 44));
        gelen_icon.setForeground(new java.awt.Color(165, 165, 172));
        gelen_icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gelen_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/mail_menu_icon.png"))); // NOI18N
        gelen_icon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gelen_icon.setName("gelenKutu"); // NOI18N
        gelen_icon.setPreferredSize(new java.awt.Dimension(60, 60));
        pnl_gelen_icon.add(gelen_icon, java.awt.BorderLayout.LINE_END);

        pnl_sidebar_gelen.add(pnl_gelen_icon, java.awt.BorderLayout.CENTER);

        pnl_sidebar_menus.add(pnl_sidebar_gelen, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 160, 40));

        pnl_sidebar_cop.setBackground(new java.awt.Color(37, 40, 44));
        pnl_sidebar_cop.setName("copKutu"); // NOI18N
        pnl_sidebar_cop.setLayout(new java.awt.BorderLayout());

        pnl_cop_text.setBackground(new java.awt.Color(37, 40, 44));
        pnl_cop_text.setOpaque(false);
        pnl_cop_text.setPreferredSize(new java.awt.Dimension(100, 100));
        pnl_cop_text.setLayout(new java.awt.BorderLayout());

        cop_text.setBackground(new java.awt.Color(37, 40, 44));
        cop_text.setForeground(new java.awt.Color(165, 165, 172));
        cop_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cop_text.setText("Çöp Kutusu");
        cop_text.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cop_text.setName("gelenKutu"); // NOI18N
        cop_text.setPreferredSize(new java.awt.Dimension(60, 60));
        pnl_cop_text.add(cop_text, java.awt.BorderLayout.CENTER);

        pnl_sidebar_cop.add(pnl_cop_text, java.awt.BorderLayout.LINE_END);

        pnl_cop_icon.setBackground(new java.awt.Color(37, 40, 44));
        pnl_cop_icon.setOpaque(false);
        pnl_cop_icon.setLayout(new java.awt.BorderLayout());

        cop_icon.setBackground(new java.awt.Color(37, 40, 44));
        cop_icon.setForeground(new java.awt.Color(165, 165, 172));
        cop_icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cop_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/mail_menu_icon_cop.png"))); // NOI18N
        cop_icon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cop_icon.setName("gelenKutu"); // NOI18N
        cop_icon.setPreferredSize(new java.awt.Dimension(60, 60));
        pnl_cop_icon.add(cop_icon, java.awt.BorderLayout.LINE_END);

        pnl_sidebar_cop.add(pnl_cop_icon, java.awt.BorderLayout.CENTER);

        pnl_sidebar_menus.add(pnl_sidebar_cop, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 260, 160, 40));

        pnl_sidebar_taslak.setBackground(new java.awt.Color(37, 40, 44));
        pnl_sidebar_taslak.setName("taslakKutu"); // NOI18N
        pnl_sidebar_taslak.setLayout(new java.awt.BorderLayout());

        pnl_taslak_text.setBackground(new java.awt.Color(37, 40, 44));
        pnl_taslak_text.setOpaque(false);
        pnl_taslak_text.setPreferredSize(new java.awt.Dimension(100, 100));
        pnl_taslak_text.setLayout(new java.awt.BorderLayout());

        taslak_text.setForeground(new java.awt.Color(165, 165, 172));
        taslak_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        taslak_text.setText("Taslak");
        taslak_text.setName("taslakKutu"); // NOI18N
        taslak_text.setPreferredSize(new java.awt.Dimension(41, 10));
        pnl_taslak_text.add(taslak_text, java.awt.BorderLayout.CENTER);

        pnl_sidebar_taslak.add(pnl_taslak_text, java.awt.BorderLayout.LINE_END);

        pnl_taslak_icon.setBackground(new java.awt.Color(37, 40, 44));
        pnl_taslak_icon.setOpaque(false);
        pnl_taslak_icon.setPreferredSize(new java.awt.Dimension(60, 60));
        pnl_taslak_icon.setLayout(new java.awt.BorderLayout());

        taslak_icon.setBackground(new java.awt.Color(37, 40, 44));
        taslak_icon.setForeground(new java.awt.Color(165, 165, 172));
        taslak_icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        taslak_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/mail_menu_icon_taslak.png"))); // NOI18N
        taslak_icon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        taslak_icon.setName("gelenKutu"); // NOI18N
        taslak_icon.setPreferredSize(new java.awt.Dimension(60, 60));
        pnl_taslak_icon.add(taslak_icon, java.awt.BorderLayout.LINE_END);

        pnl_sidebar_taslak.add(pnl_taslak_icon, java.awt.BorderLayout.LINE_START);

        pnl_sidebar_menus.add(pnl_sidebar_taslak, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 160, 40));

        pnl_sidebar_giden.setBackground(new java.awt.Color(37, 40, 44));
        pnl_sidebar_giden.setName("gidenKutu"); // NOI18N
        pnl_sidebar_giden.setLayout(new java.awt.BorderLayout());

        pnl_giden_text.setBackground(new java.awt.Color(37, 40, 44));
        pnl_giden_text.setOpaque(false);
        pnl_giden_text.setPreferredSize(new java.awt.Dimension(100, 100));
        pnl_giden_text.setLayout(new java.awt.BorderLayout());

        giden_text.setForeground(new java.awt.Color(165, 165, 172));
        giden_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        giden_text.setText("Giden Kutusu");
        giden_text.setName("gidenKutu"); // NOI18N
        pnl_giden_text.add(giden_text, java.awt.BorderLayout.CENTER);

        pnl_sidebar_giden.add(pnl_giden_text, java.awt.BorderLayout.LINE_END);

        pnl_giden_icon.setBackground(new java.awt.Color(37, 40, 44));
        pnl_giden_icon.setOpaque(false);
        pnl_giden_icon.setPreferredSize(new java.awt.Dimension(60, 60));
        pnl_giden_icon.setLayout(new java.awt.BorderLayout());

        giden_icon.setBackground(new java.awt.Color(37, 40, 44));
        giden_icon.setForeground(new java.awt.Color(165, 165, 172));
        giden_icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        giden_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/mail_menu_icon_giden.png"))); // NOI18N
        giden_icon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        giden_icon.setName("gelenKutu"); // NOI18N
        giden_icon.setPreferredSize(new java.awt.Dimension(60, 60));
        pnl_giden_icon.add(giden_icon, java.awt.BorderLayout.LINE_END);

        pnl_sidebar_giden.add(pnl_giden_icon, java.awt.BorderLayout.LINE_START);

        pnl_sidebar_menus.add(pnl_sidebar_giden, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 160, 40));

        pnl_sidebar.add(pnl_sidebar_menus, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnl_sidebar, java.awt.BorderLayout.LINE_START);

        pnl_main.setBackground(new java.awt.Color(56, 58, 71));
        pnl_main.setLayout(new java.awt.BorderLayout());

        pnl_main_titlebar.setBackground(new java.awt.Color(37, 40, 44));
        pnl_main_titlebar.setPreferredSize(new java.awt.Dimension(0, 50));

        javax.swing.GroupLayout pnl_main_titlebarLayout = new javax.swing.GroupLayout(pnl_main_titlebar);
        pnl_main_titlebar.setLayout(pnl_main_titlebarLayout);
        pnl_main_titlebarLayout.setHorizontalGroup(
            pnl_main_titlebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnl_main_titlebarLayout.setVerticalGroup(
            pnl_main_titlebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        pnl_main.add(pnl_main_titlebar, java.awt.BorderLayout.PAGE_START);

        pnl_main_mail.setBackground(new java.awt.Color(37, 40, 44));
        pnl_main_mail.setLayout(new java.awt.CardLayout());

        pnl_gelen_splitpane.setBackground(new java.awt.Color(37, 40, 44));
        pnl_gelen_splitpane.setDividerLocation(700);
        pnl_gelen_splitpane.setDividerSize(3);
        pnl_gelen_splitpane.setForeground(new java.awt.Color(37, 40, 44));
        pnl_gelen_splitpane.setPreferredSize(new java.awt.Dimension(1250, 700));

        pnl_main_mails.setBackground(new java.awt.Color(45, 48, 53));
        pnl_main_mails.setPreferredSize(new java.awt.Dimension(700, 750));
        pnl_main_mails.setLayout(new java.awt.BorderLayout());

        pnl_mail_slider.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_slider.setPreferredSize(new java.awt.Dimension(600, 40));
        pnl_mail_slider.setLayout(new java.awt.BorderLayout());

        pnl_slider_left.setBackground(new java.awt.Color(45, 48, 53));
        pnl_slider_left.setPreferredSize(new java.awt.Dimension(200, 300));
        pnl_slider_left.setLayout(new java.awt.BorderLayout());

        pnl_slider_left_arrow.setBackground(new java.awt.Color(22, 22, 22));
        pnl_slider_left_arrow.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_slider_left_arrow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/left-arrow.png"))); // NOI18N
        pnl_slider_left.add(pnl_slider_left_arrow, java.awt.BorderLayout.CENTER);

        pnl_mail_slider.add(pnl_slider_left, java.awt.BorderLayout.LINE_START);

        pnl_slider_right.setBackground(new java.awt.Color(45, 48, 53));
        pnl_slider_right.setPreferredSize(new java.awt.Dimension(200, 50));
        pnl_slider_right.setLayout(new java.awt.BorderLayout());

        pnl_slider_right_arrow.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_slider_right_arrow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/right-arrow.png"))); // NOI18N
        pnl_slider_right.add(pnl_slider_right_arrow, java.awt.BorderLayout.CENTER);

        pnl_mail_slider.add(pnl_slider_right, java.awt.BorderLayout.LINE_END);

        pnl_slider_middle.setBackground(new java.awt.Color(45, 48, 53));
        pnl_slider_middle.setLayout(new java.awt.BorderLayout());

        lbl_slider_number.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lbl_slider_number.setForeground(new java.awt.Color(255, 255, 255));
        lbl_slider_number.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_slider_number.setText("1");
        pnl_slider_middle.add(lbl_slider_number, java.awt.BorderLayout.CENTER);

        pnl_mail_slider.add(pnl_slider_middle, java.awt.BorderLayout.CENTER);

        pnl_main_mails.add(pnl_mail_slider, java.awt.BorderLayout.PAGE_END);

        pnl_mail_list.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_list.setPreferredSize(new java.awt.Dimension(700, 700));
        pnl_mail_list.setRequestFocusEnabled(false);
        pnl_mail_list.setLayout(new java.awt.GridLayout(10, 0));

        pnl_mail_1.setBackground(new java.awt.Color(255, 102, 102));
        pnl_mail_1.setLayout(new java.awt.BorderLayout());

        pnl_mail_1_author.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_1_author.setPreferredSize(new java.awt.Dimension(100, 175));
        pnl_mail_1_author.setLayout(new java.awt.BorderLayout());

        pnl_mail_1_author_text.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_1_author_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_1_author_text.setText("[Author]");
        pnl_mail_1_author.add(pnl_mail_1_author_text, java.awt.BorderLayout.CENTER);

        pnl_mail_1.add(pnl_mail_1_author, java.awt.BorderLayout.LINE_START);

        pnl_mail_1_time.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_1_time.setPreferredSize(new java.awt.Dimension(100, 175));
        pnl_mail_1_time.setLayout(new java.awt.BorderLayout());

        pnl_mail_1_time_text.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_1_time_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_1_time_text.setText("[Time]");
        pnl_mail_1_time.add(pnl_mail_1_time_text, java.awt.BorderLayout.CENTER);

        pnl_mail_1.add(pnl_mail_1_time, java.awt.BorderLayout.LINE_END);

        pnl_mail_1_message.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_1_message.setLayout(new java.awt.BorderLayout());

        pnl_mail_1_message_text.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_1_message_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_1_message_text.setText("[Header.Message]");
        pnl_mail_1_message.add(pnl_mail_1_message_text, java.awt.BorderLayout.CENTER);

        pnl_mail_1.add(pnl_mail_1_message, java.awt.BorderLayout.CENTER);

        pnl_mail_list.add(pnl_mail_1);

        pnl_mail_2.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_2.setLayout(new java.awt.BorderLayout());

        pnl_mail_2_author.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_2_author.setPreferredSize(new java.awt.Dimension(100, 175));
        pnl_mail_2_author.setLayout(new java.awt.BorderLayout());

        pnl_mail_2_author_text.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_2_author_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_2_author_text.setText("[Author]");
        pnl_mail_2_author.add(pnl_mail_2_author_text, java.awt.BorderLayout.CENTER);

        pnl_mail_2.add(pnl_mail_2_author, java.awt.BorderLayout.LINE_START);

        pnl_mail_2_time.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_2_time.setPreferredSize(new java.awt.Dimension(100, 175));
        pnl_mail_2_time.setLayout(new java.awt.BorderLayout());

        pnl_mail_2_time_text.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_2_time_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_2_time_text.setText("[Time]");
        pnl_mail_2_time.add(pnl_mail_2_time_text, java.awt.BorderLayout.CENTER);

        pnl_mail_2.add(pnl_mail_2_time, java.awt.BorderLayout.LINE_END);

        pnl_mail_2_message.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_2_message.setLayout(new java.awt.BorderLayout());

        pnl_mail_2_message_text.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_2_message_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_2_message_text.setText("[Header.Message]");
        pnl_mail_2_message.add(pnl_mail_2_message_text, java.awt.BorderLayout.CENTER);

        pnl_mail_2.add(pnl_mail_2_message, java.awt.BorderLayout.CENTER);

        pnl_mail_list.add(pnl_mail_2);

        pnl_mail_3.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_3.setLayout(new java.awt.BorderLayout());

        pnl_mail_3_author.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_3_author.setPreferredSize(new java.awt.Dimension(100, 175));
        pnl_mail_3_author.setLayout(new java.awt.BorderLayout());

        pnl_mail_3_author_text.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_3_author_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_3_author_text.setText("[Author]");
        pnl_mail_3_author.add(pnl_mail_3_author_text, java.awt.BorderLayout.CENTER);

        pnl_mail_3.add(pnl_mail_3_author, java.awt.BorderLayout.LINE_START);

        pnl_mail_3_time.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_3_time.setPreferredSize(new java.awt.Dimension(100, 175));
        pnl_mail_3_time.setLayout(new java.awt.BorderLayout());

        pnl_mail_3_time_text.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_3_time_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_3_time_text.setText("[Time]");
        pnl_mail_3_time.add(pnl_mail_3_time_text, java.awt.BorderLayout.CENTER);

        pnl_mail_3.add(pnl_mail_3_time, java.awt.BorderLayout.LINE_END);

        pnl_mail_3_message.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_3_message.setLayout(new java.awt.BorderLayout());

        pnl_mail_3_message_text.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_3_message_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_3_message_text.setText("[Header.Message]");
        pnl_mail_3_message.add(pnl_mail_3_message_text, java.awt.BorderLayout.CENTER);

        pnl_mail_3.add(pnl_mail_3_message, java.awt.BorderLayout.CENTER);

        pnl_mail_list.add(pnl_mail_3);

        pnl_mail_4.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_4.setLayout(new java.awt.BorderLayout());

        pnl_mail_4_time.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_4_time.setPreferredSize(new java.awt.Dimension(100, 175));
        pnl_mail_4_time.setLayout(new java.awt.BorderLayout());

        pnl_mail_4_time_text.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_4_time_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_4_time_text.setText("[Author]");
        pnl_mail_4_time.add(pnl_mail_4_time_text, java.awt.BorderLayout.CENTER);

        pnl_mail_4.add(pnl_mail_4_time, java.awt.BorderLayout.LINE_START);

        pnl_mail_4_author.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_4_author.setPreferredSize(new java.awt.Dimension(100, 175));
        pnl_mail_4_author.setLayout(new java.awt.BorderLayout());

        pnl_mail_4_author_text.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_4_author_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_4_author_text.setText("[Time]");
        pnl_mail_4_author.add(pnl_mail_4_author_text, java.awt.BorderLayout.CENTER);

        pnl_mail_4.add(pnl_mail_4_author, java.awt.BorderLayout.LINE_END);

        pnl_mail_4_message.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_4_message.setLayout(new java.awt.BorderLayout());

        pnl_mail_4_message_text.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_4_message_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_4_message_text.setText("[Header.Message]");
        pnl_mail_4_message.add(pnl_mail_4_message_text, java.awt.BorderLayout.CENTER);

        pnl_mail_4.add(pnl_mail_4_message, java.awt.BorderLayout.CENTER);

        pnl_mail_list.add(pnl_mail_4);

        pnl_mail_5.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_5.setLayout(new java.awt.BorderLayout());

        pnl_mail_5_author.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_5_author.setPreferredSize(new java.awt.Dimension(100, 175));
        pnl_mail_5_author.setLayout(new java.awt.BorderLayout());

        pnl_mail_5_author_text.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_5_author_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_5_author_text.setText("[Author]");
        pnl_mail_5_author.add(pnl_mail_5_author_text, java.awt.BorderLayout.CENTER);

        pnl_mail_5.add(pnl_mail_5_author, java.awt.BorderLayout.LINE_START);

        pnl_mail_5_time.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_5_time.setPreferredSize(new java.awt.Dimension(100, 175));
        pnl_mail_5_time.setLayout(new java.awt.BorderLayout());

        pnl_mail_5_time_text.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_5_time_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_5_time_text.setText("[Time]");
        pnl_mail_5_time.add(pnl_mail_5_time_text, java.awt.BorderLayout.CENTER);

        pnl_mail_5.add(pnl_mail_5_time, java.awt.BorderLayout.LINE_END);

        pnl_mail_5_message.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_5_message.setLayout(new java.awt.BorderLayout());

        pnl_mail_5_message_text.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_5_message_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_5_message_text.setText("[Header.Message]");
        pnl_mail_5_message.add(pnl_mail_5_message_text, java.awt.BorderLayout.CENTER);

        pnl_mail_5.add(pnl_mail_5_message, java.awt.BorderLayout.CENTER);

        pnl_mail_list.add(pnl_mail_5);

        pnl_mail_6.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_6.setLayout(new java.awt.BorderLayout());

        pnl_mail_6_author.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_6_author.setPreferredSize(new java.awt.Dimension(100, 175));
        pnl_mail_6_author.setLayout(new java.awt.BorderLayout());

        pnl_mail_6_author_text.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_6_author_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_6_author_text.setText("[Author]");
        pnl_mail_6_author.add(pnl_mail_6_author_text, java.awt.BorderLayout.CENTER);

        pnl_mail_6.add(pnl_mail_6_author, java.awt.BorderLayout.LINE_START);

        pnl_mail_6_time.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_6_time.setPreferredSize(new java.awt.Dimension(100, 175));
        pnl_mail_6_time.setLayout(new java.awt.BorderLayout());

        pnl_mail_6_time_text.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_6_time_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_6_time_text.setText("[Time]");
        pnl_mail_6_time.add(pnl_mail_6_time_text, java.awt.BorderLayout.CENTER);

        pnl_mail_6.add(pnl_mail_6_time, java.awt.BorderLayout.LINE_END);

        pnl_mail_6_message.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_6_message.setLayout(new java.awt.BorderLayout());

        pnl_mail_6_message_text.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_6_message_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_6_message_text.setText("[Header.Message]");
        pnl_mail_6_message.add(pnl_mail_6_message_text, java.awt.BorderLayout.CENTER);

        pnl_mail_6.add(pnl_mail_6_message, java.awt.BorderLayout.CENTER);

        pnl_mail_list.add(pnl_mail_6);

        pnl_mail_7.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_7.setLayout(new java.awt.BorderLayout());

        pnl_mail_7_author.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_7_author.setPreferredSize(new java.awt.Dimension(100, 175));
        pnl_mail_7_author.setLayout(new java.awt.BorderLayout());

        pnl_mail_7_author_text.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_7_author_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_7_author_text.setText("[Author]");
        pnl_mail_7_author.add(pnl_mail_7_author_text, java.awt.BorderLayout.CENTER);

        pnl_mail_7.add(pnl_mail_7_author, java.awt.BorderLayout.LINE_START);

        pnl_mail_7_time.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_7_time.setPreferredSize(new java.awt.Dimension(100, 175));
        pnl_mail_7_time.setLayout(new java.awt.BorderLayout());

        pnl_mail_7_time_text.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_7_time_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_7_time_text.setText("[Time]");
        pnl_mail_7_time.add(pnl_mail_7_time_text, java.awt.BorderLayout.CENTER);

        pnl_mail_7.add(pnl_mail_7_time, java.awt.BorderLayout.LINE_END);

        pnl_mail_7_message.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_7_message.setLayout(new java.awt.BorderLayout());

        pnl_mail_7_message_text.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_7_message_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_7_message_text.setText("[Header.Message]");
        pnl_mail_7_message.add(pnl_mail_7_message_text, java.awt.BorderLayout.CENTER);

        pnl_mail_7.add(pnl_mail_7_message, java.awt.BorderLayout.CENTER);

        pnl_mail_list.add(pnl_mail_7);

        pnl_mail_8.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_8.setLayout(new java.awt.BorderLayout());

        pnl_mail_8_author.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_8_author.setPreferredSize(new java.awt.Dimension(100, 175));
        pnl_mail_8_author.setLayout(new java.awt.BorderLayout());

        pnl_mail_8_author_text.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_8_author_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_8_author_text.setText("[Author]");
        pnl_mail_8_author.add(pnl_mail_8_author_text, java.awt.BorderLayout.CENTER);

        pnl_mail_8.add(pnl_mail_8_author, java.awt.BorderLayout.LINE_START);

        pnl_mail_8_time.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_8_time.setPreferredSize(new java.awt.Dimension(100, 175));
        pnl_mail_8_time.setLayout(new java.awt.BorderLayout());

        pnl_mail_8_time_text.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_8_time_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_8_time_text.setText("[Time]");
        pnl_mail_8_time.add(pnl_mail_8_time_text, java.awt.BorderLayout.CENTER);

        pnl_mail_8.add(pnl_mail_8_time, java.awt.BorderLayout.LINE_END);

        pnl_mail_8_message.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_8_message.setLayout(new java.awt.BorderLayout());

        pnl_mail_8_message_text.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_8_message_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_8_message_text.setText("[Header.Message]");
        pnl_mail_8_message.add(pnl_mail_8_message_text, java.awt.BorderLayout.CENTER);

        pnl_mail_8.add(pnl_mail_8_message, java.awt.BorderLayout.CENTER);

        pnl_mail_list.add(pnl_mail_8);

        pnl_mail_9.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_9.setLayout(new java.awt.BorderLayout());

        pnl_mail_9_author.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_9_author.setPreferredSize(new java.awt.Dimension(100, 175));
        pnl_mail_9_author.setLayout(new java.awt.BorderLayout());

        pnl_mail_9_author_text.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_9_author_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_9_author_text.setText("[Author]");
        pnl_mail_9_author.add(pnl_mail_9_author_text, java.awt.BorderLayout.CENTER);

        pnl_mail_9.add(pnl_mail_9_author, java.awt.BorderLayout.LINE_START);

        pnl_mail_9_time.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_9_time.setPreferredSize(new java.awt.Dimension(100, 175));
        pnl_mail_9_time.setLayout(new java.awt.BorderLayout());

        pnl_mail_9_time_text.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_9_time_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_9_time_text.setText("[Time]");
        pnl_mail_9_time.add(pnl_mail_9_time_text, java.awt.BorderLayout.CENTER);

        pnl_mail_9.add(pnl_mail_9_time, java.awt.BorderLayout.LINE_END);

        pnl_mail_9_message.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_9_message.setLayout(new java.awt.BorderLayout());

        pnl_mail_9_message_text.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_9_message_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_9_message_text.setText("[Header.Message]");
        pnl_mail_9_message.add(pnl_mail_9_message_text, java.awt.BorderLayout.CENTER);

        pnl_mail_9.add(pnl_mail_9_message, java.awt.BorderLayout.CENTER);

        pnl_mail_list.add(pnl_mail_9);

        pnl_mail_10.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_10.setLayout(new java.awt.BorderLayout());

        pnl_mail_10_author.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_10_author.setPreferredSize(new java.awt.Dimension(100, 175));
        pnl_mail_10_author.setLayout(new java.awt.BorderLayout());

        pnl_mail_10_author_text.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_10_author_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_10_author_text.setText("[Author]");
        pnl_mail_10_author.add(pnl_mail_10_author_text, java.awt.BorderLayout.CENTER);

        pnl_mail_10.add(pnl_mail_10_author, java.awt.BorderLayout.LINE_START);

        pnl_mail_10_time.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_10_time.setPreferredSize(new java.awt.Dimension(100, 175));
        pnl_mail_10_time.setLayout(new java.awt.BorderLayout());

        pnl_mail_10_time_text.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_10_time_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_10_time_text.setText("[Time]");
        pnl_mail_10_time.add(pnl_mail_10_time_text, java.awt.BorderLayout.CENTER);

        pnl_mail_10.add(pnl_mail_10_time, java.awt.BorderLayout.LINE_END);

        pnl_mail_10_message.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_10_message.setLayout(new java.awt.BorderLayout());

        pnl_mail_10_message_text.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_10_message_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_10_message_text.setText("[Header.Message]");
        pnl_mail_10_message.add(pnl_mail_10_message_text, java.awt.BorderLayout.CENTER);

        pnl_mail_10.add(pnl_mail_10_message, java.awt.BorderLayout.CENTER);

        pnl_mail_list.add(pnl_mail_10);

        pnl_main_mails.add(pnl_mail_list, java.awt.BorderLayout.LINE_START);

        pnl_gelen_splitpane.setLeftComponent(pnl_main_mails);

        pnl_main_details.setBackground(new java.awt.Color(37, 40, 44));
        pnl_main_details.setLayout(new java.awt.BorderLayout());

        pnl_mail_header.setPreferredSize(new java.awt.Dimension(574, 60));
        pnl_mail_header.setLayout(new java.awt.BorderLayout());

        mail_author.setBackground(new java.awt.Color(45, 48, 53));
        mail_author.setLayout(new java.awt.BorderLayout());

        mail_author_text.setBackground(new java.awt.Color(45, 48, 53));
        mail_author_text.setForeground(new java.awt.Color(255, 255, 255));
        mail_author_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mail_author_text.setText("[Author]");
        mail_author_text.setPreferredSize(new java.awt.Dimension(89, 15));
        mail_author.add(mail_author_text, java.awt.BorderLayout.CENTER);

        pnl_mail_header.add(mail_author, java.awt.BorderLayout.LINE_START);

        mail_buttons.setBackground(new java.awt.Color(45, 48, 53));
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

        mail_time.setBackground(new java.awt.Color(45, 48, 53));
        mail_time.setLayout(new java.awt.BorderLayout());

        mail_time_text.setBackground(new java.awt.Color(45, 48, 53));
        mail_time_text.setForeground(new java.awt.Color(255, 255, 255));
        mail_time_text.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        mail_time_text.setText("[Time]");
        mail_time.add(mail_time_text, java.awt.BorderLayout.CENTER);

        pnl_mail_header.add(mail_time, java.awt.BorderLayout.CENTER);

        pnl_main_details.add(pnl_mail_header, java.awt.BorderLayout.PAGE_START);

        pnl_mail_body.setBackground(new java.awt.Color(45, 48, 53));
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
        pnl_mail_detail_header.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_detail_header.setPreferredSize(new java.awt.Dimension(60, 60));
        pnl_mail_detail_header.setLayout(new java.awt.BorderLayout());

        pnl_mail_detail_header_scrollpane.setBorder(null);
        pnl_mail_detail_header_scrollpane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        pnl_mail_detail_header_scrollpane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        pnl_mail_detail_header_text.setEditable(false);
        pnl_mail_detail_header_text.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_detail_header_text.setColumns(5);
        pnl_mail_detail_header_text.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        pnl_mail_detail_header_text.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_detail_header_text.setLineWrap(true);
        pnl_mail_detail_header_text.setRows(2);
        pnl_mail_detail_header_text.setTabSize(2);
        pnl_mail_detail_header_text.setWrapStyleWord(true);
        pnl_mail_detail_header_text.setCaretColor(new java.awt.Color(255, 255, 255));
        pnl_mail_detail_header_scrollpane.setViewportView(pnl_mail_detail_header_text);

        pnl_mail_detail_header.add(pnl_mail_detail_header_scrollpane, java.awt.BorderLayout.CENTER);

        mail_body_message.add(pnl_mail_detail_header, java.awt.BorderLayout.PAGE_START);

        pnl_mail_detail_body.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_detail_body.setLayout(new java.awt.BorderLayout());

        pnl_mail_detail_attachment.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_detail_attachment.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3), "Email Eki", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 11), new java.awt.Color(165, 165, 172))); // NOI18N
        pnl_mail_detail_attachment.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_detail_attachment.setPreferredSize(new java.awt.Dimension(60, 60));
        pnl_mail_detail_attachment.setLayout(new java.awt.BorderLayout());
        pnl_mail_detail_body.add(pnl_mail_detail_attachment, java.awt.BorderLayout.PAGE_END);

        pnl_mail_body_scrollpane.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2));

        pnl_mail_body_text.setEditable(false);
        pnl_mail_body_text.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_body_text.setColumns(5);
        pnl_mail_body_text.setForeground(new java.awt.Color(165, 165, 172));
        pnl_mail_body_text.setLineWrap(true);
        pnl_mail_body_text.setRows(2);
        pnl_mail_body_text.setTabSize(2);
        pnl_mail_body_text.setWrapStyleWord(true);
        pnl_mail_body_text.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2));
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
        pnl_mail_body_reply_text.setForeground(new java.awt.Color(255, 255, 255));
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

        pnl_main_mail.add(pnl_gelen_splitpane, "gelencard");

        pnl_giden_splitpane.setBackground(new java.awt.Color(37, 40, 44));
        pnl_giden_splitpane.setDividerLocation(700);
        pnl_giden_splitpane.setDividerSize(3);
        pnl_giden_splitpane.setForeground(new java.awt.Color(37, 40, 44));
        pnl_giden_splitpane.setPreferredSize(new java.awt.Dimension(1250, 700));

        pnl_main_mails1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_main_mails1.setPreferredSize(new java.awt.Dimension(700, 750));
        pnl_main_mails1.setLayout(new java.awt.BorderLayout());

        pnl_mail_slider1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_slider1.setPreferredSize(new java.awt.Dimension(600, 40));
        pnl_mail_slider1.setLayout(new java.awt.BorderLayout());

        pnl_slider_left1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_slider_left1.setPreferredSize(new java.awt.Dimension(200, 300));
        pnl_slider_left1.setLayout(new java.awt.BorderLayout());

        pnl_slider_left_arrow1.setBackground(new java.awt.Color(22, 22, 22));
        pnl_slider_left_arrow1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_slider_left_arrow1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/left-arrow.png"))); // NOI18N
        pnl_slider_left1.add(pnl_slider_left_arrow1, java.awt.BorderLayout.CENTER);

        pnl_mail_slider1.add(pnl_slider_left1, java.awt.BorderLayout.LINE_START);

        pnl_slider_right1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_slider_right1.setPreferredSize(new java.awt.Dimension(200, 50));
        pnl_slider_right1.setLayout(new java.awt.BorderLayout());

        pnl_slider_right_arrow1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_slider_right_arrow1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/right-arrow.png"))); // NOI18N
        pnl_slider_right1.add(pnl_slider_right_arrow1, java.awt.BorderLayout.CENTER);

        pnl_mail_slider1.add(pnl_slider_right1, java.awt.BorderLayout.LINE_END);

        pnl_slider_middle1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_slider_middle1.setLayout(new java.awt.BorderLayout());

        lbl_slider_number1.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lbl_slider_number1.setForeground(new java.awt.Color(255, 255, 255));
        lbl_slider_number1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_slider_number1.setText("1");
        pnl_slider_middle1.add(lbl_slider_number1, java.awt.BorderLayout.CENTER);

        pnl_mail_slider1.add(pnl_slider_middle1, java.awt.BorderLayout.CENTER);

        pnl_main_mails1.add(pnl_mail_slider1, java.awt.BorderLayout.PAGE_END);

        pnl_mail_list1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_list1.setPreferredSize(new java.awt.Dimension(700, 700));
        pnl_mail_list1.setRequestFocusEnabled(false);
        pnl_mail_list1.setLayout(new java.awt.GridLayout(10, 0));

        pnl_mail_11.setBackground(new java.awt.Color(255, 102, 102));
        pnl_mail_11.setLayout(new java.awt.BorderLayout());

        pnl_mail_1_author1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_1_author1.setPreferredSize(new java.awt.Dimension(100, 175));
        pnl_mail_1_author1.setLayout(new java.awt.BorderLayout());

        pnl_mail_1_author_text1.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_1_author_text1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_1_author_text1.setText("[Author]");
        pnl_mail_1_author1.add(pnl_mail_1_author_text1, java.awt.BorderLayout.CENTER);

        pnl_mail_11.add(pnl_mail_1_author1, java.awt.BorderLayout.LINE_START);

        pnl_mail_1_time1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_1_time1.setPreferredSize(new java.awt.Dimension(100, 175));
        pnl_mail_1_time1.setLayout(new java.awt.BorderLayout());

        pnl_mail_1_time_text1.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_1_time_text1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_1_time_text1.setText("[Time]");
        pnl_mail_1_time1.add(pnl_mail_1_time_text1, java.awt.BorderLayout.CENTER);

        pnl_mail_11.add(pnl_mail_1_time1, java.awt.BorderLayout.LINE_END);

        pnl_mail_1_message1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_1_message1.setLayout(new java.awt.BorderLayout());

        pnl_mail_1_message_text1.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_1_message_text1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_1_message_text1.setText("[Header.Message]");
        pnl_mail_1_message1.add(pnl_mail_1_message_text1, java.awt.BorderLayout.CENTER);

        pnl_mail_11.add(pnl_mail_1_message1, java.awt.BorderLayout.CENTER);

        pnl_mail_list1.add(pnl_mail_11);

        pnl_mail_12.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_12.setLayout(new java.awt.BorderLayout());

        pnl_mail_2_author1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_2_author1.setPreferredSize(new java.awt.Dimension(100, 175));
        pnl_mail_2_author1.setLayout(new java.awt.BorderLayout());

        pnl_mail_2_author_text1.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_2_author_text1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_2_author_text1.setText("[Author]");
        pnl_mail_2_author1.add(pnl_mail_2_author_text1, java.awt.BorderLayout.CENTER);

        pnl_mail_12.add(pnl_mail_2_author1, java.awt.BorderLayout.LINE_START);

        pnl_mail_2_time1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_2_time1.setPreferredSize(new java.awt.Dimension(100, 175));
        pnl_mail_2_time1.setLayout(new java.awt.BorderLayout());

        pnl_mail_2_time_text1.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_2_time_text1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_2_time_text1.setText("[Time]");
        pnl_mail_2_time1.add(pnl_mail_2_time_text1, java.awt.BorderLayout.CENTER);

        pnl_mail_12.add(pnl_mail_2_time1, java.awt.BorderLayout.LINE_END);

        pnl_mail_2_message1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_2_message1.setLayout(new java.awt.BorderLayout());

        pnl_mail_2_message_text1.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_2_message_text1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_2_message_text1.setText("[Header.Message]");
        pnl_mail_2_message1.add(pnl_mail_2_message_text1, java.awt.BorderLayout.CENTER);

        pnl_mail_12.add(pnl_mail_2_message1, java.awt.BorderLayout.CENTER);

        pnl_mail_list1.add(pnl_mail_12);

        pnl_mail_13.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_13.setLayout(new java.awt.BorderLayout());

        pnl_mail_3_author1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_3_author1.setPreferredSize(new java.awt.Dimension(100, 175));
        pnl_mail_3_author1.setLayout(new java.awt.BorderLayout());

        pnl_mail_3_author_text1.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_3_author_text1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_3_author_text1.setText("[Author]");
        pnl_mail_3_author1.add(pnl_mail_3_author_text1, java.awt.BorderLayout.CENTER);

        pnl_mail_13.add(pnl_mail_3_author1, java.awt.BorderLayout.LINE_START);

        pnl_mail_3_time1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_3_time1.setPreferredSize(new java.awt.Dimension(100, 175));
        pnl_mail_3_time1.setLayout(new java.awt.BorderLayout());

        pnl_mail_3_time_text1.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_3_time_text1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_3_time_text1.setText("[Time]");
        pnl_mail_3_time1.add(pnl_mail_3_time_text1, java.awt.BorderLayout.CENTER);

        pnl_mail_13.add(pnl_mail_3_time1, java.awt.BorderLayout.LINE_END);

        pnl_mail_3_message1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_3_message1.setLayout(new java.awt.BorderLayout());

        pnl_mail_3_message_text1.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_3_message_text1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_3_message_text1.setText("[Header.Message]");
        pnl_mail_3_message1.add(pnl_mail_3_message_text1, java.awt.BorderLayout.CENTER);

        pnl_mail_13.add(pnl_mail_3_message1, java.awt.BorderLayout.CENTER);

        pnl_mail_list1.add(pnl_mail_13);

        pnl_mail_14.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_14.setLayout(new java.awt.BorderLayout());

        pnl_mail_4_time1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_4_time1.setPreferredSize(new java.awt.Dimension(100, 175));
        pnl_mail_4_time1.setLayout(new java.awt.BorderLayout());

        pnl_mail_4_time_text1.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_4_time_text1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_4_time_text1.setText("[Author]");
        pnl_mail_4_time1.add(pnl_mail_4_time_text1, java.awt.BorderLayout.CENTER);

        pnl_mail_14.add(pnl_mail_4_time1, java.awt.BorderLayout.LINE_START);

        pnl_mail_4_author1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_4_author1.setPreferredSize(new java.awt.Dimension(100, 175));
        pnl_mail_4_author1.setLayout(new java.awt.BorderLayout());

        pnl_mail_4_author_text1.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_4_author_text1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_4_author_text1.setText("[Time]");
        pnl_mail_4_author1.add(pnl_mail_4_author_text1, java.awt.BorderLayout.CENTER);

        pnl_mail_14.add(pnl_mail_4_author1, java.awt.BorderLayout.LINE_END);

        pnl_mail_4_message1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_4_message1.setLayout(new java.awt.BorderLayout());

        pnl_mail_4_message_text1.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_4_message_text1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_4_message_text1.setText("[Header.Message]");
        pnl_mail_4_message1.add(pnl_mail_4_message_text1, java.awt.BorderLayout.CENTER);

        pnl_mail_14.add(pnl_mail_4_message1, java.awt.BorderLayout.CENTER);

        pnl_mail_list1.add(pnl_mail_14);

        pnl_mail_15.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_15.setLayout(new java.awt.BorderLayout());

        pnl_mail_5_author1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_5_author1.setPreferredSize(new java.awt.Dimension(100, 175));
        pnl_mail_5_author1.setLayout(new java.awt.BorderLayout());

        pnl_mail_5_author_text1.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_5_author_text1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_5_author_text1.setText("[Author]");
        pnl_mail_5_author1.add(pnl_mail_5_author_text1, java.awt.BorderLayout.CENTER);

        pnl_mail_15.add(pnl_mail_5_author1, java.awt.BorderLayout.LINE_START);

        pnl_mail_5_time1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_5_time1.setPreferredSize(new java.awt.Dimension(100, 175));
        pnl_mail_5_time1.setLayout(new java.awt.BorderLayout());

        pnl_mail_5_time_text1.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_5_time_text1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_5_time_text1.setText("[Time]");
        pnl_mail_5_time1.add(pnl_mail_5_time_text1, java.awt.BorderLayout.CENTER);

        pnl_mail_15.add(pnl_mail_5_time1, java.awt.BorderLayout.LINE_END);

        pnl_mail_5_message1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_5_message1.setLayout(new java.awt.BorderLayout());

        pnl_mail_5_message_text1.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_5_message_text1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_5_message_text1.setText("[Header.Message]");
        pnl_mail_5_message1.add(pnl_mail_5_message_text1, java.awt.BorderLayout.CENTER);

        pnl_mail_15.add(pnl_mail_5_message1, java.awt.BorderLayout.CENTER);

        pnl_mail_list1.add(pnl_mail_15);

        pnl_mail_16.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_16.setLayout(new java.awt.BorderLayout());

        pnl_mail_6_author1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_6_author1.setPreferredSize(new java.awt.Dimension(100, 175));
        pnl_mail_6_author1.setLayout(new java.awt.BorderLayout());

        pnl_mail_6_author_text1.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_6_author_text1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_6_author_text1.setText("[Author]");
        pnl_mail_6_author1.add(pnl_mail_6_author_text1, java.awt.BorderLayout.CENTER);

        pnl_mail_16.add(pnl_mail_6_author1, java.awt.BorderLayout.LINE_START);

        pnl_mail_6_time1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_6_time1.setPreferredSize(new java.awt.Dimension(100, 175));
        pnl_mail_6_time1.setLayout(new java.awt.BorderLayout());

        pnl_mail_6_time_text1.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_6_time_text1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_6_time_text1.setText("[Time]");
        pnl_mail_6_time1.add(pnl_mail_6_time_text1, java.awt.BorderLayout.CENTER);

        pnl_mail_16.add(pnl_mail_6_time1, java.awt.BorderLayout.LINE_END);

        pnl_mail_6_message1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_6_message1.setLayout(new java.awt.BorderLayout());

        pnl_mail_6_message_text1.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_6_message_text1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_6_message_text1.setText("[Header.Message]");
        pnl_mail_6_message1.add(pnl_mail_6_message_text1, java.awt.BorderLayout.CENTER);

        pnl_mail_16.add(pnl_mail_6_message1, java.awt.BorderLayout.CENTER);

        pnl_mail_list1.add(pnl_mail_16);

        pnl_mail_17.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_17.setLayout(new java.awt.BorderLayout());

        pnl_mail_7_author1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_7_author1.setPreferredSize(new java.awt.Dimension(100, 175));
        pnl_mail_7_author1.setLayout(new java.awt.BorderLayout());

        pnl_mail_7_author_text1.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_7_author_text1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_7_author_text1.setText("[Author]");
        pnl_mail_7_author1.add(pnl_mail_7_author_text1, java.awt.BorderLayout.CENTER);

        pnl_mail_17.add(pnl_mail_7_author1, java.awt.BorderLayout.LINE_START);

        pnl_mail_7_time1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_7_time1.setPreferredSize(new java.awt.Dimension(100, 175));
        pnl_mail_7_time1.setLayout(new java.awt.BorderLayout());

        pnl_mail_7_time_text1.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_7_time_text1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_7_time_text1.setText("[Time]");
        pnl_mail_7_time1.add(pnl_mail_7_time_text1, java.awt.BorderLayout.CENTER);

        pnl_mail_17.add(pnl_mail_7_time1, java.awt.BorderLayout.LINE_END);

        pnl_mail_7_message1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_7_message1.setLayout(new java.awt.BorderLayout());

        pnl_mail_7_message_text1.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_7_message_text1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_7_message_text1.setText("[Header.Message]");
        pnl_mail_7_message1.add(pnl_mail_7_message_text1, java.awt.BorderLayout.CENTER);

        pnl_mail_17.add(pnl_mail_7_message1, java.awt.BorderLayout.CENTER);

        pnl_mail_list1.add(pnl_mail_17);

        pnl_mail_18.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_18.setLayout(new java.awt.BorderLayout());

        pnl_mail_8_author1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_8_author1.setPreferredSize(new java.awt.Dimension(100, 175));
        pnl_mail_8_author1.setLayout(new java.awt.BorderLayout());

        pnl_mail_8_author_text1.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_8_author_text1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_8_author_text1.setText("[Author]");
        pnl_mail_8_author1.add(pnl_mail_8_author_text1, java.awt.BorderLayout.CENTER);

        pnl_mail_18.add(pnl_mail_8_author1, java.awt.BorderLayout.LINE_START);

        pnl_mail_8_time1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_8_time1.setPreferredSize(new java.awt.Dimension(100, 175));
        pnl_mail_8_time1.setLayout(new java.awt.BorderLayout());

        pnl_mail_8_time_text1.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_8_time_text1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_8_time_text1.setText("[Time]");
        pnl_mail_8_time1.add(pnl_mail_8_time_text1, java.awt.BorderLayout.CENTER);

        pnl_mail_18.add(pnl_mail_8_time1, java.awt.BorderLayout.LINE_END);

        pnl_mail_8_message1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_8_message1.setLayout(new java.awt.BorderLayout());

        pnl_mail_8_message_text1.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_8_message_text1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_8_message_text1.setText("[Header.Message]");
        pnl_mail_8_message1.add(pnl_mail_8_message_text1, java.awt.BorderLayout.CENTER);

        pnl_mail_18.add(pnl_mail_8_message1, java.awt.BorderLayout.CENTER);

        pnl_mail_list1.add(pnl_mail_18);

        pnl_mail_19.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_19.setLayout(new java.awt.BorderLayout());

        pnl_mail_9_author1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_9_author1.setPreferredSize(new java.awt.Dimension(100, 175));
        pnl_mail_9_author1.setLayout(new java.awt.BorderLayout());

        pnl_mail_9_author_text1.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_9_author_text1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_9_author_text1.setText("[Author]");
        pnl_mail_9_author1.add(pnl_mail_9_author_text1, java.awt.BorderLayout.CENTER);

        pnl_mail_19.add(pnl_mail_9_author1, java.awt.BorderLayout.LINE_START);

        pnl_mail_9_time1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_9_time1.setPreferredSize(new java.awt.Dimension(100, 175));
        pnl_mail_9_time1.setLayout(new java.awt.BorderLayout());

        pnl_mail_9_time_text1.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_9_time_text1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_9_time_text1.setText("[Time]");
        pnl_mail_9_time1.add(pnl_mail_9_time_text1, java.awt.BorderLayout.CENTER);

        pnl_mail_19.add(pnl_mail_9_time1, java.awt.BorderLayout.LINE_END);

        pnl_mail_9_message1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_9_message1.setLayout(new java.awt.BorderLayout());

        pnl_mail_9_message_text1.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_9_message_text1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_9_message_text1.setText("[Header.Message]");
        pnl_mail_9_message1.add(pnl_mail_9_message_text1, java.awt.BorderLayout.CENTER);

        pnl_mail_19.add(pnl_mail_9_message1, java.awt.BorderLayout.CENTER);

        pnl_mail_list1.add(pnl_mail_19);

        pnl_mail_20.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_20.setLayout(new java.awt.BorderLayout());

        pnl_mail_10_author1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_10_author1.setPreferredSize(new java.awt.Dimension(100, 175));
        pnl_mail_10_author1.setLayout(new java.awt.BorderLayout());

        pnl_mail_10_author_text1.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_10_author_text1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_10_author_text1.setText("[Author]");
        pnl_mail_10_author1.add(pnl_mail_10_author_text1, java.awt.BorderLayout.CENTER);

        pnl_mail_20.add(pnl_mail_10_author1, java.awt.BorderLayout.LINE_START);

        pnl_mail_10_time1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_10_time1.setPreferredSize(new java.awt.Dimension(100, 175));
        pnl_mail_10_time1.setLayout(new java.awt.BorderLayout());

        pnl_mail_10_time_text1.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_10_time_text1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_10_time_text1.setText("[Time]");
        pnl_mail_10_time1.add(pnl_mail_10_time_text1, java.awt.BorderLayout.CENTER);

        pnl_mail_20.add(pnl_mail_10_time1, java.awt.BorderLayout.LINE_END);

        pnl_mail_10_message1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_10_message1.setLayout(new java.awt.BorderLayout());

        pnl_mail_10_message_text1.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_10_message_text1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_mail_10_message_text1.setText("[Header.Message]");
        pnl_mail_10_message1.add(pnl_mail_10_message_text1, java.awt.BorderLayout.CENTER);

        pnl_mail_20.add(pnl_mail_10_message1, java.awt.BorderLayout.CENTER);

        pnl_mail_list1.add(pnl_mail_20);

        pnl_main_mails1.add(pnl_mail_list1, java.awt.BorderLayout.LINE_START);

        pnl_giden_splitpane.setLeftComponent(pnl_main_mails1);

        pnl_main_details1.setBackground(new java.awt.Color(37, 40, 44));
        pnl_main_details1.setLayout(new java.awt.BorderLayout());

        pnl_mail_header1.setPreferredSize(new java.awt.Dimension(574, 60));
        pnl_mail_header1.setLayout(new java.awt.BorderLayout());

        mail_author1.setBackground(new java.awt.Color(45, 48, 53));
        mail_author1.setLayout(new java.awt.BorderLayout());

        mail_author_text1.setBackground(new java.awt.Color(45, 48, 53));
        mail_author_text1.setForeground(new java.awt.Color(255, 255, 255));
        mail_author_text1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mail_author_text1.setText("[Author]");
        mail_author_text1.setPreferredSize(new java.awt.Dimension(89, 15));
        mail_author1.add(mail_author_text1, java.awt.BorderLayout.CENTER);

        pnl_mail_header1.add(mail_author1, java.awt.BorderLayout.LINE_START);

        mail_buttons1.setBackground(new java.awt.Color(45, 48, 53));
        mail_buttons1.setLayout(new java.awt.BorderLayout());

        btn_mail_4.setBackground(new java.awt.Color(45, 48, 53));
        btn_mail_4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_mail_4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/left-arrow.png"))); // NOI18N
        btn_mail_4.setPreferredSize(new java.awt.Dimension(40, 15));
        mail_buttons1.add(btn_mail_4, java.awt.BorderLayout.LINE_START);

        btn_mail_5.setBackground(new java.awt.Color(45, 48, 53));
        btn_mail_5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_mail_5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/right-arrow.png"))); // NOI18N
        btn_mail_5.setPreferredSize(new java.awt.Dimension(40, 15));
        mail_buttons1.add(btn_mail_5, java.awt.BorderLayout.LINE_END);

        btn_mail_6.setBackground(new java.awt.Color(45, 48, 53));
        btn_mail_6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_mail_6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/mail_menu_icon_cop.png"))); // NOI18N
        btn_mail_6.setPreferredSize(new java.awt.Dimension(40, 15));
        mail_buttons1.add(btn_mail_6, java.awt.BorderLayout.CENTER);

        pnl_mail_header1.add(mail_buttons1, java.awt.BorderLayout.LINE_END);

        mail_time1.setBackground(new java.awt.Color(45, 48, 53));
        mail_time1.setLayout(new java.awt.BorderLayout());

        mail_time_text1.setBackground(new java.awt.Color(45, 48, 53));
        mail_time_text1.setForeground(new java.awt.Color(255, 255, 255));
        mail_time_text1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        mail_time_text1.setText("[Time]");
        mail_time1.add(mail_time_text1, java.awt.BorderLayout.CENTER);

        pnl_mail_header1.add(mail_time1, java.awt.BorderLayout.CENTER);

        pnl_main_details1.add(pnl_mail_header1, java.awt.BorderLayout.PAGE_START);

        pnl_mail_body1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_body1.setLayout(new java.awt.BorderLayout());

        mail_body_split1.setBackground(new java.awt.Color(45, 48, 53));
        mail_body_split1.setDividerLocation(595);
        mail_body_split1.setDividerSize(3);
        mail_body_split1.setForeground(new java.awt.Color(45, 48, 53));
        mail_body_split1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        mail_body_message1.setBackground(new java.awt.Color(45, 48, 53));
        mail_body_message1.setForeground(new java.awt.Color(45, 48, 53));
        mail_body_message1.setLayout(new java.awt.BorderLayout());

        pnl_mail_detail_header1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_detail_header1.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_detail_header1.setPreferredSize(new java.awt.Dimension(60, 60));
        pnl_mail_detail_header1.setLayout(new java.awt.BorderLayout());

        pnl_mail_detail_header_scrollpane1.setBorder(null);
        pnl_mail_detail_header_scrollpane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        pnl_mail_detail_header_scrollpane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        pnl_mail_detail_header_text1.setEditable(false);
        pnl_mail_detail_header_text1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_detail_header_text1.setColumns(5);
        pnl_mail_detail_header_text1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        pnl_mail_detail_header_text1.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_detail_header_text1.setLineWrap(true);
        pnl_mail_detail_header_text1.setRows(2);
        pnl_mail_detail_header_text1.setTabSize(2);
        pnl_mail_detail_header_text1.setWrapStyleWord(true);
        pnl_mail_detail_header_text1.setCaretColor(new java.awt.Color(255, 255, 255));
        pnl_mail_detail_header_scrollpane1.setViewportView(pnl_mail_detail_header_text1);

        pnl_mail_detail_header1.add(pnl_mail_detail_header_scrollpane1, java.awt.BorderLayout.CENTER);

        mail_body_message1.add(pnl_mail_detail_header1, java.awt.BorderLayout.PAGE_START);

        pnl_mail_detail_body1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_detail_body1.setLayout(new java.awt.BorderLayout());

        pnl_mail_detail_attachment1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_detail_attachment1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3), "Email Eki", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 11), new java.awt.Color(165, 165, 172))); // NOI18N
        pnl_mail_detail_attachment1.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_detail_attachment1.setPreferredSize(new java.awt.Dimension(60, 60));
        pnl_mail_detail_attachment1.setLayout(new java.awt.BorderLayout());
        pnl_mail_detail_body1.add(pnl_mail_detail_attachment1, java.awt.BorderLayout.PAGE_END);

        pnl_mail_body_scrollpane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2));

        pnl_mail_body_text1.setEditable(false);
        pnl_mail_body_text1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_body_text1.setColumns(5);
        pnl_mail_body_text1.setForeground(new java.awt.Color(165, 165, 172));
        pnl_mail_body_text1.setLineWrap(true);
        pnl_mail_body_text1.setRows(2);
        pnl_mail_body_text1.setTabSize(2);
        pnl_mail_body_text1.setWrapStyleWord(true);
        pnl_mail_body_text1.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2));
        pnl_mail_body_scrollpane1.setViewportView(pnl_mail_body_text1);

        pnl_mail_detail_body1.add(pnl_mail_body_scrollpane1, java.awt.BorderLayout.CENTER);

        mail_body_message1.add(pnl_mail_detail_body1, java.awt.BorderLayout.CENTER);

        mail_body_split1.setTopComponent(mail_body_message1);

        mail_body_reply1.setBackground(new java.awt.Color(45, 48, 53));
        mail_body_reply1.setForeground(new java.awt.Color(45, 48, 53));
        mail_body_reply1.setPreferredSize(new java.awt.Dimension(100, 100));
        mail_body_reply1.setLayout(new java.awt.BorderLayout());

        pnl_mail_body_reply_btn1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_body_reply_btn1.setPreferredSize(new java.awt.Dimension(40, 40));
        pnl_mail_body_reply_btn1.setLayout(new java.awt.BorderLayout());

        btn_reply1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_reply1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/send.png"))); // NOI18N
        btn_reply1.setPreferredSize(new java.awt.Dimension(30, 30));
        pnl_mail_body_reply_btn1.add(btn_reply1, java.awt.BorderLayout.CENTER);

        mail_body_reply1.add(pnl_mail_body_reply_btn1, java.awt.BorderLayout.LINE_END);

        pnl_mail_body_reply1.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_body_reply1.setForeground(new java.awt.Color(12, 12, 12));
        pnl_mail_body_reply1.setPreferredSize(new java.awt.Dimension(300, 124));
        pnl_mail_body_reply1.setLayout(new java.awt.BorderLayout());

        pnl_mail_body_reply_scrollpane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2));
        pnl_mail_body_reply_scrollpane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        pnl_mail_body_reply_scrollpane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        pnl_mail_body_reply_scrollpane1.setAutoscrolls(true);

        pnl_mail_body_reply_text1.setBackground(new java.awt.Color(44, 48, 53));
        pnl_mail_body_reply_text1.setColumns(50);
        pnl_mail_body_reply_text1.setFont(new java.awt.Font("Bahnschrift", 0, 11)); // NOI18N
        pnl_mail_body_reply_text1.setForeground(new java.awt.Color(255, 255, 255));
        pnl_mail_body_reply_text1.setRows(5);
        pnl_mail_body_reply_text1.setText("Cevap göndermek için tıklayın.\n");
        pnl_mail_body_reply_text1.setToolTipText("");
        pnl_mail_body_reply_text1.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        pnl_mail_body_reply_text1.setCaretColor(new java.awt.Color(255, 255, 255));
        pnl_mail_body_reply_text1.setMinimumSize(new java.awt.Dimension(1, 1));
        pnl_mail_body_reply_text1.setSelectionColor(new java.awt.Color(255, 255, 255));
        pnl_mail_body_reply_scrollpane1.setViewportView(pnl_mail_body_reply_text1);

        pnl_mail_body_reply1.add(pnl_mail_body_reply_scrollpane1, java.awt.BorderLayout.CENTER);

        mail_body_reply1.add(pnl_mail_body_reply1, java.awt.BorderLayout.CENTER);

        mail_body_split1.setRightComponent(mail_body_reply1);

        pnl_mail_body1.add(mail_body_split1, java.awt.BorderLayout.CENTER);

        pnl_main_details1.add(pnl_mail_body1, java.awt.BorderLayout.CENTER);

        pnl_giden_splitpane.setRightComponent(pnl_main_details1);

        pnl_main_mail.add(pnl_giden_splitpane, "gidencard");

        pnl_main.add(pnl_main_mail, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnl_main, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void hoverColor(Component jpanel, Color color) {
        jpanel.setBackground(color);
    }

    private void btn_maximizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_maximizeMouseClicked
        maximize();
    }//GEN-LAST:event_btn_maximizeMouseClicked

    private void btn_closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_closeMouseClicked
        System.exit(0);
    }//GEN-LAST:event_btn_closeMouseClicked

    private void btn_minimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_minimizeMouseClicked
        this.setState(ICONIFIED);
    }//GEN-LAST:event_btn_minimizeMouseClicked

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
            java.util.logging.Logger.getLogger(FrmMailYedek2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmMailYedek2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmMailYedek2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmMailYedek2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmMailYedek2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_close;
    private javax.swing.JLabel btn_mail_1;
    private javax.swing.JLabel btn_mail_2;
    private javax.swing.JLabel btn_mail_3;
    private javax.swing.JLabel btn_mail_4;
    private javax.swing.JLabel btn_mail_5;
    private javax.swing.JLabel btn_mail_6;
    private javax.swing.JButton btn_maximize;
    private javax.swing.JButton btn_minimize;
    private javax.swing.JLabel btn_reply;
    private javax.swing.JLabel btn_reply1;
    private javax.swing.JButton btn_yeni_mail;
    private javax.swing.JLabel cop_icon;
    private javax.swing.JLabel cop_text;
    private javax.swing.JLabel email_text;
    private javax.swing.JLabel gelen_icon;
    private javax.swing.JLabel gelen_text;
    private javax.swing.JLabel giden_icon;
    private javax.swing.JLabel giden_text;
    private javax.swing.JLabel lbl_slider_number;
    private javax.swing.JLabel lbl_slider_number1;
    private javax.swing.JPanel mail_author;
    private javax.swing.JPanel mail_author1;
    private javax.swing.JLabel mail_author_text;
    private javax.swing.JLabel mail_author_text1;
    private javax.swing.JPanel mail_body_message;
    private javax.swing.JPanel mail_body_message1;
    private javax.swing.JPanel mail_body_reply;
    private javax.swing.JPanel mail_body_reply1;
    private javax.swing.JSplitPane mail_body_split;
    private javax.swing.JSplitPane mail_body_split1;
    private javax.swing.JPanel mail_buttons;
    private javax.swing.JPanel mail_buttons1;
    private javax.swing.JPanel mail_time;
    private javax.swing.JPanel mail_time1;
    private javax.swing.JLabel mail_time_text;
    private javax.swing.JLabel mail_time_text1;
    private javax.swing.JPanel pnl_btnclose;
    private javax.swing.JPanel pnl_btnmaximize;
    private javax.swing.JPanel pnl_btnminimize;
    private javax.swing.JPanel pnl_cop_icon;
    private javax.swing.JPanel pnl_cop_text;
    private javax.swing.JPanel pnl_email_text;
    private javax.swing.JPanel pnl_gelen_icon;
    private javax.swing.JSplitPane pnl_gelen_splitpane;
    private javax.swing.JPanel pnl_gelen_text;
    private javax.swing.JPanel pnl_giden_icon;
    private javax.swing.JSplitPane pnl_giden_splitpane;
    private javax.swing.JPanel pnl_giden_text;
    private javax.swing.JPanel pnl_mail_1;
    private javax.swing.JPanel pnl_mail_10;
    private javax.swing.JPanel pnl_mail_10_author;
    private javax.swing.JPanel pnl_mail_10_author1;
    private javax.swing.JLabel pnl_mail_10_author_text;
    private javax.swing.JLabel pnl_mail_10_author_text1;
    private javax.swing.JPanel pnl_mail_10_message;
    private javax.swing.JPanel pnl_mail_10_message1;
    private javax.swing.JLabel pnl_mail_10_message_text;
    private javax.swing.JLabel pnl_mail_10_message_text1;
    private javax.swing.JPanel pnl_mail_10_time;
    private javax.swing.JPanel pnl_mail_10_time1;
    private javax.swing.JLabel pnl_mail_10_time_text;
    private javax.swing.JLabel pnl_mail_10_time_text1;
    private javax.swing.JPanel pnl_mail_11;
    private javax.swing.JPanel pnl_mail_12;
    private javax.swing.JPanel pnl_mail_13;
    private javax.swing.JPanel pnl_mail_14;
    private javax.swing.JPanel pnl_mail_15;
    private javax.swing.JPanel pnl_mail_16;
    private javax.swing.JPanel pnl_mail_17;
    private javax.swing.JPanel pnl_mail_18;
    private javax.swing.JPanel pnl_mail_19;
    private javax.swing.JPanel pnl_mail_1_author;
    private javax.swing.JPanel pnl_mail_1_author1;
    private javax.swing.JLabel pnl_mail_1_author_text;
    private javax.swing.JLabel pnl_mail_1_author_text1;
    private javax.swing.JPanel pnl_mail_1_message;
    private javax.swing.JPanel pnl_mail_1_message1;
    private javax.swing.JLabel pnl_mail_1_message_text;
    private javax.swing.JLabel pnl_mail_1_message_text1;
    private javax.swing.JPanel pnl_mail_1_time;
    private javax.swing.JPanel pnl_mail_1_time1;
    private javax.swing.JLabel pnl_mail_1_time_text;
    private javax.swing.JLabel pnl_mail_1_time_text1;
    private javax.swing.JPanel pnl_mail_2;
    private javax.swing.JPanel pnl_mail_20;
    private javax.swing.JPanel pnl_mail_2_author;
    private javax.swing.JPanel pnl_mail_2_author1;
    private javax.swing.JLabel pnl_mail_2_author_text;
    private javax.swing.JLabel pnl_mail_2_author_text1;
    private javax.swing.JPanel pnl_mail_2_message;
    private javax.swing.JPanel pnl_mail_2_message1;
    private javax.swing.JLabel pnl_mail_2_message_text;
    private javax.swing.JLabel pnl_mail_2_message_text1;
    private javax.swing.JPanel pnl_mail_2_time;
    private javax.swing.JPanel pnl_mail_2_time1;
    private javax.swing.JLabel pnl_mail_2_time_text;
    private javax.swing.JLabel pnl_mail_2_time_text1;
    private javax.swing.JPanel pnl_mail_3;
    private javax.swing.JPanel pnl_mail_3_author;
    private javax.swing.JPanel pnl_mail_3_author1;
    private javax.swing.JLabel pnl_mail_3_author_text;
    private javax.swing.JLabel pnl_mail_3_author_text1;
    private javax.swing.JPanel pnl_mail_3_message;
    private javax.swing.JPanel pnl_mail_3_message1;
    private javax.swing.JLabel pnl_mail_3_message_text;
    private javax.swing.JLabel pnl_mail_3_message_text1;
    private javax.swing.JPanel pnl_mail_3_time;
    private javax.swing.JPanel pnl_mail_3_time1;
    private javax.swing.JLabel pnl_mail_3_time_text;
    private javax.swing.JLabel pnl_mail_3_time_text1;
    private javax.swing.JPanel pnl_mail_4;
    private javax.swing.JPanel pnl_mail_4_author;
    private javax.swing.JPanel pnl_mail_4_author1;
    private javax.swing.JLabel pnl_mail_4_author_text;
    private javax.swing.JLabel pnl_mail_4_author_text1;
    private javax.swing.JPanel pnl_mail_4_message;
    private javax.swing.JPanel pnl_mail_4_message1;
    private javax.swing.JLabel pnl_mail_4_message_text;
    private javax.swing.JLabel pnl_mail_4_message_text1;
    private javax.swing.JPanel pnl_mail_4_time;
    private javax.swing.JPanel pnl_mail_4_time1;
    private javax.swing.JLabel pnl_mail_4_time_text;
    private javax.swing.JLabel pnl_mail_4_time_text1;
    private javax.swing.JPanel pnl_mail_5;
    private javax.swing.JPanel pnl_mail_5_author;
    private javax.swing.JPanel pnl_mail_5_author1;
    private javax.swing.JLabel pnl_mail_5_author_text;
    private javax.swing.JLabel pnl_mail_5_author_text1;
    private javax.swing.JPanel pnl_mail_5_message;
    private javax.swing.JPanel pnl_mail_5_message1;
    private javax.swing.JLabel pnl_mail_5_message_text;
    private javax.swing.JLabel pnl_mail_5_message_text1;
    private javax.swing.JPanel pnl_mail_5_time;
    private javax.swing.JPanel pnl_mail_5_time1;
    private javax.swing.JLabel pnl_mail_5_time_text;
    private javax.swing.JLabel pnl_mail_5_time_text1;
    private javax.swing.JPanel pnl_mail_6;
    private javax.swing.JPanel pnl_mail_6_author;
    private javax.swing.JPanel pnl_mail_6_author1;
    private javax.swing.JLabel pnl_mail_6_author_text;
    private javax.swing.JLabel pnl_mail_6_author_text1;
    private javax.swing.JPanel pnl_mail_6_message;
    private javax.swing.JPanel pnl_mail_6_message1;
    private javax.swing.JLabel pnl_mail_6_message_text;
    private javax.swing.JLabel pnl_mail_6_message_text1;
    private javax.swing.JPanel pnl_mail_6_time;
    private javax.swing.JPanel pnl_mail_6_time1;
    private javax.swing.JLabel pnl_mail_6_time_text;
    private javax.swing.JLabel pnl_mail_6_time_text1;
    private javax.swing.JPanel pnl_mail_7;
    private javax.swing.JPanel pnl_mail_7_author;
    private javax.swing.JPanel pnl_mail_7_author1;
    private javax.swing.JLabel pnl_mail_7_author_text;
    private javax.swing.JLabel pnl_mail_7_author_text1;
    private javax.swing.JPanel pnl_mail_7_message;
    private javax.swing.JPanel pnl_mail_7_message1;
    private javax.swing.JLabel pnl_mail_7_message_text;
    private javax.swing.JLabel pnl_mail_7_message_text1;
    private javax.swing.JPanel pnl_mail_7_time;
    private javax.swing.JPanel pnl_mail_7_time1;
    private javax.swing.JLabel pnl_mail_7_time_text;
    private javax.swing.JLabel pnl_mail_7_time_text1;
    private javax.swing.JPanel pnl_mail_8;
    private javax.swing.JPanel pnl_mail_8_author;
    private javax.swing.JPanel pnl_mail_8_author1;
    private javax.swing.JLabel pnl_mail_8_author_text;
    private javax.swing.JLabel pnl_mail_8_author_text1;
    private javax.swing.JPanel pnl_mail_8_message;
    private javax.swing.JPanel pnl_mail_8_message1;
    private javax.swing.JLabel pnl_mail_8_message_text;
    private javax.swing.JLabel pnl_mail_8_message_text1;
    private javax.swing.JPanel pnl_mail_8_time;
    private javax.swing.JPanel pnl_mail_8_time1;
    private javax.swing.JLabel pnl_mail_8_time_text;
    private javax.swing.JLabel pnl_mail_8_time_text1;
    private javax.swing.JPanel pnl_mail_9;
    private javax.swing.JPanel pnl_mail_9_author;
    private javax.swing.JPanel pnl_mail_9_author1;
    private javax.swing.JLabel pnl_mail_9_author_text;
    private javax.swing.JLabel pnl_mail_9_author_text1;
    private javax.swing.JPanel pnl_mail_9_message;
    private javax.swing.JPanel pnl_mail_9_message1;
    private javax.swing.JLabel pnl_mail_9_message_text;
    private javax.swing.JLabel pnl_mail_9_message_text1;
    private javax.swing.JPanel pnl_mail_9_time;
    private javax.swing.JPanel pnl_mail_9_time1;
    private javax.swing.JLabel pnl_mail_9_time_text;
    private javax.swing.JLabel pnl_mail_9_time_text1;
    private javax.swing.JPanel pnl_mail_body;
    private javax.swing.JPanel pnl_mail_body1;
    private javax.swing.JPanel pnl_mail_body_reply;
    private javax.swing.JPanel pnl_mail_body_reply1;
    private javax.swing.JPanel pnl_mail_body_reply_btn;
    private javax.swing.JPanel pnl_mail_body_reply_btn1;
    private javax.swing.JScrollPane pnl_mail_body_reply_scrollpane;
    private javax.swing.JScrollPane pnl_mail_body_reply_scrollpane1;
    private javax.swing.JTextArea pnl_mail_body_reply_text;
    private javax.swing.JTextArea pnl_mail_body_reply_text1;
    private javax.swing.JScrollPane pnl_mail_body_scrollpane;
    private javax.swing.JScrollPane pnl_mail_body_scrollpane1;
    private javax.swing.JTextArea pnl_mail_body_text;
    private javax.swing.JTextArea pnl_mail_body_text1;
    private javax.swing.JPanel pnl_mail_detail_attachment;
    private javax.swing.JPanel pnl_mail_detail_attachment1;
    private javax.swing.JPanel pnl_mail_detail_body;
    private javax.swing.JPanel pnl_mail_detail_body1;
    private javax.swing.JPanel pnl_mail_detail_header;
    private javax.swing.JPanel pnl_mail_detail_header1;
    private javax.swing.JScrollPane pnl_mail_detail_header_scrollpane;
    private javax.swing.JScrollPane pnl_mail_detail_header_scrollpane1;
    private javax.swing.JTextArea pnl_mail_detail_header_text;
    private javax.swing.JTextArea pnl_mail_detail_header_text1;
    private javax.swing.JPanel pnl_mail_header;
    private javax.swing.JPanel pnl_mail_header1;
    private javax.swing.JPanel pnl_mail_list;
    private javax.swing.JPanel pnl_mail_list1;
    private javax.swing.JPanel pnl_mail_slider;
    private javax.swing.JPanel pnl_mail_slider1;
    private javax.swing.JPanel pnl_main;
    private javax.swing.JPanel pnl_main_details;
    private javax.swing.JPanel pnl_main_details1;
    private javax.swing.JPanel pnl_main_mail;
    private javax.swing.JPanel pnl_main_mails;
    private javax.swing.JPanel pnl_main_mails1;
    private javax.swing.JPanel pnl_main_titlebar;
    private javax.swing.JPanel pnl_sidebar;
    private javax.swing.JPanel pnl_sidebar_cop;
    private javax.swing.JPanel pnl_sidebar_gelen;
    private javax.swing.JPanel pnl_sidebar_giden;
    private javax.swing.JPanel pnl_sidebar_menus;
    private javax.swing.JPanel pnl_sidebar_taslak;
    private javax.swing.JPanel pnl_sidebar_text;
    private javax.swing.JPanel pnl_slider_left;
    private javax.swing.JPanel pnl_slider_left1;
    private javax.swing.JLabel pnl_slider_left_arrow;
    private javax.swing.JLabel pnl_slider_left_arrow1;
    private javax.swing.JPanel pnl_slider_middle;
    private javax.swing.JPanel pnl_slider_middle1;
    private javax.swing.JPanel pnl_slider_right;
    private javax.swing.JPanel pnl_slider_right1;
    private javax.swing.JLabel pnl_slider_right_arrow;
    private javax.swing.JLabel pnl_slider_right_arrow1;
    private javax.swing.JPanel pnl_taslak_icon;
    private javax.swing.JPanel pnl_taslak_text;
    private javax.swing.JPanel pnl_titlebuttons;
    private javax.swing.JLabel taslak_icon;
    private javax.swing.JLabel taslak_text;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent evt) {
        if (evt.getSource() == pnl_slider_right_arrow) {
            pageNumber++;
            lbl_slider_number.setText("" + pageNumber);
        }
        if (evt.getSource() == pnl_slider_left_arrow) {
            if (pageNumber != 1) {
                pageNumber--;
                lbl_slider_number.setText("" + pageNumber);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent evt) {

        // Mouse clicked effect on menu
        if (evt.getSource() == pnl_sidebar_gelen || evt.getSource() == pnl_sidebar_giden || evt.getSource() == pnl_sidebar_taslak || evt.getSource() == pnl_sidebar_cop) {
            menuButtonActiveEffect((JPanel) evt.getSource());
        }
    }

    @Override
    public void mouseReleased(MouseEvent evt) {
    }

    @Override
    public void mouseEntered(MouseEvent evt) {
        if (evt.getSource() == pnl_mail_1 || evt.getSource() == pnl_mail_2 || evt.getSource() == pnl_mail_3 || evt.getSource() == pnl_mail_4 || evt.getSource() == pnl_mail_5
                || evt.getSource() == pnl_mail_6 || evt.getSource() == pnl_mail_7 || evt.getSource() == pnl_mail_8 || evt.getSource() == pnl_mail_9 || evt.getSource() == pnl_mail_10) {
            JPanel panel = (JPanel) evt.getSource();
            Component panelComp[] = panel.getComponents();
            for (int y = 0; y < panelComp.length; y++) {
                hoverColor((Component) panelComp[y], new Color(56, 58, 71));
            }
        }

        if (evt.getSource() == btn_minimize || evt.getSource() == btn_maximize || evt.getSource() == btn_close) {
            JButton btn = (JButton) evt.getSource();
            hoverColor((JPanel) btn.getParent(), new Color(56, 58, 71));
        }

        if (evt.getSource() == pnl_sidebar_gelen || evt.getSource() == pnl_sidebar_giden || evt.getSource() == pnl_sidebar_taslak || evt.getSource() == pnl_sidebar_cop) {
            hoverColor((Component) evt.getSource(), new Color(56, 58, 71));
        }

        if (evt.getSource() == pnl_slider_left_arrow || evt.getSource() == pnl_slider_right_arrow) {
            JLabel label = (JLabel) evt.getSource();
            hoverColor((JPanel) label.getParent(), new Color(56, 58, 71));
            // Mail Page Operations
        }
    }

    @Override
    public void mouseExited(MouseEvent evt) {

        if (evt.getSource() == pnl_mail_1 || evt.getSource() == pnl_mail_2 || evt.getSource() == pnl_mail_3 || evt.getSource() == pnl_mail_4 || evt.getSource() == pnl_mail_5
                || evt.getSource() == pnl_mail_6 || evt.getSource() == pnl_mail_7 || evt.getSource() == pnl_mail_8 || evt.getSource() == pnl_mail_9 || evt.getSource() == pnl_mail_10) {
            JPanel panel = (JPanel) evt.getSource();
            Component panelComp[] = panel.getComponents();
            for (int y = 0; y < panelComp.length; y++) {
                hoverColor((Component) panelComp[y], new Color(45, 48, 53));
            }
        }

        if (evt.getSource() == btn_minimize || evt.getSource() == btn_maximize || evt.getSource() == btn_close) {
            JButton btn = (JButton) evt.getSource();
            hoverColor((JPanel) btn.getParent(), new Color(37, 40, 44));
        }

        if (evt.getSource() == pnl_sidebar_gelen || evt.getSource() == pnl_sidebar_giden || evt.getSource() == pnl_sidebar_taslak || evt.getSource() == pnl_sidebar_cop) {
            JPanel panel = (JPanel) evt.getSource();
            if (!getMenuButtonState(panel)) {
                hoverColor(panel, new Color(37, 40, 44));
            }
        }

        if (evt.getSource() == pnl_slider_left_arrow || evt.getSource() == pnl_slider_right_arrow) {
            JLabel label = (JLabel) evt.getSource();
            hoverColor((JPanel) label.getParent(), new Color(45, 48, 53));
            // Mail Page Operations
        }
    }

    @Override
    public void mouseDragged(MouseEvent evt) {

    }

    @Override
    public void mouseMoved(MouseEvent evt) {
    }
}

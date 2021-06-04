/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.views;

import com.vbteam.views.visualUtils.AttachmentTableModel;
import com.vbteam.views.visualUtils.MailTableModel;
import com.vbteam.views.visualUtils.TableRenderer;
import com.vbteam.views.visualUtils.UserTableModel;
import com.vbteam.models.*;

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
import com.vbteam.models.Mail;
import com.vbteam.utils.BCrypt;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author BatuPC
 */
public class FrmDashboard extends javax.swing.JFrame implements MouseListener {

    private AbstractTableModel mailTableModel, attachmentTableModel, userTableModel;
    static FrmDialog popupPanel;

    CardLayout mainLayout;
    boolean maximized = false;
    int selectedRow, pX, pY;
    User user;

    byte[] fileByteArray;
    String fileTypeString;

    public static JPanel mailPanelState;

    List<Mail> mailList = new ArrayList<>();
    List<Attachment> attachments = new ArrayList<>();
    List<User> users = new ArrayList<>();

    public FrmDashboard() {

        popupPanel = new FrmDialog();
        popupPanel.attachDialogToFrame(this);

        mailTableModel = (AbstractTableModel) new MailTableModel(mailList, "income");
        attachmentTableModel = (AbstractTableModel) new AttachmentTableModel(attachments);
        userTableModel = (AbstractTableModel) new UserTableModel(users);

        initGui();
        initComponents();
        customizeTable();
        moveTitlebar();

        centerLocationAndSetSize();
        setListeners();

        kullanici_bilgi_onaylama.setVisible(false);
        pnl_mail_detail_attachment.setVisible(false);

        mainLayout = (CardLayout) cardPanel.getLayout();
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
        this.setSize(1280, 720);
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Dimension screenSize = toolkit.getScreenSize();
        final int x = (screenSize.width - this.getWidth()) / 2;
        final int y = (screenSize.height - this.getHeight()) / 2;
        this.setLocation(x, y);
        
    }

    private void maximize() {
        if (!maximized) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            double width = screenSize.getWidth();
            double height = screenSize.getHeight();
            this.setLocation(0, 0);
            this.setSize((int) width, (int) height - 40);
        } else {
            centerLocationAndSetSize();
        }
        maximized = !maximized;
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

        mailTable.addMouseListener(this);
        attachmentTable.addMouseListener(this);

        kaydet.addMouseListener(this);

        gelenpanel.addMouseListener(this);
        gidenpanel.addMouseListener(this);
        taslakpanel.addMouseListener(this);
        coppanel.addMouseListener(this);
        Profile.addMouseListener(this);

        yenimailpanel.addMouseListener(this);

        pnl_mail_detail_attachment.addMouseListener(this);

        kullanici_bilgi_onaylama.addMouseListener(this);

        firstname_edit.addMouseListener(this);
        lastname_edit.addMouseListener(this);
        password_edit.addMouseListener(this);
        username_edit.addMouseListener(this);

        yonetici_paneli.addMouseListener(this);

        yonetici_user_onaylama.addMouseListener(this);

        yonetici_kullanici_detay.addMouseListener(this);
        yonetici_kullanici_sil.addMouseListener(this);
        yonetici_kullanici_ekle.addMouseListener(this);

        yonetici_user_ekle_geri.addMouseListener(this);
        yonetici_user_edit_geri.addMouseListener(this);

        yonetici_bilgi_onaylama.addMouseListener(this);

        btn_mail_3.addMouseListener(this);

    }

    public void customizeTable() {

        jScrollPane2.getViewport().setBackground(new Color(30, 30, 30));
        jScrollPane2.getViewport().setForeground(new Color(30, 30, 30));

        jScrollPane3.getViewport().setBackground(new Color(30, 30, 30));
        jScrollPane3.getViewport().setForeground(new Color(30, 30, 30));

        jScrollPane1.getViewport().setBackground(new Color(30, 30, 30));
        jScrollPane1.getViewport().setForeground(new Color(30, 30, 30));

        mailTable.setForeground(new Color(255, 215, 0));
        mailTable.setOpaque(false);
        mailTable.setBorder(null);

        userTable.setForeground(new Color(255, 215, 0));
        userTable.setOpaque(false);
        userTable.setBorder(null);

        attachmentTable.setForeground(new Color(255, 215, 0));
        attachmentTable.setOpaque(false);
        attachmentTable.setBorder(null);

        mailTable.setDefaultRenderer(Object.class, new TableRenderer());
        attachmentTable.setDefaultRenderer(Object.class, new TableRenderer());
        userTable.setDefaultRenderer(Object.class, new TableRenderer());
    }

    public void popupDialog(String type, String message, String actionType) {
        try {
            popupPanel = new FrmDialog();
            popupPanel.setIcon(type);
            popupPanel.setMessage(message);
            popupPanel.setActionType(actionType);
            popupPanel.attachDialogToFrame(this);
            popupPanel.setVisible(true);
        }catch(Exception ex){
            popupPanel.setVisible(false);
        }
    }
    
    public void disposeDialog(){
        popupPanel.dispose();
    }

    /*
    
        *********************************************************************
        *******************    Mail Methods    ******************************
        *********************************************************************
        
     */
    boolean isSent=false;
    public void sendMail() {
        isSent=false;
        Mail mail = new Mail();
        List<Header> headers = new ArrayList<Header>();

        if (!(mailgonder_field_baslik.getText().isEmpty() || mailgonder_field_icerik.getText().isEmpty() || mailgonder_field_kime.getText().isEmpty())) {
            mail.setSubject(mailgonder_field_baslik.getText());
            mail.setBody(mailgonder_field_icerik.getText());

            String[] userSplit = mailgonder_field_kime.getText().split(",");

            for (int i = 0; i < userSplit.length; i++) {
                Header header = new Header();
                header.setSenderUser(user.getUserName());
                header.setRecipientUser(userSplit[i]);
                header.setState(true);
                header.setType("Normal");
                headers.add(header);
            }

            if (attachments.size() > 0) {
                mail.setAttachmentState(true);
            } else {
                Attachment attachment = new Attachment();
                attachments.add(attachment);
                mail.setAttachmentState(false);
            }

            mail.setAttachments(attachments);
            mail.setHeaders(headers);
            mail.setCreateDate(new java.sql.Timestamp(new java.util.Date().getTime()));

            System.out.println("Sent Mail Debug : "+mail.getAttachments().size());            
            FrmAuth.conService.SendCommand(new Command("mail-send", null, user, mail));
            attachments.clear();
            clearMailSection();

        } else {
            popupDialog("error", "Boşlukları doldurunuz lütfen.", null);
            attachments.clear();
        }
    }

    public void getMails(String type) {
        try {
            if (type.equals("income")) {
                FrmAuth.conService.SendCommand(new Command("mail-income", null, user, null));
            }
            if (type.equals("outgoing")) {
                FrmAuth.conService.SendCommand(new Command("mail-outgoing", null, user, null));
            }
            if (type.equals("draft")) {
                FrmAuth.conService.SendCommand(new Command("mail-draft", null, user, null));
            }
            if (type.equals("trash")) {
                FrmAuth.conService.SendCommand(new Command("mail-trash", null, user, null));
            }
            //popupDialog("wait", "Lütfen bekleyin.", "null");
        } catch (Exception ex) {
            System.out.println("Client Get Mail Exception : " + ex.getLocalizedMessage());
        }
    }



    private void clearMailSection() {
        mailgonder_field_baslik.setText("");
        mailgonder_field_icerik.setText("");
        mailgonder_field_kime.setText("");

        attachments.clear();
    }

    public MailTableModel getMailTable() {
        return (MailTableModel) mailTableModel;
    }

    private Mail getMailFromId(List<Mail> list, int id) {
        for (Mail mail : list) {
            if (mail.getId() == id) {
                return mail;
            }
        }
        return null;
    }

    private Attachment getAttachmentFromId(int id) {
        for (Attachment attachment : attachments) {
            if (attachment.getId() == id) {
                return attachment;
            }
        }
        return null;
    }

    public String mailHeaderChooser(Mail mail) {
        String User = "";
        if (mail.getHeaders().get(0).getType().equals("outgoing")) {
            User += mail.getHeaders().get(0).getRecipientUser();
            if (mail.getHeaders().size() > 1) {
                for (int i = 1; i < mail.getHeaders().size(); i++) {
                    User += " , " + mail.getHeaders().get(i).getRecipientUser();
                }
            }
        } else {
            /*
            for (Header header : mail.getHeaders()) {
            recipientUser += header.getRecipientUser() + " , ";
            }
             */
            User += mail.getHeaders().get(0).getRecipientUser();
            if (mail.getHeaders().size() > 1) {
                for (int i = 1; i < mail.getHeaders().size(); i++) {
                    User += " , " + mail.getHeaders().get(i).getSenderUser();
                }
            }
        }
        return User;
    }

    private void setMailCredentials(Mail mail) {
        String recipientUsers = "";
        recipientUsers = mailHeaderChooser(mail);
        mail_author_text.setText(recipientUsers);
        pnl_mail_body_text.setText(mail.getBody());
        mail_time_text.setText(mail.getCreateDate().toString());
        pnl_mail_detail_header_text.setText(mail.getSubject());

        if (mail.isAttachmentState() != false) {
            pnl_mail_detail_attachment.setVisible(true);
        } else {
            pnl_mail_detail_attachment.setVisible(false);
        }
    }

    public void setMails(Command _command) {
        mailList = (List<Mail>) _command.getObject();
        mailTableModel = (AbstractTableModel) new MailTableModel(mailList, _command.getCommandText());
        mailTable.setModel(mailTableModel);
        popupPanel.setVisible(false);
        FrmAuth.disposeDialog();
        mainLayout.show(cardPanel, "mail");
    }

    private void setAttachmentCredentials(Attachment _attachment) {
        attachment_adi_field.setText(_attachment.getAttachmentName());
        attachment_tipi_field.setText(_attachment.getAttachmentType());
        attachment_boyutu_field.setText(_attachment.getAttachmentSize() + " KB ");
    }

    private void dosyaKaydet(Attachment _attachment) {
        try {
            FileOutputStream fos = new FileOutputStream(_attachment.getAttachmentName() + "." + _attachment.getAttachmentType());
            fos.write(_attachment.getAttachmentContent());
            fos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void dosyaGonder() {
        try {
            attachments.clear();

            JFileChooser jfc = new JFileChooser(System.getProperty("user.dir", "."));

            jfc.setMultiSelectionEnabled(true);

            jfc.removeChoosableFileFilter(jfc.getAcceptAllFileFilter());

            if (jfc.showDialog(this, "Dosya Seç") == JFileChooser.APPROVE_OPTION) {

                File[] files = jfc.getSelectedFiles();
                for (int i = 0; i < files.length; i++) {
                    Attachment _attachment = new Attachment();

                    fileByteArray = Files.readAllBytes(Paths.get(files[i].getPath()));
                    fileTypeString = files[i].getName();


                    String[] extension = fileTypeString.split("\\.(?=[^\\.]+$)");

                    _attachment.setAttachmentContent(fileByteArray);

                    _attachment.setAttachmentName(extension[0]);
                    _attachment.setAttachmentType(extension[1]);
                    _attachment.setAttachmentSize((int) (files[i].length() / 1024L * 1024L));

                    attachments.add(_attachment);
                }

                //mailgonder_label_dosyaismi.setVisible(true);
                //mailgonder_label_dosyaismi.setText(jfc.getSelectedFile().getName());
                repaint();

            }

        } catch (Exception ex) {
            System.out.println("File Transfer Exception : " + ex.getMessage());
        }

    }

    public void saveDraft() {
        Mail draftMail = new Mail();

        List<Header> headers = new ArrayList<Header>();
        
        String[] userSplit = mailgonder_field_kime.getText().split(",");

            for (int i = 0; i < userSplit.length; i++) {
                Header header = new Header();
                header.setSenderUser(user.getUserName());
                header.setRecipientUser(userSplit[i]);
                header.setState(true);
                header.setType("Draft");
                headers.add(header);
            }
            
            System.out.println("sjsj "+attachments.size());
        if (attachments.size() > 0) {
            draftMail.setAttachmentState(true);
        } else {
            Attachment attachment = new Attachment();
            attachments.add(attachment);
            draftMail.setAttachmentState(false);
        }

        draftMail.setAttachments(attachments);
        draftMail.setSubject(mailgonder_field_baslik.getText());
        draftMail.setBody(mailgonder_field_icerik.getText());

        draftMail.setCreateDate(new java.sql.Timestamp(new java.util.Date().getTime()));
        draftMail.setHeaders(headers);
        System.out.println("Draft Mail Debug : "+draftMail.getAttachments().size());
        FrmAuth.conService.SendCommand(new Command("mail-add-draft", null, user, draftMail));
        clearMailSection();
        attachments.clear();

    }

    public void deleteMail(Mail mailFromId) {
        conService.SendCommand(new Command("mail-delete", null, user, mailFromId));
    }

    /*
    
     *********************************************************************
     *******************    User Methods    ******************************
     *********************************************************************
        
     */
    private void getUsers() {
        try {
            FrmAuth.conService.SendCommand(new Command("manager-listuser", null, user, null));
            //popupDialog("wait", "Lütfen bekleyin.", "null");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteUser(User _user) {
        try {
            conService.SendCommand(new Command("manager-deleteuser", null, _user, null));
            //popupDialog("wait", "Lütfen bekleyiniz", null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public User getUserId(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public void setUserDetails(User _user,String _authType) {
        user = _user;
        
        if(_authType.equals("register")){
            user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        }

        welcomeText.setText("Hoşgeldin , " + user.getFirstName() + " " + user.getLastName());
        txt_LastTime.setText("Son Giriş Tarihi  : " + user.getLastLogin());

        profile_ad_text.setText(user.getFirstName());
        profile_soyad_text.setText(user.getLastName());
        profile_username_text.setText(user.getUserName());
        profile_password_text.setText(user.getPassword());
        

        if (user.getRole().equals("Admin")) {
            yonetici_paneli.setVisible(true);
        } else {
            yonetici_paneli.setVisible(false);
        }
    }

    private void setUserCredentialsToDetail(User _user) {
        yonetici_ad_text.setText(_user.getFirstName());
        yonetici_soyad_text.setText(_user.getLastName());
        yonetici_username_text.setText(_user.getUserName());
        yonetici_password_text.setText(_user.getPassword());
        yonetici_edit_id.setText("" + _user.getId());
        yonetici_edit_password.setText(_user.getPassword());
    }

    /*
    
        *********************************************************************
        *******************    Interface Methods    *************************
        *********************************************************************
     */
    public void dialogControl(String type) {
        if (type.equals("draft-mail-bool")) {
            saveDraft();
        }
    }

    public void showManagementPanel(Command _command) {
        FrmAuth.disposeDialog();

        users = (List<User>) _command.getObject();
        userTableModel = (AbstractTableModel) new UserTableModel(users);
        userTable.setModel(userTableModel);
        mainLayout.show(cardPanel, "yonetici");
    }

    /*
    
        *********************************************************************
        *******************     Action Methods      *************************
        *********************************************************************
    
     */
    @Override
    public void mouseClicked(MouseEvent evt) {

        if (evt.getSource() == btn_mail_3) {
            deleteMail(getMailFromId(getMailTable().getList(), (int) mailTableModel.getValueAt(selectedRow, 4)));

            MailTableModel model = (MailTableModel) mailTableModel;
            getMails(model.getType());
        }

        if (evt.getSource() == yonetici_bilgi_onaylama) {
            User _user = new User();

            if (!(yonetici_ad_text.getText().isEmpty() || yonetici_soyad_text.getText().isEmpty() || yonetici_username_text.getText().isEmpty() || yonetici_password_text.getPassword().length == 0)) {
                _user.setFirstName(yonetici_ad_text.getText());
                _user.setLastName(yonetici_soyad_text.getText());
                _user.setUserName(yonetici_username_text.getText());
                _user.setId(Integer.parseInt(yonetici_edit_id.getText()));
                _user.setRole((String) yonetici_edit_combo.getSelectedItem());

                String password = new String(yonetici_password_text.getPassword());

                if (yonetici_edit_password.equals(password)) {
                    _user.setPassword(yonetici_edit_password.getText());
                } else {
                    _user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
                }
            } else {
                popupDialog("error", "Boşlukları doldurunuz lütfen. Onaylanmadı", null);
            }

            FrmAuth.conService.SendCommand(new Command("manager-updateuser", null, _user, null));

            kullanici_bilgi_onaylama.setVisible(false);
        }

        if (evt.getSource() == yonetici_kullanici_sil) {
            deleteUser(getUserId((int) userTableModel.getValueAt(userTable.getSelectedRow(), 6)));
            getUsers();
        }

        if (evt.getSource() == yonetici_kullanici_detay) {
            setUserCredentialsToDetail(getUserId((int) userTableModel.getValueAt(userTable.getSelectedRow(), 6)));
            mainLayout.show(cardPanel, "yonetici_edit");
        }

        if (evt.getSource() == yonetici_user_ekle_geri || evt.getSource() == yonetici_user_edit_geri || evt.getSource() == yonetici_paneli) {
            getUsers();
        }

        if (evt.getSource() == yonetici_kullanici_ekle) {
            mainLayout.show(cardPanel, "kullanici_ekle");
        }

        if (evt.getSource() == yonetici_user_onaylama) {
            if (!(yonetici_ekle_ad_text.getText().isEmpty() || yonetici_ekle_password_text.getPassword().length == 0
                    || yonetici_ekle_soyad_text.getText().isEmpty() || yonetici_ekle_username_text.getText().isEmpty())) {

                User newUser = new User();
                newUser.setFirstName(yonetici_ekle_ad_text.getText());
                newUser.setLastName(yonetici_ekle_soyad_text.getText());
                newUser.setUserName(yonetici_ekle_username_text.getText());
                newUser.setPassword(new String(yonetici_ekle_password_text.getPassword()));
                newUser.setRole((String) rol_list_combo.getSelectedItem());

                FrmAuth.conService.SendCommand(new Command("manager-adduser", null, newUser, null));

                yonetici_ekle_ad_text.setText("");
                yonetici_ekle_soyad_text.setText("");
                yonetici_ekle_username_text.setText("");
                yonetici_ekle_password_text.setText("");

            } else {
                popupDialog("error", "Boşlukları doldurunuz.", null);
            }
        }

        if (evt.getSource() == firstname_edit) {
            kullanici_bilgi_onaylama.setVisible(true);
            profile_ad_text.setEditable(true);
        }
        if (evt.getSource() == lastname_edit) {
            kullanici_bilgi_onaylama.setVisible(true);
            profile_soyad_text.setEditable(true);
        }
        if (evt.getSource() == username_edit) {
            kullanici_bilgi_onaylama.setVisible(true);
            profile_username_text.setEditable(true);
        }
        if (evt.getSource() == password_edit) {
            kullanici_bilgi_onaylama.setVisible(true);
            profile_password_text.setEditable(true);
        }

        if (evt.getSource() == kullanici_bilgi_onaylama) {
            User _user = new User();

            if (!(profile_ad_text.getText().isEmpty() || profile_soyad_text.getText().isEmpty() || profile_username_text.getText().isEmpty() || profile_password_text.getPassword().length == 0)) {
                _user.setFirstName(profile_ad_text.getText());
                _user.setLastName(profile_soyad_text.getText());
                _user.setUserName(profile_username_text.getText());
                _user.setId(user.getId());
                _user.setRole(user.getRole());

                String password = new String(profile_password_text.getPassword());

                if (user.getPassword().equals(password)) {
                    _user.setPassword(user.getPassword());
                } else {
                    _user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
                }
            } else {
                popupDialog("error", "Boşlukları doldurunuz lütfen. Onaylanmadı", null);
            }

            FrmAuth.conService.SendCommand(new Command("manager-updateuser", null, _user, null));

            kullanici_bilgi_onaylama.setVisible(false);
        }

        if (evt.getSource() == mailgonder_btn_dosyaekle) {
            dosyaGonder();
        }

        if (evt.getSource() == mailgonder_btn_gonder) {
            sendMail();
        }

        if (evt.getSource() == yenimailpanel) {
            isSent=true;
            attachments.clear();
            System.out.println("Send Mail Debug : "+isSent);
            mainLayout.show(cardPanel, "mailgonder");
            mailPanelState = pnl_mail_gonder;
        }

        if (evt.getSource() == btn_mail_1) {
            if (!(selectedRow == 0)) {
                selectedRow--;
            }
            setMailCredentials(getMailFromId(getMailTable().getList(), (int) mailTableModel.getValueAt(selectedRow, 4)));
        }
        if (evt.getSource() == btn_mail_2) {
            if (!(selectedRow == mailTableModel.getRowCount() - 1)) {
                selectedRow++;
            }
            setMailCredentials(getMailFromId(getMailTable().getList(), (int) mailTableModel.getValueAt(selectedRow, 4)));
        }
        if (evt.getSource() == gidenpanel) {
            getMails("outgoing");
            mailPanelState = pnl_mail;
        }

        if (evt.getSource() == gelenpanel) {
            getMails("income");
            mailPanelState = pnl_mail;
        }
        if (evt.getSource() == taslakpanel) {
            getMails("draft");
            mailPanelState = pnl_mail;
        }
        if (evt.getSource() == coppanel) {
            getMails("trash");
            mailPanelState = pnl_mail;
        }
        if (evt.getSource() == btn_pnl_anasayfa) {
            if (mailPanelState == pnl_mail_gonder) {
                if (isSent) {
                    popupDialog("boolean", "Taslak olarak kaydetmek istiyor musunuz ?", "draft-mail-bool");
                }                
            }
            mainLayout.show(cardPanel, "anasayfa");
            mailPanelState = pnl_anasayfa;
        }
        if (evt.getSource() == btn_pnl_mail) {
            mainLayout.show(cardPanel, "mailcategory");
            if (mailPanelState == pnl_mail_gonder) {
                if (isSent) {
                    popupDialog("boolean", "Taslak olarak kaydetmek istiyor musunuz ?", "draft-mail-bool");
                }
            }
        }
        if (evt.getSource() == Profile) {
            if (mailPanelState == pnl_mail_gonder) {
                if (isSent) {
                    popupDialog("boolean", "Taslak olarak kaydetmek istiyor musunuz ?", "draft-mail-bool");
                }                
            }

            mainLayout.show(cardPanel, "profil");
            mailPanelState = pnl_profil;

        }
        if (evt.getSource() == pnl_mail_detail_attachment) {
            attachments = (List<Attachment>) mailTableModel.getValueAt(selectedRow, 5);

            attachmentTableModel = (AbstractTableModel) new AttachmentTableModel(attachments);
            attachmentTable.setModel(attachmentTableModel);
            mainLayout.show(cardPanel, "attachment");
        }

        if (evt.getSource() == kaydet) {
            dosyaKaydet((Attachment) attachmentTableModel.getValueAt(attachmentTable.getSelectedRow(), 5));
        }
    }

    @Override
    public void mousePressed(MouseEvent evt) {
        if (evt.getSource() == mailTable) {
            selectedRow = mailTable.getSelectedRow();
            setMailCredentials(getMailFromId(getMailTable().getList(), (int) mailTableModel.getValueAt(selectedRow, 4)));
        }
        if (evt.getSource() == attachmentTable) {
            setAttachmentCredentials(getAttachmentFromId((int) attachmentTableModel.getValueAt(attachmentTable.getSelectedRow(), 4)));
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
                || evt.getSource() == gelenpanel || evt.getSource() == gidenpanel
                || evt.getSource() == taslakpanel || evt.getSource() == coppanel
                || evt.getSource() == Profile || evt.getSource() == pnl_mail_detail_attachment
                || evt.getSource() == firstname_edit || evt.getSource() == kullanici_bilgi_onaylama
                || evt.getSource() == kaydet || evt.getSource() == lastname_edit
                || evt.getSource() == password_edit || evt.getSource() == username_edit
                || evt.getSource() == yonetici_kullanici_ekle || evt.getSource() == yonetici_kullanici_sil || evt.getSource() == yonetici_kullanici_detay
                || evt.getSource() == yonetici_user_ekle_geri) {
            JPanel panel = (JPanel) evt.getSource();
            panel.setOpaque(true);
            repaint();
        }
    }

    @Override
    public void mouseExited(MouseEvent evt) {
        if (evt.getSource() == mailgonder_btn_gonder || evt.getSource() == mailgonder_btn_dosyaekle || evt.getSource() == btn_pnl_anasayfa
                || evt.getSource() == btn_pnl_ayarlar || evt.getSource() == btn_pnl_mail || evt.getSource() == yenimailpanel
                || evt.getSource() == gelenpanel || evt.getSource() == gidenpanel
                || evt.getSource() == taslakpanel || evt.getSource() == coppanel
                || evt.getSource() == Profile || evt.getSource() == pnl_mail_detail_attachment
                || evt.getSource() == firstname_edit || evt.getSource() == kullanici_bilgi_onaylama
                || evt.getSource() == kaydet || evt.getSource() == lastname_edit
                || evt.getSource() == password_edit || evt.getSource() == username_edit
                || evt.getSource() == yonetici_kullanici_ekle || evt.getSource() == yonetici_kullanici_sil || evt.getSource() == yonetici_kullanici_detay
                || evt.getSource() == yonetici_user_ekle_geri) {
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
        buttons = new javax.swing.JPanel();
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
        jLabel2 = new javax.swing.JLabel();
        txt_LastTime = new javax.swing.JLabel();
        pnl_lastTime = new javax.swing.JLabel();
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
        pnl_mail = new javax.swing.JPanel();
        pnl_main_mail = new javax.swing.JPanel();
        pnl_gelen_splitpane = new javax.swing.JSplitPane();
        pnl_main_details = new javax.swing.JPanel();
        pnl_mail_header = new javax.swing.JPanel();
        mail_author = new javax.swing.JPanel();
        mail_author_text = new javax.swing.JTextArea();
        mail_buttons = new javax.swing.JPanel();
        btn_mail_1 = new javax.swing.JLabel();
        btn_mail_2 = new javax.swing.JLabel();
        btn_mail_3 = new javax.swing.JLabel();
        mail_time = new javax.swing.JPanel();
        mail_time_text = new javax.swing.JLabel();
        pnl_mail_body = new javax.swing.JPanel();
        mail_body_message = new javax.swing.JPanel();
        pnl_mail_detail_header = new javax.swing.JPanel();
        pnl_mail_detail_header_scrollpane = new javax.swing.JScrollPane();
        pnl_mail_detail_header_text = new javax.swing.JTextArea();
        pnl_mail_detail_body = new javax.swing.JPanel();
        pnl_mail_detail_attachment = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        pnl_mail_body_scrollpane = new javax.swing.JScrollPane();
        pnl_mail_body_text = new javax.swing.JTextArea();
        pnl_main_mails = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        mailTable = new javax.swing.JTable();
        pnl_profil = new javax.swing.JPanel();
        profile_top_text = new javax.swing.JLabel();
        profile_body = new javax.swing.JPanel();
        profile_ad = new javax.swing.JLabel();
        profile_username_text = new javax.swing.JTextField();
        mailgonder_field_seperator2 = new javax.swing.JSeparator();
        profile_username = new javax.swing.JLabel();
        profile_password_text = new javax.swing.JPasswordField();
        register_seperator_password = new javax.swing.JSeparator();
        profile_password = new javax.swing.JLabel();
        profile_soyad = new javax.swing.JLabel();
        mailgonder_field_seperator3 = new javax.swing.JSeparator();
        mailgonder_field_seperator4 = new javax.swing.JSeparator();
        profile_ad_text = new javax.swing.JTextField();
        mailgonder_field_seperator5 = new javax.swing.JSeparator();
        profile_soyad_text = new javax.swing.JTextField();
        kullanici_bilgi_onaylama = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        lastname_edit = new javax.swing.JPanel();
        lastname_edit_text = new javax.swing.JLabel();
        username_edit = new javax.swing.JPanel();
        username_edit_text = new javax.swing.JLabel();
        firstname_edit = new javax.swing.JPanel();
        firstname_edit_Text = new javax.swing.JLabel();
        password_edit = new javax.swing.JPanel();
        password_edit_text = new javax.swing.JLabel();
        yonetici_paneli = new javax.swing.JPanel();
        yonetici_paneli_text = new javax.swing.JLabel();
        pnl_attachments = new javax.swing.JPanel();
        attachment_baslik = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        attachmentTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        attachment_adi = new javax.swing.JLabel();
        attachment_tipi = new javax.swing.JLabel();
        attachment_boyutu = new javax.swing.JLabel();
        kaydet = new javax.swing.JPanel();
        kaydet_text = new javax.swing.JLabel();
        attachment_boyutu_field = new javax.swing.JLabel();
        attachment_adi_field = new javax.swing.JLabel();
        attachment_tipi_field = new javax.swing.JLabel();
        pnl_yonetici = new javax.swing.JPanel();
        yonetici_text = new javax.swing.JLabel();
        yonetici_sidebar = new javax.swing.JPanel();
        yonetici_kullanici_sil = new javax.swing.JPanel();
        yonetici_kullanici_sil_text = new javax.swing.JLabel();
        yonetici_kullanici_ekle = new javax.swing.JPanel();
        yonetici_kullanici_ekle_text = new javax.swing.JLabel();
        yonetici_kullanici_detay = new javax.swing.JPanel();
        yonetici_kullanici_detay_text = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        userTable = new javax.swing.JTable();
        pnl_yonetici_kullanici_ekle = new javax.swing.JPanel();
        kullanici_ekle = new javax.swing.JLabel();
        yonetici_ekle_ad = new javax.swing.JLabel();
        yonetici_ekle_ad_text = new javax.swing.JTextField();
        yonetici_ekle_soyad = new javax.swing.JLabel();
        yonetici_ekle_soyad_text = new javax.swing.JTextField();
        mailgonder_field_seperator6 = new javax.swing.JSeparator();
        mailgonder_field_seperator7 = new javax.swing.JSeparator();
        mailgonder_field_seperator8 = new javax.swing.JSeparator();
        yonetici_ekle_username_text = new javax.swing.JTextField();
        yonetici_ekle_password_text = new javax.swing.JPasswordField();
        yonetici_ekle_password = new javax.swing.JLabel();
        yonetici_ekle_username = new javax.swing.JLabel();
        register_seperator_password1 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        rol_list_combo = new javax.swing.JComboBox<>();
        yonetici_user_onaylama = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        yonetici_user_ekle_geri = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        pnl_yonetici_kullanici_edit = new javax.swing.JPanel();
        mailgonder_field_seperator9 = new javax.swing.JSeparator();
        yonetici_bilgi_onaylama = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        yonetici_ad_text = new javax.swing.JTextField();
        yonetici_profile_soyad = new javax.swing.JLabel();
        yonetici_soyad_text = new javax.swing.JTextField();
        yonetici_username = new javax.swing.JLabel();
        yonetici_username_text = new javax.swing.JTextField();
        mailgonder_field_seperator10 = new javax.swing.JSeparator();
        register_seperator_password2 = new javax.swing.JSeparator();
        profile_ad1 = new javax.swing.JLabel();
        mailgonder_field_seperator11 = new javax.swing.JSeparator();
        mailgonder_field_seperator12 = new javax.swing.JSeparator();
        yonetici_password_text = new javax.swing.JPasswordField();
        yonetici_password = new javax.swing.JLabel();
        yonetici_edit_combo = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        yonetici_edit_id = new javax.swing.JLabel();
        yonetici_user_edit_geri = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        yonetici_edit_password = new javax.swing.JLabel();
        bottomBar = new javax.swing.JPanel();
        Profile = new javax.swing.JPanel();
        profile_icon = new javax.swing.JLabel();
        Menus = new javax.swing.JPanel();
        btn_pnl_ayarlar = new javax.swing.JPanel();
        btn_pnl_anasayfa = new javax.swing.JPanel();
        txt_anasayfa = new javax.swing.JLabel();
        btn_pnl_mail = new javax.swing.JPanel();
        txt_mailkutusu = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        buttons.add(pnl_titlebuttons, java.awt.BorderLayout.PAGE_START);

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
        pnl_welcomer.add(welcomeText, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 210, 580, 40));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/clock.png"))); // NOI18N
        pnl_welcomer.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 320, 40, 40));

        txt_LastTime.setForeground(new java.awt.Color(254, 254, 254));
        txt_LastTime.setText("Son Giriş Tarihi  : ");
        pnl_welcomer.add(txt_LastTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 330, 410, 20));

        pnl_lastTime.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/vbteam/views/images/rect600.png"))); // NOI18N
        pnl_lastTime.setToolTipText("");
        pnl_welcomer.add(pnl_lastTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 310, 620, 60));

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

        gidenpanel.setBackground(new java.awt.Color(30, 29, 32));
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

        taslakpanel.setBackground(new java.awt.Color(30, 29, 32));
        taslakpanel.setOpaque(false);
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

        coppanel.setBackground(new java.awt.Color(30, 29, 32));
        coppanel.setOpaque(false);
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

        mail_author.setBackground(new java.awt.Color(20, 20, 22));
        mail_author.setOpaque(false);
        mail_author.setLayout(new java.awt.BorderLayout());

        mail_author_text.setEditable(false);
        mail_author_text.setBackground(new java.awt.Color(20, 20, 22));
        mail_author_text.setColumns(20);
        mail_author_text.setForeground(new java.awt.Color(254, 254, 254));
        mail_author_text.setLineWrap(true);
        mail_author_text.setRows(4);
        mail_author_text.setTabSize(4);
        mail_author_text.setWrapStyleWord(true);
        mail_author_text.setBorder(null);
        mail_author_text.setPreferredSize(new java.awt.Dimension(300, 72));
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
        mail_time_text.setForeground(new java.awt.Color(254, 254, 254));
        mail_time_text.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        mail_time_text.setText("[Time]");
        mail_time.add(mail_time_text, java.awt.BorderLayout.CENTER);

        pnl_mail_header.add(mail_time, java.awt.BorderLayout.CENTER);

        pnl_main_details.add(pnl_mail_header, java.awt.BorderLayout.PAGE_START);

        pnl_mail_body.setBackground(new java.awt.Color(45, 48, 53));
        pnl_mail_body.setPreferredSize(new java.awt.Dimension(100, 100));
        pnl_mail_body.setLayout(new java.awt.BorderLayout());

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
        pnl_mail_detail_header_text.setForeground(new java.awt.Color(254, 254, 254));
        pnl_mail_detail_header_text.setLineWrap(true);
        pnl_mail_detail_header_text.setRows(2);
        pnl_mail_detail_header_text.setTabSize(2);
        pnl_mail_detail_header_text.setWrapStyleWord(true);
        pnl_mail_detail_header_text.setCaretColor(new java.awt.Color(255, 255, 255));
        pnl_mail_detail_header_scrollpane.setViewportView(pnl_mail_detail_header_text);

        pnl_mail_detail_header.add(pnl_mail_detail_header_scrollpane, java.awt.BorderLayout.CENTER);

        mail_body_message.add(pnl_mail_detail_header, java.awt.BorderLayout.PAGE_START);

        pnl_mail_detail_body.setBackground(new java.awt.Color(20, 20, 22));
        pnl_mail_detail_body.setPreferredSize(new java.awt.Dimension(50, 100));
        pnl_mail_detail_body.setLayout(new java.awt.BorderLayout());

        pnl_mail_detail_attachment.setBackground(new java.awt.Color(30, 29, 32));
        pnl_mail_detail_attachment.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), new java.awt.Color(165, 165, 172))); // NOI18N
        pnl_mail_detail_attachment.setOpaque(false);
        pnl_mail_detail_attachment.setPreferredSize(new java.awt.Dimension(30, 30));
        pnl_mail_detail_attachment.setLayout(new java.awt.BorderLayout());

        jLabel5.setForeground(new java.awt.Color(254, 254, 254));
        jLabel5.setText("Email Ekleri");
        pnl_mail_detail_attachment.add(jLabel5, java.awt.BorderLayout.CENTER);

        jLabel6.setForeground(new java.awt.Color(254, 254, 254));
        jLabel6.setText(">");
        pnl_mail_detail_attachment.add(jLabel6, java.awt.BorderLayout.LINE_END);

        pnl_mail_detail_body.add(pnl_mail_detail_attachment, java.awt.BorderLayout.PAGE_END);

        pnl_mail_body_scrollpane.setBackground(new java.awt.Color(20, 20, 22));
        pnl_mail_body_scrollpane.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2));
        pnl_mail_body_scrollpane.setPreferredSize(new java.awt.Dimension(40, 40));

        pnl_mail_body_text.setEditable(false);
        pnl_mail_body_text.setBackground(new java.awt.Color(20, 20, 22));
        pnl_mail_body_text.setColumns(2);
        pnl_mail_body_text.setForeground(new java.awt.Color(165, 165, 172));
        pnl_mail_body_text.setLineWrap(true);
        pnl_mail_body_text.setRows(10);
        pnl_mail_body_text.setTabSize(2);
        pnl_mail_body_text.setWrapStyleWord(true);
        pnl_mail_body_text.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2));
        pnl_mail_body_scrollpane.setViewportView(pnl_mail_body_text);

        pnl_mail_detail_body.add(pnl_mail_body_scrollpane, java.awt.BorderLayout.CENTER);

        mail_body_message.add(pnl_mail_detail_body, java.awt.BorderLayout.CENTER);

        pnl_mail_body.add(mail_body_message, java.awt.BorderLayout.CENTER);

        pnl_main_details.add(pnl_mail_body, java.awt.BorderLayout.CENTER);

        pnl_gelen_splitpane.setRightComponent(pnl_main_details);

        pnl_main_mails.setBackground(new java.awt.Color(45, 48, 53));
        pnl_main_mails.setOpaque(false);
        pnl_main_mails.setPreferredSize(new java.awt.Dimension(700, 750));
        pnl_main_mails.setLayout(new java.awt.BorderLayout());

        mailTable.setModel(mailTableModel);
        mailTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(mailTable);

        pnl_main_mails.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        pnl_gelen_splitpane.setLeftComponent(pnl_main_mails);

        pnl_main_mail.add(pnl_gelen_splitpane, "gelencard");

        pnl_mail.add(pnl_main_mail, java.awt.BorderLayout.CENTER);

        cardPanel.add(pnl_mail, "mail");

        pnl_profil.setBackground(new java.awt.Color(20, 20, 22));
        pnl_profil.setLayout(new java.awt.BorderLayout());

        profile_top_text.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        profile_top_text.setForeground(new java.awt.Color(254, 254, 254));
        profile_top_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        profile_top_text.setText("Kullanıcı Profili");
        pnl_profil.add(profile_top_text, java.awt.BorderLayout.PAGE_START);

        profile_body.setOpaque(false);
        profile_body.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        profile_ad.setForeground(new java.awt.Color(254, 254, 254));
        profile_ad.setText("Adı :");
        profile_body.add(profile_ad, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 140, -1, -1));

        profile_username_text.setEditable(false);
        profile_username_text.setBackground(new java.awt.Color(20, 20, 22));
        profile_username_text.setForeground(new java.awt.Color(238, 217, 217));
        profile_username_text.setBorder(null);
        profile_body.add(profile_username_text, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 300, 260, 40));

        mailgonder_field_seperator2.setBackground(new java.awt.Color(25, 25, 28));
        mailgonder_field_seperator2.setForeground(new java.awt.Color(25, 25, 28));
        profile_body.add(mailgonder_field_seperator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 340, 260, 40));

        profile_username.setForeground(new java.awt.Color(254, 254, 254));
        profile_username.setText("Kullanıcı  :");
        profile_body.add(profile_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 310, -1, -1));

        profile_password_text.setEditable(false);
        profile_password_text.setBackground(new java.awt.Color(20, 20, 22));
        profile_password_text.setForeground(new java.awt.Color(238, 217, 217));
        profile_password_text.setBorder(null);
        profile_body.add(profile_password_text, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 390, 260, 40));

        register_seperator_password.setBackground(new java.awt.Color(25, 25, 28));
        register_seperator_password.setForeground(new java.awt.Color(25, 25, 28));
        profile_body.add(register_seperator_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 430, 260, 40));

        profile_password.setForeground(new java.awt.Color(254, 254, 254));
        profile_password.setText("Şifre :");
        profile_body.add(profile_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 400, -1, -1));

        profile_soyad.setForeground(new java.awt.Color(254, 254, 254));
        profile_soyad.setText("Soyadı  : ");
        profile_body.add(profile_soyad, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 220, -1, -1));

        mailgonder_field_seperator3.setBackground(new java.awt.Color(25, 25, 28));
        mailgonder_field_seperator3.setForeground(new java.awt.Color(25, 25, 28));
        profile_body.add(mailgonder_field_seperator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 250, 260, 40));

        mailgonder_field_seperator4.setBackground(new java.awt.Color(25, 25, 28));
        mailgonder_field_seperator4.setForeground(new java.awt.Color(25, 25, 28));
        profile_body.add(mailgonder_field_seperator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 170, 260, 40));

        profile_ad_text.setEditable(false);
        profile_ad_text.setBackground(new java.awt.Color(20, 20, 22));
        profile_ad_text.setForeground(new java.awt.Color(238, 217, 217));
        profile_ad_text.setBorder(null);
        profile_body.add(profile_ad_text, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 130, 260, 40));

        mailgonder_field_seperator5.setBackground(new java.awt.Color(25, 25, 28));
        mailgonder_field_seperator5.setForeground(new java.awt.Color(25, 25, 28));
        profile_body.add(mailgonder_field_seperator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 250, 260, 40));

        profile_soyad_text.setEditable(false);
        profile_soyad_text.setBackground(new java.awt.Color(20, 20, 22));
        profile_soyad_text.setForeground(new java.awt.Color(238, 217, 217));
        profile_soyad_text.setBorder(null);
        profile_body.add(profile_soyad_text, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 210, 260, 40));

        kullanici_bilgi_onaylama.setBackground(new java.awt.Color(30, 29, 32));
        kullanici_bilgi_onaylama.setOpaque(false);
        kullanici_bilgi_onaylama.setLayout(new java.awt.BorderLayout());

        jLabel11.setForeground(new java.awt.Color(254, 254, 254));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Onayla");
        kullanici_bilgi_onaylama.add(jLabel11, java.awt.BorderLayout.CENTER);

        profile_body.add(kullanici_bilgi_onaylama, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 450, 400, 40));

        lastname_edit.setBackground(new java.awt.Color(30, 29, 32));
        lastname_edit.setOpaque(false);
        lastname_edit.setLayout(new java.awt.BorderLayout());

        lastname_edit_text.setBackground(new java.awt.Color(30, 29, 32));
        lastname_edit_text.setForeground(new java.awt.Color(254, 254, 254));
        lastname_edit_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lastname_edit_text.setText("Düzenle");
        lastname_edit.add(lastname_edit_text, java.awt.BorderLayout.CENTER);

        profile_body.add(lastname_edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 210, 70, 40));

        username_edit.setBackground(new java.awt.Color(30, 29, 32));
        username_edit.setOpaque(false);
        username_edit.setLayout(new java.awt.BorderLayout());

        username_edit_text.setBackground(new java.awt.Color(30, 29, 32));
        username_edit_text.setForeground(new java.awt.Color(254, 254, 254));
        username_edit_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        username_edit_text.setText("Düzenle");
        username_edit.add(username_edit_text, java.awt.BorderLayout.CENTER);

        profile_body.add(username_edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 300, 70, 40));

        firstname_edit.setBackground(new java.awt.Color(30, 29, 32));
        firstname_edit.setOpaque(false);
        firstname_edit.setLayout(new java.awt.BorderLayout());

        firstname_edit_Text.setBackground(new java.awt.Color(30, 29, 32));
        firstname_edit_Text.setForeground(new java.awt.Color(254, 254, 254));
        firstname_edit_Text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        firstname_edit_Text.setText("Düzenle");
        firstname_edit.add(firstname_edit_Text, java.awt.BorderLayout.CENTER);

        profile_body.add(firstname_edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 130, 70, 40));

        password_edit.setBackground(new java.awt.Color(30, 29, 32));
        password_edit.setOpaque(false);
        password_edit.setLayout(new java.awt.BorderLayout());

        password_edit_text.setBackground(new java.awt.Color(30, 29, 32));
        password_edit_text.setForeground(new java.awt.Color(254, 254, 254));
        password_edit_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        password_edit_text.setText("Düzenle");
        password_edit.add(password_edit_text, java.awt.BorderLayout.CENTER);

        profile_body.add(password_edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 390, 70, 40));

        yonetici_paneli.setBackground(new java.awt.Color(30, 29, 32));
        yonetici_paneli.setOpaque(false);
        yonetici_paneli.setLayout(new java.awt.BorderLayout());

        yonetici_paneli_text.setForeground(new java.awt.Color(254, 254, 254));
        yonetici_paneli_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        yonetici_paneli_text.setText("Yönetici paneline giriş");
        yonetici_paneli.add(yonetici_paneli_text, java.awt.BorderLayout.CENTER);

        profile_body.add(yonetici_paneli, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 50, 350, 60));

        pnl_profil.add(profile_body, java.awt.BorderLayout.CENTER);

        cardPanel.add(pnl_profil, "profil");

        pnl_attachments.setBackground(new java.awt.Color(20, 20, 22));
        pnl_attachments.setLayout(new java.awt.BorderLayout());

        attachment_baslik.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        attachment_baslik.setText("Mail Ekleri");
        pnl_attachments.add(attachment_baslik, java.awt.BorderLayout.PAGE_START);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(1300, 1300));

        attachmentTable.setModel(attachmentTableModel);
        jScrollPane1.setViewportView(attachmentTable);

        pnl_attachments.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel2.setBackground(new java.awt.Color(20, 20, 22));
        jPanel2.setPreferredSize(new java.awt.Dimension(300, 300));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        attachment_adi.setForeground(new java.awt.Color(254, 254, 254));
        attachment_adi.setText("Adı : ");
        jPanel2.add(attachment_adi, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        attachment_tipi.setForeground(new java.awt.Color(254, 254, 254));
        attachment_tipi.setText("Dosya tipi :");
        jPanel2.add(attachment_tipi, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        attachment_boyutu.setForeground(new java.awt.Color(254, 254, 254));
        attachment_boyutu.setText("Dosya boyutu :");
        jPanel2.add(attachment_boyutu, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, -1));

        kaydet.setBackground(new java.awt.Color(30, 29, 32));
        kaydet.setOpaque(false);
        kaydet.setLayout(new java.awt.BorderLayout());

        kaydet_text.setForeground(new java.awt.Color(255, 254, 254));
        kaydet_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        kaydet_text.setText("Kaydet");
        kaydet.add(kaydet_text, java.awt.BorderLayout.CENTER);

        jPanel2.add(kaydet, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 300, 110));

        attachment_boyutu_field.setForeground(new java.awt.Color(254, 254, 254));
        jPanel2.add(attachment_boyutu_field, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 140, 150, -1));

        attachment_adi_field.setForeground(new java.awt.Color(254, 254, 254));
        jPanel2.add(attachment_adi_field, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 210, -1));

        attachment_tipi_field.setForeground(new java.awt.Color(254, 254, 254));
        jPanel2.add(attachment_tipi_field, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, 180, -1));

        pnl_attachments.add(jPanel2, java.awt.BorderLayout.LINE_END);

        cardPanel.add(pnl_attachments, "attachment");

        pnl_yonetici.setBackground(new java.awt.Color(20, 20, 22));
        pnl_yonetici.setLayout(new java.awt.BorderLayout());

        yonetici_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        yonetici_text.setText("Yönetici Paneli");
        pnl_yonetici.add(yonetici_text, java.awt.BorderLayout.PAGE_START);

        yonetici_sidebar.setBackground(new java.awt.Color(20, 20, 22));
        yonetici_sidebar.setPreferredSize(new java.awt.Dimension(100, 100));
        yonetici_sidebar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        yonetici_kullanici_sil.setBackground(new java.awt.Color(30, 29, 32));
        yonetici_kullanici_sil.setOpaque(false);
        yonetici_kullanici_sil.setLayout(new java.awt.BorderLayout());

        yonetici_kullanici_sil_text.setFont(new java.awt.Font("Ubuntu", 0, 13)); // NOI18N
        yonetici_kullanici_sil_text.setForeground(new java.awt.Color(254, 254, 254));
        yonetici_kullanici_sil_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        yonetici_kullanici_sil_text.setText("Kullanici Sil");
        yonetici_kullanici_sil.add(yonetici_kullanici_sil_text, java.awt.BorderLayout.CENTER);

        yonetici_sidebar.add(yonetici_kullanici_sil, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 100, 90, 60));

        yonetici_kullanici_ekle.setBackground(new java.awt.Color(30, 29, 32));
        yonetici_kullanici_ekle.setOpaque(false);
        yonetici_kullanici_ekle.setLayout(new java.awt.BorderLayout());

        yonetici_kullanici_ekle_text.setFont(new java.awt.Font("Ubuntu", 0, 13)); // NOI18N
        yonetici_kullanici_ekle_text.setForeground(new java.awt.Color(254, 254, 254));
        yonetici_kullanici_ekle_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        yonetici_kullanici_ekle_text.setText("Kullanici Ekle");
        yonetici_kullanici_ekle.add(yonetici_kullanici_ekle_text, java.awt.BorderLayout.CENTER);

        yonetici_sidebar.add(yonetici_kullanici_ekle, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 5, 90, 60));

        yonetici_kullanici_detay.setBackground(new java.awt.Color(30, 29, 32));
        yonetici_kullanici_detay.setOpaque(false);
        yonetici_kullanici_detay.setLayout(new java.awt.BorderLayout());

        yonetici_kullanici_detay_text.setFont(new java.awt.Font("Ubuntu", 0, 13)); // NOI18N
        yonetici_kullanici_detay_text.setForeground(new java.awt.Color(254, 254, 254));
        yonetici_kullanici_detay_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        yonetici_kullanici_detay_text.setText("Detay");
        yonetici_kullanici_detay.add(yonetici_kullanici_detay_text, java.awt.BorderLayout.CENTER);

        yonetici_sidebar.add(yonetici_kullanici_detay, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 180, 90, 60));

        pnl_yonetici.add(yonetici_sidebar, java.awt.BorderLayout.LINE_END);

        userTable.setModel(userTableModel);
        jScrollPane3.setViewportView(userTable);

        pnl_yonetici.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        cardPanel.add(pnl_yonetici, "yonetici");

        pnl_yonetici_kullanici_ekle.setBackground(new java.awt.Color(20, 20, 22));
        pnl_yonetici_kullanici_ekle.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        kullanici_ekle.setForeground(new java.awt.Color(254, 254, 254));
        kullanici_ekle.setText("Kullanıcı Ekle");
        pnl_yonetici_kullanici_ekle.add(kullanici_ekle, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 30, -1, -1));

        yonetici_ekle_ad.setForeground(new java.awt.Color(254, 254, 254));
        yonetici_ekle_ad.setText("Adı :");
        pnl_yonetici_kullanici_ekle.add(yonetici_ekle_ad, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 140, -1, -1));

        yonetici_ekle_ad_text.setBackground(new java.awt.Color(20, 20, 22));
        yonetici_ekle_ad_text.setForeground(new java.awt.Color(238, 217, 217));
        yonetici_ekle_ad_text.setBorder(null);
        pnl_yonetici_kullanici_ekle.add(yonetici_ekle_ad_text, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 130, 260, 40));

        yonetici_ekle_soyad.setForeground(new java.awt.Color(254, 254, 254));
        yonetici_ekle_soyad.setText("Soyadı  : ");
        pnl_yonetici_kullanici_ekle.add(yonetici_ekle_soyad, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 220, -1, -1));

        yonetici_ekle_soyad_text.setBackground(new java.awt.Color(20, 20, 22));
        yonetici_ekle_soyad_text.setForeground(new java.awt.Color(238, 217, 217));
        yonetici_ekle_soyad_text.setBorder(null);
        pnl_yonetici_kullanici_ekle.add(yonetici_ekle_soyad_text, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 210, 260, 40));

        mailgonder_field_seperator6.setBackground(new java.awt.Color(25, 25, 28));
        mailgonder_field_seperator6.setForeground(new java.awt.Color(25, 25, 28));
        pnl_yonetici_kullanici_ekle.add(mailgonder_field_seperator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 250, 260, 40));

        mailgonder_field_seperator7.setBackground(new java.awt.Color(25, 25, 28));
        mailgonder_field_seperator7.setForeground(new java.awt.Color(25, 25, 28));
        pnl_yonetici_kullanici_ekle.add(mailgonder_field_seperator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 170, 260, 40));

        mailgonder_field_seperator8.setBackground(new java.awt.Color(25, 25, 28));
        mailgonder_field_seperator8.setForeground(new java.awt.Color(25, 25, 28));
        pnl_yonetici_kullanici_ekle.add(mailgonder_field_seperator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 340, 260, 40));

        yonetici_ekle_username_text.setBackground(new java.awt.Color(20, 20, 22));
        yonetici_ekle_username_text.setForeground(new java.awt.Color(238, 217, 217));
        yonetici_ekle_username_text.setBorder(null);
        pnl_yonetici_kullanici_ekle.add(yonetici_ekle_username_text, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 300, 260, 40));

        yonetici_ekle_password_text.setBackground(new java.awt.Color(20, 20, 22));
        yonetici_ekle_password_text.setForeground(new java.awt.Color(238, 217, 217));
        yonetici_ekle_password_text.setBorder(null);
        pnl_yonetici_kullanici_ekle.add(yonetici_ekle_password_text, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 390, 260, 40));

        yonetici_ekle_password.setForeground(new java.awt.Color(254, 254, 254));
        yonetici_ekle_password.setText("Şifre :");
        pnl_yonetici_kullanici_ekle.add(yonetici_ekle_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 400, -1, -1));

        yonetici_ekle_username.setForeground(new java.awt.Color(254, 254, 254));
        yonetici_ekle_username.setText("Kullanıcı  :");
        pnl_yonetici_kullanici_ekle.add(yonetici_ekle_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 310, -1, -1));

        register_seperator_password1.setBackground(new java.awt.Color(25, 25, 28));
        register_seperator_password1.setForeground(new java.awt.Color(25, 25, 28));
        pnl_yonetici_kullanici_ekle.add(register_seperator_password1, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 430, 260, 40));

        jLabel7.setForeground(new java.awt.Color(254, 254, 254));
        jLabel7.setText("Rol : ");
        pnl_yonetici_kullanici_ekle.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 470, -1, -1));

        rol_list_combo.setForeground(new java.awt.Color(254, 254, 254));
        rol_list_combo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "User", "Admin" }));
        pnl_yonetici_kullanici_ekle.add(rol_list_combo, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 460, 260, -1));

        yonetici_user_onaylama.setBackground(new java.awt.Color(30, 29, 32));
        yonetici_user_onaylama.setOpaque(false);
        yonetici_user_onaylama.setLayout(new java.awt.BorderLayout());

        jLabel12.setForeground(new java.awt.Color(254, 254, 254));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Onayla");
        yonetici_user_onaylama.add(jLabel12, java.awt.BorderLayout.CENTER);

        pnl_yonetici_kullanici_ekle.add(yonetici_user_onaylama, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 510, 400, 40));

        yonetici_user_ekle_geri.setBackground(new java.awt.Color(30, 29, 32));
        yonetici_user_ekle_geri.setOpaque(false);
        yonetici_user_ekle_geri.setLayout(new java.awt.BorderLayout());

        jLabel13.setForeground(new java.awt.Color(254, 254, 254));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Geri");
        yonetici_user_ekle_geri.add(jLabel13, java.awt.BorderLayout.CENTER);

        pnl_yonetici_kullanici_ekle.add(yonetici_user_ekle_geri, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 70, 50));

        cardPanel.add(pnl_yonetici_kullanici_ekle, "kullanici_ekle");

        pnl_yonetici_kullanici_edit.setBackground(new java.awt.Color(20, 20, 22));
        pnl_yonetici_kullanici_edit.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        mailgonder_field_seperator9.setBackground(new java.awt.Color(25, 25, 28));
        mailgonder_field_seperator9.setForeground(new java.awt.Color(25, 25, 28));
        pnl_yonetici_kullanici_edit.add(mailgonder_field_seperator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 170, 260, 40));

        yonetici_bilgi_onaylama.setBackground(new java.awt.Color(30, 29, 32));
        yonetici_bilgi_onaylama.setOpaque(false);
        yonetici_bilgi_onaylama.setLayout(new java.awt.BorderLayout());

        jLabel14.setForeground(new java.awt.Color(254, 254, 254));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Onayla");
        yonetici_bilgi_onaylama.add(jLabel14, java.awt.BorderLayout.CENTER);

        pnl_yonetici_kullanici_edit.add(yonetici_bilgi_onaylama, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 530, 400, 40));

        yonetici_ad_text.setBackground(new java.awt.Color(20, 20, 22));
        yonetici_ad_text.setForeground(new java.awt.Color(238, 217, 217));
        yonetici_ad_text.setBorder(null);
        pnl_yonetici_kullanici_edit.add(yonetici_ad_text, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 130, 260, 40));

        yonetici_profile_soyad.setForeground(new java.awt.Color(254, 254, 254));
        yonetici_profile_soyad.setText("Soyadı  : ");
        pnl_yonetici_kullanici_edit.add(yonetici_profile_soyad, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 220, -1, -1));

        yonetici_soyad_text.setBackground(new java.awt.Color(20, 20, 22));
        yonetici_soyad_text.setForeground(new java.awt.Color(238, 217, 217));
        yonetici_soyad_text.setBorder(null);
        pnl_yonetici_kullanici_edit.add(yonetici_soyad_text, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 210, 260, 40));

        yonetici_username.setForeground(new java.awt.Color(254, 254, 254));
        yonetici_username.setText("Kullanıcı  :");
        pnl_yonetici_kullanici_edit.add(yonetici_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 310, -1, -1));

        yonetici_username_text.setBackground(new java.awt.Color(20, 20, 22));
        yonetici_username_text.setForeground(new java.awt.Color(238, 217, 217));
        yonetici_username_text.setBorder(null);
        pnl_yonetici_kullanici_edit.add(yonetici_username_text, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 300, 260, 40));

        mailgonder_field_seperator10.setBackground(new java.awt.Color(25, 25, 28));
        mailgonder_field_seperator10.setForeground(new java.awt.Color(25, 25, 28));
        pnl_yonetici_kullanici_edit.add(mailgonder_field_seperator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 250, 260, 40));

        register_seperator_password2.setBackground(new java.awt.Color(25, 25, 28));
        register_seperator_password2.setForeground(new java.awt.Color(25, 25, 28));
        pnl_yonetici_kullanici_edit.add(register_seperator_password2, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 430, 260, 40));

        profile_ad1.setForeground(new java.awt.Color(254, 254, 254));
        profile_ad1.setText("Adı :");
        pnl_yonetici_kullanici_edit.add(profile_ad1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 140, -1, -1));

        mailgonder_field_seperator11.setBackground(new java.awt.Color(25, 25, 28));
        mailgonder_field_seperator11.setForeground(new java.awt.Color(25, 25, 28));
        pnl_yonetici_kullanici_edit.add(mailgonder_field_seperator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 250, 260, 40));

        mailgonder_field_seperator12.setBackground(new java.awt.Color(25, 25, 28));
        mailgonder_field_seperator12.setForeground(new java.awt.Color(25, 25, 28));
        pnl_yonetici_kullanici_edit.add(mailgonder_field_seperator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 340, 260, 40));

        yonetici_password_text.setBackground(new java.awt.Color(20, 20, 22));
        yonetici_password_text.setForeground(new java.awt.Color(238, 217, 217));
        yonetici_password_text.setBorder(null);
        pnl_yonetici_kullanici_edit.add(yonetici_password_text, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 390, 260, 40));

        yonetici_password.setForeground(new java.awt.Color(254, 254, 254));
        yonetici_password.setText("Şifre :");
        pnl_yonetici_kullanici_edit.add(yonetici_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 400, -1, -1));

        yonetici_edit_combo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "User", "Admin" }));
        pnl_yonetici_kullanici_edit.add(yonetici_edit_combo, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 460, 260, -1));

        jLabel15.setForeground(new java.awt.Color(254, 254, 254));
        jLabel15.setText("Rol :");
        pnl_yonetici_kullanici_edit.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 470, -1, -1));

        yonetici_edit_id.setForeground(new java.awt.Color(20, 20, 22));
        yonetici_edit_id.setText("ID");
        pnl_yonetici_kullanici_edit.add(yonetici_edit_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 80, -1, -1));

        yonetici_user_edit_geri.setBackground(new java.awt.Color(30, 29, 32));
        yonetici_user_edit_geri.setOpaque(false);
        yonetici_user_edit_geri.setLayout(new java.awt.BorderLayout());

        jLabel16.setForeground(new java.awt.Color(254, 254, 254));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Geri");
        yonetici_user_edit_geri.add(jLabel16, java.awt.BorderLayout.CENTER);

        pnl_yonetici_kullanici_edit.add(yonetici_user_edit_geri, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 70, 50));

        yonetici_edit_password.setForeground(new java.awt.Color(20, 20, 22));
        yonetici_edit_password.setText("jLabel17");
        pnl_yonetici_kullanici_edit.add(yonetici_edit_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 80, -1, -1));

        cardPanel.add(pnl_yonetici_kullanici_edit, "yonetici_edit");

        getContentPane().add(cardPanel, java.awt.BorderLayout.CENTER);

        bottomBar.setBackground(new java.awt.Color(0, 0, 0));
        bottomBar.setForeground(new java.awt.Color(0, 0, 0));
        bottomBar.setPreferredSize(new java.awt.Dimension(1366, 60));
        bottomBar.setLayout(new java.awt.BorderLayout());

        Profile.setBackground(new java.awt.Color(228, 14, 22));
        Profile.setOpaque(false);
        Profile.setLayout(new java.awt.BorderLayout());

        profile_icon.setBackground(new java.awt.Color(228, 14, 22));
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
        Menus.add(btn_pnl_ayarlar, java.awt.BorderLayout.LINE_END);

        btn_pnl_anasayfa.setBackground(new java.awt.Color(30, 29, 32));
        btn_pnl_anasayfa.setOpaque(false);
        btn_pnl_anasayfa.setLayout(new java.awt.BorderLayout());

        txt_anasayfa.setBackground(new java.awt.Color(1, 0, 1));
        txt_anasayfa.setForeground(new java.awt.Color(219, 218, 220));
        txt_anasayfa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_anasayfa.setText("Anasayfa");
        txt_anasayfa.setPreferredSize(new java.awt.Dimension(600, 600));
        btn_pnl_anasayfa.add(txt_anasayfa, java.awt.BorderLayout.CENTER);

        Menus.add(btn_pnl_anasayfa, java.awt.BorderLayout.LINE_START);

        btn_pnl_mail.setBackground(new java.awt.Color(30, 29, 32));
        btn_pnl_mail.setOpaque(false);
        btn_pnl_mail.setLayout(new java.awt.BorderLayout());

        txt_mailkutusu.setBackground(new java.awt.Color(1, 0, 1));
        txt_mailkutusu.setForeground(new java.awt.Color(219, 218, 220));
        txt_mailkutusu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_mailkutusu.setText("Mail Kutusu");
        txt_mailkutusu.setPreferredSize(new java.awt.Dimension(600, 600));
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
            java.util.logging.Logger.getLogger(FrmDashboard.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmDashboard.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmDashboard.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmDashboard.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmDashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Menus;
    private javax.swing.JPanel Profile;
    private javax.swing.JTable attachmentTable;
    private javax.swing.JLabel attachment_adi;
    private javax.swing.JLabel attachment_adi_field;
    private javax.swing.JLabel attachment_baslik;
    private javax.swing.JLabel attachment_boyutu;
    private javax.swing.JLabel attachment_boyutu_field;
    private javax.swing.JLabel attachment_tipi;
    private javax.swing.JLabel attachment_tipi_field;
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
    private javax.swing.JPanel buttons;
    private javax.swing.JPanel cardPanel;
    private javax.swing.JLabel cop_Text;
    private javax.swing.JPanel coppanel;
    private javax.swing.JPanel firstname_edit;
    private javax.swing.JLabel firstname_edit_Text;
    private javax.swing.JLabel gelen_Text;
    private javax.swing.JLabel gelen_icon;
    private javax.swing.JLabel gelen_icon1;
    private javax.swing.JLabel gelen_icon3;
    private javax.swing.JLabel gelen_icon4;
    private javax.swing.JPanel gelenpanel;
    private javax.swing.JLabel giden_Text;
    private javax.swing.JPanel gidenpanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel kaydet;
    private javax.swing.JLabel kaydet_text;
    private javax.swing.JPanel kullanici_bilgi_onaylama;
    private javax.swing.JLabel kullanici_ekle;
    private javax.swing.JPanel lastname_edit;
    private javax.swing.JLabel lastname_edit_text;
    private javax.swing.JTable mailTable;
    private javax.swing.JPanel mail_author;
    private javax.swing.JTextArea mail_author_text;
    private javax.swing.JPanel mail_body_message;
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
    private javax.swing.JSeparator mailgonder_field_seperator10;
    private javax.swing.JSeparator mailgonder_field_seperator11;
    private javax.swing.JSeparator mailgonder_field_seperator12;
    private javax.swing.JSeparator mailgonder_field_seperator2;
    private javax.swing.JSeparator mailgonder_field_seperator3;
    private javax.swing.JSeparator mailgonder_field_seperator4;
    private javax.swing.JSeparator mailgonder_field_seperator5;
    private javax.swing.JSeparator mailgonder_field_seperator6;
    private javax.swing.JSeparator mailgonder_field_seperator7;
    private javax.swing.JSeparator mailgonder_field_seperator8;
    private javax.swing.JSeparator mailgonder_field_seperator9;
    private javax.swing.JPanel password_edit;
    private javax.swing.JLabel password_edit_text;
    private javax.swing.JPanel pnl_anasayfa;
    private javax.swing.JPanel pnl_attachments;
    private javax.swing.JPanel pnl_btnclose;
    private javax.swing.JPanel pnl_btnmaximize;
    private javax.swing.JPanel pnl_btnminimize;
    private javax.swing.JSplitPane pnl_gelen_splitpane;
    private javax.swing.JLabel pnl_lastTime;
    private javax.swing.JPanel pnl_mail;
    private javax.swing.JPanel pnl_mail_body;
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
    private javax.swing.JPanel pnl_profil;
    private javax.swing.JPanel pnl_titlebuttons;
    private javax.swing.JPanel pnl_welcomer;
    private javax.swing.JPanel pnl_yonetici;
    private javax.swing.JPanel pnl_yonetici_kullanici_edit;
    private javax.swing.JPanel pnl_yonetici_kullanici_ekle;
    private javax.swing.JLabel profile_ad;
    private javax.swing.JLabel profile_ad1;
    private javax.swing.JTextField profile_ad_text;
    private javax.swing.JPanel profile_body;
    private javax.swing.JLabel profile_icon;
    private javax.swing.JLabel profile_password;
    private javax.swing.JPasswordField profile_password_text;
    private javax.swing.JLabel profile_soyad;
    private javax.swing.JTextField profile_soyad_text;
    private javax.swing.JLabel profile_top_text;
    private javax.swing.JLabel profile_username;
    private javax.swing.JTextField profile_username_text;
    private javax.swing.JSeparator register_seperator_password;
    private javax.swing.JSeparator register_seperator_password1;
    private javax.swing.JSeparator register_seperator_password2;
    private javax.swing.JComboBox<String> rol_list_combo;
    private javax.swing.JLabel taslak_Text;
    private javax.swing.JPanel taslakpanel;
    private javax.swing.JLabel title;
    private javax.swing.JPanel titlebar;
    private javax.swing.JPanel topBar;
    private javax.swing.JLabel txt_LastTime;
    private javax.swing.JLabel txt_anasayfa;
    private javax.swing.JLabel txt_mailkutusu;
    private javax.swing.JTable userTable;
    private javax.swing.JPanel username_edit;
    private javax.swing.JLabel username_edit_text;
    private javax.swing.JLabel welcomeText;
    private javax.swing.JLabel yenimail_icon;
    private javax.swing.JLabel yenimail_text;
    private javax.swing.JPanel yenimailpanel;
    private javax.swing.JTextField yonetici_ad_text;
    private javax.swing.JPanel yonetici_bilgi_onaylama;
    private javax.swing.JComboBox<String> yonetici_edit_combo;
    private javax.swing.JLabel yonetici_edit_id;
    private javax.swing.JLabel yonetici_edit_password;
    private javax.swing.JLabel yonetici_ekle_ad;
    private javax.swing.JTextField yonetici_ekle_ad_text;
    private javax.swing.JLabel yonetici_ekle_password;
    private javax.swing.JPasswordField yonetici_ekle_password_text;
    private javax.swing.JLabel yonetici_ekle_soyad;
    private javax.swing.JTextField yonetici_ekle_soyad_text;
    private javax.swing.JLabel yonetici_ekle_username;
    private javax.swing.JTextField yonetici_ekle_username_text;
    private javax.swing.JPanel yonetici_kullanici_detay;
    private javax.swing.JLabel yonetici_kullanici_detay_text;
    private javax.swing.JPanel yonetici_kullanici_ekle;
    private javax.swing.JLabel yonetici_kullanici_ekle_text;
    private javax.swing.JPanel yonetici_kullanici_sil;
    private javax.swing.JLabel yonetici_kullanici_sil_text;
    private javax.swing.JPanel yonetici_paneli;
    private javax.swing.JLabel yonetici_paneli_text;
    private javax.swing.JLabel yonetici_password;
    private javax.swing.JPasswordField yonetici_password_text;
    private javax.swing.JLabel yonetici_profile_soyad;
    private javax.swing.JPanel yonetici_sidebar;
    private javax.swing.JTextField yonetici_soyad_text;
    private javax.swing.JLabel yonetici_text;
    private javax.swing.JPanel yonetici_user_edit_geri;
    private javax.swing.JPanel yonetici_user_ekle_geri;
    private javax.swing.JPanel yonetici_user_onaylama;
    private javax.swing.JLabel yonetici_username;
    private javax.swing.JTextField yonetici_username_text;
    // End of variables declaration//GEN-END:variables

}

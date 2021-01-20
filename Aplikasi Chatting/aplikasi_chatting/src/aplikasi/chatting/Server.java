package aplikasi.chatting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;


public class Server extends JFrame implements ActionListener {
  JPanel navbar;
  JPanel main;
  JPanel footer;
  JTextArea pesanDisplay;

  static ServerSocket serverSocket;
  static Socket socket;
  static DataInputStream dataInputStream;
  static DataOutputStream dataOutputStream;

  Server() {
    //----- CONSTRUCTOR -----
    //Constructor - Navbar
    navbar = new JPanel();
    navbar.setLayout(null);
    navbar.setBackground(new Color(255, 255, 255));
    navbar.setBounds(0, 0, 450, 70);
    add(navbar);

    //Constructor - Main
    main = new JPanel();
    main.setLayout(null);
    main.setBackground(new Color(255, 255, 255));
    main.setBounds(15, 85, 420, 480);
    main.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
    add(main);

    //Constructor - Footer
    footer = new JPanel();
    footer.setLayout(null);
    footer.setBackground(new Color(0, 0, 0, 32));
    footer.setBounds(0, 580, 450, 200);
    add(footer);
    //----- END CONSTRUCTOR -----


    //----- NAVBAR -----
    //Navbar - Panah Kiri
    ImageIcon panahKiri_1 = new ImageIcon(ClassLoader.getSystemResource("aplikasi/chatting/icons/keyboard-left-arrow-button.png"));
    Image panahKiri_2 = panahKiri_1.getImage().getScaledInstance(20,20, Image.SCALE_DEFAULT);
    ImageIcon panahKiri_3 = new ImageIcon(panahKiri_2);
    JLabel panahKiri_label = new JLabel(panahKiri_3);
    panahKiri_label.setBounds(5, 20, 30,30);
    navbar.add(panahKiri_label);
    //Navbar - Panah Kiri (Action)
    panahKiri_label.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        System.exit(0);
      }
    });

    //Navbar - Foto Profil
    ImageIcon photoProfile_1 = new ImageIcon(ClassLoader.getSystemResource("aplikasi/chatting/icons/round-account-button-with-user-inside.png"));
    Image photoProfile_2 = photoProfile_1.getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT);
    ImageIcon photoProfile_3 = new ImageIcon(photoProfile_2);
    JLabel photoProfile_label = new JLabel(photoProfile_3);
    photoProfile_label.setBounds(40, 5, 60,60);
    navbar.add(photoProfile_label);

    //Navbar - Icon Video Call
    ImageIcon iconVideo_1 = new ImageIcon(ClassLoader.getSystemResource("aplikasi/chatting/icons/phone-call-button.png"));
    Image iconVideo_2 = iconVideo_1.getImage().getScaledInstance(20,20, Image.SCALE_DEFAULT);
    ImageIcon iconVideo_3 = new ImageIcon(iconVideo_2);
    JLabel iconVideo_label = new JLabel(iconVideo_3);
    iconVideo_label.setBounds(300, 5, 60,60);
    navbar.add(iconVideo_label);

    //Navbar - Icon Panggilan
    ImageIcon iconPanggilan_1 = new ImageIcon(ClassLoader.getSystemResource("aplikasi/chatting/icons/videocam-filled-tool.png"));
    Image iconPanggilan_2 = iconPanggilan_1.getImage().getScaledInstance(30,30, Image.SCALE_DEFAULT);
    ImageIcon iconPanggilan_3 = new ImageIcon(iconPanggilan_2);
    JLabel iconPanggilan_label = new JLabel(iconPanggilan_3);
    iconPanggilan_label.setBounds(345, 5, 60,60);
    navbar.add(iconPanggilan_label);

    //Navbar - Icon 3 Dots
    ImageIcon icon3Dots_1 = new ImageIcon(ClassLoader.getSystemResource("aplikasi/chatting/icons/show-more-button-with-three-dots.png"));
    Image icon3Dots_2 = icon3Dots_1.getImage().getScaledInstance(20,20, Image.SCALE_DEFAULT);
    ImageIcon icon3Dots_3 = new ImageIcon(icon3Dots_2);
    JLabel icon3Dots_label = new JLabel(icon3Dots_3);
    icon3Dots_label.setBounds(385, 5, 60,60);
    navbar.add(icon3Dots_label);

    //Navbar - Username
    JLabel username_label = new JLabel("Server Admin");
    username_label.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
    username_label.setForeground(Color.BLACK);
    username_label.setBounds(110, 15, 150, 20);
    navbar.add(username_label);

    //Navbar - Status Aktif
    JLabel statusAktif_label = new JLabel("Sedang Aktif");
    statusAktif_label.setFont(new Font("SAN_SERIF", Font.PLAIN, 15));
    statusAktif_label.setForeground(Color.BLACK);
    statusAktif_label.setBounds(110, 35, 150, 24);
    navbar.add(statusAktif_label);
    //----- END NAVBAR -----

    //----- MAIN -----
    JTextArea pesanDisplay = new JTextArea();
    pesanDisplay.setEditable(false);
    pesanDisplay.setLineWrap(true);
    pesanDisplay.setWrapStyleWord(true);
    pesanDisplay.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
    pesanDisplay.setForeground(new Color(0, 0, 0));
    pesanDisplay.setBounds(5, 5, 410, 470);
    main.add(pesanDisplay);
    //----- END MAIN -----

    //----- FOOTER -----
    //Footer - Text Field
    JTextField pesanField = new JTextField();
    pesanField.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
    pesanField.setForeground(new Color(0, 0, 0));
    pesanField.setBounds(15, 15, 370, 40);
    footer.add(pesanField);

    //Footer - Icon Send
    ImageIcon iconSend_1 = new ImageIcon(ClassLoader.getSystemResource("aplikasi/chatting/icons/send-button.png"));
    Image iconSend_2 = iconSend_1.getImage().getScaledInstance(30,30, Image.SCALE_DEFAULT);
    ImageIcon iconSend_3 = new ImageIcon(iconSend_2);
    JLabel iconSend_label = new JLabel(iconSend_3);
    iconSend_label.setBounds(390, 8, 55,55);
    footer.add(iconSend_label);
    //Footer - Icon Send (Action)
    iconSend_label.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        String pesanOutput = pesanField.getText();
        pesanDisplay.setText(pesanDisplay.getText() +  "\n\t\t\t" + pesanOutput);
        pesanField.setText("");
      }
    });
    //----- END FOOTER -----


    //----- PEMBATAS -----
    JPanel pembatasAtas = new JPanel();
    pembatasAtas.setLayout(null);
    pembatasAtas.setBackground(new Color(0, 0, 0));
    pembatasAtas.setBounds(0, 70, 450, 3);
    add(pembatasAtas);

    JPanel pembatasBawah = new JPanel();
    pembatasBawah.setLayout(null);
    pembatasBawah.setBackground(new Color(0, 0, 0, 96));
    pembatasBawah.setBounds(0, 579, 450, 1);
    add(pembatasBawah);
    //----- END PEMBATAS -----

    //----- BASE PANEL -----
    //getContentPane().setBackground(Color.BLACK);
    setLayout(null);
    setSize(450, 650);
    setLocation(15, 50);
    setUndecorated(true);
    setVisible(true);
    //----- END BASE PANEL -----
  }


  public void actionPerformed(ActionEvent e) {

  }

  public static void main(String[] args) {
    new Server().setVisible(true);
  }

  String pesanInput = "";
  try {
    serverSocket = new ServerSocket(6001);
    socket = serverSocket.accept();
    dataInputStream = new DataInputStream(socket.getInputStream());
    dataOutputStream = new DataOutputStream(socket.getOutputStream());

    pesanInput = dataInputStream.readUTF();
    pesanDisplay.setText(pesanDisplay.getText() + "\n" + pesanInput);

    serverSocket.close();
    socket.close();
  }
  catch(Exception e) {

  }
}

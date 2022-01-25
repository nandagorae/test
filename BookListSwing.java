

import java.awt.event.*;
//import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JTextArea;


public class BookListSwing extends JFrame implements ActionListener {
   JButton btnOk, btnReset;
   JTextArea txtResult, txtStatus;
   JPanel pn1;

   static Connection con;
   Statement stmt;
   ResultSet rs;
   String Driver = "";
   String url = "jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul&useSSL=false";
   String userid = "madang";
   String pwd = "madang";

   public BookListSwing() {
      super("Swing Database");
      layInit();
      conDB();
      setVisible(true);
      setBounds(200, 200, 400, 400); //������ġ,������ġ,���α���,���α���
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }

   public void layInit() {
      btnOk = new JButton("select * from book");
      btnReset = new JButton("ȭ���ʱ�ȭ");
      txtResult = new JTextArea();
      txtStatus = new JTextArea(5,30);
      
      pn1 = new JPanel();
      pn1.add(btnOk);
      pn1.add(btnReset);
      
      txtResult.setEditable(false);
      txtStatus.setEditable(false);
      JScrollPane scrollPane = new JScrollPane(txtResult);
      JScrollPane scrollPane2 = new JScrollPane(txtStatus);
      
      add("North", pn1);
      add("Center", scrollPane);
      add("South", scrollPane2);
      
      btnOk.addActionListener(this);
      btnReset.addActionListener(this);
   }

   public void conDB() {
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         //System.out.println("����̹� �ε� ����");
         txtStatus.append("����̹� �ε� ����...\n");
      } catch (ClassNotFoundException e) {
         e.printStackTrace();
      }
      
      try { /* �����ͺ��̽��� �����ϴ� ���� */
          //System.out.println("�����ͺ��̽� ���� �غ�...");
    	  txtStatus.append("�����ͺ��̽� ���� �غ�...\n");
          con = DriverManager.getConnection(url, userid, pwd);
          //System.out.println("�����ͺ��̽� ���� ����");
          txtStatus.append("�����ͺ��̽� ���� ����...\n");
       } catch (SQLException e1) {
          e1.printStackTrace();
       }
   }

   @Override
   public void actionPerformed(ActionEvent e) {
    
      try {
         stmt = con.createStatement();
         String query = "SELECT * FROM Book ";
         if (e.getSource() == btnOk) {
            txtResult.setText("");
            txtResult.setText("BOOKNO           BOOK NAME       PUBLISHER      PRICE\n");
            rs = stmt.executeQuery(query);
            while (rs.next()) {
               String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getInt(4)
                     + "\n";
               txtResult.append(str);
            }
         } else if (e.getSource() == btnReset) {
            txtResult.setText("");
         }
      } catch (Exception e2) {
         System.out.println("���� �б� ���� :" + e2);
/*      } finally {
         try {
            if (rs != null)
               rs.close();
            if (stmt != null)
               stmt.close();
            if (con != null)
               con.close();
         } catch (Exception e3) {
            // TODO: handle exception
         }
  */
      }

   }

   public static void main(String[] args) {
      BookListSwing BLS = new BookListSwing();
      
      //BLS.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      //BLS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      BLS.addWindowListener(new WindowAdapter() {
    	  public void windowClosing(WindowEvent we) {
    		try {
    			con.close();
    		} catch (Exception e4) { 	}
    		System.out.println("���α׷� ���� ����!");
    		System.exit(0);
    	  }
    	});
   }
}

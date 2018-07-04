import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public class jsWin extends JFrame implements ActionListener,DocumentListener{
	RegisterWindow rwin;
	JButton buttonxsmd,buttonyykc,buttonzjkc,
			buttonxgkc,buttoncjlr,buttonmmxg,buttonluru;
	JTable table;
	JPanel mainpane,kjpane,pane2;
	JTextField field1,field2,field3;
	String get1=null,get2=null,get3=null;
	JLabel label;
	Box box=Box.createHorizontalBox();
	public jsWin() {
		super();
		init();
		this.setVisible(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	String user;
	public void getuser(String user) {
		this.user=user;
	}
	Connection ct;
	public void setconnection(Connection ct ) {
		this.ct=ct;
	}
	private void init() {
		SetWindow();
		SetButton();
	}
	private void SetWindow() {
		this.setBounds(300,150,600,500);
		this.setTitle("��ʦ����");
	}
	private void SetButton() {
		kjpane=new JPanel();
		pane2=new JPanel();
		table=new JTable();
		field1=new JTextField(10);
		field2=new JTextField(10);
		field3=new JTextField(5);
		buttonxsmd=new JButton("ѧ������");
		buttonyykc=new JButton("���пγ�");
		buttonzjkc=new JButton("�����¿γ�");
		buttonxgkc=new JButton("�޸Ŀγ�");
		buttonmmxg=new JButton("�����޸�");
		buttoncjlr=new JButton("�ɼ�¼��");
		buttonluru=new JButton("¼��");
		kjpane.add(buttonyykc);
		kjpane.add(buttonzjkc);
		kjpane.add(buttonxsmd);
		kjpane.add(buttoncjlr);
		kjpane.add(buttonmmxg);
		buttonyykc.addActionListener(this);
		buttonxsmd.addActionListener(this);
		buttonzjkc.addActionListener(this);
		buttonxgkc.addActionListener(this);
		buttonmmxg.addActionListener(this);
		buttoncjlr.addActionListener(this);
		buttonluru.addActionListener(this);
		(field1.getDocument()).addDocumentListener(this);
		(field2.getDocument()).addDocumentListener(this);
		(field3.getDocument()).addDocumentListener(this);
		this.getContentPane().add(pane2,BorderLayout.CENTER);
		this.getContentPane().add(kjpane, BorderLayout.SOUTH);
	}
	public void setlabel() {
		try {
			Statement cx=ct.createStatement();
			ResultSet r=cx.executeQuery("select ����,Ժϵ  from teacher where ����="+user);
			while(r.next()) {
				label=new JLabel("��ӭѧ����"+r.getString(1)+" ���� "+r.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.getContentPane().add(label, BorderLayout.NORTH);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src=e.getSource();
		if(src==buttonxgkc) {
			try {
				Statement findcjd=ct.createStatement();
				ResultSet rs=findcjd.executeQuery(" ");
				table=this.gettable(rs);
				JScrollPane jp=new JScrollPane(table);
				pane2.removeAll();
				pane2.add(jp);
				pane2.repaint();
				this.setVisible(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		else if(src==buttonluru) {
			try {
				Statement findcjd=ct.createStatement();
				JOptionPane.showMessageDialog(this,"¼��ɹ�","��ʾ",JOptionPane.PLAIN_MESSAGE);
				findcjd.executeQuery("update grade set �ɼ�="+get3+"where ѧ��="+get1+"and �γ̺�="+get2);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		else if(src==buttonyykc) {
			try {
				Statement findcjd=ct.createStatement();
				ResultSet rs=findcjd.executeQuery("select �γ���,�꼶,��ʱ,ѧ��,���״̬  from course where ���ν�ʦ="+user);
				table=this.gettable(rs);
				JScrollPane jp=new JScrollPane(table);
				pane2.removeAll();
				pane2.add(jp);
				pane2.repaint();
				this.setVisible(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		else if(src==buttonzjkc) {
			String max=null;
			try {
				Statement findcjd=ct.createStatement();
				ResultSet rs=findcjd.executeQuery("select �γ̺� from course order by �γ̺� desc");
				rs.next();
				max=rs.getString(1);
				max=Integer.toString(Integer.parseInt(max)+1);
				JOptionPane.showMessageDialog(this, "Ϊ���Զ������˿γ̺ţ�"+max,"��ʾ",JOptionPane.WARNING_MESSAGE);
				String xkm=JOptionPane.showInputDialog
						(this,"������γ���","���ӿγ�",JOptionPane.WARNING_MESSAGE);
				String nj=JOptionPane.showInputDialog
						(this,"�������꼶","���ӿγ�",JOptionPane.WARNING_MESSAGE);
				String ks=JOptionPane.showInputDialog
						(this,"�������ʱ","���ӿγ�",JOptionPane.WARNING_MESSAGE);
				String xf=JOptionPane.showInputDialog
						(this,"������ѧ��","���ӿγ�",JOptionPane.WARNING_MESSAGE);
				JOptionPane.showMessageDialog
					(this, "���ӳɹ�����ȴ��������","��ʾ",JOptionPane.WARNING_MESSAGE);
					findcjd.execute
					("insert into course values("+max+",'"+xkm+"',"+user+","+nj+","+ks+","+xf+",'δ���')");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		else if(src==buttonxsmd) {
			try {
				Statement findcjd=ct.createStatement();
				ResultSet rs=findcjd.executeQuery("select ѧ��,����,�Ա�,Ժϵ,רҵ from stu where ѧ�� in(select ѧ�� from grade where �γ̺� in(select �γ̺� from course where ���ν�ʦ="+user+"))");
				table=this.gettable(rs);
				JScrollPane jp=new JScrollPane(table);
				pane2.removeAll();
				pane2.add(jp);
				pane2.repaint();
				this.setVisible(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		else if(src==buttoncjlr) {
			try {
				Statement findcjd=ct.createStatement();
				ResultSet rs=findcjd.executeQuery("select * from grade");
				table=this.gettable(rs);
				JScrollPane jp=new JScrollPane(table);
				pane2.removeAll();
				pane2.add(jp);
				pane2.repaint();
				field1.setText("����ѧ��");
				field2.setText("����γ̺�");
				field3.setText("����ɼ�");
				box.add(field1);
				box.add(field2);
				box.add(field3);
				box.add(buttonluru);
				this.getContentPane().add(box, BorderLayout.NORTH);
				this.setVisible(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		else if(src==buttonmmxg) {
			String newpw=JOptionPane.showInputDialog
					(this,"����������","�޸�����",JOptionPane.WARNING_MESSAGE);
			try {
				Statement findcjd=ct.createStatement();
				findcjd.execute("update teacher set ����="+newpw+"where ����="+user);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog
			(this, "�����޸ĳɹ���","��ʾ",JOptionPane.WARNING_MESSAGE);
		}
		else ;
	}
public JTable gettable(ResultSet rs) {
		DefaultTableModel tbmode = null;
		try {
			tbmode=new DefaultTableModel();
			ResultSetMetaData meta=rs.getMetaData();
			int colcount=meta.getColumnCount();
			for(int i=1;i<=colcount;i++) 
				tbmode.addColumn(meta.getColumnName(i));
			Object[] col=new Object[colcount];
			while(rs.next()) {
				for(int j=1;j<=col.length;j++)
					col[j-1]=rs.getString(j);
				tbmode.addRow(col);	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new JTable(tbmode);
	}
public void changedUpdate(DocumentEvent e) {
	get1=field1.getText();
	get2=field2.getText();
	get3=field3.getText();
}
public void insertUpdate(DocumentEvent e) {
	changedUpdate(e);
}
public void removeUpdate(DocumentEvent e) {
	changedUpdate(e);
}
}


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

public class xsWin extends JFrame implements ActionListener,DocumentListener{
	RegisterWindow rwin;
	JButton buttonxgmm,buttonxk,buttonkb,
			buttoncj,buttonxf,buttonfxk,buttonfcx;
	JTable table;
	JPanel kjpane,pane;
	JLabel label=new JLabel();
	JTextField field;
	String get=null;
	Box box=Box.createHorizontalBox();
	public xsWin() {
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
		this.setTitle("ѧ��ѡ��");
	}
	private void SetButton() {
		kjpane=new JPanel();
		pane=new JPanel();
		table=new JTable();
		field=new JTextField(20);
		buttonxk=new JButton("��ѡ�γ�");
		buttonkb=new JButton("�ҵĿα�");
		buttoncj=new JButton("�ҵĳɼ�");
		buttonxf=new JButton("�ѻ��ѧ��");
		buttonxgmm=new JButton("�޸�����");
		buttonfxk=new JButton("ѡ��");
		buttonfcx=new JButton("�˽��ʦ");
		kjpane.add(buttonxk);
		kjpane.add(buttonkb);
		kjpane.add(buttoncj);
		kjpane.add(buttonxf);
		kjpane.add(buttonxgmm);
		buttonxk.addActionListener(this);
		buttonxgmm.addActionListener(this);
		buttonkb.addActionListener(this);
		buttoncj.addActionListener(this);
		buttonxf.addActionListener(this);
		buttonfxk.addActionListener(this);
		buttonfcx.addActionListener(this);
		(field.getDocument()).addDocumentListener(this);
		this.getContentPane().add(pane,BorderLayout.CENTER);
		this.getContentPane().add(kjpane, BorderLayout.SOUTH);
	}
	public void setlabel() {
		try {
			Statement cx=ct.createStatement();
			ResultSet r=cx.executeQuery("select ����,Ժϵ  from stu where ѧ��="+user);
			while(r.next()) {
				label=new JLabel("��ӭѧ����"+r.getString(1)+" ���� "+r.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.getContentPane().add(label, BorderLayout.NORTH);
	}
	public void actionPerformed(ActionEvent e) {
		Object src=e.getSource();
		if(src==buttoncj) {
			try {
				Statement cx=ct.createStatement();
				ResultSet rs=cx.executeQuery("select course.�γ̺�,course.�γ���,grade.�ɼ� from "
						+ "course,grade where grade.�γ̺�"
						+ "=course.�γ̺� AND grade.ѧ��="+user);
				table=this.gettable(rs);
				JScrollPane jp=new JScrollPane(table);
				pane.removeAll();
				pane.add(jp);
				pane.repaint();
				this.setVisible(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		else if(src==buttonxk) {
			try {
				Statement cx=ct.createStatement();
				ResultSet rs=cx.executeQuery("select �γ̺�,�γ���,���ν�ʦ,��ʱ,ѧ�� from course where ���״̬='�����' AND �꼶 in(select �꼶 from stu where ѧ��="+user+") ");
				table=this.gettable(rs);
				JScrollPane jp=new JScrollPane(table);
				pane.removeAll();
				pane.add(jp);
				field.setText("����γ̺ſ���ѡ��||�����ʦ�����˽��ʦ��Ϣ");
				Box box=Box.createHorizontalBox();
				box.add(field);
				box.add(buttonfcx);
				box.add(buttonfxk);
				this.getContentPane().add(box, BorderLayout.NORTH);
				pane.repaint();
				this.setVisible(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		else if(src==buttonfcx) {
			try {
				Statement cx=ct.createStatement();
				ResultSet r=cx.executeQuery("select ����,�Ա�,Ժϵ from teacher where ����="+get);
				r.next();
				JOptionPane.showMessageDialog(this,"��ʦ����Ϊ"+r.getString(1)+" �Ա��ǣ�"+r.getString(2)+" ���ԣ�"+r.getString(3),"��ʦ��Ϣ",JOptionPane.PLAIN_MESSAGE);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		else if(src==buttonfxk) {
			try {
				Statement cx=ct.createStatement();
				JOptionPane.showMessageDialog(this,"ѡ�γɹ�","��ʾ",JOptionPane.PLAIN_MESSAGE);
				ResultSet r=cx.executeQuery("insert into grade values("+user+","+get+",0)");
				r.next();	
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		else if(src==buttonkb) {
			try {
				Statement cx=ct.createStatement();
				ResultSet rs=cx.executeQuery("select �γ̺�,�γ���,��ʱ,ѧ��  from course where �γ̺� "
						+ "in(select �γ̺� from grade where ѧ��="
						+ user+")");
				table=this.gettable(rs);
				JScrollPane jp=new JScrollPane(table);
				pane.removeAll();
				pane.add(jp);
				pane.repaint();
				this.setVisible(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		else if(src==buttonxf) {
			setlabel();
			try {
				Statement cx=ct.createStatement();
				ResultSet rs=cx.executeQuery("select sum(ѧ��) from course where �γ̺� in(select �γ̺� from grade where ѧ��="+user+")");
				rs.next();
				JOptionPane.showMessageDialog(this,"��ѡ�޵�ѧ��Ϊ��"+rs.getString(1),"ѧ�ֲ�ѯ",JOptionPane.PLAIN_MESSAGE);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		else if(src==buttonxgmm) {
			setlabel();
			String newpw=JOptionPane.showInputDialog
					(this,"����������","�޸�����",JOptionPane.WARNING_MESSAGE);
			try {
				Statement cx=ct.createStatement();
				cx.execute("update stu set ����="+newpw+"where ѧ��="+user);
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
	get=field.getText();
}
public void insertUpdate(DocumentEvent e) {
	changedUpdate(e);
}
public void removeUpdate(DocumentEvent e) {
	changedUpdate(e);
}
}
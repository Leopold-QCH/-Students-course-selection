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
		this.setTitle("教师管理");
	}
	private void SetButton() {
		kjpane=new JPanel();
		pane2=new JPanel();
		table=new JTable();
		field1=new JTextField(10);
		field2=new JTextField(10);
		field3=new JTextField(5);
		buttonxsmd=new JButton("学生名单");
		buttonyykc=new JButton("已有课程");
		buttonzjkc=new JButton("增加新课程");
		buttonxgkc=new JButton("修改课程");
		buttonmmxg=new JButton("密码修改");
		buttoncjlr=new JButton("成绩录入");
		buttonluru=new JButton("录入");
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
			ResultSet r=cx.executeQuery("select 姓名,院系  from teacher where 工号="+user);
			while(r.next()) {
				label=new JLabel("欢迎学生："+r.getString(1)+" 来自 "+r.getString(2));
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
				JOptionPane.showMessageDialog(this,"录入成功","提示",JOptionPane.PLAIN_MESSAGE);
				findcjd.executeQuery("update grade set 成绩="+get3+"where 学号="+get1+"and 课程号="+get2);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		else if(src==buttonyykc) {
			try {
				Statement findcjd=ct.createStatement();
				ResultSet rs=findcjd.executeQuery("select 课程名,年级,课时,学分,审核状态  from course where 开课教师="+user);
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
				ResultSet rs=findcjd.executeQuery("select 课程号 from course order by 课程号 desc");
				rs.next();
				max=rs.getString(1);
				max=Integer.toString(Integer.parseInt(max)+1);
				JOptionPane.showMessageDialog(this, "为您自动分配了课程号："+max,"提示",JOptionPane.WARNING_MESSAGE);
				String xkm=JOptionPane.showInputDialog
						(this,"请输入课程名","增加课程",JOptionPane.WARNING_MESSAGE);
				String nj=JOptionPane.showInputDialog
						(this,"请输入年级","增加课程",JOptionPane.WARNING_MESSAGE);
				String ks=JOptionPane.showInputDialog
						(this,"请输入课时","增加课程",JOptionPane.WARNING_MESSAGE);
				String xf=JOptionPane.showInputDialog
						(this,"请输入学分","增加课程",JOptionPane.WARNING_MESSAGE);
				JOptionPane.showMessageDialog
					(this, "增加成功，请等待教务处审核","提示",JOptionPane.WARNING_MESSAGE);
					findcjd.execute
					("insert into course values("+max+",'"+xkm+"',"+user+","+nj+","+ks+","+xf+",'未审核')");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		else if(src==buttonxsmd) {
			try {
				Statement findcjd=ct.createStatement();
				ResultSet rs=findcjd.executeQuery("select 学号,姓名,性别,院系,专业 from stu where 学号 in(select 学号 from grade where 课程号 in(select 课程号 from course where 开课教师="+user+"))");
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
				field1.setText("输入学号");
				field2.setText("输入课程号");
				field3.setText("输入成绩");
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
					(this,"请输入密码","修改密码",JOptionPane.WARNING_MESSAGE);
			try {
				Statement findcjd=ct.createStatement();
				findcjd.execute("update teacher set 密码="+newpw+"where 工号="+user);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog
			(this, "密码修改成功！","提示",JOptionPane.WARNING_MESSAGE);
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


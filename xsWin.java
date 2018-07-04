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
		this.setTitle("学生选课");
	}
	private void SetButton() {
		kjpane=new JPanel();
		pane=new JPanel();
		table=new JTable();
		field=new JTextField(20);
		buttonxk=new JButton("可选课程");
		buttonkb=new JButton("我的课表");
		buttoncj=new JButton("我的成绩");
		buttonxf=new JButton("已获得学分");
		buttonxgmm=new JButton("修改密码");
		buttonfxk=new JButton("选课");
		buttonfcx=new JButton("了解教师");
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
			ResultSet r=cx.executeQuery("select 姓名,院系  from stu where 学号="+user);
			while(r.next()) {
				label=new JLabel("欢迎学生："+r.getString(1)+" 来自 "+r.getString(2));
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
				ResultSet rs=cx.executeQuery("select course.课程号,course.课程名,grade.成绩 from "
						+ "course,grade where grade.课程号"
						+ "=course.课程号 AND grade.学号="+user);
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
				ResultSet rs=cx.executeQuery("select 课程号,课程名,开课教师,课时,学分 from course where 审核状态='已审核' AND 年级 in(select 年级 from stu where 学号="+user+") ");
				table=this.gettable(rs);
				JScrollPane jp=new JScrollPane(table);
				pane.removeAll();
				pane.add(jp);
				field.setText("输入课程号可以选课||输入教师工号了解教师信息");
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
				ResultSet r=cx.executeQuery("select 姓名,性别,院系 from teacher where 工号="+get);
				r.next();
				JOptionPane.showMessageDialog(this,"教师姓名为"+r.getString(1)+" 性别是："+r.getString(2)+" 来自："+r.getString(3),"教师信息",JOptionPane.PLAIN_MESSAGE);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		else if(src==buttonfxk) {
			try {
				Statement cx=ct.createStatement();
				JOptionPane.showMessageDialog(this,"选课成功","提示",JOptionPane.PLAIN_MESSAGE);
				ResultSet r=cx.executeQuery("insert into grade values("+user+","+get+",0)");
				r.next();	
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		else if(src==buttonkb) {
			try {
				Statement cx=ct.createStatement();
				ResultSet rs=cx.executeQuery("select 课程号,课程名,课时,学分  from course where 课程号 "
						+ "in(select 课程号 from grade where 学号="
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
				ResultSet rs=cx.executeQuery("select sum(学分) from course where 课程号 in(select 课程号 from grade where 学号="+user+")");
				rs.next();
				JOptionPane.showMessageDialog(this,"已选修的学分为："+rs.getString(1),"学分查询",JOptionPane.PLAIN_MESSAGE);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		else if(src==buttonxgmm) {
			setlabel();
			String newpw=JOptionPane.showInputDialog
					(this,"请输入密码","修改密码",JOptionPane.WARNING_MESSAGE);
			try {
				Statement cx=ct.createStatement();
				cx.execute("update stu set 密码="+newpw+"where 学号="+user);
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
	get=field.getText();
}
public void insertUpdate(DocumentEvent e) {
	changedUpdate(e);
}
public void removeUpdate(DocumentEvent e) {
	changedUpdate(e);
}
}
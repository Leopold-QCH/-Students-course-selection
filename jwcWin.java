import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class jwcWin extends JFrame implements ActionListener{
	RegisterWindow rwin;
	JButton buttonsp,buttoncjd,buttoncjfx,
			buttonxkb,buttonspjs,buttonspxs;
	JTable table;
	CardLayout card=new CardLayout(5,5);
	JPanel mainpane,kjpane,pane2;
	public jwcWin() {
		super();
		init();
		this.setVisible(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
		this.setTitle("教务处管理");
	}
	private void SetButton() {
		mainpane=new JPanel(card);
		kjpane=new JPanel();
		pane2=new JPanel();
		table=new JTable();
		buttonsp=new JButton("课程审批");
		buttoncjd=new JButton("成绩单");
		buttoncjfx=new JButton("成绩分析");
		buttonxkb=new JButton("选课表");
		buttonspxs=new JButton("学生名单");
		buttonspjs=new JButton("教师名单");
		kjpane.add(buttoncjd);
		kjpane.add(buttoncjfx);
		kjpane.add(buttonxkb);
		kjpane.add(buttonsp);
		kjpane.add(buttonspjs);
		kjpane.add(buttonspxs);
		mainpane.add(pane2, "p2");
		buttoncjd.addActionListener(this);
		buttonsp.addActionListener(this);
		buttoncjfx.addActionListener(this);
		buttonxkb.addActionListener(this);
		buttonspxs.addActionListener(this);
		buttonspjs.addActionListener(this);
		this.getContentPane().add(mainpane);
		this.getContentPane().add(kjpane, BorderLayout.SOUTH);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src=e.getSource();
		if(src==buttonxkb) {
			try {
				Statement findcjd=ct.createStatement();
				ResultSet rs=findcjd.executeQuery("select stu.学号,stu.姓名,course.课程名\r\n" + 
						"from stu,course,grade\r\n" + 
						"where stu.学号=grade.学号 AND grade.课程号=course.课程号 ");
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
		else if(src==buttoncjd) {
			try {
				Statement findcjd=ct.createStatement();
				ResultSet rs=findcjd.executeQuery("select stu.学号,stu.姓名,course.课程名,grade.成绩\r\n" + 
						"from stu,course,grade\r\n" + 
						"where stu.学号=grade.学号 AND grade.课程号=course.课程号 ");
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
		else if(src==buttoncjfx) {
			try {
				Statement findcjd=ct.createStatement();
				ResultSet rs=findcjd.executeQuery("select course.课程名,max(grade.成绩)as\"最高分\",min(grade.成绩)as\"最低分\"\r\n" + 
						"from stu,course,grade\r\n" + 
						"where stu.学号=grade.学号 AND grade.课程号=course.课程号\r\n" + 
						"group by course.课程名\r\n" + 
						" ");
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
		else if(src==buttonsp) {
			try {
				Statement findcjd=ct.createStatement();
				ResultSet rs=findcjd.executeQuery("select * from course order by 审核状态");
				table=this.gettable(rs);
				JScrollPane jp=new JScrollPane(table);
				pane2.removeAll();
				pane2.add(jp);
				pane2.repaint();
				this.setVisible(true);
				String xk=JOptionPane.showInputDialog
						(this,"请输入需要审批的课程号","课程审批",JOptionPane.WARNING_MESSAGE);
				JOptionPane.showMessageDialog(this,"确定审核通过吗","课程审批",JOptionPane.QUESTION_MESSAGE);
					findcjd.execute("update course set 审核状态='已审核' where 课程号="+xk);
					JOptionPane.showMessageDialog
					(this, "审批成功","提示",JOptionPane.WARNING_MESSAGE);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		else if(src==buttonspjs) {
			try {
				Statement findcjd=ct.createStatement();
				ResultSet rs=findcjd.executeQuery("select * from teacher");
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
		else if(src==buttonspxs) {
			try {
				Statement findcjd=ct.createStatement();
				ResultSet rs=findcjd.executeQuery("select 学号,姓名,性别,院系,专业,年级   from stu");
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
}


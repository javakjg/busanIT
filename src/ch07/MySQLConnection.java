package ch07;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.catalina.valves.ErrorReportValve;

public class MySQLConnection {
	
	private String driver = "org.gjt.mm.mysql.Driver";
	private String url = "jdbc:mysql://127.0.0.1:3306/mydb?useUnicode=true&characterEncoding=EUC_KR";
	private String user = "root";
	private String pwd = "1234";
	Connection con;
	
	public MySQLConnection()
	{
		try
		{
			//DB ���� ����
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, pwd);
			System.out.println("DB ���� ����");
		}
		
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	}
	
	public void listTeam()
	{
		
		try
		{
			String sql ="select * from tblTeam"; //���� ������
			PreparedStatement pstmt = con.prepareStatement(sql);//DB�� ������ �ۼ� �� ��Ȳ
			ResultSet rs = pstmt.executeQuery(); //DB���� Ctrl + Enter�� �ϴ� ����.
			System.out.println("�� ȣ\t�� ��\t������\t�� ��\t����");
			
			while(rs.next())
			{
				int num = rs.getInt("num");
				String name = rs.getString("name");
				String city = rs.getString("city");
				int age = rs.getInt("age");
				String team = rs.getString("team");
				System.out.println(num + "\t"+ name + "\t"+ city + "\t"+ age + "\t"+ team);
			}
			
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void insertTeam(String name, String city, int age, String team)////////////���̺� ������ �ִ� ������/////////////////
	{
		try
		{
			String sql = "insert tblTeam(name, city, age, team) values(?,?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, city);
			pstmt.setInt(3, age);
			pstmt.setString(4, team);
			int cnt = pstmt.executeUpdate();// insert, update, delete���� Ctrl+Enter�ϴ� ����.(����� ���� �����ϴϱ� int��)
			
			if(cnt==1)
				System.out.println("�Է� ����");
			else
				System.out.println("�Է� ����");
			
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	public void updateTeam(String name, int num)////////////���̺� �����͸� �����ϴ� ������/////////////////
	{
		try
		{
			String sql = "update tblTeam set name=? where num=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setInt(2, num);
			int cnt = pstmt.executeUpdate();// insert, update, delete���� Ctrl+Enter�ϴ� ����.(����� ���� �����ϴϱ� int��)
			if(cnt==1)
				System.out.println("���� ����");
			else
				System.out.println("���� ����");
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
	}
	public void deleteTeam(int num)////////////���̺� �����͸� �����ϴ� ������/////////////////
	{
		try
		{
			String sql = "delete from tblTeam where num=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			int cnt = pstmt.executeUpdate();// insert, update, delete���� Ctrl+Enter�ϴ� ����.(����� ���� �����ϴϱ� int��)
			if(cnt==1)
			{
				System.out.println("���� ����");
				reset();
			}
			else
			{
				System.out.println("���� ����");
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
	}
	public void reset() 
	{
	    try
	    {
	      String sql = "ALTER TABLE tblteam AUTO_INCREMENT=1;"; //�ڵ����� �����Ĺ� 
	      PreparedStatement pstmt = con.prepareStatement(sql);
	      pstmt.executeUpdate();

	      String sql1 = "SET @COUNT = 0;";	//�ڵ����� �������� ���� ī��Ʈ �ʱ�ȭ
	      PreparedStatement pstmt1 = con.prepareStatement(sql1);
	      pstmt1.executeUpdate();//insert, update, delete

	      String sql2 = "update tblTeam set tblTeam.num=@COUNT:=@COUNT+1;"; //�̹� �ڵ����� �Ǿ��ִ� ���� ī��Ʈ�� ����.
	      PreparedStatement pstmt2 = con.prepareStatement(sql2);
	      pstmt2.executeUpdate();//insert, update, delete
	    }
	    
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	  }
	public static void main(String[] args)
	{
		MySQLConnection mcon = new MySQLConnection();
//		mcon.insertTeam("�嵿��", "����", 25, "���");
//		mcon.deleteTeam(9);
//		mcon.updateTeam("�ŵ���", 2);
		mcon.listTeam();
	}

}



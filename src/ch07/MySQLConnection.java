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
			//DB 접속 공식
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, pwd);
			System.out.println("DB 연결 성공");
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
			String sql ="select * from tblTeam"; //넣을 쿼리문
			PreparedStatement pstmt = con.prepareStatement(sql);//DB에 쿼리문 작성 한 상황
			ResultSet rs = pstmt.executeQuery(); //DB에서 Ctrl + Enter를 하는 문장.
			System.out.println("번 호\t성 명\t거주지\t나 이\t팀명");
			
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
	
	public void insertTeam(String name, String city, int age, String team)////////////테이블에 데이터 넣는 연동문/////////////////
	{
		try
		{
			String sql = "insert tblTeam(name, city, age, team) values(?,?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, city);
			pstmt.setInt(3, age);
			pstmt.setString(4, team);
			int cnt = pstmt.executeUpdate();// insert, update, delete문을 Ctrl+Enter하는 문장.(적용된 행을 리턴하니까 int문)
			
			if(cnt==1)
				System.out.println("입력 성공");
			else
				System.out.println("입력 실패");
			
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	public void updateTeam(String name, int num)////////////테이블에 데이터를 수정하는 연동문/////////////////
	{
		try
		{
			String sql = "update tblTeam set name=? where num=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setInt(2, num);
			int cnt = pstmt.executeUpdate();// insert, update, delete문을 Ctrl+Enter하는 문장.(적용된 행을 리턴하니까 int문)
			if(cnt==1)
				System.out.println("수정 성공");
			else
				System.out.println("수정 실패");
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
	}
	public void deleteTeam(int num)////////////테이블에 데이터를 삭제하는 연동문/////////////////
	{
		try
		{
			String sql = "delete from tblTeam where num=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			int cnt = pstmt.executeUpdate();// insert, update, delete문을 Ctrl+Enter하는 문장.(적용된 행을 리턴하니까 int문)
			if(cnt==1)
			{
				System.out.println("삭제 성공");
				reset();
			}
			else
			{
				System.out.println("삭제 실패");
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
	      String sql = "ALTER TABLE tblteam AUTO_INCREMENT=1;"; //자동증가 재정렬문 
	      PreparedStatement pstmt = con.prepareStatement(sql);
	      pstmt.executeUpdate();

	      String sql1 = "SET @COUNT = 0;";	//자동증가 재정렬을 위한 카운트 초기화
	      PreparedStatement pstmt1 = con.prepareStatement(sql1);
	      pstmt1.executeUpdate();//insert, update, delete

	      String sql2 = "update tblTeam set tblTeam.num=@COUNT:=@COUNT+1;"; //이미 자동증가 되어있는 열의 카운트를 수정.
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
//		mcon.insertTeam("장동건", "서울", 25, "배우");
//		mcon.deleteTeam(9);
//		mcon.updateTeam("신동엽", 2);
		mcon.listTeam();
	}

}



package file;
import java.io.IOException;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.*;
public class Storeing 
{
	public boolean validate(String uname,String token)
	{    String un="";
		Connection con = null;
        Statement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","kishore", "1234");
			con.setAutoCommit(false);
			stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery("SELECT * FROM auth WHERE  tok='"+token+"';");
			 while (rs.next())
			{
				un=rs.getString(1);
				System.out.println(rs.getString(1));
			}
			stmt.close();
			con.commit();
			con.close();
			if(un.equals(uname))
				return true;
			else
				return false;
			
            } 
		catch (Exception e) {
			System.out.println( e.getClass().getName()+": "+ e.getMessage() );
			System.exit(0);
			}
			return false;
	}
	public void clear(String uname)
	{
		Connection con = null;
        Statement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","kishore", "1234");
			con.setAutoCommit(false);
			stmt = con.createStatement();
			 String sql = "truncate table auth";
            stmt.executeUpdate(sql);
			stmt.close();
			con.commit();
			con.close();
            } 
		catch (Exception e) {
			System.out.println( e.getClass().getName()+": "+ e.getMessage() );
			System.exit(0);
			}
	}
	public void Store(String uname,String code)
	{
		Connection con = null;
        Statement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","kishore", "1234");
			con.setAutoCommit(false);
			stmt = con.createStatement();
			 String sql = "INSERT INTO auth (username,tok) "+ "VALUES('"+uname+"','"+code+"');";
            stmt.executeUpdate(sql);
			stmt.close();
			con.commit();
			con.close();
            } 
		catch (Exception e) {
			System.out.println( e.getClass().getName()+": "+ e.getMessage() );
			System.exit(0);
			}
	}
	public static void main(String args[]){
	    Storeing s=new Storeing();
		s.Store("kishore","true");
		boolean b = s.validate("kishore","69b166d1-ee3f-4f43-a5de-d6979041d");
		System.out.println(b);
		}
		}
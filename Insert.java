import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import file.Storeing;
import org.hibernate.Session;  
import org.hibernate.SessionFactory;  
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import file.Details;
import java.util.*;
public class Insert extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Storeing sto=new Storeing();
		String username = request.getHeader("Authorization");
		String tok = request.getHeader("X-CSRF-TOKEN");
		String name=request.getParameter("name");
		String value=request.getParameter("value");
		if(username.equals(null))
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		if(sto.validate(username,tok)){
			if(check(name)){
			store(name,value);
		    out.print("success");
			}
			else
			out.print("name should be unique");
			}
		else
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		
		}
	void store(String s,String b)
	{
		StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
		Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
		SessionFactory factory = meta.getSessionFactoryBuilder().build();
		Session session = factory.openSession();
		Transaction t = session.beginTransaction(); 
        Details e1=new Details();  
		e1.setName(s);  
		e1.setValue(b);  
		session.save(e1);
		t.commit();	
		factory.close();
		session.close();  
    }
	boolean check(String uname){ 
	try {
		StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
		Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
		SessionFactory factory = meta.getSessionFactoryBuilder().build();
		Session session = factory.openSession(); 
		List li=session.createQuery("from Details d").list();
		Iterator it=li.iterator();
		while(it.hasNext()){
		Object o=(Object)it.next();
        Details e1=(Details) o;  
		if(uname.equals(e1.getName()))
		{
			return false;
			
		}
		}
		
		factory.close();
		session.close(); 
		return true;
		}
		
	catch(Exception e)
	{
	System.out.println(e);
	}
	return false;
	}
	}
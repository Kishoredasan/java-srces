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

public class Modify extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String username = request.getHeader("Authorization");
		String tok = request.getHeader("X-CSRF-TOKEN");
		String name=request.getParameter("name");
		String value=request.getParameter("value");
		Storeing sto=new Storeing();
		if(username.equals(null))
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		if(sto.validate(username,tok)){
			update(name,value);
		    out.print("success");
			}
		else
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		
		}
	void update(String name,String value){
		try
	   {
		StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
		Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
		SessionFactory factory = meta.getSessionFactoryBuilder().build();
		Session session = factory.openSession(); 
		Transaction tx = session.beginTransaction();
		Object o=session.load(Details.class,name);
        Details p=(Details)o;
		p.setValue(value);
		session.update(p);
        System.out.println("Object updated successfully.....!!");
        tx.commit();
		factory.close();
		session.close();
		}
	catch(Exception e)
	{
	System.out.println(e);
	}
	}
	}
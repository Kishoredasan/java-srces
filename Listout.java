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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
public class Listout extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		String username = request.getHeader("Authorization");
		String tok = request.getHeader("X-CSRF-TOKEN");
		JSONObject arr=new JSONObject();
		JSONArray array=new JSONArray();
		Storeing sto=new Storeing();
	try {
		StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
		Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
		SessionFactory factory = meta.getSessionFactoryBuilder().build();
		Session session = factory.openSession(); 
		List li=session.createQuery("from Details d").list();
		Iterator it=li.iterator();
		while(it.hasNext()){
		JSONObject jobj = new JSONObject();
		Object o=(Object)it.next();
        Details e1=(Details) o;  
		jobj.put("name",e1.getName());
		jobj.put("value",e1.getValue());
		array.add(jobj);
		}
		arr.put("row",array);
		factory.close();
		session.close(); 
		}
	catch(Exception e)
	{
	System.out.println(e);
	}
		if(username.equals(null))
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		if(sto.validate(username,tok))
		    out.print(arr);
		else
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		
		}
	}
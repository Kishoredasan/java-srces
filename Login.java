import java.io.IOException;
import java.io.File;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import file.TOTP;
import file.Storeing;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import java.util.UUID;
public class Login extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Storeing sto=new Storeing();
		String s,c;
		String secretKey = getRandomSecretKey();
		String code = getTOTPCode(secretKey);
		s=request.getParameter("name");
		c=request.getParameter("code");
		if(s.equals("kishore"))
			if(c.equals(code)){
				String token = UUID.randomUUID().toString();
				sto.Store("kishore",token);
				out.print(token);
				}
			else 
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		else
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				
		
		
	}
	
	public static String getRandomSecretKey() {
     
        String secretKey = "abcdefghijklmnopqrstuvwxyz";
		
        return secretKey.replace('1', 'I').replace('0', 'O');
    }
	public static String getTOTPCode(String secretKey) {
        String normalizedBase32Key = secretKey.replace(" ", "").toUpperCase();
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(normalizedBase32Key);
        String hexKey = Hex.encodeHexString(bytes);
        long time = (System.currentTimeMillis() / 1000) / 30;
        String hexTime = Long.toHexString(time);
        return TOTP.generateTOTP(hexKey, hexTime, "6");
    }
	}
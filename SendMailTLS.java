import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * SendMailTLS class to instantiate an object which is used to send emails whenever
 * there is a change in the student's registered courses
 */
public class SendMailTLS {
	private final String username = "oodpsucks@gmail.com";
	private final String password = "oodpSUCKS2020";

	private Properties prepProps(){
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		return props;
	}

	/**
	 * Creates a session that possesses the properties for composing  and uses PasswordAuthentication to authenticate the sender
	 * @param ip_Prop The java mail properties
	 * @param ip_ID The email username
	 * @param ip_Pwd The email password
	 * @return The session for java mail
	 */
	private Session createSess(Properties ip_Prop, String ip_ID, String ip_Pwd){
		Session session = Session.getInstance(ip_Prop,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(ip_ID, ip_Pwd);
					}
				});
		return session;
	}

	/**
	 * Sends an email after successfully registering for a course
	 * @param studName The student's name
	 * @param p_CouName The name of course that student registered for
	 * @param p_IndNum The index number that student registered for
	 */
	public void sendAddEmail(String studName,String p_CouName, int p_IndNum) {
		Properties re_props = prepProps();
		Session sess = createSess(re_props, username, password);
		try {

			Message message = new MimeMessage(sess);
			message.setFrom(new InternetAddress("from-email@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("oodpsucks@gmail.com"));
			message.setSubject("Add course success");
			message.setText("Hello, "+studName+"! You have successfully registered for "+p_IndNum+" under "+p_CouName+".");

			System.out.println("\nPlease wait while we send you a notification email...");
			Transport.send(message);
			System.out.println("Done!");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Sends an email after successfully dropping a registered course
	 * @param studName The student's name
	 * @param p_CouName The name of course that student dropped
	 * @param p_IndNum The index number that student dropped
	 */
	public void sendDropEmail(String studName, String p_CouName, int p_IndNum){
		Properties re_props = prepProps();
		Session sess = createSess(re_props, username, password);
		try {

			Message message = new MimeMessage(sess);
			message.setFrom(new InternetAddress("from-email@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse("oodpsucks@gmail.com"));
			message.setSubject("Drop course success");
			message.setText("Hello, "+studName+"! You have successfully dropped "+p_IndNum+" under "+p_CouName);

			System.out.println("\nPlease wait while we send you a notification email...");
			Transport.send(message);
			System.out.println("Done!");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Sends an email after successfully switching the index of a registered course
	 * @param studName The student's name
	 * @param old_IndNum The previous index number of the registered course
	 * @param new_IndNum The updated index number of the registered course
	 * @param p_CouName The name of the registered course
	 */
	public void changeEmail(String studName, int old_IndNum, int new_IndNum, String p_CouName){
		Properties re_props = prepProps();
		Session sess = createSess(re_props, username, password);
		try {

			Message message = new MimeMessage(sess);
			message.setFrom(new InternetAddress("from-email@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse("oodpsucks@gmail.com"));
			message.setSubject("Change course success");
			message.setText("Hello, "+studName+"! You have successfully changed index from "+old_IndNum+" to "+new_IndNum+" under "+p_CouName);

			System.out.println("\nPlease wait while we send you a notification email...");
			Transport.send(message);
			System.out.println("Done!");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Sends an email after successfully swapping index of a registered course with another student
	 * @param studName The student's name
	 * @param peeName The name of student whose index was swapped with
	 * @param oldInd The previous index number of the registered course
	 * @param newInd The updated index number of the registered course
	 */
	public void swapEmail(String studName, String peeName, int oldInd, int newInd){
		Properties re_props = prepProps();
		Session sess = createSess(re_props, username, password);
		try {

			Message message = new MimeMessage(sess);
			message.setFrom(new InternetAddress("from-email@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse("oodpsucks@gmail.com"));
			message.setSubject("Swap course success");
			message.setText("Hello, "+studName+"! You have successfully swapped your original index/class "+oldInd+" to "+newInd+" belonging to "+peeName);

			System.out.println("\nPlease wait while we send you a notification email...");
			Transport.send(message);
			System.out.println("Done!");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Sends email after being added to waitlist of an index
	 * @param studName The student's name
	 * @param indxNum The oversubscribed index number of the course the student is trying to register for
	 * @param couName The name of oversubscribed course the student is trying to register for
	 */
	public void addWaitListEmail(String studName, int indxNum, String couName){
		Properties re_props = prepProps();
		Session sess = createSess(re_props, username, password);
		try {

			Message message = new MimeMessage(sess);
			message.setFrom(new InternetAddress("from-email@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse("oodpsucks@gmail.com"));
			message.setSubject("Added to wait list");
			message.setText("Hello, "+studName+"! You have been added to the wait list for index "+indxNum+" under "+couName);

			System.out.println("\nPlease wait while we send you a notification email...");
			Transport.send(message);
			System.out.println("Done!");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

}
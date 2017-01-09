package mailing;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

/*import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;*/

//70 send email: http://www.rgagnon.com/javadetails/java-0083.html
//http://www.codejava.net/java-ee/javamail/send-e-mail-with-attachment-in-java

/**
 * @author Niklas Karlsson
 * @version 1.0
 * @since 2016-09-15
 */

public interface SentMail extends OnlyOne {

  String AdminEmail = "cykeltur@gmail.com";
  //   String AdminEmail = "flagrans@hotmail.com";

  static boolean sendDelRQ(String userNamn, String email) {

    //_____________________________________________________________________


    String host = "smtp.gmail.com";
    String port = "587";
    String mailFrom = "bike4u2Rent@gmail.com";
    String password = "Just4Fun#";

    //String mailTo = "cykeltur@gmail.com";  //
    String mailTo = AdminEmail;
    String mailinfo = email;
    String subject = "Användare " + userNamn + " önskar del";

    String message = makeNiceEMailtextDel(mailTo, userNamn, mailinfo);

    String[] attachFiles = new String[1];
    attachFiles[0] = "img/bikelogo.png";

    try {
      sendEmailWithAttachments(host, port, mailFrom, password, mailTo, subject, message, attachFiles);
      System.out.println("mailTo " + mailTo);
      System.out.println("subject " + subject);
      System.out.println("message " + message);

//    		
    } catch (Exception ex) {
      ex.printStackTrace();
      return false; //inte ett fullskaligt test..
    }

    return true;

  }


  static boolean sendNewUser(String userNamn, String email) {

    //_____________________________________________________________________


    String host = "smtp.gmail.com";
    String port = "587";
    String mailFrom = "bike4u2Rent@gmail.com";
    String password = "Just4Fun#";

    //String mailTo = "cykeltur@gmail.com";  //
    String mailTo = email;
    String mailinfo = email;
    String subject = "Användare " + userNamn + ", konto är nu registrerat hos bike4u2rent";

    String message = makeNiceEMailtextNewUser(mailTo, userNamn, mailinfo);

    String[] attachFiles = new String[1];
    attachFiles[0] = "img/bikelogo.png";

    try {
      sendEmailWithAttachments(host, port, mailFrom, password, mailTo, subject, message, attachFiles);
      System.out.println("mailTo " + mailTo);
      System.out.println("subject " + subject);
      System.out.println("message " + message);

//
    } catch (Exception ex) {
      ex.printStackTrace();
      return false; //inte ett fullskaligt test..
    }

    return true;

  }


  static String makeNiceEMailtextDel(String mailTo, String userNamn, String mailinfo) {
    System.out.println("make a mail to anmin for DEL");
    System.out.println(mailTo);
    System.out.println(userNamn);
    System.out.println(mailinfo);
    System.out.println("----");
    //String cid = generateCID();
    String mailToNice = changeSpecialCharForHTML(mailTo);
    String userNamnNice = changeSpecialCharForHTML(userNamn);
    String siteNamnNice = changeSpecialCharForHTML(mailinfo);
    String message =
        "<table border=\"0\" cellpadding=\"0\" class=\"MsoNormalTable\" height=\"58\" style=\"background: none repeat scroll 0% 0% rgb(0, 219, 200);\" width=\"100%\">" +
            "<tbody> <tr> " +
            "<th><img src=\"cid:image\"></th>" +
            "<th><big><big><big><big>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
            + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
            + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </big></big></big></big></th>"
            + " <th>&nbsp; "
            + "</th> 		</tr> 			<tr> 			</tr>		</tbody>	</table>"
            + "<p><strong>Hej&nbsp;Admin, uppdrag åt anv:  "
            + userNamn
            + "</strong><br />	H&auml;r kommer uppdraget: "
            +
            "<br />	denna användare vill radera sitt konto:&nbsp; "
            + mailinfo
            + "</p>	<p>&nbsp;</p>	<p>&nbsp;</p>"//;
            + "<br /> <a href=\"http://www.bike4u2Rent.app4solutions.com\"> 	<img src=\"cid:imageFoot\"> </a>";


    return message;
  }

  static String makeNiceEMailtextNewUser(String mailTo, String userNamn, String mailinfo) {
    System.out.println("make a mail to new user");
    System.out.println(mailTo);
    System.out.println(userNamn);
    System.out.println(mailinfo);
    System.out.println("----");
    //String cid = generateCID();
    String mailToNice = changeSpecialCharForHTML(mailTo);
    String userNamnNice = changeSpecialCharForHTML(userNamn);
    String siteNamnNice = changeSpecialCharForHTML(mailinfo);
    String message =
        "<table border=\"0\" cellpadding=\"0\" class=\"MsoNormalTable\" height=\"58\" style=\"background: none repeat scroll 0% 0% rgb(0, 219, 200);\" width=\"100%\">" +
            "<tbody> <tr> " +
            "<th><img src=\"cid:image\"></th>" +
            "<th><big><big><big><big>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
            + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
            + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </big></big></big></big></th>"
            + " <th>&nbsp; "
            + "</th> 		</tr> 			<tr> 			</tr>		</tbody>	</table>"
            + "<p><strong>Hej&nbsp;:  "
            + userNamn
            + "</strong><br />	V&auml;lkommen till bike4u2rent "
            +
            "<br />	ni angav Email:&nbsp; "
            + mailinfo
            + "</p>	<p>&nbsp;</p>	<p>&nbsp;</p>"//;
            + "<br /> <a href=\"http://www.bike4u2Rent.app4solutions.com\"> 	<img src=\"cid:imageFoot\"> </a>";


    return message;
  }


  static String changeSpecialCharForHTML(String textString) {
    String old = textString;
    try {
      textString = textString.replaceAll("&", "&amp;");
      textString = textString.replaceAll("¤", "&curren;");
      textString = textString.replaceAll("Ñ", "&Ntilde;");
      textString = textString.replaceAll("Ü", "&uuml;");
      textString = textString.replaceAll("ß", "&szlig;");
      textString = textString.replaceAll("€", "&euro;");
      textString = textString.replaceAll("Æ", "&AElig;");
      textString = textString.replaceAll("Æ", "&aelig;");
      textString = textString.replaceAll("À", "&agrave;");
      textString = textString.replaceAll("È", "&egrave;");
      textString = textString.replaceAll("É", "&eacute;");
      textString = textString.replaceAll("§", "&sect;");
      textString = textString.replaceAll("°", "&deg;");
      textString = textString.replaceAll("£", "&pound;");
      textString = textString.replaceAll("´", "&acute;");
      textString = textString.replaceAll(">", "&gt;");
      textString = textString.replaceAll("<", "&lt;");
      textString = textString.replaceAll("”", "&rdquo;");
      textString = textString.replaceAll("å", "&aring;");
      textString = textString.replaceAll("ä", "&auml;");//&auml;
      textString = textString.replaceAll("ö", "&ouml;");
      textString = textString.replaceAll("Å", "&Aring;");
      textString = textString.replaceAll("Ä", "&Auml;");
      textString = textString.replaceAll("Ö", "&Ouml;");
      textString = textString.replaceAll("&", "&amp;");
      textString = textString.replaceAll("~", "&rsquo;");
      textString = textString.replaceAll("á", "&aacute;");
      textString = textString.replaceAll("à", "&agrave;");
      textString = textString.replaceAll("Á", "&Aacute;");
      textString = textString.replaceAll("À", "&Agrave;");
      textString = textString.replaceAll("é", "&eacute;");
      textString = textString.replaceAll("è", "&egrave;");
      textString = textString.replaceAll("È", "&Egrave;");
      textString = textString.replaceAll("É", "&Eacute;");
      textString = textString.replaceAll("@", "AT");

      System.out.println("del av mailtext; " + textString);
      return textString;
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return old;
    }


  }

  static void sendEmailWithAttachments(String host, String port,
                                     final String userName, final String password, String toAddress,
                                       String subject, String message, String[] attachFiles)
      throws MessagingException {
    /*
    // sets SMTP server properties
    Properties properties = new Properties();
    properties.put("mail.smtp.host", host);
    properties.put("mail.smtp.port", port);
    properties.put("mail.smtp.auth", "true");

    //properties.put("mail.smtp.starttls.enable", "true"); //google
    properties.put("mail.smtp.starttls.enable", "false"); //old

    properties.put("mail.smtp.starttls.enable", "true"); //google


    properties.put("mail.user", userName);
    properties.put("mail.password", password);
    // creates a new session with an authenticator
    Authenticator auth = new Authenticator() {
      @Override
      public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userName, password);
      }
    };
    Session session = Session.getInstance(properties, auth);

    // creates a new e-mail message
    Message msg = new MimeMessage(session);

    msg.setFrom(new InternetAddress(userName));
    InternetAddress[] toAddresses = {new InternetAddress(toAddress)};
    msg.setRecipients(Message.RecipientType.TO, toAddresses);
    msg.setSubject(subject);
    msg.setSentDate(new Date());

    // creates message part
    MimeBodyPart messageBodyPart = new MimeBodyPart();
    //messageBodyPart.setContent(message, "text/html");
    messageBodyPart.setContent(message, "text/html; charset=UTF-8");

    // creates multi-part
    Multipart multipart = new MimeMultipart();
    multipart.addBodyPart(messageBodyPart);


    // second part (the image)

    messageBodyPart = new MimeBodyPart();
    DataSource fds = new FileDataSource
        ("img/bikelogo-mail.png");
    messageBodyPart.setDataHandler(new DataHandler(fds));
    messageBodyPart.setHeader("Content-ID", "<image>");
    // add it
    multipart.addBodyPart(messageBodyPart);


    messageBodyPart = new MimeBodyPart();
    DataSource fdsFot = new FileDataSource
        ("img/bikelogo-mailfot.png");
    messageBodyPart.setDataHandler(new DataHandler(fdsFot));
    messageBodyPart.setHeader("Content-ID", "<imageFoot>");
    // add it
    multipart.addBodyPart(messageBodyPart);


    if (attachFiles != null && attachFiles.length > 0) {
      for (String filePath : attachFiles) {
        MimeBodyPart attachPart = new MimeBodyPart();

        String newfilepath = filePath;

        try {
          attachPart.attachFile(newfilepath);
        } catch (IOException ex) {
          ex.printStackTrace();
        }

        multipart.addBodyPart(attachPart);
      }
    }

    // sets the multi-part as e-mail's content
    msg.setContent(multipart);

    // sends the e-mail
    Transport.send(msg);
    System.out.println("ot sent : " + message);
*/
  }


}
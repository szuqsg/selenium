package com.springMVC.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

import org.apache.log4j.Logger;

import com.sun.mail.smtp.SMTPTransport;


public class MailSender {

	private static final String CONFIG_FILE = "/system.properties";
	private static Logger logger = Logger.getLogger(MailSender.class.getName());
	private MimeMessage mimeMsg;
	private Session session;
	private Multipart mp;
	private Properties props;
	
	/**
	 * Initialize MailSender with smtp server properties
	 * @throws Exception
	 */
	public MailSender() throws Exception {
		
		loadConfig();
		session = Session.getDefaultInstance(props);
		mimeMsg = new MimeMessage(session);
		mp = new MimeMultipart("mixed");
		
	}
	
	/**
	 * Set email subject
	 * @param mailSubject
	 * @return false when text format error
	 */
	private boolean setSubject(String mailSubject) {
		
		try {
			mimeMsg.setSubject(mailSubject);
			return true;
		} catch (Exception e) {
			logger.info(e.toString());
			return false;
		}
	}

	/**
	 * Set email body with default charset utf8
	 * @param content
	 * @return false when text format error
	 */
	private boolean setBody(String content) {
		
		try {
			BodyPart bp = new MimeBodyPart();
			String contentType = CommUtil.getMessageContentType(content);
			bp.setContent(content, contentType);
			//bp.setContent(content, "text/plain;charset=utf-8");
			mp.addBodyPart(bp);
			return true;
		} catch (Exception e) {
			logger.info(e.toString());
			return false;
		}
	}

	/**
	 * The direct email receivers
	 * @param to
	 * @return false when email address format error
	 */
	private boolean setTo(String to) {
		
		if (to == null || to.trim().length() == 0) return true;
		
		if (!emailFormatValidate(to)) return false;
		to = to.replace(";", ",");
		
		try {
			mimeMsg.setRecipients(Message.RecipientType.TO, (Address[]) InternetAddress.parse(to));
			return true;
		} catch (Exception e) {
			logger.info(e.toString());
			return false;
		}
	}

	/**
	 * The receivers who you want to copy to
	 * @param copyto
	 * @return false when email address format error
	 */
	private boolean setCopyTo(String copyto) {
		
		if (copyto == null || copyto.trim().length() == 0) return true;
		try {
		if (!emailFormatValidate(copyto)) return false;
//		String[] tolist = copyto.split(";");
//		//是否有收件人
//		if(tolist.length>0){		
//			Address[]  addressCC = new InternetAddress[tolist.length]; //收件人
//			
//			for (int i = 0; i < tolist.length; i++) {
//				addressCC[i] = new InternetAddress(tolist[i]);
//			}
//			
//			mimeMsg.setRecipients(Message.RecipientType.CC, addressCC);
//		}	
		
		copyto = copyto.replace(";", ",");
		mimeMsg.setRecipients(Message.RecipientType.CC, (Address[]) InternetAddress.parse(copyto));
			return true;
		} catch (Exception e) {
			logger.info(e.toString());
			return false;
		}
	}
	
	/**
	 * The receivers who you want to blind copy to
	 * @param blindCopyTo
	 * @return false when email address format error
	 */
	private boolean setBlindCopyTo(String blindCopyTo) {
		
		if (blindCopyTo == null || blindCopyTo.trim().length() == 0) return true;
		
		if (!emailFormatValidate(blindCopyTo)) return false;
		blindCopyTo = blindCopyTo.replace(";", ",");
		
		try {
			mimeMsg.setRecipients(Message.RecipientType.BCC, (Address[]) InternetAddress.parse(blindCopyTo));
			return true;
		} catch (Exception e) {
			logger.info(e.toString());
			return false;
		}
	}
	
	/**
	 * Set attachments which you want to sent
	 * @param files
	 * @return false when file attched failed
	 */
	private boolean setAttachments(File[] files) {
		
		if (files == null || files.length == 0) return true;
		
		try {		
			for (File file : files) {
				MimeBodyPart attachment = new MimeBodyPart();
				attachment.setDataHandler(new DataHandler(new FileDataSource(file)));
				String fileName = file.getName();
				attachment.setFileName(fileName.substring(fileName.lastIndexOf(File.separator) + 1));
				mp.addBodyPart(attachment);
			}
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("MailSender.setAttachments Exception",e);
			return false;
		}

	}
	
	
	/**
	 * @author ChangPan
	 * @param arrayInputList
	 * @param mineTypeList
	 * @param fileNameList
	 * @return
	 * @throws Exception
	 */
	public boolean setAttachmentsByByteMineTypeList(List<byte[]> arrayInputList, List<String> mineTypeList,List<String> fileNameList) throws Exception {
		try {
			int i = 0;
			if(arrayInputList!=null){
				for (byte[] arrayInput : arrayInputList) {
					ByteArrayDataSource ds = new ByteArrayDataSource(arrayInput, mineTypeList.get(i));
					MimeBodyPart mimeFile = new MimeBodyPart();
					mimeFile.setDataHandler(new DataHandler(ds));
					mimeFile.setFileName(MimeUtility.encodeText(fileNameList.get(i),"utf-8", "Q"));//support Chinese Language
					mp.addBodyPart(mimeFile);
					i++;
				}
			}
			return true;
		} catch (Exception e) {
			logger.info(e.toString());
			return false;
		}
	}
	/**
	 * Email sending main function
	 * @return false if some exception happened
	 */
	private boolean send() {
		
		try {
			String from = props.getProperty("mail.from");
			String user = props.getProperty("mail.user");
			String password = props.getProperty("mail.password");			
			
			mimeMsg.setFrom(new InternetAddress(from));
			mimeMsg.setSentDate(new Date());
			mimeMsg.setContent(mp);
			mimeMsg.saveChanges();
			
			Transport transport = session.getTransport();
			transport.connect(user, password);
			transport.sendMessage(mimeMsg, mimeMsg.getAllRecipients());
			
			logger.info("send mail success.");
			transport.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.toString());
			return false;
		}
	}
	
	/**
	 * Email response with returning feedback
	 * @author Liky_Pan
	 * @return EmailResponse containing the return code and server response
	 */
	private EmailResponse sendWithResponseFeedback() {
		
		try {
			String from = props.getProperty("mail.from");
			String user = props.getProperty("mail.user");
			String password = props.getProperty("mail.password");
			
			mimeMsg.setFrom(new InternetAddress(from));
			mimeMsg.setSentDate(new Date());
			mimeMsg.setContent(mp);
			mimeMsg.saveChanges();
			
			SMTPTransport transport = (SMTPTransport)session.getTransport();
			transport.connect(user, password);
			transport.sendMessage(mimeMsg, mimeMsg.getAllRecipients());
			
			int lastReturnCode = transport.getLastReturnCode();
			String lastServerResponse = transport.getLastServerResponse();
			
			transport.close();
			
			EmailResponse emailResponse = new EmailResponse();
			emailResponse.setLastReturnCode(lastReturnCode);
			emailResponse.setLastServerResponse(lastServerResponse);
			if(lastReturnCode==250){
				emailResponse.setReportSuccess(true);
			}else{
				emailResponse.setReportSuccess(false);
			}
			emailResponse.setServerRespTimestamp(new Date());
			logger.info("send mail success.");
			return emailResponse;
		} catch (Exception e) {
			logger.info(e.toString());
			EmailResponse emailResponse = new EmailResponse();
			emailResponse.setReportSuccess(false);
			return emailResponse;
		}
	}
	
	/**
	 * send plain text
	 * @param text
	 * @return
	 */
	private boolean send(String text) {
		
		try {
			String from = props.getProperty("mail.from");
			String user = props.getProperty("mail.user");
			String password = props.getProperty("mail.password");
			
			mimeMsg.setFrom(new InternetAddress(from));
			mimeMsg.setSentDate(new Date());
			mimeMsg.setText(text);
			mimeMsg.saveChanges();
			
			Transport transport = session.getTransport();
			transport.connect(user, password);
			transport.sendMessage(mimeMsg, mimeMsg.getAllRecipients());
			
			logger.info("send mail success.");
			transport.close();
			return true;
		} catch (Exception e) {
			logger.info(e.toString());
			return false;
		}
	}
	
	/**
	 * Set all information which need to express
	 * @param to
	 * @param copyto
	 * @param blindCopyTo
	 * @param subject
	 * @param content
	 * @param files
	 * @return false if some exception happened
	 * @throws Exception
	 */
	public boolean sendEmail(String file) throws Exception {
		if(file==null){
			return false;
		}
		String to = props.getProperty("mail.to");
		String copyto = props.getProperty("mail.copyto");
		String blindCopyTo = props.getProperty("mail.blindCopyTo");
		String subject = props.getProperty("mail.subject");
		String content = props.getProperty("mail.content");
		
		if (!this.setTo(to)) return false;
		if (!this.setCopyTo(copyto)) return false;
		if (!this.setBlindCopyTo(blindCopyTo)) return false;
		if (!this.setSubject(subject+"-"+CommUtil.getCurrentDate())) return false;
		if (!this.setBody(content)) return false;
		
		File[] files={new File(file)};
		if (!this.setAttachments(files)) return false;
		
		if (!this.send()) return false;
		
		return true;
	}
	
	/**
	 * Set all information which need to express
	 * @param to
	 * @param copyto
	 * @param blindCopyTo
	 * @param subject
	 * @param content
	 * @param files
	 * @return false if some exception happened
	 * @throws Exception
	 */
	public static boolean sendEmail(String to, String copyto, String blindCopyTo,
			String subject, String content, File[] files) throws Exception {

		MailSender mailSender = new MailSender();
		
		if (!mailSender.setTo(to)) return false;
		if (!mailSender.setCopyTo(copyto)) return false;
		if (!mailSender.setBlindCopyTo(blindCopyTo)) return false;
		if (!mailSender.setSubject(subject)) return false;
		if (!mailSender.setBody(content)) return false;
		if (!mailSender.setAttachments(files)) return false;
		
		if (!mailSender.send()) return false;
		
		return true;
	}
		

	/**
	 * @author ChangPan
	 * @param to
	 * @param copyto
	 * @param blindCopyTo
	 * @param subject
	 * @param content
	 * @param bytesList
	 * @param fileNameList
	 * @return
	 * @throws Exception
	 */
	public static boolean sendEmailWithBytesMineAttach(String to, String copyto, String blindCopyTo,
			String subject, String content, List<String> mineTypeList, List<byte[]> bytesList,List<String> fileNameList) throws Exception {

		MailSender mailSender = new MailSender();
		
		if (!mailSender.setTo(to)) return false;
		if (!mailSender.setCopyTo(copyto)) return false;
		if (!mailSender.setBlindCopyTo(blindCopyTo)) return false;
		if (!mailSender.setSubject(subject)) return false;
		if (!mailSender.setBody(content)) return false;
		if (!mailSender.setAttachmentsByByteMineTypeList(bytesList, mineTypeList, fileNameList)) return false;
		if (!mailSender.send()) return false;
		
		return true;
	}
	
	/**
	 * Send email with returning response
	 * @author Liky_Pan
	 * @param to
	 * @param copyto
	 * @param blindCopyTo
	 * @param subject
	 * @param content
	 * @param mineTypeList
	 * @param bytesList
	 * @param fileNameList
	 * @return
	 * @throws Exception
	 */
	public static EmailResponse sendEmailWithBytesMineAttachWithResponse(String to, String copyto, String blindCopyTo,
			String subject, String content, List<String> mineTypeList, List<byte[]> bytesList,List<String> fileNameList) throws Exception {

		MailSender mailSender = new MailSender();
		EmailResponse response = new EmailResponse();
		if (!mailSender.setTo(to)){
			response.setReportSuccess(false);
			return response;
		}
		if (!mailSender.setCopyTo(copyto)){
			response.setReportSuccess(false);
			return response;
		}
		if (!mailSender.setBlindCopyTo(blindCopyTo)) {
			response.setReportSuccess(false);
			return response;
		}
		if (!mailSender.setSubject(subject)) {
			response.setReportSuccess(false);
			return response;
		}
		if (!mailSender.setBody(content)) {
			response.setReportSuccess(false);
			return response;
		}
		if (!mailSender.setAttachmentsByByteMineTypeList(bytesList, mineTypeList, fileNameList)) {
			response.setReportSuccess(false);
			return response;
		}
		
		return mailSender.sendWithResponseFeedback();
	}
	
	/**
	 * send plain text email
	 * @param to
	 * @param copyto
	 * @param blindCopyTo
	 * @param subject
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static boolean sendTextEmail(String to, String copyto, String blindCopyTo,
			String subject, String content) throws Exception {

		MailSender mailSender = new MailSender();
		
		if (!mailSender.setTo(to)) return false;
		if (!mailSender.setCopyTo(copyto)) return false;
		if (!mailSender.setBlindCopyTo(blindCopyTo)) return false;
		if (!mailSender.setSubject(subject)) return false;
		if (!mailSender.send(content)) return false;
		
		return true;
	}
	
	/**
	 * Email address validating
	 * @param emaillist
	 * @return false if format is invalid
	 */
	private boolean emailFormatValidate(String emaillist) {
        boolean tag = true;
        
        String[] list = emaillist.split(";");
        for (String mail : list) {
        	
        	tag = isPatternValid(mail);
        	if (!tag) break;
        }
        
        return tag;
    }
	
	private boolean isPatternValid(String mail) {
//		return mail.matches(MAIL_PATTERN);
		return mail!=null&&CommUtil.validateEmail(mail);
	}
	
	/**
	 * Load configuration about smtp server
	 * @throws Exception
	 */
	private void loadConfig() throws Exception {
		
		String configPath =  CONFIG_FILE;		
//		FileReader fr = null;
		InputStream in =null;
		try {
			in = PropertiesUtil.class.getResourceAsStream(configPath);
//			fr = new FileReader(configPath); 
			props = new Properties();
			props.load(in);
			
		} catch(Exception e) {
			
			logger.info(e.toString());
		} finally {
			if(in !=null){
				try{
					in.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
//			fr.close();
		}
	}
	
	public static void sendRegularEmail(final String to, final String copyto, final String blindCopyTo, final String subject,
			final String content, final List<byte[]> bytesList,final List<String> mineTypeList, final List<String> fileNameList){
		Thread thread = new Thread(new Runnable() {
			public void run() {
				logger.debug("=========================Send Email Start==================================");
				boolean state = false;
				try {
					state = sendEmailWithBytesMineAttach(to, copyto, blindCopyTo, subject, content, mineTypeList, bytesList, fileNameList);
				} catch (Exception e) {
					e.printStackTrace();
				}
				logger.debug("==============Send Email End with state = "+ state+" ===========================");
			}
		});
		thread.start();
	}
	

}

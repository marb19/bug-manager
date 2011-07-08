/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: SmtpConsoleAspect.aj 282 2010-10-31 03:07:22Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-10-30 22:07:22 -0500 (Sat, 30 Oct 2010) $
 * Last Version      : $Revision: 282 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.utils;

import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;
import java.util.Iterator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 *
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 282 $
 */
public aspect SmtpConsoleAspect perthis(sendingMail(JavaMailSenderImpl)) {

    private static final Log LOGGER =
            LogFactory.getLog(SmtpConsoleAspect.class);

    private SimpleSmtpServer smtpServer;

    private static void logMessages(SimpleSmtpServer smtpServer,
            Class<?> withinType) {
        Log logger = LogFactory.getLog(withinType);

        if(logger.isInfoEnabled()) {
            Iterator<SmtpMessage> messages =
                    (Iterator<SmtpMessage>)smtpServer.getReceivedEmail();

            while(messages.hasNext()) {
                logger.info("======EMAIL SENT======");
                SmtpMessage msg = messages.next();

                Iterator<String> headers =
                        (Iterator<String>)msg.getHeaderNames();
                while(headers.hasNext()) {
                    String header_name = headers.next();
                    String[] header_values = msg.getHeaderValues(header_name);
                    for(String hvalue : header_values) {
                        logger.info("[header] " + header_name + ": " + hvalue);
                    }
                }

                String body = msg.getBody();
                String[] body_lines = body.split("\\s*(\r?\n|\r)");

                for(String line : body_lines) {
                    logger.info("[body] " + line);
                }

                logger.info("=======END EMAIL======");
            }
        }
    }

    pointcut sendingMail(JavaMailSenderImpl sender) : target(sender)
            && call(public void send(..));

    before(JavaMailSenderImpl sender) : sendingMail(sender) {
        smtpServer = null;
        String host = sender.getHost();
        int port = sender.getPort();
        if("localhost".equals(host) && port >= 1024) {
            smtpServer = SimpleSmtpServer.start(port);
            if(smtpServer.isStopped()) {
                LOGGER.warn("Unable to grab service port " + port);
                smtpServer = null;
            }
        }
    }

    after() : sendingMail(JavaMailSenderImpl) {
        if(smtpServer != null) {
            smtpServer.stop();
            logMessages(smtpServer, thisJoinPointStaticPart.getSourceLocation().
                    getWithinType());
        }
    }

}

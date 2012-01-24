/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: UserAccessBizOpImpl.java 197 2010-10-09 20:07:05Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-10-09 15:07:05 -0500 (Sat, 09 Oct 2010) $
 * Last Version      : $Revision: 197 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.biz.impl;

import java.io.StringWriter;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import mx.itesm.gda.bm.biz.UserAccessBizOp;
import mx.itesm.gda.bm.biz.UserAccessBizConfig;
import mx.itesm.gda.bm.model.User;
import mx.itesm.gda.bm.model.dao.UserDAO;
import mx.itesm.gda.bm.utils.WebUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.tools.ToolManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

/**
 *
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 197 $
 */
@Scope("prototype")
@Component
public class UserAccessBizOpImpl extends AbstractBizOp
        implements UserAccessBizOp {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private Random random;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private VelocityEngineFactoryBean velocityEngine;

    @Autowired
    private ToolManager toolManager;

    @Autowired
    private UserAccessBizConfig config;

    @Autowired
    private MessageSourceAccessor msgSource;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public boolean checkValidUser(String username) {
        return userDAO.findByUserName(username) != null;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public boolean validateUserLogin(String username, String password) {
        User u = userDAO.findByUserName(username);
        return (u != null && password != null
                && password.equals(u.getPassword()));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public boolean checkUserAdministrator(String username) {
        User u = userDAO.findByUserName(username);
        return (u != null && u.isAdministrator());
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public boolean initiatePasswordRecovery(String email) {
        User u = userDAO.findByEmail(email);
        if(u == null) {
            return false;
        }

        String ticket = new BigInteger(160, random).toString(36);
        Calendar expiration = Calendar.getInstance();
        expiration.add(Calendar.DAY_OF_MONTH, 1);
        u.setPasswordRecoveryTicket(ticket);
        u.setPasswordRecoveryExpiration(expiration.getTime());
        userDAO.update(u);

        sendUserEmail(u, msgSource.getMessage(
                "UserAccessBizOp.recoveryLinkMailSubject"),
                config.getRecoveryLinkMailTemplate());

        return true;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public boolean validatePasswordRecoveryTicket(String pwd_ticket) {
        User u = userDAO.findByRecoveryTicket(pwd_ticket);
        if(u == null) {
            return false;
        }

        u.setPassword(WebUtils.generatePassword(random));
        u.setPasswordRecoveryTicket(null);
        u.setPasswordRecoveryExpiration(null);
        userDAO.update(u);

        sendUserEmail(u, msgSource.getMessage(
                "UserAccessBizOp.recoveryNewPasswordMailSubject"),
                config.getRecoveryNewPasswordMailTemplate());

        return true;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public Map<String, ?> getUserModel(String username) {
        Map<String, Object> m = null;
        User u = userDAO.findByUserName(username);
        if(u != null) {
            m = new HashMap<String, Object>();
            m.put("userName", u.getUserName());
            m.put("fullName", u.getFullName());
            m.put("email", u.getEmail());
            m.put("administrator", u.isAdministrator());
            m.put("permissions", u.getPermissions());
        }
        return m;
    }

    private void sendUserEmail(final User u, final String mailSubject,
            final String mailTemplate) {

        MimeMessagePreparator p = new MimeMessagePreparator() {

            @Override
            public void prepare(MimeMessage msg) throws Exception {
                MimeMessageHelper h = new MimeMessageHelper(msg);
                h.setTo(new InternetAddress(u.getEmail(), u.getFullName()));
                h.setFrom(config.getRecoveryMailFrom());
                h.setSubject(mailSubject);
                Map<String, Object> m = new HashMap<String, Object>();
                m.put("user", u);
                m.put("mailSubject", mailSubject);
                m.put("hostContextPath", WebUtils.getWebAppUrl(request));
                VelocityContext ctx = new VelocityContext(m,
                        toolManager.createContext());
                StringWriter html_writer = new StringWriter();
                velocityEngine.createVelocityEngine().mergeTemplate(
                        mailTemplate, "UTF-8", ctx, html_writer);
                String html_text = html_writer.toString();
                h.setText(html_text, true);
            }

        };

        mailSender.send(p);
    }

}

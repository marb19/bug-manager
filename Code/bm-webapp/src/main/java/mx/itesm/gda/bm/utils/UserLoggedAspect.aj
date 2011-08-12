/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: UserLoggedAspect.aj 282 2010-10-31 03:07:22Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-10-30 22:07:22 -0500 (Sat, 30 Oct 2010) $
 * Last Version      : $Revision: 282 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.utils;

import java.util.HashMap;
import java.util.Map;
import mx.itesm.gda.bm.biz.UserAccessBizOp;
import mx.itesm.gda.bm.controllers.AccessDeniedException;
import mx.itesm.gda.bm.controllers.ControllerException;
import mx.itesm.gda.bm.session.UserLoginSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.aspectj.AnnotationTransactionAspect;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 282 $
 */
public aspect UserLoggedAspect {

    declare precedence : AnnotationTransactionAspect, UserLoggedAspect, *;

    public interface LoginAble {
    }

    declare parents : @Controller mx.itesm.gda.bm.controllers..*
            implements LoginAble;

    @Autowired
    private UserLoginSession LoginAble.loginSession;

    @Autowired
    private UserAccessBizOp LoginAble.userAccessBizOp;

    @Autowired
    private MessageSourceAccessor LoginAble.msgSource;

    private Map<String, Object> LoginAble.userModel;

    @ModelAttribute("user")
    private Map<String, Object> LoginAble.getUserModel() {
        if(userModel == null) {
            userModel = new HashMap<String, Object>();
        }
        return userModel;
    }

    private void LoginAble.doLogout() {
        if(loginSession.getLoggedUserName() != null) {
            loginSession.setLoggedUserName(null);
            getUserModel().clear();
        }
    }

    private void LoginAble.validateUserLogin(boolean admin_required) {
        String username = loginSession.getLoggedUserName();
        if(username == null || !userAccessBizOp.checkValidUser(username)) {
            throw new AccessDeniedException(msgSource.getMessage(
                    "UserLoggedAspect.loginRequired"));
        }

        if(admin_required
                && !userAccessBizOp.checkUserAdministrator(username)) {
            loginSession.setLoggedUserName(null);
            throw new AccessDeniedException(msgSource.getMessage(
                    "UserLoggedAspect.adminRequired"));
        }

        Map<String, Object> um = getUserModel();
        um.clear();
        um.putAll(userAccessBizOp.getUserModel(username));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ModelAndView LoginAble.accessDeniedHandler(AccessDeniedException ade) {
        loginSession.setLoggedUserName(null);
        loginSession.setLoginMessage(ade.getMessage());
        getUserModel().clear();
        return new ModelAndView("redirect:userLogin.do");
    }

    pointcut annotatedControllers() : execution(* LoginAble+.*(..))
            && @annotation(RequestMapping);

    pointcut annotatedUserLoggedControllers(UserLogged userLoggedAnn) :
            annotatedControllers() && @annotation(userLoggedAnn);

    pointcut autoLogout(UserLogged userLoggedAnn) :
            annotatedUserLoggedControllers(userLoggedAnn)
            && if(userLoggedAnn.autoLogout());

    pointcut autoValidateLogin(UserLogged userLoggedAnn) :
            annotatedUserLoggedControllers(userLoggedAnn)
            && if(userLoggedAnn.value() && !userLoggedAnn.autoLogout());

    before(LoginAble that) : autoLogout(UserLogged) && target(that) {
        that.doLogout();
    }

    before(UserLogged userLoggedAnn, LoginAble that) :
            autoValidateLogin(userLoggedAnn) && target(that) {
        that.validateUserLogin(userLoggedAnn.adminRequired());
    }

    after() throwing(Exception e) : annotatedControllers() {
        if(e instanceof ControllerException) {
            throw (ControllerException)e;
        }
        throw new ControllerException(e);
    }

}

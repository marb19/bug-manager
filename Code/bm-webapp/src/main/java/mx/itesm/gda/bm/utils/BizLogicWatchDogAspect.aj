/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: BizLogicWatchDogAspect.aj 282 2010-10-31 03:07:22Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-10-30 22:07:22 -0500 (Sat, 30 Oct 2010) $
 * Last Version      : $Revision: 282 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.utils;

import java.io.PrintStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import mx.itesm.gda.bm.biz.BizOp;
import mx.itesm.gda.bm.controllers.ControllerException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.SourceLocation;
import org.springframework.stereotype.Controller;

/**
 *
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 282 $
 */
public aspect BizLogicWatchDogAspect {

    private static final Log LOGGER = LogFactory.getLog(
            BizLogicWatchDogAspect.class);

    declare error : controllerDAOError() : "Controllers cannot call DAOs";

    declare error : bizopModelParams() :
            "BizOps cannot accept DAO model parameters";

    declare error : bizopModelLeakage() :
            "BizOps cannot return DAO model values";

    declare error : bizopIndependence() : "BizOps cannot call other BizOps";

    declare warning : callSysOutPrint() : "Invalid System.out interaction";

    pointcut controllerDAOError() : @within(Controller)
            && call(* mx.itesm.gda.bm.model..*.*(..));

    pointcut bizopModelParams() :
            execution(public * BizOp+.*(.., mx.itesm.gda.bm.model..*, ..));

    pointcut bizopModelLeakage() :
            execution(public mx.itesm.gda.bm.model..* BizOp+.*(..));

    pointcut bizopIndependence() :
            within(BizOp+) && call(public * BizOp+.*(..));

    pointcut bizOpCall() : execution(public * BizOp+.*(..));

    pointcut callSysOutPrint() : 
            (within(mx.itesm.gda.bm.biz..*) ||
            within(mx.itesm.gda.bm.controllers..*) ||
            within(mx.itesm.gda.bm.model..*) ||
            within(mx.itesm.gda.bm.session..*)) &&
            call(public * PrintStream+.*(..));

    pointcut systemOutPrint(PrintStream that) : callSysOutPrint()
            && target(that) && if(that == System.out);

    private void actuallyThrowException(String msg,
            JoinPoint.StaticPart thisJoinPointStaticPart) {
        SourceLocation sl = thisJoinPointStaticPart.getSourceLocation();
        String m = msg + " at " + sl.getFileName() + ":" + sl.getLine();
        throw new ControllerException(m);
    }

    before() : controllerDAOError() {
        actuallyThrowException("Controllers cannot call DAOs",
                thisJoinPointStaticPart);
    }

    before() : bizopModelParams() {
        actuallyThrowException("BizOps cannot accept DAO model parameters",
                thisJoinPointStaticPart);
    }

    before() : bizopModelLeakage() {
        actuallyThrowException("BizOps cannot return DAO model values",
                thisJoinPointStaticPart);
    }

    before() : bizopIndependence() {
        actuallyThrowException("BizOps cannot call other BizOps",
                thisJoinPointStaticPart);
    }

    before() : systemOutPrint(PrintStream+) {
        actuallyThrowException("Invalid System.out interaction",
                thisJoinPointStaticPart);
    }

    after() returning(Object returnValue) : bizOpCall() {
        SourceLocation sl = thisJoinPointStaticPart.getSourceLocation();

        Map<String, Object> to_check = new HashMap<String, Object>();
        to_check.put("returnValue", returnValue);

        Map<String, String> leaks = new HashMap<String, String>();

        while(!to_check.isEmpty()) {
            Map<String, Object> checking = to_check;
            to_check = new HashMap<String, Object>();
            for(Map.Entry<String, Object> entry : checking.entrySet()) {
                String key = entry.getKey();
                Object that = entry.getValue();
                if(that == null) {
                    continue;
                }

                String classname = that.getClass().getName();
                if(classname.startsWith("mx.itesm.gda.bm.model.")) {
                    String msg = sl.getFileName() + ":" + sl.getLine()
                            + " is leaking DAO model " + key + " -> "
                            + classname;
                    LOGGER.error(msg);
                    leaks.put(key, classname);
                }

                if(that instanceof Collection) {
                    Collection c = (Collection)that;
                    int i = 0;
                    for(Object v : c) {
                        to_check.put(key + "[" + (i++) + "]", v);
                    }
                } else if(that instanceof Map) {
                    Map<Object, Object> map = (Map)that;
                    for(Map.Entry e : map.entrySet()) {
                        Object my_key = e.getKey();
                        to_check.put(key + "#" + my_key, my_key);
                        to_check.put(key + "." + my_key, e.getValue());
                    }
                } else if(that instanceof Object[]) {
                    Object[] a = (Object[])that;
                    for(int i = 0; i < a.length; i++) {
                        to_check.put(key + "[" + i + "]", a[i]);
                    }
                }
            }
        }

        if(!leaks.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append(sl.getFileName()).
                    append(":").
                    append(sl.getLine()).
                    append(" is leaking DAO model ");

            for(Map.Entry<String, String> entry : leaks.entrySet()) {
                sb.append(entry.getKey()).
                        append(" -> ").
                        append(entry.getValue()).
                        append("; ");
            }

            throw new ControllerException(sb.substring(0, sb.length() - 2));
        }
    }

}

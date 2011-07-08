/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: JpaIdSetter.java 309 2010-11-10 02:10:56Z inzunzo $
 * Last Revised By   : $Author: inzunzo $
 * Last Checked In   : $Date: 2010-11-09 20:10:56 -0600 (Tue, 09 Nov 2010) $
 * Last Version      : $Revision: 309 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.test.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.easymock.IArgumentMatcher;
import org.springframework.beans.BeanUtils;

/**
 *
 * @author $Author: inzunzo $
 * @version $Revision: 309 $
 */
public class JpaIdSetter_1 implements IArgumentMatcher {

    private Object value;

    public JpaIdSetter_1(Object new_value) {
        value = new_value;
    }

    private static AccessibleObject findAnnotatedId(Class<?> clazz) {
        Class<?> uber_class = clazz;
        while(uber_class != null) {
            Field[] fields = uber_class.getDeclaredFields();
            for(Field field : fields) {
                if(field.isAnnotationPresent(Id.class)) {
                    return field;
                }
            }
            Method[] methods = uber_class.getDeclaredMethods();
            for(Method method : methods) {
                if(method.isAnnotationPresent(Id.class)) {
                    return method;
                }

            }
            uber_class = uber_class.getSuperclass();
        }
        return null;
    }

    private static void setValue(Object that, AccessibleObject ao, Object value)
            throws IllegalAccessException, InvocationTargetException {
        if(ao instanceof Field) {
            Field f = (Field)ao;
            if(!f.isAccessible()) {
                f.setAccessible(true);
            }
            f.set(that, value);
        } else {
            Method m = (Method)ao;
            PropertyDescriptor desc = BeanUtils.findPropertyForMethod(m);
            Method setter = desc.getWriteMethod();
            if(!setter.isAccessible()) {
                setter.setAccessible(true);
            }
            setter.invoke(that, value);
        }
    }

    @Override
    public boolean matches(Object that) {
        if(that == null) {
            return false;
        }

        Class<?> that_class = that.getClass();
        if(!that_class.isAnnotationPresent(Entity.class)) {
            return false;
        }

        AccessibleObject ao = findAnnotatedId(that_class);
        if(ao == null) {
            return false;
        }

        try {
            setValue(that, ao, value);
        } catch(Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public void appendTo(StringBuffer buffer) {
        buffer.append("setId(").append(value.toString()).append(")");
    }

}

/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   All Rights Reserved
 *
 * @(#)$Id: ModelUtils.java 152 2010-10-04 16:49:06Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-10-04 11:49:06 -0500 (Mon, 04 Oct 2010) $
 * Last Version      : $Revision: 152 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.model.utils;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Utility class to generate standard equals() and hashCode() implementation
 * using business keys.
 * For more info see: {@link https://www.hibernate.org/451.html}
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 152 $
 */
public class ModelUtils {

    private static final int USE_IN_EQUALS = 1;

    private static final int USE_IN_HASHCODE = 2;

    private static final Map<String, Reference<List<AccessibleObject>>> cache =
            new HashMap<String, Reference<List<AccessibleObject>>>();

    private static ReferenceQueue<Object> queue = new ReferenceQueue<Object>();

    public static boolean equals(Object a, Object b) {
        if(a == b) {
            return true;
        }

        Class<?> clazz = a != null ? a.getClass() : null;

        if(a == null || b == null || clazz != b.getClass()) {
            return false;
        }

        EqualsBuilder e_builder = new EqualsBuilder();

        for(AccessibleObject member : getAccessibleObjects(clazz,
                USE_IN_EQUALS)) {
            try {
                if(member instanceof Field) {
                    Field field = (Field)member;
                    e_builder.append(field.get(a), field.get(b));

                } else {
                    Method method = (Method)member;
                    e_builder.append(method.invoke(a), method.invoke(b));

                }

            } catch(InvocationTargetException ite) {
                rethrowITE(ite);

            } catch(IllegalAccessException iae) {
            }

            if(!e_builder.isEquals()) {
                break;

            }

        }

        return e_builder.isEquals();

    }

    public static int hashCode(Object that) {
        Class<?> clazz = that.getClass();

        HashCodeBuilder h_builder = new HashCodeBuilder();

        for(AccessibleObject member : getAccessibleObjects(clazz,
                USE_IN_HASHCODE)) {
            try {
                if(member instanceof Field) {
                    Field field = (Field)member;
                    h_builder.append(field.get(that));

                } else {
                    Method method = (Method)member;
                    h_builder.append(method.invoke(that));

                }

            } catch(InvocationTargetException ite) {
                rethrowITE(ite);

            } catch(IllegalAccessException iae) {
            }

        }

        return h_builder.toHashCode();

    }

    private static void rethrowITE(InvocationTargetException that_ite) {
        Throwable that_cause = that_ite.getCause();

        if(that_cause instanceof RuntimeException) {
            throw (RuntimeException)that_cause;

        } else {
            throw new RuntimeException(that_cause);

        }

    }

    private static List<AccessibleObject> getAccessibleObjects(
            Class<?> my_class, int that_filter) {

        String key = my_class.getName() + that_filter;
        List<AccessibleObject> ret_list = null;

        synchronized(cache) {
            Reference<?> ref = queue.poll();
            if(ref != null) {
                Collection<?> cache_values = cache.values();
                for(; ref != null; ref = queue.poll()) {
                    cache_values.remove(ref);
                    ref = queue.poll();

                }

            }

            if(cache.containsKey(key)) {
                ret_list = cache.get(key).get();

            }

            if(ret_list == null) {
                ret_list = new ArrayList<AccessibleObject>();

                Class<?> that_class = my_class;

                do {
                    Field[] those_fields = that_class.getDeclaredFields();
                    for(Field that_field : those_fields) {
                        BusinessKey that_bk =
                                that_field.getAnnotation(BusinessKey.class);

                        if(that_bk != null && filter(that_bk, that_filter)) {

                            if(!that_field.isAccessible()) {
                                that_field.setAccessible(true);

                            }

                            ret_list.add(that_field);

                        }

                    }

                    Method[] those_methods = that_class.getDeclaredMethods();
                    for(Method that_method : those_methods) {
                        BusinessKey that_bk = that_method.getAnnotation(
                                BusinessKey.class);

                        if(that_bk != null && filter(that_bk, that_filter) && that_method.
                                getParameterTypes().length == 0) {

                            if(!that_method.isAccessible()) {
                                that_method.setAccessible(true);

                            }

                            ret_list.add(that_method);

                        }

                    }

                    that_class = that_class.getSuperclass();

                } while(that_class != null);

                cache.put(key, new SoftReference<List<AccessibleObject>>(
                        ret_list, queue));

            }

        }

        return ret_list;

    }

    private static boolean filter(BusinessKey bk, int filter) {
        switch(filter) {
            case USE_IN_EQUALS:
                return bk.useInEquals();
            case USE_IN_HASHCODE:
                return bk.useInHashCode();
        }

        return false;

    }

}

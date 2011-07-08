/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: BusinessKey.java 152 2010-10-04 16:49:06Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-10-04 11:49:06 -0500 (Mon, 04 Oct 2010) $
 * Last Version      : $Revision: 152 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.model.utils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to identify a business key.
 * For more info see: {@link https://www.hibernate.org/451.html}
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 152 $
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Documented
public @interface BusinessKey {

    boolean useInEquals() default true;

    boolean useInHashCode() default true;

}

/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: EasyMockFactory.java 280 2010-10-30 16:53:44Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-10-30 11:53:44 -0500 (Sat, 30 Oct 2010) $
 * Last Version      : $Revision: 280 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.test.mocks;

import org.easymock.EasyMock;
import org.junit.Ignore;
import org.springframework.beans.factory.config.AbstractFactoryBean;

/**
 *
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 280 $
 */
@Ignore
public class EasyMockFactory_1<T> extends AbstractFactoryBean<T> {

    private final Class<T> objectType;

    public EasyMockFactory_1(Class<T> clazz) {
        objectType = clazz;
    }

    @Override
    protected T createInstance() throws Exception {
        T mock = EasyMock.createMock(objectType);
        return mock;
    }

    @Override
    public Class<?> getObjectType() {
        return objectType;
    }

}

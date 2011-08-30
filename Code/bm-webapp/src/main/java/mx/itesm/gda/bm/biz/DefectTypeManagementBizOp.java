/* ***************************************************************************
 *   Copyright (c) 2011 BM
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: NewUserController.java 312 2010-11-11 06:36:43Z inzunzo $
 * Last Revised By   : $Author: inzunzo $
 * Last Checked In   : $Date: 2010-11-11 00:36:43 -0600 (Thu, 11 Nov 2010) $
 * Last Version      : $Revision: 312 $
 *
 * Original Author   : Humberto Garcia Robles
 * Notes             :
 * ************************************************************************ */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.itesm.gda.bm.biz;

import java.util.List;
import java.util.Map;

/**
 *
 * @author CDE-Marco
 */
public interface DefectTypeManagementBizOp extends BizOp{
    
    public List<Map<String, ?>> retrieveDefectTypes();
    
    public int createDefectType(String defectTypeName, String defectTypeDescription);
    
    public void modifyDefectType(int defectTypeID, String defectTypeName, String defectTypeDescription);
    
    public void deleteDefectType(int defectTypeID);
    
    public Map<String, ?> getDefectType(int defectTypeID);
    
}

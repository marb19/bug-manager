/* ***************************************************************************
 *   Copyright (c) 2011 BM
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: ReviewType.java 279 2010-10-30 15:43:29Z lalo.campos@gmail.com $
 * Last Revised By   : $Author: lalo.campos@gmail.com $
 * Last Checked In   : $Date: 2010-10-30 10:43:29 -0500 (Sat, 30 Oct 2010) $
 * Last Version      : $Revision: 0 $
 *
 * Original Author   : Eduardo Campos
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.model;

/**
 *
 * @author $Author: lalo.campos@gmail.com $
 * @version $Revision: 0 $
 */

public enum TaskType {

    REQUIREMENTS(true, false, false, false, false, false, false, false),
    DESIGN(false, true, false, false, false, false, false, false),
    DEVELOPMENT(false, false, true, false, false, false, false, false),
    PERSONAL_REVIEW(false, false, false, true, false, false, false, false),
    PEER_REVIEW(false, false, false, false, true, false, false, false),
    WALKTHROUGH(false, false, false, false, false, true, false, false),
    INSPECTION(false, false, false, false, false, false, true, false),
    TESTING(false, false, false, false, false, false, false, true);

    private final boolean requirements;
    
    private final boolean design;

    private final boolean development;

    private final boolean personalReview;

    private final boolean peerReview;

    private final boolean walk;

    private final boolean inspection;

    private final boolean testing;

    TaskType(boolean my_requirements, boolean my_design, boolean my_development, boolean my_personalReview,
            boolean my_peerReview, boolean my_walk, boolean my_inspection, boolean my_testing){
        requirements = my_requirements;
        design = my_design;
        development = my_development;
        personalReview = my_personalReview;
        peerReview = my_peerReview;
        walk = my_walk;
        inspection = my_inspection;
        testing = my_testing;
    }


    /**
     * @return the development task
     */
     public boolean isRequirements(){
         return requirements;
     }


    /**
     * @return the development task
     */
     public boolean isDesign(){
         return design;
     }

    /**
     * @return the development task
     */
     public boolean isDevelopment(){
         return development;
     }

     /**
     * @return the personal review task
     */
     public boolean isPersonalReview(){
         return personalReview;
     }

     /**
     * @return the peer review task
     */
     public boolean isPeerReview(){
         return peerReview;
     }

     /**
     * @return the walk task
     */
     public boolean isWalk(){
        return walk;
     }

     /**
     * @return the inspection task
     */
     public boolean isInspection(){
         return inspection;
     }

     /**
     * @return the testing task
     */
     public boolean isTesting(){
         return testing;
     }
}


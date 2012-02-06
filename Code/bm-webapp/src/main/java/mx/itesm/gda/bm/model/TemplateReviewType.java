/* ***************************************************************************
 *   Copyright (c) 2011 BM
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: TemplateReviewType.java 279 2010-10-30 15:43:29Z zerocoolx@gmail.com $
 * Last Revised By   : $Author: zerocoolx@gmail.com $
 * Last Checked In   : $Date: 2010-10-30 10:43:29 -0500 (Sat, 30 Oct 2010) $
 * Last Version      : $Revision: 0 $
 *
 * Original Author   : Humberto Garcia
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.model;

/**
 *
 * @author $Author: lalo.campos@gmail.com $
 * @version $Revision: 0 $
 */

public enum TemplateReviewType {

    PERSONAL_REVIEW(true, false, false, false),
    PEER_REVIEW(false, true, false, false),
    WALKTHROUGH(false, false, true, false),
    INSPECTION(false, false, false, true);

    private final boolean personalReview;

    private final boolean peerReview;

    private final boolean walk;

    private final boolean inspection;

    TemplateReviewType(boolean my_personalReview, boolean my_peerReview,
            boolean my_walk, boolean my_inspection){
        personalReview = my_personalReview;
        peerReview = my_peerReview;
        walk = my_walk;
        inspection = my_inspection;
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

}


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

public enum PhaseType {

    REQUIREMENTS(true, false, false, false, false, false),
    DESIGN(false, true, false, false, false, false),
    CODING(false, false, true, false, false, false),
    REVIEW(false, false, false, true, false, false),
    TESTING(false, false, false, false, true, false),
    MAINTENANCE(false, false, false, false, false, true);

    private final boolean requirements;

    private final boolean design;

    private final boolean coding;

    private final boolean review;

    private final boolean testing;

    private final boolean maintenance;

    PhaseType(boolean my_requirements, boolean my_design, boolean my_coding, boolean my_review,
            boolean my_testing, boolean my_maintenance){
        requirements = my_requirements;
        design = my_design;
        coding = my_coding;
        review = my_review;
        testing = my_testing;
        maintenance = my_maintenance;
    }

    /**
     * @return the requirements phase
     */
    public boolean isRequirements(){
        return requirements;
    }

    /**
     * @return the design phase
     */
    public boolean isDesign(){
        return design;
    }

    /**
     * @return the coding phase
     */
    public boolean isCoding(){
        return coding;
    }

    /**
     * @return the review phase
     */
    public boolean isReview(){
        return review;
    }

    /**
     * @return the testing phase
     */
    public boolean isTesting(){
        return testing;
    }

    /**
     * @return the maintenance phase
     */
    public boolean isMaintenance(){
        return maintenance;
    }
}

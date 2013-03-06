/*
 * Cell for storing the affine gap dp table infor
 */
package GenomicsAlign;

/**
 *
 * @author Joshua
 */
public class DP_CellSDI{
    int subScore;
    int insScore;
    int delScore;
    
    public DP_CellSDI()
    {
        InitializeScores();
    }
    
    //initialize scores to 0 when created.
    public void InitializeScores()
    {
        this.subScore = 0;
        this.insScore = 0;
        this.delScore = 0;
    }

}

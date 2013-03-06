/*
 * Dynamic programming cell lite for doing linear gap
 */
package GenomicsAlign;

/**
 *
 * @author Joshua
 */
public class DP_Cell {
    int score;
    
    public DP_Cell()
    {
        score = 0;
    }
    public void setScore(int s)
    {
        score = s;
    }
    
    public int getScore()
    {
        return score;
    }

}

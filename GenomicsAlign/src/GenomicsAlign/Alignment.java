
package GenomicsAlign;
import java.util.ArrayList;
/**
 *
 * @author Joshua
 */
//Interface for alignments
public interface Alignment {
    public boolean align(DP_TableSDI myTable);
    public DP_Table backtrackcount(DP_TableSDI myTable);
    public ArrayList<AlignedSequences> backtrack(DP_TableSDI myTable);
}

/*
 * Writs the report for each of the alignment types
 */
package GenomicsAlign;
import java.util.ArrayList;
/**
 *
 * @author Joshua
 */
public class IOWriter {
    
    public void printReportGlobalAffineGap(DP_TableSDI dptable, ArrayList<AlignedSequences> als, Parameters p)
    {
        System.out.println("******************************************");
        System.out.println("Alignment type: Global Affine Gap");
        System.out.println("Scores:\t"+ 
                String.format("match = %s, mismatch = %s, h = %s, g = %s",
                p.match,p.mismatch,p.h,p.g));
        System.out.println(String.format("Sequence 1 = \"%s\", length = %s characters",
                dptable.seq1.header,dptable.seq1.sequence.length()));
        System.out.println(String.format("Sequence 2 = \"%s\", length = %s characters",
                dptable.seq2.header,dptable.seq2.sequence.length()));
        System.out.println();

        System.out.println(als.get(0));
        
        
    }
    
        public void printReportLocalAffineGap(DP_TableSDI dptable, ArrayList<AlignedSequences> als, Parameters p)
    {
        System.out.println("******************************************");
        System.out.println("Alignment type: Local Affine Gap");
        System.out.println("Scores:\t"+ 
                String.format("match = %s, mismatch = %s, h = %s, g = %s",
                p.match,p.mismatch,p.h,p.g));
        System.out.println(String.format("Sequence 1 = \"%s\", length = %s characters",
                dptable.seq1.header,dptable.seq1.sequence.length()));
        System.out.println(String.format("Sequence 2 = \"%s\", length = %s characters",
                dptable.seq2.header,dptable.seq2.sequence.length()));
        System.out.println();
        for(int i = 0; i < als.size(); i++)
        {   System.out.println(als.get(i));
            System.out.println();
        }
        
    }
}

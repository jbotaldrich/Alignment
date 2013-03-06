/*
Dynamic progamming table including substitution,insertion,deletion.
 */
package GenomicsAlign;
import java.util.ArrayList;
/**
 *
 * @author Joshua
 */
public class DP_TableSDI {
        Sequence seq1;
        Sequence seq2;
	int m;
	int n;
	DP_CellSDI[][] m_table;
	Alignment m_align;
	
        
        
	public DP_TableSDI(Sequence seq1, Sequence seq2)
	{
		this.seq1 = seq1;
		this.seq2 = seq2;
		m = seq1.sequence.length()+1;
		n = seq2.sequence.length()+1;
		InitializeTable();

	}
        
        public DP_TableSDI(Sequence seq1, Sequence seq2, Alignment al)
	{
		this.seq1 = seq1;
		this.seq2 = seq2;
		m = seq1.sequence.length()+1;
		n = seq2.sequence.length()+1;
                this.m_align = al;
		InitializeTable();
                
	}
	
        //Initialize cells to 0
        public void InitializeTable()
        {
            m_table = new DP_CellSDI[m][n];
            for(int i = 0; i < m; i++)
            {   
                for(int j = 0; j < n;j++)
                {
                    m_table[i][j] = new DP_CellSDI();
                }
            }
        }
        
        //Use the asigned alignment fucntion
	public boolean Getalignment()
	{
		return m_align.align(this);
	}
        
        //Perform the backtrack and return the alignment in an array compatible 
        //for local
        public ArrayList<AlignedSequences> BacktrackAlignment()
        {
          return m_align.backtrack(this);
        }
	
        //sets the alignmetn funciton to be used by the table
	public void setAlignment(Alignment a)
	{
		m_align = a;
	}
	
        
	public DP_CellSDI getcell(int i, int j)
        {
            return m_table[i][j];
        }
        
        public int getm()
	{
		return m;
	}
	
	public int getn()
	{
		return n;
	}
	
        //Gets an element from the 
	public char GetElement(Sequence seq, int i)
	{
            if(i <= seq.sequence.length() && i > 0)
            {
                return seq.sequence.charAt(i-1);
            }
            return '-';
	}
	        
        @Override public String toString()
        {
            String s = "\t";
            for(int i = 0; i < n; i++)
            {
                s+= GetElement(seq2,i) + "\t";
            }
            s+= "\n";
            for(int i = 0; i < m; i++)
            {
                s+= GetElement(seq1, i) + "\t";
                for(int j=0; j < n; j++)
                {
                    s += m_table[i][j].subScore + ",\t";
                }
                s+= "\n";
            }
            return s;
        }

}

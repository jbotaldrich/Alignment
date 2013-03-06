/*
 * Global alignment function
 */
package GenomicsAlign;
import java.util.ArrayList;
/**
 *
 * @author Joshua
 */
public class GlobalAlignment implements Alignment{
    Parameters param;
    
    public GlobalAlignment(Parameters param)
    {
        this.param = param;
    }
 
    //check for a match
    private int substitution(char c1, char c2)
    {
        if(c1 == c2)
        {
            return param.getMatch();
        }
        else
        {
            return param.getMismatch();
        }
    }

        //count the number of alignment possible
    public DP_Table backtrackcount(DP_TableSDI mytable)
    {
        int mi = mytable.m;
        int ni = mytable.n;

        DP_Table dt = new DP_Table(mi, ni);

        dt.setcell(1, mi-1, ni-1);
        for(int i = mi-1; i >0; i--)
        {
            for(int j = ni-1; j > 0; j--)
            {
                int curr_val = dt.getcell(i,j);
                if(curr_val > 0)
                {
                    
                    int sub = mytable.m_table[i][j].subScore;
                    int del = mytable.m_table[i][j].delScore;
                    int ins = mytable.m_table[i][j].insScore;
                    int max = Math.max(sub,Math.max(del,ins));

                    if(sub == max)
                        dt.setcell(dt.getcell(i-1,j-1)+ curr_val, i-1, j-1);

                    if(del == max)
                        dt.setcell(dt.getcell(i-1,j)+ curr_val, i-1, j);

                    if(ins == max)
                        dt.setcell(dt.getcell(i,j-1)+curr_val, i, j-1);
                }
            }
        }
        return dt;
    }
    
    //performs the backtrack
    public ArrayList<AlignedSequences> backtrack(DP_TableSDI mytable)
    {
        int i = mytable.m-1;
        int j = mytable.n-1;
        
        AlignedSequences alseq;
        String s1 = "";
        String s2 = "";
        String match = "";
        int countmatch = 0;
        int countmismatch = 0;
        int countopeningGap = 0;
        int countGap = 0;
        
        while(i != 0 && j != 0)
        {
            int max = Tmax(mytable.m_table[i][j]);
            char c1 = mytable.GetElement(mytable.seq1,i);
            char c2 = mytable.GetElement(mytable.seq2,j);

            if(mytable.m_table[i][j].subScore == max)
            {
                s1 += c1;
                s2 += c2;
                if(c1 == c2)
                {
                    match += "|";
                    countmatch++;
                }
                else
                {
                    match += " ";
                    countmismatch++;
                }
                i = i - 1;
                j = j - 1;
            }
            else if(mytable.m_table[i][j].delScore == max)
            {   
                if(Tmax(mytable.m_table[i-1][j]) != mytable.m_table[i-1][j].delScore)
                {   
                    countopeningGap++;
                }
                countGap++;
                
                s1 += c1;
                s2 += "-";
                match += " ";
                i--;
            }
            else if(mytable.m_table[i][j].insScore == max)
            {
                if(Tmax(mytable.m_table[i][j-1]) != mytable.m_table[i][j-1].insScore)
                {   
                    countopeningGap++;
                }
                countGap++;
                s1 += "-";
                s2 += c2;
                match += " ";
                j--;
            }
        }
        alseq = new AlignedSequences(reverseString(s1), reverseString(s2),
                reverseString(match), mytable.seq1.header,mytable.seq2.header, 
                Tmax(mytable.m_table[mytable.m-1][mytable.n-1]), countmatch,
                countmismatch, countopeningGap, countGap, 1, 1);
        
        ArrayList<AlignedSequences> alList = new ArrayList<>();
        alList.add(alseq);
        return alList;
        
    }
    
    //reverses the string that is generated in teh backtrack
    private String reverseString(String seqToRev)
    {
        String s1rev = "";
        int len = seqToRev.length()-1;
        for(int i = len; i >=0; i--)
        {
            s1rev += seqToRev.charAt(i);
        }
        return s1rev;
    }

    /**
 *
 * @param myTable
 * @return
 */
    public boolean align(DP_TableSDI myTable)
    {
        int mp1 = myTable.m;
        int np1 = myTable.n;
        myTable.m_table[1][0].subScore = -100000;
        myTable.m_table[0][1].subScore = -100000;
        myTable.m_table[1][0].delScore = param.h + param.g;
        myTable.m_table[0][1].insScore = param.h + param.g;
        myTable.m_table[0][1].delScore = -100000;
        myTable.m_table[1][0].insScore = -100000;

        for(int i = 2; i < mp1; i++)
        {
            myTable.m_table[i][0].subScore = -100000;
            myTable.m_table[i][0].delScore += myTable.m_table[i-1][0].delScore + 
                    param.g;
            myTable.m_table[i][0].insScore = -100000;
        }
        for(int j = 2; j < np1; j++)
        {
            myTable.m_table[0][j].subScore = -100000;
            myTable.m_table[0][j].insScore += myTable.m_table[0][j-1].insScore + 
                    param.g;
            myTable.m_table[0][j].delScore = -100000;
        }

        for(int i = 1; i < mp1; i++)
        {
            for(int j= 1; j < np1; j++)
            {

                myTable.m_table[i][j].subScore = Tmax(myTable.m_table[i-1][j-1])
                        + substitution(myTable.GetElement(myTable.seq1,i),myTable.GetElement(myTable.seq2,j));

                myTable.m_table[i][j].delScore = Math.max(
                        myTable.m_table[i-1][j].delScore + param.g,
                        Tmax(myTable.m_table[i-1][j]) + param.h + param.g);

                myTable.m_table[i][j].insScore = Math.max(
                        myTable.m_table[i][j-1].insScore + param.g,
                        Tmax(myTable.m_table[i][j-1]) + param.h + param.g);
            }
        }
        return true;
    }

    private int Tmax(DP_CellSDI sdi)
    {
            return Math.max(sdi.subScore, 
                        Math.max(sdi.delScore,
                        sdi.insScore));
    }

}
        

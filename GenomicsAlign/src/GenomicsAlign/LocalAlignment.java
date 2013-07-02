/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GenomicsAlign;

import java.util.ArrayList;

/**
 *
 * @author Joshua
 */
public class LocalAlignment implements Alignment {
    
    Parameters param;
    
    public LocalAlignment(Parameters param)
    {
        this.param = param;
    }
 
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
    //counts the nubmer of alignments
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
                    int max = Math.max(sub,Math.max(del,Math.max(0,ins)));
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
    
    //finds the max value in teh array and returns the index
    private int[] getMaxIndex(DP_TableSDI table)
    {
        int currMax = 0;
        int[] maxIndex = new int[2];
        for(int i = 0; i < table.m; i++)
        {
            for(int j = 0; j < table.n;j++)
            {
                int tempMax = Tmax(table.m_table[i][j]);
                if(tempMax > currMax)
                {
                    currMax = tempMax;
                    maxIndex[0] = i;
                    maxIndex[1] = j;
                }
            }
        }
        return maxIndex;
    }
    
    //helper method which actually performs the local backtrack
    private AlignedSequences backtrackhelp(DP_TableSDI mytable)
    {
        AlignedSequences alseq;
        String s1 = "";
        String s2 = "";
        String match = "";
        int countmatch = 0;
        int countmismatch = 0;
        int countopeningGap = 0;
        int countGap = 0;

        int[] maxIndex = getMaxIndex(mytable);
        int i = maxIndex[0];
        int j = maxIndex[1];

        while(Tmax(mytable.m_table[i][j])!= 0)
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
                if(Tmax(mytable.m_table[i-1][j]) == mytable.m_table[i-1][j].delScore)
                {   
                    countGap++;
                }
                else
                {
                    countopeningGap++;
                }

                s1 += c1;
                s2 += "-";
                match += " ";
                i--;
            }
            else if(mytable.m_table[i][j].insScore == max)
            {
                if(Tmax(mytable.m_table[i][j-1]) == mytable.m_table[i][j-1].insScore)
                {   
                    countGap++;
                }
                else
                {
                    countopeningGap++;
                }
                s1 += "-";
                s2 += c2;
                match += " ";
                j--;
            }
        }
        if(i == 0 && i == 0)
        {
            i = 0;
            j = 0;
        }
        alseq = new AlignedSequences(reverseString(s1), 
                reverseString(s2),
                reverseString(match), mytable.seq1.header,mytable.seq2.header, 
                Tmax(mytable.m_table[maxIndex[0]][maxIndex[1]]), countmatch,
                countmismatch, countopeningGap, countGap, i+1, j+1);
        
        return alseq;
    }
    // uses the backtrackhelp function to align up to 3 local alignment depending
    //on how well each successive one performs.
    public ArrayList<AlignedSequences> backtrack(DP_TableSDI mytable)
    {
        ArrayList<AlignedSequences> alseq = new ArrayList<>();
        AlignedSequences als = backtrackhelp(mytable);
        alseq.add(als);
        int s1len = (als.s1).replace("-","").length();
        int s2len = (als.s2).replace("-","").length();
        boolean mshortest = mytable.m < mytable.n;
        //Check if this is over 90% if so return
        if(mshortest && (float)(s1len) / (float)mytable.m > 0.9)
        {
            return alseq;
        }
        else if(!mshortest && (float)(s2len) / (float)mytable.n > 0.9)
        {
            return alseq;
        }
        
        //find the lower and upper squares outside of previous alignment
        int[] tablemax = getMaxIndex(mytable);
        DP_TableSDI uppercorner;
        int[] uppermax;
        boolean hasupper = !(tablemax[0] - alseq.get(0).startpositions1 == 1 ||
                tablemax[1] - alseq.get(0).startpositions2 == 1);
        
        boolean haslower = !(tablemax[0] == mytable.m-1  || tablemax[1] == mytable.n-1);
        if(hasupper)
        {
            mytable.m_table = null;
            Sequence s2_1 = new Sequence();
            s2_1.header = mytable.seq1.header;
            s2_1.sequence = mytable.seq1.sequence.substring(0, tablemax[0] - s1len-1);
            Sequence s2_2 = new Sequence();
            s2_2.header = mytable.seq2.header;
            s2_2.sequence = mytable.seq2.sequence.substring(0, tablemax[1] - s2len-1);

            uppercorner = new DP_TableSDI(s2_1,s2_2);
            align(uppercorner);
            uppermax = getMaxIndex(uppercorner);
        }
        else
        {
            uppercorner = null;
            uppermax = null;
        }
        DP_TableSDI lowercorner;
        int[] lowermax;

        if(haslower)
        {
            Sequence s3_1 = new Sequence();
            s3_1.header = mytable.seq1.header;
            s3_1.sequence = mytable.seq1.sequence.substring(tablemax[0]+1, mytable.m-1);
            Sequence s3_2 = new Sequence();
            s3_2.header = mytable.seq2.header;
            s3_2.sequence = mytable.seq2.sequence.substring(tablemax[1]+1, mytable.n-1);

            lowercorner = new DP_TableSDI(s3_1, s3_2);
            align(lowercorner);
            lowermax = getMaxIndex(lowercorner);
        }
        else
        {
            lowercorner = null;
            lowermax = null;
        }

            //Add one of the two alignments check 90% again
        if(hasupper && haslower)
        {
            boolean addUpper = (Tmax(uppercorner.m_table[uppermax[0]][uppermax[1]]) > 
               Tmax(lowercorner.m_table[lowermax[0]][lowermax[1]]));
            if(addUpper)
            {
                AlignedSequences al2 = backtrackhelp(uppercorner);
                alseq.add(al2);
            }
            else
            {
               AlignedSequences al2 = backtrackhelp(lowercorner);
               al2.startpositions1 = tablemax[0] + lowermax[0] - al2.s1.replace("-","").length();
               al2.startpositions2 = tablemax[1] + lowermax[1] - al2.s2.replace("-","").length();
               alseq.add(al2);
            }
            s1len += alseq.get(1).s1.replace("-","").length();
            s2len += alseq.get(1).s2.replace("-","").length();
            if(mshortest && (float)(s1len) / (float)mytable.m > 0.9)
            {
                return alseq;
            }
            else if(!mshortest && (float)(s2len) / (float)mytable.n > 0.9)
            {
                return alseq;
            }
            else
            {
                //Still no 90% so add the remaining alignment
                if(!addUpper)
                {
                    AlignedSequences al2 = backtrackhelp(uppercorner);
                    alseq.add(al2);
                }
                else
                {
                   AlignedSequences al2 = backtrackhelp(lowercorner);
                   al2.startpositions1 = tablemax[0] + lowermax[0] - al2.s1.replace("-","").length();
                   al2.startpositions2 = tablemax[1] + lowermax[1] - al2.s2.replace("-","").length();
                   alseq.add(al2);
                }
            }
        }
        else if(hasupper)
        {
            AlignedSequences al2 = backtrackhelp(uppercorner);
            alseq.add(al2);
        }
        else if(haslower)
        {
            AlignedSequences al2 = backtrackhelp(lowercorner);
            alseq.add(al2);
        }
        return alseq;
        
    }
    
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
 * Performs the smith-waterman local alignment
 * @param myTable
 * @return
 */
    public boolean align(DP_TableSDI myTable)
    {
        int mp1 = myTable.m;
        int np1 = myTable.n;


        for(int i = 0; i < mp1; i++)
        {
            myTable.m_table[i][0].subScore = 0;
            myTable.m_table[i][0].delScore = 0;
            myTable.m_table[i][0].insScore = 0;
        }
        for(int j = 0; j < np1; j++)
        {
            myTable.m_table[0][j].subScore = 0;
            myTable.m_table[0][j].insScore = 0;
            myTable.m_table[0][j].delScore = 0;
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

    //gets the max max value in a particular cell.
    private int Tmax(DP_CellSDI sdi)
    {
            return Math.max(sdi.subScore, 
                        Math.max(sdi.delScore,
                        Math.max(0,sdi.insScore)));
    }
}

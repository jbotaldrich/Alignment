package GenomicsAlign;

/*
 * Object which stores the results of a backtrack
 */
import java.util.LinkedList;
/**
 *
 * @author Joshua
 */
public class AlignedSequences {
    String s1;
    String s2;
    String match;
    String seq1name;
    String seq2name;
    int startpositions1;
    int startpositions2;
    int optmscore;
    int cmatches;
    int cmismatches;
    int copengap; 
    int cgapcontinue;
    
    public AlignedSequences(String s1, String s2,String match,
        String seq1name,String seq2name, int optmscore,
        int matches, int mismatches, int opengap, int gapcontinue, 
        int startpositions1, int startpositions2)
    {
        this.s1 = s1;
        this.s2 = s2;
        this.match = match;
        this.seq1name = seq1name;
        this.seq2name = seq2name;
        this.optmscore = optmscore;
        this.cmatches = matches;
        this.cmismatches = mismatches;
        this.copengap = opengap; 
        this.cgapcontinue = gapcontinue;
        this.startpositions1 = startpositions1;
        this.startpositions2 = startpositions2;
    }
    
    //Generates the report information as a toString
    @Override public String toString()
    {
        String buff = "";
        int seq1count = startpositions1-1;
        int seq2count = startpositions2-1;
        int maxlength = Math.max(s1.length(), s2.length());
        String seq1nametemp = seq1name;
        String seq2nametemp = seq2name;
        int namediff = seq1nametemp.length() - seq2nametemp.length();
        if(seq1nametemp.length() > seq2nametemp.length())
        {
            for(int i = 0; i < namediff; i++)
                seq2nametemp += " ";
        }
        else
        {
            for(int i = 0; i < -namediff; i++)
                seq1nametemp += " ";
        }    
        for(int j = 0; j <(maxlength/60)+ 1; j++ )
        {
            String wspace = seq1nametemp + ":\t" + (seq1count + 1) + "\t";
            buff += wspace;
            int spaceLength = wspace.length();
            for(int l = j*60; l < (j+1)*60 && l < s1.length(); l++)
            {
                if(s1.charAt(l)!= '-')
                    seq1count++;
                
                buff += s1.charAt(l);
            }
            buff += "\t" + seq1count +"\n";
        
            for(int l = 0; l < spaceLength-2; l++)
            {
                buff += " ";
            }
            buff += "\t\t";
            for(int l = j*60; l < (j+1)*60 && l < match.length(); l++)
            {
                buff += match.charAt(l);
            }
            buff += "\n";
            buff += seq2nametemp + ":\t" + (seq2count + 1) + "\t";
            for(int l = j*60; l < (j+1)*60 && l < s2.length(); l++)
            {
                if(s2.charAt(l)!= '-')
                    seq2count++;

                buff += s2.charAt(l);
            }
            buff += "\t" + seq2count + "\n\n";

            

        }
        buff += "Report:\n\n";
        buff += String.format("Optimal score = %s\nNumber of: matches = %s"
        + ",mismatches = %s, gaps = %s, opening gaps = %s\n",optmscore,
        cmatches,cmismatches, cgapcontinue, copengap);
        return buff;
    }
    
    
}

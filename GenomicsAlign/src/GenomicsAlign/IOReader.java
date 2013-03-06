/*
 * Utility for reading the parameters and input sequence files
 */

package GenomicsAlign;
import java.util.*;
import java.io.*;
/**
 *
 * @author Joshua
 */
public class IOReader {
    
    public Parameters ReadParameters(String paramConfig) throws FileNotFoundException
    {
        Parameters param = new Parameters();
        File file = new File(paramConfig);
        Scanner scanner = new Scanner(file);
        String line;
        while(scanner.hasNext())
        {
            line = scanner.nextLine();
            String[] lineArray = (line.trim()).split("\\s+");
            int paramVal = Integer.parseInt(lineArray[1]);
            if(lineArray[0].equals("match"))
            {
                param.match = paramVal;
            }else if(lineArray[0].equals("mismatch"))
            {
                param.mismatch = paramVal;
            }else if(lineArray[0].equals("g"))
            {
                param.g = paramVal;
            }else if(lineArray[0].equals("h"))
            {
                param.h = paramVal;
            }
        }
        return param;
    }
    
    /**
     *
     * @param filePath
     * @return a list of sequence objects which provide 
     * the name and the sequence.
     */
    public ArrayList<Sequence> ReadFasta(String filePath) throws FileNotFoundException
    {
        ArrayList<Sequence> sequenceList = new ArrayList<>();
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        String line;
        String sequence = "";
        String sequenceName = "";
        Sequence fastaEntry = null;

        while(scanner.hasNext())
        {
            Sequence s;
            line = scanner.nextLine();
            if(line.length() > 0 && line.charAt(0) == '>')
            {
                if(fastaEntry != null)
                {
                    fastaEntry.header = sequenceName.split("\\s+")[0].substring(1);
                    fastaEntry.sequence = sequence.trim();
                    sequenceList.add(fastaEntry);
                    sequence = "";
                }
                fastaEntry = new Sequence();
                sequenceName = line;
            }
            else
            {
                sequence += line;
            }
        }
        if(fastaEntry != null)
        {
          fastaEntry.header = sequenceName.split("\\s+")[0].substring(1);
          fastaEntry.sequence = sequence.trim();
          sequenceList.add(fastaEntry);
          sequence = "";
        }
        return sequenceList; 
    }
    
}

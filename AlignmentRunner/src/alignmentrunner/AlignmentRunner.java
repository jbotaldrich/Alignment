
/*
 * Console application using the GenomicsAlign library
 */
package AlignmentRunner;

import GenomicsAlign.*;
import java.io.*;
import java.util.ArrayList;
/**
 *
 * @author Joshua
 */
public class AlignmentRunner {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
       Run(args);
       // test1()
       // test2();
    }
    
    public static boolean Run(String[] args)
    {
        IOReader reader = new IOReader();
        Parameters param = new Parameters();
        ArrayList<Sequence> listOfSequences = new ArrayList<Sequence>();
        //Read the fasta file
        if(args.length > 1)
        {
            String fastapath = args[0];

            try{
               listOfSequences = reader.ReadFasta(fastapath);
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
                return false;
            }
            //read the paramters if it has them
           if(args.length >2)
        {
            try
            {
                param = reader.ReadParameters(args[2]);
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
                return false;
            }
        }
           //set the alignment function
        Alignment al;
        if(args[1].equals("0"))
        {
            al = new GlobalAlignment(param);
        }
        else if(args[1].equals("1"))
        {
            al = new LocalAlignment(param);
        }
        else
        {
            return false;
        }
        //perform the alignment
        DP_TableSDI dpTable = new DP_TableSDI(listOfSequences.get(0), 
                listOfSequences.get(1),al);
        dpTable.Getalignment();
        ArrayList<AlignedSequences> alSeqs = dpTable.BacktrackAlignment();
        IOWriter writer = new IOWriter();
        
        ///Write the report
        if(args[1].equals("0"))
        {
            writer.printReportGlobalAffineGap(dpTable, alSeqs, param);
        }
        else if(args[1].equals("1"))
        {
            writer.printReportLocalAffineGap(dpTable, alSeqs, param);
        }
            return true; 
        }
        
        return false;
    }
    
    
    public static void test1() throws FileNotFoundException
    {
        String fastaPath = "C:\\Users\\Joshua\\Documents\\Classes\\Spring2013\\Cpts571\\Programming\\PA1\\AlignmentTools\\src\\Testfiles\\Human-Mouse-BRCA2-cds.fasta";
        String paramPath = "C:\\Users\\Joshua\\Documents\\Classes\\Spring2013\\Cpts571\\Programming\\PA1\\AlignmentTools\\src\\Testfiles\\parameter.txt";
        
        IOReader ioreader = new IOReader();
        ArrayList<Sequence> listSeq;
        
        listSeq = ioreader.ReadFasta(fastaPath);

        
        Parameters param = ioreader.ReadParameters(paramPath);
        

        if(listSeq != null && param != null)
        {
            Alignment al = new LocalAlignment(param);
            DP_TableSDI dpTable = new DP_TableSDI(listSeq.get(0), listSeq.get(1),al);
            dpTable.Getalignment();
            ArrayList<AlignedSequences> alSeqs = dpTable.BacktrackAlignment();
            IOWriter writer = new IOWriter();
            writer.printReportLocalAffineGap(dpTable, alSeqs, param);
        }
    }
    
    
    public static void test2() throws FileNotFoundException
    {
        String fastaPath = "C:\\Users\\Joshua\\Documents\\Classes\\Spring2013\\Cpts571\\Programming\\PA1\\AlignmentTools\\src\\Testfiles\\myfasta.txt";
        String paramPath = "C:\\Users\\Joshua\\Documents\\Classes\\Spring2013\\Cpts571\\Programming\\PA1\\AlignmentTools\\src\\Testfiles\\parameter.txt";
        
        IOReader ioreader = new IOReader();
        ArrayList<Sequence> listSeq;
        
        listSeq = ioreader.ReadFasta(fastaPath);

        
        Parameters param = ioreader.ReadParameters(paramPath);
        

        if(listSeq != null && param != null)
        {
            Alignment al = new GlobalAlignment(param);
            DP_TableSDI dpTable = new DP_TableSDI(listSeq.get(0), listSeq.get(1),al);
            dpTable.Getalignment();
            ArrayList<AlignedSequences> alSeqs = dpTable.BacktrackAlignment();
            IOWriter writer = new IOWriter();
            writer.printReportGlobalAffineGap(dpTable, alSeqs, param);
        }
    }
    
    
}

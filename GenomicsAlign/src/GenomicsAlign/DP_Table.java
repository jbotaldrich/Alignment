/*
 * A lite dynamic programming table useful for counting the number of possible 
 * alignments
 */
package GenomicsAlign;

/**
 *
 * @author Joshua
 */
public class DP_Table {
  	private String s1;
	private String s2;
	private int m;
	private int n;
	DP_Cell[][] m_table;
	Alignment m_align;
	
        
        
	public DP_Table(String s1, String s2)
	{
		this.s1 = s1;
		this.s2 = s2;
		m = s1.length()+1;
		n = s2.length()+1;
		InitializeTable();

	}
	
        public DP_Table(int m, int n)
        {
            this.s1 = new String(new char[m-1]).replace("\0", "-");
            this.s2 = new String(new char[n-1]).replace("\0", "-");
            this.m = m;
            this.n = n;
            InitializeTable();
        }
        
        public void ClearTable()
        {
            for(int i = 0; i < m; i++)
            {   
                for(int j = 0; j < n;j++)
                {
                    m_table[i][j].score =0;
                }
            }
        }
        
        public DP_Table flipTable()
        {
            DP_Table f = new DP_Table(s1,s2);
            for(int i = 0; i < this.m; i++)
            {
                for(int j = 0; j < this.n; j++)
                {
                    f.setcell(this.m_table[m-i-1][n-j-1].score, i, j);
                }
            }
            return f;
        }
        
        
        public void InitializeTable()
        {
            m_table = new DP_Cell[m][n];
            for(int i = 0; i < m; i++)
            {   
                for(int j = 0; j < n;j++)
                {
                    m_table[i][j] = new DP_Cell();
                }
            }
        }
        

	
	public void setAlignment(Alignment a)
	{
		m_align = a;
	}
	
	public int getm()
	{
		return m;
	}
	
	public int getn()
	{
		return n;
	}
	
	public char GetS1Element(int i)
	{
            if(i <= s1.length() && i > 0)
            {
                return s1.charAt(i-1);
            }
            return '-';
	}
	
	public char GetS2Element(int i)
	{
            if(i <= s2.length() && i > 0)
            {  
                return s2.charAt(i-1);
            }
            return '-';
	}
	
	public void setcell(int value, int i, int j)
	{
		if(i <= m && j <= n)
		{
			m_table[i][j].score = value;
		}
	}
	public int getcell(int i, int j)
	{
		if(i <= m && j <=n)
		{
			return m_table[i][j].score;
		}
		else
		{
			return -1;
		}
	}
        
        @Override public String toString()
        {
            String s = "\t";
            for(int i = 0; i < n; i++)
            {
                s+= GetS2Element(i) + "\t";
            }
            s+= "\n";
            for(int i = 0; i < m; i++)
            {
                s+= GetS1Element(i) + "\t";
                for(int j=0; j < n; j++)
                {
                    s += m_table[i][j].score + ",\t";
                }
                s+= "\n";
            }
            return s;
        }
        
        public DP_Table subtract(int value)
        {
            String s1 = new String(new char[m-1]).replace("\0", "-");
            String s2 = new String(new char[n-1]).replace("\0", "-");
            DP_Table da = new DP_Table(s1,s2);
            for(int i = 0; i < m; i++)
            {
                for(int j = 0; j < n; j++)
                {
                    int diff = value - this.getcell(i, j) ;
                    da.setcell(diff, i, j);
                }
            }
            return da;
        }
        
        public DP_Table subtract(DP_Table dt)
        {
            if(this.n == dt.getn() && this.m == dt.getm())
            {
                String s1 = new String(new char[m-1]).replace("\0", "-");
                String s2 = new String(new char[n-1]).replace("\0", "-");
                DP_Table da = new DP_Table(s1,s2);
                for(int i = 0; i < m; i++)
                {
                    for(int j = 0; j < n; j++)
                    {
                        int diff = this.getcell(i, j) - dt.getcell(i, j);
                        da.setcell(diff, i, j);
                    }
                }
                return da;
            }
            return null;
        }
        
        
}

package org.smallfoot.csv;

/**
 * @file
 */

import java.util.Vector;

/**
 *
 */
public abstract class MergingRowPrinter extends RowPrinter
{
    protected RowPrinter parent;
    Vector<Boolean> merge = null;
    Vector<Boolean> merge()
    {
        if (null == merge) merge = new Vector<Boolean>(1,1);
        return merge;
    };
    Vector<String> work;	/**< until the first merged data row, work contains indices of merged headers that don't parse as array indices -- if it's not a numbe, it's a future column name */
    int firstMerge = -1;

    public MergingRowPrinter ()
    {
        this(new CsvRowPrinter());
    }
    public MergingRowPrinter (RowPrinter aParent)
    {
        if (null == aParent) parent = new CsvRowPrinter();    // yes, covering the case of calling it with a null value
        else parent = aParent;
        merge = new Vector<Boolean>(12,1);
    }

    protected void set(int index, boolean aMerge)
    {
        if (index >= merge.size()) merge.setSize(index+1);
        merge.set(index, aMerge);
    }
    protected void set(String index, boolean aMerge)
    {
        try
        {
            Integer x = new Integer(index);
            set (x, aMerge);
        }
        catch (Exception e)
        {
            if (null == work) work = new Vector<String>();
            work.add(index);
        }
    }
    protected int outputColumnCount()
    {
        int res = 1;
        for (int i = 0; i < merge.size(); i++) if (isMerge(i)) res++;
        return res;
    }
    protected boolean isMerge(int index)
    {
        if (null == merge.get(index))
            return false;
        boolean res = merge.get(index);
        return res;
    }

    /** set the columns for merging.  Convenience function. @param cols column numbers to merge (first column is #0) */
    protected void mergeKeys(String[] cols)
    {
        for (String m: cols) set(m, true);
    }

    public void printHeaderRow (String[] data)
    {
        merge.setSize(data.length);

        if (-1 == firstMerge)
        {
            firstMerge = -2;
            if (null != work)
                for (int i = data.length-1; i >= 0; i--)
                    if (work.contains(data[i]))
                        merge.set(i, true);
            for (int i = merge.size()-1; i >= 0; i--)
                if (true == isMerge(i))
                {
//System.out.println("Column " + i + " is now first");
                    firstMerge = i;
                }
        }
    }

    abstract protected String[] trim (String[] data);
    {
    }

    public void printDataRow (String[] data)
    {
        if (true == checkProperty("debug.RowPrintIn"))
        {
            for (String s: data) System.out.print(s+", ");
            System.out.println("from upstream (GRP)");
        }

        switch (firstMerge)
        {
        case -2: /* no merge; passthru */
            parent.printDataRow(data);
            return ;
        default:
            parent.printDataRow(trim(data));
        }
    }

    public void printFooterRow ()
    {
        parent.printFooterRow();
    }
}

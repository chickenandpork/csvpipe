package org.smallfoot.csv;

/**
 * @file
 */

import java.util.regex.Pattern;



/**
 * NoCapHeader is a brain-dead header-replacer: if the header matches "Total Capacity %" (taken from
 * some reports), it replaces the text with "% Utilization" to avoid a % Capacity chart in a
 * channel-utilization report to be confused to mean % Capacity on the end-storage, despite that the
 * report involved has zero in common with end-storage.
 */
public class NoCapHeader extends RowPrinter
{
    RowPrinter parent = null;

    public NoCapHeader ()
    {
        this(null);
    }
    public NoCapHeader (RowPrinter aParent)
    {
	if (null == aParent)
	    parent = new CsvRowPrinter();
	else
            parent = aParent;
    }

    public void printHeaderRow (String[] data)
    {
	Pattern p = Pattern.compile("(Total |)Capacity %");

	for (int i = java.lang.reflect.Array.getLength(data); i > 0; i--)
	    data[i-1] = p.matcher(data[i-1]).replaceAll("% Utilization");

        parent.printHeaderRow(data);
    }



    public void printDataRow (String[] data)
    {
            parent.printDataRow(data);
    }

    public void printFooterRow ()
    {
        parent.printFooterRow();
    }
}

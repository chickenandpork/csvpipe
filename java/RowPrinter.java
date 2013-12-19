package org.smallfoot.csv;

/**
 * @file
 */

//import java.io.FileOutputStream;
//import org.apache.poi.ss.usermodel.WorkbookFactory;
//import org.apache.poi.ss.usermodel.CreationHelper;
//import org.apache.poi.ss.util.CellRangeAddress;
//import org.apache.poi.xssf.usermodel.*;
//import org.apache.poi.ss.usermodel.*;

//import javax.activation.DataSource;
//import javax.activation.URLDataSource;
//import java.io.BufferedReader;
//import java.io.FileInputStream;
//import java.io.FileReader;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.util.Vector;


    /**
     * rowPrinter is a way to pop in a singleton representing the format of a printer
     *
     * if unset, the singleton request results in the default, a CSV printer.  Additional may
     * be assigned in the descendent classes.
     *
     * In my defense, this was originally done because the output path involved arrays of strings:
     * by altering the strings, one can easily insert additional columns or munge them on the way past.
     *
     * The resulting Stringly-typed code (no, not a typo) in these RowPrinter descendents were
     * a case of shimming on additional (bag-on-the-side?) adapters to get it one-step-further.
     * The result may well be a true Stringly-typed, but it got me across the bridge this time.
     *
     * I'd like to suggest that the maintenance burden this adds is similar to the maintenance
     * burden of trying to give "good" CSV, where "good" is a moving definition (start by trying
     * all four line-endings starting with #13#10 and #10, and add spaces where your test data
     * didn't originally have it, plus a few double-quotes, and now you're starting to test the
     * N! of the CSV issue).  I should have moved back into the XML space from the start, and
     * looked at re-using the transforms I already use.
     */
    abstract class RowPrinter
    {
        abstract public void printHeaderRow (String[] columns);
        abstract public void printDataRow (String[] columns);
        abstract public void printFooterRow ();

        java.util.Properties _prop = null;
        boolean checkProperty(String n)
        {
            if (null == _prop) _prop = System.getProperties();
            //return ( (null != _prop.getProperty(n)) && (false != Boolean.parseBoolean(_prop.getProperty(n))) );
            return (
                       Boolean.parseBoolean(_prop.getProperty(n))
                       || (getClass().getName().equalsIgnoreCase(_prop.getProperty(n)))
                       || (getClass().getName().replaceAll("^.*\\.","").equalsIgnoreCase(_prop.getProperty(n)))
                   );
        }
    };


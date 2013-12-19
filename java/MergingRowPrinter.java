package org.smallfoot.csv;

/**
 * @file
 */

//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.conn.ClientConnectionManager;
//import org.apache.http.conn.scheme.Scheme;
//import org.apache.http.conn.scheme.SchemeRegistry;
//import org.apache.http.conn.ssl.SSLSocketFactory;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.NodeList;

//import javax.net.ssl.KeyManager;
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.TrustManager;
//import javax.net.ssl.X509TrustManager;
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.transform.OutputKeys;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;
//import javax.xml.XMLConstants;
//import javax.xml.namespace.QName;
//import javax.xml.xpath.XPath;
//import javax.xml.xpath.XPathExpression;
//import javax.xml.xpath.XPathFactory;
//import java.io.StringWriter;
//import java.security.SecureRandom;
//import java.security.cert.CertificateException;
//import java.security.cert.X509Certificate;

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
//import java.util.Calendar;
import java.util.Vector;
//import gnu.getopt.Getopt;
//import gnu.getopt.LongOpt;



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

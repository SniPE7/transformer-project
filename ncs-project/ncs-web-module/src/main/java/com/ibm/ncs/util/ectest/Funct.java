
package com.ibm.ncs.util.ectest;

import com.ibm.ncs.util.ectest.RefString;
import com.ibm.ncs.util.ectest.IPModel;
import java.io.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


// Referenced classes of package com.eccom.ncs.common:
//            NccLog

public class Funct
{

    public Funct()
    {
    }

    public static void repeatString(RefString opStr, String src, String tar)
    {
        int nBegPos = 0;
        int nEndPos = 0;
        int nSrcLen = 0;
        String sNewStr = "";
        if(tar == null || src == null || opStr == null || opStr.m_data == null)
            return;
        int nOpLen = opStr.m_data.length();
        if(nOpLen <= 0)
            return;
        nSrcLen = src.length();
        if(nSrcLen <= 0)
            return;
        for(nEndPos = opStr.m_data.indexOf(src, nBegPos); nEndPos >= 0; nEndPos = opStr.m_data.indexOf(src, nBegPos))
        {
            sNewStr = opStr.m_data.substring(0, nEndPos);
            sNewStr = String.valueOf(sNewStr) + String.valueOf(tar);
            nBegPos = sNewStr.length();
            sNewStr = String.valueOf(sNewStr) + String.valueOf(opStr.m_data.substring(nEndPos + nSrcLen));
            opStr.m_data = sNewStr;
        }

    }

    public static void repeatStringOneTime(RefString opStr, String src, String tar)
    {
        int nBegPos = 0;
        int nEndPos = 0;
        int nSrcLen = 0;
        String sNewStr = "";
        int nOpLen = opStr.m_data.length();
        if(nOpLen <= 0)
            return;
        nSrcLen = src.length();
        if(nSrcLen <= 0)
            return;
        nEndPos = opStr.m_data.indexOf(src, nBegPos);
        if(nEndPos >= 0)
        {
            sNewStr = opStr.m_data.substring(nBegPos, nEndPos);
            sNewStr = String.valueOf(sNewStr) + String.valueOf(tar);
            sNewStr = String.valueOf(sNewStr) + String.valueOf(opStr.m_data.substring(nEndPos + nSrcLen));
            opStr.m_data = sNewStr;
        }
    }

    public static long getUnitSize(String unit)
    {
        long l = 0L;
        if(unit == null)
            return 0L;
        unit = unit.trim();
        if(unit.equalsIgnoreCase("G"))
            l = 0x40000000L;
        if(unit.equalsIgnoreCase("M"))
            l = 0x100000L;
        if(unit.equalsIgnoreCase("K"))
            l = 1024L;
        if(unit.equalsIgnoreCase("B"))
            l = 1L;
        return l;
    }

    public static long getSizeByUnit(String size, String unit)
    {
        long l = 0L;
        if(size == null || size.equals(""))
            return 0L;
        l = Long.parseLong(size);
        if(unit == null)
            return l;
        unit = unit.trim();
        if(unit.equalsIgnoreCase("G"))
            l = l * 1024L * 1024L * 1024L;
        else
        if(unit.equalsIgnoreCase("M"))
            l = l * 1024L * 1024L;
        else
        if(unit.equalsIgnoreCase("K"))
            l *= 1024L;
        else
        if(unit.equalsIgnoreCase("B"))
            l = l;
        return l;
    }

    public static long getUnSizeByUnit(long size, String unit)
    {
        long l = 0L;
        if(unit == null)
            return size;
        unit = unit.trim();
        if(unit == null || unit.equals(""))
            return size;
        if(unit.equalsIgnoreCase("G"))
            l = size / 0x40000000L;
        else
        if(unit.equalsIgnoreCase("M"))
            l = size / 0x100000L;
        else
        if(unit.equalsIgnoreCase("K"))
            l = size / 1024L;
        else
        if(unit.equalsIgnoreCase("B"))
            l = size;
        return l;
    }

    public static int regexVersion(String versiondesc, StringBuffer softversion, int flag)
    {
        int i;
        if(versiondesc != null && !versiondesc.equals("")){
      //      break MISSING_BLOCK_LABEL_17;
        i = 1;
        return i;}
        try
        {
            Pattern pattern = Pattern.compile("Version");
            Matcher matcher = pattern.matcher(versiondesc);
            matcher.find();
            int start = matcher.start();
            if(flag == 1)
                pattern = Pattern.compile("Copyright");
            else
                pattern = Pattern.compile(",");
            matcher = pattern.matcher(versiondesc);
            matcher.find(start);
            int end = matcher.start();
            String str = versiondesc.substring(start + 7, end);
            str = str.trim();
            str = str.replaceAll("\r", "");
            str = str.replaceAll("\n", "");
            str = str.replaceAll("\t", " ");
            softversion.insert(0, str);
        }
        catch(Exception e)
        {
         //   NccLog.log("\u89E3\u6790\u8F6F\u4EF6\u7248\u672C", e.toString(), 2);
            softversion.insert(0, versiondesc);
            byte byte0 = -1;
            return byte0;
        }
        return 0;
    }

    public static String getCurrentDateTime()
    {
        String str = "";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        str = df.format(date);
        return str;
    }

    public static boolean chkIpInIpRangeList(Vector ipRange_list, long ipdecode)
    {
        if(ipRange_list == null)
            return false;
        int size = ipRange_list.size();
        if(size <= 0)
            return false;
        IPModel ip = null;
        for(int i = 0; i < size; i++)
        {
            ip = (IPModel)ipRange_list.get(i);
            if(ip != null && ipdecode >= ip.minIpdecode && ipdecode <= ip.maxIpdecode)
                return true;
        }

        return false;
    }

    public static String regexIfDescr(String ifDescr)
    {
        String rtDescr = "";
        String patten = "-802";
        rtDescr = ifDescr;
        if(ifDescr == null || ifDescr.equals(""))
            return "";
        int pos = ifDescr.indexOf(patten);
        if(pos >= 0)
            rtDescr = ifDescr.substring(0, pos).trim();
        return rtDescr;
    }

    public static String ifDescrDispose(String ifDescr, int flag)
    {
        if(ifDescr == null || ifDescr.equals(""))
            return "";
        if(flag != 0 && flag != 1)
            return ifDescr;
        int atmPos = ifDescr.indexOf("ATM");
        if(atmPos < 0)
            return ifDescr;
        try
        {
            int aal5Pos = ifDescr.indexOf("-aal5 layer");
            if(flag == 0)
            {
                if(aal5Pos < 0)
                    ifDescr = String.valueOf(String.valueOf(ifDescr)).concat("-aal5 layer");
            } else
            if(flag == 1 && aal5Pos >= 0)
                ifDescr = ifDescr.substring(0, ifDescr.indexOf("-aal5 layer"));
        }
        catch(Exception e)
        {
            e.printStackTrace();
           // NccLog.log("\u5904\u7406ATM\u7EBF\u8DEF\u7AEF\u53E3\u63CF\u8FF0", e.toString(), 2);
        }
        return ifDescr;
    }

    public static String portIpDispose(String ip)
    {
        if(ip == null || ip.equals(""))
            return "";
        String arrip[] = ip.split("[.]");
        if(arrip.length != 4)
            return "";
        if(Integer.parseInt(arrip[0], 10) == 127)
            return "";
        if(Integer.parseInt(arrip[0], 10) == 0 && Integer.parseInt(arrip[1], 10) == 0 && Integer.parseInt(arrip[2], 10) == 0 && Integer.parseInt(arrip[3], 10) == 0)
            return "";
        else
            return ip;
    }

    public static int CopyFile(String fs, String ft)
        throws Exception
    {
        File in = new File(fs);
        File out = new File(ft);
        FileInputStream fis = new FileInputStream(in);
        FileOutputStream fos = new FileOutputStream(out);
        byte buf[] = new byte[1024];
        for(int i = 0; (i = fis.read(buf)) != -1;)
            fos.write(buf, 0, i);

        fis.close();
        fos.close();
        return 0;
    }

//    public static int readAdditionalLooktableDataFromExcelfiles(String srcpathfilename, Vector rtData)
//    {
//        int rowcount;
//        int colcount;
//        boolean cellisnull;
//        int nullcellcount;
//        Sheet sheet;
//        String rowdata;
//        String celldata;
//        File xlsfile;
//        int rt = 0;
//        rowcount = 0;
//        colcount = 0;
//        cellisnull = false;
//        nullcellcount = 0;
//        boolean nullflag = false;
//        sheet = null;
//        int i = 0;
//        rowdata = "";
//        celldata = "";
//        xlsfile = null;
//        String stmp = "";
//        char c;
//        xlsfile = new File(srcpathfilename);
//        if(xlsfile.exists())
//            break MISSING_BLOCK_LABEL_66;
//        xlsfile = null;
//        c = '\u0C3B';
//        return c;
//        char c1;
//        if(xlsfile.isFile())
//            break MISSING_BLOCK_LABEL_129;
//        xlsfile = null;
//        String stmp = String.valueOf(String.valueOf((new StringBuffer("Excel \u6587\u4EF6:")).append(srcpathfilename).append(" \u4E0D\u5B58\u5728\u6216\u4E3A\u4E00\u76EE\u5F55!")));
//        rtData.add(stmp);
//        NccLog.log("\u8BFB\u989D\u5916\u5916\u8868", stmp, 2);
//        c1 = '\u0C3B';
//        return c1;
//        char c2;
//        Workbook m_workBook = Workbook.getWorkbook(xlsfile);
//        sheet = m_workBook.getSheet(0);
//        if(sheet != null)
//            break MISSING_BLOCK_LABEL_201;
//        String stmp = String.valueOf(String.valueOf((new StringBuffer("Excel \u6587\u4EF6:")).append(srcpathfilename).append(" \u662F\u7A7A\u7684")));
//        rtData.add(stmp);
//        NccLog.log("\u8BFB\u989D\u5916\u5916\u8868", stmp, 2);
//        c2 = '\u0C3A';
//        return c2;
//        int row;
//        rowcount = sheet.getRows();
//        row = 0;
//          goto _L1
//_L10:
//        Cell cells[];
//        int col;
//        rowdata = "";
//        colcount = sheet.getColumns();
//        cells = sheet.getRow(row);
//        col = 0;
//          goto _L2
//_L8:
//        if(col < cells.length)
//            celldata = cells[col].getContents();
//        else
//            celldata = null;
//        if(celldata != null && celldata.length() > 0) goto _L4; else goto _L3
//_L3:
//        if(celldata == null || celldata.equals(""))
//            celldata = "NA";
//        if(cellisnull)
//        {
//            nullcellcount++;
//        } else
//        {
//            cellisnull = true;
//            nullcellcount = 1;
//        }
//          goto _L5
//_L4:
//        cellisnull = false;
//        nullcellcount = 0;
//        if(celldata.indexOf("\t") <= 0 && celldata.indexOf("\n") <= 0) goto _L5; else goto _L6
//_L6:
//        char c3;
//        rtData.removeAllElements();
//        String stmp = String.valueOf(String.valueOf((new StringBuffer("Excel \u6587\u4EF6:")).append(srcpathfilename).append("\u4E2D \u5355\u5143[\u884C ").append(String.valueOf(row)).append(",\u5217:").append(String.valueOf(col)).append("]\u6570\u636E\u4E2D\u6709 tab\u7B26")));
//        rtData.add(stmp);
//        NccLog.log("\u8BFB\u989D\u5916\u5916\u8868", stmp, 2);
//        c3 = '\u0C3C';
//        return c3;
//_L5:
//        if(rowdata.length() > 0)
//            rowdata = String.valueOf(String.valueOf(rowdata)).concat("\t");
//        rowdata = String.valueOf(rowdata) + String.valueOf(celldata);
//        col++;
//_L2:
//        if(col < colcount) goto _L8; else goto _L7
//_L7:
//        if(rowdata.length() > 0)
//        {
//            rowdata = String.valueOf(String.valueOf(rowdata)).concat("\n");
//            rtData.add(rowdata);
//            rowdata = "";
//        }
//        row++;
//_L1:
//        if(row < rowcount) goto _L10; else goto _L9
//_L9:
//        int j = 0;
//        return j;
//        Exception e;
//        e;
//        NccLog.log("\u8BFBExcel\u683C\u5F0F\u7684\u989D\u5916\u5916\u8868", e.toString(), 2);
//        byte byte0 = -1;
//        return byte0;
//    }

}

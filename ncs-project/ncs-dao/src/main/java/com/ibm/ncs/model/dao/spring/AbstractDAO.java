package com.ibm.ncs.model.dao.spring;

import java.io.*;
import java.sql.*;
import oracle.sql.BLOB;
import oracle.sql.CLOB;

/**
 * Base class for Oracle 9i DAO classes.
 *
 * This is a customizable template within FireStorm/DAO.
 */
public class AbstractDAO
{
    public void updateClob(Clob clob, String data) throws IOException, SQLException 
    {
        if (clob == null || data == null) {
            return;
        }

        Reader reader = new StringReader( data );
         Writer clobWriter = ((oracle.sql.CLOB)clob).getCharacterOutputStream();
        char[] cbuffer = new char[10* 1024];
        int nread = 0;
        while( (nread= reader.read(cbuffer)) != -1 )
          clobWriter.write( cbuffer, 0, nread);
        reader.close();
        clobWriter.close();
    }

    public void updateBlob(Blob blob, byte[] data) throws IOException, SQLException
    {
        if (blob == null || data == null) {
            return;
        }

        OutputStream os = ((oracle.sql.BLOB)blob).getBinaryOutputStream();
        InputStream is = new ByteArrayInputStream(data);
        byte[] buffer = new byte[10* 1024];
        int nread = 0;
        while( (nread= is.read(buffer)) != -1 ) {
          os.write(buffer, 0, nread);
      }
        is.close();
        os.close();
    }


    public byte[] getBlobColumn(ResultSet rs, int columnIndex)
            throws SQLException
    {
        try {

            int type = rs.getMetaData().getColumnType( columnIndex );
            if (type == Types.LONGVARBINARY) {
                return rs.getBytes( columnIndex );
            }

            Blob blob = rs.getBlob( columnIndex );
            if (blob == null) {
                return null;
            }

            InputStream is = blob.getBinaryStream();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            if (is == null) {
                return null;
            }
            else {
                byte buffer[] = new byte[ 64 ];
                int c = is.read( buffer );
                while (c>0) {
                    bos.write( buffer, 0, c );
                    c = is.read( buffer );
                }
                return bos.toByteArray();
            }
        }
        catch (IOException e) {
            throw new SQLException( "Failed to read BLOB column due to IOException: " + e.getMessage() );
        }
    }

    public void setBlobColumn(PreparedStatement stmt, int parameterIndex, byte[] value)
            throws SQLException
    {
        if (value == null) {
            stmt.setNull( parameterIndex, Types.BLOB );
        }
        else {
            stmt.setBinaryStream( parameterIndex, new ByteArrayInputStream(value), value.length );
        }
    }

    public String getClobColumn(ResultSet rs, int columnIndex)
        throws SQLException
    {
        try {
            Clob clob = rs.getClob( columnIndex );
            if (clob == null) {
                return null;
            }

            StringBuffer ret = new StringBuffer();
            InputStream is = clob.getAsciiStream();

            if (is == null) {
                return null;
            }
            else {
                byte buffer[] = new byte[ 64 ];
                int c = is.read( buffer );
                while (c>0) {
                    ret.append( new String(buffer, 0, c) );
                    c = is.read( buffer );
                }
                return ret.toString();
            }
        }
        catch (IOException e) {
            throw new SQLException( "Failed to read CLOB column due to IOException: " + e.getMessage() );
        }
    }

    public void setClobColumn(PreparedStatement stmt, int parameterIndex, String value)
        throws SQLException
    {
        if (value == null) {
            stmt.setNull( parameterIndex, Types.CLOB );
        }
        else {
            stmt.setAsciiStream( parameterIndex, new ByteArrayInputStream(value.getBytes()), value.length() );
        }
    }
}

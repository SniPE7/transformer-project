package com.ibm.tivoli.cmcc.web.monitor;

import java.util.ArrayList;
import java.util.List;

public class DataTableResult<V> {
  
  private String sEcho = "1";
  private int iTotalRecords = 57;
  private int iTotalDisplayRecords = 57;
  private List<V> aaData = new ArrayList<V>();

  public DataTableResult() {
    super();
  }

  /**
   * @param sEcho
   * @param iTotalRecords
   * @param iTotalDisplayRecords
   * @param aaData
   */
  public DataTableResult(int iTotalRecords, int iTotalDisplayRecords) {
    super();
    this.iTotalRecords = iTotalRecords;
    this.iTotalDisplayRecords = iTotalDisplayRecords;
  }

  /**
   * @param sEcho
   * @param iTotalRecords
   * @param iTotalDisplayRecords
   * @param aaData
   */
  public DataTableResult(List<V> aaData) {
    super();
    this.iTotalRecords = aaData.size();
    this.iTotalDisplayRecords = aaData.size();
    this.aaData = aaData;
  }
  /**
   * @return the sEcho
   */
  public String getsEcho() {
    return sEcho;
  }

  /**
   * @param sEcho the sEcho to set
   */
  public void setsEcho(String sEcho) {
    this.sEcho = sEcho;
  }

  /**
   * @return the iTotalRecords
   */
  public int getiTotalRecords() {
    return iTotalRecords;
  }

  /**
   * @param iTotalRecords the iTotalRecords to set
   */
  public void setiTotalRecords(int iTotalRecords) {
    this.iTotalRecords = iTotalRecords;
  }

  /**
   * @return the iTotalDisplayRecords
   */
  public int getiTotalDisplayRecords() {
    return iTotalDisplayRecords;
  }

  /**
   * @param iTotalDisplayRecords the iTotalDisplayRecords to set
   */
  public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
    this.iTotalDisplayRecords = iTotalDisplayRecords;
  }

  /**
   * @return the aaData
   */
  public List<V> getAaData() {
    return aaData;
  }

  /**
   * @param aaData the aaData to set
   */
  public void setAaData(List<V> aaData) {
    this.aaData = aaData;
  }

}

/**
 * 
 */
package com.ibm.ncs.web.policytemplateapply;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.TTakeEffectHistoryDao;
import com.ibm.ncs.model.dto.TTakeEffectHistory;
import com.ibm.ncs.util.Log4jInit;

/**
 * @author root
 * 
 */
public class DownloadApplyFileSetController implements Controller {

	private TTakeEffectHistoryDao takeEffectHistoryDao;
	String pageView;
	String message = "";

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public TTakeEffectHistoryDao getTakeEffectHistoryDao() {
		return takeEffectHistoryDao;
	}

	public void setTakeEffectHistoryDao(TTakeEffectHistoryDao takeEffectHistoryDao) {
		this.takeEffectHistoryDao = takeEffectHistoryDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		message = "";
		Map<String, Object> model = new HashMap<String, Object>();
		InputStream bis = null;
		OutputStream bos = null;
		try {
			String ppiid = request.getParameter("ppiid");
			String serverId = request.getParameter("serverId");

			TTakeEffectHistory history = this.takeEffectHistoryDao.findLastItemByServerIdAndReleaseInfo(Long.parseLong(serverId), Long.parseLong(ppiid));
			if (history == null) {
				 response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				 return new ModelAndView(this.getPageView(), "definition", model);
			}
			
			String fileName = String.format("ncs-%s-%s.zip", serverId, ppiid);
			File zipFile = File.createTempFile(fileName, ".zip");
			// Generate zip file
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
			
			ZipEntry ze = new ZipEntry("icmp.xml");
			ze.setSize(history.getIcmpXMLFile().length());
			zos.putNextEntry(ze);
			zos.write((history.getIcmpXMLFile().getBytes("UTF-8")));
			
			ze = new ZipEntry("snmp.xml");
			ze.setSize(history.getSnmpXMLFile().length());
			zos.putNextEntry(ze);
			zos.write((history.getSnmpXMLFile().getBytes("UTF-8")));

			ze = new ZipEntry("srcType.txt");
			ze.setSize(history.getSrcTypeFile().length());
			zos.putNextEntry(ze);
			zos.write((history.getSrcTypeFile().getBytes("UTF-8")));

			zos.close();
			
			// Write to browser
			response.setContentType("application/x-msdownload;");
			response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
			response.setHeader("Content-Length", String.valueOf(zipFile.length()));
			bis = new BufferedInputStream(new FileInputStream(zipFile));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}

		} catch (Exception e) {
			message = "policyDefinitionController.error";
			model.put("message", message);
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
		return null;

	}

	public String getPageView() {
		return pageView;
	}

	public void setPageView(String pageView) {
		this.pageView = pageView;
	}
}

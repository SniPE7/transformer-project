package com.ibm.ncs.service;

import java.io.Serializable;
import java.util.List;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.dto.*;
import com.ibm.ncs.model.exceptions.*;

public class TCategoryMapInitManager implements Serializable {
	
	private TCategoryMapInitDao tCategoryMapInitDao;
	private TManufacturerInfoInitDao tManufacturerInfoInitDao;
	


	public List<TCategoryMapInit> getCategory() throws TCategoryMapInitDaoException{
		return tCategoryMapInitDao.findAll();
	}

//	public TCategoryMapInitDao getTCategoryMapInitDao() {
//		return tCategoryMapInitDao;
//	}

	public void setTCategoryMapInitDao(TCategoryMapInitDao tCategoryMapInitDao) {
		this.tCategoryMapInitDao = tCategoryMapInitDao;
	}
	

	

	public void setTManufacturerInfoInitDao(
			TManufacturerInfoInitDao manufacturerInfoInitDao) {
		tManufacturerInfoInitDao = manufacturerInfoInitDao;
	}

	public List<TManufacturerInfoInit> getManufacturers() throws TManufacturerInfoInitDaoException{
		return tManufacturerInfoInitDao.findAll();
	}
}

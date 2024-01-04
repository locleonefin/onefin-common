package com.onefin.ewallet.common.base.service;

import com.onefin.ewallet.common.base.constants.OneFinConstants;
import com.onefin.ewallet.common.base.repository.mariadb.IBaseTransactionRepository;
import com.onefin.ewallet.common.domain.base.AbstractBaseEwalletTrans;
import com.onefin.ewallet.common.utility.date.DateTimeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public abstract class BaseService<T extends AbstractBaseEwalletTrans> implements IBaseService<T> {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseService.class);

	private IBaseTransactionRepository baseRepository;

	protected void setTransBaseRepository(IBaseTransactionRepository repository) {
		this.baseRepository = repository;
	}

	@Autowired
	private DateTimeHelper dateHelper;

	@Override
	public T create(T model) throws Exception {
		Date createdDate = dateHelper.currentDate(OneFinConstants.HO_CHI_MINH_TIME_ZONE);
		model.setCreatedDate(createdDate);
		model.setUpdatedDate(createdDate);
		return (T) baseRepository.save(model);
	}

	@Override
	public T update(T model) {
		model.setUpdatedDate(dateHelper.currentDate(OneFinConstants.HO_CHI_MINH_TIME_ZONE));
		return (T) baseRepository.save(model);
	}

	@Override
	public T findByRequestId(String requestId) throws Exception {
		T model = (T) baseRepository.findByRequestId(requestId);
		return model;
	}

}

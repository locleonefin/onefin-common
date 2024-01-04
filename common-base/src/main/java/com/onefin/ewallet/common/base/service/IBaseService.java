package com.onefin.ewallet.common.base.service;

import com.onefin.ewallet.common.domain.base.AbstractBaseEwalletTrans;

public interface IBaseService<T extends AbstractBaseEwalletTrans> {
	
	T create(T model) throws Exception;

	T update(T model) throws Exception;

	T findByRequestId(String requestId) throws Exception;
}

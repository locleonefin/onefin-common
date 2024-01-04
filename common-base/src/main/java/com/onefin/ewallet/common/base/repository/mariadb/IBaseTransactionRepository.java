package com.onefin.ewallet.common.base.repository.mariadb;

import com.onefin.ewallet.common.domain.base.AbstractBaseEwalletTrans;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IBaseTransactionRepository<T extends AbstractBaseEwalletTrans> extends JpaRepository<T, UUID> {

    public T findByRequestId(String requestId);

}

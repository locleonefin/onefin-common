package com.onefin.ewallet.common.domain.bank.bidv;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.onefin.ewallet.common.domain.base.AbstractBaseEwalletTrans;

import lombok.Data;

@Data
@Entity
@Table(name = "Bidv_Link_Bank_Transaction_View")
public class BidvLinkBankTransactionView extends AbstractBaseEwalletTrans {

}

package com.onefin.ewallet.common.domain.bank.transfer;

import com.onefin.ewallet.common.domain.base.AbstractBaseEwalletTrans;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "Bank_Transfer_Transaction")
public class BankTransferTransaction extends AbstractBaseEwalletTrans {

	@Column(length = 10)
	private String connResult;

	@Column(nullable = false)
	private String clientRequestId;

	@Column
	private Date bankTransTime;

	@Column
	private String walletId;

	@Column(length = 50)
	private String providerId;

	@Column(length = 50)
	private String merchantId;

	@Column
	private int processedRecord;

	@Column(length = 10)
	private String bankStatusCode;

	@OneToMany(mappedBy = "bankTransferTransaction", cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<BankTransferChildRecords> records;

	@Column(length = 100)
	private String remittanceType;

	@Column(length = 100)
	private String feeType;

	@Column(length = 5)
	private String verifyByBank;

	@Column(length = 30)
	private String execUserID;

	@Column(length = 100)
	private String channel;

	@Column(length = 3)
	private String language;

	public void addChildRecord(BankTransferChildRecords bankTransferChildRecords) {
		if (records == null) {
			records = new HashSet<>();
		}
		records.add(bankTransferChildRecords);
	}

	@Column(name = "provider_bank_code")
	private String providerBankCode;

}

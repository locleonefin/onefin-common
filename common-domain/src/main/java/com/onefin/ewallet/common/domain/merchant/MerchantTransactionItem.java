package com.onefin.ewallet.common.domain.merchant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onefin.ewallet.common.domain.constants.DomainConstants;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "portal_transaction_list")
public class MerchantTransactionItem {

	@Id
	@Column()
	private String id;

	@Column()
	private String trxId;

	@Column
	private String businessName;

	@Column
	private String primaryMid;

	@Column
	private BigDecimal trxAmount;

	@Column
	private String businessLicense;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column
	private Date trxDate;

	@Column
	private String tranStatus;

	@Column
	private String trxType;

	@Column
	private String channelType;

	@Column
	private String partner;


}

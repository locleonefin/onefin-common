package com.onefin.ewallet.common.domain.merchant;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "merchant_hierarchy")
public class MerchantHierarchy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
	private String merchantCode;

	@Column
	private String businessName;

	@Column
	private String merchantName;

	@Column
	private String onefinMerchantType;

	@Column
	private String ssMerchantType;

	@Column
	private String merchantMid;

	@Column
	private String parentMid;

//	@Column
//	private String parentCode;
//
//	@Column
//	private String parent;
}

package com.onefin.ewallet.common.domain.billpay.base;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onefin.ewallet.common.domain.constants.DomainConstants;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Billpay_Onefin_Provider")
public class BillpayOnefinProvider extends AbstractBillPayServiceProvider {

	@Column(length = 100)
	private String name;

	@Column(length = 100)
	private String province;
	
	@Column(length = 100)
	private String district;
	
	@Column(length = 100)
	private String unitLevel;

	@Column
	private Integer payIndex = 0;

	@Column(length = 10)
	private String keyBoard = "text";
	
	@Column(length = 100)
	@JsonIgnore
	private String desciption;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private BillpayService billpayService;
	
//	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "billpayOnefinProvider")
//	@Fetch(value = FetchMode.SELECT)
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "billpayOnefinProvider")
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<BillpayPartnerProvider> partnerProviders;

	public BillpayOnefinProvider(String[] params) {
		if (params == null || params.length == 0) {
			return;
		}

		if (params.length >= 1) {
			this.setCode(params[0]);
		}
		if (params.length >= 2) {
			this.name = params[1];
		}
		if (params.length >= 3) {
			this.province = params[2];
		}
		if (params.length >= 4) {
			this.district = params[3];
		}
		if (params.length >= 5) {
			this.unitLevel = params[4];
		}
		if (params.length >= 7) {
			try {
				this.payIndex = Integer.parseInt(params[6]);
			} catch (Exception e) {}
		}
		if (params.length >= 8) {
			this.keyBoard = params[7];
			if (this.keyBoard == null || (this.keyBoard != null &&
					!DomainConstants.KEY_BOARD_TEXT.equals(this.keyBoard) &&
					!DomainConstants.KEY_BOARD_NUMERIC.equals(this.keyBoard))) {
				this.keyBoard = DomainConstants.KEY_BOARD_TEXT;
			}
		}
		if (params.length >= 9) {
			this.desciption = params[8];
		}
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BillpayOnefinProvider that = (BillpayOnefinProvider) o;

        return (this.getCode().equals(that.getCode()));
    }
	
	@Override
    public int hashCode() {
        int result = this.getCode().hashCode();
        return 31 * result;
    }
	
	public Set<BillpayPartnerProvider> getPartnerProviders() {
		if (this.partnerProviders == null) {
			this.partnerProviders = new HashSet<>();
		}
		return this.partnerProviders;
	}
	
	public void setPartnerProviders(Set<BillpayPartnerProvider> partnerProviders) {
		this.getPartnerProviders().retainAll(partnerProviders);
		this.getPartnerProviders().addAll(partnerProviders);
	}
}

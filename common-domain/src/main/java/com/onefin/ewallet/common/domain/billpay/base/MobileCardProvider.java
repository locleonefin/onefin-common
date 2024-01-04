package com.onefin.ewallet.common.domain.billpay.base;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(indexes = {
		@Index(name = "datacard_denom_providerCode_index", columnList = "providerCode,transType,active")
})
public class MobileCardProvider extends AbstractBillPay {
	
	/*******  Partner info  ******/
	@Column(length = 20)
	private String partner;			// code of partner	IOMEDIA | Other
	
	/*******  Service, Provider info  ******/
	@Column(length = 20)
	private String providerCode;	// VIETTEL|MOBIFONE|VINAPHONE|VIETNAMOBILE|BEELINE
	
	@Column(length = 30)
	private String providerName;	// Viettel | Mobifone | Vinaphone | Vietnamobile | Beeline 
		
	@Column(length = 5)
	private String partnerProviderCode;		// VT|MB|VP|VM|BL
	
	@Column(length = 5)
	private String transType;		// BC|TU	
	
	@Column
	private boolean active;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private BillpayService billpayService;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "mobileCardProvider")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<DataCardDenomination> cardDenominations;
	
	public List<DataCardDenomination> getCardDenominations() {
		if (this.cardDenominations == null) {
			this.cardDenominations = new ArrayList<>();
		}
		return this.cardDenominations;
	}

	public void setCardDenominations(List<DataCardDenomination> cardDenominations) {
		this.getCardDenominations().retainAll(cardDenominations);
		this.getCardDenominations().addAll(cardDenominations);
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MobileCardProvider that = (MobileCardProvider) o;

        return (this.id.equals(that.id));
    }
	
	@Override
    public int hashCode() {
        int result = this.id.hashCode();
        return 31 * result;
    }
	
	public MobileCardProvider(String[] params) {
		if (params == null || params.length == 0) {
			return;
		}

		if (params.length >= 1) {
			this.providerCode =params[0];
		}
		if (params.length >= 2) {
			this.providerName = params[1];
		}
		if (params.length >= 3) {
			this.partner = params[2];
		}
		if (params.length >= 4) {
			this.partnerProviderCode = params[3];
		}
		if (params.length >= 5) {
			this.transType = params[4];
		}
		if (params.length >= 6) {
			try {
				this.active = Boolean.parseBoolean(params[5]);
			} catch (Exception e) {}
		}
	}

}

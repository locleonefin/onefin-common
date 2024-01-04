package com.onefin.ewallet.common.domain.billpay.imedia;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onefin.ewallet.common.domain.constants.DomainConstants;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "Imedia_Data_Card")
public class IMediaDataCard {

	@Id
	@GenericGenerator(name = DomainConstants.UUID, strategy = DomainConstants.UUID2)
	@GeneratedValue(generator = DomainConstants.UUID)
	@Column(unique = true, nullable = false, columnDefinition = "BINARY(16)")
	@JsonIgnore
	private UUID id;

	@Column
	private String serial;
	
	@JsonIgnore
	@Column(length = 1000)
	private String pinCodeEncrypted;
	
	@JsonIgnore
	@Column
	private Date expiredate;

	@Column
	private String pinCode;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "imedia_billpay_transaction_id")
    //@OnDelete(action = OnDeleteAction.CASCADE)
	private IMediaBillPayTransaction iMediaBillPayTransaction;

	@JsonIgnore
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	public String getExpireDate() {
		if (this.expiredate != null) {
			return (new SimpleDateFormat(DomainConstants.DATE_FORMAT_TRANS)).format(expiredate);
		}
		return "";
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IMediaDataCard that = (IMediaDataCard) o;

        try {
        	return (this.serial.equals(that.serial) && this.pinCode.equals(that.pinCode));
        } catch (Exception e) {}
        
        return false;
    }
	
	@Override
    public int hashCode() {
		try {
	        int result = this.serial.hashCode() + this.pinCode.hashCode();
	        return 37 * result;
		} catch (Exception e) {}
		return -1;
    }
}

package com.onefin.ewallet.common.domain.errorCode;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Partner_Error_Code")
public class PartnerErrorCode {

	@EmbeddedId
	private PartnerErrorCodeKey partnerErrorCodeKey;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn
	@OnDelete(action = OnDeleteAction.CASCADE)

	private BaseErrorCode baseErrorCode;

}

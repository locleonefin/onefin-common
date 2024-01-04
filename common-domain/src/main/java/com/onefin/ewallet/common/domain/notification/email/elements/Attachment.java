package com.onefin.ewallet.common.domain.notification.email.elements;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Lob;

import lombok.Data;

/**
 * @author thaita
 *
 */
@Data
public class Attachment {

	private String name;

	private String contentType;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(length = 16777215)
	private byte[] content;

	private String status;
}

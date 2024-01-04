package com.onefin.ewallet.common.domain.notification.email.elements;

import java.util.List;

import com.onefin.ewallet.common.domain.notification.common.KeyValueModel;

import lombok.Data;

@Data
public class HeaderEmail {

	private String fromSender;

	private String fromName;

	private List<KeyValueModel> toReceiver;

	private List<KeyValueModel> cc;

	private List<KeyValueModel> bcc;

	private String replyTo;

	private String replyToName;

	private String subject;

	private String bodyText;

}

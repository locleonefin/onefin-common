package com.onefin.ewallet.common.utility.card;

import java.util.regex.Pattern;

public class CardUtil {

	/*
		   ^(?:4[0-9]{12}(?:[0-9]{3})?          # Visa
			|  5[1-5][0-9]{14}                  # MasterCard
			|  3[47][0-9]{13}                   # American Express
			|  3(?:0[0-5]|[68][0-9])[0-9]{11}   # Diners Club
			|  6(?:011|5[0-9]{2})[0-9]{12}      # Discover
			|  (?:2131|1800|35\d{3})\d{11}      # JCB
		    )$
	*/
	
	private static final String NAPAS_TEMPLATE = "9704[0-9]{12}(?:[0-9]{3})?";
	private static final String VISA_TEMPLATE = "4[0-9]{12}(?:[0-9]{3})?";
	private static final String MASTERCARD_TEMPLATE = "(?:5[1-5][0-9]{2}|222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|2720)[0-9]{12}";
	private static final String JCB_TEMPLATE = "(?:2131|1800|35\\d{3})\\d{11}";
	private static final String AMEX_TEMPLATE = "3[47][0-9]{13}";
	private static final String DISCOVER_TEMPLATE = "6(?:011|5[0-9]{2})[0-9]{12}";
	private static final String DINERSCLUB_TEMPLATE = "3(?:0[0-5]|[68][0-9])[0-9]{11}";
    
	private static final String NAPAS = "NAPAS";
	private static final String VISA = "VISA";
	private static final String MASTERCARD = "MASTERCARD";
	private static final String JCB = "JCB";
	private static final String AMEX = "AMEX";
	private static final String DISCOVER = "DISCOVER";
	private static final String DINERSCLUB = "DINERSCLUB";
	
	private static final String UNIONPAY = "UNIONPAY";
	private static final String UNIPASS = "UNIPASS";
	private static final String ONEFIN = "ONEFIN";
	private static final String OTHER = "OTHER";

	private static CardUtil instance;

	public static CardUtil getInstance() {
		if (instance == null) {
			instance = new CardUtil();
		}
		return instance;
	}
	
	public String getCardBrand(String cardPan) {
		if (this.isNapasCard(cardPan)) {
			return NAPAS;
		}
		if (this.isMasterCard(cardPan)) {
			return MASTERCARD;
		}
		if (this.isVisaCard(cardPan)) {
			return VISA;
		}
		if (this.isJcbCard(cardPan)) {
			return JCB;
		}
		if (this.isAmexCard(cardPan)) {
			return AMEX;
		}
		if (this.isDiscoverCard(cardPan)) {
			return DISCOVER;
		}
		if (this.isDinersClubCard(cardPan)) {
			return DINERSCLUB;
		}
		return null;
	}

	public boolean isNapasCard(String cardPan) {
		String template = "^" + NAPAS_TEMPLATE + "$";
		return Pattern.matches(template, cardPan);  
	}
	
	public boolean isMasterCard(String cardPan) {
		String template = "^" + MASTERCARD_TEMPLATE + "$";
		return Pattern.matches(template, cardPan);  
	}
	
	public boolean isVisaCard(String cardPan) {
		String template = "^" + VISA_TEMPLATE + "$";
		return Pattern.matches(template, cardPan);  
	}
	
	public boolean isJcbCard(String cardPan) {
		String template = "^" + JCB_TEMPLATE + "$";
		return Pattern.matches(template, cardPan);  
	}
	
	public boolean isAmexCard(String cardPan) {
		String template = "^" + AMEX_TEMPLATE + "$";
		return Pattern.matches(template, cardPan);  
	}
	
	public boolean isDiscoverCard(String cardPan) {
		String template = "^" + DISCOVER_TEMPLATE + "$";
		return Pattern.matches(template, cardPan);  
	}
	
	public boolean isDinersClubCard(String cardPan) {
		String template = "^" + DINERSCLUB_TEMPLATE + "$";
		return Pattern.matches(template, cardPan);  
	}
	
	public boolean isVisaMasterJcbAmexCard(String cardPan) {
		String template = "^(?:" + 
				MASTERCARD_TEMPLATE + "|" +
				VISA_TEMPLATE + "|" +
				AMEX_TEMPLATE + "|" +
				JCB_TEMPLATE + ")$";
		return Pattern.matches(template, cardPan);  
	}
	

}

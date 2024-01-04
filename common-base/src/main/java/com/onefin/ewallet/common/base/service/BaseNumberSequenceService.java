package com.onefin.ewallet.common.base.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.onefin.ewallet.common.base.repository.sequenceTrans.INumberSequenceRepository;
import com.onefin.ewallet.common.domain.base.NumberBlock;
import com.onefin.ewallet.common.domain.base.PeekNumber;
import com.onefin.ewallet.common.domain.base.sequenceTrans.NumberSequenceTrans;
import com.onefin.ewallet.common.utility.date.DateTimeHelper;

public abstract class BaseNumberSequenceService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseNumberSequenceService.class);

	// Vietcom LinkBank Account
	protected int vcbLinkBankAccountSequenceStart = 1;
	protected String prefixVcbLinkBankAccountName = "VCBALB";

	// Vietcom LinkBank Card
	protected int vcbLinkBankCardSequenceStart = 1;
	protected String prefixVcbLinkBankCardName = "VCBCLB";

	// Vietcom LinkBank Card Token CVV Sequence
	protected int vcbLinkBankCardTokenCvvSequenceStart = 1;
	protected String prefixVcbLinkBankCardTokenCvvName = "VCBCLBCVV";

	// Vietin LinkBank
	protected int vtbLinkBankSequenceStart = 1;
	protected String prefixVtbLinkBankName = "VTLB";

	// Vietin Bank Transfer
	protected int vtbBankTransferSequenceStart = 1;
	protected String prefixVtbBankTransferName = "VTBT";

	// Vietin Bank Transfer Child
	protected int vtbBankTransferChildSequenceStart = 1;
	protected String prefixVtbBankTransferChildName = "VTBTC";

	// Vietin VirtualAcct
	protected int vtbVirtualAcctSequenceStart = 1;
	protected String prefixVtbVirtualAcctName = "VTVA";

	// Vietin VirtualAcct
	protected int vtbVirtualAcctNumberSequenceStart = 1;

	public static String prefixVtbPoolVirtualAcct = "686";

	public static String prefixVtbPoolVirtualAcctSchoolMerchantNumberName = prefixVtbPoolVirtualAcct + "8";

	public static String prefixVtbPoolVirtualAcctCommonMerchantNumberName = prefixVtbPoolVirtualAcct + "9";

	// Vietin Statement
	protected int vtbStatementSequenceStart = 1;
	protected String prefixVtbStatementName = "VTST";

	//NAPAS cashout
	protected int napasCashoutIBFTAccountInquiryReferenceIdSequenceStart = 1;
	protected String prefixNapasCashoutIBFTAccountInquiryReferenceId = "AI";
	protected int napasCashoutIBFTAccountInquiryPaymentTraceSequenceStart = 1;
	protected String prefixNapasCashoutIBFTAccountInquiryPaymentTrace = "AIPT";
	protected int napasCashoutIBFTAccountInquiryPaymentReferenceSequenceStart = 1;
	protected String prefixNapasCashoutIBFTAccountInquiryPaymentReference = "AIPR";

	// VNPAY SMS
	protected int VNPAYSMSNumberSequenceStart = 1;

	// BVB VirtualAcct
	protected int bvbVirtualAcctSequenceStart = 1;

	protected String prefixBvbVirtualAcctName = "BVVA";

	protected int bvbVirtualAcctNumberSequenceStart = 1;

	protected int bvbIBFTTransferNumberSequenceStart = 1;

	protected int bvbIBFTTransferChildNumberSequenceStart = 1;

	public static String prefixBvbPoolVirtualAcct = "6";

	public static String prefixBvbPoolVirtualAcctSchoolMerchantNumberName = "BVB_VIRTUAL_ACCT_SCHOOL";

	public static String prefixBvbPoolVirtualAcctCommonMerchantNumberName = "BVB_VIRTUAL_ACCT_COMMON";

	public static String prefixBvbPoolVirtualAcctSchoolMerchantNumber = prefixBvbPoolVirtualAcct + "8";

	public static String prefixBvbPoolVirtualAcctCommonMerchantNumber = prefixBvbPoolVirtualAcct + "9";

	public static String suffixBvbTransSequenceFieldName = "BVB";

	public static String prefixBvbIBFTTransferNumberName = "BVB_IBFT_TRANS";

	public static String prefixBvbIBFTTransferChildNumberName = "BVB_IBFT_TRANS_CHILD";

	protected String prefixMerchantRefundTransactionIdSequence = "MERCHANT_REFUND_TRANSACTION";

	protected int MerchantRefundTransactionIdSequenceStart = 1;

	public int blocks = 1;
	public int blockSize = 100;
	public long defaultBlocksize = 0;
	public int maxCount = 100;
	public int defaultTimeout = 5;
	public int mode = 0;
	public int bvbVirtualBlockSize = 2;
	public int bvbIBFTBlockSize = 100;
	public int merchantRefundTransactionBlockSize = 2;

	private Map<String, LinkedList<NumberBlock>> blockListMap = new HashMap<String, LinkedList<NumberBlock>>();
	private Map<String, PeekNumber> peekNumber = new HashMap<String, PeekNumber>();

	private INumberSequenceRepository seqRepository;

	protected void setINumberSequenceRepository(INumberSequenceRepository repository) {
		this.seqRepository = repository;
	}

	@Autowired
	public DateTimeHelper dateHelper;

	public synchronized long getNumber(String name, int timeout) throws Exception {
		return this.getNumbers(name, 1, timeout)[0];
	}

	public synchronized long getNumberV2(String name, int timeout, int blockSize) throws Exception {
		return this.getNumbersV2(name, 1, timeout, blockSize)[0];
	}

	private long[] getNumbers(String name, int count, int timeout) throws Exception {

		synchronized (this) {
			if (!blockListMap.containsKey(name) || blockListMap.get(name) == null) {
				LinkedList<NumberBlock> listBlock = new LinkedList<NumberBlock>();
				this.blockListMap.put(name, listBlock);
			}

			if (blockListMap.get(name).size() < blocks) {
				try {
					// get one block of numbers to validate DB
					NumberBlock nb = this.getBlock(this.blockSize, name, this.mode);
					if (nb != null) {
						LOGGER.info("Next number range for '" + name + "' is " + nb.getFirst() + " to " + nb.getLast());
						this.blockListMap.get(name).add(nb);
					} else {
						LOGGER.error("Cannot generate next number range for '" + name); // this should never happen
					}
				} catch (Exception se) {
					LOGGER.error("NumberGenerator.getBlock() threw ", se);
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
					}
				}
			}
		}

		if (count < 1) {
			count = 1;
		}

		// fetch peek number (if any);
		PeekNumber peek = peekNumber.remove(name);

		// fetch any numbers we need after accounting for the pre-fetched 'peek' number
		long[] more = null;
		if (peek == null || count > 1) {
			more = this.getNumbersByName(name, count - (peek == null ? 0 : 1), timeout);
		}

		// allocate space for the peek nunmber (if any) plus the extra numbers (if any)
		int idx = 0;
		long[] result = new long[(more == null ? 0 : more.length) + (peek == null ? 0 : 1)];

		// copy peek number (if any) to first slot in result
		if (peek != null) {
			result[0] = peek.getNumber();
			idx = 1;
		}

		// copy additional numbers (if any) to subsequent slots
		if (more != null) {
			for (int i = 0; i < more.length; i++) {
				result[i + idx] = more[i];
			}
		}

		return result;
	}

	private long[] getNumbersV2(String name, int count, int timeout, int blockSize) throws Exception {

		synchronized (this) {
			if (!blockListMap.containsKey(name) || blockListMap.get(name) == null) {
				LinkedList<NumberBlock> listBlock = new LinkedList<NumberBlock>();
				this.blockListMap.put(name, listBlock);
			}

			if (blockListMap.get(name).size() < blocks) {
				try {
					// get one block of numbers to validate DB
					NumberBlock nb = this.getBlock(blockSize, name, this.mode);
					if (nb != null) {
						LOGGER.info("Next number range for '" + name + "' is " + nb.getFirst() + " to " + nb.getLast());
						this.blockListMap.get(name).add(nb);
					} else {
						LOGGER.error("Cannot generate next number range for '" + name); // this should never happen
					}
				} catch (Exception se) {
					LOGGER.error("NumberGenerator.getBlock() threw ", se);
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
					}
				}
			}
		}

		if (count < 1) {
			count = 1;
		}

		// fetch peek number (if any);
		PeekNumber peek = peekNumber.remove(name);

		// fetch any numbers we need after accounting for the pre-fetched 'peek' number
		long[] more = null;
		if (peek == null || count > 1) {
			more = this.getNumbersByNameV2(name, count - (peek == null ? 0 : 1), timeout, blockSize);
		}

		// allocate space for the peek nunmber (if any) plus the extra numbers (if any)
		int idx = 0;
		long[] result = new long[(more == null ? 0 : more.length) + (peek == null ? 0 : 1)];

		// copy peek number (if any) to first slot in result
		if (peek != null) {
			result[0] = peek.getNumber();
			idx = 1;
		}

		// copy additional numbers (if any) to subsequent slots
		if (more != null) {
			for (int i = 0; i < more.length; i++) {
				result[i + idx] = more[i];
			}
		}

		return result;
	}

	private long[] getNumbersByName(String name, int count, int timeout) throws Exception {

		if (count <= 0) {
			count = 1;
		} else if (count > maxCount) {
			count = maxCount;
		}

		if (timeout < defaultTimeout) {
			timeout = defaultTimeout;
		}

		int idx = 0;
		long[] result = new long[count];

		// while
		if (count > 1 && (count - idx) >= defaultBlocksize) {
			try {
				NumberBlock nb = this.getBlock(this.blockSize, name, this.mode);
				while (idx < count && !nb.isExhausted()) {
					result[idx++] = nb.getNumber();
				}
			} catch (Exception e) {
				throw new Exception("Failed to get new number block for '" + name + "'", e);
			}
		}

		synchronized (this) {
			if (!blockListMap.get(name).isEmpty()) {
				NumberBlock nb = blockListMap.get(name).peek();
				while (idx < count && !nb.isExhausted()) {
					result[idx++] = nb.getNumber();
				}

				if (nb.isExhausted()) {
					blockListMap.get(name).remove();
				}
			}
		}
		if (idx == 0) {
			throw new Exception("No numbers for '" + name + "' in " + timeout + " secs");
		}

		if (idx < count) {
			result = Arrays.copyOf(result, idx);
		}

		LOGGER.debug("Number service '" + name + "' generated: " + Arrays.toString(result));

		return result;
	}

	private long[] getNumbersByNameV2(String name, int count, int timeout, int blockSize) throws Exception {

		if (count <= 0) {
			count = 1;
		} else if (count > maxCount) {
			count = maxCount;
		}

		if (timeout < defaultTimeout) {
			timeout = defaultTimeout;
		}

		int idx = 0;
		long[] result = new long[count];

		// while
		if (count > 1 && (count - idx) >= defaultBlocksize) {
			try {
				NumberBlock nb = this.getBlock(blockSize, name, this.mode);
				while (idx < count && !nb.isExhausted()) {
					result[idx++] = nb.getNumber();
				}
			} catch (Exception e) {
				throw new Exception("Failed to get new number block for '" + name + "'", e);
			}
		}

		synchronized (this) {
			if (!blockListMap.get(name).isEmpty()) {
				NumberBlock nb = blockListMap.get(name).peek();
				while (idx < count && !nb.isExhausted()) {
					result[idx++] = nb.getNumber();
				}

				if (nb.isExhausted()) {
					blockListMap.get(name).remove();
				}
			}
		}
		if (idx == 0) {
			throw new Exception("No numbers for '" + name + "' in " + timeout + " secs");
		}

		if (idx < count) {
			result = Arrays.copyOf(result, idx);
		}

		LOGGER.debug("Number service '" + name + "' generated: " + Arrays.toString(result));

		return result;
	}

	private synchronized NumberBlock getBlock(int blockSize, String name, int mode) throws Exception {
		NumberBlock block = null;

		// update if any
		try {
			if (blockSize > 1) {
				seqRepository.updateByNameAndSize(name, new Long(blockSize));
			} else {
				seqRepository.updateByName(name);
			}
		} catch (Exception e) {
			/* Do nothing */
			LOGGER.error("Cannot update NumberSequenceCard", e);
		}

		// then query block

		try {
			List<Object[]> results = null;
			if (blockSize > 1) {
				results = seqRepository.queryByNameAndSize(name, new Long(blockSize));
			} else {
				results = seqRepository.queryByName(name);
			}

			if (results != null && results.size() == 1) {
				block = new NumberBlock(mode, (Long) results.get(0)[0], (Long) results.get(0)[1]);
			}

		} catch (Exception e) {
			/* Do nothing */
			LOGGER.error("Cannot query NumberSequenceCard", e);
		}

		return block;
	}

	public List<NumberSequenceTrans> findByName(String name) throws Exception {
		return seqRepository.findAllByName(name);
	}

	public NumberSequenceTrans findOneByName(String name) throws Exception {
		return seqRepository.findByName(name);
	}

}
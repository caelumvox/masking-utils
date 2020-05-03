package com.caelumvox.masking.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaskingUtils {
    // Very simple email regex
    public static final Pattern EMAIL_SIMPLE = Pattern.compile("([^@]+)@([^@]+)");

    // Card numbers can only be 13 to 19 digits long
    public static final Pattern PAYMENT_CARD_NUMBER = Pattern.compile("(\\d{6})\\d{3,9}(\\d{4})");

    public static final Pattern SSN_ALL_NUMBERS = Pattern.compile("\\d{9}");
    public static final String SSN_NUMBERS_MASKED_PREFIX = "*****";

    public static final Pattern SSN_DASHES = Pattern.compile("\\d{3}-\\d{2}-(\\d{4})");
    public static final String SSN_DASHES_MASKED_PREFIX = "***-**-";

    /**
     * Masks an email address.  It will just show first two digits of the name part and the full domain part.
     *
     * Examples:
     * <code>
     *     MaskingUtils.maskEmailAddress(null)           = null
     *     MaskingUtils.maskEmailAddress("john@doe.com") = "jo**@doe.com"
     *     MaskingUtils.maskEmailAddress("jo@doe.com")   = "jo@doe.com"
     *     MaskingUtils.maskEmailAddress("john@doe@com") = "john@doe@com"
     *     MaskingUtils.maskEmailAddress("john.doe.com") = "john.doe.com"
     *     MaskingUtils.maskEmailAddress("@@@@@@@@@@")   = "@@@@@@@@@@"
     *     MaskingUtils.maskEmailAddress("@")            = "@"
     *     MaskingUtils.maskEmailAddress("abc@")         = "abc@"
     *     MaskingUtils.maskEmailAddress("abc@d")        = "ab*@d"
     *     MaskingUtils.maskEmailAddress("")             = ""
     * </code>
     * @param emailAddress The email address to be masked.
     * @return The masked email address.
     */
    public static String maskEmailAddress(String emailAddress) {
        if (StringUtils.isEmpty(emailAddress)) {
            return emailAddress;
        }
        Matcher m = EMAIL_SIMPLE.matcher(emailAddress);
        if (m.matches()) {
            String maskedName = m.group(1);
            String domainPart = m.group(2);
            if (maskedName.length() > 2) {
                maskedName = maskedName.substring(0, 2) +
                        StringUtils.repeat("*", maskedName.length() - 2);
            }
            return maskedName + "@" + domainPart;
        }

        return emailAddress;
    }

    /**
     * Masks a payment card number.  It will show just the first 6 and last 4 characters of the number.
     *
     * Examples:
     * <code>
     *     MaskingUtils.maskPaymentCardNumber(null)          = null
     *     MaskingUtils.maskPaymentCardNumber("4111111111111111") = "411111******1111"
     *     MaskingUtils.maskPaymentCardNumber("6771771771771770000") = "677177******0000"
     *     MaskingUtils.maskPaymentCardNumber("67717717717717700000") = "67717717717717700000"
     *     MaskingUtils.maskPaymentCardNumber("") = "411111******1111"
     *     MaskingUtils.maskPaymentCardNumber("") = "411111******1111"
     *     MaskingUtils.maskPaymentCardNumber("whatever")    = "whatever"
     *     MaskingUtils.maskPaymentCardNumber("")            = ""
     * </code>
     * @param paymentCardNumber The payment card number under masking.
     * @return Masked value of the payment card number.
     */
    public static String maskPaymentCardNumber(String paymentCardNumber) {
        if (StringUtils.isEmpty(paymentCardNumber)) {
            return paymentCardNumber;
        }
        Matcher m = PAYMENT_CARD_NUMBER.matcher(paymentCardNumber);
        if (m.matches()) {
            return m.group(1) + StringUtils.repeat("*", paymentCardNumber.length() - 10) + m.group(2);
        }
        return paymentCardNumber;
    }

    /**
     * Masks a social security number.  It will show just the last four characters of the SSN.
     *
     * Examples:
     * <code>
     *     MaskingUtils.maskSSN(null)          = null
     *     MaskingUtils.maskSSN("123-45-6789") = "***-**-6789"
     *     MaskingUtils.maskSSN("123456789")   = "*****6789"
     *     MaskingUtils.maskSSN("whatever")    = "whatever"
     *     MaskingUtils.maskSSN("")            = ""
     * </code>
     * @param ssn The Social Security Number (SSN) to be masked
     * @return Masked value of the SSN
     */
    public static String maskSSN(String ssn) {
        if (StringUtils.isEmpty(ssn)) {
            return ssn;
        }
        Matcher m = SSN_DASHES.matcher(ssn);
        if (m.matches()) {
            return SSN_DASHES_MASKED_PREFIX + m.group(1);
        }

        m = SSN_ALL_NUMBERS.matcher(ssn);
        if (m.matches()) {
            return SSN_NUMBERS_MASKED_PREFIX + ssn.substring(5);
        }
        return ssn;
    }
}

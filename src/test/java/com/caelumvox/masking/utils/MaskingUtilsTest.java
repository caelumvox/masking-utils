package com.caelumvox.masking.utils;

import org.junit.jupiter.api.Test;

import static com.caelumvox.masking.utils.MaskingUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MaskingUtilsTest {
    @Test
    public void maskEmailTests() {
        assertNull(MaskingUtils.maskEmailAddress(null));
        assertEquals("jo**@doe.com", maskEmailAddress("john@doe.com"));
        assertEquals("jo@doe.com", maskEmailAddress("jo@doe.com"));
        assertEquals("john@doe@com", maskEmailAddress("john@doe@com"));
        assertEquals("john.doe.com", maskEmailAddress("john.doe.com"));
        assertEquals("@@@@@@@@@@", maskEmailAddress("@@@@@@@@@@"));
        assertEquals("@", maskEmailAddress("@"));
        assertEquals("abc@", maskEmailAddress("abc@"));
        assertEquals("ab*@d", maskEmailAddress("abc@d"));
        assertEquals("今天真是了不起的一天", maskEmailAddress("今天真是了不起的一天"));
        assertEquals("", maskEmailAddress(""));
    }
    @Test
    public void maskPaymentCardTests() {
        assertEquals("411111******1111", maskPaymentCardNumber("4111111111111111"));
        assertEquals("411111*********1111", maskPaymentCardNumber("4111111111111111111"));
        assertEquals("41111111111111111111", maskPaymentCardNumber("41111111111111111111"));
        assertEquals("411111***1111", maskPaymentCardNumber("4111111111111"));
        assertEquals("411111111111", maskPaymentCardNumber("411111111111"));
        assertEquals("今天真是了不起的一天", maskPaymentCardNumber("今天真是了不起的一天"));
        assertEquals("", maskPaymentCardNumber(""));
        assertNull(maskPaymentCardNumber(null));
    }
    @Test
    public void maskSSNTests() {
        assertEquals("***-**-6789", maskSSN("123-45-6789"));
        assertEquals("*****6789", maskSSN("123456789"));
        assertEquals("12345678", maskSSN("12345678"));
        assertEquals("a12345678", maskSSN("a12345678"));
        assertEquals("1234567890", maskSSN("1234567890"));
        assertEquals("humphrey", maskSSN("humphrey"));
        assertEquals("", maskSSN(""));
        assertEquals("今天真是了不起的一天", maskSSN("今天真是了不起的一天"));
        assertNull(maskSSN(null));
    }
}

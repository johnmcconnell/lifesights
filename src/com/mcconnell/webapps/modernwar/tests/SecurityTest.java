package com.mcconnell.webapps.modernwar.tests;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.Test;

import com.mcconnell.utils.SecurityUtil;

public class SecurityTest {

	@Test
	public void stringEncryptionTest() throws InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			UnsupportedEncodingException {
		String text = "Hello World";
		byte[] ctext = SecurityUtil.encrypt(text);
		assertTrue(SecurityUtil.decrypt(ctext).equals(text));
	}
}

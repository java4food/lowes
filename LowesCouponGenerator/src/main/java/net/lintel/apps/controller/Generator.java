package net.lintel.apps.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.validator.routines.checkdigit.CheckDigitException;
import org.apache.commons.validator.routines.checkdigit.ISBNCheckDigit;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Generator {
	@RequestMapping(produces = "application/json;charset=UTF-8", value = "/generate")
	public List<String> generateCoupons() {
		final List<String> coupons = new ArrayList<String>();

		for (int idx = 0; idx < 10; idx++) {
			coupons.add(generateCode("470001RRRR7121Z"));
		}

		return coupons;
	}

	private String getCheckDigit(String input) throws CheckDigitException {
		return ISBNCheckDigit.ISBN13_CHECK_DIGIT.calculate(input);
	}

	private String generateCode(String pattern) {
		try {
			final StringBuilder code = new StringBuilder();
			for (int idx = 0; idx < pattern.length(); idx++) {
				final char inputChar = pattern.charAt(idx);
				switch (inputChar) {
				case 'R':
					code.append(Integer.toString((int) Math.round(Math.random() * 9)));
					break;
				case 'Z':
					return code.append(getCheckDigit(code.toString())).toString();
				default:
					code.append(inputChar);
					break;
				}
			}

			throw new IllegalArgumentException("invalid pattern");
		} catch (Exception e) {
			throw new RuntimeException("failed to generate code", e);
		}
	}
}

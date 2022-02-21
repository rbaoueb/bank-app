package com.parking.test;

import com.bank.ridha.util.BankUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BankUtilsTest {
	@Test
	public void generate_id_test() {
		assertThat(BankUtils.generayteId()).isNotNull();
	}
}

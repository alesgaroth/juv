package com.alesgaroth.zuv;

import org.junit.jupiter.api.Test;

class ZuvAcceptanceTest {

	Integer heardValue = null;

	@Test
	public void constantReturnsConstant() {
		ZConstant constant = new ZConstant((Integer)7);
		constant.addListener(
		new ZListener<Integer>() {
			void update(Integer value) {
				heardValue = value;
			}
		});
		contant.refresh();
		constant.execute();
		assertEqual(7, heardValue);
	}

}

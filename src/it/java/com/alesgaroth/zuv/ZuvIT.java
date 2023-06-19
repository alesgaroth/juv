package com.alesgaroth.zuv;

import org.junit.jupiter.api.Test;

class ZuvIT {

	Integer heardValue = null;

	@Test
	public void constantReturnsConstant() {
		ZConstant constant = new ZConstant((Integer)7);
		constant.addListener(
		new ZListener<Integer>() {
			void update(ZValue<Integer> value) {
				heardValue = value.getValue();
			}
		});
		contant.refresh();
		constant.execute();
		assertEqual(7, heardValue);
	}

}

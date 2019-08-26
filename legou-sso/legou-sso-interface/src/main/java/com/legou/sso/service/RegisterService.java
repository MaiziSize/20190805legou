package com.legou.sso.service;

import com.legou.Utils.utils.LegouResult;
import com.legou.pojo.TbUser;

public interface RegisterService {

	LegouResult checkData(String param, int type);
	LegouResult register(TbUser user);
}

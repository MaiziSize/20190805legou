package com.legou.sso.service;

import com.legou.Utils.utils.LegouResult;

public interface TokenService {

	LegouResult getUserByToken(String token);
}

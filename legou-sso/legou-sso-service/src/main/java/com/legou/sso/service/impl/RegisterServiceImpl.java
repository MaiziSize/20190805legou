package com.legou.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.legou.Utils.utils.LegouResult;
import com.legou.mapper.TbUserMapper;
import com.legou.pojo.TbUser;
import com.legou.pojo.TbUserExample;
import com.legou.pojo.TbUserExample.Criteria;
import com.legou.sso.service.RegisterService;



@Service
public class RegisterServiceImpl implements RegisterService {
	
	@Autowired
	private TbUserMapper userMapper;

	@Override
	public LegouResult checkData(String param, int type) {
		//根据不同的type生成不同的查询条件
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		//1：用户名 2：手机号 3：邮箱
		if (type == 1) {
			criteria.andUsernameEqualTo(param);
		} else if (type == 2) {
			criteria.andPhoneEqualTo(param);
		} else if (type == 3) {
			criteria.andEmailEqualTo(param);
		} else {
			return LegouResult.build(400, "数据类型错误");
		}
		//执行查询
		List<TbUser> list = userMapper.selectByExample(example);
		//判断结果中是否包含数据
		if (list != null && list.size()>0) {
			//如果有数据返回false
			return LegouResult.ok(false);
		}
		//如果没有数据返回true
		return LegouResult.ok(true);
	}

	@Override
	public LegouResult register(TbUser user) {
		//数据有效性校验
		if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword()) 
				|| StringUtils.isBlank(user.getPhone())) {
			return LegouResult.build(400, "用户数据不完整，注册失败");
		}
		//1：用户名 2：手机号 3：邮箱
		LegouResult result = checkData(user.getUsername(), 1);
		if (!(boolean) result.getData()) {
			return LegouResult.build(400, "此用户名已经被占用");
		}
		result = checkData(user.getPhone(), 2);
		if (!(boolean)result.getData()) {
			return LegouResult.build(400, "手机号已经被占用");
		}
		//补全pojo的属性
		user.setCreated(new Date());
		user.setUpdated(new Date());
		//对密码进行md5加密
		String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass);
		//把用户数据插入到数据库中
		userMapper.insert(user);
		//返回添加成功
		return LegouResult.ok();
	}

	

}

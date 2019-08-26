package com.legou.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legou.Service.ItemService;
import com.legou.Utils.utils.CookieUtils;
import com.legou.Utils.utils.JsonUtils;
import com.legou.Utils.utils.LegouResult;
import com.legou.pojo.TbItem;

@Controller
public class CartController {
	
	@Autowired
	private ItemService itemService;
	
	public List<TbItem> getItemFromCookie(HttpServletRequest request){
		String jsonString = CookieUtils.getCookieValue(request, "legoucart", true);
		if(StringUtils.isBlank(jsonString)) {
			return new ArrayList<TbItem>();
		}
		List<TbItem> itemList = JsonUtils.jsonToList(jsonString, TbItem.class);
		return itemList;		
	}
	
	@RequestMapping("/cart/add/{itemid}")
	public String addItemid(@PathVariable long itemid,@RequestParam(defaultValue = "1") Integer num,HttpServletRequest request,HttpServletResponse response) {
		// 把用户购买的商品信息加入购物车(即使用户在没有登录的状态下也需要保存用户的购物信息到cookie)
		// 先从cookie里面取出所有的商品信息
		List<TbItem> items = getItemFromCookie(request);
		// 添加一个判断标记
		boolean flag = false;
		// 遍历cookie里面的所有的商品
		for (TbItem item : items) {
			if (item.getId() == itemid) {
				item.setNum(item.getNum() + num);
				flag = true;
			}
		}
		// 如果cookie中不存在当前商品那么再去数据库中搜索该商品
		if (!flag) {
			TbItem tbitem = itemService.getItem(itemid);
			tbitem.setNum(num);
			String imagepath = tbitem.getImage();
			if (StringUtils.isNoneBlank(imagepath)) {
				// 由于一条商品可以有多张图片，我们购物车仅仅需要一张图片
				String[] images = imagepath.split(",");
				tbitem.setImage(images[0]);
			}
			items.add(tbitem);
		}
		CookieUtils.setCookie(request, response, "legoucart", JsonUtils.objectToJson(items), 30 * 60 * 60, true);
		return "cartSuccess";
	}

	@RequestMapping("/cart/cart")
	public String goCart(HttpServletResponse response, HttpServletRequest request) {
		List<TbItem> list = getItemFromCookie(request);
		request.setAttribute("cartList", list);
		return "cart";
	}

	// 更新购物车数量
	@RequestMapping("/cart/update/num/{itemid}/{num}")
	@ResponseBody
	public LegouResult modifyItemNum(@PathVariable long itemid, @PathVariable int num, HttpServletRequest request,
			HttpServletResponse response) {
		List<TbItem> items = getItemFromCookie(request);
		for (TbItem tbItem : items) {
			if (tbItem.getId() == itemid) {
				tbItem.setNum(num);
			}

		}
		CookieUtils.setCookie(request, response, "legoucart", JsonUtils.objectToJson(items), 30 * 60 * 60, true);
		return LegouResult.ok();
	}
	
	@RequestMapping("/cart/delete/{itemid}")
	public String deleteCart(@PathVariable Long itemid, HttpServletRequest request, HttpServletResponse response) {
		// 先从cookie里面取出所有的商品信息
		List<TbItem> items = getItemFromCookie(request);
		//遍历Id进行判断如果相等则删除这条商品
		for (TbItem tbItem : items) {
			if(tbItem.getId().longValue() == itemid) {
				//删除商品
				items.remove(tbItem);
				//如果删除则跳出循环
				break;
			}
		}
		CookieUtils.setCookie(request, response, "legoucart", JsonUtils.objectToJson(items), 30 * 60 * 60, true);
		return "redirect:/cart/cart.html";				
	}
	
	
}

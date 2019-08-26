package com.legou.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.legou.Utils.utils.LegouResult;
import com.legou.search.service.ImporItemtoSolrService;

@Controller
public class ImportItemToSolrController {
	@Autowired
	ImporItemtoSolrService imporItemtoSolrService;
	
	@RequestMapping("/index/item/import")
	@ResponseBody
	public LegouResult importSorl() {
		LegouResult legouResult = imporItemtoSolrService.importAllItemToSolr();
		return legouResult;
	}
}

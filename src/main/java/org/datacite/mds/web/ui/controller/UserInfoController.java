package org.datacite.mds.web.ui.controller;

import org.apache.log4j.Logger;
import org.datacite.mds.domain.Allocator;
import org.datacite.mds.domain.Datacentre;
import org.datacite.mds.domain.Dataset;
import org.datacite.mds.util.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserInfoController {
    Logger log = Logger.getLogger(UserInfoController.class);

    @RequestMapping(value = "/userinfo", method = RequestMethod.GET)
    public String userinfo(Model model) {
        Datacentre datacentre = SecurityUtils.getCurrentDatacentreOrNull();
        Allocator allocator = (datacentre == null) ? SecurityUtils.getCurrentAllocatorOrNull() : datacentre.getAllocator();

        if (allocator != null) {
            log.debug("userinfo for allocator '" + allocator.getSymbol() + "'");
            model.addAttribute("allocator", allocator);
        }

        if (datacentre != null) {
            log.debug("userinfo for datacentre '" + datacentre.getSymbol() + "'");
            model.addAttribute("datacentre", datacentre);
            model.addAttribute("countDatasets", Dataset.countDatasetsByDatacentre(datacentre));
        }

        return "userinfo";
    }
}

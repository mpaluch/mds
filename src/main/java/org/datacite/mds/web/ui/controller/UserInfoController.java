package org.datacite.mds.web.ui.controller;

import org.apache.log4j.Logger;
import org.datacite.mds.domain.Allocator;
import org.datacite.mds.domain.Datacentre;
import org.datacite.mds.domain.Dataset;
import org.datacite.mds.service.SecurityException;
import org.datacite.mds.util.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserInfoController {
    Logger log = Logger.getLogger(UserInfoController.class);

    @RequestMapping(value = "/userinfo", method = RequestMethod.GET)
    public String userinfo(Model model) throws SecurityException {
        if (SecurityUtils.isLoggedInAsDatacentre())
            userinfoDatacentre(model);
        else if (SecurityUtils.isLoggedInAsAllocator())
            userinfoAllocator(model);

        return "userinfo";
    }

    private void userinfoDatacentre(Model model) throws SecurityException {
        Datacentre datacentre = SecurityUtils.getCurrentDatacentre();
        addDatacentreToModel(datacentre, model);
        Allocator allocator = datacentre.getAllocator();
        addAllocatorToModel(allocator, model);
    }

    private void userinfoAllocator(Model model) throws SecurityException {
        Allocator allocator = SecurityUtils.getCurrentAllocator();
        addAllocatorToModel(allocator, model);
    }

    private void addDatacentreToModel(Datacentre datacentre, Model model) {
        log.debug("userinfo for datacentre '" + datacentre.getSymbol() + "'");
        model.addAttribute("datacentre", datacentre);
        model.addAttribute("countDatasets", Dataset.countDatasetsByDatacentre(datacentre));
    }

    private void addAllocatorToModel(Allocator allocator, Model model) {
        log.debug("userinfo for allocator '" + allocator.getSymbol() + "'");
        model.addAttribute("allocator", allocator);
    }
}

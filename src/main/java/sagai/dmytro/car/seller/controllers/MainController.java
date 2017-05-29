package sagai.dmytro.car.seller.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * TODO: add comments
 *
 * @author dsagai
 * @version TODO: set version
 * @since 11.05.2017
 */
@Controller
public class MainController {

    @RequestMapping("/")
    public String advertisementList() {
        return "adv-list";
    }

    @RequestMapping("/adv-update-form")
    public String getAdvertisementUpdateForm() {
        return "adv-add-update";
    }
}

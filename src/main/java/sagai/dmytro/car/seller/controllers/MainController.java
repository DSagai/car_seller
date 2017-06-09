package sagai.dmytro.car.seller.controllers;



import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sagai.dmytro.car.seller.model.advertisements.Advertisement;
import sagai.dmytro.car.seller.model.advertisements.AlbumItem;
import sagai.dmytro.car.seller.model.advertisements.attributes.AdvAttribute;
import sagai.dmytro.car.seller.model.advertisements.attributes.AttributeTypes;
import sagai.dmytro.car.seller.storage.AdvAttributeRepository;
import sagai.dmytro.car.seller.storage.AdvertisementRepository;
import sagai.dmytro.car.seller.storage.AlbumItemsRepository;
import sagai.dmytro.car.seller.storage.UserRepository;


import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;


/**
 * TODO: add comments
 *
 * @author dsagai
 * @version TODO: set version
 * @since 11.05.2017
 */
@Controller
public class MainController {

    @Autowired
    @Named("advAttributeRepository")
    private AdvAttributeRepository advAttributeRepository;

    @Autowired
    ServletContext servletContext;

    private static final String RESOURCE_PATH = "WEB-INF/resources/images/%s";

    @Autowired
    @Named("userRepository")
    private UserRepository userRepository;

    @Autowired
    @Named("advertisementRepository")
    private AdvertisementRepository advertisementRepository;

    @Autowired
    @Named("albumItemsRepository")
    private AlbumItemsRepository albumItemsRepository;

    @RequestMapping("/")
    public String advertisementList() {
        return "adv-list";
    }


    @RequestMapping("/add-file-form")
    public String addFileForm() {
        return "file-upload-test";
    }

    @RequestMapping(value = "/upload-file",
            consumes = "multipart/form-data",
            method = RequestMethod.POST)
    public String uploadFile(@RequestPart("picture") MultipartFile picture) throws IOException, FileUploadException {


        if (picture != null){
            Advertisement advertisement = this.advertisementRepository.getAdvertisement(5);
            AlbumItem albumItem = new AlbumItem();
            albumItem.setAdvertisement(advertisement);
            albumItem.setPhoto(picture.getBytes());
            this.albumItemsRepository.saveUpdateAlbumItem(albumItem);
        }
        return "login";
    }

    @RequestMapping("/add-advertisement-form")
    public ModelAndView getAddAdvForm() {
        ModelAndView mv = new ModelAndView("adv-add");
        mv.addObject("manufacturerList",
                this.advAttributeRepository.getAttributesByType(AttributeTypes.Manufacturer));
        mv.addObject("engineType",
                this.advAttributeRepository.getAttributesByType(AttributeTypes.EngineType));
        mv.addObject("carBodyType",
                this.advAttributeRepository.getAttributesByType(AttributeTypes.CarBodyType));
        mv.addObject("transmission",
                this.advAttributeRepository.getAttributesByType(AttributeTypes.Transmission));


        mv.addObject("advertisement", new Advertisement());
        return mv;
    }

    @RequestMapping(value = "/ADD-ADVERTISEMENT",
            method = RequestMethod.POST
    )
    public String addAdvertisement(@ModelAttribute Advertisement advertisement,
                                   Principal user,
                                   RedirectAttributes attributes) {
        advertisement.setStatus(this.advAttributeRepository.getDefaultStatus());
        advertisement.setOwner(this.userRepository.getUser(user.getName()));
        advertisement.setCreated(new Timestamp(System.currentTimeMillis()));
        this.advertisementRepository.saveUpdateAdvertisement(advertisement);

        attributes.addAttribute("advId", advertisement.getId());
        return "redirect:/update-advertisement-form";
    }

    @RequestMapping(value = "/update-advertisement-form")
    public ModelAndView getUpdateAdvForm(@RequestParam int advId) {
        ModelAndView mv = new ModelAndView("adv-update");
        Advertisement advertisement = this.advertisementRepository.getAdvertisement(advId);
        mv.addObject("manufacturerList",
                this.advAttributeRepository.getAttributesByType(AttributeTypes.Manufacturer));
        mv.addObject("engineType",
                this.advAttributeRepository.getAttributesByType(AttributeTypes.EngineType));
        mv.addObject("carBodyType",
                this.advAttributeRepository.getAttributesByType(AttributeTypes.CarBodyType));
        mv.addObject("transmission",
                this.advAttributeRepository.getAttributesByType(AttributeTypes.Transmission));
        mv.addObject("advertisement", advertisement);

        List<AlbumItem> images = this.albumItemsRepository.getAlbumItems(advertisement);
        mv.addObject("images", images);
        return mv;
    }

    @RequestMapping(value = "/sketchPhoto.jpg", method = RequestMethod.GET)
    public @ResponseBody byte[] getSketchPhoto(@RequestParam int id) throws IOException {
        Advertisement advertisement = this.advertisementRepository.getAdvertisement(id);
        byte[] result = null;
        if (advertisement != null) {
            if (advertisement.getSketchPhoto() != null && advertisement.getSketchPhoto().length > 0) {
                result = advertisement.getSketchPhoto();
            } else {
                InputStream in = this.servletContext.getResourceAsStream(String.format(RESOURCE_PATH, "NA.jpg"));
                result = new byte[in.available()];
                in.read(result);
            }
        }
        return result;
    }

    @RequestMapping(value = "/albumPhoto.jpg", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getAlbumPhoto(@RequestParam int id) {
        AlbumItem albumItem = this.albumItemsRepository.getAlbumItem(id);
        byte[] result = null;
        if (albumItem != null) {
            result = albumItem.getPhoto();
        }
        return result;
    }
}



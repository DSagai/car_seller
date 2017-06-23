package sagai.dmytro.car.seller.controllers;



import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sagai.dmytro.car.seller.model.advertisements.Advertisement;
import sagai.dmytro.car.seller.model.advertisements.AlbumItem;
import sagai.dmytro.car.seller.model.advertisements.attributes.AttributeTypes;
import sagai.dmytro.car.seller.model.authentication.User;
import sagai.dmytro.car.seller.storage.AdvAttributeRepository;
import sagai.dmytro.car.seller.storage.AdvertisementRepository;
import sagai.dmytro.car.seller.storage.AlbumItemsRepository;
import sagai.dmytro.car.seller.storage.UserRepository;
import sagai.dmytro.car.seller.utility.ImageConverterService;


import javax.inject.Named;
import javax.servlet.ServletContext;
import java.io.IOException;
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

    @Autowired
    @Named("imageConverterService")
    private ImageConverterService imageConverterService;

    @RequestMapping("/")
    public String advertisementList() {
        return "adv-list";
    }


    @RequestMapping("/add-file-form")
    public String addFileForm() {
        return "file-upload-test";
    }


    /**
     * todo comments
     * @param picture

     * @return
     * @throws IOException
     * @throws FileUploadException
     */
    @RequestMapping(value = "/upload-album-item",
            consumes = "multipart/form-data",
            method = RequestMethod.POST)

    public ResponseEntity<Integer> uploadAlbumItem(@RequestPart("picture") MultipartFile picture,
                                              @RequestParam int id, Principal login
    ) throws IOException, FileUploadException {

        int body = 0;
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        try {
            Advertisement advertisement = this.advertisementRepository.getAdvertisement(id);
            checkOwnership(advertisement.getOwner(), login);
            AlbumItem albumItem = new AlbumItem();
            albumItem.setAdvertisement(advertisement);
            albumItem.setPhoto(this.imageConverterService.getResizedImage(
                    ImageConverterService.StandardVerticalDimensions.Large,
                    picture.getBytes()));
            this.albumItemsRepository.saveUpdateAlbumItem(albumItem);
            body = albumItem.getId();
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<Integer>(body, httpStatus);
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
    public ModelAndView getUpdateAdvForm(@RequestParam int advId, Principal login) {
        ModelAndView mv = new ModelAndView("adv-update");
        Advertisement advertisement = this.advertisementRepository.getAdvertisement(advId);

        checkOwnership(advertisement.getOwner(), login);

        mv.addObject("manufacturerList",
                this.advAttributeRepository.getAttributesByType(AttributeTypes.Manufacturer));
        mv.addObject("engineType",
                this.advAttributeRepository.getAttributesByType(AttributeTypes.EngineType));
        mv.addObject("carBodyType",
                this.advAttributeRepository.getAttributesByType(AttributeTypes.CarBodyType));
        mv.addObject("transmission",
                this.advAttributeRepository.getAttributesByType(AttributeTypes.Transmission));
        mv.addObject("advertisement", advertisement);
        return mv;
    }

    @RequestMapping(value = "/get-album-list", method = RequestMethod.POST)
    @ResponseBody
    public Integer[] getAlbumList(@ModelAttribute Advertisement advertisement) {
        return this.albumItemsRepository.getAlbumItemIdArray(advertisement);
    }


    /**
     * todo
     * @return
     */
    @RequestMapping("/UPDATE-ADVERTISEMENT")
    public String updateAdvertisement(@ModelAttribute Advertisement advertisement,
                                      RedirectAttributes attributes, Principal login) {
        checkOwnership(this.userRepository.getUser(advertisement.getOwner().getId()), login);
        this.advertisementRepository.saveUpdateAdvertisement(advertisement);
        attributes.addAttribute("advId", advertisement.getId());
        return "redirect:/update-advertisement-form";
    }

//    @RequestMapping(value = "/sketchPhoto.jpg", method = RequestMethod.GET)
//    public @ResponseBody byte[] getSketchPhoto(@RequestParam int id) throws IOException {
//        Advertisement advertisement = this.advertisementRepository.getAdvertisement(id);
//        byte[] result = null;
//        if (advertisement != null) {
//            if (advertisement.getSketchPhoto() != null) {
//                result = advertisement.getSketchPhoto().getPhoto();
//            } else {
//                InputStream in = this.servletContext.getResourceAsStream(String.format(RESOURCE_PATH, "NA.jpg"));
//                result = new byte[in.available()];
//                in.read(result);
//            }
//        }
//        return result;
//    }

    @RequestMapping(value = "/albumItem.jpg", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getAlbumItem(@RequestParam int id) {
        AlbumItem albumItem = this.albumItemsRepository.getAlbumItem(id);
        byte[] result = null;
        if (albumItem != null) {
            result = albumItem.getPhoto();
        }
        return result;
    }


    @RequestMapping(value = "/remove-album-item", method = RequestMethod.POST)
    public ResponseEntity<Integer> removeAlbumItem(@RequestParam int id, Principal login) {
        int body = 0;
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        try {
            AlbumItem albumItem = this.albumItemsRepository.getAlbumItem(id);
            checkOwnership(albumItem.getAdvertisement().getOwner(), login);
            this.albumItemsRepository.removeAlbumItem(albumItem);
            body = albumItem.getId();
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<Integer>(body, httpStatus);
    }


    /**
     * Method checks is current session login creator of requested resource.
     * If not, then exception AccessDeniedException will be thrown.
     * @param owner User.
     * @param login Principal.
     */
    private void checkOwnership(User owner, Principal login) {
        if (!owner.getUsername().equals(login.getName())) {
            throw new AccessDeniedException(
                    String.format("User %s have no rights to perform requested action!", login.getName())
            );
        }
    }


    @RequestMapping("/view-adv-form")
    public ModelAndView getViewAdvForm(@RequestParam int id) {
        ModelAndView mv = new ModelAndView("adv-view");
        Advertisement advertisement = this.advertisementRepository.getAdvertisement(id);
        mv.addObject("advertisement", advertisement);
        mv.addObject("albumItems", this.albumItemsRepository.getAlbumItemIdArray(advertisement));
        return mv;
    }

}



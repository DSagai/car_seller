package sagai.dmytro.car.seller.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sagai.dmytro.car.seller.model.authentication.User;
import sagai.dmytro.car.seller.storage.UserRepository;
import sagai.dmytro.car.seller.utility.MailerService;

import javax.inject.Named;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

/**
 * TODO: add comments
 *
 * @author dsagai
 * @version TODO: set version
 * @since 11.05.2017
 */
@Controller
public class LoginController {
    @Autowired
    @Named("userRepository")
    private UserRepository userRepository;

    @Autowired
    @Named("mailerService")
    private MailerService mailerService;

    /**
     * returns custom login page.
     * @return
     */
    @RequestMapping("/login")
    public String getLoginForm() {
        return "login";
    }

    /**
     * Method returns "add-user-form" view, which performs
     * input of required data for new database user.
     * @param model
     * @return
     */
    @RequestMapping("/add-user-form")
    public String getAddUserForm(Model model) {
        model.addAttribute("user", new User());
        return "new-user";
    }

    /**
     * Method saves new user in database.
     * After user was saved, method sends
     * email request for user activation.
     * So the method also performs email address verification.
     * @param request
     * @param model
     * @param user
     * @param bindingResult
     * @param redirectAttributes
     * @return "user-created" view if validation was successful. Otherwise returns "new-user" form with validation
     * errors captured.
     * @throws MessagingException
     */
    @RequestMapping("/ADD_USER")
    public String addUser(HttpServletRequest request, Model model,
                          @Valid @ModelAttribute User user,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) throws MessagingException {
        if (bindingResult.hasErrors()) {
            return  "new-user";
        }
        switch (this.userRepository.checkDuplicates(user)) {
            case UserRepository.DUPLICATE_LOGIN :
                bindingResult.rejectValue("username","DuplicateKey.user.login", "This login already registered");
                return  "new-user";
            case UserRepository.DUPLICATE_EMAIL :
                bindingResult.rejectValue("email","DuplicateKey.user.email", "This email already registered");
                return  "new-user";
        }

        this.userRepository.addUser(user);


        redirectAttributes.addFlashAttribute("USERNAME",user.getUsername());
        mailerService.sendActivationMessage(user, request.getLocalAddr(),
                request.getLocalPort(), request.getContextPath());
        return ("redirect:user-created");
    }

    /**
     * Method returns "user-created" view, which informs that new
     * database user was successfully created.
     * @return
     */
    @RequestMapping("/user-created")
    public String getUserCreated() {
        return "user-created";
    }

    /**
     * Method ac
     * @param model
     * @param uuid
     * @return
     */
    @RequestMapping("/activate")
    public String activateUser(Model model, @RequestParam(value = "uuid") UUID uuid) {
        String result = "";
        User user = this.userRepository.getUser(uuid);
        if (user == null) {
            result = "Invalid activation request.";
        } else {
            user.setEnabled(true);
            this.userRepository.updateUser(user);
            result = String.format("User %s successfully activated.", user.getUsername());
        }

        model.addAttribute("result", result);
        return "user-activated";
    }
}



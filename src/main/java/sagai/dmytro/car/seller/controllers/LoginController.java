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

    @RequestMapping("/login")
    public String getLoginForm() {
        return "login";
    }

    @RequestMapping("/add-user-form")
    public String getAddUserForm(Model model) {
        model.addAttribute("user", new User());
        return "new-user";
    }

    @RequestMapping("/ADD_USER")
    public String addUser(HttpServletRequest request, Model model, @Valid @ModelAttribute User user, BindingResult bindingResult,
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

    @RequestMapping("/user-created")
    public String getUserCreated() {
        return "user-created";
    }

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



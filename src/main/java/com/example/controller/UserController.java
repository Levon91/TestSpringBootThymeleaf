package com.example.controller;

import com.example.data.form.MessageForm;
import com.example.data.model.Country;
import com.example.data.model.CurrentUser;
import com.example.data.model.User;
import com.example.enumeration.Role;
import com.example.exception.DatabaseException;
import com.example.manager.impl.UserMangerImpl;
import com.example.util.Constant;
import com.example.util.PubnubHelper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    private UserMangerImpl manger;

    @Autowired
    public UserController(UserMangerImpl manger) {
        this.manger = manger;
    }

    @RequestMapping("/home")
    public String actionHome(@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        if (currentUser.getUser().getRole() == Role.ADMIN) {
            try {
                List<User> users = manger.getAllUsers();
                model.addAttribute("users", users);
                return "admin/home";
            } catch (DatabaseException e) {
//                TODO
            }
        }
        List<Country> countries = manger.getUsersCountByCountries();
        model.addAttribute("list", countries);
        return "user/home";
    }


    @RequestMapping("/checkRole")
    public String checkAdmin(@AuthenticationPrincipal CurrentUser currentUser) {

        if (currentUser.getUser().getRole() == Role.ADMIN) {
            return "admin/home";
        }
        return "/home";
    }


    @RequestMapping("/list")
    public String actionList(Model model,
                             Authentication authentication) {
        CurrentUser currentUser = (CurrentUser) authentication.getPrincipal();
        List<User> users = manger.getList(currentUser.getId());
        model.addAttribute("list", users);
        return "user/list";
    }

    @RequestMapping(value = "/message", method = RequestMethod.POST)
    public String actionSendMessage(@Valid MessageForm form,
                                    Errors errors,
                                    RedirectAttributes ra,
                                    Authentication authentication) {
        if (errors.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            builder.append("<ul>");
            for (ObjectError error : errors.getAllErrors()) {
                builder.append(String.format("<li>%s</li>", error.getDefaultMessage()));
            }
            builder.append("</ul>");
            ra.addFlashAttribute(Constant.ERROR_MESSAGE, builder);
        } else {
            CurrentUser currentUser = (CurrentUser) authentication.getPrincipal();

            JSONObject message = new JSONObject();
            message.put("content", form.getMessage());
            message.put("user", currentUser.getFullName());
            message.put("time", System.currentTimeMillis() / 1000L);

            PubnubHelper.publish(form.getChannels(), message);
            ra.addFlashAttribute(Constant.SUCCESS_MESSAGE, "Message was send successfully.");
        }

        return "redirect:/list";
    }
}

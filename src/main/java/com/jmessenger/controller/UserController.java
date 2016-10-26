package com.jmessenger.controller;

import com.jmessenger.data.form.MessageForm;
import com.jmessenger.data.model.Country;
import com.jmessenger.data.model.CurrentUser;
import com.jmessenger.data.model.User;
import com.jmessenger.manager.impl.UserMangerImpl;
import com.jmessenger.util.Constant;
import com.jmessenger.util.PubnubHelper;
import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class UserController {

    private UserMangerImpl manger;

    @Autowired
    public UserController(UserMangerImpl manger) {
        this.manger = manger;
    }

    @RequestMapping("/home")
    public String actionHome(Model model) {
        List<Country> countries = manger.getUsersCountByCountries();
        model.addAttribute("list", countries);
        return "user/home";
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

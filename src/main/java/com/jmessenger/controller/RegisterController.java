package com.jmessenger.controller;

import com.jmessenger.data.form.RegisterForm;
import com.jmessenger.exception.DataBaseException;
import com.jmessenger.manager.impl.UserMangerImpl;
import com.jmessenger.util.Constant;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class RegisterController {

    private UserMangerImpl manger;

    @Autowired
    public RegisterController(UserMangerImpl manger) {
        this.manger = manger;
    }

    @RequestMapping("/signUp")
    public String actionSignUpForm(Model model) {
        model.addAttribute(new RegisterForm());
        return "auth/register";
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public String actionSignUp(@Valid RegisterForm form, BindingResult result, RedirectAttributes ra) {
        if (result.hasErrors()) {
            return "auth/register";
        }

        if (!form.getPassword().equals(form.getConfirmPassword())) {
            result.addError(new FieldError("registerForm", "confirmPassword", "Confirm password doesn't match."));
            return "auth/register";
        }

        if (manger.getCountByCountry(form.getCountry()) == Constant.COUNTRY_REGISTRATION_LIMIT) {
            ra.addFlashAttribute(Constant.ERROR_MESSAGE, "Registration from this country was limited.");
            return "redirect:/signUp";
        }

        if (manger.isEmailExist(form.getEmail())) {
            result.addError(new FieldError("user", "email", "Email already been taken."));
            return "auth/register";
        }

        try {
            manger.save(form);
            ra.addFlashAttribute(Constant.SUCCESS_MESSAGE, "You have registered successfully.");
        } catch (DataBaseException e) {
            ra.addFlashAttribute(Constant.ERROR_MESSAGE, "Server cannot create your account.Please try again.");
            return "redirect:/signUp";
        }

        return "redirect:/signIn";
    }
}

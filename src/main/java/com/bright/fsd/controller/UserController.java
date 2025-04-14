package com.bright.fsd.controller;

import com.bright.fsd.dto.request.UserRequestDto;
import com.bright.fsd.dto.response.UserResponseDto;
import com.bright.fsd.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public String getAllUsers(Model model) {
        List<UserResponseDto> userResponseDtos = userService.findAllUsers();
        model.addAttribute("userResponseDtos", userResponseDtos);
        return "users";
    }

    @GetMapping("/sign-up")
    public String showSignUpPage(Model model) {
        model.addAttribute(
                "userRequestDto",
                new UserRequestDto(
                        null,
                        null,
                        null,
                        null
                )
        );
        return "sign-up";
    }

    @PostMapping
    public String signUp(@Valid @ModelAttribute UserRequestDto userRequestDto, RedirectAttributes redirectAttributes) {
        Optional<UserResponseDto> optionalUserResponseDto = userService.createUser(userRequestDto);
        if (optionalUserResponseDto.isPresent()) {
            //success
            redirectAttributes.addFlashAttribute("success", userRequestDto.username() + "added successfully");
        } else {
            //something wrong
            redirectAttributes.addFlashAttribute("error", userRequestDto.username() + " already exists");
        }
        return "redirect:/api/v1/users";
    }

    @GetMapping("/{username}")
    public String getUser(@PathVariable String username, Model model, RedirectAttributes redirectAttributes) {
        Optional<UserResponseDto> optionalUserResponseDto = userService.findUserByUsername(username);
        if (optionalUserResponseDto.isPresent()) {
            model.addAttribute("userResponseDto", optionalUserResponseDto.get());
            return "edit-user";
        } else {
            redirectAttributes.addFlashAttribute("error", username + " does not exist");
            return "redirect:/api/v1/users/";
        }
    }

    @DeleteMapping("/{username}")
    public String deleteUser(@PathVariable String username, RedirectAttributes redirectAttributes) {
        userService.deleteUser(username);
        redirectAttributes.addFlashAttribute("success", username + " deleted successfully");
        return "redirect:/api/v1/users";
    }
}

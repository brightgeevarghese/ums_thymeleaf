package com.bright.srs.controller;

import com.bright.srs.dto.request.UserRequestDto;
import com.bright.srs.dto.response.UserResponseDto;
import com.bright.srs.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String listUsers(Model model, @ModelAttribute("success") String success) {
        List<UserResponseDto> userResponseDtos = userService.findAllUsers();
        System.out.println("Users: " + userResponseDtos);
        model.addAttribute("users", userResponseDtos);
        return "users_page";
    }

    @GetMapping("/sign-up")
    public String showSignUpPage(Model model, @ModelAttribute("error") String error) {
        model.addAttribute(
                "userRequestDto",
                new UserRequestDto(
                        null,
                        null,
                        null,
                        null
                )
        );
        return "add_user";
    }

    @PostMapping
    public String addUser(@ModelAttribute UserRequestDto userRequestDto, RedirectAttributes redirectAttributes) {
        Optional<UserResponseDto> userResponseDto = userService.createUser(userRequestDto);
        if (userResponseDto.isPresent()) {
            redirectAttributes.addFlashAttribute("success", "User added successfully");
            return "redirect:/api/v1/users";
        }
        redirectAttributes.addFlashAttribute("error", "User creation failed");
        return "redirect:/api/v1/users/sign-up";
    }

    @DeleteMapping("/{username}")
    public String deleteUser(@PathVariable String username, RedirectAttributes redirectAttributes) {
        userService.deleteUser(username);
        redirectAttributes.addFlashAttribute("success", "User deleted successfully");
        return "redirect:/api/v1/users";
    }

    @GetMapping("/edit/{username}")
    public String showEditPage(@PathVariable String username, Model model, RedirectAttributes redirectAttributes) {
        Optional<UserResponseDto> userResponseDto = userService.findUserByUsername(username);
        if (userResponseDto.isPresent()) {
            model.addAttribute("userRequestDto", new UserRequestDto(null, null, username, null));
            return "edit_user";
        }
        redirectAttributes.addFlashAttribute("error", "User not found");
        return "redirect:/api/v1/users";
    }

    @PutMapping("/{username}")
    public String updateUser(@PathVariable String username, @ModelAttribute UserRequestDto userRequestDto, RedirectAttributes redirectAttributes) {
        Optional<UserResponseDto> userResponseDto = userService.updateUser(username, userRequestDto);
        if (userResponseDto.isPresent()) {
            redirectAttributes.addFlashAttribute("success", "User updated successfully");
            return "redirect:/api/v1/users";
        }
        redirectAttributes.addFlashAttribute("error", "User not found");
        return "redirect:/api/v1/users";
    }

}

package com.hotelbooking.controller;

import com.hotelbooking.model.Room;
import com.hotelbooking.model.User;
import com.hotelbooking.service.AdminService;
import com.hotelbooking.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {
        private final AdminService adminService;

        private final UserService userService;

        public AdminController(AdminService adminService,UserService userService) {
            this.adminService = adminService;
            this.userService = userService;
        }

        // Get all rooms
        @GetMapping("/rooms")
        public List<Room> getRooms() {
            return adminService.getAllRooms();
        }

        // Get all users

        // Add a room
        @PostMapping("/rooms")
        public Room createRoom(@RequestBody Room room) {
            return adminService.createRoom(room);
        }

        // Delete a room
        @DeleteMapping("/rooms/{id}")
        public void deleteRoom(@PathVariable Long id) {
            adminService.deleteRoom(id);
        }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Add a new user
    @PostMapping("/users")
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    // Update user
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    // Block (delete) a user
    @DeleteMapping("/users/{id}/block")
    public String blockUser(@PathVariable Long id) {
        userService.blockUser(id);
        return "User blocked successfully!";
    }

    // Unblock a user
    @PutMapping("/users/{id}/unblock")
    public String unblockUser(@PathVariable Long id) {
        userService.unblockUser(id);
        return "User unblocked successfully!";
    }
        // Update user role
//        @PutMapping("/users/{id}")
//        public void updateUserRole(@PathVariable Long id, @RequestBody String role) {
//            adminService.updateUserRole(id, role);
//        }
    }

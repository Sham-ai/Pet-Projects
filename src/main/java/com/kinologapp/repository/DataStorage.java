package com.kinologapp.repository;

import com.kinologapp.model.entity.User;
import com.kinologapp.model.enums.Role;
import com.kinologapp.model.payment.Payment;

import java.util.*;
import java.util.stream.Collectors;

public class DataStorage {

    private static final Map<Long, User> users = new HashMap<>();
    private static final List<Payment> payments = new ArrayList<>();

    public static void addUser(User user) {
        users.put(user.getId(), user);
    }

    public static User getUser(long id) {
        return users.get(id);
    }

    public static List<User> getUsersByRole(Role role) {
        return users.values().stream()
                .filter(u -> u.hasRole(role))
                .collect(Collectors.toList());
    }

    public static void addPayment(Payment payment) {
        payments.add(payment);
    }

    public static List<Payment> getAllPayments() {
        return payments;
    }
}

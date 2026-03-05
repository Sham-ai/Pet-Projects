package com.kinologapp.model.entity;

import com.kinologapp.model.enums.Role;
import com.kinologapp.util.Notifiable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * User — "человек" в системе.
 *
 * Один человек может иметь несколько ролей (TRAINER, CLIENT, JUDGE и т.д.),
 * поэтому роли — Set<Role>.
 *
 * Специфичные данные роли вынесены в профили:
 * TrainerProfile / ClientProfile.
 */
public class User implements Notifiable {

    private final long id;          // id не меняем
    private String firstName;
    private String phoneNumber;

    private final Set<Role> roles = new HashSet<>();

    // Профили: могут быть null, если роль не включена
    private TrainerProfile trainerProfile;
    private ClientProfile clientProfile;

    public User(long id, String firstName, String phoneNumber) {
        this.id = id;
        this.firstName = Objects.requireNonNull(firstName, "firstName");
        this.phoneNumber = Objects.requireNonNull(phoneNumber, "phoneNumber");
    }

    /**
     * Добавление роли, которая не требует отдельного профиля.
     * TRAINER/CLIENT добавляем только через enableTrainer/enableClient,
     * чтобы не получить "битую" модель (роль есть, профиля нет).
     */
    public void addRole(Role role) {
        Objects.requireNonNull(role, "role");
        if (role == Role.TRAINER || role == Role.CLIENT) {
            throw new IllegalArgumentException("Use enableTrainer/enableClient for TRAINER/CLIENT roles");
        }
        roles.add(role);
    }

    public boolean hasRole(Role role) {
        return roles.contains(role);
    }

    public Set<Role> getRoles() {
        return Collections.unmodifiableSet(roles);
    }

    public void enableTrainer(TrainerProfile profile) {
        this.trainerProfile = Objects.requireNonNull(profile, "trainerProfile");
        roles.add(Role.TRAINER);
    }

    public void enableClient(ClientProfile profile) {
        this.clientProfile = Objects.requireNonNull(profile, "clientProfile");
        roles.add(Role.CLIENT);
    }

    public TrainerProfile getTrainerProfile() {
        return trainerProfile;
    }

    public ClientProfile getClientProfile() {
        return clientProfile;
    }

    @Override
    public void sendNotification(String message) {
        System.out.println("Уведомление для " + firstName + ": " + message);
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = Objects.requireNonNull(firstName, "firstName");
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = Objects.requireNonNull(phoneNumber, "phoneNumber");
    }
}

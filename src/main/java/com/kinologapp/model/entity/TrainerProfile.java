package com.kinologapp.model.entity;

/**
 * TrainerProfile — данные роли TRAINER.
 * Рабочее время живёт здесь (а не в Appointment).
 */
public class TrainerProfile {

    private int experienceYears;

    // пока просто часы 0..23
    private int startWorkHour;
    private int endWorkHour;

    public TrainerProfile(int experienceYears, int startWorkHour, int endWorkHour) {
        this.experienceYears = experienceYears;
        this.startWorkHour = startWorkHour;
        this.endWorkHour = endWorkHour;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    public int getStartWorkHour() {
        return startWorkHour;
    }

    public void setStartWorkHour(int startWorkHour) {
        this.startWorkHour = startWorkHour;
    }

    public int getEndWorkHour() {
        return endWorkHour;
    }

    public void setEndWorkHour(int endWorkHour) {
        this.endWorkHour = endWorkHour;
    }
}

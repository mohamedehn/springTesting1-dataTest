package com.testSpring1.questDataTest.DTO;

public class FiremanStatsDTO {
    int firemenCount;
    int firesCount;

    public FiremanStatsDTO(int firemenCount, int firesCount) {
        this.firemenCount = firemenCount;
        this.firesCount = firesCount;
    }

    public int getFiremenCount() {
        return firemenCount;
    }

    public int setFiremenCount(int firemenCount) {
        this.firemenCount = firemenCount;
        return firemenCount;
    }

    public int getFiresCount() {
        return firesCount;
    }

    public int setFiresCount(int firesCount) {
        this.firesCount = firesCount;
        return firesCount;
    }

    public FiremanStatsDTO() {
    }

}

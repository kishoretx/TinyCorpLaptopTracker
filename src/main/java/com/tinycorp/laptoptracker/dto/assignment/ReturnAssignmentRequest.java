package com.tinycorp.laptoptracker.dto.assignment;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class ReturnAssignmentRequest {

    @NotNull
    private LocalDate returnedDate;

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(LocalDate returnedDate) {
        this.returnedDate = returnedDate;
    }
}

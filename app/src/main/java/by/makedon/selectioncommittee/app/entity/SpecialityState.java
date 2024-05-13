package by.makedon.selectioncommittee.app.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecialityState {
    private String speciality;
    private int numberOfSeats;
    private int filledDocuments;
}

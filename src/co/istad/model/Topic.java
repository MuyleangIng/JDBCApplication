package co.istad.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Topic {
    private Integer id;
    private String name;
    private String description;
    private Boolean status;

}

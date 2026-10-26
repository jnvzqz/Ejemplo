package com.jv.spring.ejemplo.core.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Superhero {

    @Setter(AccessLevel.NONE)
    private Long id;
    private String heroName;
    private String realName;
    private String superpower;
}

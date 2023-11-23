package com.example.jedis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("user")
@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {


    private static final long serialVersionUID = 5962011647926411830L;

    public enum Gender {
        MALE, FEMALE
    }

    private Long id;
    private String name;
    private Gender gender;

}

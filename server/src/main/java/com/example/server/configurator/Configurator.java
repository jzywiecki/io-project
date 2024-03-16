package com.example.server.configurator;

import com.example.server.model.Role;
import com.example.server.model.Room;
import com.example.server.model.Term;
import com.example.server.model.User;
import com.example.server.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Configuration
public class Configurator {
    /**
     *  Environment variable.
     */
    private final Environment env;
    /**
     *  Constructor.
     * @param environment environment.
     */
    @Autowired
    public Configurator(final Environment environment) {
        this.env = environment;
    }
    /**
     *  Data source.
     * @return data source.
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:./enroll.sqlite");
        dataSource.setUsername("admin");
        dataSource.setPassword("admin");
        return dataSource;
    }

    /**
     * Generating example data to database.
     * @param resultRepository result repository.
     * @param roomRepository room repository.
     * @param termRepository term repository.
     * @param userRepository user repository.
     * @param voteRepository vote repository.
     * @return command line runner.
     *
     */
    @Bean
    CommandLineRunner commandLineRunner(ResultRepository resultRepository,
                                        RoomRepository roomRepository,
                                        TermRepository termRepository,
                                        UserRepository userRepository,
                                        VoteRepository voteRepository
    ) {
        return args -> {
            if (userRepository.count() == 0) {
                User exampleStudent1 = User.builder()
                        .firstName("Jan")
                        .lastName("Kowalski")
                        .email("jankowalski@student.agh.edu.pl")
                        .password("password")
                        .role(Role.STUDENT)
                        .active(true)
                        .build();

                User exampleStudent2 = User.builder()
                        .firstName("Anna")
                        .lastName("Kowalska")
                        .email("annakowalska@student.agh.edu.pl")
                        .password("password")
                        .role(Role.STUDENT)
                        .active(true)
                        .build();

                User exampleTeacher = User.builder()
                        .firstName("Piotr")
                        .lastName("Nowak")
                        .email("piotrnowak@agh.edu.pl")
                        .password("password")
                        .role(Role.TEACHER)
                        .active(true)
                        .build();

                //save example users
                userRepository.save(exampleStudent1);
                userRepository.save(exampleStudent2);
                userRepository.save(exampleTeacher);

                Room exampleRoom = Room.builder()
                        .deadLineTime(Time.valueOf(LocalTime.of(13, 30)))
                        .deadlineDate(java.sql.Date.valueOf(LocalDateTime.now().toLocalDate()))
                        .description("Example room description")
                        .name("Example room")
                        .build();

                //save example room
                roomRepository.save(exampleRoom);

                Term exampleTerm1 = Term.builder()
                        .day(DayOfWeek.MONDAY)
                        .startTime(Time.valueOf(LocalTime.of(13, 30)))
                        .endTime(Time.valueOf(LocalTime.of(13, 30)))
                        .build();

                Term exampleTerm2 = Term.builder()
                        .day(DayOfWeek.WEDNESDAY)
                        .startTime(Time.valueOf(LocalTime.of(13, 30)))
                        .endTime(Time.valueOf(LocalTime.of(13, 30)))
                        .build();

                Term exampleTerm3 = Term.builder()
                        .day(DayOfWeek.WEDNESDAY)
                        .startTime(Time.valueOf(LocalTime.of(13, 30)))
                        .endTime(Time.valueOf(LocalTime.of(13, 30)))
                        .build();

                //save terms
                termRepository.save(exampleTerm1);
                termRepository.save(exampleTerm2);
                termRepository.save(exampleTerm3);
            }
        };
    }
}

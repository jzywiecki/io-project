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
    CommandLineRunner commandLineRunner(final ResultRepository resultRepository,
                                        final RoomRepository roomRepository,
                                        final TermRepository termRepository,
                                        final UserRepository userRepository,
                                        final VoteRepository voteRepository
    ) {
        return args -> {
            if (userRepository.count() == 0) {
                final int exampleHour = 13;
                final int exampleMinute = 30;
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
                        .deadlineTime(
                                LocalTime.of(exampleHour, exampleMinute))
                        .deadlineDate(java.sql.Date.valueOf(
                                LocalDateTime.now().toLocalDate()))
                        .description("Example room description")
                        .name("Example room")
                        .build();

                //save example room
                roomRepository.save(exampleRoom);

                for (int i=0; i<5; i++) {
                    DayOfWeek day = DayOfWeek.of(i+1);
                    LocalTime time = LocalTime.of(8, 0);
                    for (int j=0; j<7; j++) {
                        Term term = Term.builder()
                                .day(day)
                                .startTime(time)
                                .endTime(time.plusHours(1).plusMinutes(30))
                                .build();
                        termRepository.save(term);
                        time = time.plusHours(1).plusMinutes(45);
                    }
                }
            }
        };
    }
}

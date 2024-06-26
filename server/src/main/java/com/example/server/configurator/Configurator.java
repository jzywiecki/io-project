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
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Configuration
@EnableTransactionManagement
@EnableScheduling
public class Configurator {
    /**
     *  Environment variable.
     */
    private final Environment env;
    /**
     *  Constructor.
     * @param environment environment.
     */
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public Configurator(final Environment environment) {
        this.env = environment;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    /**
     *  Data source.
     * @return data source.
     */
    @Bean
    public DataSource dataSource() {
        SQLiteConfig config = new SQLiteConfig();
        config.setJournalMode(SQLiteConfig.JournalMode.WAL);

        SQLiteDataSource dataSource = new SQLiteDataSource(config);
        dataSource.setUrl("jdbc:sqlite:./enroll.sqlite");

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
                        .password(passwordEncoder.encode("password"))
                        .role(Role.STUDENT)
                        .active(true)
                        .build();

                User exampleStudent2 = User.builder()
                        .firstName("Anna")
                        .lastName("Kowalska")
                        .email("annakowalska@student.agh.edu.pl")
                        .password(passwordEncoder.encode("password"))
                        .role(Role.STUDENT)
                        .active(true)
                        .build();

                User exampleStudent3 = User.builder()
                        .firstName("Adam")
                        .lastName("Trybus")
                        .email("adamtrybus@student.agh.edu.pl")
                        .password(passwordEncoder.encode("password"))
                        .role(Role.STUDENT)
                        .active(true)
                        .build();

                User exampleStudent4 = User.builder()
                        .firstName("Piotr")
                        .lastName("Olszak")
                        .email("piotrolszak@student.agh.edu.pl")
                        .password(passwordEncoder.encode("password"))
                        .role(Role.STUDENT)
                        .active(true)
                        .build();

                User exampleTeacher = User.builder()
                        .firstName("Piotr")
                        .lastName("Nowak")
                        .email("piotrnowak@agh.edu.pl")
                        .password(passwordEncoder.encode("password"))
                        .role(Role.TEACHER)
                        .active(true)
                        .build();

                //save example users
                userRepository.save(exampleStudent1);
                userRepository.save(exampleStudent2);
                userRepository.save(exampleStudent3);
                userRepository.save(exampleStudent4);
                userRepository.save(exampleTeacher);

                //save terms
                final int days = 5;
                final int termsPerDay = 7;
                final int startHour = 8;
                final int startMinute = 0;
                final int durationHour = 1;
                final int durationMinute = 30;
                final int intervalHour = 1;
                final int intervalMinute = 45;

                Set<Term> terms = new HashSet<>();

                for (int i = 0; i < days; i++) {
                    DayOfWeek day = DayOfWeek.of(i + 1);
                    LocalTime time = LocalTime.of(startHour, startMinute);
                    for (int j = 0; j < termsPerDay; j++) {
                        Term term = Term.builder()
                                .day(day)
                                .startTime(time)
                                .endTime(time
                                        .plusHours(durationHour)
                                        .plusMinutes(durationMinute))
                                .build();
                        termRepository.save(term);

                        time = time
                                .plusHours(intervalHour)
                                .plusMinutes(intervalMinute);

                        if ((i+j) % 5 == 0) {
                            terms.add(term);
                        }
                    }
                }

                Room exampleRoom = Room.builder()
                        .deadlineTime(
                                LocalTime.of(exampleHour, exampleMinute))
                        .deadlineDate(java.sql.Date.valueOf(
                                LocalDateTime.now().plusYears(1).toLocalDate()))
                        .description("Example room description")
                        .name("Example room")
                        .joinedUsers(new HashSet<>())
                        .finished(false)
                        .build();

                //save example room
                roomRepository.save(exampleRoom);

                exampleRoom.setTerms(terms);

                //init user
                User user = User.builder()
                        .email("malysz@student.agh.edu.pl")
                        .active(false)
                        .role(Role.STUDENT)
                        .build();
                userRepository.save(user);

                exampleRoom.getJoinedUsers().add(user);
                roomRepository.save(exampleRoom);
            }
        };
    }
}

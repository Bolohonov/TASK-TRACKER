package repository;

import entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TasksRepository extends JpaRepository<Users, Integer> {

    Task findByLogin(String login);
}

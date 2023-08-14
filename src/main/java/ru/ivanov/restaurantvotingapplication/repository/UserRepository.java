package ru.ivanov.restaurantvotingapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ivanov.restaurantvotingapplication.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
}

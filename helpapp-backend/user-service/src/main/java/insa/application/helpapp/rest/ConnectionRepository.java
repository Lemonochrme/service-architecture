package insa.application.helpapp.rest;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConnectionRepository extends JpaRepository<Connection, Integer> {
    List<Connection> findByIdUser(int idUser);
}
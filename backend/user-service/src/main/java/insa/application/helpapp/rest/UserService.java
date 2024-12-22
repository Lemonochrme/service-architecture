package insa.application.helpapp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private ConnectionRepository connectionRepository;

    public boolean checkToken(int idUser, String token) {
        List<Connection> connections = connectionRepository.findByIdUser(idUser);

        if (connections.isEmpty()) {
            return false;
        }
        Connection c = connections.getFirst();
        return c.getToken().equals(token) && c.getExpiresAt().isAfter(java.time.LocalDateTime.now());
    }
}

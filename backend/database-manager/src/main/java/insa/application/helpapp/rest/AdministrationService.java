package insa.application.helpapp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministrationService {

    @Autowired
    private ConnectionRepository connectionRepository;
    @Autowired
    private UserRepository userRepository;

    public boolean checkToken(int idUser, String token) {
        List<Connection> connections = connectionRepository.findByIdUser(idUser);

        if (connections.isEmpty()) {
            return false;
        }
        Connection c = connections.getFirst();
        return c.getToken().equals(token) && c.getExpiresAt().isAfter(java.time.LocalDateTime.now());
    }

    public Optional<Integer> getRole(int idUser)
    {
        Optional<User> user = userRepository.findById(idUser);
        if (!user.isPresent()) {
            return Optional.empty();
        }
        return Optional.of(user.get().getIdRole());
    }
}

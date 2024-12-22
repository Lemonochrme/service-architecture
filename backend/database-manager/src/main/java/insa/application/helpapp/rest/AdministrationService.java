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
    private RoleRepository roleRepository;

    public boolean checkToken(int idUser, String token) {
        List<Connection> connections = connectionRepository.findByIdUser(idUser);

        if (connections.isEmpty()) {
            return false;
        }
        Connection c = connections.getFirst();
        return c.getToken().equals(token) && c.getExpiresAt().isAfter(java.time.LocalDateTime.now());
    }

    public boolean checkRole(int idUser, int role)
    {
        List<Connection> connections = connectionRepository.findByIdUser(idUser);

        if (connections.isEmpty()) {
            return false;
        }
        // Connection c = connections.getFirst();
        Optional<Role> roleOption = roleRepository.findById(role);
        if(!roleOption.isPresent()) {
            return false;
        }
        return true;
    }

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }
}

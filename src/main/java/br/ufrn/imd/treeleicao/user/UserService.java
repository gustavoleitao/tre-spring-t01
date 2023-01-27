package br.ufrn.imd.treeleicao.user;

import br.ufrn.imd.treeleicao.common.AbstractService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserService extends AbstractService<User, UserDTO> {

    private PasswordEncoder encoder;

    private UserRepository userRepository;

    public UserService(UserRepository repository, PasswordEncoder encoder) {
        super(repository);
        this.encoder = encoder;
        this.userRepository = repository;
    }

    public UserDTO findUserByUsername(String username){
        var modelMapper = new ModelMapper();
        var user = userRepository.findUserByUsername(username);
        var userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }

    @Override
    public UserDTO saveOrUpdate(UserDTO newCandidate) {
        var preparedUser = prepareUser(newCandidate);
        return super.saveOrUpdate(preparedUser);
    }

    private UserDTO prepareUser(UserDTO newCandidate) {
        if (Objects.nonNull(newCandidate.getPassword())){
            newCandidate.setPassword(encoder.encode(newCandidate.getPassword()));
        }
        return newCandidate;
    }

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public Class<UserDTO> getDTOClass() {
        return UserDTO.class;
    }

    @Override
    public String getEntityName() {
        return "User";
    }

}

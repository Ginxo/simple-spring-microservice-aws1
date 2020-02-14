package mingorance.cano.simplespringmicroserviceaws1.resource;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import mingorance.cano.simplespringmicroserviceaws1.resource.criteria.UserCriteria;
import mingorance.cano.simplespringmicroserviceaws1.resource.dto.UserDTO;
import mingorance.cano.simplespringmicroserviceaws1.resource.errors.BadRequestAlertException;
import mingorance.cano.simplespringmicroserviceaws1.resource.util.HeaderUtil;
import mingorance.cano.simplespringmicroserviceaws1.resource.util.PaginationUtil;
import mingorance.cano.simplespringmicroserviceaws1.resource.util.ResponseUtil;
import mingorance.cano.simplespringmicroserviceaws1.service.UserService;
import mingorance.cano.simplespringmicroserviceaws1.resource.service.query.UserQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

@RestController
@RequestMapping("/api/users")
public class UserResource {

    private final UserService userService;
    private final UserQueryService userQueryService;

    public UserResource(UserService userService, UserQueryService userQueryService) {
        this.userService = userService;
        this.userQueryService = userQueryService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO userDTO) throws URISyntaxException {
        if (userDTO.getId() != null) {
            throw new BadRequestAlertException("A new user cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserDTO result = userService.save(userDTO);
        return ResponseEntity.created(new URI("/api/users/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PutMapping
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO userDTO) throws URISyntaxException {
        if (userDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserDTO result = userService.save(userDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userDTO.getId().toString()))
                .body(result);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll(UserCriteria criteria, Pageable pageable) {
        Page<UserDTO> page = userQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count(UserCriteria criteria) {
        return ResponseEntity.ok().body(userQueryService.countByCriteria(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable Long id) {
        Optional<UserDTO> userDTO = userService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

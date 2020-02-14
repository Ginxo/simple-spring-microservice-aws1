package mingorance.cano.simplespringmicroserviceaws1.resource.criteria;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import mingorance.cano.simplespringmicroserviceaws1.resource.service.filter.LongFilter;
import mingorance.cano.simplespringmicroserviceaws1.resource.service.filter.StringFilter;

@Getter
@Setter
public class UserCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;
    private StringFilter name;
    private StringFilter surname;
}

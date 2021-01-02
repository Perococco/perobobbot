package perobobbot.security;

import org.springframework.security.core.Authentication;

import java.io.Serializable;

public interface PermissionEvaluator {

    boolean hasPermissionWithObject(Authentication authentication, Object targetDomainObject, Object permission);

    boolean hasPermissionWithId(Authentication authentication, Serializable targetId, Object permission);


    PermissionEvaluator DENY_ALL = new PermissionEvaluator() {
        @Override
        public boolean hasPermissionWithObject(Authentication authentication, Object targetDomainObject, Object permission) {
            return false;
        }

        @Override
        public boolean hasPermissionWithId(Authentication authentication, Serializable targetId, Object permission) {
            return false;
        }
    };

}

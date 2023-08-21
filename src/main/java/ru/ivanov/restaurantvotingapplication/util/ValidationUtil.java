package ru.ivanov.restaurantvotingapplication.util;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import ru.ivanov.restaurantvotingapplication.HasId;
import ru.ivanov.restaurantvotingapplication.error.IllegalRequestDataException;
import ru.ivanov.restaurantvotingapplication.error.NotFoundException;

public class ValidationUtil {

    private static final Validator validator;

    static {
        //  From Javadoc: implementations are thread-safe and instances are typically cached and reused.
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        //  From Javadoc: implementations of this interface must be thread-safe
        validator = factory.getValidator();
    }

    private ValidationUtil() {
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        checkNotFoundWithId(object != null, id);
        return object;
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    //    public static void checkNew(BaseEntity entity) {
//        if (!entity.isNew()) {
//            throw new IllegalArgumentException(entity + " must be new (id=null)");
//        }
//    }
    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must be new (id=null)");
        }
    }

    //    public static void assureIdConsistent(BaseEntity entity, int id) {
////      conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
//        if (entity.isNew()) {
//            entity.setId(id);
//        } else if (entity.id() != id) {
//            throw new IllegalArgumentException(entity + " must be with id=" + id);
//        }
//    }
    public static void assureIdConsistent(HasId bean, int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must has id=" + id);
        }
    }
}

package BLL.Validators;

/**
 * @param <T>
 */
public interface Validator<T> {

    /**
     * @param t
     */
    public void validate(T t);
}

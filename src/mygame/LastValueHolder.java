/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

/**
 *
 * @author krzysiek
 * @param <T>
 */
public class LastValueHolder<T> {
    private T lastValue = null;
    private final LastValueHolderDeterminant<T> determinant;
    public LastValueHolder(LastValueHolderDeterminant<T> determinant) {
        this.determinant = determinant;
    }
    public LastValueHolder(LastValueHolderDeterminant<T> determinant, T defaultValue) {
        this(determinant);
        lastValue = defaultValue;
    }
    public T getValue(T value) {
        if (determinant.isValidValue(value)) {
            lastValue = value;
            return value;
        } else {
            return lastValue;
        }
    }
    public interface LastValueHolderDeterminant<T> {
        boolean isValidValue(T value);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controler.exceptions;

/**
 *
 * @author Stasio
 */
public class EmptyFile extends Exception {

    /**
     * Creates a new instance of
     * <code>EmptyFile</code> without detail message.
     */
    public EmptyFile() {
    }

    /**
     * Constructs an instance of
     * <code>EmptyFile</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public EmptyFile(String msg) {
        super(msg);
    }
}

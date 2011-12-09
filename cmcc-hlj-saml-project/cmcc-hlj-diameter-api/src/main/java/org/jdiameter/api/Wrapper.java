/*
 * Copyright (c) 2006 jDiameter.
 * https://jdiameter.dev.java.net/
 *
 * License: Lesser General Public License (LGPL)
 *
 * e-mail: erick.svenson@yahoo.com, artem.litvinov@gmail.com
 *
 */
package org.jdiameter.api;

/**
 * Interface for JDiameter classes which provide the ability to retrieve the d
 * elegate instance when the instance in question is in fact a proxy class.
 * The wrapper pattern is employed by many JDiameter stack implementations to
 * provide extensions beyond the traditional JDiameter API that are specific to a
 * data source. Developers may wish to gain access to these resources that are wrapped (
 * the delegates) as proxy class instances representing the the actual resources.
 * This interface describes a standard mechanism to access these wrapped resources
 * represented by their proxy, to permit direct access to the resource delegates.
 * @version 1.5.1  Final
 */

public interface Wrapper {

    /**
     * Returns true if this either implements the interface argument or is directly or indirectly a wrapper for
     * an object that does. Returns false otherwise. If this implements the interface then return true, else if t
     * his is a wrapper then return the result of recursively calling isWrapperFor on the wrapped object. If
     * this does not implement the interface and is not a wrapper, return false. This method should be implemented
     * as a low-cost operation compared to unwrap so that callers can use this method to avoid expensive unwrap
     * calls that may fail. If this method returns true then calling unwrap with the same argument should succeed.
     * @param iface A Class defining an interface that the result must implement.
     * @return true if this implements the interface or directly or indirectly wraps an object that does.
     * @throws InternalException If no object found that implements the interface
     */

    boolean isWrapperFor(java.lang.Class<?> iface) throws InternalException;

    /**
     * Returns an object that implements the
     * given interface to allow access to non-standard methods, or standard methods
     * not exposed by the proxy. The result may be either the object found to implement
     * the interface or a proxy for that object. If the receiver implements the interface
     * then that is the object. If the receiver is a wrapper and the wrapped object implements
     * the interface then that is the object. Otherwise the object is the result of calling unwrap
     * recursively on the wrapped object. If the receiver is not a wrapper and does not implement the
     * interface, then an SQLException is thrown.
     *
     * @param iface A Class defining an interface that the result must implement.
     * @return an object that implements the interface. May be a proxy for the actual implementing object.
     * @throws InternalException If no object found that implements the interface
     */

    <T> T unwrap(java.lang.Class<T> iface) throws InternalException;
}

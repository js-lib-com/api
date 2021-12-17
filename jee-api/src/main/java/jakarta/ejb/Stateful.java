/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package jakarta.ejb;

import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * Component-defining annotation for a stateful session bean.
 *
 * @since EJB 3.0
 */

@Target(TYPE)
@Retention(RUNTIME)
public @interface Stateful {

    /**
     * The ejb-name for this bean. Defaults to the unqualified name of the stateful session bean class.
     *
     * @return a {@link java.lang.String} object.
     */
    String name() default "";

    /**
     * A product specific name(e.g. global JNDI name) that this session bean should be mapped to.
     *
     * Application servers are not required to support any particular form or type of mapped name, nor the ability to use
     * mapped names. The mapped name is product-dependent and often installation-dependent. No use of a mapped name is
     * portable.
     *
     * @return a {@link java.lang.String} object.
     */
    String mappedName() default "";

    /**
     * A string describing the stateful session bean.
     *
     * @return a {@link java.lang.String} object.
     */
    String description() default "";

    /**
     * Specifies whether this stateful session bean is passivation capable
     *
     * @since EJB 3.2
     * @return a boolean.
     */
    boolean passivationCapable() default true;

}

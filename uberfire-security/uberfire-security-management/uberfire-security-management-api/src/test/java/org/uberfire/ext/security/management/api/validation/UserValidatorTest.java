/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.uberfire.ext.security.management.api.validation;

import org.jboss.errai.security.shared.api.identity.User;
import org.junit.Test;
import org.uberfire.ext.security.management.util.SecurityManagementUtils;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserValidatorTest {

    @Test
    public void testValid() {
        final User user = SecurityManagementUtils.createUser("user1");
        Set<ConstraintViolation<User>> violations = new UserValidatorTestImpl().validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testBlankUsername() {
        final User blankUser = SecurityManagementUtils.createUser("");
        final Set<ConstraintViolation<User>> violations1 = new UserValidatorTestImpl().validate(blankUser);
        assertTrue(violations1.size() == 1);
        final ConstraintViolation<User> violation = violations1.iterator().next();
        assertEquals(violation.getPropertyPath().iterator().next().getName(), "identifier");
        assertEquals(violation.getMessage(), UserValidator.KEY_NAME_NOT_EMPTY);
    }
    
    public static class UserValidatorTestImpl extends UserValidator {
        @Override
        public String getMessage(String key) {
            return key;
        }
    }
}

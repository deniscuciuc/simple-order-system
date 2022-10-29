package com.stefanini.ordersystem.service.impl.helper;

import com.stefanini.ordersystem.service.impl.exceptions.InvalidIdException;
import com.stefanini.ordersystem.service.impl.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ValuesCheckerTest {



    /**
     * Unit test for {@link ValuesChecker#verifyIfOrderTypeExists(String) verifyIfOrderTypeExists} method
     */
    @Test
    void shouldThrowNotFoundExceptionIfOrderTypeNotFound() {
        String fakeOrderType = "dog";

        assertThrows(NotFoundException.class, () -> ValuesChecker.verifyIfOrderTypeExists(fakeOrderType));
    }

    /**
     * Unit test for {@link ValuesChecker#verifyIfOrderStatusExists(String) verifyIfOrderStatusExists} method
     */
    @Test
    void shouldThrowNotFoundExceptionIfOrderStatusNotFound() {
        String fakeOrderStatus = "beautiful";

        assertThrows(NotFoundException.class, () -> ValuesChecker.verifyIfOrderStatusExists(fakeOrderStatus));
    }

    /**
     * Unit test for {@link ValuesChecker#verifyIfIdIsValid(Long) verifyIfIdIsValid} method
     */
    @Test
    void shouldThrowInvalidIdExceptionIfIdIsNegative() {
        Long negativeId = (long) -25;

        assertThrows(InvalidIdException.class, () -> ValuesChecker.verifyIfIdIsValid(negativeId));
    }

    /**
     * Unit test for {@link ValuesChecker#verifyIfIdIsValid(Long) verifyIfIdIsValid} method
     */
    @Test
    void shouldThrowInvalidIdExceptionIfIdIsZero() {
        Long zeroId = (long) 0;

        assertThrows(InvalidIdException.class, () -> ValuesChecker.verifyIfIdIsValid(zeroId));
    }
}
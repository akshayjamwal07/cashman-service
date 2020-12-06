package sc.com.assessment.cashman.restcontroller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static sc.com.assessment.cashman.util.TestUtility.getCashRequest;
import static sc.com.assessment.cashman.util.TestUtility.getCashResponse;
import static sc.com.assessment.cashman.util.TestUtility.getDenominationResponse;

import java.math.BigInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sc.com.assessment.cashman.controller.CashManController;
import sc.com.assessment.cashman.dto.response.GetCashResponse;
import sc.com.assessment.cashman.dto.response.GetDenominationResponse;


@ExtendWith(MockitoExtension.class)
class CashManRestControllerTest {

    @Mock
    private CashManController cashManController;

    private CashManRestController cashManRestController;

    @BeforeEach
    void setUp() {
        cashManRestController = new CashManRestController(cashManController);
    }

    @Test
    void testWithdrawCash() {
        when(cashManController.withdrawCash(BigInteger.valueOf(360))).thenReturn(getCashResponse());

        //test
        final GetCashResponse retrievedAmount = cashManRestController.withdrawCash(BigInteger.valueOf(360));

        //verify
        assertNotNull(retrievedAmount);
        assertNotNull(retrievedAmount.getCashResponse());
        assertEquals(retrievedAmount.getCashResponse().size(), 3);
        assertEquals(retrievedAmount.getCashResponse().get(0).getDenominationType(), "Fifty");
        assertEquals(retrievedAmount.getCashResponse().get(0).getDenominationCount(), 6);
        assertEquals(retrievedAmount.getCashResponse().get(1).getDenominationType(), "Twenty");
        assertEquals(retrievedAmount.getCashResponse().get(1).getDenominationCount(), 2);
        assertEquals(retrievedAmount.getCashResponse().get(2).getDenominationType(), "Ten");
        assertEquals(retrievedAmount.getCashResponse().get(2).getDenominationCount(), 2);
    }

    @Test
    void testUpdateDenomination() {
        when(cashManController.updateDenomination(getCashRequest())).thenReturn(getCashResponse());

        //test
        final GetCashResponse retrievedAmount = cashManRestController.updateDenomination(getCashRequest());

        //verify
        assertNotNull(retrievedAmount);
        assertNotNull(retrievedAmount.getCashResponse());
        assertEquals(retrievedAmount.getCashResponse().size(), 3);
        assertEquals(retrievedAmount.getCashResponse().get(0).getDenominationType(), "Fifty");
        assertEquals(retrievedAmount.getCashResponse().get(0).getDenominationCount(), 6);
        assertEquals(retrievedAmount.getCashResponse().get(1).getDenominationType(), "Twenty");
        assertEquals(retrievedAmount.getCashResponse().get(1).getDenominationCount(), 2);
        assertEquals(retrievedAmount.getCashResponse().get(2).getDenominationType(), "Ten");
        assertEquals(retrievedAmount.getCashResponse().get(2).getDenominationCount(), 2);
    }

    @Test
    void testUpdateDenominationWithNullRequestShouldThrowIllegalArgumentException() {
        //test
        assertThrows(IllegalArgumentException.class, () -> cashManRestController.updateDenomination(null));
    }

    @Test
    void testAddNewDenominationWithNullBodyShouldThrowException() {
        //test
        assertThrows(IllegalArgumentException.class, () -> cashManRestController.addNewDenomination(null));
    }

    @Test
    void testGetAvailableCash() {
        //setup
        when(cashManController.getAvailableCash()).thenReturn(BigInteger.valueOf(2800));

        //test
        final BigInteger cashWithdrawn = cashManRestController.getAvailableCash();

        //verify
        assertEquals(cashWithdrawn, BigInteger.valueOf(2800));
    }

    @Test
    void testGetAvailableDenominations() {
        //setup
        when(cashManController.getAvailableDenominations()).thenReturn(getDenominationResponse());

        //test
        final GetDenominationResponse response = cashManController.getAvailableDenominations();

        //verify
        assertNotNull(response);
        assertNotNull(response.getDenominationResponse());
        assertEquals(response.getDenominationResponse().size(), 3);
        assertEquals(response.getDenominationResponse().get(0).getDenominationType(), "Fifty");
        assertEquals(response.getDenominationResponse().get(0).getDenominationCount(), 6);
        assertEquals(response.getDenominationResponse().get(1).getDenominationType(), "Twenty");
        assertEquals(response.getDenominationResponse().get(1).getDenominationCount(), 2);
        assertEquals(response.getDenominationResponse().get(2).getDenominationType(), "Ten");
        assertEquals(response.getDenominationResponse().get(2).getDenominationCount(), 2);
    }
}

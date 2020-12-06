package sc.com.assessment.cashman.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static sc.com.assessment.cashman.util.TestUtility.getCashRequest;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import sc.com.assessment.cashman.CashManApplicationTests;
import sc.com.assessment.cashman.client.CashManClient;
import sc.com.assessment.cashman.dto.response.ErrorResponse;
import sc.com.assessment.cashman.dto.response.GetCashResponse;
import sc.com.assessment.cashman.dto.response.GetDenominationResponse;

@ActiveProfiles("TEST")
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = CashManApplicationTests.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class CashManRestControllerITest {

    @Autowired
    private CashManClient cashManClient;

    @Test
    public void givenAvailableCashTestWithDrawCash() throws Exception {
        //test
        final GetCashResponse response = cashManClient.withdrawCash(BigInteger.valueOf(300));

        //verify
        assertNotNull(response);
        assertNotNull(response.getCashResponse());
        assertEquals(response.getCashResponse().size(), 1);
        assertEquals(response.getCashResponse().get(0).getDenominationType(), "Ten");
        assertEquals(response.getCashResponse().get(0).getDenominationCount(), 30);
    }

    @Test
    public void givenCashTestUpdateDenomination() throws Exception {
        //test
        final GetCashResponse response = cashManClient.updateDenomination(getCashRequest());

        //verify
        assertNotNull(response);
        assertNotNull(response.getCashResponse());
        assertEquals(response.getCashResponse().size(), 3);
        assertEquals(response.getCashResponse().get(0).getDenominationType(), "Fifty");
        assertEquals(response.getCashResponse().get(0).getDenominationCount(), 36);
        assertEquals(response.getCashResponse().get(1).getDenominationType(), "Twenty");
        assertEquals(response.getCashResponse().get(1).getDenominationCount(), 42);
        assertEquals(response.getCashResponse().get(2).getDenominationType(), "Ten");
        assertEquals(response.getCashResponse().get(2).getDenominationCount(), 52);
    }

    @Test
    public void givenCashTestAddDenomination() {
        //test
        assertDoesNotThrow(() -> cashManClient.addDenomination(getCashRequest()));
    }

    @Test
    public void givenCashTestgetAvailableCash() throws Exception {
        //test
        final BigInteger availableCash = cashManClient.getAvailableCash();

        //verify
        assertEquals(availableCash, BigInteger.valueOf(2800));
    }

    @Test
    public void givenCashTestgetAvailableDenominations() throws Exception {
        //test
        final GetDenominationResponse availableDenominations = cashManClient.getAvailableDenominations();

        //verify
        assertNotNull(availableDenominations);
        assertNotNull(availableDenominations.getDenominationResponse());
        assertEquals(availableDenominations.getDenominationResponse().size(), 3);
        assertEquals(availableDenominations.getDenominationResponse().get(0).getDenominationType(), "Ten");
        assertEquals(availableDenominations.getDenominationResponse().get(0).getDenominationCount(), 50);
        assertEquals(availableDenominations.getDenominationResponse().get(1).getDenominationType(), "Twenty");
        assertEquals(availableDenominations.getDenominationResponse().get(1).getDenominationCount(), 40);
        assertEquals(availableDenominations.getDenominationResponse().get(2).getDenominationType(), "Fifty");
        assertEquals(availableDenominations.getDenominationResponse().get(2).getDenominationCount(), 30);

    }

    @Test
    public void givenInsufficientCashWithDrawCashShouldReturnError() throws Exception {
        //test
        final ErrorResponse errorResponse = cashManClient.withdrawOverdrawnAmount(BigInteger.valueOf(4000));

        //verify
        assertNotNull(errorResponse);
        assertEquals(errorResponse.getErrors().size(), 1);
        assertEquals(errorResponse.getErrors().get(0).getCode(), "error.client.insufficientFunds");
        assertEquals(errorResponse.getErrors().get(0).getTitle(), "Unprocessable Entity");
        assertEquals(errorResponse.getErrors().get(0).getDetail(),
                "Unable to dispense amount: 4000, maximum of 2800 can be withdrawn");
    }

    @Test
    public void givenUnserviceableAmountWithDrawCashShouldReturnError() throws Exception {
        //test
        final ErrorResponse errorResponse = cashManClient.withdrawOverdrawnAmount(BigInteger.valueOf(105));

        //verify
        assertNotNull(errorResponse);
        assertEquals(errorResponse.getErrors().size(), 1);
        assertEquals(errorResponse.getErrors().get(0).getCode(), "error.client.denominationsUnavailable");
        assertEquals(errorResponse.getErrors().get(0).getTitle(), "Unprocessable Entity");
        assertEquals(errorResponse.getErrors().get(0).getDetail(), "Unable to dispense, please use some other amount");
    }


    @Test
    public void givenAvailableCashTestTillCashEnds() throws Exception {
        //test
        final BigInteger initialCash = cashManClient.getAvailableCash();

        // withdraw till available < 100
        while (cashManClient.getAvailableCash().compareTo(BigInteger.valueOf(100)) > 0) {
            cashManClient.withdrawCash(BigInteger.valueOf(300));
        }
        // compare
        final BigInteger cashAfterWithdrawl = cashManClient.getAvailableCash();
        assertEquals(cashAfterWithdrawl, BigInteger.valueOf(100));
        assertTrue(initialCash.compareTo(cashAfterWithdrawl) > 0);

        //update
        cashManClient.updateDenomination(getCashRequest());

        //addMore
        cashManClient.addDenomination(getCashRequest());

        //Check Inventory
        final BigInteger remainingCash = cashManClient.getAvailableCash();

        assertEquals(remainingCash, BigInteger.valueOf(820));
        assertTrue(cashAfterWithdrawl.compareTo(remainingCash) < 0);

    }
}



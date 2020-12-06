package sc.com.assessment.cashman.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static sc.com.assessment.cashman.util.TestUtility.getCash;
import static sc.com.assessment.cashman.util.TestUtility.getCashRequest;
import static sc.com.assessment.cashman.util.TestUtility.getCashResponseDtos;
import static sc.com.assessment.cashman.util.TestUtility.getDenominations;
import static sc.com.assessment.cashman.util.TestUtility.getDenominationResponseDtos;

import java.math.BigInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sc.com.assessment.cashman.dto.mapper.CashManMapper;
import sc.com.assessment.cashman.dto.response.GetCashResponse;
import sc.com.assessment.cashman.dto.response.GetDenominationResponse;
import sc.com.assessment.cashman.service.CashManService;

@ExtendWith(MockitoExtension.class)
class CashManControllerTest {

    @Mock
    private CashManService cashManService;

    @Mock
    private CashManMapper cashManMapper;

    private CashManController cashManController;

    @BeforeEach
    void setUp() {
        cashManController = new CashManController(cashManService, cashManMapper);
    }

    @Test
    void testWithdrawCash() {
        //setup
        when(cashManService.withdrawCash(BigInteger.valueOf(360))).thenReturn(getDenominations());
        when(cashManMapper.fromModelsToCashResponse(getDenominations())).thenReturn(getCashResponseDtos());

        //test
        final GetCashResponse response = cashManController.withdrawCash(BigInteger.valueOf(360));

        //verify
        assertNotNull(response);
        assertNotNull(response.getCashResponse());
        assertEquals(response.getCashResponse().size(), 3);
        assertEquals(response.getCashResponse().get(0).getDenominationType(), "Fifty");
        assertEquals(response.getCashResponse().get(0).getDenominationCount(), 6);
        assertEquals(response.getCashResponse().get(1).getDenominationType(), "Twenty");
        assertEquals(response.getCashResponse().get(1).getDenominationCount(), 2);
        assertEquals(response.getCashResponse().get(2).getDenominationType(), "Ten");
        assertEquals(response.getCashResponse().get(2).getDenominationCount(), 2);

    }

    @Test
    void testUpdateDenomination() {
        //setup
        when(cashManService.updateDenomination(getDenominations())).thenReturn(getDenominations());
        when(cashManMapper.fromDtosToModels(getCash())).thenReturn(getDenominations());
        when(cashManMapper.fromModelsToCashResponse(getDenominations())).thenReturn(getCashResponseDtos());

        //test
        final GetCashResponse response = cashManController.updateDenomination(getCashRequest());

        //verify
        assertNotNull(response);
        assertNotNull(response.getCashResponse());
        assertEquals(response.getCashResponse().size(), 3);
        assertEquals(response.getCashResponse().get(0).getDenominationType(), "Fifty");
        assertEquals(response.getCashResponse().get(0).getDenominationCount(), 6);
        assertEquals(response.getCashResponse().get(1).getDenominationType(), "Twenty");
        assertEquals(response.getCashResponse().get(1).getDenominationCount(), 2);
        assertEquals(response.getCashResponse().get(2).getDenominationType(), "Ten");
        assertEquals(response.getCashResponse().get(2).getDenominationCount(), 2);
    }

    @Test
    void testGetAvailableCash() {
        //setup
        when(cashManService.getAvailableCash()).thenReturn(BigInteger.valueOf(2800));

        //test
        final BigInteger cashWithdrawn = cashManController.getAvailableCash();

        //verify
        assertEquals(cashWithdrawn, BigInteger.valueOf(2800));
    }

    @Test
    void testGetAvailableDenominations() {
        //setup
        when(cashManService.getAvailableDenominations()).thenReturn(getDenominations());
        when(cashManMapper.fromModelsToDenominationResponses(getDenominations()))
                .thenReturn(getDenominationResponseDtos());
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

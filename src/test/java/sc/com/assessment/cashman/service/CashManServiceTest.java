package sc.com.assessment.cashman.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static sc.com.assessment.cashman.util.TestUtility.getDenominationEntities;
import static sc.com.assessment.cashman.util.TestUtility.getDenominations;

import java.math.BigInteger;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sc.com.assessment.cashman.dao.CashManDao;
import sc.com.assessment.cashman.dto.mapper.CashManMapper;
import sc.com.assessment.cashman.exception.DenomationsNotAvailableException;
import sc.com.assessment.cashman.exception.InsufficientCashException;
import sc.com.assessment.cashman.model.Denomination;

@ExtendWith(MockitoExtension.class)
class CashManServiceTest {

    @Mock
    private CashManDao cashManDao;

    @Mock
    private CashManMapper cashManMapper;

    private CashManService cashManService;

    @BeforeEach
    void setUp() {
        cashManService = new CashManService(cashManDao, cashManMapper);
    }

    @Test
    public void givenAvailableCashAndDenominationsWithdrawCashShouldReturnCash() {
        //setup
        when(cashManDao.isRequestedCashAvailable(BigInteger.valueOf(240))).thenReturn(true);
        when(cashManDao.getAvailableDenominations(BigInteger.valueOf(240))).thenReturn(getDenominationEntities());

        // test
        final List<Denomination> dispensedDenominations = cashManService.withdrawCash(BigInteger.valueOf(240));

        //verify
        assertNotNull(dispensedDenominations);
        assertEquals(dispensedDenominations.size(), 2);
        assertEquals(dispensedDenominations.get(0).getDenominationValue(), BigInteger.valueOf(20));
        assertEquals(dispensedDenominations.get(0).getDenominationCount(), 2);
        assertEquals(dispensedDenominations.get(1).getDenominationValue(), BigInteger.valueOf(50));
        assertEquals(dispensedDenominations.get(1).getDenominationCount(), 4);
    }

    @Test
    public void givenAvailableDenominationsTestMultipleTransactions()  {
        //setup
        when(cashManDao.isRequestedCashAvailable(BigInteger.valueOf(240))).thenReturn(true);
        when(cashManDao.isRequestedCashAvailable(BigInteger.valueOf(140))).thenReturn(true);
        when(cashManDao.isRequestedCashAvailable(BigInteger.valueOf(130))).thenReturn(true);
        when(cashManDao.isRequestedCashAvailable(BigInteger.valueOf(120))).thenReturn(true);
        when(cashManDao.isRequestedCashAvailable(BigInteger.valueOf(110))).thenReturn(true);
        when(cashManDao.isRequestedCashAvailable(BigInteger.valueOf(60))).thenReturn(true);
        when(cashManDao.isRequestedCashAvailable(BigInteger.valueOf(30))).thenReturn(true);
        when(cashManDao.isRequestedCashAvailable(BigInteger.valueOf(10))).thenReturn(true);

        when(cashManDao.getAvailableDenominations(BigInteger.valueOf(240))).thenReturn(getDenominationEntities());
        when(cashManDao.getAvailableDenominations(BigInteger.valueOf(140))).thenReturn(getDenominationEntities());
        when(cashManDao.getAvailableDenominations(BigInteger.valueOf(130))).thenReturn(getDenominationEntities());
        when(cashManDao.getAvailableDenominations(BigInteger.valueOf(120))).thenReturn(getDenominationEntities());
        when(cashManDao.getAvailableDenominations(BigInteger.valueOf(110))).thenReturn(getDenominationEntities());
        when(cashManDao.getAvailableDenominations(BigInteger.valueOf(60))).thenReturn(getDenominationEntities());
        when(cashManDao.getAvailableDenominations(BigInteger.valueOf(30))).thenReturn(getDenominationEntities());
        when(cashManDao.getAvailableDenominations(BigInteger.valueOf(10))).thenReturn(getDenominationEntities());

        // test
        final List<Denomination> txn1 = cashManService.withdrawCash(BigInteger.valueOf(240));
        final List<Denomination> txn2 = cashManService.withdrawCash(BigInteger.valueOf(140));
        final List<Denomination> txn3 = cashManService.withdrawCash(BigInteger.valueOf(130));
        final List<Denomination> txn4 = cashManService.withdrawCash(BigInteger.valueOf(120));
        final List<Denomination> txn5 = cashManService.withdrawCash(BigInteger.valueOf(110));
        final List<Denomination> txn6 = cashManService.withdrawCash(BigInteger.valueOf(60));
        final List<Denomination> txn7 = cashManService.withdrawCash(BigInteger.valueOf(30));
        final List<Denomination> txn8 = cashManService.withdrawCash(BigInteger.valueOf(10));

        //verify
        assertNotNull(txn1);
        assertNotNull(txn2);
        assertNotNull(txn3);
        assertNotNull(txn4);
        assertNotNull(txn5);
        assertNotNull(txn6);
        assertNotNull(txn7);
        assertNotNull(txn8);

        assertEquals(txn1.size(), 2);
        assertEquals(txn1.get(0).getDenominationValue(), BigInteger.valueOf(20));
        assertEquals(txn1.get(0).getDenominationCount(), 2);
        assertEquals(txn1.get(1).getDenominationValue(), BigInteger.valueOf(50));
        assertEquals(txn1.get(1).getDenominationCount(), 4);
        assertNotNull(txn2);
        assertEquals(txn2.size(), 2);
        assertEquals(txn2.get(0).getDenominationValue(), BigInteger.valueOf(50));
        assertEquals(txn2.get(0).getDenominationCount(), 2);
        assertEquals(txn2.get(1).getDenominationValue(), BigInteger.valueOf(20));
        assertEquals(txn2.get(1).getDenominationCount(), 2);
        assertNotNull(txn3);
        assertEquals(txn3.size(), 3);
        assertEquals(txn3.get(0).getDenominationValue(), BigInteger.valueOf(50));
        assertEquals(txn3.get(0).getDenominationCount(), 2);
        assertEquals(txn3.get(1).getDenominationValue(), BigInteger.valueOf(20));
        assertEquals(txn3.get(1).getDenominationCount(), 1);
        assertEquals(txn3.get(2).getDenominationValue(), BigInteger.valueOf(10));
        assertEquals(txn3.get(2).getDenominationCount(), 1);
        assertNotNull(txn4);
        assertEquals(txn4.size(), 2);
        assertEquals(txn4.get(0).getDenominationValue(), BigInteger.valueOf(50));
        assertEquals(txn4.get(0).getDenominationCount(), 2);
        assertEquals(txn4.get(1).getDenominationValue(), BigInteger.valueOf(20));
        assertEquals(txn4.get(1).getDenominationCount(), 1);
        assertNotNull(txn5);
        assertEquals(txn5.size(), 2);
        assertEquals(txn5.get(0).getDenominationValue(), BigInteger.valueOf(50));
        assertEquals(txn5.get(0).getDenominationCount(), 2);
        assertEquals(txn5.get(1).getDenominationValue(), BigInteger.valueOf(10));
        assertEquals(txn5.get(1).getDenominationCount(), 1);
        assertNotNull(txn6);
        assertEquals(txn6.size(), 2);
        assertEquals(txn6.get(0).getDenominationValue(), BigInteger.valueOf(50));
        assertEquals(txn6.get(0).getDenominationCount(), 1);
        assertEquals(txn6.get(1).getDenominationValue(), BigInteger.valueOf(10));
        assertEquals(txn6.get(1).getDenominationCount(), 1);
        assertNotNull(txn7);
        assertEquals(txn7.size(), 2);
        assertEquals(txn7.get(0).getDenominationValue(), BigInteger.valueOf(20));
        assertEquals(txn7.get(0).getDenominationCount(), 1);
        assertEquals(txn7.get(1).getDenominationValue(), BigInteger.valueOf(10));
        assertEquals(txn7.get(1).getDenominationCount(), 1);
        assertNotNull(txn8);
        assertEquals(txn8.size(), 1);
        assertEquals(txn8.get(0).getDenominationValue(), BigInteger.valueOf(10));
        assertEquals(txn8.get(0).getDenominationCount(), 1);

    }

    @Test
    public void givenInsufficientMoneyDispenseCashShouldReturnInsufficentCashException() {
        //setup
        when(cashManDao.isRequestedCashAvailable(BigInteger.valueOf(240))).thenReturn(false);

        //verify
        assertThrows(InsufficientCashException.class, () -> {
            cashManService.withdrawCash(BigInteger.valueOf(240));
        });

    }

    @Test
    public void givenInsufficientDenominationsDispenseCashShouldReturnDenominationsNotavailableException() {
        //setup
        when(cashManDao.isRequestedCashAvailable(BigInteger.valueOf(85))).thenReturn(true);
        when(cashManDao.getAvailableDenominations(BigInteger.valueOf(85))).thenReturn(getDenominationEntities());

        //verify
        assertThrows(DenomationsNotAvailableException.class, () -> {
            cashManService.withdrawCash(BigInteger.valueOf(85));
        });

    }

    @Test
    public void givenDenominationsUpdateDenominationShouldCallUpdateDenominations() {
        //setup
        when(cashManDao.updateDenomination(getDenominations())).thenReturn(getDenominationEntities());

        //test
        cashManService.updateDenomination(getDenominations());

        //verify
        verify(cashManDao, times(1)).updateDenomination(getDenominations());
    }

}

package sc.com.assessment.cashman.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static sc.com.assessment.cashman.util.TestUtility.getDenominationEntities;

import java.math.BigInteger;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sc.com.assessment.cashman.dto.mapper.CashManMapper;
import sc.com.assessment.cashman.entity.DenominationEntity;
import sc.com.assessment.cashman.repository.CashManRepository;

@ExtendWith(MockitoExtension.class)
class GetCashResponseManDaoTest {

    @Mock
    private CashManRepository cashManRepository;

    @Mock
    private CashManMapper cashManMapper;

    private CashManDao cashManDao;

    @BeforeEach
    void setUp() {
        cashManDao = new CashManDao(cashManRepository, cashManMapper);
    }

    @Test
    void testIsRequestedCashAvailable() {
        //setup
        when(cashManRepository.getAvailableCash()).thenReturn(BigInteger.valueOf(500));

        //test
        final boolean isRequestedCashAvailable = cashManDao.isRequestedCashAvailable(BigInteger.valueOf(300));

        //verify
        assertTrue(isRequestedCashAvailable);

    }

    @Test
    void testGetAvailableDenominations() {
        //setup
        when(cashManRepository.retrieveAvailableDenominations(BigInteger.valueOf(100)))
                .thenReturn(getDenominationEntities());

        //test
        final List<DenominationEntity> denominations = cashManDao.getAvailableDenominations(BigInteger.valueOf(100));

        //verify
        assertNotNull(denominations);
        assertEquals(denominations.size(), 3);
        assertEquals(denominations.get(0).getDenominationType(), "Fifty");
        assertEquals(denominations.get(0).getDenominationCount(), 6);
        assertEquals(denominations.get(1).getDenominationType(), "Twenty");
        assertEquals(denominations.get(1).getDenominationCount(), 2);
        assertEquals(denominations.get(2).getDenominationType(), "Ten");
        assertEquals(denominations.get(2).getDenominationCount(), 2);
    }

    @Test
    void testGetAvailableCash() {
        //setup
        when(cashManRepository.getAvailableCash()).thenReturn(BigInteger.valueOf(360));

        //test
        final BigInteger totalFunds = cashManDao.getAvailableCash();

        //verify
        assertNotNull(totalFunds);
        assertEquals(totalFunds, BigInteger.valueOf(360));

    }

    @Test
    void testGetTotalAvailableDenominations() {
        //setup
        when(cashManRepository.findAll()).thenReturn(getDenominationEntities());

        //test
        final List<DenominationEntity> denominations = cashManDao.getAvailableDenominations();

        //verify
        assertNotNull(denominations);
        assertEquals(denominations.size(), 3);
        assertEquals(denominations.get(0).getDenominationType(), "Fifty");
        assertEquals(denominations.get(0).getDenominationCount(), 6);
        assertEquals(denominations.get(1).getDenominationType(), "Twenty");
        assertEquals(denominations.get(1).getDenominationCount(), 2);
        assertEquals(denominations.get(2).getDenominationType(), "Ten");
        assertEquals(denominations.get(2).getDenominationCount(), 2);
    }
}

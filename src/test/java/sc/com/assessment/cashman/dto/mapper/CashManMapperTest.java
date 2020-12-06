package sc.com.assessment.cashman.dto.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static sc.com.assessment.cashman.util.TestUtility.DENOMINATION_TYPE_FIFTY;
import static sc.com.assessment.cashman.util.TestUtility.DENOMINATION_TYPE_TWENTY;
import static sc.com.assessment.cashman.util.TestUtility.DENOMINATION_TYPE_TEN;
import static sc.com.assessment.cashman.util.TestUtility.getCash;
import static sc.com.assessment.cashman.util.TestUtility.getDenominationEntities;
import static sc.com.assessment.cashman.util.TestUtility.getDenominations;

import java.math.BigInteger;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import sc.com.assessment.cashman.dto.response.CashResponse;
import sc.com.assessment.cashman.dto.response.DenominationResponse;
import sc.com.assessment.cashman.entity.DenominationEntity;
import sc.com.assessment.cashman.model.Denomination;
import sc.com.assessment.cashman.model.ImmutableDenomination;

@ExtendWith(MockitoExtension.class)
class CashManMapperTest {

    private CashManMapperImpl cashManMapper;

    @BeforeEach
    void setUp() {
        cashManMapper = new CashManMapperImpl();
    }

    @Test
    void testFromDtosToModels() {
        //test
        final List<Denomination> denominations = cashManMapper.fromDtosToModels(getCash());

        //verify
        assertNotNull(denominations);
        assertEquals(denominations.size(), 3);
        assertEquals(denominations.get(0).getDenominationType(), DENOMINATION_TYPE_FIFTY);
        assertEquals(denominations.get(0).getDenominationCount(), 6);
        assertEquals(denominations.get(1).getDenominationType(), DENOMINATION_TYPE_TWENTY);
        assertEquals(denominations.get(1).getDenominationCount(), 2);
        assertEquals(denominations.get(2).getDenominationType(), DENOMINATION_TYPE_TEN);
        assertEquals(denominations.get(2).getDenominationCount(), 2);
    }

    @Test
    void testFromModelToEntity() {
        //setup
        final Denomination denomination = ImmutableDenomination
                .builder().denominationCount(10)
                .denominationType(DENOMINATION_TYPE_FIFTY).denominationValue(BigInteger.valueOf(50))
                .build();

        //test
        final DenominationEntity denominationEntity = cashManMapper.fromModelToEntity(denomination);

        //verify
        assertNotNull(denominationEntity);
        assertEquals(denominationEntity.getDenominationType(), DENOMINATION_TYPE_FIFTY);
        assertEquals(denominationEntity.getDenominationCount(), 10);
        assertEquals(denominationEntity.getDenominationValue(), BigInteger.valueOf(50));
    }

    @Test
    void testFromEntityToModel() {
        //setup
        final DenominationEntity denominationEntity = new DenominationEntity();
        denominationEntity.setDenominationCount(10);
        denominationEntity.setDenominationType(DENOMINATION_TYPE_FIFTY);
        denominationEntity.setDenominationValue(BigInteger.valueOf(50));

        //test
        final Denomination denomination = cashManMapper.fromEntityToModel(denominationEntity);

        //verify
        assertNotNull(denomination);
        assertEquals(denomination.getDenominationType(), DENOMINATION_TYPE_FIFTY);
        assertEquals(denomination.getDenominationCount(), 10);
        assertEquals(denomination.getDenominationValue(), BigInteger.valueOf(50));
    }

    @Test
    void testFromEntitiesToModels() {
        //test
        final List<Denomination> denominations = cashManMapper.fromEntitiesToModels(getDenominationEntities());

        //verify
        assertNotNull(denominations);
        assertEquals(denominations.size(), 3);
        assertEquals(denominations.get(0).getDenominationType(), DENOMINATION_TYPE_FIFTY);
        assertEquals(denominations.get(0).getDenominationCount(), 6);
        assertEquals(denominations.get(1).getDenominationType(), DENOMINATION_TYPE_TWENTY);
        assertEquals(denominations.get(1).getDenominationCount(), 2);
        assertEquals(denominations.get(2).getDenominationType(), DENOMINATION_TYPE_TEN);
        assertEquals(denominations.get(2).getDenominationCount(), 2);

    }

    @Test
    void testFromModelsToDenominationResponses() {
        //test
        final List<DenominationResponse> response = cashManMapper.fromModelsToDenominationResponses(getDenominations());

        //verify
        assertNotNull(response);
        assertEquals(response.size(), 3);
        assertEquals(response.get(0).getDenominationType(), DENOMINATION_TYPE_FIFTY);
        assertEquals(response.get(0).getDenominationCount(), 6);
        assertEquals(response.get(1).getDenominationType(), DENOMINATION_TYPE_TWENTY);
        assertEquals(response.get(1).getDenominationCount(), 2);
        assertEquals(response.get(2).getDenominationType(), DENOMINATION_TYPE_TEN);
        assertEquals(response.get(2).getDenominationCount(), 2);
    }

    @Test
    void testFromModelsToCashResponse() {
        //test
        final List<CashResponse> response = cashManMapper.fromModelsToCashResponse(getDenominations());

        //verify
        assertNotNull(response);
        assertEquals(response.size(), 3);
        assertEquals(response.get(0).getDenominationType(), DENOMINATION_TYPE_FIFTY);
        assertEquals(response.get(0).getDenominationCount(), 6);
        assertEquals(response.get(1).getDenominationType(), DENOMINATION_TYPE_TWENTY);
        assertEquals(response.get(1).getDenominationCount(), 2);
        assertEquals(response.get(2).getDenominationType(), DENOMINATION_TYPE_TEN);
        assertEquals(response.get(2).getDenominationCount(), 2);
    }
}

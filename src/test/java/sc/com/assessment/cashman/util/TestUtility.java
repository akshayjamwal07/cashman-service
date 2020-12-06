package sc.com.assessment.cashman.util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import sc.com.assessment.cashman.dto.request.Cash;
import sc.com.assessment.cashman.dto.request.CashRequest;
import sc.com.assessment.cashman.dto.request.ImmutableCash;
import sc.com.assessment.cashman.dto.request.ImmutableCashRequest;
import sc.com.assessment.cashman.dto.response.CashResponse;
import sc.com.assessment.cashman.dto.response.DenominationResponse;
import sc.com.assessment.cashman.dto.response.GetCashResponse;
import sc.com.assessment.cashman.dto.response.GetDenominationResponse;
import sc.com.assessment.cashman.dto.response.ImmutableCashResponse;
import sc.com.assessment.cashman.dto.response.ImmutableDenominationResponse;
import sc.com.assessment.cashman.dto.response.ImmutableGetCashResponse;
import sc.com.assessment.cashman.dto.response.ImmutableGetDenominationResponse;
import sc.com.assessment.cashman.entity.DenominationEntity;
import sc.com.assessment.cashman.model.Denomination;
import sc.com.assessment.cashman.model.ImmutableDenomination;

public final class TestUtility {

    public static final String DENOMINATION_TYPE_FIFTY = "Fifty";
    public static final String DENOMINATION_TYPE_TWENTY = "Twenty";
    public static final String DENOMINATION_TYPE_TEN = "Ten";

    private TestUtility() {
    }

    public static List<DenominationEntity> getDenominationEntities() {
        final DenominationEntity denominationEntity1 = new DenominationEntity();
        denominationEntity1.setDenominationCount(6);
        denominationEntity1.setDenominationValue(BigInteger.valueOf(50));
        denominationEntity1.setDenominationType(DENOMINATION_TYPE_FIFTY);

        final DenominationEntity denominationEntity2 = new DenominationEntity();
        denominationEntity2.setDenominationCount(2);
        denominationEntity2.setDenominationValue(BigInteger.valueOf(20));
        denominationEntity2.setDenominationType(DENOMINATION_TYPE_TWENTY);

        final DenominationEntity denominationEntity3 = new DenominationEntity();
        denominationEntity3.setDenominationCount(2);
        denominationEntity3.setDenominationValue(BigInteger.valueOf(10));
        denominationEntity3.setDenominationType(DENOMINATION_TYPE_TEN);

        final List<DenominationEntity> denominationEntities = new ArrayList<>();
        denominationEntities.add(denominationEntity1);
        denominationEntities.add(denominationEntity2);
        denominationEntities.add(denominationEntity3);

        return denominationEntities;
    }

    public static List<Denomination> getDenominations() {
        final Denomination denomination1 = ImmutableDenomination.builder()
                .denominationType(DENOMINATION_TYPE_FIFTY)
                .denominationValue(BigInteger.valueOf(50))
                .denominationCount(6)
                .build();

        final Denomination denomination2 = ImmutableDenomination.builder()
                .denominationType(DENOMINATION_TYPE_TWENTY)
                .denominationValue(BigInteger.valueOf(20))
                .denominationCount(2)
                .build();

        final Denomination denomination3 = ImmutableDenomination.builder()
                .denominationType(DENOMINATION_TYPE_TEN)
                .denominationValue(BigInteger.valueOf(10))
                .denominationCount(2)
                .build();

        final List<Denomination> denominations = new ArrayList<>();
        denominations.add(denomination1);
        denominations.add(denomination2);
        denominations.add(denomination3);

        return denominations;
    }

    public static List<Cash> getCash() {
        final Cash cash1 = ImmutableCash.builder()
                .denominationType(DENOMINATION_TYPE_FIFTY)
                .denominationValue(BigInteger.valueOf(50))
                .denominationCount(6)
                .build();

        final Cash cash2 = ImmutableCash.builder()
                .denominationType(DENOMINATION_TYPE_TWENTY)
                .denominationValue(BigInteger.valueOf(20))
                .denominationCount(2)
                .build();

        final Cash cash3 = ImmutableCash.builder()
                .denominationType(DENOMINATION_TYPE_TEN)
                .denominationValue(BigInteger.valueOf(10))
                .denominationCount(2)
                .build();

        final List<Cash> cash = new ArrayList<>();
        cash.add(cash1);
        cash.add(cash2);
        cash.add(cash3);

        return cash;
    }

    public static CashRequest getCashRequest() {
        return ImmutableCashRequest.builder()
                .cash(new ArrayList<>(getCash()))
                .build();
    }

    public static GetCashResponse getCashResponse() {


        return ImmutableGetCashResponse.builder()
                .cashResponse(getCashResponseDtos())
                .build();

    }

    public static List<CashResponse> getCashResponseDtos() {
        final CashResponse cash1 = ImmutableCashResponse.builder()
                .denominationType(DENOMINATION_TYPE_FIFTY)
                .denominationValue(BigInteger.valueOf(50))
                .denominationCount(6)
                .build();

        final CashResponse cash2 = ImmutableCashResponse.builder()
                .denominationType(DENOMINATION_TYPE_TWENTY)
                .denominationValue(BigInteger.valueOf(20))
                .denominationCount(2)
                .build();

        final CashResponse cash3 = ImmutableCashResponse.builder()
                .denominationType(DENOMINATION_TYPE_TEN)
                .denominationValue(BigInteger.valueOf(10))
                .denominationCount(2)
                .build();

        final List<CashResponse> cash = new ArrayList<>();
        cash.add(cash1);
        cash.add(cash2);
        cash.add(cash3);

        return cash;
    }


    public static GetDenominationResponse getDenominationResponse() {
        return ImmutableGetDenominationResponse.builder()
                .denominationResponse(getDenominationResponseDtos())
                .build();

    }

    public static List<DenominationResponse> getDenominationResponseDtos() {

        final DenominationResponse denomination1 = ImmutableDenominationResponse.builder()
                .denominationType(DENOMINATION_TYPE_FIFTY)
                .denominationCount(6)
                .build();

        final DenominationResponse denomination2 = ImmutableDenominationResponse.builder()
                .denominationType(DENOMINATION_TYPE_TWENTY)
                .denominationCount(2)
                .build();

        final DenominationResponse denomination3 = ImmutableDenominationResponse.builder()
                .denominationType(DENOMINATION_TYPE_TEN)
                .denominationCount(2)
                .build();

        final List<DenominationResponse> denominations = new ArrayList<>();
        denominations.add(denomination1);
        denominations.add(denomination2);
        denominations.add(denomination3);

        return denominations;
    }
}

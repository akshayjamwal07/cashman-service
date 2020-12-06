package sc.com.assessment.cashman.controller;

import static org.slf4j.LoggerFactory.getLogger;

import java.math.BigInteger;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import sc.com.assessment.cashman.dto.request.Cash;
import sc.com.assessment.cashman.dto.request.CashRequest;
import sc.com.assessment.cashman.dto.response.GetCashResponse;
import sc.com.assessment.cashman.dto.response.GetDenominationResponse;
import sc.com.assessment.cashman.dto.mapper.CashManMapper;
import sc.com.assessment.cashman.dto.response.ImmutableGetCashResponse;
import sc.com.assessment.cashman.dto.response.ImmutableGetDenominationResponse;
import sc.com.assessment.cashman.service.CashManService;

@Controller
public class CashManController {

    private static final Logger LOG = getLogger(CashManController.class);

    private final CashManService cashManService;

    private final CashManMapper cashManMapper;

    public CashManController(final CashManService cashManService, final CashManMapper cashManMapper) {
        this.cashManService = cashManService;
        this.cashManMapper = cashManMapper;
    }

    public GetCashResponse withdrawCash(final BigInteger cashAmount) {
        return ImmutableGetCashResponse.builder()
                .cashResponse(cashManMapper.fromModelsToCashResponse(cashManService.withdrawCash(cashAmount)))
                .build();
    }

    public GetCashResponse updateDenomination(final CashRequest cashRequest) {
        final List<Cash> cash = cashRequest.getCash();
        Assert.notEmpty(cash, "Cash should not be empty");

        LOG.info("Recieved a request to update {} denominations", cash.size());
        return ImmutableGetCashResponse.builder().cashResponse(cashManMapper.fromModelsToCashResponse(
                cashManService.updateDenomination(cashManMapper.fromDtosToModels(cash)))
        ).build();
    }

    public void addNewDenomination(final CashRequest cashRequest) {
        final List<Cash> cash = cashRequest.getCash();
        Assert.notEmpty(cash, "Cash should not be empty");

        cashManService.addNewDenomination(cashManMapper.fromDtosToModels(cash));
    }

    public BigInteger getAvailableCash() {
        return cashManService.getAvailableCash();
    }

    public GetDenominationResponse getAvailableDenominations() {
        return ImmutableGetDenominationResponse.builder()
                .denominationResponse(cashManMapper.fromModelsToDenominationResponses(
                        cashManService.getAvailableDenominations())
                ).build();
    }
}

package sc.com.assessment.cashman.restcontroller;

import static org.slf4j.LoggerFactory.getLogger;

import java.math.BigInteger;

import org.slf4j.Logger;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RestController;
import sc.com.assessment.cashman.api.CashManRestApi;
import sc.com.assessment.cashman.controller.CashManController;
import sc.com.assessment.cashman.dto.request.CashRequest;
import sc.com.assessment.cashman.dto.response.GetCashResponse;
import sc.com.assessment.cashman.dto.response.GetDenominationResponse;

@RestController
public class CashManRestController implements CashManRestApi {

    private static final Logger LOG = getLogger(CashManRestController.class);

    private final CashManController cashManController;

    public CashManRestController(final CashManController cashManController) {
        this.cashManController = cashManController;
    }

    @Override
    public GetCashResponse withdrawCash(final BigInteger cashAmount) {
        LOG.info("Recieved a request to retrieve cash {}", cashAmount);
        return cashManController.withdrawCash(cashAmount);

    }

    @Override
    public GetCashResponse updateDenomination(final CashRequest cashRequest) {
        Assert.notNull(cashRequest, "CashRequest should not be null");

        LOG.info("Recieved a request to update denominations : {}", cashRequest);
        return cashManController.updateDenomination(cashRequest);
    }

    @Override
    public void addNewDenomination(final CashRequest cashRequest) {
        Assert.notNull(cashRequest, "CashRequest should not be null");

        LOG.info("Recieved a request to add new denominations: {}", cashRequest);
        cashManController.addNewDenomination(cashRequest);
    }

    @Override
    public BigInteger getAvailableCash() {
        return cashManController.getAvailableCash();
    }

    @Override
    public GetDenominationResponse getAvailableDenominations() {
        return cashManController.getAvailableDenominations();
    }
}

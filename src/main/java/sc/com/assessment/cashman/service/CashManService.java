package sc.com.assessment.cashman.service;

import static org.slf4j.LoggerFactory.getLogger;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import sc.com.assessment.cashman.dao.CashManDao;
import sc.com.assessment.cashman.dto.mapper.CashManMapper;
import sc.com.assessment.cashman.entity.DenominationEntity;
import sc.com.assessment.cashman.exception.CashManException;
import sc.com.assessment.cashman.exception.DenomationsNotAvailableException;
import sc.com.assessment.cashman.exception.InsufficientCashException;
import sc.com.assessment.cashman.model.Denomination;
import sc.com.assessment.cashman.model.ImmutableDenomination;

@Service
public class CashManService {

    private static final Logger LOG = getLogger(CashManService.class);

    private final CashManDao cashManDao;

    private final CashManMapper cashManMapper;

    @Value("${cashman-util.inventory-scan.denomination-threshold}")
    private int denominationThreshold;

    public CashManService(final CashManDao cashManDao, final CashManMapper cashManMapper) {
        this.cashManDao = cashManDao;
        this.cashManMapper = cashManMapper;
    }

    @Transactional
    public List<Denomination> withdrawCash(final BigInteger cashAmount) {
        // check if cash is available in cashman
        if (cashManDao.isRequestedCashAvailable(cashAmount)) {
            // fetch available denominations
            final List<DenominationEntity> availableDenominations = retrieveAvailableDenominations(cashAmount);

            // fetch denominations dispensed
            final Set<DenominationEntity> denominationDispensed = getDenominationsDispensed(cashAmount,
                    getClone(availableDenominations), new ArrayList<>());

            // if denominations available then proceed, else throw exception
            if (CollectionUtils.isEmpty(denominationDispensed)) {
                LOG.error("Unable to dispense the amount {} due to limited denominations {}",
                        cashAmount, availableDenominations);
                throw new DenomationsNotAvailableException("Unable to dispense, please use some other amount");
            }
            // update cash inventory before dispensing
            cashManDao.syncCashInventory(getClone(availableDenominations), denominationDispensed);

            return denominationDispensed.stream()
                    .map(denominationEntity ->
                            ImmutableDenomination.builder()
                                    .denominationType(denominationEntity.getDenominationType())
                                    .denominationValue(denominationEntity.getDenominationValue())
                                    .denominationCount(denominationEntity.getDenominationCount())
                                    .build()
                    ).collect(Collectors.toList());
        } else {
            LOG.error("Unable to dispense amount {}, maximum of {} can be withdrwan", cashAmount, getAvailableCash());
            throw new InsufficientCashException(
                    String.format("Unable to dispense amount: %s, maximum of %s can be withdrawn",
                            cashAmount, getAvailableCash()));
        }
    }

    private List<DenominationEntity> retrieveAvailableDenominations(final BigInteger cashAmount) {
        return cashManDao.getAvailableDenominations(cashAmount);
    }

    private List<DenominationEntity> getClone(final List<DenominationEntity> availableDenominations) {
        final List<DenominationEntity> denominationsClone = new ArrayList<>();
        for (final DenominationEntity denominationEntity : availableDenominations) {
            DenominationEntity copyOfDenomination;
            try {
                copyOfDenomination = denominationEntity.clone();
            } catch (CloneNotSupportedException exception) {
                throw new CashManException("Unable to clone the denomination", exception);
            }
            denominationsClone.add(copyOfDenomination);
        }
        return denominationsClone;
    }

    private Set<DenominationEntity> getDenominationsDispensed(final BigInteger cashAmount,
                                                              final List<DenominationEntity> availableDenominations,
                                                              final List<DenominationEntity> denominationOptions) {

        BigInteger target = BigInteger.ZERO;
        Set<DenominationEntity> denominationDispensed = new HashSet<>();
        for (final DenominationEntity denominationEntity : denominationOptions) {
            for (int k = 0; k < denominationEntity.getDenominationCount(); k++) {
                target = target.add(denominationEntity.getDenominationValue());
                denominationDispensed.add(denominationEntity);
                if (target.compareTo(cashAmount) > 0) {
                    denominationEntity.setDenominationCount(k);
                    break;
                }
                if (target.compareTo(cashAmount) == 0) {
                    denominationEntity.setDenominationCount(k + 1);
                    break;
                }
            }
        }

        if (target.compareTo(cashAmount) != 0) {
            denominationDispensed.clear();
        }

        for (int i = 0; i < availableDenominations.size(); i++) {
            final List<DenominationEntity> partialList = new ArrayList(denominationOptions);
            partialList.add(availableDenominations.get(i));

            final List<DenominationEntity> remaining = new ArrayList<>();
            for (int j = i + 1; j < availableDenominations.size(); j++) {
                remaining.add(availableDenominations.get(j));
            }
            if (CollectionUtils.isEmpty(denominationDispensed)) {
                denominationDispensed = getDenominationsDispensed(cashAmount, remaining, partialList);
            }
        }
        return denominationDispensed;
    }

    @Transactional
    public List<Denomination> updateDenomination(final List<Denomination> denominations) {
        return cashManMapper.fromEntitiesToModels(cashManDao.updateDenomination(denominations));

    }

    @Transactional
    public void addNewDenomination(final List<Denomination> denominations) {
        cashManDao.addNewDenomination(denominations);
    }

    @Transactional
    public BigInteger getAvailableCash() {
        return cashManDao.getAvailableCash();
    }

    @Transactional
    public List<Denomination> getAvailableDenominations() {
        return cashManMapper.fromEntitiesToModels(cashManDao.getAvailableDenominations());
    }

    @Scheduled(initialDelayString = "${cashman-util.inventory-scan.initial.delay.ms}",
            fixedDelayString = "${cashman-util.inventory-scan.frequency.ms}")
    public void scanCashInventory() {
        cashManMapper.fromEntitiesToModels(cashManDao.getAvailableDenominations())
                .stream()
                .filter(denomination -> denomination.getDenominationCount() <= denominationThreshold)
                .forEach(denomination ->
                        LOG.warn("CashMan is running out of denomination {}, Currenntly available {}. Please refill.",
                                denomination.getDenominationType(), denomination.getDenominationCount()));

    }
}

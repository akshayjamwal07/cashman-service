package sc.com.assessment.cashman.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import sc.com.assessment.cashman.dto.mapper.CashManMapper;
import sc.com.assessment.cashman.entity.DenominationEntity;
import sc.com.assessment.cashman.model.Denomination;
import sc.com.assessment.cashman.repository.CashManRepository;

@Repository
public class CashManDao {

    private final CashManRepository cashManRepository;

    private final CashManMapper cashManMapper;

    public CashManDao(final CashManRepository cashManRepository, final CashManMapper cashManMapper) {
        this.cashManRepository = cashManRepository;
        this.cashManMapper = cashManMapper;
    }

    public List<DenominationEntity> updateDenomination(final List<Denomination> denominations) {

        final List<DenominationEntity> denominationEntities = new ArrayList<>();
        for (final Denomination denomination : denominations) {
            final DenominationEntity forUpdate = cashManRepository.findByDenominationType(
                    denomination.getDenominationType());

            forUpdate.setDenominationCount(forUpdate.getDenominationCount() + denomination.getDenominationCount());
            denominationEntities.add(cashManRepository.save(forUpdate));
        }

        return denominationEntities;
    }

    public void addNewDenomination(final List<Denomination> denominations) {

        final List<DenominationEntity> denominationEntities = new ArrayList<>();
        for (final Denomination denomination : denominations) {
            final DenominationEntity retrieved = cashManRepository.findByDenominationType(
                    denomination.getDenominationType());

            if (!ObjectUtils.isEmpty(retrieved)
                    && retrieved.getDenominationType().equalsIgnoreCase(denomination.getDenominationType())) {
                retrieved.setDenominationCount(retrieved.getDenominationCount() + denomination.getDenominationCount());
                denominationEntities.add(retrieved);
            } else {
                denominationEntities.add(cashManMapper.fromModelToEntity(denomination));
            }
        }

        cashManRepository.saveAll(denominationEntities);
    }

    public BigInteger getAvailableCash() {
        return cashManRepository.getAvailableCash();
    }

    public List<DenominationEntity> getAvailableDenominations() {
        return cashManRepository.findAll();
    }

    public List<DenominationEntity> getAvailableDenominations(final BigInteger cashAmount) {
        return cashManRepository.retrieveAvailableDenominations(cashAmount);
    }

    public boolean isRequestedCashAvailable(final BigInteger cashAmount) {
        return cashAmount.compareTo(getAvailableCash()) == -1;
    }

    public void syncCashInventory(final List<DenominationEntity> availableDenominations,
                                  final Set<DenominationEntity> denominationsDispensed) {

        for (final DenominationEntity dispensed : denominationsDispensed) {
            for (final DenominationEntity available : availableDenominations) {
                if (dispensed.getDenominationType().equalsIgnoreCase(available.getDenominationType())) {
                    available.setDenominationCount(available.getDenominationCount()
                            - dispensed.getDenominationCount()
                    );
                }
            }

        }
        cashManRepository.saveAll(availableDenominations);
    }
}

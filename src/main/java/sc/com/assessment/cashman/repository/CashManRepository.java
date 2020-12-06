package sc.com.assessment.cashman.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sc.com.assessment.cashman.entity.DenominationEntity;

@Repository
public interface CashManRepository extends CrudRepository<DenominationEntity, String> {
    @Query("Select SUM(denominationCount * denominationValue) from DenominationEntity ")
    BigInteger getAvailableCash();

    @Query("Select d from DenominationEntity d where d.denominationValue <= :cashAmount and d.denominationCount > 0")
    List<DenominationEntity> retrieveAvailableDenominations(@Param("cashAmount") final BigInteger cashAmount);

    DenominationEntity findByDenominationType(String denominationType);

    @Override
    List<DenominationEntity> findAll();
}

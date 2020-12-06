package sc.com.assessment.cashman.dto.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import sc.com.assessment.cashman.dto.request.Cash;
import sc.com.assessment.cashman.dto.response.CashResponse;
import sc.com.assessment.cashman.dto.response.DenominationResponse;
import sc.com.assessment.cashman.entity.DenominationEntity;
import sc.com.assessment.cashman.model.Denomination;

@Mapper(componentModel = "spring")
public interface CashManMapper {

    List<Denomination> fromDtosToModels(final List<Cash> cash);

    DenominationEntity fromModelToEntity(final Denomination denomination);

    Denomination fromEntityToModel(final DenominationEntity denominationEntity);

    List<Denomination> fromEntitiesToModels(final List<DenominationEntity> denominationEntities);

    List<DenominationResponse> fromModelsToDenominationResponses(final List<Denomination> denominations);

    List<CashResponse> fromModelsToCashResponse(final List<Denomination> denominations);

}

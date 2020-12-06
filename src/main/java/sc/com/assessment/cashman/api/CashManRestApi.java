package sc.com.assessment.cashman.api;

import java.math.BigInteger;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sc.com.assessment.cashman.dto.request.CashRequest;
import sc.com.assessment.cashman.dto.response.ErrorResponse;
import sc.com.assessment.cashman.dto.response.GetCashResponse;
import sc.com.assessment.cashman.dto.response.GetDenominationResponse;

@RestController
@Validated
@RequestMapping(path = "/cash-man", consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public interface CashManRestApi {

    String HTTP_OK = "200";
    String HTTP_BAD_REQUEST = "400";
    String HTTP_UNPROCESSABLE_ENTITY = "422";
    String HTTP_SERVER_ERROR = "500";
    String CONTENT_TYPE_JSON = "application/json";
    String HTTP_SERVER_ERROR_DETAIL = "A server error has occurred.";

    /**
     * Recieves a request to dispense cash.
     *
     * @return requested cash in available denominations
     */
    @Operation(
            summary = "Recieves a request to withdraw cash",
            operationId = "withdraw-cash",
            description = "Recieves a request to withdraw cash",
            responses = {
                    @ApiResponse(
                            responseCode = HTTP_OK,
                            description = "The cash withdrawn successfully.",
                            content = @Content(
                                    mediaType = CONTENT_TYPE_JSON,
                                    schema = @Schema(
                                            implementation = GetCashResponse.class
                                    ))
                    ),
                    @ApiResponse(
                            responseCode = HTTP_BAD_REQUEST,
                            description = "The request format to retrieve cash is incorrect.",
                            content = @Content(
                                    mediaType = CONTENT_TYPE_JSON,
                                    schema = @Schema(
                                            implementation = ErrorResponse.class
                                    ))
                    ),
                    @ApiResponse(
                            responseCode = HTTP_UNPROCESSABLE_ENTITY,
                            description = "The cash dispenser failed to dispense cash.",
                            content = @Content(
                                    mediaType = CONTENT_TYPE_JSON,
                                    schema = @Schema(
                                            implementation = ErrorResponse.class
                                    ))
                    ),
                    @ApiResponse(
                            responseCode = HTTP_SERVER_ERROR,
                            description = HTTP_SERVER_ERROR_DETAIL,
                            content = @Content(
                                    mediaType = CONTENT_TYPE_JSON,
                                    schema = @Schema(
                                            implementation = ErrorResponse.class
                                    ))

                    )
            }
    )
    @GetMapping("/withdraw/{cashAmount}")
    @ResponseStatus(HttpStatus.OK)
    GetCashResponse withdrawCash(@PathVariable final BigInteger cashAmount);

    /**
     * Recieves a request to update denominations.
     *
     * @return updated snapshot of cash inventory
     */
    @Operation(
            summary = "Recieves a request to update denomination",
            operationId = "deposit-cash",
            description = "Recieves a request to update denomination",
            responses = {
                    @ApiResponse(
                            responseCode = HTTP_OK,
                            description = "The denomination was updated successfully.",
                            content = @Content(
                                    mediaType = CONTENT_TYPE_JSON,
                                    schema = @Schema(
                                            implementation = GetCashResponse.class
                                    ))
                    ),
                    @ApiResponse(
                            responseCode = HTTP_BAD_REQUEST,
                            description = "The request format to update denomination is incorrect.",
                            content = @Content(
                                    mediaType = CONTENT_TYPE_JSON,
                                    schema = @Schema(
                                            implementation = Problem.class
                                    ))
                    ),
                    @ApiResponse(
                            responseCode = HTTP_UNPROCESSABLE_ENTITY,
                            description = "The cashman failed to update denomination.",
                            content = @Content(
                                    mediaType = CONTENT_TYPE_JSON,
                                    schema = @Schema(
                                            implementation = Problem.class
                                    ))
                    ),
                    @ApiResponse(
                            responseCode = HTTP_SERVER_ERROR,
                            description = HTTP_SERVER_ERROR_DETAIL,
                            content = @Content(
                                    mediaType = CONTENT_TYPE_JSON,
                                    schema = @Schema(
                                            implementation = Problem.class
                                    ))
                    )
            }
    )
    @PutMapping("/update-denomination")
    @ResponseStatus(HttpStatus.OK)
    GetCashResponse updateDenomination(@RequestBody final CashRequest cash);

    /**
     * Recieves a request to add new denomination.
     *
     * @return updated snapshot of cash inventory
     */
    @Operation(
            summary = "Recieves a request to add denomination",
            operationId = "add-denomination",
            description = "Recieves a request to add denomination",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "The cash denomination added successfully"
                    ),
                    @ApiResponse(
                            responseCode = HTTP_BAD_REQUEST,
                            description = "The request format to add cash denomination is incorrect."
                    ),
                    @ApiResponse(
                            responseCode = HTTP_UNPROCESSABLE_ENTITY,
                            description = "The cashman failed to add cash denomination."
                    ),
                    @ApiResponse(
                            responseCode = HTTP_SERVER_ERROR,
                            description = HTTP_SERVER_ERROR_DETAIL
                    )
            }
    )
    @PostMapping("/add-denomination")
    @ResponseStatus(HttpStatus.CREATED)
    void addNewDenomination(@RequestBody final CashRequest cashRequest);

    /**
     * Recieves a request to retrieve available cash amount.
     *
     * @return Sum of available cash amount
     */
    @Operation(
            summary = "Recieves a request to retrieve available cash amount",
            operationId = "available-cash",
            description = "Recieves a request to retrieve cash amount",
            responses = {
                    @ApiResponse(
                            responseCode = HTTP_OK,
                            description = "The sum of available cash returned successfully."
                    ),
                    @ApiResponse(
                            responseCode = HTTP_BAD_REQUEST,
                            description = "The request format to retrieve cash amount is incorrect."
                    ),
                    @ApiResponse(
                            responseCode = HTTP_UNPROCESSABLE_ENTITY,
                            description = "The cash dispenser failed to calculate available cash."
                    ),
                    @ApiResponse(
                            responseCode = HTTP_SERVER_ERROR,
                            description = HTTP_SERVER_ERROR_DETAIL
                    )
            }
    )
    @GetMapping("/available-cash")
    @ResponseStatus(HttpStatus.OK)
    BigInteger getAvailableCash();

    /**
     * Recieves a request to dispense cash.
     *
     * @return requested cash in available denominations
     */
    @Operation(
            summary = "Recieves a request to retrieve available denominations",
            operationId = "available-denominations",
            description = "Recieves a request to retrieve available denominations",
            responses = {
                    @ApiResponse(
                            responseCode = HTTP_OK,
                            description = "The available denominations returned successfully.",
                            content = @Content(
                                    mediaType = CONTENT_TYPE_JSON,
                                    schema = @Schema(
                                            implementation = List.class
                                    ))
                    ),
                    @ApiResponse(
                            responseCode = HTTP_BAD_REQUEST,
                            description = "The request format to retrieve available denominations is incorrect.",
                            content = @Content(
                                    mediaType = CONTENT_TYPE_JSON,
                                    schema = @Schema(
                                            implementation = Problem.class
                                    ))
                    ),
                    @ApiResponse(
                            responseCode = HTTP_UNPROCESSABLE_ENTITY,
                            description = "The cash dispenser failed to retrieve available denominations.",
                            content = @Content(
                                    mediaType = CONTENT_TYPE_JSON,
                                    schema = @Schema(
                                            implementation = Problem.class
                                    ))
                    ),
                    @ApiResponse(
                            responseCode = HTTP_SERVER_ERROR,
                            description = HTTP_SERVER_ERROR_DETAIL,
                            content = @Content(
                                    mediaType = CONTENT_TYPE_JSON,
                                    schema = @Schema(
                                            implementation = Problem.class
                                    ))
                    )
            }
    )
    @GetMapping("/available-denominations")
    @ResponseStatus(HttpStatus.OK)
    GetDenominationResponse getAvailableDenominations();

}

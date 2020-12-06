package sc.com.assessment.cashman.client;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigInteger;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import sc.com.assessment.cashman.dto.request.CashRequest;
import sc.com.assessment.cashman.dto.response.ErrorResponse;
import sc.com.assessment.cashman.dto.response.GetCashResponse;
import sc.com.assessment.cashman.dto.response.GetDenominationResponse;

@Component
public class CashManClient {

    // URL to send the operator request to
    private static final String BASE_URL = "/cash-man";
    private static final String WITHDRAW_CASH_URL = "/withdraw";
    private static final String UPDATE_URL = "/update-denomination";
    private static final String ADD_URL = "/add-denomination";
    private static final String AVAILABLE_CASH_URL = "/available-cash";
    private static final String AVAILABLE_DENOMINATION_URL = "/available-denominations";

    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;

    public CashManClient(final ObjectMapper objectMapper, final MockMvc mockMvc) {
        this.objectMapper = objectMapper;
        this.mockMvc = mockMvc;
    }

    public GetCashResponse withdrawCash(final BigInteger cashAmount) throws Exception {
        final MvcResult mvcResult = mockMvc.perform(get(BASE_URL + WITHDRAW_CASH_URL
                + "/{cashAmount}", cashAmount)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), GetCashResponse.class);
    }

    public GetCashResponse updateDenomination(final CashRequest cashRequest) throws Exception {
        //final CashRequest request = ImmutableCashRequest.builder().cash(getCash()).build();
        final MvcResult mvcResult = mockMvc.perform(put(BASE_URL + UPDATE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cashRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), GetCashResponse.class);

    }

    public void addDenomination(final CashRequest cashRequest) throws Exception {
        mockMvc.perform(post(BASE_URL + ADD_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cashRequest)))
                .andExpect(status().isCreated());

    }

    public BigInteger getAvailableCash() throws Exception {
        final MvcResult mvcResult = mockMvc.perform(get(BASE_URL + AVAILABLE_CASH_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), BigInteger.class);

    }

    public GetDenominationResponse getAvailableDenominations() throws Exception {
        final MvcResult mvcResult = mockMvc.perform(get(BASE_URL + AVAILABLE_DENOMINATION_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), GetDenominationResponse.class);

    }

    public ErrorResponse withdrawOverdrawnAmount(final BigInteger cashAmount) throws Exception {
        final MvcResult mvcResult = mockMvc.perform(get(BASE_URL + WITHDRAW_CASH_URL
                + "/{cashAmount}", cashAmount)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();

        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorResponse.class);
    }
}

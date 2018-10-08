package com.traderev.auction.controller;

import java.util.Arrays;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.traderev.auction.model.Bid;
import com.traderev.auction.model.Car;
import com.traderev.auction.model.User;
import com.traderev.auction.service.IBidService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(MockitoJUnitRunner.class)
public class AuctionControllerTest
{
    private static final String BID = "/bid/";
    private static final String GET_ALL = "/allBids/car/";
    private static final String GET_WINNING = "/winningBid/car/";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private IBidService bidService;

    @InjectMocks
    private AuctionController auctionController;
    private MockMvc mockMvc;

    @Before
    public void setUp()
    {
        mockMvc = MockMvcBuilders.standaloneSetup(auctionController).setControllerAdvice().build();
    }

    @Test
    public void shouldPlaceBidOnCarAndReturnTheBidMade() throws Exception
    {
        final Bid bid = getTestBidData(1L, 123.00);

        final String bidJSON = objectMapper.writeValueAsString(bid);
        when(bidService.recordBid(any(Bid.class))).thenReturn(bid);

        mockMvc.perform(post(BID).content(bidJSON).header("Content-Type", "application/json"))
                .andExpect(status().is(201)).andExpect(jsonPath("$.user.id").value(1))
                .andExpect(jsonPath("$.user.firstName").value(bid.getUser().getFirstName()))
                .andExpect(jsonPath("$.user.lastName").value(bid.getUser().getLastName()))
                .andExpect(jsonPath("$.user.emailAddress").value(bid.getUser().getEmailAddress()))
                .andExpect(jsonPath("$.car.id").value(bid.getCar().getId()))
                .andExpect(jsonPath("$.bidPrice").value(bid.getBidPrice()));
    }

    @Test
    public void shouldGetAllBidsMade() throws Exception
    {
        final Bid bid1 = getTestBidData(1L, 123.00);
        final Bid bid2 = getTestBidData(1L, 1234.00);

        when(bidService.getListOfBids(1L)).thenReturn(Arrays.asList(bid1, bid2));

        mockMvc.perform(get(GET_ALL + 1)).andExpect(status().is(200)).andExpect(jsonPath("$.[0].user.id").value(1))
                .andExpect(jsonPath("$.[0].user.firstName").value(bid1.getUser().getFirstName()))
                .andExpect(jsonPath("$.[0].user.lastName").value(bid1.getUser().getLastName()))
                .andExpect(jsonPath("$.[0].user.emailAddress").value(bid1.getUser().getEmailAddress()))
                .andExpect(jsonPath("$.[0].car.id").value(bid1.getCar().getId()))
                .andExpect(jsonPath("$.[0].bidPrice").value(bid1.getBidPrice()))
                .andExpect(jsonPath("$.[1].user.id").value(1))
                .andExpect(jsonPath("$.[1].user.firstName").value(bid2.getUser().getFirstName()))
                .andExpect(jsonPath("$.[1].user.lastName").value(bid2.getUser().getLastName()))
                .andExpect(jsonPath("$.[1].user.emailAddress").value(bid2.getUser().getEmailAddress()))
                .andExpect(jsonPath("$.[1].car.id").value(bid2.getCar().getId()))
                .andExpect(jsonPath("$.[1].bidPrice").value(bid2.getBidPrice()));
    }

    @Test
    public void shouldGetWinningBidsMade() throws Exception
    {
        final Bid bid = getTestBidData(1L, 1234.00);

        when(bidService.getCurrentWinningBid(1L)).thenReturn(bid);

        mockMvc.perform(get(GET_WINNING + 1)).andExpect(status().is(200))
                .andExpect(jsonPath("$.user.id").value(1))
                .andExpect(jsonPath("$.user.firstName").value(bid.getUser().getFirstName()))
                .andExpect(jsonPath("$.user.lastName").value(bid.getUser().getLastName()))
                .andExpect(jsonPath("$.user.emailAddress").value(bid.getUser().getEmailAddress()))
                .andExpect(jsonPath("$.car.id").value(bid.getCar().getId()))
                .andExpect(jsonPath("$.bidPrice").value(bid.getBidPrice()));
    }

    private Bid getTestBidData(Long id, Double bidAmount)
    {
        return new Bid(new User(id, "fName", "lName", "emailAddr"), new Car(id, 1, "", "", ""), bidAmount);
    }
}

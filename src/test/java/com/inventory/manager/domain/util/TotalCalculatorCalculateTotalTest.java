package com.inventory.manager.domain.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.inventory.manager.application.item.dto.GetItemResponseDTO;
import com.inventory.manager.application.item.price.PriceResponseDTO;
import com.inventory.manager.application.shared.dto.CalculateTotalRequestDTO;
import com.inventory.manager.application.shared.dto.CalculateTotalResponseDTO;
import com.inventory.manager.application.shared.dto.LineDTO;
import com.inventory.manager.domain.enums.InvoiceType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAppConfig.class, loader = AnnotationConfigContextLoader.class)
public class TotalCalculatorCalculateTotalTest {

    private static final String VALUE = "VALUE";

    private static final String PER_PIECE = "PER_PIECE";

    private static final String PERCENTAGE = "PERCENTAGE";

    private static final String PER_DOZEN = "PER_DOZEN";

    @Autowired
    private TotalCalculator totalCalculator;

    @Test
    public void testCalculateTotal1() {

        GetItemResponseDTO itemAppleS = getAppleS(PER_DOZEN);
        GetItemResponseDTO itemAppleM = getAppleM(PER_DOZEN);
        GetItemResponseDTO itemAppleL = getAppleL(PER_DOZEN);
        GetItemResponseDTO itemAppleXL = getAppleXL(PER_DOZEN);
        GetItemResponseDTO itemAppleXXL = getAppleXXL(PER_DOZEN);

        List<LineDTO> lines = new ArrayList<LineDTO>();
        lines.add(getLine(itemAppleS, PERCENTAGE, 15, 120L));
        lines.add(getLine(itemAppleM, PERCENTAGE, 15, 120L));
        lines.add(getLine(itemAppleL, PERCENTAGE, 15, 120L));
        lines.add(getLine(itemAppleXL, PERCENTAGE, 15, 120L));
        lines.add(getLine(itemAppleXXL, null, 0, 120L));

        CalculateTotalRequestDTO requestDTO = new CalculateTotalRequestDTO();
        requestDTO.setLines(lines);

        CalculateTotalResponseDTO responseDTO = totalCalculator.calculateTotal(requestDTO, InvoiceType.GRN);

        List<LineDTO> linesResp = responseDTO.getLines();
        for (LineDTO line : linesResp) {

            if (line.getItem().getId().equals(1)) {

                Assert.assertEquals("Apple S line G.total mismatch", 1000.00, line.getGrandTotal(), 0.0);
                Assert.assertEquals("Apple S line D.total mismatch", 150.00, line.getDiscountTotal(), 0.0);
                Assert.assertEquals("Apple S line N.total mismatch", 850.00, line.getNetTotal(), 0.0);
            } else if (line.getItem().getId().equals(2)) {

                Assert.assertEquals("Apple M line G.total mismatch", 2000.00, line.getGrandTotal(), 0.0);
                Assert.assertEquals("Apple M line D.total mismatch", 300.00, line.getDiscountTotal(), 0.0);
                Assert.assertEquals("Apple M line N.total mismatch", 1700.00, line.getNetTotal(), 0.0);
            } else if (line.getItem().getId().equals(3)) {

                Assert.assertEquals("Apple L line G.total mismatch", 3000.00, line.getGrandTotal(), 0.0);
                Assert.assertEquals("Apple L line D.total mismatch", 450.00, line.getDiscountTotal(), 0.0);
                Assert.assertEquals("Apple L line N.total mismatch", 2550.00, line.getNetTotal(), 0.0);
            } else if (line.getItem().getId().equals(4)) {

                Assert.assertEquals("Apple XL line G.total mismatch", 4000.00, line.getGrandTotal(), 0.0);
                Assert.assertEquals("Apple XL line D.total mismatch", 600.00, line.getDiscountTotal(), 0.0);
                Assert.assertEquals("Apple XL line N.total mismatch", 3400.00, line.getNetTotal(), 0.0);
            } else if (line.getItem().getId().equals(5)) {

                Assert.assertEquals("Apple XXL line G.total mismatch", 5000.00, line.getGrandTotal(), 0.0);
                Assert.assertEquals("Apple XXL line D.total mismatch", 0.00, line.getDiscountTotal(), 0.0);
                Assert.assertEquals("Apple XXL line N.total mismatch", 5000.00, line.getNetTotal(), 0.0);
            }
        }

        Assert.assertEquals("Apple XXL G.total mismatch", 15000.00, responseDTO.getGrandTotal(), 0.0);
        Assert.assertEquals("Apple XXL D.total mismatch", 1500.00, responseDTO.getDiscountTotal(), 0.0);
        Assert.assertEquals("Apple XXL N.total mismatch", 13500.00, responseDTO.getNetTotal(), 0.0);

    }

    @Test
    public void testCalculateTotal2() {

        GetItemResponseDTO itemAppleS = getAppleS(PER_PIECE);
        GetItemResponseDTO itemAppleM = getAppleM(PER_PIECE);
        GetItemResponseDTO itemAppleL = getAppleL(PER_PIECE);
        GetItemResponseDTO itemAppleXL = getAppleXL(PER_DOZEN);
        GetItemResponseDTO itemAppleXXL = getAppleXXL(PER_PIECE);

        List<LineDTO> lines = new ArrayList<LineDTO>();
        lines.add(getLine(itemAppleS, VALUE, 150, 120L));
        lines.add(getLine(itemAppleM, VALUE, 300, 120L));
        lines.add(getLine(itemAppleL, VALUE, 450, 120L));
        lines.add(getLine(itemAppleXL, PERCENTAGE, 20, 120L));
        lines.add(getLine(itemAppleXXL, null, 0, 150L));

        CalculateTotalRequestDTO requestDTO = new CalculateTotalRequestDTO();
        requestDTO.setLines(lines);

        CalculateTotalResponseDTO responseDTO = totalCalculator.calculateTotal(requestDTO, InvoiceType.GRN);

        List<LineDTO> linesResp = responseDTO.getLines();
        for (LineDTO line : linesResp) {

            if (line.getItem().getId().equals(1)) {

                Assert.assertEquals("Apple S line G.total mismatch", 12000.00, line.getGrandTotal(), 0.0);
                Assert.assertEquals("Apple S line D.total mismatch", 150.00, line.getDiscountTotal(), 0.0);
                Assert.assertEquals("Apple S line N.total mismatch", 11850.00, line.getNetTotal(), 0.0);
            } else if (line.getItem().getId().equals(2)) {

                Assert.assertEquals("Apple M line G.total mismatch", 24000.00, line.getGrandTotal(), 0.0);
                Assert.assertEquals("Apple M line D.total mismatch", 300.00, line.getDiscountTotal(), 0.0);
                Assert.assertEquals("Apple M line N.total mismatch", 23700.00, line.getNetTotal(), 0.0);
            } else if (line.getItem().getId().equals(3)) {

                Assert.assertEquals("Apple L line G.total mismatch", 36000.00, line.getGrandTotal(), 0.0);
                Assert.assertEquals("Apple L line D.total mismatch", 450.00, line.getDiscountTotal(), 0.0);
                Assert.assertEquals("Apple L line N.total mismatch", 35550.00, line.getNetTotal(), 0.0);
            } else if (line.getItem().getId().equals(4)) {

                Assert.assertEquals("Apple XL line G.total mismatch", 4000.00, line.getGrandTotal(), 0.0);
                Assert.assertEquals("Apple XL line D.total mismatch", 800.00, line.getDiscountTotal(), 0.0);
                Assert.assertEquals("Apple XL line N.total mismatch", 3200.00, line.getNetTotal(), 0.0);
            } else if (line.getItem().getId().equals(5)) {

                Assert.assertEquals("Apple XXL line G.total mismatch", 75000.00, line.getGrandTotal(), 0.0);
                Assert.assertEquals("Apple XXL line D.total mismatch", 0.00, line.getDiscountTotal(), 0.0);
                Assert.assertEquals("Apple XXL line N.total mismatch", 75000.00, line.getNetTotal(), 0.0);
            }
        }

        Assert.assertEquals("Apple XXL G.total mismatch", 151000.00, responseDTO.getGrandTotal(), 0.0);
        Assert.assertEquals("Apple XXL D.total mismatch", 1700.00, responseDTO.getDiscountTotal(), 0.0);
        Assert.assertEquals("Apple XXL N.total mismatch", 149300.00, responseDTO.getNetTotal(), 0.0);

    }

    @Test
    public void testCalculateTotal3() {

        GetItemResponseDTO itemAppleS = getAppleS(PER_DOZEN);
        GetItemResponseDTO itemAppleM = getAppleM(PER_DOZEN);
        GetItemResponseDTO itemAppleL = getAppleL(PER_DOZEN);
        GetItemResponseDTO itemAppleXL = getAppleXL(PER_DOZEN);
        GetItemResponseDTO itemAppleXXL = getAppleXXL(PER_DOZEN);

        List<LineDTO> lines = new ArrayList<LineDTO>();
        lines.add(getLine(itemAppleS, PERCENTAGE, 15, 120L));
        lines.add(getLine(itemAppleM, PERCENTAGE, 15, 120L));
        lines.add(getLine(itemAppleL, PERCENTAGE, 15, 120L));
        lines.add(getLine(itemAppleXL, PERCENTAGE, 15, 120L));
        lines.add(getLine(itemAppleXXL, null, 0, 120L));

        CalculateTotalRequestDTO requestDTO = new CalculateTotalRequestDTO();
        requestDTO.setLines(lines);

        CalculateTotalResponseDTO responseDTO = totalCalculator.calculateTotal(requestDTO, InvoiceType.SALES);

        List<LineDTO> linesResp = responseDTO.getLines();
        for (LineDTO line : linesResp) {

            if (line.getItem().getId().equals(1)) {

                Assert.assertEquals("Apple S line G.total mismatch", 1500.00, line.getGrandTotal(), 0.0);
                Assert.assertEquals("Apple S line D.total mismatch", 225.00, line.getDiscountTotal(), 0.0);
                Assert.assertEquals("Apple S line N.total mismatch", 1275.00, line.getNetTotal(), 0.0);
            } else if (line.getItem().getId().equals(2)) {

                Assert.assertEquals("Apple M line G.total mismatch", 2500.00, line.getGrandTotal(), 0.0);
                Assert.assertEquals("Apple M line D.total mismatch", 375.00, line.getDiscountTotal(), 0.0);
                Assert.assertEquals("Apple M line N.total mismatch", 2125.00, line.getNetTotal(), 0.0);
            } else if (line.getItem().getId().equals(3)) {

                Assert.assertEquals("Apple L line G.total mismatch", 3500.00, line.getGrandTotal(), 0.0);
                Assert.assertEquals("Apple L line D.total mismatch", 525.00, line.getDiscountTotal(), 0.0);
                Assert.assertEquals("Apple L line N.total mismatch", 2975.00, line.getNetTotal(), 0.0);
            } else if (line.getItem().getId().equals(4)) {

                Assert.assertEquals("Apple XL line G.total mismatch", 4500.00, line.getGrandTotal(), 0.0);
                Assert.assertEquals("Apple XL line D.total mismatch", 675.00, line.getDiscountTotal(), 0.0);
                Assert.assertEquals("Apple XL line N.total mismatch", 3825.00, line.getNetTotal(), 0.0);
            } else if (line.getItem().getId().equals(5)) {

                Assert.assertEquals("Apple XXL line G.total mismatch", 5500.00, line.getGrandTotal(), 0.0);
                Assert.assertEquals("Apple XXL line D.total mismatch", 0.00, line.getDiscountTotal(), 0.0);
                Assert.assertEquals("Apple XXL line N.total mismatch", 5500.00, line.getNetTotal(), 0.0);
            }
        }

        Assert.assertEquals("Apple XXL G.total mismatch", 17500.00, responseDTO.getGrandTotal(), 0.0);
        Assert.assertEquals("Apple XXL D.total mismatch", 1800.00, responseDTO.getDiscountTotal(), 0.0);
        Assert.assertEquals("Apple XXL N.total mismatch", 15700.00, responseDTO.getNetTotal(), 0.0);

    }

    @Test
    public void testCalculateTotal4() {

        GetItemResponseDTO itemAppleS = getAppleS(PER_PIECE);
        GetItemResponseDTO itemAppleM = getAppleM(PER_PIECE);
        GetItemResponseDTO itemAppleL = getAppleL(PER_PIECE);
        GetItemResponseDTO itemAppleXL = getAppleXL(PER_DOZEN);
        GetItemResponseDTO itemAppleXXL = getAppleXXL(PER_PIECE);

        List<LineDTO> lines = new ArrayList<LineDTO>();
        lines.add(getLine(itemAppleS, VALUE, 150, 120L));
        lines.add(getLine(itemAppleM, VALUE, 300, 120L));
        lines.add(getLine(itemAppleL, VALUE, 450, 120L));
        lines.add(getLine(itemAppleXL, PERCENTAGE, 20, 120L));
        lines.add(getLine(itemAppleXXL, null, 0, 150L));

        CalculateTotalRequestDTO requestDTO = new CalculateTotalRequestDTO();
        requestDTO.setLines(lines);

        CalculateTotalResponseDTO responseDTO = totalCalculator.calculateTotal(requestDTO, InvoiceType.SALES);

        List<LineDTO> linesResp = responseDTO.getLines();
        for (LineDTO line : linesResp) {

            if (line.getItem().getId().equals(1)) {

                Assert.assertEquals("Apple S line G.total mismatch", 18000.00, line.getGrandTotal(), 0.0);
                Assert.assertEquals("Apple S line D.total mismatch", 150.00, line.getDiscountTotal(), 0.0);
                Assert.assertEquals("Apple S line N.total mismatch", 17850.00, line.getNetTotal(), 0.0);
            } else if (line.getItem().getId().equals(2)) {

                Assert.assertEquals("Apple M line G.total mismatch", 30000.00, line.getGrandTotal(), 0.0);
                Assert.assertEquals("Apple M line D.total mismatch", 300.00, line.getDiscountTotal(), 0.0);
                Assert.assertEquals("Apple M line N.total mismatch", 29700.00, line.getNetTotal(), 0.0);
            } else if (line.getItem().getId().equals(3)) {

                Assert.assertEquals("Apple L line G.total mismatch", 42000.00, line.getGrandTotal(), 0.0);
                Assert.assertEquals("Apple L line D.total mismatch", 450.00, line.getDiscountTotal(), 0.0);
                Assert.assertEquals("Apple L line N.total mismatch", 41550.00, line.getNetTotal(), 0.0);
            } else if (line.getItem().getId().equals(4)) {

                Assert.assertEquals("Apple XL line G.total mismatch", 4500.00, line.getGrandTotal(), 0.0);
                Assert.assertEquals("Apple XL line D.total mismatch", 900.00, line.getDiscountTotal(), 0.0);
                Assert.assertEquals("Apple XL line N.total mismatch", 3600.00, line.getNetTotal(), 0.0);
            } else if (line.getItem().getId().equals(5)) {

                Assert.assertEquals("Apple XXL line G.total mismatch", 82500.00, line.getGrandTotal(), 0.0);
                Assert.assertEquals("Apple XXL line D.total mismatch", 0.00, line.getDiscountTotal(), 0.0);
                Assert.assertEquals("Apple XXL line N.total mismatch", 82500.00, line.getNetTotal(), 0.0);
            }
        }

        Assert.assertEquals("Apple XXL G.total mismatch", 177000.00, responseDTO.getGrandTotal(), 0.0);
        Assert.assertEquals("Apple XXL D.total mismatch", 1800.00, responseDTO.getDiscountTotal(), 0.0);
        Assert.assertEquals("Apple XXL N.total mismatch", 175200.00, responseDTO.getNetTotal(), 0.0);

    }

    private LineDTO getLine(GetItemResponseDTO item, String discountMode, double discountVal, long quantity) {
        LineDTO line = new LineDTO();
        line.setItem(item);
        line.setDiscountMode(discountMode);
        line.setDiscountValue(discountVal);
        line.setQuantity(quantity);
        return line;
    }

    private GetItemResponseDTO getAppleS(String priceType) {
        PriceResponseDTO price = new PriceResponseDTO();
        price.setBuyingPrice(100.00);
        price.setSellingPrice(150.00);
        price.setPriceType(priceType);

        GetItemResponseDTO item = new GetItemResponseDTO();
        item.setId(1);
        item.setName("APPLE S");
        item.setPrice(price);
        return item;
    }

    private GetItemResponseDTO getAppleM(String priceType) {
        PriceResponseDTO price = new PriceResponseDTO();
        price.setBuyingPrice(200.00);
        price.setSellingPrice(250.00);
        price.setPriceType(priceType);

        GetItemResponseDTO item = new GetItemResponseDTO();
        item.setId(2);
        item.setName("APPLE M");
        item.setPrice(price);
        return item;
    }

    private GetItemResponseDTO getAppleL(String priceType) {
        PriceResponseDTO price = new PriceResponseDTO();
        price.setBuyingPrice(300.00);
        price.setSellingPrice(350.00);
        price.setPriceType(priceType);

        GetItemResponseDTO item = new GetItemResponseDTO();
        item.setId(3);
        item.setName("APPLE L");
        item.setPrice(price);
        return item;
    }

    private GetItemResponseDTO getAppleXL(String priceType) {
        PriceResponseDTO price = new PriceResponseDTO();
        price.setBuyingPrice(400.00);
        price.setSellingPrice(450.00);
        price.setPriceType(priceType);

        GetItemResponseDTO item = new GetItemResponseDTO();
        item.setId(4);
        item.setName("APPLE L");
        item.setPrice(price);
        return item;
    }

    private GetItemResponseDTO getAppleXXL(String priceType) {
        PriceResponseDTO price = new PriceResponseDTO();
        price.setBuyingPrice(500.00);
        price.setSellingPrice(550.00);
        price.setPriceType(priceType);

        GetItemResponseDTO item = new GetItemResponseDTO();
        item.setId(5);
        item.setName("APPLE L");
        item.setPrice(price);
        return item;
    }

}

package com.inventory.manager.domain.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.inventory.manager.domain.enums.DiscountMode;
import com.inventory.manager.domain.enums.PriceType;
import com.inventory.manager.domain.item.Item;
import com.inventory.manager.domain.item.price.ItemPrice;
import com.inventory.manager.domain.stockhistory.grn.GoodReceiveNote;
import com.inventory.manager.domain.stockhistory.grn.GoodReceiveNoteLine;
import com.inventory.manager.domain.stockhistory.sales.SalesInvoice;
import com.inventory.manager.domain.stockhistory.sales.SalesInvoiceLine;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAppConfig.class, loader = AnnotationConfigContextLoader.class)
public class TotalCalculatorUpdateGRNAndSITest {

    @Autowired
    private TotalCalculator totalCalculator;

    @Test
    public void testupdateGRNLineTotal1() {

        Item itemAppleS = getAppleS(PriceType.PER_DOZEN);
        Item itemAppleM = getAppleM(PriceType.PER_DOZEN);
        Item itemAppleL = getAppleL(PriceType.PER_DOZEN);
        Item itemAppleXL = getAppleXL(PriceType.PER_DOZEN);
        Item itemAppleXXL = getAppleXXL(PriceType.PER_DOZEN);

        List<GoodReceiveNoteLine> goodReceiveNoteLines = new ArrayList<>();
        goodReceiveNoteLines.add(getGRNLine(itemAppleS, DiscountMode.PERCENTAGE, BigDecimal.valueOf(15), 120L));
        goodReceiveNoteLines.add(getGRNLine(itemAppleM, DiscountMode.PERCENTAGE, BigDecimal.valueOf(15), 120L));
        goodReceiveNoteLines.add(getGRNLine(itemAppleL, DiscountMode.PERCENTAGE, BigDecimal.valueOf(15), 120L));
        goodReceiveNoteLines.add(getGRNLine(itemAppleXL, DiscountMode.PERCENTAGE, BigDecimal.valueOf(15), 120L));
        goodReceiveNoteLines.add(getGRNLine(itemAppleXXL, null, BigDecimal.ZERO, 120L));

        goodReceiveNoteLines = totalCalculator.updateGRNLineTotal(goodReceiveNoteLines);

        for (GoodReceiveNoteLine line : goodReceiveNoteLines) {

            if (line.getItem().getId().equals(1)) {

                Assert.assertEquals("Apple S line G.total mismatch", 1000.00, line.getGrandTotal().doubleValue(), 0.0);
                Assert.assertEquals("Apple S line D.total mismatch", 150.00, line.getDiscountTotal().doubleValue(), 0.0);
                Assert.assertEquals("Apple S line N.total mismatch", 850.00, line.getNetTotal().doubleValue(), 0.0);
            } else if (line.getItem().getId().equals(2)) {

                Assert.assertEquals("Apple M line G.total mismatch", 2000.00, line.getGrandTotal().doubleValue(), 0.0);
                Assert.assertEquals("Apple M line D.total mismatch", 300.00, line.getDiscountTotal().doubleValue(), 0.0);
                Assert.assertEquals("Apple M line N.total mismatch", 1700.00, line.getNetTotal().doubleValue(), 0.0);
            } else if (line.getItem().getId().equals(3)) {

                Assert.assertEquals("Apple L line G.total mismatch", 3000.00, line.getGrandTotal().doubleValue(), 0.0);
                Assert.assertEquals("Apple L line D.total mismatch", 450.00, line.getDiscountTotal().doubleValue(), 0.0);
                Assert.assertEquals("Apple L line N.total mismatch", 2550.00, line.getNetTotal().doubleValue(), 0.0);
            } else if (line.getItem().getId().equals(4)) {

                Assert.assertEquals("Apple XL line G.total mismatch", 4000.00, line.getGrandTotal().doubleValue(), 0.0);
                Assert.assertEquals("Apple XL line D.total mismatch", 600.00, line.getDiscountTotal().doubleValue(),
                        0.0);
                Assert.assertEquals("Apple XL line N.total mismatch", 3400.00, line.getNetTotal().doubleValue(), 0.0);
            } else if (line.getItem().getId().equals(5)) {

                Assert.assertEquals("Apple XXL line G.total mismatch", 5000.00, line.getGrandTotal().doubleValue(), 0.0);
                Assert.assertEquals("Apple XXL line D.total mismatch", 0.00, line.getDiscountTotal().doubleValue(), 0.0);
                Assert.assertEquals("Apple XXL line N.total mismatch", 5000.00, line.getNetTotal().doubleValue(), 0.0);
            }
        }

        GoodReceiveNote grn = new GoodReceiveNote();
        totalCalculator.updateGRNTotal(goodReceiveNoteLines, grn);
        Assert.assertEquals("Apple XXL G.total mismatch", 15000.00, grn.getGrandTotal().doubleValue(), 0.0);
        Assert.assertEquals("Apple XXL D.total mismatch", 1500.00, grn.getDiscountTotal().doubleValue(), 0.0);
        Assert.assertEquals("Apple XXL N.total mismatch", 13500.00, grn.getNetTotal().doubleValue(), 0.0);

    }

    @Test
    public void testupdateGRNLineTotal2() {

        Item itemAppleS = getAppleS(PriceType.PER_PIECE);
        Item itemAppleM = getAppleM(PriceType.PER_PIECE);
        Item itemAppleL = getAppleL(PriceType.PER_PIECE);
        Item itemAppleXL = getAppleXL(PriceType.PER_DOZEN);
        Item itemAppleXXL = getAppleXXL(PriceType.PER_PIECE);

        List<GoodReceiveNoteLine> goodReceiveNoteLines = new ArrayList<>();
        goodReceiveNoteLines.add(getGRNLine(itemAppleS, DiscountMode.VALUE, BigDecimal.valueOf(150), 120L));
        goodReceiveNoteLines.add(getGRNLine(itemAppleM, DiscountMode.VALUE, BigDecimal.valueOf(300), 120L));
        goodReceiveNoteLines.add(getGRNLine(itemAppleL, DiscountMode.VALUE, BigDecimal.valueOf(450), 120L));
        goodReceiveNoteLines.add(getGRNLine(itemAppleXL, DiscountMode.PERCENTAGE, BigDecimal.valueOf(20), 120L));
        goodReceiveNoteLines.add(getGRNLine(itemAppleXXL, null, BigDecimal.ZERO, 150L));

        goodReceiveNoteLines = totalCalculator.updateGRNLineTotal(goodReceiveNoteLines);

        for (GoodReceiveNoteLine line : goodReceiveNoteLines) {

            if (line.getItem().getId().equals(1)) {

                Assert.assertEquals("Apple S line G.total mismatch", 12000.00, line.getGrandTotal().doubleValue(), 0.0);
                Assert.assertEquals("Apple S line D.total mismatch", 150.00, line.getDiscountTotal().doubleValue(), 0.0);
                Assert.assertEquals("Apple S line N.total mismatch", 11850.00, line.getNetTotal().doubleValue(), 0.0);
            } else if (line.getItem().getId().equals(2)) {

                Assert.assertEquals("Apple M line G.total mismatch", 24000.00, line.getGrandTotal().doubleValue(), 0.0);
                Assert.assertEquals("Apple M line D.total mismatch", 300.00, line.getDiscountTotal().doubleValue(), 0.0);
                Assert.assertEquals("Apple M line N.total mismatch", 23700.00, line.getNetTotal().doubleValue(), 0.0);
            } else if (line.getItem().getId().equals(3)) {

                Assert.assertEquals("Apple L line G.total mismatch", 36000.00, line.getGrandTotal().doubleValue(), 0.0);
                Assert.assertEquals("Apple L line D.total mismatch", 450.00, line.getDiscountTotal().doubleValue(), 0.0);
                Assert.assertEquals("Apple L line N.total mismatch", 35550.00, line.getNetTotal().doubleValue(), 0.0);
            } else if (line.getItem().getId().equals(4)) {

                Assert.assertEquals("Apple XL line G.total mismatch", 4000.00, line.getGrandTotal().doubleValue(), 0.0);
                Assert.assertEquals("Apple XL line D.total mismatch", 800.00, line.getDiscountTotal().doubleValue(),
                        0.0);
                Assert.assertEquals("Apple XL line N.total mismatch", 3200.00, line.getNetTotal().doubleValue(), 0.0);
            } else if (line.getItem().getId().equals(5)) {

                Assert.assertEquals("Apple XXL line G.total mismatch", 75000.00, line.getGrandTotal().doubleValue(),
                        0.0);
                Assert.assertEquals("Apple XXL line D.total mismatch", 0.00, line.getDiscountTotal().doubleValue(), 0.0);
                Assert.assertEquals("Apple XXL line N.total mismatch", 75000.00, line.getNetTotal().doubleValue(), 0.0);
            }
        }

        GoodReceiveNote grn = new GoodReceiveNote();
        totalCalculator.updateGRNTotal(goodReceiveNoteLines, grn);
        Assert.assertEquals("Apple XXL G.total mismatch", 151000.00, grn.getGrandTotal().doubleValue(), 0.0);
        Assert.assertEquals("Apple XXL D.total mismatch", 1700.00, grn.getDiscountTotal().doubleValue(), 0.0);
        Assert.assertEquals("Apple XXL N.total mismatch", 149300.00, grn.getNetTotal().doubleValue(), 0.0);

    }

    @Test
    public void testUpdateSalesInvoiceLineTotal1() {

        Item itemAppleS = getAppleS(PriceType.PER_DOZEN);
        Item itemAppleM = getAppleM(PriceType.PER_DOZEN);
        Item itemAppleL = getAppleL(PriceType.PER_DOZEN);
        Item itemAppleXL = getAppleXL(PriceType.PER_DOZEN);
        Item itemAppleXXL = getAppleXXL(PriceType.PER_DOZEN);

        List<SalesInvoiceLine> salesInvoiceLines = new ArrayList<>();
        salesInvoiceLines.add(getSILine(itemAppleS, DiscountMode.PERCENTAGE, BigDecimal.valueOf(15), 120L));
        salesInvoiceLines.add(getSILine(itemAppleM, DiscountMode.PERCENTAGE, BigDecimal.valueOf(15), 120L));
        salesInvoiceLines.add(getSILine(itemAppleL, DiscountMode.PERCENTAGE, BigDecimal.valueOf(15), 120L));
        salesInvoiceLines.add(getSILine(itemAppleXL, DiscountMode.PERCENTAGE, BigDecimal.valueOf(15), 120L));
        salesInvoiceLines.add(getSILine(itemAppleXXL, null, BigDecimal.ZERO, 120L));

        salesInvoiceLines = totalCalculator.updateSalesInvoiceLineTotal(salesInvoiceLines);

        for (SalesInvoiceLine line : salesInvoiceLines) {

            if (line.getItem().getId().equals(1)) {

                Assert.assertEquals("Apple S line G.total mismatch", 1500.00, line.getGrandTotal().doubleValue(), 0.0);
                Assert.assertEquals("Apple S line D.total mismatch", 225.00, line.getDiscountTotal().doubleValue(), 0.0);
                Assert.assertEquals("Apple S line N.total mismatch", 1275.00, line.getNetTotal().doubleValue(), 0.0);
            } else if (line.getItem().getId().equals(2)) {

                Assert.assertEquals("Apple M line G.total mismatch", 2500.00, line.getGrandTotal().doubleValue(), 0.0);
                Assert.assertEquals("Apple M line D.total mismatch", 375.00, line.getDiscountTotal().doubleValue(), 0.0);
                Assert.assertEquals("Apple M line N.total mismatch", 2125.00, line.getNetTotal().doubleValue(), 0.0);
            } else if (line.getItem().getId().equals(3)) {

                Assert.assertEquals("Apple L line G.total mismatch", 3500.00, line.getGrandTotal().doubleValue(), 0.0);
                Assert.assertEquals("Apple L line D.total mismatch", 525.00, line.getDiscountTotal().doubleValue(), 0.0);
                Assert.assertEquals("Apple L line N.total mismatch", 2975.00, line.getNetTotal().doubleValue(), 0.0);
            } else if (line.getItem().getId().equals(4)) {

                Assert.assertEquals("Apple XL line G.total mismatch", 4500.00, line.getGrandTotal().doubleValue(), 0.0);
                Assert.assertEquals("Apple XL line D.total mismatch", 675.00, line.getDiscountTotal().doubleValue(),
                        0.0);
                Assert.assertEquals("Apple XL line N.total mismatch", 3825.00, line.getNetTotal().doubleValue(), 0.0);
            } else if (line.getItem().getId().equals(5)) {

                Assert.assertEquals("Apple XXL line G.total mismatch", 5500.00, line.getGrandTotal().doubleValue(), 0.0);
                Assert.assertEquals("Apple XXL line D.total mismatch", 0.00, line.getDiscountTotal().doubleValue(), 0.0);
                Assert.assertEquals("Apple XXL line N.total mismatch", 5500.00, line.getNetTotal().doubleValue(), 0.0);
            }
        }

        SalesInvoice si = new SalesInvoice();
        totalCalculator.updateSalesInvoiceTotal(salesInvoiceLines, si);
        Assert.assertEquals("Apple XXL G.total mismatch", 17500.00, si.getGrandTotal().doubleValue(), 0.0);
        Assert.assertEquals("Apple XXL D.total mismatch", 1800.00, si.getDiscountTotal().doubleValue(), 0.0);
        Assert.assertEquals("Apple XXL N.total mismatch", 15700.00, si.getNetTotal().doubleValue(), 0.0);

    }

    @Test
    public void testUpdateSalesInvoiceLineTotal2() {

        Item itemAppleS = getAppleS(PriceType.PER_PIECE);
        Item itemAppleM = getAppleM(PriceType.PER_PIECE);
        Item itemAppleL = getAppleL(PriceType.PER_PIECE);
        Item itemAppleXL = getAppleXL(PriceType.PER_DOZEN);
        Item itemAppleXXL = getAppleXXL(PriceType.PER_PIECE);

        List<SalesInvoiceLine> salesInvoiceLines = new ArrayList<>();
        salesInvoiceLines.add(getSILine(itemAppleS, DiscountMode.VALUE, BigDecimal.valueOf(150), 120L));
        salesInvoiceLines.add(getSILine(itemAppleM, DiscountMode.VALUE, BigDecimal.valueOf(300), 120L));
        salesInvoiceLines.add(getSILine(itemAppleL, DiscountMode.VALUE, BigDecimal.valueOf(450), 120L));
        salesInvoiceLines.add(getSILine(itemAppleXL, DiscountMode.PERCENTAGE, BigDecimal.valueOf(20), 120L));
        salesInvoiceLines.add(getSILine(itemAppleXXL, null, BigDecimal.ZERO, 150L));

        salesInvoiceLines = totalCalculator.updateSalesInvoiceLineTotal(salesInvoiceLines);

        for (SalesInvoiceLine line : salesInvoiceLines) {

            if (line.getItem().getId().equals(1)) {

                Assert.assertEquals("Apple S line G.total mismatch", 18000.00, line.getGrandTotal().doubleValue(), 0.0);
                Assert.assertEquals("Apple S line D.total mismatch", 150.00, line.getDiscountTotal().doubleValue(), 0.0);
                Assert.assertEquals("Apple S line N.total mismatch", 17850.00, line.getNetTotal().doubleValue(), 0.0);
            } else if (line.getItem().getId().equals(2)) {

                Assert.assertEquals("Apple M line G.total mismatch", 30000.00, line.getGrandTotal().doubleValue(), 0.0);
                Assert.assertEquals("Apple M line D.total mismatch", 300.00, line.getDiscountTotal().doubleValue(), 0.0);
                Assert.assertEquals("Apple M line N.total mismatch", 29700.00, line.getNetTotal().doubleValue(), 0.0);
            } else if (line.getItem().getId().equals(3)) {

                Assert.assertEquals("Apple L line G.total mismatch", 42000.00, line.getGrandTotal().doubleValue(), 0.0);
                Assert.assertEquals("Apple L line D.total mismatch", 450.00, line.getDiscountTotal().doubleValue(), 0.0);
                Assert.assertEquals("Apple L line N.total mismatch", 41550.00, line.getNetTotal().doubleValue(), 0.0);
            } else if (line.getItem().getId().equals(4)) {

                Assert.assertEquals("Apple XL line G.total mismatch", 4500.00, line.getGrandTotal().doubleValue(), 0.0);
                Assert.assertEquals("Apple XL line D.total mismatch", 900.00, line.getDiscountTotal().doubleValue(),
                        0.0);
                Assert.assertEquals("Apple XL line N.total mismatch", 3600.00, line.getNetTotal().doubleValue(), 0.0);
            } else if (line.getItem().getId().equals(5)) {

                Assert.assertEquals("Apple XXL line G.total mismatch", 82500.00, line.getGrandTotal().doubleValue(),
                        0.0);
                Assert.assertEquals("Apple XXL line D.total mismatch", 0.00, line.getDiscountTotal().doubleValue(), 0.0);
                Assert.assertEquals("Apple XXL line N.total mismatch", 82500.00, line.getNetTotal().doubleValue(), 0.0);
            }
        }

        SalesInvoice si = new SalesInvoice();
        totalCalculator.updateSalesInvoiceTotal(salesInvoiceLines, si);
        Assert.assertEquals("Apple XXL G.total mismatch", 177000.00, si.getGrandTotal().doubleValue(), 0.0);
        Assert.assertEquals("Apple XXL D.total mismatch", 1800.00, si.getDiscountTotal().doubleValue(), 0.0);
        Assert.assertEquals("Apple XXL N.total mismatch", 175200.00, si.getNetTotal().doubleValue(), 0.0);

    }

    private GoodReceiveNoteLine getGRNLine(Item item, DiscountMode discountMode, BigDecimal discountVal, long quantity) {
        GoodReceiveNoteLine line = new GoodReceiveNoteLine();
        line.setItem(item);
        line.setDiscountMode(discountMode);
        line.setDiscountValue(discountVal);
        line.setQuantity(quantity);
        return line;
    }

    private SalesInvoiceLine getSILine(Item item, DiscountMode discountMode, BigDecimal discountVal, long quantity) {
        SalesInvoiceLine line = new SalesInvoiceLine();
        line.setItem(item);
        line.setDiscountMode(discountMode);
        line.setDiscountValue(discountVal);
        line.setQuantity(quantity);
        return line;
    }

    private Item getAppleS(PriceType priceType) {
        ItemPrice price = new ItemPrice();
        price.setBuyingPrice(BigDecimal.valueOf(100.00));
        price.setSellingPrice(BigDecimal.valueOf(150.00));
        price.setPriceType(priceType);

        Item item = new Item();
        item.setId(1);
        item.setName("APPLE S");
        item.setPrice(price);
        return item;
    }

    private Item getAppleM(PriceType priceType) {
        ItemPrice price = new ItemPrice();
        price.setBuyingPrice(BigDecimal.valueOf(200.00));
        price.setSellingPrice(BigDecimal.valueOf(250.00));
        price.setPriceType(priceType);

        Item item = new Item();
        item.setId(2);
        item.setName("APPLE M");
        item.setPrice(price);
        return item;
    }

    private Item getAppleL(PriceType priceType) {
        ItemPrice price = new ItemPrice();
        price.setBuyingPrice(BigDecimal.valueOf(300.00));
        price.setSellingPrice(BigDecimal.valueOf(350.00));
        price.setPriceType(priceType);

        Item item = new Item();
        item.setId(3);
        item.setName("APPLE L");
        item.setPrice(price);
        return item;
    }

    private Item getAppleXL(PriceType priceType) {
        ItemPrice price = new ItemPrice();
        price.setBuyingPrice(BigDecimal.valueOf(400.00));
        price.setSellingPrice(BigDecimal.valueOf(450.00));
        price.setPriceType(priceType);

        Item item = new Item();
        item.setId(4);
        item.setName("APPLE L");
        item.setPrice(price);
        return item;
    }

    private Item getAppleXXL(PriceType priceType) {
        ItemPrice price = new ItemPrice();
        price.setBuyingPrice(BigDecimal.valueOf(500.00));
        price.setSellingPrice(BigDecimal.valueOf(550.00));
        price.setPriceType(priceType);

        Item item = new Item();
        item.setId(5);
        item.setName("APPLE L");
        item.setPrice(price);
        return item;
    }

}

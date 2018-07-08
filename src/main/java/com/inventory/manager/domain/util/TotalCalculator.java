package com.inventory.manager.domain.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.inventory.manager.application.shared.dto.CalculateTotalRequestDTO;
import com.inventory.manager.application.shared.dto.CalculateTotalResponseDTO;
import com.inventory.manager.application.shared.dto.LineDTO;
import com.inventory.manager.domain.enums.DiscountMode;
import com.inventory.manager.domain.enums.InvoiceType;
import com.inventory.manager.domain.enums.PriceType;
import com.inventory.manager.domain.stockhistory.grn.GoodReceiveNote;
import com.inventory.manager.domain.stockhistory.grn.GoodReceiveNoteLine;
import com.inventory.manager.domain.stockhistory.sales.SalesInvoice;
import com.inventory.manager.domain.stockhistory.sales.SalesInvoiceLine;

@Component
public class TotalCalculator {

    private final static int DECIMAL_SCALE = 2;

    private final static BigDecimal DOZEN_VALUE = BigDecimal.valueOf(12);

    /**
     * Update GRN line total.
     *
     * @param goodReceiveNoteLines the good receive note lines
     * @return the list
     */
    public List<GoodReceiveNoteLine> updateGRNLineTotal(List<GoodReceiveNoteLine> goodReceiveNoteLines) {

        for (GoodReceiveNoteLine grnl : goodReceiveNoteLines) {

            DiscountMode discountMode = grnl.getDiscountMode();
            BigDecimal discountTotal = BigDecimal.ZERO;

            // default quantity in pieces
            BigDecimal quantity = BigDecimal.valueOf(grnl.getQuantity());

            if (PriceType.PER_DOZEN == grnl.getItem().getPrice().getPriceType()) {
                // calculate the quantity in dozen
                quantity = BigDecimal.valueOf(grnl.getQuantity()).divide(DOZEN_VALUE, DECIMAL_SCALE,
                        RoundingMode.HALF_EVEN);
            }

            BigDecimal grandTotal = grnl.getItem().getPrice().getBuyingPrice().multiply(quantity);
            if (discountMode != null) {
                if (DiscountMode.PERCENTAGE == discountMode) {
                    discountTotal = grandTotal.multiply(grnl.getDiscountValue()).divide(BigDecimal.valueOf(100));
                } else {
                    discountTotal = grnl.getDiscountValue();
                }
            }

            BigDecimal netTotal = grandTotal.subtract(discountTotal);
            grnl.setGrandTotal(grandTotal);
            grnl.setDiscountTotal(discountTotal);
            grnl.setNetTotal(netTotal);
        }

        return goodReceiveNoteLines;

    }

    /**
     * Update GRN total.
     *
     * @param goodReceiveNoteLines the good receive note lines
     * @param grn the grn
     * @return the good receive note
     */
    public GoodReceiveNote updateGRNTotal(List<GoodReceiveNoteLine> goodReceiveNoteLines, GoodReceiveNote grn) {

        BigDecimal grandTotal = goodReceiveNoteLines.stream().map(GoodReceiveNoteLine::getGrandTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal discountTotal = goodReceiveNoteLines.stream().map(GoodReceiveNoteLine::getDiscountTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal netTotal = goodReceiveNoteLines.stream().map(GoodReceiveNoteLine::getNetTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        grn.setGrandTotal(grandTotal);
        grn.setDiscountTotal(discountTotal);
        grn.setNetTotal(netTotal);

        return grn;

    }

    /**
     * Update sales invoice line total.
     *
     * @param salesInvoiceLines the sales invoice lines
     * @return the list
     */
    public List<SalesInvoiceLine> updateSalesInvoiceLineTotal(List<SalesInvoiceLine> salesInvoiceLines) {

        for (SalesInvoiceLine sil : salesInvoiceLines) {

            DiscountMode discountMode = sil.getDiscountMode();
            BigDecimal discountTotal = BigDecimal.ZERO;

            // default quantity in pieces
            BigDecimal quantity = BigDecimal.valueOf(sil.getQuantity());

            if (PriceType.PER_DOZEN == sil.getItem().getPrice().getPriceType()) {
                // calculate the quantity in dozen
                quantity = BigDecimal.valueOf(sil.getQuantity()).divide(DOZEN_VALUE, DECIMAL_SCALE,
                        RoundingMode.HALF_EVEN);
            }

            BigDecimal grandTotal = sil.getItem().getPrice().getSellingPrice().multiply(quantity);
            if (discountMode != null) {
                if (DiscountMode.PERCENTAGE == discountMode) {
                    discountTotal = grandTotal.multiply(sil.getDiscountValue()).divide(BigDecimal.valueOf(100));
                } else {
                    discountTotal = sil.getDiscountValue();
                }
            }

            BigDecimal netTotal = grandTotal.subtract(discountTotal);
            sil.setGrandTotal(grandTotal);
            sil.setDiscountTotal(discountTotal);
            sil.setNetTotal(netTotal);
        }

        return salesInvoiceLines;

    }

    /**
     * Update sales invoice total.
     *
     * @param sil the sil
     * @param salesInvoice the sales invoice
     * @return the sales invoice
     */
    public SalesInvoice updateSalesInvoiceTotal(List<SalesInvoiceLine> sil, SalesInvoice salesInvoice) {

        BigDecimal grandTotal = sil.stream().map(SalesInvoiceLine::getGrandTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal discountTotal = sil.stream().map(SalesInvoiceLine::getDiscountTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal netTotal = sil.stream().map(SalesInvoiceLine::getNetTotal).reduce(BigDecimal.ZERO, BigDecimal::add);

        salesInvoice.setGrandTotal(grandTotal);
        salesInvoice.setDiscountTotal(discountTotal);
        salesInvoice.setNetTotal(netTotal);

        return salesInvoice;

    }

    /**
     * Calculate total.
     *
     * @param requestDTO the request DTO
     * @param invoiceType the invoice type
     * @return the calculate total response DTO
     */
    public CalculateTotalResponseDTO calculateTotal(CalculateTotalRequestDTO requestDTO, InvoiceType invoiceType) {

        CalculateTotalResponseDTO responseDTO = new CalculateTotalResponseDTO();

        BigDecimal grandTotal = BigDecimal.ZERO;
        BigDecimal discountTotal = BigDecimal.ZERO;
        BigDecimal netTotal = BigDecimal.ZERO;

        for (LineDTO line : requestDTO.getLines()) {

            DiscountMode discountMode = null;
            if (StringUtils.hasText(line.getDiscountMode())) {
                discountMode = DiscountMode.valueOf(line.getDiscountMode().toUpperCase(Locale.ENGLISH));
            }

            BigDecimal lineDiscountTotal = BigDecimal.ZERO;

            // default quantity in pieces
            BigDecimal quantity = BigDecimal.valueOf(line.getQuantity());

            if (PriceType.PER_DOZEN == PriceType.valueOf(line.getItem().getPrice().getPriceType()
                    .toUpperCase(Locale.ENGLISH))) {
                // calculate the quantity in dozen
                quantity = BigDecimal.valueOf(line.getQuantity()).divide(DOZEN_VALUE, DECIMAL_SCALE,
                        RoundingMode.HALF_EVEN);
            }

            BigDecimal buyingPrice = null;
            if (InvoiceType.GRN == invoiceType) {
                buyingPrice = BigDecimal.valueOf(line.getItem().getPrice().getBuyingPrice());
            } else {
                buyingPrice = BigDecimal.valueOf(line.getItem().getPrice().getSellingPrice());
            }

            BigDecimal discountnValue = BigDecimal.valueOf(line.getDiscountValue());
            BigDecimal lineGrandTotal = buyingPrice.multiply(quantity);
            if (discountMode != null) {
                if (DiscountMode.PERCENTAGE == discountMode) {
                    lineDiscountTotal = lineGrandTotal.multiply(discountnValue).divide(BigDecimal.valueOf(100));
                } else {
                    lineDiscountTotal = discountnValue;
                }
            }

            BigDecimal lineNetTotal = lineGrandTotal.subtract(lineDiscountTotal);
            line.setGrandTotal(lineGrandTotal.doubleValue());
            line.setDiscountTotal(lineDiscountTotal.doubleValue());
            line.setNetTotal(lineNetTotal.doubleValue());

            grandTotal = grandTotal.add(lineGrandTotal);
            discountTotal = discountTotal.add(lineDiscountTotal);
            netTotal = netTotal.add(lineNetTotal);

        }

        responseDTO.setLines(requestDTO.getLines());
        responseDTO.setGrandTotal(grandTotal.doubleValue());
        responseDTO.setDiscountTotal(discountTotal.doubleValue());
        responseDTO.setNetTotal(netTotal.doubleValue());

        return responseDTO;

    }
}

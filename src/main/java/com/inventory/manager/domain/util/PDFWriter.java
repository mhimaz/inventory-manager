package com.inventory.manager.domain.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.inventory.manager.domain.enums.DiscountMode;
import com.inventory.manager.domain.enums.PriceType;
import com.inventory.manager.domain.stockhistory.sales.SalesInvoice;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Font.FontStyle;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Component
public class PDFWriter {

    private final static int DECIMAL_SCALE = 2;

    private final static BigDecimal DOZEN_VALUE = BigDecimal.valueOf(12);

    private final static DecimalFormat QUANTITY_FORMAT = new DecimalFormat("0.##");

    private final static DecimalFormat PERCENTAGE_FORMAT = new DecimalFormat("##0.##");

    private final static NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(new Locale("en", "LK"));

    public void write(SalesInvoice salesInvoice) {

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("D:\\Inventory\\sales_invoice_" + salesInvoice.getId()
                    + ".pdf"));

            document.open();

            PdfPTable invoiceHeader = prepareInvoiceHeader(salesInvoice);
            document.add(invoiceHeader);

            float[] columnWidths = {43, 11, 15, 13, 18};
            PdfPTable invoiceTable = new PdfPTable(5);
            invoiceTable.setWidthPercentage(90);
            invoiceTable.setWidths(columnWidths);

            addTableHeader(invoiceTable);
            addRows(invoiceTable, salesInvoice);
            addTotalRows(invoiceTable, salesInvoice);

            document.add(invoiceTable);
            document.close();
        } catch (Exception e) {
            System.err.println(e);

        }
    }

    private PdfPTable prepareInvoiceHeader(SalesInvoice salesInvoice) throws DocumentException, URISyntaxException,
            BadElementException, MalformedURLException, IOException {
        float[] columnWidths = {15, 60, 25};
        PdfPTable invoiceHeader = new PdfPTable(3);
        invoiceHeader.setWidthPercentage(90);
        invoiceHeader.setWidths(columnWidths);
        invoiceHeader.setSpacingAfter(10);
        Path path = Paths.get(getClass().getClassLoader().getResource("/images/metco_marketing_logo.jpg").toURI());
        Image img = Image.getInstance(path.toAbsolutePath().toString());
        img.scalePercent(10);

        PdfPCell logoCell = new PdfPCell(img, true);
        logoCell.setRowspan(3);
        logoCell.setBorder(0);
        invoiceHeader.addCell(logoCell);

        PdfPCell companyNameCell = new PdfPCell();
        companyNameCell.setBorder(0);
        companyNameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        companyNameCell.setPhrase(new Phrase("Metco Marketing (Pvt) Ltd.", new Font(FontFamily.COURIER, 14,
                FontStyle.BOLD.ordinal())));
        invoiceHeader.addCell(companyNameCell);

        PdfPCell invoiceTitleCell = new PdfPCell();
        invoiceTitleCell.setBorder(0);
        invoiceTitleCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        invoiceTitleCell.setPhrase(new Phrase("INVOICE", new Font(FontFamily.COURIER, 20, FontStyle.BOLD.ordinal())));
        invoiceHeader.addCell(invoiceTitleCell);

        PdfPCell addressCell = new PdfPCell();
        addressCell.setBorder(0);
        addressCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        addressCell.setPhrase(new Phrase("17/1, Sri Sunandarama Road, Kalubowila", new Font(FontFamily.COURIER, 12)));
        invoiceHeader.addCell(addressCell);

        PdfPCell dateCell = new PdfPCell();
        dateCell.setBorder(0);
        dateCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        dateCell.setPhrase(new Phrase("Date: " + salesInvoice.getSoldDate().toString("dd/MM/YYYY"), new Font(
                FontFamily.COURIER, 12)));
        invoiceHeader.addCell(dateCell);

        PdfPCell contactCell = new PdfPCell();
        contactCell.setBorder(0);
        contactCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        contactCell.setPhrase(new Phrase("Phone: 077-7466766 \t Fax: 011-2432601 \n Email: metco_marketing@gmail.com",
                new Font(FontFamily.COURIER, 12)));
        invoiceHeader.addCell(contactCell);

        PdfPCell invoiceNoCell = new PdfPCell();
        invoiceNoCell.setBorder(0);
        invoiceNoCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        invoiceNoCell
                .setPhrase(new Phrase("Invoice#: " + salesInvoice.getReceiptNo(), new Font(FontFamily.COURIER, 12)));
        invoiceHeader.addCell(invoiceNoCell);
        return invoiceHeader;
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("ITEM", "UNITS", "UNIT PRICE", "DISCOUNT", "LINE TOTAL").forEach(columnTitle -> {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            // header.setBorderWidth(1);
                header.setPhrase(new Phrase(columnTitle, new Font(FontFamily.COURIER, 10, FontStyle.BOLD.ordinal())));
                table.addCell(header);
            });
    }

    private void addRows(PdfPTable table, SalesInvoice salesInvoice) {

        salesInvoice.getSalesInvoiceLines().forEach(line -> {

            // default quantity in pieces
                BigDecimal quantity = BigDecimal.valueOf(line.getQuantity());
                String qtySuffix = "Pcs";

                if (PriceType.PER_DOZEN == line.getItem().getPrice().getPriceType()) {
                    // calculate the quantity in dozen
                    quantity = BigDecimal.valueOf(line.getQuantity()).divide(DOZEN_VALUE, DECIMAL_SCALE,
                            RoundingMode.HALF_EVEN);
                    qtySuffix = "Dz";
                }

                PdfPCell itemNameCell = new PdfPCell();
                itemNameCell.setPhrase(new Phrase(line.getItem().getName(), new Font(FontFamily.COURIER, 10)));
                table.addCell(itemNameCell);

                PdfPCell quantityCell = new PdfPCell();
                quantityCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                quantityCell.setPhrase(new Phrase(QUANTITY_FORMAT.format(quantity.doubleValue()) + " " + qtySuffix,
                        new Font(FontFamily.COURIER, 10)));
                table.addCell(quantityCell);

                PdfPCell unitPriceCell = new PdfPCell();
                unitPriceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                unitPriceCell.setPhrase(new Phrase(CURRENCY_FORMAT.format(
                        line.getItem().getPrice().getSellingPrice().doubleValue()).replaceAll("LKR", ""), new Font(
                        FontFamily.COURIER, 10)));
                table.addCell(unitPriceCell);

                PdfPCell discountCell = new PdfPCell();
                discountCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                String discount = "";

                if (line.getDiscountMode() != null) {
                    if (DiscountMode.PERCENTAGE == line.getDiscountMode()) {
                        discount = PERCENTAGE_FORMAT.format(line.getDiscountValue().doubleValue()) + "%";
                    } else {
                        discount = CURRENCY_FORMAT.format(line.getDiscountValue().doubleValue()).replaceAll("LKR", "");
                    }
                }
                discountCell.setPhrase(new Phrase(discount, new Font(FontFamily.COURIER, 10)));
                table.addCell(discountCell);

                PdfPCell grandTotalCell = new PdfPCell();
                grandTotalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                grandTotalCell.setPhrase(new Phrase(CURRENCY_FORMAT.format(line.getGrandTotal().doubleValue())
                        .replaceAll("LKR", ""), new Font(FontFamily.COURIER, 10)));
                table.addCell(grandTotalCell);
            });
    }

    private void addTotalRows(PdfPTable table, SalesInvoice salesInvoice) {

        PdfPCell grandTotalTitleCell = new PdfPCell(new Phrase("Grand Total", new Font(FontFamily.COURIER, 10)));
        grandTotalTitleCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        grandTotalTitleCell.setColspan(4);
        table.addCell(grandTotalTitleCell);

        PdfPCell grandTotalValueCell = new PdfPCell(new Phrase(CURRENCY_FORMAT.format(
                salesInvoice.getGrandTotal().doubleValue()).replaceAll("LKR", ""), new Font(FontFamily.COURIER, 10)));
        grandTotalValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(grandTotalValueCell);

        PdfPCell discountTotalTitleCell = new PdfPCell(new Phrase("Discount Total", new Font(FontFamily.COURIER, 10)));
        discountTotalTitleCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        discountTotalTitleCell.setColspan(4);
        table.addCell(discountTotalTitleCell);

        PdfPCell discountTotalValueCell = new PdfPCell(new Phrase(CURRENCY_FORMAT.format(
                salesInvoice.getDiscountTotal().doubleValue()).replaceAll("LKR", ""), new Font(FontFamily.COURIER, 10)));
        discountTotalValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(grandTotalValueCell);

        PdfPCell netTotalTitleCell = new PdfPCell(new Phrase("Net Total", new Font(FontFamily.COURIER, 10)));
        netTotalTitleCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        netTotalTitleCell.setColspan(4);
        table.addCell(netTotalTitleCell);

        PdfPCell netTotalValueCell = new PdfPCell(new Phrase(CURRENCY_FORMAT.format(
                salesInvoice.getNetTotal().doubleValue()).replaceAll("LKR", ""), new Font(FontFamily.COURIER, 10)));
        netTotalValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(netTotalValueCell);

    }
}

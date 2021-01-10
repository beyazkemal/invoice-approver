package com.kemalbeyaz.invoice.approver;

import com.kemalbeyaz.invoice.approver.model.Invoice;
import com.kemalbeyaz.invoice.approver.model.PurchasingSpecialist;
import com.kemalbeyaz.invoice.approver.repository.InvoiceRepository;
import com.kemalbeyaz.invoice.approver.repository.PurchasingSpecialistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author kemal.beyaz
 */
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = {"classpath:application-test.yml"})
class InvoiceServiceTest {

    @Value("${purchasing.specialist.limit}")
    private double purchasingSpecialistLimit;

    @MockBean
    private InvoiceRepository invoiceRepository;
    @MockBean
    private PurchasingSpecialistRepository purchasingSpecialistRepository;

    @Test
    void saveApproved() {
        double amount = 100;
        String email = "john@doe.com";

        PurchasingSpecialist purchasingSpecialist = new PurchasingSpecialist();
        purchasingSpecialist.setFirstName("John");
        purchasingSpecialist.setLastName("Doe");
        purchasingSpecialist.setEmail(email);

        Invoice invoice = new Invoice();
        invoice.setPurchasingSpecialist(purchasingSpecialist);
        invoice.setProductName("USB Disc");
        invoice.setBillNo("TR001");
        invoice.setAmount(amount);

        assertDoesNotThrow(() -> getInvoiceService().save(invoice));
    }

    @Test
    void saveButNotApproved() {
        PurchasingSpecialist purchasingSpecialist = new PurchasingSpecialist();
        purchasingSpecialist.setFirstName("John");
        purchasingSpecialist.setLastName("Doe");
        purchasingSpecialist.setEmail("john@doe.com");

        Invoice invoice = new Invoice();
        invoice.setPurchasingSpecialist(purchasingSpecialist);
        invoice.setProductName("USB Disc");
        invoice.setBillNo("TR001");
        invoice.setAmount(purchasingSpecialistLimit + 1);

        IllegalArgumentException throwException = assertThrows(IllegalArgumentException.class,
                () -> getInvoiceService().save(invoice));

        assertEquals("Fatura limiti aşıldığı için onaylanmadı!", throwException.getMessage());
    }

    @Test
    void findAll() {
        List<Invoice> invoices = getInvoiceService().findAll();
        assertEquals(0, invoices.size());
    }

    @Test
    void findAllApproved() {
        List<Invoice> invoices = getInvoiceService().findAllApproved();
        assertEquals(0, invoices.size());
    }

    @Test
    void findAllNonApproved() {
        List<Invoice> invoices = getInvoiceService().findAllNonApproved();
        assertEquals(0, invoices.size());
    }

    private InvoiceService getInvoiceService() {
        InvoiceService invoiceService = new InvoiceService(invoiceRepository, purchasingSpecialistRepository);
        invoiceService.setPurchasingSpecialistLimit(purchasingSpecialistLimit);
        return invoiceService;
    }
}
package com.kemalbeyaz.invoice.approver;

import com.kemalbeyaz.invoice.approver.model.Invoice;
import com.kemalbeyaz.invoice.approver.model.PurchasingSpecialist;
import com.kemalbeyaz.invoice.approver.repository.InvoiceRepository;
import com.kemalbeyaz.invoice.approver.repository.PurchasingSpecialistRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author kemal.beyaz
 */
@Service
public class InvoiceService {

    public static final String LIMIT_EXCEEDED_MSG = "Fatura limiti aşıldığı için onaylanmadı!";

    @Value("${purchasing.specialist.limit}")
    private double purchasingSpecialistLimit;

    private final InvoiceRepository invoiceRepository;
    private final PurchasingSpecialistRepository purchasingSpecialistRepository;

    public InvoiceService(InvoiceRepository invoiceRepository, PurchasingSpecialistRepository purchasingSpecialistRepository) {
        this.invoiceRepository = invoiceRepository;
        this.purchasingSpecialistRepository = purchasingSpecialistRepository;
    }

    public void setPurchasingSpecialistLimit(double purchasingSpecialistLimit) {
        this.purchasingSpecialistLimit = purchasingSpecialistLimit;
    }

    /**
     * Satın alım uzmanı için limit kontrolü yaptıktan sonra faturayı kaydeder.
     */
    public Invoice save(Invoice invoice) {
        findOrSavePurchasingSpecialistAndSet(invoice);

        // Belirtilen mail adresi için (Satın Alım Uzmanı) limit kontrolü yapılıyor.
        boolean isLimitExceeded = checkPurchasingSpecialistLimitFor(invoice);
        if (!isLimitExceeded) {
            // Limit aşılmamış, onaylandı olarak işaretlenip kaydediliyor.
            invoice.setApproved(true);
            return invoiceRepository.save(invoice);
        }

        // limit aşılmış, kaydedilip hata fırlatılıyor.
        invoiceRepository.save(invoice);
        throw new IllegalArgumentException(LIMIT_EXCEEDED_MSG);
    }

    /**
     * Kaydedilen tüm faturaları listeler.
     */
    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

    /**
     * Onaylanmış faturaları listeler.
     */
    public List<Invoice> findAllApproved() {
        return invoiceRepository.findAllByApproved(true);
    }

    /**
     * Onaylanmamış faturaları listeler.
     */
    public List<Invoice> findAllNonApproved() {
        return invoiceRepository.findAllByApproved(false);
    }

    /**
     * Faturada belirtilen mail adresi (Satın Alım Uzmanı) için fatura tutar limit kontrolüni yapar.
     */
    private boolean checkPurchasingSpecialistLimitFor(Invoice invoice) {
        // Belirtilen mail adresi (Satın Alım Uzmanı) için onaylanmış faturalar bulunuyor.
        List<Invoice> invoicesByEmail = invoiceRepository.findAllByApprovedAndPurchasingSpecialistEmail(
                true, invoice.getPurchasingSpecialist().getEmail());

        // Bu faturaların toplam tutarı hesaplanıyor.
        double sumApprovedAmount = invoicesByEmail.stream()
                .mapToDouble(Invoice::getAmount)
                .sum();

        // Yeni faturadaki tutar, toplam tutara ekleniyor.
        double currentSum = Double.sum(sumApprovedAmount, invoice.getAmount());

        // Elde edilen yeni toplam tutar ile satış temsilcisi limiti karşılaştırılıyor.
        return Double.compare(currentSum, purchasingSpecialistLimit) > 0;
    }

    /**
     * checkPurchasingSpecialistLimitFor() methodu ile aynı işleve sahiptir.
     * Sadece toplam tutar veri tabanı üzerinde hesaplanmıştır.
     */
    private boolean checkPurchasingSpecialistLimitOnDbFor(Invoice invoice) {
        Double sumApprovedAmount = invoiceRepository.sumApprovedAmountFor(invoice.getPurchasingSpecialist().getEmail());
        sumApprovedAmount = sumApprovedAmount == null ? 0 : sumApprovedAmount;

        double currentSum = Double.sum(sumApprovedAmount, invoice.getAmount());
        return Double.compare(currentSum, purchasingSpecialistLimit) > 0;
    }

    /**
     * Faturada belirtilen mail adresi (Satın Alım Uzmanı) daha önce kaydedilmiş ise email adresi aracılığı ile bulunur
     * kaydedilmeiş ise kaydedilir.
     */
    private void findOrSavePurchasingSpecialistAndSet(Invoice invoice) {
        String email = invoice.getPurchasingSpecialist().getEmail();
        Optional<PurchasingSpecialist> purchasingSpecialistOptional = purchasingSpecialistRepository.findByEmail(email);

        if (purchasingSpecialistOptional.isPresent()) {
            PurchasingSpecialist purchasingSpecialist = purchasingSpecialistOptional.get();
            invoice.setPurchasingSpecialist(purchasingSpecialist);
        } else {
            purchasingSpecialistRepository.save(invoice.getPurchasingSpecialist());
        }
    }
}
